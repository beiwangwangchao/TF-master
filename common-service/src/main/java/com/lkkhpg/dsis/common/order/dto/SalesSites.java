/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 订单地址dto.
 * 
 * @author wuyichu
 */
public class SalesSites extends BaseDTO {
    private Long salesSiteId;

    @NotNull
    private Long headerId;
    @NotNull
    private Long salesOrgId;
    @NotNull
    private String siteType;
    @NotNull
    private String name;
    @NotNull
    private String phone;

    private String cityCode;

    private String stateCode;

    private String countryCode;
    @NotNull
    private String address;
    @NotNull
    private String areaCode;

    private String zipCode;

    private String autoshipFlag;

    private String address1;

    private String address2;

    private String address3;

    private String cityName;
    
    private String stateName;

    private String countryName;
    
    public Long getSalesSiteId() {
        return salesSiteId;
    }

    public void setSalesSiteId(Long salesSiteId) {
        this.salesSiteId = salesSiteId;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType == null ? null : siteType.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode == null ? null : stateCode.trim();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getAutoshipFlag() {
        return autoshipFlag;
    }

    public void setAutoshipFlag(String autoshipFlag) {
        this.autoshipFlag = autoshipFlag == null ? null : autoshipFlag.trim();
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}