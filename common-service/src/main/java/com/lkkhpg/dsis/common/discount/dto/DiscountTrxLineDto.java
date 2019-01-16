package com.lkkhpg.dsis.common.discount.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "TF_DISCOUNT_TRX_LINE")
public class DiscountTrxLineDto extends BaseDTO {
    private Long discountTrxLineId;

    private long memberId;

    private String memberName;

    private Long discountTrxHeadId;

    private BigDecimal discountAdjustAmt;

    private String remark;

    private String chineseFirstName;

    private String  memberCode;

    private BigDecimal discountAmt;

    private String attribute1;

    @Override
    public String getAttribute1() {
        return attribute1;
    }

    @Override
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public Long getDiscountTrxLineId() {
        return discountTrxLineId;
    }

    public void setDiscountTrxLineId(Long discountTrxLineId) {
        this.discountTrxLineId = discountTrxLineId;
    }

    public long getMemberId() {
        return memberId;
    }

    public Long getDiscountTrxHeadId() {
        return discountTrxHeadId;
    }

    public void setDiscountTrxHeadId(Long discountTrxHeadId) {
        this.discountTrxHeadId = discountTrxHeadId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public BigDecimal getDiscountAdjustAmt() {
        return discountAdjustAmt;
    }

    public void setDiscountAdjustAmt(BigDecimal discountAdjustAmt) {
        this.discountAdjustAmt = discountAdjustAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getChineseFirstName() {
        return chineseFirstName;
    }

    public void setChineseFirstName(String chineseFirstName) {
        this.chineseFirstName = chineseFirstName;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    @Override
    public String toString() {
        return "DiscountTrxLineDto{" +
                "discountTrxLineId='" + discountTrxLineId + '\'' +
                ", memberId=" + memberId +
                ", discountTrxHeadId='" + discountTrxHeadId + '\'' +
                ", discountAdjustAmt='" + discountAdjustAmt + '\'' +
                '}';
    }
}
