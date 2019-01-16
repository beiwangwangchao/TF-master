/*
 *
 */
package com.lkkhpg.dsis.mws.service;

import java.util.Map;

import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.order.dto.OrderPayTransaction;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.integration.payment.dto.PaymentResult;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * mws订单支付跳转页面service接口.
 * 
 * @author Zhaoqi
 *
 */
public interface IOrderPaymentResultService extends ProxySelf<IOrderPaymentResultService> {

    SalesOrder paymentResult(IRequest request, Long headerId);

    SalesOrder queryOrderByOrderNumber(IRequest request, String orderNumber);

    Map<Object, Object> payingSalesOrder(IRequest request, OrderPayTransaction orderPayTransaction);

    SalesOrder payedSalesOrderWithTransaction(IRequest request, SalesOrder salesOrder)
            throws CommOrderException, CommDeliveryException, CommMemberException, CommSystemProfileException;

    /**
     * 根据支付结果，更新订单状态（更改程序逻辑时，需要同步更新DSIS程序）
     * 
     * @param request
     * @param paymentResult
     * @return SalesOrder
     * @throws CommOrderException
     * @throws CommDeliveryException
     * @throws CommMemberException
     * @throws CommSystemProfileException
     */
    SalesOrder payedSalesOrder(IRequest request, PaymentResult paymentResult)
            throws CommOrderException, CommDeliveryException, CommMemberException, CommSystemProfileException;

    SalesOrder failSalesOrder(IRequest request, PaymentResult paymentResult);

    /**
     * 主动查询银行支付结果（更改程序逻辑时，需要同步更新DSIS程序）
     * 
     * @throws CommOrderException
     * @throws CommDeliveryException
     * @throws CommMemberException
     * @throws CommSystemProfileException
     */
    /*
     * void listenPayTransaction() throws CommOrderException,
     * CommDeliveryException, CommMemberException, CommSystemProfileException;
     */
}
