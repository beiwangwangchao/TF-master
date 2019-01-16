/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 移库详细DTO.
 * 
 * @author zhangchuangsheng
 */
public class TransferTrxDetail extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private Long trxDetailId;
    /**
     * 事务ID，引用INV_TRANSFER_TRX的TRX_ID.
     */
    @NotEmpty
    private Long trxId;
    /**
     * 状态.
     */
    @NotEmpty
    private String status;
    /**
     * 库存组织ID.
     */
    @NotEmpty
    private Long organizationId;

    /**
     * 子库ID.
     */
    @NotEmpty
    private Long subinventoryId;
    /**
     * 货位ID.
     */
    @NotEmpty
    private Long locatorId;

    /**
     * 商品ID.
     */
    private Long itemId;
    /**
     * 商品编码,仅作展示字段.
     */
    private String itemNumber;
    /**
     * 商品名称.
     */
    private String itemName;
    /**
     * 商品单位.
     */
    private String itemUomCode;

    /**
     * 商品单位名称,展示用.
     */
    private String itemUomName;
    /**
     * 批次.
     */
    @NotEmpty
    private String lotNumber;
    /**
     * 库存量.
     */
    private BigDecimal quantity;
    /**
     * 批次有效期.
     */
    private Date expiryDate;

    /**
     * 包装类型.
     */
    @NotEmpty
    private String packingType;

    /**
     * 包装类型名称.
     */
    @NotEmpty
    private String name;

    /**
     * 箱子数量.
     */
    private Long numberOfCarton;

    /**
     * 箱子内单件数量.
     */
    private Long numberOfIndCarton;

    /**
     * 剩余数量.
     */
    private BigDecimal remainingIndAftCar;
    /**
     * 事务处理数量.
     */
    private BigDecimal trxQty;
    /**
     * 来源事务处理行ID.
     */
    private Long sourceLineId;
    /**
     * 备注.
     */
    private String remark;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber == null ? null : lotNumber.trim();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getItemUomName() {
        return itemUomName;
    }

    public void setItemUomName(String itemUomName) {
        this.itemUomName = itemUomName;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType == null ? null : packingType.trim();
    }

    public Long getNumberOfCarton() {
        return numberOfCarton;
    }

    public void setNumberOfCarton(Long numberOfCarton) {
        this.numberOfCarton = numberOfCarton;
    }

    public Long getNumberOfIndCarton() {
        return numberOfIndCarton;
    }

    public void setNumberOfIndCarton(Long numberOfIndCarton) {
        this.numberOfIndCarton = numberOfIndCarton;
    }

    public BigDecimal getRemainingIndAftCar() {
        return remainingIndAftCar;
    }

    public void setRemainingIndAftCar(BigDecimal remainingIndAftCar) {
        this.remainingIndAftCar = remainingIndAftCar;
    }

    public BigDecimal getTrxQty() {
        return trxQty;
    }

    public void setTrxQty(BigDecimal trxQty) {
        this.trxQty = trxQty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getSourceLineId() {
        return sourceLineId;
    }

    public void setSourceLineId(Long sourceLineId) {
        this.sourceLineId = sourceLineId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUomCode() {
        return itemUomCode;
    }

    public void setItemUomCode(String itemUomCode) {
        this.itemUomCode = itemUomCode;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}