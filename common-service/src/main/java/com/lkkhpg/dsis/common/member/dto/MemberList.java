/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;
 
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 导入会员列表Dto.
 * 
 * @author mclin
 */
public class MemberList extends BaseDTO {

    private Long uniqueId;

    private Long memberId;

    private String memberCode;
    
    private Long groupId;
    
    private String groupType;
    
    private String validateFlag;
    
    private String reasonCode;

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

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
        this.memberCode = memberCode == null ? null : memberCode.trim();
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getValidateFlag() {
        return validateFlag;
    }

    public void setValidateFlag(String validateFlag) {
        this.validateFlag = validateFlag;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
}