/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 销售分公司 dto.
 * 
 * @author chuangsheng.zhang.
 */
public class IsgSaleBranchList {

    private Long interfaceId;

    /**
     * 获取销售分公司列表.
     */
    private String saleOrgCode;

    /**
     * 语言编码.
     */
    private String languageCode;

    /**
     * 分支机构代号.
     */
    private String saleBranchCode;

    /**
     * 分支机构简称.
     */
    private String saleBranchDesc;

    /**
     * 分支机构全称.
     */
    private String saleBranchDesc2;

    /**
     * 销售大区代号.
     */
    private String saleRegionCode;

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

    public String getSaleOrgCode() {
        return saleOrgCode;
    }

    public void setSaleOrgCode(String saleOrgCode) {
        this.saleOrgCode = saleOrgCode == null ? null : saleOrgCode.trim();
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode == null ? null : languageCode.trim();
    }

    public String getSaleBranchCode() {
        return saleBranchCode;
    }

    public void setSaleBranchCode(String saleBranchCode) {
        this.saleBranchCode = saleBranchCode == null ? null : saleBranchCode.trim();
    }

    public String getSaleBranchDesc() {
        return saleBranchDesc;
    }

    public void setSaleBranchDesc(String saleBranchDesc) {
        this.saleBranchDesc = saleBranchDesc == null ? null : saleBranchDesc.trim();
    }

    public String getSaleBranchDesc2() {
        return saleBranchDesc2;
    }

    public void setSaleBranchDesc2(String saleBranchDesc2) {
        this.saleBranchDesc2 = saleBranchDesc2 == null ? null : saleBranchDesc2.trim();
    }

    public String getSaleRegionCode() {
        return saleRegionCode;
    }

    public void setSaleRegionCode(String saleRegionCode) {
        this.saleRegionCode = saleRegionCode == null ? null : saleRegionCode.trim();
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