/*
 *
 */
package com.lkkhpg.dsis.admin.order.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.PaymentRefund;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 
 * 支付退款接口.
 * 
 * @author gulin
 *
 */
public interface IPaymentRefundService {
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
     * 根据订单头ID查询退款信息.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头ID.
     * @return 返回退款信息集合，可以为空.
     */
    List<PaymentRefund> queryPayOrRefundByHeaderId(IRequest request, Long headerId);
    
    /**
     * 插入退款信息.
     * 
     * @param request
     *            统一上下文.
     * @param paymentRefund
     *            退款信息.
     */
    void insertPaymentRefund(IRequest request, PaymentRefund paymentRefund);

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
