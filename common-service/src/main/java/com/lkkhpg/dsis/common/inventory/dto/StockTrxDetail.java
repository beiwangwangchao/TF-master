/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 出入库明细实体类.
 * 
 * @author mclin
 */
@Table(name = "inv_stock_trx_detail")
public class StockTrxDetail extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "trx_detail_id", nullable = false)
    private Long trxDetailId;

    private Long trxId;

    private Long organizationId;

    private Long subinventoryId;

    private Long locatorId;

    /**
     * 商品代码.
     */
    @Column(name = "item_id")
    @NotNull
    private Long itemId;

    private String operType;

    private String operReasonCode;

    private String lotNumber;

    private Date expiryDate;

    /**
     * 包装类型.
     */
    @Column(name = "packing_type")
    @NotEmpty
    private String packingType;

    private BigDecimal unitOfCarton;

    private BigDecimal numberOfCarton;

    private BigDecimal numberOfIndCarton;

    private BigDecimal remainingIndAftCar;
    /**
     * 出入库数量.
     */
    @Column(name = "quantity")
    @NotNull
    private BigDecimal quantity;

    private Integer unitCost;

    private String remark;

    /**
     * 数量.
     */
    @NotNull
    @DecimalMin(value = "1")
    private BigDecimal amount;

    private String itemNumber;

    private String itemCode;

    private String itemName;

    private String uomCode;

    private String uomName;

    private String toUomName;

    private BigDecimal unitPrice;

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType == null ? null : operType.trim();
    }

    public String getOperReasonCode() {
        return operReasonCode;
    }

    public void setOperReasonCode(String operReasonCode) {
        this.operReasonCode = operReasonCode == null ? null : operReasonCode.trim();
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

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType == null ? null : packingType.trim();
    }

    public BigDecimal getUnitOfCarton() {
        return unitOfCarton;
    }

    public void setUnitOfCarton(BigDecimal unitOfCarton) {
        this.unitOfCarton = unitOfCarton;
    }

    public BigDecimal getNumberOfCarton() {
        return numberOfCarton;
    }

    public void setNumberOfCarton(BigDecimal numberOfCarton) {
        this.numberOfCarton = numberOfCarton;
    }

    public BigDecimal getNumberOfIndCarton() {
        return numberOfIndCarton;
    }

    public void setNumberOfIndCarton(BigDecimal numberOfIndCarton) {
        this.numberOfIndCarton = numberOfIndCarton;
    }

    public BigDecimal getRemainingIndAftCar() {
        return remainingIndAftCar;
    }

    public void setRemainingIndAftCar(BigDecimal remainingIndAftCar) {
        this.remainingIndAftCar = remainingIndAftCar;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Integer unitCost) {
        this.unitCost = unitCost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemarks(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber == null ? null : itemNumber.trim();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode == null ? null : uomCode.trim();
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName == null ? null : uomName.trim();
    }

    public String getToUomName() {
        return toUomName;
    }

    public void setToUomName(String toUomName) {
        this.toUomName = toUomName == null ? null : toUomName.trim();
    }

    @Override
    public String toString() {
        return "StockTrxDetail{" +
                "trxDetailId=" + trxDetailId +
                ", trxId=" + trxId +
                ", organizationId=" + organizationId +
                ", subinventoryId=" + subinventoryId +
                ", locatorId=" + locatorId +
                ", itemId=" + itemId +
                ", operType='" + operType + '\'' +
                ", operReasonCode='" + operReasonCode + '\'' +
                ", lotNumber='" + lotNumber + '\'' +
                ", expiryDate=" + expiryDate +
                ", packingType='" + packingType + '\'' +
                ", unitOfCarton=" + unitOfCarton +
                ", numberOfCarton=" + numberOfCarton +
                ", numberOfIndCarton=" + numberOfIndCarton +
                ", remainingIndAftCar=" + remainingIndAftCar +
                ", quantity=" + quantity +
                ", unitCost=" + unitCost +
                ", remark='" + remark + '\'' +
                ", amount=" + amount +
                ", itemNumber='" + itemNumber + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", uomCode='" + uomCode + '\'' +
                ", uomName='" + uomName + '\'' +
                ", toUomName='" + toUomName + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}