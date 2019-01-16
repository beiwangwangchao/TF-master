/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 地址DTO.
 *
 * @author frank.li
 */

/**
 * 地点Dto.
 * 
 * @author chenjingxiong
 */
@AuditEnabled
@Table(name = "spm_location")
public class SpmLocation extends BaseDTO {

    @Id
    @Column(name = "location_id")
    private Long locationId;

    private String cityCode;

    private String cityName;

    private String stateCode;

    private String stateName;

    @NotEmpty
    private String countryCode;

    private String countryName;

    private String address;

    /**
     * 退货-地址.
     */
    private String addressReturn;

    private String locationCode;

    private String name;

    private String description;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String addressLine4;

    private String addressLine5;

    private String zipCode;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode == null ? null : locationCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1 == null ? null : addressLine1.trim();
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2 == null ? null : addressLine2.trim();
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3 == null ? null : addressLine3.trim();
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4 == null ? null : addressLine4.trim();
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public void setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5 == null ? null : addressLine5.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
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

    public String getAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(countryName).append(stateName).append(cityName).append(addressLine1)
                .append(addressLine2 == null ? "" : addressLine2).append(addressLine3 == null ? "" : addressLine3);
        return sb.toString();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressReturn() {
        return addressReturn;
    }

    public void setAddressReturn(String addressReturn) {
        this.addressReturn = addressReturn;
    }

}
