/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员申请角色变更.
 * 
 * @author linyuheng
 */
public class MemApplyRole extends BaseDTO {

    /**
     * 序列化版本ID.
     */
    private static final long serialVersionUID = 1L;

    public Long applyId;

    public String applyNumber;

    public Date applyDate;

    public Long memberId;

    public Long marketId;

    public String status;

    public Date approveDate;

    public String oldRole;

    public String newRole;

    public Date approveDateFrom;

    public Date approveDateTo;
    
    public String memberCode;
    
    public String memberName;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getOldRole() {
        return oldRole;
    }

    public void setOldRole(String oldRole) {
        this.oldRole = oldRole;
    }

    public String getNewRole() {
        return newRole;
    }

    public void setNewRole(String newRole) {
        this.newRole = newRole;
    }

    public Date getApproveDateFrom() {
        return approveDateFrom;
    }

    public void setApproveDateFrom(Date approveDateFrom) {
        this.approveDateFrom = approveDateFrom;
    }

    public Date getApproveDateTo() {
        return approveDateTo;
    }

    public void setApproveDateTo(Date approveDateTo) {
        this.approveDateTo = approveDateTo;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

}
