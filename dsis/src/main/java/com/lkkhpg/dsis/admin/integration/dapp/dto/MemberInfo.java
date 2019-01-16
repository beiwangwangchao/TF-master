/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 订单callback参数 - 会员信息DTO.
 * 
 * @author shenqb
 */
public class MemberInfo {
    private String memberID;
    private String memberType;
    private String market;
    private String areaCode;
    private String mobileNumber;
    private String email;
    private String memberRole;
    public String getMemberID() {
        return memberID;
    }
    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }
    public String getMemberType() {
        return memberType;
    }
    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }
    public String getMarket() {
        return market;
    }
    public void setMarket(String market) {
        this.market = market;
    }
    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMemberRole() {
        return memberRole;
    }
    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }
    
    
}
