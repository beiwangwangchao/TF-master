/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.system.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 事件参与会员.
 * 
 * @author wangc
 *
 */
public class SysEventAssign extends BaseDTO {
    private Long eventAssignId;

    private Long eventId;

    private String assignType;

    private String assignValue;

    private BigDecimal attendance;

    private String memberCode;

    private String memberName;

    private String phoneNo;

    private String email;

    private String status;
    
    private Long memberId;
    
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEventAssignId() {
        return eventAssignId;
    }

    public void setEventAssignId(Long eventAssignId) {
        this.eventAssignId = eventAssignId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getAssignType() {
        return assignType;
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

    public BigDecimal getAttendance() {
        return attendance;
    }

    public void setAttendance(BigDecimal attendance) {
        this.attendance = attendance;
    }

}