/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员资料同步DTO.
 * @author linyuheng
 *
 */
public class IsgMemberSync extends BaseDTO {
    private Long interfaceId;

    private String operationType;

    private String dealerNo;

    private String dealerType;

    private String dealerSubType;

    private String dealerPostCode;

    private String sponsorNo;

    private String dealerName;

    private String registerSpouse;

    private Date typeEffectiveDate;

    private Date typeInactiveDate;

    private Date postEffectiveDate;

    private Date postInactiveDate;

    private String saleOrgCode;

    private String saleBranchNo;

    private String saleZoneNo;

    private String appPeriod;

    private String appFirstSoNo;

    private String appForInternational;

    private String sex;

    private String englishName;

    private String comments;

    private String opLockStatus;

    private String taxCustCode;

    private String adviseNo;

    private String processStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date processDate;

    private String processMessage;

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo == null ? null : dealerNo.trim();
    }

    public String getDealerType() {
        return dealerType;
    }

    public void setDealerType(String dealerType) {
        this.dealerType = dealerType == null ? null : dealerType.trim();
    }

    public String getDealerSubType() {
        return dealerSubType;
    }

    public void setDealerSubType(String dealerSubType) {
        this.dealerSubType = dealerSubType == null ? null : dealerSubType.trim();
    }

    public String getDealerPostCode() {
        return dealerPostCode;
    }

    public void setDealerPostCode(String dealerPostCode) {
        this.dealerPostCode = dealerPostCode == null ? null : dealerPostCode.trim();
    }

    public String getSponsorNo() {
        return sponsorNo;
    }

    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo == null ? null : sponsorNo.trim();
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName == null ? null : dealerName.trim();
    }

    public String getRegisterSpouse() {
        return registerSpouse;
    }

    public void setRegisterSpouse(String registerSpouse) {
        this.registerSpouse = registerSpouse == null ? null : registerSpouse.trim();
    }

    public Date getTypeEffectiveDate() {
        return typeEffectiveDate;
    }

    public void setTypeEffectiveDate(Date typeEffectiveDate) {
        this.typeEffectiveDate = typeEffectiveDate;
    }

    public Date getTypeInactiveDate() {
        return typeInactiveDate;
    }

    public void setTypeInactiveDate(Date typeInactiveDate) {
        this.typeInactiveDate = typeInactiveDate;
    }

    public Date getPostEffectiveDate() {
        return postEffectiveDate;
    }

    public void setPostEffectiveDate(Date postEffectiveDate) {
        this.postEffectiveDate = postEffectiveDate;
    }

    public Date getPostInactiveDate() {
        return postInactiveDate;
    }

    public void setPostInactiveDate(Date postInactiveDate) {
        this.postInactiveDate = postInactiveDate;
    }

    public String getSaleOrgCode() {
        return saleOrgCode;
    }

    public void setSaleOrgCode(String saleOrgCode) {
        this.saleOrgCode = saleOrgCode == null ? null : saleOrgCode.trim();
    }

    public String getSaleBranchNo() {
        return saleBranchNo;
    }

    public void setSaleBranchNo(String saleBranchNo) {
        this.saleBranchNo = saleBranchNo == null ? null : saleBranchNo.trim();
    }

    public String getSaleZoneNo() {
        return saleZoneNo;
    }

    public void setSaleZoneNo(String saleZoneNo) {
        this.saleZoneNo = saleZoneNo == null ? null : saleZoneNo.trim();
    }

    public String getAppPeriod() {
        return appPeriod;
    }

    public void setAppPeriod(String appPeriod) {
        this.appPeriod = appPeriod == null ? null : appPeriod.trim();
    }

    public String getAppFirstSoNo() {
        return appFirstSoNo;
    }

    public void setAppFirstSoNo(String appFirstSoNo) {
        this.appFirstSoNo = appFirstSoNo == null ? null : appFirstSoNo.trim();
    }

    public String getAppForInternational() {
        return appForInternational;
    }

    public void setAppForInternational(String appForInternational) {
        this.appForInternational = appForInternational == null ? null : appForInternational.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getOpLockStatus() {
        return opLockStatus;
    }

    public void setOpLockStatus(String opLockStatus) {
        this.opLockStatus = opLockStatus == null ? null : opLockStatus.trim();
    }

    public String getTaxCustCode() {
        return taxCustCode;
    }

    public void setTaxCustCode(String taxCustCode) {
        this.taxCustCode = taxCustCode == null ? null : taxCustCode.trim();
    }

    public String getAdviseNo() {
        return adviseNo;
    }

    public void setAdviseNo(String adviseNo) {
        this.adviseNo = adviseNo == null ? null : adviseNo.trim();
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus == null ? null : processStatus.trim();
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getProcessMessage() {
        return processMessage;
    }

    public void setProcessMessage(String processMessage) {
        this.processMessage = processMessage == null ? null : processMessage.trim();
    }
}