/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import javax.validation.constraints.NotNull;

/**
 * TeamStructure 参数DTO.
 * 
 * @author linyuheng
 */
public class OmkTeamStructureRequest {

    @NotNull
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
