/*
 *
 */
package com.lkkhpg.dsis.common.bonus.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * re_transfer .
 * @author li.peng@hand-china.com
 *
 */
public class BonusRetransfer extends BaseDTO {
    
    private Long retransId;

    private String retransCode;

    private Long bonusId;

    private Long finalBonusId;

    private Long memberId;

    private Long periodId;

    private String status;

    private BigDecimal retransAmt;

    private Long marketId;

    private String currencyCode;
    
    /**
     *会员编码. 
     */
    private String memberCode;
    
    /**
     * 会员名.
     */
    private String memberName;
    
    /**
     * 所属市场.
     */
    private String marketName;
    
    /**
     * 所属公司.
     */
    private String companyName;
    
    /**
     * 期间名称.
     * 
     */
    private String periodName;
    
    private Date creationDate;
    
    private String bonusCode;
    
    private String finalBonusCode;
    
    /**
     * 生成re-transfer记录的最终奖金类型
     */
    private String bonusType;
    
    public String getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(String bonusCode) {
        this.bonusCode = bonusCode;
    }

    public String getFinalBonusCode() {
        return finalBonusCode;
    }

    public void setFinalBonusCode(String finalBonusCode) {
        this.finalBonusCode = finalBonusCode;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

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

    public Long getRetransId() {
        return retransId;
    }

    public void setRetransId(Long retransId) {
        this.retransId = retransId;
    }

    public String getRetransCode() {
        return retransCode;
    }

    public void setRetransCode(String retransCode) {
        this.retransCode = retransCode;
    }

    public Long getBonusId() {
        return bonusId;
    }

    public void setBonusId(Long bonusId) {
        this.bonusId = bonusId;
    }

    public Long getFinalBonusId() {
        return finalBonusId;
    }

    public void setFinalBonusId(Long finalBonusId) {
        this.finalBonusId = finalBonusId;
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
        this.status = status;
    }

    public BigDecimal getRetransAmt() {
        return retransAmt;
    }

    public void setRetransAmt(BigDecimal retransAmt) {
        this.retransAmt = retransAmt;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getBonusType() {
        return bonusType;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType;
    }
    
}