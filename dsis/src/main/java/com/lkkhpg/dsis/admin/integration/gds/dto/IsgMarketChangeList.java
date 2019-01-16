/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * gds市场变更申请列表DTO.
 * @author yuchuan.zeng@hand-china.com
 *
 */
public class IsgMarketChangeList extends BaseDTO {
    private Long interfaceId;

    private String operationType;

    private Long gdsId;

    private String createby;

    private String createTime;

    private String lastUpdateby;

    private String lastUpdateTime;

    private String dealerNo;

    private String oldSaleOrgCode;

    private String newSaleOrgCode;

    private String newDealerType;

    private String newDealerSubType;

    private String appDate;

    private String appDocNo;

    private String appEntryTime;

    private String appEntryBy;

    private String appCheckedTime;

    private String appCheckedBy;

    private String appCheckedMemo;

    private String appAuditedTime;

    private String appAuditedBy;

    private String appAuditedMemo;

    private String gdsTranStatus;

    private String gdsTranSoPeriod;

    private String gdsTranEffectivePeriod;

    private String gdsTranTime;

    private String gdsTranBy;

    private String gdsTranMemo;

    private String oldOrgTranFlag;

    private String oldOrgTranTime;

    private String oldOrgTranBy;

    private String oldOrgTranMemo;

    private String newOrgTranFlag;

    private String newOrgTranTime;

    private String newOrgTranBy;

    private String newOrgTranMemo;

    private String appFullName;

    private String appLastName;

    private String appFirstName;

    private String appCertificateNationCode;

    private String appCertificateType;

    private String appCertificateNo;

    private String newSaleBranchNo;

    private String newSaleZoneNo;

    private String comments;

    private String subOrg;

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

