/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 会员余额请求DTO.
 * 
 * @author zhenyang.he
 *
 */
public class OmkAcctBalanceRequest {
    
    private String month;

    private String language;

    private String distributorId;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

}
