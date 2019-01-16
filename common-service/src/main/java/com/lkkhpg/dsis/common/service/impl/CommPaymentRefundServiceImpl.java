/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.constant.DeliveryConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.delivery.mapper.OrderDeliveryMapper;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.PaymentRefund;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.PaymentRefundMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.service.ICommPaymentRefundService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 支付退款接口实现类.
 * 
 * @author gulin
 *
 */
@Service
@Transactional
public class CommPaymentRefundServiceImpl implements ICommPaymentRefundService {

    @Autowired
    private PaymentRefundMapper paymentRefundMapper;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private OrderDeliveryMapper dmOrderDeliveryMapper;

    @Autowired
    private SpmPeriodMapper spmPeriodMapper;

    @Autowired
    private IParamService paramService;

    @Override
    public List<PaymentRefund> queryPaymentByHeaderId(IRequest iRequest, Long headerId) {
        return paymentRefundMapper.queryPaymentByHeadId(headerId);
    }

    @Override
    public int insertPaymentRefund(IRequest iRequest, PaymentRefund paymentRefund) {
        return paymentRefundMapper.insertSelective(paymentRefund);
    }

    @Override
    public boolean isInvalid(IRequest iRequest, Long headerId) {
        SalesOrder salesOrder = salesOrderMapper.selectByPrimaryKey(headerId);
        String orderStatus = salesOrder.getOrderStatus(); // compeleted
        List<String> status = new ArrayList<String>();
        status.add(DeliveryConstants.DELIVERY_STATUS_NEW);
        status.add(DeliveryConstants.DELIVERY_STATUS_PENDDING);
        status.add(DeliveryConstants.DELIVERY_STATUS_CANCLED);
        Long counts = dmOrderDeliveryMapper.checkDeliveryStatus(headerId, status);
        SpmPeriod spmPeriod = spmPeriodMapper.selectByPrimaryKey(salesOrder.getPeriodId());
        return ((OrderConstants.SALES_STATUS_COMP.equals(orderStatus)
                || OrderConstants.SALES_STATUS_CONF.equals(orderStatus)) && 0 == counts && null != spmPeriod
                && SystemProfileConstants.PEROID_CLOSE_STATUS_N.equals(spmPeriod.getClosingStatus()));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean checkRefundInvalid(IRequest iRequest, Long headerId, List<PaymentRefund> paymentRefunds)
            throws CommOrderException {
        SalesOrder salesOrder = salesOrderMapper.selectByPrimaryKey(headerId);
        BigDecimal payAmounts = salesOrder.getActrualPayAmt();
        BigDecimal refundAmounts = new BigDecimal(OrderConstants.ZERO);
        String paymentMethod = ""; // 支付方式
        String bankCode = ""; //
        String creditCardType = "";
        String paymentMethodInfo = "";
        String transactionNumber = "";
        String tailNumber = "";
        BigDecimal rowAmounts;
        for (PaymentRefund temp : paymentRefunds) {
            paymentMethod = temp.getPaymentMethod();
            bankCode = temp.getBankCode();
            creditCardType = temp.getCreditCardType();
            paymentMethodInfo = temp.getPaymentMethodInfo();
            transactionNumber = temp.getTransactionNumber();
            tailNumber = temp.getTailNumber();
            rowAmounts = temp.getPaymentAmount();
            // 验证不同方式必填字段是否为空
            if (OrderConstants.PAYMENT_METHOD_CHEQU.equals(paymentMethod)) { // 支票and汇款单
                if (StringUtils.isEmpty(transactionNumber)) {
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_REFUND_EMPTY, null);
                }
            } else if (OrderConstants.PAYMENT_METHOD_CREDI.equals(paymentMethod)) { // 信用卡-POS
                if (StringUtils.isEmpty(creditCardType) /*|| StringUtils.isEmpty(paymentMethodInfo)*/
                        || StringUtils.isEmpty(transactionNumber) || StringUtils.isEmpty(tailNumber)) {
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_REFUND_EMPTY, null);
                }
            } else if (OrderConstants.PAYMENT_METHOD_DBCRD.equals(paymentMethod)) { // 借记卡—POS
                if ((/*StringUtils.isEmpty(paymentMethodInfo) ||*/ StringUtils.isEmpty(transactionNumber)
                        || StringUtils.isEmpty(tailNumber))) {
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_REFUND_EMPTY, null);
                }
            }
            if (rowAmounts == null || BigDecimal.ZERO.compareTo(rowAmounts) >= 0) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_AMOUNT_ERROR, null);
            }
            refundAmounts = refundAmounts.add(rowAmounts);
        }
        if (payAmounts.compareTo(refundAmounts) != 0) {
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_AMOUNT_BEYOND, null);
        } else {
            return true;
        }
    }

    @Override
    public List<String> queryOrderSpmRefund(IRequest request, Long headerId) {
        SalesOrder salesOrder = salesOrderMapper.selectByPrimaryKey(headerId);
        // 获取组织参数
        return paramService.getParamValues(request, OrderConstants.ORDER_SPM_REFUND_TO_RB,
                SystemProfileConstants.ORG_TYPE_SALES, salesOrder.getSalesOrgId());
    }

    @Override
    public List<PaymentRefund> queryPayOrRefundByHeaderId(IRequest request, Long headerId) {
        SalesOrder salesOrder = salesOrderMapper.selectByPrimaryKey(headerId);
        if (salesOrder == null) {
            return new ArrayList<PaymentRefund>();
        }
        String orderStatus = salesOrder.getOrderStatus();
        List<PaymentRefund> payments = paymentRefundMapper.queryPaymentByHeadId(headerId);
        if (OrderConstants.SALES_STATUS_VOIDED.equals(orderStatus)) {
            List<PaymentRefund> refunds = paymentRefundMapper.queryRefundByHeadId(headerId);
            for (PaymentRefund temp : payments) {
                if (OrderConstants.PAY_METHOD_MODIFY_EBPAY.equals(temp.getPaymentMethod())
                        || OrderConstants.PAY_METHOD_MODIFY_RBPAY.equals(temp.getPaymentMethod())
                        || OrderConstants.PAYMENT_METHOD_ECUP.equals(temp.getPaymentMethod())) {
                    refunds.add(temp);
                }
            }
            return refunds;
        } else {
            return payments;
        }

    }

}
