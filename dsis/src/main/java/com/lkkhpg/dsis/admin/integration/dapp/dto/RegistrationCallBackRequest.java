/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import javax.validation.constraints.NotNull;

/**
 * POS注册回调请求DTO.
 * 
 * @author linyuheng
 *
 */
public class RegistrationCallBackRequest {

    @NotNull
    private String market;

    @NotNull
    private String distributorNumber;

    @NotNull
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
