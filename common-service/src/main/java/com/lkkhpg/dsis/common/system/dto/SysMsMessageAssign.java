/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.system.dto;

import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;

/**
 * 消息会员dto.
 * 
 * @author HuangJiaJing
 *
 */
public class SysMsMessageAssign extends BaseDTO {
    private Long assignId;

    public static final String MEM = "MEM";
    public static final String USER = "USER";

    private User user = new User();

    private String publishStatus;
    
    private String memberCode;
    
    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    private String areaPhoneUser;

    public void setUserId(Long userId) {
        user.setUserId(userId);
    }

    public void setUserName(String userName) {
        user.setUserName(userName);
    }

    private Member member = new Member();

    private Long msMessageId;
    // MEM:会员
    // USER:用户
    private String assignType;
    // 会员id;用户id
    private String assignValue;

    private Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getMemberCode() {
        return member.getMemberCode();
    }

    public void setMemberCode(String memberCode) {
        member.setMemberCode(memberCode);
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public String getUserName() {
        return user.getUserName();
    }

    public String getAreaPhone() {
        switch (getAssignType()) {
        case MEM:
            return member.getAreaPhone();
        case USER:
            return user.getAreaPhoneUser();
        default:
            break;
        }
        return "";
    }

    public void setAreaPhone(String areaPhone) {
        member.setAreaPhone(areaPhone);
        user.setAreaPhoneUser(areaPhone);
    }

    public Long getMemberId() {
        return member.getMemberId();
    }

    public void setMemberId(Long memberId) {
        member.setMemberId(memberId);
    }

    public String getStatus() {
        switch (getAssignType()) {
        case MEM:
            return member.getStatus();
        case USER:
            return user.getStatus();
        default:
            break;
        }
        return "";
    }

    public void setStatus(String status) {
        member.setStatus(status);
        user.setStatus(status);
    }

    public String getAreaCode() {
        switch (getAssignType()) {
        case MEM:
            return member.getAreaCode();
        case USER:
            return user.getAreaCode();
        default:
            break;
        }
        return "";
    }

    public void setAreaCode(String areaCode) {
        member.setAreaCode(areaCode);
        user.setAreaCode(areaCode);
    }

    public String getPhoneNo() {
        switch (getAssignType()) {
        case MEM:
            return member.getPhoneNo();
        case USER:
            return user.getPhoneNo();
        default:
            break;
        }
        return "";
    }

    public void setPhoneNo(String phoneNo) {
        member.setPhoneNo(phoneNo);
        user.setPhoneNo(phoneNo);
    }

    public String getEmail() {
        switch (getAssignType()) {
        case MEM:
            return member.getEmail();
        case USER:
            return user.getEmail();
        default:
            break;
        }
        return "";
    }

    public void setEmail(String email) {
        member.setEmail(email);
        user.setEmail(email);
    }

    public String getMemberName() {
        return member.getMemberName();
    }

    public void setMemberName(String memberName) {
        member.setMemberName(memberName);
    }

    public Long getAssignId() {
        return assignId;
    }

    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }

    public Long getMsMessageId() {
        return msMessageId;
    }

    public void setMsMessageId(Long msMessageId) {
        this.msMessageId = msMessageId;
    }

    public String getAssignType() {
        return assignType == null ? "" : assignType;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType == null ? null : assignType.trim();
    }

    public String getAssignValue() {
        return assignValue;
    }

    public void setAssignValue(String assignValue) {
        this.assignValue = assignValue == null ? null : assignValue.trim();
    }

    public String getMessageAddress(MessageTypeEnum type) {
        String address = null;
        switch (type) {
        case DSIS:
            return this.getAssignValue();
        case EMAIL:
            return this.getEmail();
        case SMS:
            // TODO
            return aP(getAreaCode(), getPhoneNo());
        default:
            break;
        }
        return address;
    }

    private String aP(String a, String p) {
        return a == null ? p : a + p;
    }

    public String getAreaPhoneUser() {
        return user.getAreaPhoneUser();
    }

    public void setAreaPhoneUser(String areaPhoneUser) {
        this.areaPhoneUser = areaPhoneUser;
    }

}