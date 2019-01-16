/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.delivery.dto.OrderDeliveryLot;

/**
 * 发运单批次接口.
 * 
 * @author liang.rao
 *
 */
public interface OrderDeliveryLotMapper {
    int deleteByPrimaryKey(Long deliveryLotId);

    int insert(OrderDeliveryLot record);

    int insertSelective(OrderDeliveryLot record);

    /**
     * 获取批次行信息.
     * 
     * @param deliveryLotId
     *            批次行ID.
     * @return 批次行信息.
     */
    OrderDeliveryLot selectByPrimaryKey(Long deliveryLotId);

    int updateByPrimaryKeySelective(OrderDeliveryLot record);

    int updateByPrimaryKey(OrderDeliveryLot record);

    /**
     * 查询批次信息.
     * 
     * @param deliveryLineId
     *            订单行ID.
     * @return 批次信息结果集.
     */
    List<OrderDeliveryLot> selectByDeliveryLineId(Long deliveryLineId);

    /**
     * 查询是否启用批次.
     * 
     * @param orgId
     *            库存组织ID.
     * @param itemId
     *            商品ID.
     * @return 是否启用批次结果.
     */
    Map<String, Object> enabledLot(@Param("orgId") Long orgId, @Param("itemId") Long itemId);

    /**
     * 获取商品批次.
     * 
     * @param itemId
     *            商品Id.
     * @param orgId
     *            库存组织ID.
     * @return 商品批次结果集.
     */
    List<Map<String, Object>> getItemLots(@Param("itemId") Long itemId, @Param("orgId") Long orgId);

    /**
     * 获取商品批次及可用量.
     * 
     * @param itemId
     *            商品Id.
     * @param orgId
     *            库存组织ID.
     * @return 商品批次结果集.
     */
    List<Map<String, Object>> getItemAvailableLots(@Param("itemId") Long itemId, @Param("orgId") Long orgId);

}