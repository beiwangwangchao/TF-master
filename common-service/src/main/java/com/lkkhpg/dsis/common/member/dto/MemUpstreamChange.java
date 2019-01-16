/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员上线变更DTO.
 * 
 * @author linyuheng
 */
public class MemUpstreamChange extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long changeId;
    @NotNull
    private Long memberId;

    private Long fromUpMemberId;

    private Long toUpMemberId;

    private Date applyDate;

    private String approvalStatus;

    private Date approvalDate;
    @NotNull
    private String remark;

    private String adviseNo;

    private String appNo;

    private String synStatus;

    private String synMessage;

    private Date synDate;

    private String memberCode;

    private String memberName;

    private String chineseName;

    private String englishName;

    private java.util.Date beforeDate;

    private java.util.Date afterDate;

    private Long sponsorId;
    @NotNull
    private String fromUpMemberCode;
    @NotNull
    private String toUpMemberCode;

    private String marketName;

    private String fromOverseasSponsor;

    private String toOverseasSponsor;

    private Long marketId;

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getFromUpMemberCode() {
        return fromUpMemberCode;
    }

    public void setFromUpMemberCode(String fromUpMemberCode) {
        this.fromUpMemberCode = fromUpMemberCode;
    }

    public String getToUpMemberCode() {
        return toUpMemberCode;
    }

    public void setToUpMemberCode(String toUpstreamCode) {
        this.toUpMemberCode = toUpstreamCode;
    }

    public String getAdviseNo() {
        return adviseNo;
    }

    public void setAdviseNo(String adviseNo) {
        this.adviseNo = adviseNo;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getSynStatus() {
        return synStatus;
    }

    public void setSynStatus(String synStatus) {
        this.synStatus = synStatus;
    }

    public String getSynMessage() {
        return synMessage;
    }

    public void setSynMessage(String synMessage) {
        this.synMessage = synMessage;
    }

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public Long getFromUpMemberId() {
        return fromUpMemberId;
    }

    public void setFromUpMemberId(Long fromUpMemberId) {
        this.fromUpMemberId = fromUpMemberId;
    }

    public Long getToUpMemberId() {
        return toUpMemberId;
    }

    public void setToUpMemberId(Long toUpMemberId) {
        this.toUpMemberId = toUpMemberId;
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

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getFromOverseasSponsor() {
        return fromOverseasSponsor;
    }

    public void setFromOverseasSponsor(String fromOverseasSponsor) {
        this.fromOverseasSponsor = fromOverseasSponsor;
    }

    public String getToOverseasSponsor() {
        return toOverseasSponsor;
    }

    public void setToOverseasSponsor(String toOverseasSponsor) {
        this.toOverseasSponsor = toOverseasSponsor;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

}