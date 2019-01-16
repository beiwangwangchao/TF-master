/*
 *
 */
package com.lkkhpg.dsis.integration.payment.mapper;

import com.lkkhpg.dsis.integration.payment.dto.PaymentTransactionModel;

import java.util.List;

/**
 * 
 * 支付事务.
 * 
 * @author shiliyan
 *
 */
public interface PaymentTransactionMapper {

    int insert(PaymentTransactionModel model);

}