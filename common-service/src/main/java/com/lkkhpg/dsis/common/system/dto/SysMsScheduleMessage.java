/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;

/**
 * @author shiliyan
 *
 */
public class SysMsScheduleMessage extends SysMsMessage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private SysMsMessageAssign sysMsMessageAssign = new SysMsMessageAssign();

    public String getMemberCode() {
        return sysMsMessageAssign.getMemberCode();
    }

    public void setMemberCode(String memberCode) {
        sysMsMessageAssign.setMemberCode(memberCode);
    }

    public String getAreaPhone() {
        return sysMsMessageAssign.getAreaPhone();
    }

    public void setAreaPhone(String areaPhone) {
        sysMsMessageAssign.setAreaPhone(areaPhone);
    }

    public String getAreaCode() {
        return sysMsMessageAssign.getAreaCode();
    }

    public void setAreaCode(String areaCode) {
        sysMsMessageAssign.setAreaCode(areaCode);
    }

    public String getAreaPhoneUser() {
        return sysMsMessageAssign.getAreaPhoneUser();
    }

    public void setAreaPhoneUser(String areaPhoneUser) {
        sysMsMessageAssign.setAreaPhoneUser(areaPhoneUser);
    }

    private String marketName;

    public Long getAccountId() {
        return getSysMsMessageAssign().getAccountId();
    }

    public void setAccountId(Long accountId) {
        getSysMsMessageAssign().setAccountId(accountId);
    }

    public Long getUserId() {
        return getSysMsMessageAssign().getUserId();
    }

    public void setUserId(Long userId) {
        getSysMsMessageAssign().setUserId(userId);
    }

    public String getUserName() {
        return getSysMsMessageAssign().getUserName();
    }

    public void setUserName(String userName) {
        getSysMsMessageAssign().setUserName(userName);
    }

    public String getMemberName() {
        return getSysMsMessageAssign().getMemberName();
    }

    public void setMemberName(String memberName) {
        getSysMsMessageAssign().setMemberName(memberName);
    }

    public String getMarketName() {
        return this.marketName;
        // return getSysMsMessageAssign().getMarketName();
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getEmail() {
        return getSysMsMessageAssign().getEmail();
    }

    public void setEmail(String email) {
        SysMsMessageAssign m = getSysMsMessageAssign();
        m.setEmail(email);
    }

    public String getPhoneNo() {
        return getSysMsMessageAssign().getPhoneNo();
    }

    public void setPhoneNo(String phoneNo) {
        SysMsMessageAssign m = getSysMsMessageAssign();
        m.setPhoneNo(phoneNo);
    }

    public String getStatus() {
        return getSysMsMessageAssign().getStatus();
    }

    public void setStatus(String status) {
        getSysMsMessageAssign().setStatus(status);
    }

    public Long getMemberId() {
        return getSysMsMessageAssign().getMemberId();
    }

    public void setMemberId(Long memberId) {
        getSysMsMessageAssign().setMemberId(memberId);
    }

    public Long getAssignId() {
        return getSysMsMessageAssign().getAssignId();
    }

    public void setAssignId(Long assignId) {
        getSysMsMessageAssign().setAssignId(assignId);
    }

    public Long getMsMessageId() {
        return getSysMsMessageAssign().getMsMessageId();
    }

    public void setMsMessageId(Long msMessageId) {
        getSysMsMessageAssign().setMsMessageId(msMessageId);
    }

    public String getAssignType() {
        return getSysMsMessageAssign().getAssignType();
    }

    public void setAssignType(String assignType) {
        getSysMsMessageAssign().setAssignType(assignType);
    }

    public String getAssignValue() {
        return getSysMsMessageAssign().getAssignValue();
    }

    public void setAssignValue(String assignValue) {
        getSysMsMessageAssign().setAssignValue(assignValue);
    }

    public String getPhone() {
        return getSysMsMessageAssign().getPhoneNo();
    }

    public void setPhone(String phone) {
        getSysMsMessageAssign().setPhoneNo(phone);
    }

    public String getMail() {
        return getSysMsMessageAssign().getEmail();
    }

    public void setMail(String mail) {
        getSysMsMessageAssign().setEmail(mail);
    }

    public String getMessageAddress(MessageTypeEnum type) {
        return getSysMsMessageAssign().getMessageAddress(type);
    }

    public String toString() {
        return getSysMsMessageAssign().toString();
    }

    public SysMsMessageAssign getSysMsMessageAssign() {
        return sysMsMessageAssign;
    }

    public void setSysMsMessageAssign(SysMsMessageAssign sysMsMessageAssign) {
        this.sysMsMessageAssign = sysMsMessageAssign;
    }

    public boolean equals(Object obj) {
        if (obj instanceof SysMsScheduleMessage) {
           return this.getMsMessageId().equals(((SysMsScheduleMessage) obj).getMsMessageId());
        }
        return getSysMsMessageAssign().equals(obj);
    }

    public int hashCode() {
        return getMsMessageId().hashCode();
    }

}
