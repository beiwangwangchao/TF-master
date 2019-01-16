/*
 *
 */

package com.lkkhpg.dsis.common.inventory.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.dto.ItemAlert;

/**
 * 库存预警Mapper.
 * 
 * @author liang.rao
 *
 */
public interface AlertJobMapper {
    
    List<ItemAlert> queryLotAlert();
    
    List<ItemAlert> queryQuantityAlert();
}
