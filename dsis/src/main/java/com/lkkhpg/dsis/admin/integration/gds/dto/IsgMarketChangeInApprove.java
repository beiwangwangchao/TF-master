/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 市场变更审批接口表DTO.
 * 
 * @author shenqb
 *
 */
public class IsgMarketChangeInApprove extends BaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long interfaceId;

    private Long gdsId;

    private String appCertificateType;

    private String appCertificateNo;

    private String newDealerType;

    private String appAuditedBy;

    private String gdsTranStatus;

    private String appAuditedMemo;

    private String processStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date processDate;

    private String processMessage;

    private Long requestId;

    private Long programId;

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Long getGdsId() {
        return gdsId;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
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

    public String getNewDealerType() {
        return newDealerType;
    }

    public void setNewDealerType(String newDealerType) {
        this.newDealerType = newDealerType == null ? null : newDealerType.trim();
    }

    public String getAppAuditedBy() {
        return appAuditedBy;
    }

    public void setAppAuditedBy(String appAuditedBy) {
        this.appAuditedBy = appAuditedBy == null ? null : appAuditedBy.trim();
    }

    public String getGdsTranStatus() {
        return gdsTranStatus;
    }

    public void setGdsTranStatus(String gdsTranStatus) {
        this.gdsTranStatus = gdsTranStatus == null ? null : gdsTranStatus.trim();
    }

    public String getAppAuditedMemo() {
        return appAuditedMemo;
    }

    public void setAppAuditedMemo(String appAuditedMemo) {
        this.appAuditedMemo = appAuditedMemo == null ? null : appAuditedMemo.trim();
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

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }
}