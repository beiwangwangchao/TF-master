/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import javax.validation.constraints.NotNull;

/**
 * 送货地址同步接口请求DTO.
 * 
 * @author fengwanjun
 */
public class GetAddressRequest {

    @NotNull
    private String saleOrganization;

    @NotNull
    private String market;

    @NotNull
    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getSaleOrganization() {
        return saleOrganization;
    }

    public void setSaleOrganization(String saleOrganization) {
        this.saleOrganization = saleOrganization;
    }
}