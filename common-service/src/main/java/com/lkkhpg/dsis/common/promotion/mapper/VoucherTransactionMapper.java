/*
 *
 */
package com.lkkhpg.dsis.common.promotion.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.promotion.dto.VoucherTransaction;

/**
 * 优惠券事务Mapper.
 * 
 * @author houmin
 *
 */
public interface VoucherTransactionMapper {
    int deleteByPrimaryKey(Long transactionId);

    int insert(VoucherTransaction record);

    int insertSelective(VoucherTransaction record);

    VoucherTransaction selectByPrimaryKey(Long transactionId);

    int updateByPrimaryKeySelective(VoucherTransaction record);

    int updateByPrimaryKey(VoucherTransaction record);
    

    List<VoucherTransaction>  queryBirthVoucherTransaction(VoucherTransaction voucherTransaction);
}