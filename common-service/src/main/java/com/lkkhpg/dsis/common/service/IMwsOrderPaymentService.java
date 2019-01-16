/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.order.dto.OmMwsOrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesVoucher;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * mws订单支付Service接口.
 * 
 * @author shenqb
 */
public interface IMwsOrderPaymentService extends ProxySelf<IMwsOrderPaymentService> {

    /**
     * 保存订单支付.
     * 
     * @param request
     *            请求上下文
     * @param omMwsOrderPayments
     *            支付信息
     * @return OmMwsOrderPayments
     * @throws CommSystemProfileException
     */
    List<OmMwsOrderPayment> saveOmMwsOrderPayment(IRequest request, @StdWho List<OmMwsOrderPayment> omMwsOrderPayments)
            throws CommSystemProfileException;

    /**
     * 删除支付信息.
     * 
     * @param request
     *            请求上下文
     * @param omMwsOrderPayments
     *            支付信息List
     * @return boolean
     */
    boolean deleteOmMwsOrderPayment(IRequest request, @StdWho List<OmMwsOrderPayment> omMwsOrderPayments);

    /**
     * 根据支付信息编码获取支付信息.
     * 
     * @param request
     *            请求上下文
     * @param paymentId
     *            支付信息编码
     * @return 支付信息DTO
     */
    OmMwsOrderPayment getOmMwsOrderPayment(IRequest request, Long paymentId);

    /**
     * 按条件获取指定支付记录
     * 
     * @param request
     * @param mwsOrderPayment
     * @return 支付信息LIST
     */
    List<OmMwsOrderPayment> getMwsOrderPayments(IRequest request, OmMwsOrderPayment mwsOrderPayment);
    
    /**
     * 查询订单使用的优惠券集合.
     * 
     * @param headerId
     *            订单头
     * @return 订单使用的优惠券
     */
    List<SalesVoucher> getVouchersByOrderId(Long headerId);
    
    /**
     * 根据订单头id获取运费.
     * @param headerId 订单头id
     * @return 配送信息
     */
    SalesLogistics queryFreightByHeaderId(Long headerId);
    
    /**
     * 订单提交时remainingBalance值修改.
     * @param request 统一上下文
     * @return 影响条数
     */
    OmMwsOrderPayment queryRemainingBalSum(IRequest request);
    
    /**
     * 取消订单.
     * @param request 统一上下文
     * @param omMwsOrderPayment 
     * @return 是否成功
     */
    boolean updateRemainingBalValue(IRequest request, OmMwsOrderPayment omMwsOrderPayment);
}
