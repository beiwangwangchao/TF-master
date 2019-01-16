/*
 *
 */

package com.lkkhpg.dsis.common.config.dto;

import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 币种DTO.
 * @author wuyichu
 */
public class SpmCurrency extends BaseDTO {
    private String currencyCode;

    private Long precision;

    private Long extendedPrecision;

    private String currencyName;

    private String description;

    private String symbol;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public Long getPrecision() {
        return precision;
    }

    public void setPrecision(Long precision) {
        this.precision = precision;
    }

    public Long getExtendedPrecision() {
        return extendedPrecision;
    }

    public void setExtendedPrecision(Long extendedPrecision) {
        this.extendedPrecision = extendedPrecision;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName == null ? null : currencyName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
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