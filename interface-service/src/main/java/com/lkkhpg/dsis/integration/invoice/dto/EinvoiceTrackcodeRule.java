/*
 * #{copyright}#
 */
package com.lkkhpg.dsis.integration.invoice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

import java.util.Date;

/**
 * 发票字轨DTO.
 * 
 * @author linyuheng
 */
public class EinvoiceTrackcodeRule extends BaseDTO {

    private Long ruleId;

    private String name;

    private String invoiceCateCode;

    private String invoiceCateMeaning;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startActiveDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endActiveDate;

    private String prefix;

    private String suffix;

    private Long initNumber;

    private Long maxNumber;

    private String enabledFlag;

    private Long stepLength;

    private Long currentNumber;

    private String ruleType;

    private Long marketId;

    private String zeroFlag;

    private String marketCode;

    private String period;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getInvoiceCateCode() {
        return invoiceCateCode;
    }

    public void setInvoiceCateCode(String invoiceCateCode) {
        this.invoiceCateCode = invoiceCateCode == null ? null : invoiceCateCode.trim();
    }

    public String getInvoiceCateMeaning() {
        return invoiceCateMeaning;
    }

    public void setInvoiceCateMeaning(String invoiceCateMeaning) {
        this.invoiceCateMeaning = invoiceCateMeaning == null ? null : invoiceCateMeaning.trim();
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix == null ? null : prefix.trim();
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    public Long getInitNumber() {
        return initNumber;
    }

    public void setInitNumber(Long initNumber) {
        this.initNumber = initNumber;
    }

    public Long getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Long maxNumber) {
        this.maxNumber = maxNumber;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }

    public Long getStepLength() {
        return stepLength;
    }

    public void setStepLength(Long stepLength) {
        this.stepLength = stepLength;
    }

    public Long getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Long currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getZeroFlag() {
        return zeroFlag;
    }

    public void setZeroFlag(String zeroFlag) {
        this.zeroFlag = zeroFlag == null ? null : zeroFlag.trim();
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

}