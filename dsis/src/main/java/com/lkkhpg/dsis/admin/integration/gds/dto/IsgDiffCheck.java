/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 差异检查.
 * @author Clerifen Li
 */
public class IsgDiffCheck extends BaseDTO {
    
    private Long interfaceId;

    private String checkDate;

    private String checkOrgCode;

    private String checkEntityType;

    private String checkEntityNo;

    private String checkEntityRefPeriod;

    private String checkPhase;

    private String checkResultCode;

    private String checkResultMemo01;

    private String checkResultMemo02;

    private String orgReadFlag;

    private String orgReadTime;

    private String orgReadBy;

    private String orgAmendFlag;

    private String orgAmendMemo;

    private String orgAmendTime;

    private String orgAmendBy;

    private String enabled;

    private String comments;

    private String orgAutoSyn;

    private String adviseNo;

    private String processStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date processDate;

    private String processMessage;
    
    private String isExist;
    
    private String uploadFlag;
    
    

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getIsExist() {
        return isExist;
    }

    public void setIsExist(String isExist) {
        this.isExist = isExist;
    }

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate == null ? null : checkDate.trim();
    }

    public String getCheckOrgCode() {
        return checkOrgCode;
    }

    public void setCheckOrgCode(String checkOrgCode) {
        this.checkOrgCode = checkOrgCode == null ? null : checkOrgCode.trim();
    }

    public String getCheckEntityType() {
        return checkEntityType;
    }

    public void setCheckEntityType(String checkEntityType) {
        this.checkEntityType = checkEntityType == null ? null : checkEntityType.trim();
    }

    public String getCheckEntityNo() {
        return checkEntityNo;
    }

    public void setCheckEntityNo(String checkEntityNo) {
        this.checkEntityNo = checkEntityNo == null ? null : checkEntityNo.trim();
    }

    public String getCheckEntityRefPeriod() {
        return checkEntityRefPeriod;
    }

    public void setCheckEntityRefPeriod(String checkEntityRefPeriod) {
        this.checkEntityRefPeriod = checkEntityRefPeriod == null ? null : checkEntityRefPeriod.trim();
    }

    public String getCheckPhase() {
        return checkPhase;
    }

    public void setCheckPhase(String checkPhase) {
        this.checkPhase = checkPhase == null ? null : checkPhase.trim();
    }

    public String getCheckResultCode() {
        return checkResultCode;
    }

    public void setCheckResultCode(String checkResultCode) {
        this.checkResultCode = checkResultCode == null ? null : checkResultCode.trim();
    }

    public String getCheckResultMemo01() {
        return checkResultMemo01;
    }

    public void setCheckResultMemo01(String checkResultMemo01) {
        this.checkResultMemo01 = checkResultMemo01 == null ? null : checkResultMemo01.trim();
    }

    public String getCheckResultMemo02() {
        return checkResultMemo02;
    }

    public void setCheckResultMemo02(String checkResultMemo02) {
        this.checkResultMemo02 = checkResultMemo02 == null ? null : checkResultMemo02.trim();
    }

    public String getOrgReadFlag() {
        return orgReadFlag;
    }

    public void setOrgReadFlag(String orgReadFlag) {
        this.orgReadFlag = orgReadFlag == null ? null : orgReadFlag.trim();
    }

    public String getOrgReadTime() {
        return orgReadTime;
    }

    public void setOrgReadTime(String orgReadTime) {
        this.orgReadTime = orgReadTime == null ? null : orgReadTime.trim();
    }

    public String getOrgReadBy() {
        return orgReadBy;
    }

    public void setOrgReadBy(String orgReadBy) {
        this.orgReadBy = orgReadBy == null ? null : orgReadBy.trim();
    }

    public String getOrgAmendFlag() {
        return orgAmendFlag;
    }

    public void setOrgAmendFlag(String orgAmendFlag) {
        this.orgAmendFlag = orgAmendFlag == null ? null : orgAmendFlag.trim();
    }

    public String getOrgAmendMemo() {
        return orgAmendMemo;
    }

    public void setOrgAmendMemo(String orgAmendMemo) {
        this.orgAmendMemo = orgAmendMemo == null ? null : orgAmendMemo.trim();
    }

    public String getOrgAmendTime() {
        return orgAmendTime;
    }

    public void setOrgAmendTime(String orgAmendTime) {
        this.orgAmendTime = orgAmendTime == null ? null : orgAmendTime.trim();
    }

    public String getOrgAmendBy() {
        return orgAmendBy;
    }

    public void setOrgAmendBy(String orgAmendBy) {
        this.orgAmendBy = orgAmendBy == null ? null : orgAmendBy.trim();
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getOrgAutoSyn() {
        return orgAutoSyn;
    }

    public void setOrgAutoSyn(String orgAutoSyn) {
        this.orgAutoSyn = orgAutoSyn == null ? null : orgAutoSyn.trim();
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