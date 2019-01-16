/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 重新分包Dto.
 * 
 * @author hanrui.huang
 */
public class RepackTrx extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private Long trxId;

    /**
     * 库存组织Id.
     */
    @NotNull
    private Long organizationId;

    /**
     * 分包单编码.
     */
    private String trxNumber;

    /**
     * 商品包Id.
     */
    @NotNull
    private Long packageItemId;

    /**
     * 分包数量.
     */
    @NotNull
    private Long repackQty;

    /**
     * 处理日期.
     */
    @NotNull
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date trxDate;

    /**
     * 分包类型.
     */
    @NotEmpty
    private String operType;

    /**
     * 状态.
     */
    @NotEmpty
    private String status;

    private String attributeCategory;

    private Date beforetrxDate;

    private Date aftertrxDate;

    private Date beforeCreationDate;

    private Date afterCreationDate;

    /**
     * 创建日期.
     */
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date creationDate;

    /**
     * 注释.
     */
    private String remark;

    /**
     * 商品包编码.
     */
    private String itemNumber;
    
    private String itemName;

    @Children
    private List<RepackTrxDetail> repackTrxDetails;

    public Long getTrxId() {
        return trxId;
    }

    public void setTrxId(Long trxId) {
        this.trxId = trxId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getTrxNumber() {
        return trxNumber;
    }

    public void setTrxNumber(String trxNumber) {
        this.trxNumber = trxNumber == null ? null : trxNumber.trim();
    }

    public Long getPackageItemId() {
        return packageItemId;
    }

    public void setPackageItemId(Long packageItemId) {
        this.packageItemId = packageItemId;
    }

    public Long getRepackQty() {
        return repackQty;
    }

    public void setRepackQty(Long repackQty) {
        this.repackQty = repackQty;
    }

    public Date getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType == null ? null : operType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAttributeCategory() {
        return attributeCategory;
    }

    public void setAttributeCategory(String attributeCategory) {
        this.attributeCategory = attributeCategory == null ? null : attributeCategory.trim();
    }

    public Date getBeforetrxDate() {
        return beforetrxDate;
    }

    public void setBeforetrxDate(Date beforetrxDate) {
        this.beforetrxDate = beforetrxDate;
    }

    public Date getAftertrxDate() {
        return aftertrxDate;
    }

    public void setAftertrxDate(Date aftertrxDate) {
        this.aftertrxDate = aftertrxDate;
    }

    public Date getBeforeCreationDate() {
        return beforeCreationDate;
    }

    public void setBeforeCreationDate(Date beforeCreationDate) {
        this.beforeCreationDate = beforeCreationDate;
    }

    public Date getAfterCreationDate() {
        return afterCreationDate;
    }

    public void setAfterCreationDate(Date afterCreationDate) {
        this.afterCreationDate = afterCreationDate;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public List<RepackTrxDetail> getRepackTrxDetails() {
        return repackTrxDetails;
    }

    public void setRepackTrxDetails(List<RepackTrxDetail> repackTrxDetails) {
        this.repackTrxDetails = repackTrxDetails;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}