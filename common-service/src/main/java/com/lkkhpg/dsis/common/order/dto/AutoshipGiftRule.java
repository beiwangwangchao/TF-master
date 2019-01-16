/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * AutoShip礼品规则dto.
 * 
 * @author zhangchuangsheng
 *
 */
public class AutoshipGiftRule extends BaseDTO {
    private Long ruleId;

    private Long marketId;

    private String ruleCode;

    private String ruleName;

    private String status;

    @JsonFormat(pattern = "yyyyMM")
    private String activeMonth;

    private Long month;

    private String calculationType;

    private Long calculationValue;

    private List<AutoshipGifts> autoshipGifts;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode == null ? null : ruleCode.trim();
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getActiveMonth() {
        return activeMonth;
    }

    public void setActiveMonth(String activeMonth) {
        this.activeMonth = activeMonth == null ? null : activeMonth.trim();
    }

    public Long getMonth() {
        return month;
    }

    public void setMonth(Long month) {
        this.month = month;
    }

    public String getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType == null ? null : calculationType.trim();
    }

    public Long getCalculationValue() {
        return calculationValue;
    }

    public void setCalculationValue(Long calculationValue) {
        this.calculationValue = calculationValue;
    }

    public List<AutoshipGifts> getAutoshipGifts() {
        return autoshipGifts;
    }

    public void setAutoshipGifts(List<AutoshipGifts> autoshipGifts) {
        this.autoshipGifts = autoshipGifts;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}