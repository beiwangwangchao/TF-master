/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 下载移线会员资料DTO.
 * @author linyuheng
 *
 */
public class IsgUpstreamChange {
    private Long interfaceId;

    private String alterNo;

    private String alterDealerNo;

    private String alterMode;

    private String alterPeriod;

    private String alterOrgCode;

    private String refFromSponsorNo;

    private String refToSponsorNo;

    private String refSoNo;

    private String comments;

    private String lastUpdatedTime;

    private String lastUpdatedBy;

    private String uploadFlag;

    private String adviseNo;

    private String appNo;

    private String processStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date processDate;

    private String processMessage;

    private Long requestId;

    private Long programId;
    
    private List<IsgUpstreamChangeDetail> details;

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getAlterNo() {
        return alterNo;
    }

    public void setAlterNo(String alterNo) {
        this.alterNo = alterNo == null ? null : alterNo.trim();
    }

    public String getAlterDealerNo() {
        return alterDealerNo;
    }

    public void setAlterDealerNo(String alterDealerNo) {
        this.alterDealerNo = alterDealerNo == null ? null : alterDealerNo.trim();
    }

    public String getAlterMode() {
        return alterMode;
    }

    public void setAlterMode(String alterMode) {
        this.alterMode = alterMode == null ? null : alterMode.trim();
    }

    public String getAlterPeriod() {
        return alterPeriod;
    }

    public void setAlterPeriod(String alterPeriod) {
        this.alterPeriod = alterPeriod == null ? null : alterPeriod.trim();
    }

    public String getAlterOrgCode() {
        return alterOrgCode;
    }

    public void setAlterOrgCode(String alterOrgCode) {
        this.alterOrgCode = alterOrgCode == null ? null : alterOrgCode.trim();
    }

    public String getRefFromSponsorNo() {
        return refFromSponsorNo;
    }

    public void setRefFromSponsorNo(String refFromSponsorNo) {
        this.refFromSponsorNo = refFromSponsorNo == null ? null : refFromSponsorNo.trim();
    }

    public String getRefToSponsorNo() {
        return refToSponsorNo;
    }

    public void setRefToSponsorNo(String refToSponsorNo) {
        this.refToSponsorNo = refToSponsorNo == null ? null : refToSponsorNo.trim();
    }

    public String getRefSoNo() {
        return refSoNo;
    }

    public void setRefSoNo(String refSoNo) {
        this.refSoNo = refSoNo == null ? null : refSoNo.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy == null ? null : lastUpdatedBy.trim();
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

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo == null ? null : appNo.trim();
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

    public List<IsgUpstreamChangeDetail> getDetails() {
        return details;
    }

    public void setDetails(List<IsgUpstreamChangeDetail> details) {
        this.details = details;
    }
    
}