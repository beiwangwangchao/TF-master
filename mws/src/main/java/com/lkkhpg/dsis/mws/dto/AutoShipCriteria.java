/*
 *
 */
package com.lkkhpg.dsis.mws.dto;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 自动订货商品查询条件dto.
 * @author gulin
 *
 */
public class AutoShipCriteria extends BaseDTO{
    private List<Long> itemIds;
    
    private String itemName;

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
}
