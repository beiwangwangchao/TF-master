package com.lkkhpg.dsis.common.discount.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Table(name = "TF_DISCOUNT_TX_HEAD")
public class DiscountTrxHeadDto extends BaseDTO {

    @Column(name = "discountTrxHeadId")
    private Long discountTrxHeadId;

    @Column(name = "discount_trx_num")
    private String discountTrxNum;

    @Column(name = "sales_org_id")
    private Long salesOrgId;

    @Column(name = "discount_trx_number")
    private Long discountTrxNumber;

    @NotNull
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date processDate;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date creationDate;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date processDateFrom;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date processDateTo;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date creationDateFrom;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date creationDateTo;

    private String adjustType;


    private String adjustReason;

    private String status;

    private String remark;

    private List<String> reasonList;

    @Children
    private List<DiscountTrxLineDto> discountTrxLineDto;

    private String attribute1;

    private String  memberCode;

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    @Override
    public String getAttribute1() {
        return attribute1;
    }

    @Override
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getDiscountTrxNum() {
        return discountTrxNum;
    }

    public void setDiscountTrxNum(String discountTrxNum) {
        this.discountTrxNum = discountTrxNum;
    }

    public Long getDiscountTrxHeadId() {
        return discountTrxHeadId;
    }

    public void setDiscountTrxHeadId(Long discountTrxHeadId) {
        this.discountTrxHeadId = discountTrxHeadId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public Long getDiscountTrxNumber() {
        return discountTrxNumber;
    }

    public void setDiscountTrxNumber(Long discountTrxNumber) {
        this.discountTrxNumber = discountTrxNumber;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DiscountTrxLineDto> getDiscountTrxLineDto() {
        return discountTrxLineDto;
    }

    public void setDiscountTrxLineDto(List<DiscountTrxLineDto> discountTrxLineDto) {
        this.discountTrxLineDto = discountTrxLineDto;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getCreationDateFrom() {
        return creationDateFrom;
    }

    public void setCreationDateFrom(Date creationDateFrom) {
        this.creationDateFrom = creationDateFrom;
    }

    public Date getCreationDateTo() {
        return creationDateTo;
    }

    public void setCreationDateTo(Date creationDateTo) {
        this.creationDateTo = creationDateTo;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "DiscountTrxHeadDto{" +
                "discountTrxHeadId=" + discountTrxHeadId +
                ", discountTrxNum='" + discountTrxNum + '\'' +
                ", salesOrgId=" + salesOrgId +
                ", discountTrxNumber=" + discountTrxNumber +
                ", processDate=" + processDate +
                ", processDateFrom=" + processDateFrom +
                ", processDateTo=" + processDateTo +
                ", adjustType='" + adjustType + '\'' +
                ", adjustReason='" + adjustReason + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", discountTrxLineDto=" + discountTrxLineDto +
                '}';
    }
}
