/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.PaymentConstants;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.order.dto.OmMwsOrderPayment;
import com.lkkhpg.dsis.common.order.dto.OrderPayTransaction;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.OrderPayTransactionMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.service.ICommOrderPaymentService;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.common.service.IMwsOrderPaymentService;
import com.lkkhpg.dsis.common.service.IOrderPayTransactionService;
import com.lkkhpg.dsis.integration.payment.dto.PaymentResult;
import com.lkkhpg.dsis.integration.payment.service.impl.PaymentComponent;
import com.lkkhpg.dsis.mws.service.IOrderPaymentResultService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * mws订单支付跳转页面service接口实现.
 * 
 * @author Zhaoqi
 *
 */
@Service
public class OrderPaymentResultServiceImpl implements IOrderPaymentResultService {

    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private IOrderPayTransactionService orderPayTranactionService;
    @Autowired
    private ICommSalesOrderService salesOrderService;
    @Autowired
    private OrderPayTransactionMapper orderPayTransactionMapper;
    @Autowired
    private ICommOrderPaymentService orderPaymentService;

    @Autowired
    private IMwsOrderPaymentService mwsOrderPaymentService;

    /**
     * 实际支付金额，从MWS支付临时表获取。类型：ONLPA(在线支付类型)
     * 
     * @param request
     * @param salesOrder
     * @return
     */
    private SalesOrder getOrderActualAmt(IRequest request, SalesOrder salesOrder) {
        // 实际支付金额，从MWS支付临时表获取。类型：ONLPA(在线支付类型)
        OmMwsOrderPayment mwsOrderPayment = new OmMwsOrderPayment();
        mwsOrderPayment.setOrderHeaderId(salesOrder.getHeaderId());
        mwsOrderPayment.setPaymentMethod(PaymentConstants.PAYMENT_METHOD_ONLPA);
        List<OmMwsOrderPayment> mwsOrderPaymentList = mwsOrderPaymentService.getMwsOrderPayments(request,
                mwsOrderPayment);
        if (mwsOrderPaymentList == null || mwsOrderPaymentList.isEmpty()) {
            salesOrder.setActrualPayAmt(BigDecimal.ZERO);
            //

        } else {
            salesOrder.setActrualPayAmt(mwsOrderPaymentList.get(0).getPaymentAmount());
        }
        return salesOrder;
    }

    private List<OrderPayment> changeMwsPaymentToDsisPayment(List<OmMwsOrderPayment> mwsOrderPaymentList) {
        List<OrderPayment> orderPayments = new ArrayList<OrderPayment>();

        for (OmMwsOrderPayment mwsOrderPayment : mwsOrderPaymentList) {
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setOrderHeaderId(mwsOrderPayment.getOrderHeaderId());
            orderPayment.setSalesOrgId(mwsOrderPayment.getSalesOrgId());
            orderPayment.setPaymentMethod(mwsOrderPayment.getPaymentMethod());
            orderPayment.setPaymentMethodInfo(mwsOrderPayment.getPaymentMethodInfo());
            orderPayment.setPaymentAmount(mwsOrderPayment.getPaymentAmount());
            orderPayment.setTransactionNumber(mwsOrderPayment.getTransactionNumber());
            orderPayment.setBankCode(mwsOrderPayment.getBankCode());
            orderPayment.setCreditCardType(mwsOrderPayment.getCreditCardType());
            orderPayment.setTailNumber(mwsOrderPayment.getTailNumber());
            orderPayment.setRemark(mwsOrderPayment.getRemark());
            orderPayment.setObjectVersionNumber(mwsOrderPayment.getObjectVersionNumber());
            orderPayment.setRequestId(mwsOrderPayment.getRequestId());
            orderPayment.setProgramId(mwsOrderPayment.getProgramId());
            orderPayment.setCreationDate(mwsOrderPayment.getCreationDate());
            orderPayment.setCreatedBy(mwsOrderPayment.getCreatedBy());
            orderPayment.setLastUpdatedBy(mwsOrderPayment.getLastUpdatedBy());
            orderPayment.setLastUpdateDate(mwsOrderPayment.getLastUpdateDate());
            orderPayment.setLastUpdateLogin(mwsOrderPayment.getLastUpdateLogin());
            orderPayment.setAttributeCategory(mwsOrderPayment.getAttributeCategory());
            orderPayment.setAttribute1(mwsOrderPayment.getAttribute1());
            orderPayment.setAttribute2(mwsOrderPayment.getAttribute2());
            orderPayment.setAttribute3(mwsOrderPayment.getAttribute3());
            orderPayment.setAttribute4(mwsOrderPayment.getAttribute4());
            orderPayment.setAttribute5(mwsOrderPayment.getAttribute5());
            orderPayment.setAttribute6(mwsOrderPayment.getAttribute6());
            orderPayment.setAttribute7(mwsOrderPayment.getAttribute7());
            orderPayment.setAttribute8(mwsOrderPayment.getAttribute8());
            orderPayment.setAttribute9(mwsOrderPayment.getAttribute9());
            orderPayment.setAttribute10(mwsOrderPayment.getAttribute10());
            orderPayment.setAttribute11(mwsOrderPayment.getAttribute11());
            orderPayment.setAttribute12(mwsOrderPayment.getAttribute12());
            orderPayment.setAttribute13(mwsOrderPayment.getAttribute13());
            orderPayment.setAttribute14(mwsOrderPayment.getAttribute14());
            orderPayment.setAttribute15(mwsOrderPayment.getAttribute15());
            orderPayment.setChequeNumber(mwsOrderPayment.getChequeNumber());
            orderPayment.setVoidTrxReference(mwsOrderPayment.getVoidTrxReference());
            orderPayment.setVoidFailReason(mwsOrderPayment.getVoidFailReason());
            orderPayment.setStatus(mwsOrderPayment.getStatus());
            orderPayment.setFailReason(mwsOrderPayment.getFailReason());
            orderPayment.setVoucherId(mwsOrderPayment.getVoucherId());
            orderPayments.add(orderPayment);

        }

        return orderPayments;

    }

