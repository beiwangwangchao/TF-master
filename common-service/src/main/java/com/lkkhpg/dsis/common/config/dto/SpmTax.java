/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 税率.
 * 
 * @author chenjingxiong
 */
public class SpmTax extends BaseDTO {

    private Long taxId;

    private String taxCode;

    private String description;

    private String registrationNumber;

    private String taxType;

    private String unitPriceTax;

    private BigDecimal taxPercent;

    private String calculationLevel;

    private String taxName;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

    private String isActive;

    /**
     * 商品是否含税,从系统参数SPM.PRICE_INCLUDE_TAX里面取值设置.
     */
    private Boolean priceInclueTax;

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
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

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode == null ? null : taxCode.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber == null ? null : registrationNumber.trim();
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType == null ? null : taxType.trim();
    }

    public String getUnitPriceTax() {
        return unitPriceTax;
    }

    public void setUnitPriceTax(String unitPriceTax) {
        this.unitPriceTax = unitPriceTax == null ? null : unitPriceTax.trim();
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(BigDecimal taxPercent) {
        this.taxPercent = taxPercent;
    }

    public String getCalculationLevel() {
        return calculationLevel;
    }

    public void setCalculationLevel(String calculationLevel) {
        this.calculationLevel = calculationLevel == null ? null : calculationLevel.trim();
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    /**
     * 商品是否含税.
     * 
     * @return true 为系统参数SPM.PRICE_INCLUDE_TAX设置为含税，false或者空则不含税.
     */
    public Boolean getPriceInclueTax() {
        if (priceInclueTax == null) {
            setPriceInclueTax(Boolean.FALSE);
        }
        return priceInclueTax;
    }

    /**
     * 商品是否含税,从系统参数SPM.PRICE_INCLUDE_TAX里面取值设置.
     * 
     */
    public void setPriceInclueTax(Boolean priceInclueTax) {
        this.priceInclueTax = priceInclueTax;
    }

}