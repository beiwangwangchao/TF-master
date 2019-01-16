/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 城市DTO.
 * 
 * @author frank.li
 */
@MultiLanguage
@Table(name = "spm_city_b")
public class SpmCity extends BaseDTO {

    @Id
    @Column(name = "city_code")
    private String cityCode;

    private String stateCode;

    @MultiLanguageField
    @Column(name = "name")
    private String name;

    @MultiLanguageField
    @Column(name = "remark")
    private String remark;

    private String enabledFlag;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startActiveDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endActiveDate;

    private String timezoneCode;

    private String language;

    private String currencyCode;
    
    private String taxType;
    
    private String taxLevel;
    
    private BigDecimal taxRate;
    
    private BigDecimal sortNum;
    
    public BigDecimal getSortNum() {
		return sortNum;
	}

	public void setSortNum(BigDecimal sortNum) {
		this.sortNum = sortNum;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public String getTimezoneCode() {
        return timezoneCode;
    }

    public void setTimezoneCode(String timezoneCode) {
        this.timezoneCode = timezoneCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getTaxLevel() {
        return taxLevel;
    }

    public void setTaxLevel(String taxLevel) {
        this.taxLevel = taxLevel;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

}