    @Override
    public SalesOrder paymentResult(IRequest request, Long headerId) {
        SalesOrder salesOrder = salesOrderMapper.selectByPrimaryKeyOnly(headerId);
        salesOrder = getOrderActualAmt(request, salesOrder);
        return salesOrder;
    }

    @Override
    public SalesOrder queryOrderByOrderNumber(IRequest request, String orderNumber) {
        SalesOrder salesOrder = salesOrderMapper.queryByOrderNumber(orderNumber);
        return salesOrder;
    }

    @Override
    public Map<Object, Object> payingSalesOrder(IRequest request, OrderPayTransaction orderPayTransaction) {
        HashMap<Object, Object> result = new HashMap<Object, Object>();
        // 更新订单状态为“付款中”
        orderPayTranactionService.lauchTransaction(request, orderPayTransaction);
        salesOrderService.updateOrderStatus(request, orderPayTransaction.getSourceKey(),
                PaymentConstants.ORDER_STATUS_PAYIN);
        result.put(PaymentConstants.PROCESS_RESULT_SUCCESS, true);
        return result;
    }

    @Override
    public SalesOrder payedSalesOrderWithTransaction(IRequest request, SalesOrder salesOrder)
            throws CommOrderException, CommDeliveryException, CommMemberException, CommSystemProfileException {

        // 保存到MWS支付表
        OmMwsOrderPayment mwsOrderPayment = new OmMwsOrderPayment();
        List<OrderPayment> orderPayments = new ArrayList<OrderPayment>();
        mwsOrderPayment.setOrderHeaderId(salesOrder.getHeaderId());
        List<OmMwsOrderPayment> mwsOrderPaymentList = mwsOrderPaymentService.getMwsOrderPayments(request,
                mwsOrderPayment);

        // mws payment to dsis payment
        orderPayments = changeMwsPaymentToDsisPayment(mwsOrderPaymentList);

        orderPaymentService.createOrderPayment(request, orderPayments, salesOrder.getHeaderId());
        SalesOrder result = salesOrderMapper.selectByPrimaryKeyOnly(salesOrder.getHeaderId());
        result = getOrderActualAmt(request, result);
        return result;

    }

