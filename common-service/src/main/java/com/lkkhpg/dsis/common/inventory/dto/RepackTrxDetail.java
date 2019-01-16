/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 重新分包明细Dto.
 * 
 * @author hanrui.huang
 */
public class RepackTrxDetail extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private Long trxDetailId;

    /**
     * 分包事务ID.
     */
    private Long trxId;

    /**
     * 库存组织Id.
     */
    private Long organizationId;

    /**
     * 子库ID.
     */
    private Long subinventoryId;

    /**
     * 货位Id.
     */
    private Long locatorId;

    /**
     * 商品ID.
     */
    private Long itemPackageComponents;
    
    /**
     * 实际扣库存商品id.
     */
    private Long countItemId;

    /**
     * 批次号.
     */
    private String lotNumber;

    /**
     * 批次到期日期.
     */
    private Date expiryDate;

    /**
     * 商品分配数量.
     */
    private Long allocateQty;

    /**
     * 注释.
     */
    private String remark;
    
    /**
     * 分包单编码.
     */
    private String trxNumber;
    
    /**
     * 库存量（不保存到数据库）.
     */
    private long quantity;

    private Long packageItemId;

    public Long getTrxDetailId() {
        return trxDetailId;
    }

    public void setTrxDetailId(Long trxDetailId) {
        this.trxDetailId = trxDetailId;
    }

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

    public Long getSubinventoryId() {
        return subinventoryId;
    }

    public void setSubinventoryId(Long subinventoryId) {
        this.subinventoryId = subinventoryId;
    }

    public Long getLocatorId() {
        return locatorId;
    }

    public void setLocatorId(Long locatorId) {
        this.locatorId = locatorId;
    }

    public Long getItemPackageComponents() {
        return itemPackageComponents;
    }

    public void setItemPackageComponents(Long itemPackageComponents) {
        this.itemPackageComponents = itemPackageComponents;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber == null ? null : lotNumber.trim();
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getAllocateQty() {
        return allocateQty;
    }

    public void setAllocateQty(Long allocateQty) {
        this.allocateQty = allocateQty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getTrxNumber() {
        return trxNumber;
    }

    public void setTrxNumber(String trxNumber) {
        this.trxNumber = trxNumber;
    }

    
    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Long getCountItemId() {
        return countItemId;
    }

    public void setCountItemId(Long countItemId) {
        this.countItemId = countItemId;
    }

    public Long getPackageItemId() {
        return packageItemId;
    }

    public void setPackageItemId(Long packageItemId) {
        this.packageItemId = packageItemId;
    }
    
}