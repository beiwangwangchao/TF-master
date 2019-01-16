/*
 *
 */
package com.lkkhpg.dsis.common.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesAdjustment;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.order.dto.SalesVoucher;
import com.lkkhpg.dsis.common.order.mapper.OrderPaymentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesAdjustmentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLogisticsMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesSitesMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesVoucherMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;
import com.lkkhpg.dsis.platform.service.IAccountService;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * 马来西亚发票模板.
 * 
 * @author xukaidi
 */

@Service("mlInvoiceDataService")
public class MLInvoiceDataServiceImpl implements IReportDataService {

    private static Logger logger = LoggerFactory.getLogger(MLInvoiceDataServiceImpl.class);

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SalesLineMapper salesLineMapper;

    @Autowired
    private OrderPaymentMapper orderPaymentMapper;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private SalesAdjustmentMapper salesAdjustmentMapper;

    @Autowired
    private SalesVoucherMapper salesVoucherMapper;

    @Autowired
    private SalesLogisticsMapper salesLogisticsMapper;

    @Autowired
    private SalesSitesMapper salesSitesMapper;

    @Autowired
    private SpmPeriodMapper spmPeriodMapper;

    @Autowired
    private ICodeService codeService;

    private final String SHIP = "SHIP";

    private final String BILL = "BILL";

