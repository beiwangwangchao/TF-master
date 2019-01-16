/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 团队余额RequestDTO.
 * 
 * @author zhenyang.he
 *
 */
public class OmkTeamBalanceRequest {

    private String distributorId;

    private String period;

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

}
