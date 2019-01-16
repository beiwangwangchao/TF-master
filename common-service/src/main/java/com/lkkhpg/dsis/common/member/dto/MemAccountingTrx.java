/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员资产交易DTO.
 * 
 * @author frank.li
 */
public class MemAccountingTrx extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long trxId;

    @NotNull
    private Long memberId;

    private Long companyId;

    private Long salesOrgId;

    @NotNull
    private Date trxDate;

    @NotNull
    private String trxType;

    @NotNull
    private String trxSourceType;

    @NotNull
    private Long trxSourceId;

    private Long trxSourceLineId;

    @NotNull
    private String accountingType;

    @NotNull
    private BigDecimal trxValue;

    @NotNull
    private BigDecimal balance;

    private String remark;

    public Long getTrxId() {
        return trxId;
    }

    public void setTrxId(Long trxId) {
        this.trxId = trxId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public Date getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType == null ? null : trxType.trim();
    }

    public String getTrxSourceType() {
        return trxSourceType;
    }

    public void setTrxSourceType(String trxSourceType) {
        this.trxSourceType = trxSourceType == null ? null : trxSourceType.trim();
    }

    public Long getTrxSourceId() {
        return trxSourceId;
    }

    public void setTrxSourceId(Long trxSourceId) {
        this.trxSourceId = trxSourceId;
    }

    public Long getTrxSourceLineId() {
        return trxSourceLineId;
    }

    public void setTrxSourceLineId(Long trxSourceLineId) {
        this.trxSourceLineId = trxSourceLineId;
    }

    public String getAccountingType() {
        return accountingType;
    }

    public void setAccountingType(String accountingType) {
        this.accountingType = accountingType == null ? null : accountingType.trim();
    }

    public BigDecimal getTrxValue() {
        return trxValue;
    }

    public void setTrxValue(BigDecimal trxValue) {
        this.trxValue = trxValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}