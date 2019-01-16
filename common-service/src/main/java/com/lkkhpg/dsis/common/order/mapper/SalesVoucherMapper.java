/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.SalesVoucher;

/**
 * 销售促销Mapper.
 * 
 * @author houmin
 *
 */
public interface SalesVoucherMapper {
    int deleteByPrimaryKey(Long salesVoucherId);
    
    int deleteByOrderId(Long  headerId);

    int insert(SalesVoucher record);

    int insertSelective(SalesVoucher record);

    SalesVoucher selectByPrimaryKey(Long salesVoucherId);

    int updateByPrimaryKeySelective(SalesVoucher record);

    int updateByPrimaryKey(SalesVoucher record);

    /**
     * 查询订单使用的优惠券集合.
     * 
     * @param headId
     *            订单头
     * @return 订单使用的优惠券
     */
    List<SalesVoucher> getVouchersByOrderId(@Param("headerId") Long headerId);
}