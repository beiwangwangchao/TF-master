/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 会员详细信息数据返回DTO.
 * 
 * @author zyhe
 *
 */
public class OmkDealerDetallResponse {

    private String distributorId;

    private String sponsorId;

    private String regionCode;

    private String lastUpdateDate;

    private String typeEffectiveDate;

    private String contactTekeAreaCode;

    private String dealerType;

    private String typeInactiveDate;

    private String phoneNumber;

    private String fullName;

    private String certificateNo;

    private String certificateType;

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getTypeEffectiveDate() {
        return typeEffectiveDate;
    }

    public void setTypeEffectiveDate(String typeEffectiveDate) {
        this.typeEffectiveDate = typeEffectiveDate;
    }

    public String getContactTekeAreaCode() {
        return contactTekeAreaCode;
    }

    public void setContactTekeAreaCode(String contactTekeAreaCode) {
        this.contactTekeAreaCode = contactTekeAreaCode;
    }

    public String getDealerType() {
        return dealerType;
    }

    public void setDealerType(String dealerType) {
        this.dealerType = dealerType;
    }

    public String getTypeInactiveDate() {
        return typeInactiveDate;
    }

    public void setTypeInactiveDate(String typeInactiveDate) {
        this.typeInactiveDate = typeInactiveDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

}
