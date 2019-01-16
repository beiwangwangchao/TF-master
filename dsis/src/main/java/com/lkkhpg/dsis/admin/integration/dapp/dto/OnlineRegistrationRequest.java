/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 线上入会请求数据DTO.
 * 
 * @author linyuheng
 */
public class OnlineRegistrationRequest {

    @NotEmpty
    private String appNo;

    @NotEmpty
    private String market;

    @NotEmpty
    private String sponsorId;

    private String firstNameChn;

    private String lastNameChn;

    private String firstNameEng;

    private String lastNameEng;

    @NotEmpty
    private String memberRole;

    @NotEmpty
    private String memberType;

    @NotEmpty
    private String idType;

    @NotEmpty
    private String idNumber;

    @NotEmpty
    private String birthday;

    @NotEmpty
    private String gender;

    private String companyName;

    private String companyIdNumber;

    private String language;

    private String education;

    private String race;

    private String nationality;

    @NotEmpty
    private String perCountry;

    @NotEmpty
    private String perState;

    @NotEmpty
    private String perCity;

    @NotEmpty
    private String perAddress;

    private String perAddress2;

    private String perAddress3;

    private String perZipCode;

    @NotEmpty
    private String curCountry;

    @NotEmpty
    private String curState;

    @NotEmpty
    private String curCity;

    @NotEmpty
    private String curAddress;

    private String curAddress2;

    private String curAddress3;

    private String curZipCode;

    private String phoneNumberAreaCode;

    private String phoneNumber;

    @NotEmpty
    private String mobileAreaCode;

    @NotEmpty
    private String mobileNumber;

    private String emailAddress;

    private String payeeName;

    private String bankCode;

    private String bankAccount;

    private String branchCode;

    private String bankAccountIdNumber;

    private String bonusPaymentMethod;

    private String spouseFirstNameChn;

    private String spouseLastNameChn;

    private String spouseFirstNameEng;

    private String spouseLastNameEng;

    private String spouseIDType;

    private String spouseID;

    private String spouseBirthday;

    private String beneficiaryFirstNameChn;

    private String beneficiaryLastNameChn;

    private String beneficiaryFirstNameEng;

    private String beneficiaryLastNameEng;

    private String beneficiaryIDType;

    private String beneficiaryID;

    private String beneficiaryBirthday;

    private String beneficiaryRelationship;

    private String travelPlanMonth;

    private String promotionOptIn;

    private String registerationChannel;
    
    private String bankAccountRemark;

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getFirstNameChn() {
        return firstNameChn;
    }

    public void setFirstNameChn(String firstNameChn) {
        this.firstNameChn = firstNameChn;
    }

    public String getLastNameChn() {
        return lastNameChn;
    }

    public void setLastNameChn(String lastNameChn) {
        this.lastNameChn = lastNameChn;
    }

    public String getFirstNameEng() {
        return firstNameEng;
    }

    public void setFirstNameEng(String firstNameEng) {
        this.firstNameEng = firstNameEng;
    }

    public String getLastNameEng() {
        return lastNameEng;
    }

