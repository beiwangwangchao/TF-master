/*
 *
 */
package com.lkkhpg.dsis.admin.order.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.MemAttribute;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.order.dto.CommodityList;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 
 * 订单支付管理接口.
 * 
 * @author houmin
 *
 */
public interface IOrderPaymentService {

    /**
     * 根据订单编号查询订单信息.
     * 
     * @param request
     *            统一上下文
     * @param orderHeaderId
     * @return 对应订单的商品清单信息
     */
    List<CommodityList> selectByOrderHeaderId(IRequest request, Long orderHeaderId);

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
     * @throws CommSystemProfileException
     * @throws CommMemberException
     * @throws CommDeliveryException
     * @throws CommOrderException
     */
    List<Long> createOrderPayment(IRequest request, List<OrderPayment> orderPayments, Long orderHeaderId)
            throws CommOrderException, CommDeliveryException, CommMemberException, CommSystemProfileException;

    /**
     * 根据订单头id获取可用ecupon.
     * 
     * @param request
     *            统一上下文.
     * @param orderHeaderId
     *            订单头id.
     * @return 返回符合条件的list集合.
     */
    List<Voucher> queryEcupon(IRequest request, Long orderHeaderId);

    /**
     * 根据会员id验证银行口令.
     * 
     * @param request
     *            统一上下文.
     * @param attribute
     *            会员id.
     * @return 验证结果.
     */
    List<String> checkPassword(IRequest request, MemAttribute attribute);

    /**
     * 获取当前会员完整的银行卡信息（解密卡号）.
     * 
     * @param request
     *            统一上下文.
     * @param memberId
     *            会员id.
     * @return 返回结果.
     */
    List<MemCard> queryBankInfo(IRequest request, Long memberId);

    /**
     * 已完成订单更新支付信息.
     * 
     * @param request
     *            统一上下文
     * @param headerId
     *            订单头ID
     * @param orderPayments
     *            订单支付信息
     * @return true-更新成功；false-更新失败
     * @throws CommOrderException
     *             订单统一异常
     */
    boolean updatePaymentAfterPay(IRequest request, Long headerId, List<OrderPayment> orderPayments)
            throws CommOrderException;

}
