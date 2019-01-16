/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 出入库Dto.
 * 
 * @author mclin
 */
@Table(name = "inv_stock_trx")
public class StockTrx extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "trx_id", nullable = false)
    private Long trxId;

    private String trxNumber;

    /**
     * 库存组织ID.
     */
    @Column(name = "organization_id")
    @NotNull
    private Long organizationId;

    @NotNull
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date trxDate;

    /**
     * 调整类型.
     */
    @Column(name = "trx_type")
    @NotEmpty
    private String trxType;

	/**
     * 供应商代码.
     */
    @Column(name = "vender_id")
    private Long vendorId;

    private String status;

    private String remark;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date creationDate;

    private Date trxDateFrom;

    private Date trxDateTo;

    private Date creationDateFrom;

    private Date creationDateTo;

    private List<String> selStatus;

    @Children
    private List<StockTrxDetail> stockTrxDetails;

    private String operReasonCode;
    
    private String name;

    //退货单号id
    private String transactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    //退货单号
    private String outRefundNo;

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

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

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Date getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType == null ? null : trxType.trim();
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getTrxDateFrom() {
        return trxDateFrom;
    }

    public void setTrxDateFrom(Date trxDateFrom) {
        this.trxDateFrom = trxDateFrom;
    }

    public Date getTrxDateTo() {
        return trxDateTo;
    }

    public void setTrxDateTo(Date trxDateTo) {
        this.trxDateTo = trxDateTo;
    }

    public Date getCreationDateFrom() {
        return creationDateFrom;
    }

    public void setCreationDateFrom(Date creationDateFrom) {
        this.creationDateFrom = creationDateFrom;
    }

    public Date getCreationDateTo() {
        return creationDateTo;
    }

    public void setCreationDateTo(Date creationDateTo) {
        this.creationDateTo = creationDateTo;
    }

    public List<String> getSelStatus() {
        return selStatus;
    }

    public void setSelStatus(List<String> selStatus) {
        this.selStatus = selStatus;
    }

    public List<StockTrxDetail> getStockTrxDetails() {
        return stockTrxDetails;
    }

    public void setStockTrxDetails(List<StockTrxDetail> stockTrxDetails) {
        this.stockTrxDetails = stockTrxDetails;
    }

    public String getOperReasonCode() {
        return operReasonCode;
    }

    public void setOperReasonCode(String operReasonCode) {
        this.operReasonCode = operReasonCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "StockTrx{" +
                "trxId=" + trxId +
                ", trxNumber='" + trxNumber + '\'' +
                ", organizationId=" + organizationId +
                ", trxDate=" + trxDate +
                ", trxType='" + trxType + '\'' +
                ", vendorId=" + vendorId +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", creationDate=" + creationDate +
                ", trxDateFrom=" + trxDateFrom +
                ", trxDateTo=" + trxDateTo +
                ", creationDateFrom=" + creationDateFrom +
                ", creationDateTo=" + creationDateTo +
                ", selStatus=" + selStatus +
                ", stockTrxDetails=" + stockTrxDetails +
                ", operReasonCode='" + operReasonCode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}