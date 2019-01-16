/*
 *
 */
package com.lkkhpg.dsis.mws.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * mws会员基本信息DTO.
 * 
 * @author guanghui.liu
 */
@AuditEnabled
@Table(name = "MM_MEMBER")
public class MemberInfo extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "member_id")
    private Long memberId;

    private BigDecimal salesPoint;

    private BigDecimal exchangeBalance;

    private BigDecimal remainingBalance;

    //updated by 11816 on 2018-01-22 ^^start
    private BigDecimal availableDiscount;

    private long discount  ;

    //updated by 11816 on 2018-01-22 $$end
    private BigDecimal currentPv;

    private String englishName;

    private String chineseName;

    @NotNull
    private String gender;

    @NotNull
    private Date dob;

    @NotNull
    private String areaCode;

    @NotNull
    private String otherAreaCode;

    @NotNull
    private String phoneNo;

    private String othPhoneNo;

    @NotNull
    private String email;

    @Children
    private List<MemSite> memSites;

    public List<MemSite> getMemSites() {
        return memSites;
    }

    public void setMemSites(List<MemSite> memSites) {
        this.memSites = memSites;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName == null ? null : chineseName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    public String getOthPhoneNo() {
        return othPhoneNo;
    }

    public void setOthPhoneNo(String othPhoneNo) {
        this.othPhoneNo = othPhoneNo == null ? null : othPhoneNo.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public BigDecimal getSalesPoint() {
        return salesPoint;
    }

    public void setSalesPoint(BigDecimal salesPoint) {
        this.salesPoint = salesPoint;
    }

    public BigDecimal getExchangeBalance() {
        return exchangeBalance;
    }

    public void setExchangeBalance(BigDecimal exchangeBalance) {
        this.exchangeBalance = exchangeBalance;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public BigDecimal getCurrentPv() {
        return currentPv;
    }

    public void setCurrentPv(BigDecimal currentPv) {
        this.currentPv = currentPv;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getOtherAreaCode() {
        return otherAreaCode;
    }

    public void setOtherAreaCode(String otherAreaCode) {
        this.otherAreaCode = otherAreaCode;
    }

    public BigDecimal getAvailableDiscount() {
        return availableDiscount;
    }

    public void setAvailableDiscount(BigDecimal availableDiscount) {
        this.availableDiscount = availableDiscount;
    }


    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }
}