    public Long getGdsId() {
        return gdsId;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby == null ? null : createby.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getLastUpdateby() {
        return lastUpdateby;
    }

    public void setLastUpdateby(String lastUpdateby) {
        this.lastUpdateby = lastUpdateby == null ? null : lastUpdateby.trim();
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime == null ? null : lastUpdateTime.trim();
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

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate == null ? null : appDate.trim();
    }

    public String getAppDocNo() {
        return appDocNo;
    }

    public void setAppDocNo(String appDocNo) {
        this.appDocNo = appDocNo == null ? null : appDocNo.trim();
    }

    public String getAppEntryTime() {
        return appEntryTime;
    }

    public void setAppEntryTime(String appEntryTime) {
        this.appEntryTime = appEntryTime == null ? null : appEntryTime.trim();
    }

    public String getAppEntryBy() {
        return appEntryBy;
    }

    public void setAppEntryBy(String appEntryBy) {
        this.appEntryBy = appEntryBy == null ? null : appEntryBy.trim();
    }

    public String getAppCheckedTime() {
        return appCheckedTime;
    }

    public void setAppCheckedTime(String appCheckedTime) {
        this.appCheckedTime = appCheckedTime == null ? null : appCheckedTime.trim();
    }

    public String getAppCheckedBy() {
        return appCheckedBy;
    }

    public void setAppCheckedBy(String appCheckedBy) {
        this.appCheckedBy = appCheckedBy == null ? null : appCheckedBy.trim();
    }

    public String getAppCheckedMemo() {
        return appCheckedMemo;
    }

    public void setAppCheckedMemo(String appCheckedMemo) {
        this.appCheckedMemo = appCheckedMemo == null ? null : appCheckedMemo.trim();
    }

    public String getAppAuditedTime() {
        return appAuditedTime;
    }

    public void setAppAuditedTime(String appAuditedTime) {
        this.appAuditedTime = appAuditedTime == null ? null : appAuditedTime.trim();
    }

    public String getAppAuditedBy() {
        return appAuditedBy;
    }

    public void setAppAuditedBy(String appAuditedBy) {
        this.appAuditedBy = appAuditedBy == null ? null : appAuditedBy.trim();
    }

    public String getAppAuditedMemo() {
        return appAuditedMemo;
    }

    public void setAppAuditedMemo(String appAuditedMemo) {
        this.appAuditedMemo = appAuditedMemo == null ? null : appAuditedMemo.trim();
    }

    public String getGdsTranStatus() {
        return gdsTranStatus;
    }

    public void setGdsTranStatus(String gdsTranStatus) {
        this.gdsTranStatus = gdsTranStatus == null ? null : gdsTranStatus.trim();
    }

    public String getGdsTranSoPeriod() {
        return gdsTranSoPeriod;
    }

    public void setGdsTranSoPeriod(String gdsTranSoPeriod) {
        this.gdsTranSoPeriod = gdsTranSoPeriod == null ? null : gdsTranSoPeriod.trim();
    }

    public String getGdsTranEffectivePeriod() {
        return gdsTranEffectivePeriod;
    }

    public void setGdsTranEffectivePeriod(String gdsTranEffectivePeriod) {
        this.gdsTranEffectivePeriod = gdsTranEffectivePeriod == null ? null : gdsTranEffectivePeriod.trim();
    }

    public String getGdsTranTime() {
        return gdsTranTime;
    }

    public void setGdsTranTime(String gdsTranTime) {
        this.gdsTranTime = gdsTranTime == null ? null : gdsTranTime.trim();
    }

    public String getGdsTranBy() {
        return gdsTranBy;
    }

    public void setGdsTranBy(String gdsTranBy) {
        this.gdsTranBy = gdsTranBy == null ? null : gdsTranBy.trim();
    }

    public String getGdsTranMemo() {
        return gdsTranMemo;
    }

    public void setGdsTranMemo(String gdsTranMemo) {
        this.gdsTranMemo = gdsTranMemo == null ? null : gdsTranMemo.trim();
    }

    public String getOldOrgTranFlag() {
        return oldOrgTranFlag;
    }

    public void setOldOrgTranFlag(String oldOrgTranFlag) {
        this.oldOrgTranFlag = oldOrgTranFlag == null ? null : oldOrgTranFlag.trim();
    }

    public String getOldOrgTranTime() {
        return oldOrgTranTime;
    }

    public void setOldOrgTranTime(String oldOrgTranTime) {
        this.oldOrgTranTime = oldOrgTranTime == null ? null : oldOrgTranTime.trim();
    }

    public String getOldOrgTranBy() {
        return oldOrgTranBy;
    }

    public void setOldOrgTranBy(String oldOrgTranBy) {
        this.oldOrgTranBy = oldOrgTranBy == null ? null : oldOrgTranBy.trim();
    }

    public String getOldOrgTranMemo() {
        return oldOrgTranMemo;
    }

    public void setOldOrgTranMemo(String oldOrgTranMemo) {
        this.oldOrgTranMemo = oldOrgTranMemo == null ? null : oldOrgTranMemo.trim();
    }

    public String getNewOrgTranFlag() {
        return newOrgTranFlag;
    }

    public void setNewOrgTranFlag(String newOrgTranFlag) {
        this.newOrgTranFlag = newOrgTranFlag == null ? null : newOrgTranFlag.trim();
    }

    public String getNewOrgTranTime() {
        return newOrgTranTime;
    }

    public void setNewOrgTranTime(String newOrgTranTime) {
        this.newOrgTranTime = newOrgTranTime == null ? null : newOrgTranTime.trim();
    }

    public String getNewOrgTranBy() {
        return newOrgTranBy;
    }

    public void setNewOrgTranBy(String newOrgTranBy) {
        this.newOrgTranBy = newOrgTranBy == null ? null : newOrgTranBy.trim();
    }

    public String getNewOrgTranMemo() {
        return newOrgTranMemo;
    }

    public void setNewOrgTranMemo(String newOrgTranMemo) {
        this.newOrgTranMemo = newOrgTranMemo == null ? null : newOrgTranMemo.trim();
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

    public String getAppCertificateNationCode() {
        return appCertificateNationCode;
    }

    public void setAppCertificateNationCode(String appCertificateNationCode) {
        this.appCertificateNationCode = appCertificateNationCode == null ? null : appCertificateNationCode.trim();
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

    public String getNewSaleBranchNo() {
        return newSaleBranchNo;
    }

    public void setNewSaleBranchNo(String newSaleBranchNo) {
        this.newSaleBranchNo = newSaleBranchNo == null ? null : newSaleBranchNo.trim();
    }

    public String getNewSaleZoneNo() {
        return newSaleZoneNo;
    }

    public void setNewSaleZoneNo(String newSaleZoneNo) {
        this.newSaleZoneNo = newSaleZoneNo == null ? null : newSaleZoneNo.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getSubOrg() {
        return subOrg;
    }

    public void setSubOrg(String subOrg) {
        this.subOrg = subOrg == null ? null : subOrg.trim();
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