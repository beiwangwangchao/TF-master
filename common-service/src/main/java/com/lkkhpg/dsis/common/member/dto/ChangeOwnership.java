/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.util.Date;

/**
 * 会员DTO.
 * 
 * @author frank.li
 */
public class ChangeOwnership {

    private Long sourceMemberId;

    private Long tempMemberId;

    private String sourceMemberCode;

    private String tempMemberCode;

    private Long oldMemberId;

    private Long newMemberId;

    private String newStatus;

    private Date terminationDate;

    private Long sponsorId;

    private String sponsorNo;

    public Long getSourceMemberId() {
        return sourceMemberId;
    }

    public void setSourceMemberId(Long sourceMemberId) {
        this.sourceMemberId = sourceMemberId;
    }

    public Long getTempMemberId() {
        return tempMemberId;
    }

    public void setTempMemberId(Long tempMemberId) {
        this.tempMemberId = tempMemberId;
    }

    public Long getOldMemberId() {
        return oldMemberId;
    }

    public void setOldMemberId(Long oldMemberId) {
        this.oldMemberId = oldMemberId;
    }

    public Long getNewMemberId() {
        return newMemberId;
    }

    public void setNewMemberId(Long newMemberId) {
        this.newMemberId = newMemberId;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorNo() {
        return sponsorNo;
    }

    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
    }

    public String getSourceMemberCode() {
        return sourceMemberCode;
    }

    public void setSourceMemberCode(String sourceMemberCode) {
        this.sourceMemberCode = sourceMemberCode;
    }

    public String getTempMemberCode() {
        return tempMemberCode;
    }

    public void setTempMemberCode(String tempMemberCode) {
        this.tempMemberCode = tempMemberCode;
    }

}
