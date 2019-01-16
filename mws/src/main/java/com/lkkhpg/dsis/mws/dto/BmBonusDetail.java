package com.lkkhpg.dsis.mws.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

public class BmBonusDetail extends BaseDTO {

    private Long memberId;

    private Long marketId;

    private String dealerNo;

    private String period;

    private String orgCode;

    private String mkCode;

    private BigDecimal orgGdcamt;

    private BigDecimal orgLbamt;

    private BigDecimal orgPbamt;

    private BigDecimal orgBbamt;

    private BigDecimal orgEx450amt;

    private BigDecimal orgOut10amt;

    private BigDecimal orgAbamt;

    private BigDecimal orgTopamt;

    private BigDecimal orgStdBonus;

    private BigDecimal orgStdBonusPvRate;

    private BigDecimal orgLclBonusOrigin;

    private BigDecimal orgRebateAmt;

    private BigDecimal orgLclBonusAdjust;

    private BigDecimal orgPayExRate;

    private BigDecimal orgPreTaxAmtIncome;

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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getMkCode() {
        return mkCode;
    }

    public void setMkCode(String mkCode) {
        this.mkCode = mkCode;
    }

    public BigDecimal getOrgGdcamt() {
        return orgGdcamt;
    }

    public void setOrgGdcamt(BigDecimal orgGdcamt) {
        this.orgGdcamt = orgGdcamt;
    }

    public BigDecimal getOrgLbamt() {
        return orgLbamt;
    }

    public void setOrgLbamt(BigDecimal orgLbamt) {
        this.orgLbamt = orgLbamt;
    }

    public BigDecimal getOrgPbamt() {
        return orgPbamt;
    }

    public void setOrgPbamt(BigDecimal orgPbamt) {
        this.orgPbamt = orgPbamt;
    }

    public BigDecimal getOrgBbamt() {
        return orgBbamt;
    }

    public void setOrgBbamt(BigDecimal orgBbamt) {
        this.orgBbamt = orgBbamt;
    }

    public BigDecimal getOrgEx450amt() {
        return orgEx450amt;
    }

    public void setOrgEx450amt(BigDecimal orgEx450amt) {
        this.orgEx450amt = orgEx450amt;
    }

    public BigDecimal getOrgOut10amt() {
        return orgOut10amt;
    }

    public void setOrgOut10amt(BigDecimal orgOut10amt) {
        this.orgOut10amt = orgOut10amt;
    }

    public BigDecimal getOrgAbamt() {
        return orgAbamt;
    }

    public void setOrgAbamt(BigDecimal orgAbamt) {
        this.orgAbamt = orgAbamt;
    }

    public BigDecimal getOrgTopamt() {
        return orgTopamt;
    }

    public void setOrgTopamt(BigDecimal orgTopamt) {
        this.orgTopamt = orgTopamt;
    }

    public BigDecimal getOrgStdBonus() {
        return orgStdBonus;
    }

    public void setOrgStdBonus(BigDecimal orgStdBonus) {
        this.orgStdBonus = orgStdBonus;
    }

    public BigDecimal getOrgStdBonusPvRate() {
        return orgStdBonusPvRate;
    }

    public void setOrgStdBonusPvRate(BigDecimal orgStdBonusPvRate) {
        this.orgStdBonusPvRate = orgStdBonusPvRate;
    }

    public BigDecimal getOrgLclBonusOrigin() {
        return orgLclBonusOrigin;
    }

    public void setOrgLclBonusOrigin(BigDecimal orgLclBonusOrigin) {
        this.orgLclBonusOrigin = orgLclBonusOrigin;
    }

    public BigDecimal getOrgRebateAmt() {
        return orgRebateAmt;
    }

    public void setOrgRebateAmt(BigDecimal orgRebateAmt) {
        this.orgRebateAmt = orgRebateAmt;
    }

    public BigDecimal getOrgLclBonusAdjust() {
        return orgLclBonusAdjust;
    }

    public void setOrgLclBonusAdjust(BigDecimal orgLclBonusAdjust) {
        this.orgLclBonusAdjust = orgLclBonusAdjust;
    }

    public BigDecimal getOrgPayExRate() {
        return orgPayExRate;
    }

    public void setOrgPayExRate(BigDecimal orgPayExRate) {
        this.orgPayExRate = orgPayExRate;
    }

    public BigDecimal getOrgPreTaxAmtIncome() {
        return orgPreTaxAmtIncome;
    }

    public void setOrgPreTaxAmtIncome(BigDecimal orgPreTaxAmtIncome) {
        this.orgPreTaxAmtIncome = orgPreTaxAmtIncome;
    }
}