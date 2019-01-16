/*
 *
 */
package com.lkkhpg.dsis.common.config.dto;


import com.lkkhpg.dsis.platform.dto.BaseDTO;
/**
 * 服务中心分配会员dto.
 * 
 * @author wangc
 *
 */
public class SpmServiceCenterAssign extends BaseDTO {
    private Long assignId;

    private Long serviceCenterId;

    private Long memberId;
    
    private String memberCode;
    
    private String chineseName;
    
    private String englishName;
    
    private String phoneNo;
    
    private String email;
    
    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
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

    public Long getAssignId() {
        return assignId;
    }

    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }

    public Long getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(Long serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

}