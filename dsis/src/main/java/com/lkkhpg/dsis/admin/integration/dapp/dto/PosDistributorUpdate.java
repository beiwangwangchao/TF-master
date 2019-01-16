/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * POS 會員更新回調 DTO.
 * 
 * @author zhenyang.he
 *
 */

public class PosDistributorUpdate {

    private String market;

    private String distributorNumber;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getDistributorNumber() {
        return distributorNumber;
    }

    public void setDistributorNumber(String distributorNumber) {
        this.distributorNumber = distributorNumber;
    }

}
