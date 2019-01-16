/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.platform.annotation.AuditEntry;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 
 * 订单支付管理接口.
 * 
 * @author houmin
 *
 */
public interface ICommOrderPaymentService extends ProxySelf<ICommOrderPaymentService> {

    /**
     * 记录订单支付信息.
     * 
     * @param request
     *            统一上下文
     * @param orderPayments
     *            所有的支付行信息，可为空
     * @param orderHeaderId
     *            订单头ID
     * @return 生成的发运头ID集合，为空或者Empty就得进行手动挑库动作
     * @throws CommOrderException
     *             订单支付时异常
     * @throws CommDeliveryException
     *             发运操作时异常
     * @throws CommMemberException
     *             会员操作时异常
     * @throws CommSystemProfileException
     *             系统配置异常
     */
    @AuditEntry("ORDER")
    List<Long> createOrderPayment(IRequest request, List<OrderPayment> orderPayments, Long orderHeaderId)
            throws CommOrderException, CommDeliveryException, CommMemberException, CommSystemProfileException;

    /**
     * 订单支付行信息校验.
     * 
     * @param request
     *            统一上下文
     * @param orderPayments
     *            所有订单支付行信息
     * @param salesOrder
     *            订单信息
     * @return true-校验正确;false-校验不正确
     * @throws CommOrderException
     *             订单校验时异常
     */
    boolean validateOrderPayment(IRequest request, List<OrderPayment> orderPayments, SalesOrder salesOrder)
            throws CommOrderException;

    /**
     * 订单支付行信息校验.
     * 
     * @param request
     *            统一上下文
     * @param orderPayments
     *            订单支付行信息
     * @param salesOrder
     *            订单信息
     * @return true-校验正确;false-校验不正确
     * @throws CommOrderException
     *             订单校验时异常
     */
    boolean valiOrderPaymentAfterPay(IRequest request, List<OrderPayment> orderPayments, SalesOrder salesOrder)
            throws CommOrderException;

}
