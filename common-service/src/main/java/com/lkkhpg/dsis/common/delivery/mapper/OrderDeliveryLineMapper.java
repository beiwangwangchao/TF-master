/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.mapper;

import com.lkkhpg.dsis.common.delivery.dto.OrderDeliveryLine;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 发运单行接口.
 * 
 * @author liang.rao
 *
 */
public interface OrderDeliveryLineMapper {

    /**
     * 根据OrderID获取发运数量.
     * 
     * @param orderId
     *            订单ID.
     * @return 发运数量outstandingQty.
     */
    int selectByOrderId(Long orderId);
    
    /**
     * 根据行ID删除信息.
     * 
     * @param lineId
     *            订单行ID.
     * @return 大于0表示删除成功.
     */
    int deleteByPrimaryKey(Long lineId);

    /**
     * 新增行记录.
     * 
     * @param record
     *            行信息.
     * @return 大于0表示插入成功.
     */
    int insert(OrderDeliveryLine record);

    int insertSelective(OrderDeliveryLine record);

    /**
     * 查询订单行信息.
     * 
     * @param lineId
     *            订单行ID.
     * @return 订单行信息.
     */
    OrderDeliveryLine selectByPrimaryKey(Long lineId);

    int updateByPrimaryKeySelective(OrderDeliveryLine record);

    int updateByPrimaryKey(OrderDeliveryLine record);

    /**
     * 查询所有订单行.
     * 
     * @param deliveryId
     *            发运单头ID.
     * @return 订单行集合.
     */
    List<OrderDeliveryLine> selectByDeleveryIdAndOrderHeaderId(@Param("deliveryId") Long deliveryId,@Param("orderHeaderId") Long orderHeaderId);

    /**
     * 查询所有订单行.
     *
     * @param deliveryId
     *            发运单头ID.
     * @return 订单行集合.
     */
    List<OrderDeliveryLine> selectByDeleveryId(Long deliveryId);

    /**
     * 根据销售订单行ID查询以发运总数量.
     * 
     * @param orderLineId
     *            销售订单ID
     * @return 对应订单行的以发运总数量
     */
    BigDecimal selectQtySumByOrderLineId(Long orderLineId);

    /**
     * dapp-根据订单行id,发运类型获取已发运的总数量.
     * 
     * @param map
     *            销售订单行ID,发运类型
     * @return 对应订单行的物流配送总数量
     */
    BigDecimal getQtySumByOrderLineIdForDapp(Map<String, Object> map);
    
    /**
     * 根据订单行id获取已发运的商品包总数量.
     * 
     * @param map
     *            入参
     * @return 对应订单行的物流配送总数量
     */
    BigDecimal getDeliveryQtyForPackg(Map<String, Object> map);
    
}