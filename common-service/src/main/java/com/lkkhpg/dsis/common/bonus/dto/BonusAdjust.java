/*
 *
 */
package com.lkkhpg.dsis.common.bonus.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 奖金调整.
 * @author li.peng@hand-china.com
 *
 */
@AuditEnabled
@Table(name = "BM_BONUS_ADJ")
public class BonusAdjust extends BaseDTO {
    
    @Id
    @Column(name = "adj_id")
    private Long adjId;

    private String adjCode;

    private Long memberId;

    private Long periodId;

    private String adjStatus;

    private String adjType;

    private String adjReason;

    private String description;

    private String syncFlag;

    private BigDecimal adjAmt;

    private Long marketId;

    private String currencyCode;
    /**
     * 会员编码.
     */
    private String memberCode;
    
    /**
     * 公司名称.
     */
    private String companyName;
    
    /**
     * 市场名称.
     */
    private String marketName;
    
    /**
     * 期间名.
     */
    
    private String periodName;
    
    /**
     * 创建时间编码.
     */
    private String createDateCode;
    
    private Date creationDate;
    
    private String adjCategory;
    
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getCreateDateCode() {
        return createDateCode;
    }

    public void setCreateDateCode(String createDateCode) {
        this.createDateCode = createDateCode;
    }

    public Long getAdjId() {
        return adjId;
    }

    public void setAdjId(Long adjId) {
        this.adjId = adjId;
    }

    public String getAdjCode() {
        return adjCode;
    }

    public void setAdjCode(String adjCode) {
        this.adjCode = adjCode == null ? null : adjCode.trim();
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public String getAdjStatus() {
        return adjStatus;
    }

    public void setAdjStatus(String adjStatus) {
        this.adjStatus = adjStatus == null ? null : adjStatus.trim();
    }

    public String getAdjType() {
        return adjType;
    }

    public void setAdjType(String adjType) {
        this.adjType = adjType == null ? null : adjType.trim();
    }

    public String getAdjReason() {
        return adjReason;
    }

    public void setAdjReason(String adjReason) {
        this.adjReason = adjReason == null ? null : adjReason.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(String syncFlag) {
        this.syncFlag = syncFlag == null ? null : syncFlag.trim();
    }

    public BigDecimal getAdjAmt() {
        return adjAmt;
    }

    public void setAdjAmt(BigDecimal adjAmt) {
        this.adjAmt = adjAmt;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getAdjCategory() {
        return adjCategory;
    }

    public void setAdjCategory(String adjCategory) {
        this.adjCategory = adjCategory;
    }
    
    
}