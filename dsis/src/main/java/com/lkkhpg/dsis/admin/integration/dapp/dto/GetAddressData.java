/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 送货地址同步接口数据DTO.
 * 
 * @author fengwanjun
 */

public class GetAddressData {
    
    private String countryCode;
    
    private String countryName;

    private String stateCode;
    
    private String stateName;

    private String cityCode;
    
    private String cityName;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}