    @Override
    public SalesOrder payedSalesOrder(IRequest request, PaymentResult paymentResult)
            throws CommOrderException, CommDeliveryException, CommMemberException, CommSystemProfileException {

        OrderPayTransaction orderPayTransaction = new OrderPayTransaction();
        orderPayTransaction.setTransactionCode(paymentResult.getLidm());
        orderPayTransaction.setTransactionType(PaymentConstants.TRANS_TYPE_PAY);
        orderPayTransaction = orderPayTransactionMapper.selectTransactionByTransCode(orderPayTransaction);
        SalesOrder result = salesOrderMapper.selectByPrimaryKeyOnly(orderPayTransaction.getSourceKey());

        if (!StringUtils.isEmpty(paymentResult.getStatus())) {
            if (paymentResult.getStatus().equals(PaymentComponent.SUCCESS)) {
                // salesOrderService.updateOrderStatus(request,
                // result.getHeaderId(), OrderConstants.SALES_STATUS_COMP);
                orderPayTransaction.setAttribute1(paymentResult.getXid());
                orderPayTransaction.setAttribute2(paymentResult.getAuthCode());
                orderPayTransaction.setAttribute3(paymentResult.getExpiry());
                orderPayTransaction.setAttribute4(paymentResult.getLast4digitPAN());
                orderPayTransaction.setTransactionStatus(PaymentConstants.TRANS_STATUS_COMPL);
                orderPayTranactionService.finishTransaction(request, orderPayTransaction);

                // 保存到MWS支付表
                OmMwsOrderPayment mwsOrderPayment = new OmMwsOrderPayment();
                mwsOrderPayment.setOrderHeaderId(result.getHeaderId());
                mwsOrderPayment.setPaymentMethod(PaymentConstants.PAYMENT_METHOD_ONLPA);
                List<OmMwsOrderPayment> mwsOrderPaymentList = mwsOrderPaymentService.getMwsOrderPayments(request,
                        mwsOrderPayment);
                mwsOrderPaymentList.get(0).setRemark(orderPayTransaction.getTransactionCode());
                mwsOrderPaymentList.get(0).setTailNumber(orderPayTransaction.getAttribute4());
                mwsOrderPaymentList.get(0).setTransactionNumber(orderPayTransaction.getAttribute1());
                mwsOrderPaymentList.get(0).setAttribute1(orderPayTransaction.getAttribute2());
                mwsOrderPaymentList.get(0).setAttribute2("");
                mwsOrderPaymentList.get(0).setAttribute3(orderPayTransaction.getAttribute3());
                mwsOrderPaymentService.saveOmMwsOrderPayment(request, mwsOrderPaymentList);

                List<OrderPayment> orderPayments = new ArrayList<OrderPayment>();
                mwsOrderPayment.setPaymentMethod(null);
                mwsOrderPaymentList = mwsOrderPaymentService.getMwsOrderPayments(request, mwsOrderPayment);

                // mws payment to dsis payment
                orderPayments = changeMwsPaymentToDsisPayment(mwsOrderPaymentList);

                orderPaymentService.createOrderPayment(request, orderPayments, result.getHeaderId());
            } else if (paymentResult.getStatus().equals(PaymentComponent.PAYING)) {
                orderPayTransaction.setTransactionStatus(PaymentConstants.TRANS_STATUS_PROCE);
                orderPayTranactionService.finishTransaction(request, orderPayTransaction);
            } else {
                salesOrderService.updateOrderStatusWithFormerStatus(request, result.getHeaderId(),
                        OrderConstants.WAIT_PAT, OrderConstants.SALES_STATUS_PAYIN_PAYMENT);
                orderPayTransaction.setTransactionStatus(PaymentConstants.TRANS_STATUS_FAIL);
                orderPayTranactionService.finishTransaction(request, orderPayTransaction);
            }
        }

        result = getOrderActualAmt(request, result);
        return result;
    }

    @Override
    public SalesOrder failSalesOrder(IRequest request, PaymentResult paymentResult) {
        OrderPayTransaction orderPayTransaction = new OrderPayTransaction();
        orderPayTransaction.setTransactionCode(paymentResult.getLidm());
        orderPayTransaction.setTransactionType(PaymentConstants.TRANS_TYPE_PAY);
        orderPayTransaction = orderPayTransactionMapper.selectTransactionByTransCode(orderPayTransaction);
        SalesOrder result = salesOrderMapper.selectByPrimaryKeyOnly(orderPayTransaction.getSourceKey());
        salesOrderService.updateOrderStatusWithFormerStatus(request, result.getHeaderId(), OrderConstants.WAIT_PAT,
                OrderConstants.SALES_STATUS_PAYIN_PAYMENT);
        orderPayTransaction.setTransactionStatus(PaymentConstants.TRANS_STATUS_FAIL);
        orderPayTranactionService.finishTransaction(request, orderPayTransaction);

        result = getOrderActualAmt(request, result);
        return result;
    }

    // @Override
    // public void listenPayTransaction()
    // throws CommOrderException, CommDeliveryException, CommMemberException,
    // CommSystemProfileException {
    // IRequest request = new MwsServiceRequest();
    // // 查处需要监听数据
    // List<OrderPayTransaction> payTransactions =
    // orderPayTransactionMapper.selectProcessingTransactions();
    // for (OrderPayTransaction payTransaction : payTransactions) {
    // String paymentType = new String();
    // // 类型转换
    // switch (payTransaction.getBankPaymentCode()) {
    // case PaymentConstants.PAYMENT_TYPE_MYHOL:
    // paymentType = PaymentComponent.MIGS_QUERY;
    // break;
    // case PaymentConstants.PAYMENT_TYPE_TWNON:
    // paymentType = PaymentComponent.NON_UNION_QUERY;
    // break;
    // case PaymentConstants.PAYMENT_TYPE_TWUNN:
    // paymentType = PaymentComponent.UNION_QUERY;
    // break;
    // }
    //
    // PaymentOrder paymentOrder = new PaymentOrder();
    // // 获取订单信息
    // SalesOrder salesOrder =
    // salesOrderMapper.selectByPrimaryKeyOnly(payTransaction.getSourceKey());
    // salesOrder = getOrderActualAmt(request, salesOrder);
    // // paymentOrder bug
    // paymentOrder.setAmount(salesOrder.getActrualPayAmt().toString());
    // paymentOrder.setKey(payTransaction.getSourceKey());
    // paymentOrder.setOrderNumber(payTransaction.getTransactionCode());
    // paymentOrder.setSourceType(PaymentConstants.SOURCE_TYPE_ORDER);
    // // 获取查询结果
    // PaymentResult paymentResult = orderPaymentQueryService.query(paymentType,
    // paymentOrder);
    //
    // self().payedSalesOrder(request, paymentResult);
    //
    // }
    // }
}
