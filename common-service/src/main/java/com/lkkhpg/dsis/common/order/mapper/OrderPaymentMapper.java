/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.OrderPayment;

/**
 * 订单支付接口.
 * 
 * @author houmin
 */
public interface OrderPaymentMapper {
    int deleteByPrimaryKey(Long paymentId);

    int insert(OrderPayment record);

    int insertSelective(OrderPayment record);

    OrderPayment selectByPrimaryKey(Long paymentId);

    int updateByPrimaryKeySelective(OrderPayment record);

    int updateByPrimaryKey(OrderPayment record);

    /**
     * 根据订单头Id查询会员销售积分.
     * 
     * @param orderHeaderId
     *            订单头ID
     * @return 会员拥有的销售积分
     */
    BigDecimal selectMemSalesPoint(@Param("orderHeaderId") Long orderHeaderId);

    /**
     * 根据订单头ID查询对应订单类型.
     * 
     * @param orderHeaderId
     *            订单头ID
     * @return 订单类型
     */
    String selectOrderTypeByHeaderId(@Param("orderHeaderId") Long orderHeaderId);

    /**
     * 根据订单头Id查询积分兑换时实际所需支付的积分.
     * 
     * @param orderHeaderId
     *            订单头ID
     * @return 实际支付总积分
     */
    Long selectRedeemPointCount(@Param("orderHeaderId") Long orderHeaderId);

    /**
     * 根據訂單頭表的数据插入到订单支付表中.
     * 
     * @param record
     *            数据集合
     * @return 相应数据
     */
    int insertBySalesOrder(OrderPayment record);

    /**
     * 根据订单头ID汇总每种支付方式的支付金额.
     * 
     * @param headerId
     *            销售订单头ID
     * @return 订单支付对象集合(包含：支付方式和对应支付方式金额)
     */
    List<OrderPayment> selectPayMethodAmtByHeaderId(Long headerId);

    /**
     * 根据订单头ID查询银行卡号后四位.
     * 
     * @param headerId
     *            销售订单头ID
     * @return 订单支付对象集合(包含：支付方式和对应支付方式金额)
     */
    OrderPayment queryPaymentAtm(Long headerId);

    /**
     * 根据优惠券ID查询.
     * 
     * @param voucherId
     *            优惠券ID
     * @return 订单支付对象
     */
    OrderPayment selectByVoucherId(Long voucherId);

    /**
     * 根据订单头ID查找所有对应的支付对象.
     * 
     * @param headerId
     *            销售订单头ID
     * @return 订单支付对象集合
     */
    List<OrderPayment> selectByHeaderId(Long headerId);

    /**
     * 簽帳卡號末四碼.
     * 
     * @param headerId
     *            销售订单头ID
     * @return 订单支付对象集合
     */
    List<OrderPayment> queryTailnumber(Long headerId);
}