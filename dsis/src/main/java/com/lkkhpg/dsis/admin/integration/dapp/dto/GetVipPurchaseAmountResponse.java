/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 查询VIP消费总额响应DTO.
 * 
 * @author shenqb
 *
 */
public class GetVipPurchaseAmountResponse {
    @JsonProperty("VIPNumber") 
    private String VIPNumber;

    private String memberRole;
    
    private String qualifiedFlag;
    
    private BigDecimal qualifiedAmt;
    
    private BigDecimal purchaseAmt;
    
    private String dateFrom;
    
    private String dateTo;
    
    private String currency;
    @JsonIgnore
    public String getVIPNumber() {
        return VIPNumber;
    }

    public void setVIPNumber(String vIPNumber) {
        VIPNumber = vIPNumber;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

    public String getQualifiedFlag() {
        return qualifiedFlag;
    }

    public void setQualifiedFlag(String qualifiedFlag) {
        this.qualifiedFlag = qualifiedFlag;
    }

    public BigDecimal getQualifiedAmt() {
        return qualifiedAmt;
    }

    public void setQualifiedAmt(BigDecimal qualifiedAmt) {
        this.qualifiedAmt = qualifiedAmt;
    }

    public BigDecimal getPurchaseAmt() {
        return purchaseAmt;
    }

    public void setPurchaseAmt(BigDecimal purchaseAmt) {
        this.purchaseAmt = purchaseAmt;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    
}
