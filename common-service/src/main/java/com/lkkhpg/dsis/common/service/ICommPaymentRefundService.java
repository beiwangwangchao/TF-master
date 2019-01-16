/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.PaymentRefund;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 
 * 支付退款接口.
 * 
 * @author gulin
 *
 */
public interface ICommPaymentRefundService extends ProxySelf<ICommPaymentRefundService> {
    /**
     * 根据订单头ID查询支付信息.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头ID.
     * @return 返回支付信息集合，可以为空.
     */
    List<PaymentRefund> queryPaymentByHeaderId(IRequest request, Long headerId);
    
    /**
     * 根据订单头ID判断状态查询支付或退款信息.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头ID.
     * @return 返回支付或退款信息集合，可以为空.
     */
    List<PaymentRefund> queryPayOrRefundByHeaderId(IRequest request, Long headerId);
    
    /**
     * 插入退款信息.
     * 
     * @param request
     *            统一上下文.
     * @param paymentRefund
     *            退款信息.
     * @return 返回修改行数.
     */
    int insertPaymentRefund(IRequest request, PaymentRefund paymentRefund);

    /**
     * 根据订单头ID检查是否满足失效条件.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头ID.
     * @return 返回ture 满足失效条件，反之false.
     */
    boolean isInvalid(IRequest request, Long headerId);

    /**
     * 后台校验退款信息是否合法.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头ID.
     * @param paymentRefunds
     *            退款信息集合.
     * @return 若合法，返回true，不合法，抛出异常.
     * @throws CommOrderException
     *             验证退款信息,不和法抛出异常
     */
    boolean checkRefundInvalid(IRequest request, Long headerId, List<PaymentRefund> paymentRefunds)
            throws CommOrderException;
    
    /**
     * 根据订单头ID获取退款所属市场组织参数.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头Id.
     * @return 返回所属市场组织参数信息
     */
    List<String> queryOrderSpmRefund(IRequest request, Long headerId);
}
