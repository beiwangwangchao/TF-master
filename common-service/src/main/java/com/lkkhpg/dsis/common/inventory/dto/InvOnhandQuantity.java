/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 库存现有量DTO.
 * 
 * @author hanrui.huang
 */
public class InvOnhandQuantity extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private Long onhandId;

    @NotEmpty
    private Long organizationId;

    @NotEmpty
    private Long itemId;

    private Long subinventoryId;

    private Long locationId;

    private String lotNumber;

    @NotEmpty
    private String uomCode;

    @NotEmpty
    private BigDecimal quantity;

    @NotEmpty
    private Long lastTransactionId;

    @NotEmpty
    private Long initTransactionId;

    /**
     * 商品包编码.
     */
    private Long packageItemId;

    /**
     * 批次到期日期.
     */
    private Date expiryDate;
    
    /**
     * 商品包商品分配量.
     */
    private Long qty;

    /**
     * 商品编号.
     */
    private String itemNumber;

    /**
     * 商品名称.
     */
    private String itemName;

    /**
     * 商品描述.
     */
    private String itemDesc;

    /**
     * 待定数量.
     */
    private BigDecimal pendingQty;

    /**
     * 可用量.
     */
    private BigDecimal availableQty;

    /**
     * 本位币.
     */
    private String currencyCode;

    /**
     * 库存单位成本.
     */
    private String unitCost;

    /**
     * 金额.
     */
    private String cost;

    /**
     * 单位名称.
     */
    private String uomName;
    
    /**
     * 批次到期日从.
     */
    private Date expiryDateFrom;
    
    /**
     * 批次到期日至.
     */
    private Date expiryDateTo;
    
    

    

    public Date getExpiryDateFrom() {
        return expiryDateFrom;
    }

    public void setExpiryDateFrom(Date expiryDateFrom) {
        this.expiryDateFrom = expiryDateFrom;
    }

    public Date getExpiryDateTo() {
        return expiryDateTo;
    }

    public void setExpiryDateTo(Date expiryDateTo) {
        this.expiryDateTo = expiryDateTo;
    }

    public Long getOnhandId() {
        return onhandId;
    }

    public void setOnhandId(Long onhandId) {
        this.onhandId = onhandId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSubinventoryId() {
        return subinventoryId;
    }

    public void setSubinventoryId(Long subinventoryId) {
        this.subinventoryId = subinventoryId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber == null ? null : lotNumber.trim();
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode == null ? null : uomCode.trim();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Long getLastTransactionId() {
        return lastTransactionId;
    }

    public void setLastTransactionId(Long lastTransactionId) {
        this.lastTransactionId = lastTransactionId;
    }

    public Long getInitTransactionId() {
        return initTransactionId;
    }

    public void setInitTransactionId(Long initTransactionId) {
        this.initTransactionId = initTransactionId;
    }

    public Long getPackageItemId() {
        return packageItemId;
    }

    public void setPackageItemId(Long packageItemId) {
        this.packageItemId = packageItemId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public BigDecimal getPendingQty() {
        return pendingQty;
    }

    public void setPendingQty(BigDecimal pendingQty) {
        this.pendingQty = pendingQty;
    }

    public BigDecimal getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(BigDecimal availableQty) {
        this.availableQty = availableQty;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(String unitCost) {
        this.unitCost = unitCost;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

}