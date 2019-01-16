/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * autoShip订单行.
 * 
 * @author wuyichu
 */
@Table(name = "OM_AUTOSHIP_LINE")
public class AutoshipLine extends BaseDTO {

    @Id
    @Column(name = "LINE_ID", nullable = false, unique = true)
    private Long lineId;

    private Long autoshipId;

    @NotNull
    private Long itemId;

    private String itemName;

    private String itemNumber;

    private BigDecimal unitOrigPrice;

    private BigDecimal unitDiscountPrice;

    private BigDecimal unitSellingPrice;

    @NotEmpty
    private String itemSalesType;

    private BigDecimal pv;

    @NotNull
    @Max(value = 99999)
    @Min(value = 1)
    private BigDecimal quantity;

    private BigDecimal amount;

    private BigDecimal unitRedeemPoint;

    private BigDecimal redeemPoint;

    private Long voucherId;

    private Long salesOrgId;

    private String uomCode;

    // 商品类型
    private String itemType;

    // 父级行id
    private Long parentLineId;

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getAutoshipId() {
        return autoshipId;
    }

    public void setAutoshipId(Long autoshipId) {
        this.autoshipId = autoshipId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getUnitOrigPrice() {
        return unitOrigPrice;
    }

    public void setUnitOrigPrice(BigDecimal unitOrigPrice) {
        this.unitOrigPrice = unitOrigPrice;
    }

    public BigDecimal getUnitDiscountPrice() {
        return unitDiscountPrice;
    }

    public void setUnitDiscountPrice(BigDecimal unitDiscountPrice) {
        this.unitDiscountPrice = unitDiscountPrice;
    }

    public BigDecimal getUnitSellingPrice() {
        return unitSellingPrice;
    }

    public void setUnitSellingPrice(BigDecimal unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }

    public String getItemSalesType() {
        return itemSalesType;
    }

    public void setItemSalesType(String itemSalesType) {
        this.itemSalesType = itemSalesType == null ? null : itemSalesType.trim();
    }

    public BigDecimal getPv() {
        return pv;
    }

    public void setPv(BigDecimal pv) {
        this.pv = pv;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUnitRedeemPoint() {
        return unitRedeemPoint;
    }

    public void setUnitRedeemPoint(BigDecimal unitRedeemPoint) {
        this.unitRedeemPoint = unitRedeemPoint;
    }

    public BigDecimal getRedeemPoint() {
        return redeemPoint;
    }

    public void setRedeemPoint(BigDecimal redeemPoint) {
        this.redeemPoint = redeemPoint;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Long getParentLineId() {
        return parentLineId;
    }

    public void setParentLineId(Long parentLineId) {
        this.parentLineId = parentLineId;
    }
}