/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 推荐人鉴别DAPP接口请求数据.
 * 
 * @author linyuheng
 */
public class SponsorVerificationRequest {

    @NotEmpty
    private String sponsorID;

    @NotEmpty
    private String market;

    private String memberRole;

    public String getSponsorID() {
        return sponsorID;
    }

    public void setSponsorID(String sponsorID) {
        this.sponsorID = sponsorID;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

}
