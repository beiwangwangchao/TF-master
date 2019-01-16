/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.util.List;

/**
 * TeamStructure 返回DTO.
 * 
 * @author linyuheng
 */
public class OmkTeamStructureResponse {

    private List<OmkTeamStructureRegion> region;

    private String cnt500K;

    private String cnt100K;

    private String cnt10K;

    private String total;

    private String distributorID;

    private String totalNew;

    public List<OmkTeamStructureRegion> getRegion() {
        return region;
    }

    public void setRegion(List<OmkTeamStructureRegion> region) {
        this.region = region;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDistributorID() {
        return distributorID;
    }

    public void setDistributorID(String distributorID) {
        this.distributorID = distributorID;
    }

    public String getTotalNew() {
        return totalNew;
    }

    public void setTotalNew(String totalNew) {
        this.totalNew = totalNew;
    }

    public String getCnt500K() {
        return cnt500K;
    }

    public void setCnt500K(String cnt500k) {
        cnt500K = cnt500k;
    }

    public String getCnt100K() {
        return cnt100K;
    }

    public void setCnt100K(String cnt100k) {
        cnt100K = cnt100k;
    }

    public String getCnt10K() {
        return cnt10K;
    }

    public void setCnt10K(String cnt10k) {
        cnt10K = cnt10k;
    }
    
}