    public void setLastNameEng(String lastNameEng) {
        this.lastNameEng = lastNameEng;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyIdNumber() {
        return companyIdNumber;
    }

    public void setCompanyIdNumber(String companyIdNumber) {
        this.companyIdNumber = companyIdNumber;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPerState() {
        return perState;
    }

    public void setPerState(String perState) {
        this.perState = perState;
    }

    public String getPerCity() {
        return perCity;
    }

    public void setPerCity(String perCity) {
        this.perCity = perCity;
    }

    public String getPerAddress() {
        return perAddress;
    }

    public void setPerAddress(String perAddress) {
        this.perAddress = perAddress;
    }

    public String getPerAddress2() {
        return perAddress2;
    }

    public void setPerAddress2(String perAddress2) {
        this.perAddress2 = perAddress2;
    }

    public String getPerZipCode() {
        return perZipCode;
    }

    public void setPerZipCode(String perZipCode) {
        this.perZipCode = perZipCode;
    }

    public String getCurState() {
        return curState;
    }

    public void setCurState(String curState) {
        this.curState = curState;
    }

    public String getCurCity() {
        return curCity;
    }

    public void setCurCity(String curCity) {
        this.curCity = curCity;
    }

    public String getCurAddress() {
        return curAddress;
    }

    public void setCurAddress(String curAddress) {
        this.curAddress = curAddress;
    }

    public String getCurAddress2() {
        return curAddress2;
    }

    public void setCurAddress2(String curAddress2) {
        this.curAddress2 = curAddress2;
    }

    public String getCurZipCode() {
        return curZipCode;
    }

    public void setCurZipCode(String curZipCode) {
        this.curZipCode = curZipCode;
    }

    public String getPhoneNumberAreaCode() {
        return phoneNumberAreaCode;
    }

    public void setPhoneNumberAreaCode(String phoneNumberAreaCode) {
        this.phoneNumberAreaCode = phoneNumberAreaCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileAreaCode() {
        return mobileAreaCode;
    }

    public void setMobileAreaCode(String mobileAreaCode) {
        this.mobileAreaCode = mobileAreaCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAccountIdNumber() {
        return bankAccountIdNumber;
    }

    public void setBankAccountIdNumber(String bankAccountIdNumber) {
        this.bankAccountIdNumber = bankAccountIdNumber;
    }

    public String getSpouseIDType() {
        return spouseIDType;
    }

    public void setSpouseIDType(String spouseIDType) {
        this.spouseIDType = spouseIDType;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public String getSpouseBirthday() {
        return spouseBirthday;
    }

    public void setSpouseBirthday(String spouseBirthday) {
        this.spouseBirthday = spouseBirthday;
    }

    public String getBeneficiaryIDType() {
        return beneficiaryIDType;
    }

    public void setBeneficiaryIDType(String beneficiaryIDType) {
        this.beneficiaryIDType = beneficiaryIDType;
    }

    public String getBeneficiaryID() {
        return beneficiaryID;
    }

    public void setBeneficiaryID(String beneficiaryID) {
        this.beneficiaryID = beneficiaryID;
    }

    public String getBeneficiaryBirthday() {
        return beneficiaryBirthday;
    }

    public void setBeneficiaryBirthday(String beneficiaryBirthday) {
        this.beneficiaryBirthday = beneficiaryBirthday;
    }

    public String getBeneficiaryRelationship() {
        return beneficiaryRelationship;
    }

    public void setBeneficiaryRelationship(String beneficiaryRelationship) {
        this.beneficiaryRelationship = beneficiaryRelationship;
    }

    public String getTravelPlanMonth() {
        return travelPlanMonth;
    }

    public void setTravelPlanMonth(String travelPlanMonth) {
        this.travelPlanMonth = travelPlanMonth;
    }

    public String getPerCountry() {
        return perCountry;
    }

    public void setPerCountry(String perCountry) {
        this.perCountry = perCountry;
    }

    public String getPerAddress3() {
        return perAddress3;
    }

    public void setPerAddress3(String perAddress3) {
        this.perAddress3 = perAddress3;
    }

    public String getCurCountry() {
        return curCountry;
    }

    public void setCurCountry(String curCountry) {
        this.curCountry = curCountry;
    }

    public String getCurAddress3() {
        return curAddress3;
    }

    public void setCurAddress3(String curAddress3) {
        this.curAddress3 = curAddress3;
    }

    public String getBonusPaymentMethod() {
        return bonusPaymentMethod;
    }

    public void setBonusPaymentMethod(String bonusPaymentMethod) {
        this.bonusPaymentMethod = bonusPaymentMethod;
    }

    public String getSpouseFirstNameChn() {
        return spouseFirstNameChn;
    }

    public void setSpouseFirstNameChn(String spouseFirstNameChn) {
        this.spouseFirstNameChn = spouseFirstNameChn;
    }

    public String getSpouseLastNameChn() {
        return spouseLastNameChn;
    }

    public void setSpouseLastNameChn(String spouseLastNameChn) {
        this.spouseLastNameChn = spouseLastNameChn;
    }

    public String getSpouseFirstNameEng() {
        return spouseFirstNameEng;
    }

    public void setSpouseFirstNameEng(String spouseFirstNameEng) {
        this.spouseFirstNameEng = spouseFirstNameEng;
    }

    public String getSpouseLastNameEng() {
        return spouseLastNameEng;
    }

    public void setSpouseLastNameEng(String spouseLastNameEng) {
        this.spouseLastNameEng = spouseLastNameEng;
    }

    public String getBeneficiaryFirstNameChn() {
        return beneficiaryFirstNameChn;
    }

    public void setBeneficiaryFirstNameChn(String beneficiaryFirstNameChn) {
        this.beneficiaryFirstNameChn = beneficiaryFirstNameChn;
    }

    public String getBeneficiaryLastNameChn() {
        return beneficiaryLastNameChn;
    }

    public void setBeneficiaryLastNameChn(String beneficiaryLastNameChn) {
        this.beneficiaryLastNameChn = beneficiaryLastNameChn;
    }

    public String getBeneficiaryFirstNameEng() {
        return beneficiaryFirstNameEng;
    }

    public void setBeneficiaryFirstNameEng(String beneficiaryFirstNameEng) {
        this.beneficiaryFirstNameEng = beneficiaryFirstNameEng;
    }

    public String getBeneficiaryLastNameEng() {
        return beneficiaryLastNameEng;
    }

    public void setBeneficiaryLastNameEng(String beneficiaryLastNameEng) {
        this.beneficiaryLastNameEng = beneficiaryLastNameEng;
    }

    public String getPromotionOptIn() {
        return promotionOptIn;
    }

    public void setPromotionOptIn(String promotionOptIn) {
        this.promotionOptIn = promotionOptIn;
    }

    public String getRegisterationChannel() {
        return registerationChannel;
    }

    public void setRegisterationChannel(String registerationChannel) {
        this.registerationChannel = registerationChannel;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBankAccountRemark() {
        return bankAccountRemark;
    }

    public void setBankAccountRemark(String bankAccountRemark) {
        this.bankAccountRemark = bankAccountRemark;
    }

    
}
