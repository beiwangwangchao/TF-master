/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 承运商分配.
 * 
 * @author RUNBAI.CHEN
 */
public class ShippingTierSeg extends BaseDTO {
    private static final long serialVersionUID = 1547247110983091414L;
    private Long tierSegId;
    private Long shippingTierId;

    private String countryCode;

    private String stateCode;

    private String cityCode;

    private String countryName;

    private String stateName;

    private String cityName;
    
    public Long getTierSegId() {
        return tierSegId;
    }

    public void setTierSegId(Long tierSegId) {
        this.tierSegId = tierSegId;
    }

    public Long getShippingTierId() {
        return shippingTierId;
    }

    public void setShippingTierId(Long shippingTierId) {
        this.shippingTierId = shippingTierId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateCode() {
          return stateCode;
    }

    public void setStateCode(String stateCode) {
         this.stateCode = stateCode;
    }

    public String getCityCode() {
          return cityCode;
    }

    public void setCityCode(String cityCode) {
          this.cityCode = cityCode;
    }

    public String getCountryName() {
         return countryName;
    }

    public void setCountryName(String countryName) {
         this.countryName = countryName;
    }

    public String getStateName() {
          return stateName;
    }

    public void setStateName(String stateName) {
          this.stateName = stateName;
    }

    public String getCityName() {
         return cityName;
    }

    public void setCityName(String cityName) {
         this.cityName = cityName;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    





}