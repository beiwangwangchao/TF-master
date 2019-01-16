/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.delivery.dto.OrderDelivery;

/**
 * 发运单头接口.
 * 
 * @author liang.rao
 *
 */
public interface OrderDeliveryMapper {
    int deleteByPrimaryKey(Long deliveryId);

    int insert(OrderDelivery record);

    int insertSelective(OrderDelivery record);

    OrderDelivery selectByPrimaryKey(@Param("localel") String localel, @Param("deliveryId") Long deliveryId);

    int updateByPrimaryKeySelective(OrderDelivery record);

    int updateByPrimaryKey(OrderDelivery record);

    int updateDeliveryStatus(@Param("headerId") Long headerId, @Param("newStatus") String newStatus,
            @Param("status") List<String> status);

    Long checkDeliveryStatus(@Param("headerId") Long headerId, @Param("status") List<String> status);

    BigDecimal getShippedAmountByOrderLine(Long orderLineId);
    
    
    OrderDelivery queryByPrimaryKey(Long deliveryId);

}