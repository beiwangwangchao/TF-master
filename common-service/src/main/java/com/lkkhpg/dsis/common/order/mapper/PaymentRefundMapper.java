/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.order.dto.PaymentRefund;

/**
 * 退款接口.
 * 
 * @author gulin
 *
 */
public interface PaymentRefundMapper {
    int deleteByPrimaryKey(Short refundId);

    int insert(PaymentRefund record);

    int insertSelective(PaymentRefund record);

    PaymentRefund selectByPrimaryKey(Short refundId);

    int updateByPrimaryKeySelective(PaymentRefund record);

    int updateByPrimaryKey(PaymentRefund record);

    // 根据订单头ID查询支付消息，便于回退
    List<PaymentRefund> queryPaymentByHeadId(Long headerId);
    
    List<PaymentRefund> queryRefundByHeadId(Long headerId);
}