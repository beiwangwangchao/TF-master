/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员冲销节余Dto.
 * 
 * @author frank.li
 */
public class MemWithdraw extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long withdrawId;

    private Long salesOrgId;

    private Long memberId;

    private Long marketId;

    private Long companyId;

    private Integer period;

    private String writeoffType;

    private String adjustmentType;

    private BigDecimal amount;

    private String status;

    private String remark;

    private String memberName;

    private String refCompany;

    private String marketBelong;

    private String memberCode;

    private String periodName;
    
    private String sourceType;
    private Long sourceKey;

    /**
     * @return the sourceType
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * @param sourceType the sourceType to set
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * @return the sourceKey
     */
    public Long getSourceKey() {
        return sourceKey;
    }

    /**
     * @param sourceKey the sourceKey to set
     */
    public void setSourceKey(Long sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMarketBelong() {
        return marketBelong;
    }

    public void setMarketBelong(String marketBelong) {
        this.marketBelong = marketBelong;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRefCompany() {
        return refCompany;
    }

    public void setRefCompany(String refCompany) {
        this.refCompany = refCompany;
    }

    public Long getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(Long withdrawId) {
        this.withdrawId = withdrawId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getWriteoffType() {
        return writeoffType;
    }

    public void setWriteoffType(String writeoffType) {
        this.writeoffType = writeoffType == null ? null : writeoffType.trim();
    }

    public String getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(String adjustmentType) {
        this.adjustmentType = adjustmentType == null ? null : adjustmentType.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }
}