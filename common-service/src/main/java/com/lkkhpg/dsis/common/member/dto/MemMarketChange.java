/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员市场变更DTO.
 * 
 * @author linyuheng
 */

public class MemMarketChange extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long changeId;
    @NotNull
    private Long memberId;
    @NotNull
    private Long fromMarketId;

    private Long toMarketId;

    private Date applyDate;

    private String approvalStatus;

    private Date approvalDate;

    @NotNull
    private String remark;

    private String memberName;

    private String chineseName;

    private String englishName;

    private java.util.Date beforeDate;

    private java.util.Date afterDate;

    private String phoneNumber;

    private String registerCode;
    
    private String memberCode;
    
    private String marketName;
    
    private String fromMarketName;
    
    private String toMarketName;
    
    private Long gdsId;
    
    private Long marketId;

    public String getFromMarketName() {
        return fromMarketName;
    }

    public void setFromMarketName(String fromMarketName) {
        this.fromMarketName = fromMarketName;
    }

    public String getToMarketName() {
        return toMarketName;
    }

    public void setToMarketName(String toMarketName) {
        this.toMarketName = toMarketName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public java.util.Date getBeforeDate() {
        return beforeDate;
    }

    public void setBeforeDate(java.util.Date beforeDate) {
        this.beforeDate = beforeDate;
    }

    public java.util.Date getAfterDate() {
        return afterDate;
    }

    public void setAfterDate(java.util.Date afterDate) {
        this.afterDate = afterDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getChangeId() {
        return changeId;
    }

    public void setChangeId(Long changeId) {
        this.changeId = changeId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getFromMarketId() {
        return fromMarketId;
    }

    public void setFromMarketId(Long fromMarketId) {
        this.fromMarketId = fromMarketId;
    }

    public Long getToMarketId() {
        return toMarketId;
    }

    public void setToMarketId(Long toMarketId) {
        this.toMarketId = toMarketId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus == null ? null : approvalStatus.trim();
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getGdsId() {
        return gdsId;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }
    
    
}