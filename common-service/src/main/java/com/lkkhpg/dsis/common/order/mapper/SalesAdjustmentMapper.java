/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.lkkhpg.dsis.common.order.dto.SalesAdjustment;

/**
 * 支付调整的mapper.
 * 
 * @author wuyichu
 */
public interface SalesAdjustmentMapper {
    int deleteByPrimaryKey(Long salesAdjustmentId);

    int insert(SalesAdjustment salesAdjustment);

    int insertSelective(SalesAdjustment salesAdjustment);

    SalesAdjustment selectByPrimaryKey(Long salesAdjustmentId);

    int updateByPrimaryKeySelective(SalesAdjustment salesAdjustment);

    int updateByPrimaryKey(SalesAdjustment salesAdjustment);

    /**
     * 根据订单头删除.
     * 
     * @param headerId
     *            订单头id
     * @return 删除的行数
     */
    int deleteByHeaderId(Long headerId);

    /**
     * 根据订单头查询.
     * 
     * @param headerId
     *            订单头id
     * @return 支付调整集合
     */
    List<SalesAdjustment> selectByHeaderId(Long headerId);

    /**
     * 根据订单头计算订单调整总额.
     * 
     * @param headerId
     *            订单头id
     * @return 支付调整的汇总金额
     */
    BigDecimal sumAmountByHeaderId(Long headerId);
}