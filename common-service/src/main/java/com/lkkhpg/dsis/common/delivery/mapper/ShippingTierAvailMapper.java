/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.delivery.dto.ShippingTierAvail;

/**
 * 
 * @author zhenyang.he
 *
 */
public interface ShippingTierAvailMapper {

    int deleteByPrimaryKey(ShippingTierAvail record);
    
    int insert(ShippingTierAvail record);
    
    int updateByShippingTierId(ShippingTierAvail record);
    
    List<ShippingTierAvail> selectShippingTierAvails(Long ShippingTierId);
}
