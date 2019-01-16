/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.util.List;
/**
 * 供货关系DTO,供前端传值使用.
 * 
 * @author wangc
 */
public class SpmSupplies {
    
    private String supplyType;

    private List<SpmSupply> supplies;
    
    public List<SpmSupply> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<SpmSupply> supplies) {
        this.supplies = supplies;
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(String supplyType) {
        this.supplyType = supplyType;
    }

}
