/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.mapper;

import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickHead;

/**
 * 挑库单头接口.
 * 
 * @author Zhaoqi
 *
 */
public interface DeliveryPickHeadMapper {

    /**
     * 挑库订单头表insert.
     * 
     * @param deliveryPickHead
     *            挑库头dto.
     * @return int影响行数.
     */
    int insertPick(DeliveryPickHead deliveryPickHead);

}