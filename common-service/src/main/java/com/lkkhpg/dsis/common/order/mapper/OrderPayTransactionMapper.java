/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.order.dto.OrderPayTransaction;

/**
 * 订单支付事务处理 mapper.
 * 
 * @author runbai.chen
 */
public interface OrderPayTransactionMapper {
    int deleteByPrimaryKey(Long transactionId);

    int insert(OrderPayTransaction record);

    int insertSelective(OrderPayTransaction record);

    OrderPayTransaction selectByPrimaryKey(Long transactionId);

    int updateByPrimaryKeySelective(OrderPayTransaction record);

    int updateByPrimaryKey(OrderPayTransaction record);

    int updateTransactionByTransCode(OrderPayTransaction record);
    
    OrderPayTransaction selectTransactionByTransCode(OrderPayTransaction record);
    
    List<OrderPayTransaction> selectProcessingTransactions();
}