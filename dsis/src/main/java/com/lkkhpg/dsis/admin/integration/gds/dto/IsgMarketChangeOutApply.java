/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 转出本市场的申请.
 * 
 * @author chuangsheng.zhang.
 */
public class IsgMarketChangeOutApply {
    private Long interfaceId;

    /**
     * 卡号.
     */
    private String dealerNo;

    /**
     * 申请人姓.
     */
    private String appLastName;

    /**
     * 申请人姓.
     */
    private String appFirstName;

    /**
     * 新销售机构代码.
     */
    private String newSaleOrgCode;

    /**
     * 
     */
    private String appEntryBy;

    /**
     * 
     */
    private String remarks;

    /**
     * 
     */
    private Long gdsId;

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

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo == null ? null : dealerNo.trim();
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

    public String getNewSaleOrgCode() {
        return newSaleOrgCode;
    }

    public void setNewSaleOrgCode(String newSaleOrgCode) {
        this.newSaleOrgCode = newSaleOrgCode == null ? null : newSaleOrgCode.trim();
    }

    public String getAppEntryBy() {
        return appEntryBy;
    }

    public void setAppEntryBy(String appEntryBy) {
        this.appEntryBy = appEntryBy == null ? null : appEntryBy.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Long getGdsId() {
        return gdsId;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
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