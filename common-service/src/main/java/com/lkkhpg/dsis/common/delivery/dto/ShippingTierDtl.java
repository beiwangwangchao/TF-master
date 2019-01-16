/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 承运商运费.
 * 
 * @author RUNBAI.CHEN
 */
public class ShippingTierDtl extends BaseDTO {

    private static final long serialVersionUID = 2749258302088236708L;

    private Long tierDtlId;

    private Long shippingTierId;

    private String currencyCode;
    @NotNull
    private BigDecimal invAmountFrom;
    @NotNull
    private BigDecimal invAmountTo;
    @NotNull
    private BigDecimal shippingFee;
    
    private String dsis;
    
    private String agencyWeb;
    
    private String agencyApp;

    public Long getTierDtlId() {
        return tierDtlId;
    }

    public void setTierDtlId(Long tierDtlId) {
        this.tierDtlId = tierDtlId;
    }

    public Long getShippingTierId() {
        return shippingTierId;
    }

    public void setShippingTierId(Long shippingTierId) {
        this.shippingTierId = shippingTierId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public BigDecimal getInvAmountFrom() {
        return invAmountFrom;
    }

    public void setInvAmountFrom(BigDecimal invAmountFrom) {
        this.invAmountFrom = invAmountFrom;
    }

    public BigDecimal getInvAmountTo() {
        return invAmountTo;
    }

    public void setInvAmountTo(BigDecimal invAmountTo) {
        this.invAmountTo = invAmountTo;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getDsis() {
        return dsis;
    }

    public void setDsis(String dsis) {
        this.dsis = dsis;
    }

    public String getAgencyWeb() {
        return agencyWeb;
    }

    public void setAgencyWeb(String agencyWeb) {
        this.agencyWeb = agencyWeb;
    }

    public String getAgencyApp() {
        return agencyApp;
    }

    public void setAgencyApp(String agencyApp) {
        this.agencyApp = agencyApp;
    }
    
}