/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 下载批删会员信息行DTO.
 * @author linyuheng
 *
 */
public class IsgMemberDeleteDetail {
    private Long interfaceDetailId;

    private Long interfaceId;

    private String alterNo;

    private String tranPeriod;

    private String tranOrgCode;

    private String tranDealerNo;

    private String tranBase;

    private String tranMode;

    private String tranFromSponsorNo;

    private String tranFromDealerType;

    private String tranToSponsorNo;

    private String tranToDealerType;

    private String orgSyn;

    private String orgSynTime;

    private String orgSynBy;

    private String gdsRecheckFlag;

    private String gdsRecheckTime;

    private String gdsRecheckBy;

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

    public Long getInterfaceDetailId() {
        return interfaceDetailId;
    }

    public void setInterfaceDetailId(Long interfaceDetailId) {
        this.interfaceDetailId = interfaceDetailId;
    }

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

    public String getTranPeriod() {
        return tranPeriod;
    }

    public void setTranPeriod(String tranPeriod) {
        this.tranPeriod = tranPeriod == null ? null : tranPeriod.trim();
    }

    public String getTranOrgCode() {
        return tranOrgCode;
    }

    public void setTranOrgCode(String tranOrgCode) {
        this.tranOrgCode = tranOrgCode == null ? null : tranOrgCode.trim();
    }

    public String getTranDealerNo() {
        return tranDealerNo;
    }

    public void setTranDealerNo(String tranDealerNo) {
        this.tranDealerNo = tranDealerNo == null ? null : tranDealerNo.trim();
    }

    public String getTranBase() {
        return tranBase;
    }

    public void setTranBase(String tranBase) {
        this.tranBase = tranBase;
    }

    public String getTranMode() {
        return tranMode;
    }

    public void setTranMode(String tranMode) {
        this.tranMode = tranMode == null ? null : tranMode.trim();
    }

    public String getTranFromSponsorNo() {
        return tranFromSponsorNo;
    }

    public void setTranFromSponsorNo(String tranFromSponsorNo) {
        this.tranFromSponsorNo = tranFromSponsorNo == null ? null : tranFromSponsorNo.trim();
    }

    public String getTranFromDealerType() {
        return tranFromDealerType;
    }

    public void setTranFromDealerType(String tranFromDealerType) {
        this.tranFromDealerType = tranFromDealerType == null ? null : tranFromDealerType.trim();
    }

    public String getTranToSponsorNo() {
        return tranToSponsorNo;
    }

    public void setTranToSponsorNo(String tranToSponsorNo) {
        this.tranToSponsorNo = tranToSponsorNo == null ? null : tranToSponsorNo.trim();
    }

    public String getTranToDealerType() {
        return tranToDealerType;
    }

    public void setTranToDealerType(String tranToDealerType) {
        this.tranToDealerType = tranToDealerType == null ? null : tranToDealerType.trim();
    }

    public String getOrgSyn() {
        return orgSyn;
    }

    public void setOrgSyn(String orgSyn) {
        this.orgSyn = orgSyn == null ? null : orgSyn.trim();
    }

    public String getOrgSynTime() {
        return orgSynTime;
    }

    public void setOrgSynTime(String orgSynTime) {
        this.orgSynTime = orgSynTime == null ? null : orgSynTime.trim();
    }

    public String getOrgSynBy() {
        return orgSynBy;
    }

    public void setOrgSynBy(String orgSynBy) {
        this.orgSynBy = orgSynBy == null ? null : orgSynBy.trim();
    }

    public String getGdsRecheckFlag() {
        return gdsRecheckFlag;
    }

    public void setGdsRecheckFlag(String gdsRecheckFlag) {
        this.gdsRecheckFlag = gdsRecheckFlag == null ? null : gdsRecheckFlag.trim();
    }

    public String getGdsRecheckTime() {
        return gdsRecheckTime;
    }

    public void setGdsRecheckTime(String gdsRecheckTime) {
        this.gdsRecheckTime = gdsRecheckTime;
    }

    public String getGdsRecheckBy() {
        return gdsRecheckBy;
    }

    public void setGdsRecheckBy(String gdsRecheckBy) {
        this.gdsRecheckBy = gdsRecheckBy == null ? null : gdsRecheckBy.trim();
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
}