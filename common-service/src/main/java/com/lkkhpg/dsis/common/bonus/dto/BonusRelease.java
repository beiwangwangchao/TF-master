/*
 *
 */
package com.lkkhpg.dsis.common.bonus.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 奖金发放.
 * @author li.peng@hand-china.com
 *
 */
public class BonusRelease extends BaseDTO {
    
    private Long bonusId;

    private Long periodId;

    private Long memberId;

    private String currencyCode;

    private BigDecimal preTaxAmtAdjust;

    private BigDecimal preTaxAmt;

    private String taxCode01;

    private BigDecimal taxAmt01;

    private String taxCode02;

    private BigDecimal taxAmt02;

    private BigDecimal afterTaxAmt;

    private String clearTaxCode03;

    private BigDecimal clearTaxAmt03;

    private String comments;

    private Long finalBonusId;

    private Long marketId;

    private String status;

    private BigDecimal withholdingTaxAmt;
    
    private String bonusCode;

    private String bonusType;
    
    private BigDecimal deliverAmt;

    /**
     *会员编码. 
     */
    private String memberCode;
    
    /**
     * 会员名.
     */
    private String memberName;
    
    /**
     * 所属市场
     */
    private String marketName;
    
    /**
     * 所属公司
     */
    private String companyName;
    
    /**
     * 期间名称.
     * 
     */
    private String periodName;
    
    /**
     * 奖金年份.
     */
    private Long periodYear;
    
    /**
     * 奖金下限.
     */
    private BigDecimal minBonusAmt;
    
    /**
     * 证件编码/商业注册码.
     */
    private String idNumber;
    
    /**
     * 月税类型.
     */
    private String monthTaxType;
    
    /**
     * 月税状态.
     */
    private String monthTaxStatus;
    
    public Long getBonusId() {
        return bonusId;
    }

    public void setBonusId(Long bonusId) {
        this.bonusId = bonusId;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getPreTaxAmtAdjust() {
        return preTaxAmtAdjust;
    }

    public void setPreTaxAmtAdjust(BigDecimal preTaxAmtAdjust) {
        this.preTaxAmtAdjust = preTaxAmtAdjust;
    }

    public BigDecimal getPreTaxAmt() {
        return preTaxAmt;
    }

    public void setPreTaxAmt(BigDecimal preTaxAmt) {
        this.preTaxAmt = preTaxAmt;
    }

    public String getTaxCode01() {
        return taxCode01;
    }

    public void setTaxCode01(String taxCode01) {
        this.taxCode01 = taxCode01;
    }

    public BigDecimal getTaxAmt01() {
        return taxAmt01;
    }

    public void setTaxAmt01(BigDecimal taxAmt01) {
        this.taxAmt01 = taxAmt01;
    }

    public String getTaxCode02() {
        return taxCode02;
    }

    public void setTaxCode02(String taxCode02) {
        this.taxCode02 = taxCode02;
    }

    public BigDecimal getTaxAmt02() {
        return taxAmt02;
    }

    public void setTaxAmt02(BigDecimal taxAmt02) {
        this.taxAmt02 = taxAmt02;
    }

    public BigDecimal getAfterTaxAmt() {
        return afterTaxAmt;
    }

    public void setAfterTaxAmt(BigDecimal afterTaxAmt) {
        this.afterTaxAmt = afterTaxAmt;
    }

    public String getClearTaxCode03() {
        return clearTaxCode03;
    }

    public void setClearTaxCode03(String clearTaxCode03) {
        this.clearTaxCode03 = clearTaxCode03;
    }

    public BigDecimal getClearTaxAmt03() {
        return clearTaxAmt03;
    }

    public void setClearTaxAmt03(BigDecimal clearTaxAmt03) {
        this.clearTaxAmt03 = clearTaxAmt03;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getFinalBonusId() {
        return finalBonusId;
    }

    public void setFinalBonusId(Long finalBonusId) {
        this.finalBonusId = finalBonusId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getWithholdingTaxAmt() {
        return withholdingTaxAmt;
    }

    public void setWithholdingTaxAmt(BigDecimal withholdingTaxAmt) {
        this.withholdingTaxAmt = withholdingTaxAmt;
    }

    
    public String getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(String bonusCode) {
        this.bonusCode = bonusCode;
    }

    public String getBonusType() {
        return bonusType;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public BigDecimal getDeliverAmt() {
        return deliverAmt;
    }

    public void setDeliverAmt(BigDecimal deliverAmt) {
        this.deliverAmt = deliverAmt;
    }

    public Long getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Long periodYear) {
        this.periodYear = periodYear;
    }

    public BigDecimal getMinBonusAmt() {
        return minBonusAmt;
    }

    public void setMinBonusAmt(BigDecimal minBonusAmt) {
        this.minBonusAmt = minBonusAmt;
    }


    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMonthTaxType() {
        return monthTaxType;
    }

    public void setMonthTaxType(String monthTaxType) {
        this.monthTaxType = monthTaxType;
    }

    public String getMonthTaxStatus() {
        return monthTaxStatus;
    }

    public void setMonthTaxStatus(String monthTaxStatus) {
        this.monthTaxStatus = monthTaxStatus;
    }

    
    
}