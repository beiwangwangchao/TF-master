/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 推荐人鉴别DAPP接口响应数据.
 * 
 * @author linyuheng
 */
public class SponsorVerificationResponse {

    private String sponsorID;

    private String result;

    private String sponsorNameEng;

    private String sponsorNameChi;

    private String sponsorMarket;

    private String sponsorMemberRole;

    public String getSponsorID() {
        return sponsorID;
    }

    public void setSponsorID(String sponsorID) {
        this.sponsorID = sponsorID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSponsorNameEng() {
        return sponsorNameEng;
    }

    public void setSponsorNameEng(String sponsorNameEng) {
        this.sponsorNameEng = sponsorNameEng;
    }

    public String getSponsorNameChi() {
        return sponsorNameChi;
    }

    public void setSponsorNameChi(String sponsorNameChi) {
        this.sponsorNameChi = sponsorNameChi;
    }

    public String getSponsorMarket() {
        return sponsorMarket;
    }

    public void setSponsorMarket(String sponsorMarket) {
        this.sponsorMarket = sponsorMarket;
    }

    public String getSponsorMemberRole() {
        return sponsorMemberRole;
    }

    public void setSponsorMemberRole(String sponsorMemberRole) {
        this.sponsorMemberRole = sponsorMemberRole;
    }

}
