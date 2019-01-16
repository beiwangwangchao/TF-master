/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

public class Sponsor extends BaseDTO {
    private String sponsorType;

    private Long memberId;

    private String sponsorNo;

    private String sponsorChineseName;

    private String sponsorEnglishName;

    private String sponsorName;

    private String postCode;

    private String taxCustCode;

    private String saleBranchNo;

    private Long marketId;

    private String marketCode;

    private String status;

    public String getSponsorType() {
        return sponsorType;
    }

    public void setSponsorType(String sponsorType) {
        this.sponsorType = sponsorType == null ? null : sponsorType.trim();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getSponsorNo() {
        return sponsorNo;
    }

    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo == null ? null : sponsorNo.trim();
    }

    public String getSponsorChineseName() {
        return sponsorChineseName;
    }

    public void setSponsorChineseName(String sponsorChineseName) {
        this.sponsorChineseName = sponsorChineseName == null ? null : sponsorChineseName.trim();
    }

    public String getSponsorEnglishName() {
        return sponsorEnglishName;
    }

    public void setSponsorEnglishName(String sponsorEnglishName) {
        this.sponsorEnglishName = sponsorEnglishName == null ? null : sponsorEnglishName.trim();
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName == null ? null : sponsorName.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getTaxCustCode() {
        return taxCustCode;
    }

    public void setTaxCustCode(String taxCustCode) {
        this.taxCustCode = taxCustCode == null ? null : taxCustCode.trim();
    }

    public String getSaleBranchNo() {
        return saleBranchNo;
    }

    public void setSaleBranchNo(String saleBranchNo) {
        this.saleBranchNo = saleBranchNo == null ? null : saleBranchNo.trim();
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode == null ? null : marketCode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}