    @Override
    public Map<String, Object> process(IRequest request, Map<String, Object> paramMap) {
        debugMsg("====================start process in MLInvoiceDataServiceImpl======================");
        Map<String, Object> result = new HashMap<>();
        String headerIdStr = (String) paramMap.get("headerId");
        if (StringUtils.isEmpty(headerIdStr)) {
            return result;
        }
        Long headerId = Long.parseLong(headerIdStr);
        // Long orderId = (Long) paramMap.get("orderId");
        debugMsg("====================headerId is {}======================", headerId);
        // 根据订单头ID获取订单信息
        SalesOrder head = salesOrderMapper.selectSalesOrderByHeadId(headerId);
        // 根据订单的会员ID获取对应的会员信息
        if (head != null && head.getMemberId() != null) {
            SalesOrder salesOrder = salesOrderMapper.getBrNo(head.getMemberId());
            if (salesOrder != null && salesOrder.getMemberName() != null) {
                head.setMemberName(salesOrder.getMemberName());
            }
            if (salesOrder != null && salesOrder.getMemberCode() != null) {
                head.setMemberCode(salesOrder.getMemberCode());
            }
            if (salesOrder != null && salesOrder.getBrNo() != null) {
                head.setBrNo(salesOrder.getBrNo());
            }
            if (salesOrder != null && salesOrder.getGstId() != null) {
                head.setGstId(salesOrder.getGstId());
            }
        }
        // 获取奖金期间
        SpmPeriod spmPeriod = spmPeriodMapper.selectByPrimaryKey(head.getPeriodId());
        String period = "";
        if (spmPeriod != null) {
            period = spmPeriod.getPeriodName();
        }
        // 订单行
        List<SalesLine> lines = salesLineMapper.querySalesLineByHeaderId(paramMap);
        // 支付方式合计金额
        List<OrderPayment> orderPayments = orderPaymentMapper.selectPayMethodAmtByHeaderId(headerId);
        for (OrderPayment orderPayment : orderPayments) {
            CodeValue codevalue = codeService.getCodeValue(request, "OM.PAY_METHOD_MODIFY",
                    orderPayment.getPaymentMethod());
            if (codevalue != null) {
                orderPayment.setPaymentMethod(codevalue.getMeaning());
            }
            CodeValue codevalue2 = codeService.getCodeValue(request, "OM.PAY_METHOD_FIXED",
                    orderPayment.getPaymentMethod());
            if (codevalue2 != null) {
                orderPayment.setPaymentMethod(codevalue2.getMeaning());
            }
        }
        // 获取优惠
        List<SalesAdjustment> salesAdjustments = salesAdjustmentMapper.selectByHeaderId(headerId);
        BigDecimal adjustment = BigDecimal.ZERO;
        for (SalesAdjustment salesAdjustment : salesAdjustments) {
            adjustment = adjustment.add(salesAdjustment.getAdjustmentAmount());
        }
        // 获取折扣
        List<SalesVoucher> salesVouchers = salesVoucherMapper.getVouchersByOrderId(headerId);
        BigDecimal voucher = BigDecimal.ZERO;
        for (SalesVoucher salesVoucher : salesVouchers) {
            if (salesVoucher.getDiscountValue() != null) {
                voucher = voucher.add(salesVoucher.getDiscountValue().negate());
            }
        }
        // 获取运费
        SalesLogistics salesLogistics = salesLogisticsMapper.selectByHeaderId(headerId, "N");

        // 获取指定用户名
        Account account = accountService.getAccountByAccountId(request.getAccountId());
        String username = account.getLoginName();
        // 获取订单配送类型
        CodeValue codevalue = codeService.getCodeValue(request, "DM.DELIVERY_TYPE", head.getDeliveryType());
        if (codevalue != null) {
            head.setDeliveryType(codevalue.getMeaning());
        }
        // 收货人和收货地址
        // 订单的收货人
        String shipTo = "";
        String telNo = "";
        // List<SalesSites> salesSites =
        // salesSitesMapper.selectSitesByHeaderIdAndSiteType(SHIP, headerId);
        List<SalesSites> salesSites2 = salesSitesMapper.selectSites(SHIP, headerId);
        if (!salesSites2.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(salesSites2.get(0).getAddress1() == null ? "" : salesSites2.get(0).getAddress1() + ",")
                    .append(salesSites2.get(0).getAddress2() == null ? "" : salesSites2.get(0).getAddress2() + ",")
                    .append(salesSites2.get(0).getAddress3() == null ? "" : salesSites2.get(0).getAddress3() + ",")
                    .append(salesSites2.get(0).getZipCode() == null ? "" : salesSites2.get(0).getZipCode() + ",")
                    .append(salesSites2.get(0).getCityName()).append(",")
                    .append(salesSites2.get(0).getCountryName());
            shipTo = salesSites2.get(0).getName() + "/" + sb.toString();
            telNo = 0+salesSites2.get(0).getPhone();
        }
        List<SalesSites> salesSites = salesSitesMapper.selectSitesByHeaderIdAndSiteType(BILL, headerId);
        if (!salesSites.isEmpty()) {
            if(!salesSites.get(0).getName().isEmpty()){
                head.setMemberName(salesSites.get(0).getName());
            }
        }
        // PV合计
        BigDecimal totalPV = BigDecimal.ZERO;
        // 金额合计
        BigDecimal totalRm = BigDecimal.ZERO;
        // 税合计
        BigDecimal totalGST = BigDecimal.ZERO;
        int index = 0;
        List<List<SalesLine>> pageList = new ArrayList<>();
        List<SalesLine> tmpList = new ArrayList<>();
        for (SalesLine line : lines) {
            index++;
            if (line.getPv() != null) {
                totalPV = totalPV.add(line.getPv().multiply(line.getQuantity()));
            }
            if (line.getAmount() != null) {
                totalRm = totalRm.add(line.getAmount());
            }
            tmpList.add(line);
            if (index == 8) {
                List<SalesLine> list = new ArrayList<>();
                list.addAll(tmpList);
                pageList.add(list);
                tmpList.clear();
                index = 0;
            }
        }
        if (tmpList.size() > 0) {
            pageList.add(tmpList);
        }
        totalGST = totalGST.add(head.getTaxAmt());
        String payment = head.getSourceType() + totalGST.toString();
        result.put("head", head);
        result.put("shipTo", shipTo);
        result.put("period", period);
        result.put("telNo", telNo);
        debugMsg("====================head is {}======================", head.getInvoiceNumber());
        result.put("lines", pageList);
        debugMsg("====================line is {}======================", lines.size());
        debugMsg("====================totalRm is {}======================", totalRm);
        result.put("totalPV", totalPV);
        result.put("totalRm", totalRm);
        result.put("totalGST", totalGST);
        result.put("totalGSTAmount", head.getActrualPayAmt());
        result.put("paymentMethed", orderPayments);
        result.put("payment", payment);
        result.put("username", username);
        result.put("salesAdjustment", adjustment);
        result.put("salesVouchers", voucher);
        result.put("SalesLogistics", salesLogistics);

        debugMsg("====================end process in MLInvoiceDataServiceImpl======================");
        return result;
    }

    private void debugMsg(String msg, Object... args) {
        if (logger.isDebugEnabled()) {
            if (args != null && args.length > 0) {
                logger.debug(msg, args);
            } else {
                logger.debug(msg);
            }
        }
    }
}
