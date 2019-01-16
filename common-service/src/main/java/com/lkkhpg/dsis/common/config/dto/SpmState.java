/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 省/州DTO.
 *
 * @author frank.li
 */
@MultiLanguage
@Table(name = "spm_state_b")
public class SpmState extends BaseDTO {
    @Id
    @NotEmpty
    @Column(name = "state_code")
    private String stateCode;

    private String countryCode;

    private String timezoneCode;

    @NotEmpty
    @MultiLanguageField
    @Column(name = "name")
    private String name;

    @MultiLanguageField
    @Column(name = "remark")
    private String remark;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

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

	@Children
    private List<SpmCity> spmCities;

    public List<SpmCity> getSpmCities() {
        return spmCities;
    }

    public void setSpmCities(List<SpmCity> spmCities) {
        this.spmCities = spmCities;
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

    public String getTimezoneCode() {
        return timezoneCode;
    }

    public void setTimezoneCode(String timezoneCode) {
        this.timezoneCode = timezoneCode == null ? null : timezoneCode.trim();
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
}