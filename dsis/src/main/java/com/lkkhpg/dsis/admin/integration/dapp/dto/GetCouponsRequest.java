package com.lkkhpg.dsis.admin.integration.dapp.dto;

import javax.validation.constraints.NotNull;

/** 
* @author songyuanhuang
* 获取优惠券请求DTO
*/
public class GetCouponsRequest {
    
    private String distributorNumber;
    @NotNull
    private String market;
    private String couponCodes;

    public String getDistributorNumber() {
        return distributorNumber;
    }

    public void setDistributorNumber(String distributorNumber) {
        this.distributorNumber = distributorNumber;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getCouponCodes() {
        return couponCodes;
    }

    public void setCouponCodes(String couponCodes) {
        this.couponCodes = couponCodes;
    }

   

}
