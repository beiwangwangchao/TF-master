/*
 *
 */
package com.lkkhpg.dsis.common.bonus.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * iPoint奖金记录.
 * 
 * @author houmin
 *
 */
public class IpointBonus extends BaseDTO {
    private Long bonusId;

    private Long salesOrgId;

    private String bonusNumber;

    private String bonusType;

    private String adjType;

    private Long memberId;

    private Long periodId;

    private String status;

    private BigDecimal amount;

    @NotNull
    private Date approvedDate;
    
    private String remark;
    
    private String periodName;
    
    private String memberCode;
    
    private Long marketId;
    
    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public Long getBonusId() {
        return bonusId;
    }

    public void setBonusId(Long bonusId) {
        this.bonusId = bonusId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getBonusNumber() {
        return bonusNumber;
    }

    public void setBonusNumber(String bonusNumber) {
        this.bonusNumber = bonusNumber == null ? null : bonusNumber.trim();
    }

    public String getBonusType() {
        return bonusType;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType == null ? null : bonusType.trim();
    }

    public String getAdjType() {
        return adjType;
    }

    public void setAdjType(String adjType) {
        this.adjType = adjType == null ? null : adjType.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }
}