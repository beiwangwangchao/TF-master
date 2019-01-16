package com.lkkhpg.dsis.mws.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

public class BmBonusPayDetail extends BaseDTO {

    private Long memberId;

    private String dealerNo;

    private String period;

    private String periodMark;

    private String type;

    private BigDecimal preTaxAmtIncome;

    private BigDecimal preTaxAmtDiffer;

    private BigDecimal preTaxAmtAdjust;

    private BigDecimal tax;

    private BigDecimal afterTaxAmt;

    private BigDecimal afterTaxAmtAdjust;

    private BigDecimal remitNetAmt;

    private String remitNetAccount;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodMark() {
        return periodMark;
    }

    public void setPeriodMark(String periodMark) {
        this.periodMark = periodMark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPreTaxAmtIncome() {
        return preTaxAmtIncome;
    }

    public void setPreTaxAmtIncome(BigDecimal preTaxAmtIncome) {
        this.preTaxAmtIncome = preTaxAmtIncome;
    }

    public BigDecimal getPreTaxAmtDiffer() {
        return preTaxAmtDiffer;
    }

    public void setPreTaxAmtDiffer(BigDecimal preTaxAmtDiffer) {
        this.preTaxAmtDiffer = preTaxAmtDiffer;
    }

    public BigDecimal getPreTaxAmtAdjust() {
        return preTaxAmtAdjust;
    }

    public void setPreTaxAmtAdjust(BigDecimal preTaxAmtAdjust) {
        this.preTaxAmtAdjust = preTaxAmtAdjust;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getAfterTaxAmt() {
        return afterTaxAmt;
    }

    public void setAfterTaxAmt(BigDecimal afterTaxAmt) {
        this.afterTaxAmt = afterTaxAmt;
    }

    public BigDecimal getAfterTaxAmtAdjust() {
        return afterTaxAmtAdjust;
    }

    public void setAfterTaxAmtAdjust(BigDecimal afterTaxAmtAdjust) {
        this.afterTaxAmtAdjust = afterTaxAmtAdjust;
    }

    public BigDecimal getRemitNetAmt() {
        return remitNetAmt;
    }

    public void setRemitNetAmt(BigDecimal remitNetAmt) {
        this.remitNetAmt = remitNetAmt;
    }

    public String getRemitNetAccount() {
        return remitNetAccount;
    }

    public void setRemitNetAccount(String remitNetAccount) {
        this.remitNetAccount = remitNetAccount;
    }
}