/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 发票编号发放Dto.
 * 
 * @author li.peng@hand-china.com
 *
 */
public class SpmInvNumbering extends BaseDTO {

    /**
     * 主键Id.
     */
    @Id
    private Long ruleId;

    /**
     * 规则.
     */
    @NotEmpty
    private String name;

    /**
     * 开始时间.
     */
    @NotNull
    private Date startActiveDate;

    /**
     * 结束时间.
     */
    @NotNull
    private Date endActiveDate;

    /**
     * 前缀.
     */
    private String prefix;

    /**
     * 初始编码.
     */
    private String initNumber;

    /**
     * 最大编码.
     */
    private String maxNumber;

    /**
     * 说明.
     */
    private String description;

    /**
     * 启用标识.
     */
    private String enabledFlag;

    /**
     * 后缀.
     */
    private String suffix;

    /**
     * 步数.
     */
    private Long stepLen;

    /**
     * 当前编号.
     */
    private String currentNumber;

    /**
     * 规则类型.
     */
    @NotEmpty
    private String ruleType;

    /**
     * 市场Id.
     */
    @NotNull
    private Long marketId;

    /**
     * 市场名称.
     */
    private String marketName;
    
    /**
     * 金额为0是否开票.
     */
    private String zeroFlag;
    
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

    public String getInitNumber() {
        return initNumber;
    }

    public void setInitNumber(String initNumber) {
        this.initNumber = initNumber == null ? null : initNumber.trim();
    }

    public String getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(String maxNumber) {
        this.maxNumber = maxNumber == null ? null : maxNumber.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    public Long getStepLen() {
        return stepLen;
    }

    public void setStepLen(Long stepLen) {
        this.stepLen = stepLen;
    }

    public String getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(String currentNumber) {
        this.currentNumber = currentNumber == null ? null : currentNumber.trim();
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

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getZeroFlag() {
        return zeroFlag;
    }

    public void setZeroFlag(String zeroFlag) {
        this.zeroFlag = zeroFlag;
    }

}