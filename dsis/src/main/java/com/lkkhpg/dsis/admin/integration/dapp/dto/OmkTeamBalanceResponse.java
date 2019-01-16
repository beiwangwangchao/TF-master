/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.math.BigDecimal;

/**
 * 团队余额Response DTO.
 * 
 * @author zhenyang.he
 *
 */
public class OmkTeamBalanceResponse {

    private String distributorId;

    private BigDecimal localOV;

    private BigDecimal intlOV;

    private BigDecimal totalOV;

    private BigDecimal totalGV;

    private BigDecimal localGV;

    private BigDecimal intlGV;

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public BigDecimal getLocalOV() {
        return localOV;
    }

    public void setLocalOV(BigDecimal localOV) {
        this.localOV = localOV;
    }

    public BigDecimal getIntlOV() {
        return intlOV;
    }

    public void setIntlOV(BigDecimal intlOV) {
        this.intlOV = intlOV;
    }

    public BigDecimal getTotalOV() {
        return totalOV;
    }

    public void setTotalOV(BigDecimal totalOV) {
        this.totalOV = totalOV;
    }

    public BigDecimal getLocalGV() {
        return localGV;
    }

    public void setLocalGV(BigDecimal localGV) {
        this.localGV = localGV;
    }

    public BigDecimal getIntlGV() {
        return intlGV;
    }

    public void setIntlGV(BigDecimal intlGV) {
        this.intlGV = intlGV;
    }

    public BigDecimal getTotalGV() {
        return totalGV;
    }

    public void setTotalGV(BigDecimal totalGV) {
        this.totalGV = totalGV;
    }

}
