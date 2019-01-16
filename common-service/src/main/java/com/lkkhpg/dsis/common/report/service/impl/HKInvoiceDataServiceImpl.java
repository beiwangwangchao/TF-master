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

import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesAdjustment;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesVoucher;
import com.lkkhpg.dsis.common.order.mapper.OrderPaymentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesAdjustmentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLogisticsMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesVoucherMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * 香港发票模板.
 * 
 * @author hanrui.huang
 *
 */
@Service("hkInvoiceDataService")
public class HKInvoiceDataServiceImpl implements IReportDataService {

    private static Logger logger = LoggerFactory.getLogger(HKInvoiceDataServiceImpl.class);

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SalesLineMapper salesLineMapper;

    @Autowired
    private OrderPaymentMapper orderPaymentMapper;

    @Autowired
    private SalesAdjustmentMapper salesAdjustmentMapper;

    @Autowired
    private SalesVoucherMapper salesVoucherMapper;

    @Autowired
    private SalesLogisticsMapper salesLogisticsMapper;

    @Autowired
    private ICodeService codeService;

    @Override
    public Map<String, Object> process(IRequest request, Map<String, Object> paramMap) {
        debugMsg("====================start process in HKInvoiceDataServiceImpl======================");
        Map<String, Object> result = new HashMap<>();
        String headerIdStr = (String) paramMap.get("headerId");
        if (StringUtils.isEmpty(headerIdStr)) {
            return result;
        }
        // 订单ID
        Long headerId = Long.parseLong(headerIdStr);
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
        // 销售支付调整
        List<SalesAdjustment> salesAdjustments = salesAdjustmentMapper.selectByHeaderId(headerId);
        // 获取折扣
        List<SalesVoucher> salesVouchers = salesVoucherMapper.getVouchersByOrderId(headerId);
        BigDecimal voucher = BigDecimal.ZERO;
        for (SalesVoucher salesVoucher : salesVouchers) {
            voucher = voucher.add(salesVoucher.getDiscountValue().negate());
        }
        // 获取运费
        SalesLogistics salesLogistics = salesLogisticsMapper.selectByHeaderId(headerId, "N");
        // PV合计
        BigDecimal totalPV = BigDecimal.ZERO;
        int index = 0;
        List<List<SalesLine>> pageList = new ArrayList<>();
        List<SalesLine> tmpList = new ArrayList<>();
        for (SalesLine line : lines) {
            index++;
            // 小计积分
            if (line.getPv() != null) {
                line.setTotalPv(line.getPv().multiply(new BigDecimal(line.getQuantity().toString())));
                totalPV = totalPV.add(line.getTotalPv());
            }
            tmpList.add(line);
            if (index == 16) {
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
        // 计算销售调整金额
        BigDecimal totalSalesAdjustmentAmt = BigDecimal.ZERO;
        for (SalesAdjustment salesAdjustment : salesAdjustments) {
            if (salesAdjustment.getAdjustmentAmount() != null) {
                totalSalesAdjustmentAmt = totalSalesAdjustmentAmt.add(salesAdjustment.getAdjustmentAmount());
            }
        }
        result.put("head", head);
        debugMsg("====================invoiceNumber is {}======================", head.getInvoiceNumber());
        result.put("lines", pageList);
        debugMsg("====================line is {}======================", lines.size());
        result.put("paymentMethed", orderPayments);
        // 订单金额
        result.put("orderAmount", head.getOrderAmt());
        result.put("totalPV", totalPV);
        result.put("salesAdjustment", totalSalesAdjustmentAmt);
        result.put("salesVouchers", voucher);
        BigDecimal shippingFee = BigDecimal.ZERO;
        if (salesLogistics != null) {
            shippingFee.add(salesLogistics.getShippingFee());
        }
        result.put("SalesLogistics", salesLogistics);
        // result.put("SalesLogistics", shippingFee);
        // Total Amount 所有订单行上AMOUNT的和-优惠券所有行的优惠总额±人工调整金额
        result.put("totalAmount", head.getActrualPayAmt());
        debugMsg("====================end process in HKInvoiceDataServiceImpl======================");
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
