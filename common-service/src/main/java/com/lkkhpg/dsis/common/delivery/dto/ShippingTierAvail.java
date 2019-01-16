/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 承運商可用性.
 * 
 * @author zhenyang.he
 *
 */
public class ShippingTierAvail extends BaseDTO{

    private Long availabilityId;

    private Long shippingTierId;
    
    private String functionArea;
    
    private String enabledFlag;

    
    
    public Long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    public Long getShippingTierId() {
        return shippingTierId;
    }

    public void setShippingTierId(Long shippingTierId) {
        this.shippingTierId = shippingTierId;
    }

    public String getFunctionArea() {
        return functionArea;
    }

    public void setFunctionArea(String functionArea) {
        this.functionArea = functionArea;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }
    
    
}
