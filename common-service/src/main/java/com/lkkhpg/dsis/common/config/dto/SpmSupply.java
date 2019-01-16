/*
 *
 */
package com.lkkhpg.dsis.common.config.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

import java.util.List;

/**
 * 供货关系DTO.
 * 
 * @author chenjingxiong
 */
public class SpmSupply extends BaseDTO {
    private Long supplyId;

    private String supplyType;

    private Long salesOrgId;

    private String countryCode;

    private String stateCode;

    private String cityCode;

    private Long invOrgId;

    private String remark;

    private String defaultFlag;
    
    private String countryName;
    
    private String stateName;
    
    private String cityName;
    
    private String salesOrgName;
    
    private String salesOrgCode;
    
    private String invOrgName;
    
    private String invOrgCode;
    
    private List<SpmSupply> invs;
    
    public List<SpmSupply> getInvs() {
        return invs;
    }

    public void setInvs(List<SpmSupply> invs) {
        this.invs = invs;
    }

    public String getInvOrgName() {
        return invOrgName;
    }

    public void setInvOrgName(String invOrgName) {
        this.invOrgName = invOrgName;
    }

    public String getInvOrgCode() {
        return invOrgCode;
    }

    public void setInvOrgCode(String invOrgCode) {
        this.invOrgCode = invOrgCode;
    }

    public String getSalesOrgCode() {
        return salesOrgCode;
    }

    public void setSalesOrgCode(String salesOrgCode) {
        this.salesOrgCode = salesOrgCode;
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

    public String getSalesOrgName() {
        return salesOrgName;
    }

    public void setSalesOrgName(String salesOrgName) {
        this.salesOrgName = salesOrgName;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(String supplyType) {
        this.supplyType = supplyType == null ? null : supplyType.trim();
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode == null ? null : stateCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Long getInvOrgId() {
        return invOrgId;
    }

    public void setInvOrgId(Long invOrgId) {
        this.invOrgId = invOrgId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag == null ? null : defaultFlag.trim();
    }
}