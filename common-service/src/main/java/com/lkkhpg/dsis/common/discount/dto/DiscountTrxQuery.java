package com.lkkhpg.dsis.common.discount.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Project: pos2
 * Package: com.lkkhpg.dsis.common.discount.dto
 * User: 11816
 * Date: 2018/1/18
 * Time: 18:58
 */

public class DiscountTrxQuery extends BaseDTO {

    private Long discountTrxLineId;

    private Long memberId;

    private String memberName;

    private String memberCode;

    private BigDecimal processAmt;

    private String adjustType;

    private String adjustReason;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date processDate;

    private String sourceTrxNum;

    private List<String> reasonList;

    private Long salesOrgId;

    private String remark;



    private String discountTrxNum;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date processDateFrom;
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date processDateTo;

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

    public BigDecimal getProcessAmt() {
        return processAmt;
    }

    public void setProcessAmt(BigDecimal processAmt) {
        this.processAmt = processAmt;
    }

    public String getAdjustType() {
        return adjustType;
    }

    public void setAdjustType(String adjustType) {
        this.adjustType = adjustType;
    }

    public String getAdjustReason() {
        return adjustReason;
    }

    public void setAdjustReason(String adjustReason) {
        this.adjustReason = adjustReason;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getSourceTrxNum() {
        return sourceTrxNum;
    }

    public void setSourceTrxNum(String sourceTrxNum) {
        this.sourceTrxNum = sourceTrxNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getDiscountTrxNum() {
        return discountTrxNum;
    }

    public void setDiscountTrxNum(String discountTrxNum) {
        this.discountTrxNum = discountTrxNum;
    }

    public Date getProcessDateFrom() {
        return processDateFrom;
    }

    public void setProcessDateFrom(Date processDateFrom) {
        this.processDateFrom = processDateFrom;
    }

    public Date getProcessDateTo() {
        return processDateTo;
    }

    public void setProcessDateTo(Date processDateTo) {
        this.processDateTo = processDateTo;
    }

    public List<String> getReasonList() {
        return reasonList;
    }

    public void setReasonList(List<String> reasonList) {
        this.reasonList = reasonList;
    }

    @Override
    public String toString() {
        return "DiscountTrxQuery{" +
                "discountTrxLineId=" + discountTrxLineId +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", processAmt='" + processAmt + '\'' +
                ", adjustType='" + adjustType + '\'' +
                ", adjustReason='" + adjustReason + '\'' +
                ", processDate=" + processDate +
                ", sourceTrxNum='" + sourceTrxNum + '\'' +
                ", salesOrgId=" + salesOrgId +
                ", remark='" + remark + '\'' +
                ", discountTrxNum='" + discountTrxNum + '\'' +
                ", processDateFrom=" + processDateFrom +
                ", processDateTo=" + processDateTo +
                '}';
    }
}
