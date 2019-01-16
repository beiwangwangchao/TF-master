/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.SalesRtnRefund;

/**
 * 退货单退款方式Mapper.
 * 
 * @author houmin
 *
 */
public interface SalesRtnRefundMapper {
    int deleteByPrimaryKey(Long returnRefundId);

    int insert(SalesRtnRefund record);

    int insertSelective(SalesRtnRefund record);

    SalesRtnRefund selectByPrimaryKey(Long returnRefundId);

    int updateByPrimaryKeySelective(SalesRtnRefund record);

    int updateByPrimaryKey(SalesRtnRefund record);

    int saveSalesRtnRefund(SalesRtnRefund record);

    /**
     * 根据退货单头Id删除对应退款信息.
     * 
     * @param returnId
     *            退货单头Id
     * @return 删除的记录数
     */
    int deleteByReturnId(Long returnId);
    
    /**
     * 查询同一退款方式的退款金额.
     * @param orderHeaderId 订单头ID.
     * @param refundMethod 退款方式.
     * @param returnId 排除退款ID.
     * @return 退款金额.
     */
    BigDecimal selectByReFundMethod(@Param("orderHeaderId") Long orderHeaderId,
            @Param("refundMethod") String refundMethod, @Param("returnId") Long returnId);

    /**
     * 根据退货单头ID查询退款信息.
     * 
     * @param returnId
     *            退货单头ID
     * @return 退款详情
     */
    List<SalesRtnRefund> queryByReturnId(Long returnId);

}