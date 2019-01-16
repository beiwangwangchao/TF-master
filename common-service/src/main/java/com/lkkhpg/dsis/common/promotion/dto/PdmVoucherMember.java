/*
 *
 */
package com.lkkhpg.dsis.common.promotion.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;
/**
 * 优惠券会员DTO.
 * @author hanrui.huang
 *
 */
public class PdmVoucherMember extends BaseDTO {
    private Long voucherMemberId;

    private Long voucherId;

    private Long memberId;
    
    private String memberCode;

    private String phoneNo;

    private String englishName;

    private String chineseName;

    public Long getVoucherMemberId() {
        return voucherMemberId;
    }

    public void setVoucherMemberId(Long voucherMemberId) {
        this.voucherMemberId = voucherMemberId;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
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
        this.memberCode = memberCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }
}