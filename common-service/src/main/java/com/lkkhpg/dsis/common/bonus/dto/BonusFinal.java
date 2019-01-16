/*
 *
 */
package com.lkkhpg.dsis.common.bonus.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 最终奖金dto.
 * 
 * @author gulin
 *
 */
public class BonusFinal extends BaseDTO {
    private Long bonusId;

    private Long periodId;

    // private String periodMark;

    private Long memberId;

    // private Long clearProcTimes;

    private String currencyCode;

    // private BigDecimal preTaxAmtIncome;

    // private BigDecimal preTaxAmtDiffer;

    private BigDecimal preTaxAmtAdjust;

    // private BigDecimal preTaxAmtOddment;

    private BigDecimal preTaxAmt;

    private String taxCode01;

    private BigDecimal taxAmt01;

    private String taxCode02;

    private BigDecimal taxAmt02;

    private BigDecimal afterTaxAmt;

    // private BigDecimal afterTaxAmtAdjust;

    private BigDecimal deliverAmt;

    // private String deliverOrgCode;

    private String clearTaxCode03;

    private BigDecimal clearTaxAmt03;

    private String deliverBankCode;

    private BigDecimal deliverBankFee;

    private BigDecimal remitNetAmt;

    private String remitBankCode;

    private String remitBankName;

    private String remitBankAccount;

    private String remitFlag;

    private Date remitDate;

    private String remitMemo;

    private String comments;

    // private BigDecimal remitServiceAmt;

    private Long marketId;

    private String status;

    private BigDecimal withholdingTaxAmt;

    private String remitMode;

    private String idNumber;

    // private String idType;

    private String bonusCode;

    private String bonusType;

    private String remitBankAccountName;

    // private Long releaseBonusId;

    // private String paymentStatus;

    /**
     * 会员编码.
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
     * 最终奖金记录数
     * 
     */
    private Long amount;

    /**
     * 奖金总额
     * 
     */
    private BigDecimal totalBonus;

    /**
     * 分行编号
     */
    private String bankBranchCode;

    private String lockFlag;

    private String autoFaliledFlag;
    
    private String processMode;
    
    private String dateFrom;
    
    private String dateTo;

    /**
     * 所得给付起始年月(本地奖金)
     */
    private Integer ldateFrom;

    /**
     * 所得給付結束年月(本地奖金)
     */
    private Integer ldateTo;

    /**
     * 所得给付起始年月(国际奖金)
     */
    private Integer idateFrom;

    /**
     * 所得給付結束年月(国际奖金)
     */
    private Integer idateTo;
    /**
     * 会员编号从
     */
    private String memberCodeForm;
    /**
     * 会员编号至
     */
    private String memberCodeTo;

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
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
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
        this.taxCode01 = taxCode01 == null ? null : taxCode01.trim();
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
        this.taxCode02 = taxCode02 == null ? null : taxCode02.trim();
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

    public BigDecimal getDeliverAmt() {
        return deliverAmt;
    }

    public void setDeliverAmt(BigDecimal deliverAmt) {
        this.deliverAmt = deliverAmt;
    }

    public String getClearTaxCode03() {
        return clearTaxCode03;
    }

    public void setClearTaxCode03(String clearTaxCode03) {
        this.clearTaxCode03 = clearTaxCode03 == null ? null : clearTaxCode03.trim();
    }

    public BigDecimal getClearTaxAmt03() {
        return clearTaxAmt03;
    }

    public void setClearTaxAmt03(BigDecimal clearTaxAmt03) {
        this.clearTaxAmt03 = clearTaxAmt03;
    }

    public String getDeliverBankCode() {
        return deliverBankCode;
    }

    public void setDeliverBankCode(String deliverBankCode) {
        this.deliverBankCode = deliverBankCode == null ? null : deliverBankCode.trim();
    }

    public BigDecimal getDeliverBankFee() {
        return deliverBankFee;
    }

