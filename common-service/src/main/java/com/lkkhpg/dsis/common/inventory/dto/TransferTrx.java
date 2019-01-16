/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 移库DTO.
 * 
 * @author zhangchuangsheng
 */
public class TransferTrx extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private Long trxId;

    /**
     * 单据编号.
     */
    @NotEmpty
    private String trxNumber;

    /**
     * 库存组织ID.
     */
    @NotEmpty
    private Long organizationId;

    /**
     * 转移库存组织ID.
     */
    @NotEmpty
    private Long transferOrgId;

    /**
     * 转移状态.
     */
    private String overallStatus;

    /**
     * 事务处理类型.
     */
    @NotEmpty
    private String transferType;

    /**
     * 状态.
     */
    private String status;

    /**
     * 事务处理日期.
     */
    @NotEmpty
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date trxDate;

    /**
     * 备注.
     */
    private String remark;

    /**
     * 来源类型.
     */
    private String sourceType;

    /**
     * 来源事务处理ID.
     */
    private String sourceTrxId;

    /**
     * 创建日期.
     */
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date creationDate;

    /**
     * 业务实体名称.
     */
    private String marketName;

    @Children
    private List<TransferTrxDetail> transferTrxDetails;

    public Long getTrxId() {
        return trxId;
    }

    public void setTrxId(Long trxId) {
        this.trxId = trxId;
    }

    public String getTrxNumber() {
        return trxNumber;
    }

    public void setTrxNumber(String trxNumber) {
        this.trxNumber = trxNumber == null ? null : trxNumber.trim();
    }

    public String getOverallStatus() {
        return overallStatus;
    }

    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getTransferOrgId() {
        return transferOrgId;
    }

    public void setTransferOrgId(Long transferOrgId) {
        this.transferOrgId = transferOrgId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType == null ? null : transferType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public String getSourceTrxId() {
        return sourceTrxId;
    }

    public void setSourceTrxId(String sourceTrxId) {
        this.sourceTrxId = sourceTrxId;
    }

    public List<TransferTrxDetail> getTransferTrxDetails() {
        return transferTrxDetails;
    }

    public void setTransferTrxDetails(List<TransferTrxDetail> transferTrxDetails) {
        this.transferTrxDetails = transferTrxDetails;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

}