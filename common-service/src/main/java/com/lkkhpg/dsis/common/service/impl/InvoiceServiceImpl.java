/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lkkhpg.dsis.common.config.mapper.SpmInvNumberingMapper;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.dto.Invoice;
import com.lkkhpg.dsis.common.order.mapper.InvoiceMapper;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.common.service.ICommSpmInvNumberingService;
import com.lkkhpg.dsis.common.service.IInvoiceService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.report.Report;
import com.lkkhpg.dsis.platform.report.exception.ReportException;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;
import com.lkkhpg.dsis.platform.report.service.IReportService;
import com.lkkhpg.dsis.platform.service.IMessageService;

/**
 * 发票接口实现类.
 * 
 * @author li.peng@hand-china.com
 *
 */
@Service
@Transactional
public class InvoiceServiceImpl implements IInvoiceService {

    private Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Autowired
    private ICommSpmInvNumberingService spmInvNumberingService;

    @Autowired
    private ICommSalesOrderService commSalesOrderService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private SpmInvNumberingMapper spmInvNumberingMapper;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private static final String ZERO = "0";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Invoice createInvoice(IRequest request, Long orderId) throws CommOrderException, CommSystemProfileException {
        // 检查是否已开发票
        Invoice invoice = invoiceMapper.selectInvoiceByOrderId(orderId);
        if (invoice != null && invoice.getInvoiceNumber() != null && invoice.getAttachmentId() > 0) {
            return invoice;
        }
        if (invoice == null) {
            invoice = new Invoice();
        }
        invoice.setOrderId(orderId);
        
        Map<String, Object> salesOrderInfo = spmInvNumberingMapper.selectMarket(orderId);
        if (salesOrderInfo == null || salesOrderInfo.get("marketId") == null
                || salesOrderInfo.get("salesOrgId") == null) {
            return null;
        }

        if (invoice.getInvoiceNumber() == null ) {
            String invoiceNumber = spmInvNumberingService.createInvoiceNumber(request, orderId);
            if (invoiceNumber == null) {
                // 0 元不开票
                return null;
            }
            if (invoiceNumber.isEmpty()) {
                throw new CommOrderException(CommOrderException.INVOICE_NUMBER_EXCEPTON, new Object[]{});
            }
            invoice.setInvoiceNumber(invoiceNumber);

            // 更新订单发票编号
            commSalesOrderService.updateInvoiceNumberByHeaderId(request, invoiceNumber, orderId);

            BigDecimal invoiceAmount = new BigDecimal(salesOrderInfo.get("actrualPayAmt").toString());

            invoice.setInvoiceAmount(invoiceAmount);

            invoice.setMarketId(Long.parseLong(salesOrderInfo.get("marketId").toString()));
        }
        // 生成PDF
        Map<String, String> reportInfo = invoiceMapper.selectInvoiceReport(invoice.getMarketId());
        if (reportInfo != null && reportInfo.size() > 0) {
            String dataServiceName = reportInfo.get("DATA_SERVICE_NAME");
            String template = reportInfo.get("FILE_PATH");
            if (logger.isDebugEnabled()) {
                logger.debug("generating invoice pdf...");
                logger.debug("dataServiceName is : {}", dataServiceName);
                logger.debug("template is : {}", template);
            }

            IReportDataService dataService = (IReportDataService) beanFactory.getBean(dataServiceName);

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("headerId", orderId.toString());
            Report report = null;

            try {
                report = reportService.generateReport(request, dataService, paramMap, template, "PDF", invoice.getInvoiceNumber(),
                        true);
                // 附件id
                invoice.setAttachmentId(report.getFileId());
            } catch (ReportException e) {
                if (logger.isErrorEnabled()) {
                    logger.error("error when generate invoice pdf!", e);
                }
            }

        }
        if (invoice.getAttachmentId() == null) {
            invoice.setAttachmentId(-1L);
        }
        if (invoice.getInvoiceId() != null) {
            invoiceMapper.updateInvoice(invoice);
        } else {
            invoiceMapper.insertInvoice(invoice);
        }
        if (!sendInvoiceEmail(salesOrderInfo, invoice)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Send Email error!");
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Send Email ended!");
            }
        }

        return invoice;
    }

    /**
     * 发送邮件给会员.
     * 
     * @param salesOrderInfo
     *            订单信息
     * @param invoice
     *            发票
     * @return true-发送结束，false-发送失败
     * @throws Exception
     *             发送邮件异常
     */
    private boolean sendInvoiceEmail(Map<String, Object> salesOrderInfo, Invoice invoice) {
        // 没有会员, 无需发送邮件
        if (salesOrderInfo.get("memberId") == null) {
            return true;
        }

        Long memberId = Long.parseLong(salesOrderInfo.get("memberId").toString());
        Long marketId = Long.parseLong(salesOrderInfo.get("marketId").toString());
        // 发送邮件
        List<Long> attachmentIds = new ArrayList<Long>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver messageReceiver = new MessageReceiver();

        Member member = memberMapper.selectByPrimaryKey(memberId);
        if (member == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("MemberId not found! memberId : {}", new Object[] { memberId });
            }
            return false;
        }
        if (StringUtils.isEmpty(member.getEmail())) {
            if (logger.isDebugEnabled()) {
                logger.debug("The Member not e-mail! memberId : {}", new Object[] { memberId });
            }
            return false;
        }
        // 组装邮件模板信息
        data.put("orderNumber", salesOrderInfo.get("orderNumber"));
        data.put("invoiceNumber", invoice.getInvoiceNumber());
        messageReceiver.setMessageId(memberId);
        messageReceiver.setMessageAddress(member.getEmail());
        messageReceiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receiverlist.add(messageReceiver);
        attachmentIds.add(invoice.getAttachmentId());

        taskExecutor.execute(() -> {
            // 邮件发送账号需确认
            try {
                messageService.sendEmailMessage(-1L, marketId, OrderConstants.EMAIL_ORDER_INVOICE,
                        OrderConstants.EMAIL_ORDER_INVOICE_ACCOUNT, data, receiverlist, attachmentIds);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("send Email Error", e);
                }
            }
        });

        return true;
    }

    @Override
    public List<Invoice> queryInvoice(IRequest request, Invoice invoice) {
        return invoiceMapper.selectByInvoice(invoice);
    }

    @Override
    public Invoice printInvoice(IRequest request, Long orderId) throws CommOrderException, CommSystemProfileException {
        Invoice invoice;

        invoice = invoiceMapper.selectInvoiceByOrderId(orderId);

        if (invoice == null || invoice.getAttachmentId().equals(-1L)) {
            invoice = self().createInvoice(request, orderId);
        }

        return invoice;
    }
}
