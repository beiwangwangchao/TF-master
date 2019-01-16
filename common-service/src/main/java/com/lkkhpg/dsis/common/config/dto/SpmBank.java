/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 银行详情dto.
 * @author liuxuan
 *
 */
@MultiLanguage
@Table(name = "SPM_BANK_B")
public class SpmBank extends BaseDTO {
    @Id
    @Column(name = "BANK_ID")
    private Long bankId;

    private Long marketId;
    
    private String marketName;

    private String bankNumber;
    
    @MultiLanguageField
    @Column(name = "BANK_NAME")
    private String bankName;

    private String bankType;

    private Long headOfficeId;
    
    private String headOfficeName;

    private String contacts;

    private String email;

    private String phone;

    private String enabledFlag;

    private String address;
    
    @MultiLanguageField
    @Column(name = "REMARK")
    private String remark;

    private Date startActiveDate;

    private Date endActiveDate;
    
    private Date startDate;
    
    private Date overDate;
    
    @Children
    private List<SpmBankCharges> spmBankChargess;
   
    @MultiLanguageField
    @Column(name = "BANK_SHORT_NAME")
    private String bankShortName;
    
    private String areaCode;
    
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }

    public List<SpmBankCharges> getSpmBankChargess() {
        return spmBankChargess;
    }

    public void setSpmBankChargess(List<SpmBankCharges> spmBankChargess) {
        this.spmBankChargess = spmBankChargess;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getHeadOfficeName() {
        return headOfficeName;
    }

    public void setHeadOfficeName(String headOfficeName) {
        this.headOfficeName = headOfficeName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getOverDate() {
        return overDate;
    }

    public void setOverDate(Date overDate) {
        this.overDate = overDate;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber == null ? null : bankNumber.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType == null ? null : bankType.trim();
    }

    public Long getHeadOfficeId() {
        return headOfficeId;
    }

    public void setHeadOfficeId(Long headOfficeId) {
        this.headOfficeId = headOfficeId;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }
    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }
}