/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.delivery.dto.DeliveryForQuery;
import com.lkkhpg.dsis.common.delivery.dto.OrderDelivery;

/**
 * 发运单查询Mapper.
 * 
 * @author pengli
 */
public interface DeliveryMapper {

    List<OrderDelivery> queryDelivery(DeliveryForQuery delivery);

    List<Map<String, Object>> orderComboxQuery();

    List<Map<String, Object>> invComboxQuery();

    List<OrderDelivery>queryDeliveryStatus(String orderNumber);
}