    public void setDeliverBankFee(BigDecimal deliverBankFee) {
        this.deliverBankFee = deliverBankFee;
    }

    public BigDecimal getRemitNetAmt() {
        return remitNetAmt;
    }

    public void setRemitNetAmt(BigDecimal remitNetAmt) {
        this.remitNetAmt = remitNetAmt;
    }

    public String getRemitBankCode() {
        return remitBankCode;
    }

    public void setRemitBankCode(String remitBankCode) {
        this.remitBankCode = remitBankCode == null ? null : remitBankCode.trim();
    }

    public String getRemitBankName() {
        return remitBankName;
    }

    public void setRemitBankName(String remitBankName) {
        this.remitBankName = remitBankName == null ? null : remitBankName.trim();
    }

    public String getRemitBankAccount() {
        return remitBankAccount;
    }

    public void setRemitBankAccount(String remitBankAccount) {
        this.remitBankAccount = remitBankAccount == null ? null : remitBankAccount.trim();
    }

    public String getRemitFlag() {
        return remitFlag;
    }

    public void setRemitFlag(String remitFlag) {
        this.remitFlag = remitFlag == null ? null : remitFlag.trim();
    }

    public Date getRemitDate() {
        return remitDate;
    }

    public void setRemitDate(Date remitDate) {
        this.remitDate = remitDate;
    }

    public String getRemitMemo() {
        return remitMemo;
    }

    public void setRemitMemo(String remitMemo) {
        this.remitMemo = remitMemo == null ? null : remitMemo.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
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
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getWithholdingTaxAmt() {
        return withholdingTaxAmt;
    }

    public void setWithholdingTaxAmt(BigDecimal withholdingTaxAmt) {
        this.withholdingTaxAmt = withholdingTaxAmt;
    }

    public String getRemitMode() {
        return remitMode;
    }

    public void setRemitMode(String remitMode) {
        this.remitMode = remitMode == null ? null : remitMode.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(String bonusCode) {
        this.bonusCode = bonusCode == null ? null : bonusCode.trim();
    }

    public String getBonusType() {
        return bonusType;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType == null ? null : bonusType.trim();
    }

    public String getRemitBankAccountName() {
        return remitBankAccountName;
    }

    public void setRemitBankAccountName(String remitBankAccountName) {
        this.remitBankAccountName = remitBankAccountName == null ? null : remitBankAccountName.trim();
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(BigDecimal totalBonus) {
        this.totalBonus = totalBonus;
    }

    public String getBankBranchCode() {
        return bankBranchCode;
    }

    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public String getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag;
    }

    public String getAutoFaliledFlag() {
        return autoFaliledFlag;
    }

    public void setAutoFaliledFlag(String autoFaliledFlag) {
        this.autoFaliledFlag = autoFaliledFlag;
    }

    public Integer getLdateFrom() {
        return ldateFrom;
    }

    public void setLdateFrom(Integer ldateFrom) {
        this.ldateFrom = ldateFrom;
    }

    public Integer getIdateTo() {
        return idateTo;
    }

    public void setIdateTo(Integer idateTo) {
        this.idateTo = idateTo;
    }

    public Integer getIdateFrom() {
        return idateFrom;
    }

    public void setIdateFrom(Integer idateFrom) {
        this.idateFrom = idateFrom;
    }

    public Integer getLdateTo() {
        return ldateTo;
    }

    public void setLdateTo(Integer ldateTo) {
        this.ldateTo = ldateTo;
    }

    public String getMemberCodeForm() {
        return memberCodeForm;
    }

    public void setMemberCodeForm(String memberCodeForm) {
        this.memberCodeForm = memberCodeForm;
    }

    public String getMemberCodeTo() {
        return memberCodeTo;
    }

    public void setMemberCodeTo(String memberCodeTo) {
        this.memberCodeTo = memberCodeTo;
    }

    public String getProcessMode() {
        return processMode;
    }

    public void setProcessMode(String processMode) {
        this.processMode = processMode;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    
}