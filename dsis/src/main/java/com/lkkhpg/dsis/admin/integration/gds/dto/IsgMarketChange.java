/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 转出、转入会员DTO.
 * 
 * @author mclin
 */
public class IsgMarketChange extends BaseDTO {
    private Long interfaceId;

    private String operationType;

    private String gdsId;

    private String dealerNo;

    private String oldSaleOrgCode;

    private String newSaleOrgCode;

    private String newDealerType;

    private String newDealerSubType;

    private String appFullName;

    private String appLastName;

    private String appFirstName;

    private String appCertificateType;

    private String appCertificateNo;

    private String sponsor;

    private String newSaleBranchNo;

    private String dealerPostCode;
    
    private String uploadFlag;

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

    public String getGdsId() {
        return gdsId;
    }

    public void setGdsId(String gdsId) {
        this.gdsId = gdsId == null ? null : gdsId.trim();
    }

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo == null ? null : dealerNo.trim();
    }

    public String getOldSaleOrgCode() {
        return oldSaleOrgCode;
    }

    public void setOldSaleOrgCode(String oldSaleOrgCode) {
        this.oldSaleOrgCode = oldSaleOrgCode == null ? null : oldSaleOrgCode.trim();
    }

    public String getNewSaleOrgCode() {
        return newSaleOrgCode;
    }

    public void setNewSaleOrgCode(String newSaleOrgCode) {
        this.newSaleOrgCode = newSaleOrgCode == null ? null : newSaleOrgCode.trim();
    }

    public String getNewDealerType() {
        return newDealerType;
    }

    public void setNewDealerType(String newDealerType) {
        this.newDealerType = newDealerType == null ? null : newDealerType.trim();
    }

    public String getNewDealerSubType() {
        return newDealerSubType;
    }

    public void setNewDealerSubType(String newDealerSubType) {
        this.newDealerSubType = newDealerSubType == null ? null : newDealerSubType.trim();
    }

    public String getAppFullName() {
        return appFullName;
    }

    public void setAppFullName(String appFullName) {
        this.appFullName = appFullName == null ? null : appFullName.trim();
    }

    public String getAppLastName() {
        return appLastName;
    }

    public void setAppLastName(String appLastName) {
        this.appLastName = appLastName == null ? null : appLastName.trim();
    }

    public String getAppFirstName() {
        return appFirstName;
    }

    public void setAppFirstName(String appFirstName) {
        this.appFirstName = appFirstName == null ? null : appFirstName.trim();
    }

    public String getAppCertificateType() {
        return appCertificateType;
    }

    public void setAppCertificateType(String appCertificateType) {
        this.appCertificateType = appCertificateType == null ? null : appCertificateType.trim();
    }

    public String getAppCertificateNo() {
        return appCertificateNo;
    }

    public void setAppCertificateNo(String appCertificateNo) {
        this.appCertificateNo = appCertificateNo == null ? null : appCertificateNo.trim();
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor == null ? null : sponsor.trim();
    }

    public String getNewSaleBranchNo() {
        return newSaleBranchNo;
    }

    public void setNewSaleBranchNo(String newSaleBranchNo) {
        this.newSaleBranchNo = newSaleBranchNo == null ? null : newSaleBranchNo.trim();
    }

    public String getDealerPostCode() {
        return dealerPostCode;
    }

    public void setDealerPostCode(String dealerPostCode) {
        this.dealerPostCode = dealerPostCode == null ? null : dealerPostCode.trim();
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag == null ? null : uploadFlag.trim();
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