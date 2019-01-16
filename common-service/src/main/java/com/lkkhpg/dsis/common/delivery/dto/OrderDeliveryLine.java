/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.dto;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 发运单行DTO.
 * 
 * @author liang.rao
 *
 */
public class OrderDeliveryLine extends BaseDTO {
    private Long lineId;

    private Long deliveryId;

    private Long invOrgId;

    private Long orderLineId;

    private BigDecimal outstandingQty;

    private BigDecimal returnQty;

    private Long pickReleaseId;

    private Long pickReleaseLineId;

    private Long itemId;

    private String itemNumber;

    private String itemName;

    /**
     * 真实商品ID.
     */
    private Long countItemId;
    /**
     * 真实商品名称.
     */
    private String countItemName;
    
    /**
     * 真实商品编码.
     */
    private String countItemNumber;
    /**
     * 所属商品包名称.
     */
    private String packageItemName;

    /**
     * 所属商品包.
     */
    private Long packageItemId;

    private String uomCode;

    private String uomName;

    private BigDecimal orderLineQuantity;

    private BigDecimal shippedQuantity;

    private BigDecimal unShippedQuantity;

    private BigDecimal pickQuantity;

    private String orderLineStatus;

    private String orderLineStatusDesc;

    private String batchCtrlFlag;

    private BigDecimal onhandQuantity;

    /**
     * add by furong.tang
     * 商品价格
     */
    private BigDecimal unitPrice;

    /**
     * add by furong.tang
     * 金额 = unitPrice * orderLineQuantity
     */
    private BigDecimal totalMoney;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Children
    private List<OrderDeliveryLot> lots;

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Long getInvOrgId() {
        return invOrgId;
    }

    public void setInvOrgId(Long invOrgId) {
        this.invOrgId = invOrgId;
    }

    public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public BigDecimal getOutstandingQty() {
        return outstandingQty;
    }

    public void setOutstandingQty(BigDecimal outstandingQty) {
        this.outstandingQty = outstandingQty;
    }

    public BigDecimal getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(BigDecimal returnQty) {
        this.returnQty = returnQty;
    }

    public Long getPickReleaseId() {
        return pickReleaseId;
    }

    public void setPickReleaseId(Long pickReleaseId) {
        this.pickReleaseId = pickReleaseId;
    }

    public Long getPickReleaseLineId() {
        return pickReleaseLineId;
    }

    public void setPickReleaseLineId(Long pickReleaseLineId) {
        this.pickReleaseLineId = pickReleaseLineId;
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

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public BigDecimal getOrderLineQuantity() {
        return orderLineQuantity;
    }

    public void setOrderLineQuantity(BigDecimal orderLineQuantity) {
        this.orderLineQuantity = orderLineQuantity;
    }

    public BigDecimal getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(BigDecimal shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }

    public BigDecimal getUnShippedQuantity() {
        return this.orderLineQuantity.subtract(this.shippedQuantity);
    }

    public void setUnShippedQuantity(BigDecimal unShippedQuantity) {
        this.unShippedQuantity = unShippedQuantity;
    }

    public BigDecimal getPickQuantity() {
        return pickQuantity;
    }

    public void setPickQuantity(BigDecimal pickQuantity) {
        this.pickQuantity = pickQuantity;
    }

    public String getOrderLineStatus() {
        return orderLineStatus;
    }

    public void setOrderLineStatus(String orderLineStatus) {
        this.orderLineStatus = orderLineStatus;
    }

    public String getBatchCtrlFlag() {
        return batchCtrlFlag;
    }

    public void setBatchCtrlFlag(String batchCtrlFlag) {
        this.batchCtrlFlag = batchCtrlFlag;
    }

    public BigDecimal getOnhandQuantity() {
        return onhandQuantity;
    }

    public void setOnhandQuantity(BigDecimal onhandQuantity) {
        this.onhandQuantity = onhandQuantity;
    }

    public List<OrderDeliveryLot> getLots() {
        return lots;
    }

    public void setLots(List<OrderDeliveryLot> lots) {
        this.lots = lots;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getOrderLineStatusDesc() {
        return orderLineStatusDesc;
    }

    public void setOrderLineStatusDesc(String orderLineStatusDesc) {
        this.orderLineStatusDesc = orderLineStatusDesc;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public Long getCountItemId() {
        return countItemId;
    }

    public void setCountItemId(Long countItemId) {
        this.countItemId = countItemId;
    }

    public String getCountItemName() {
        return countItemName;
    }

    public void setCountItemName(String countItemName) {
        this.countItemName = countItemName;
    }

    public String getPackageItemName() {
        return packageItemName;
    }

    public void setPackageItemName(String packageItemName) {
        this.packageItemName = packageItemName;
    }

    public Long getPackageItemId() {
        return packageItemId;
    }

    public void setPackageItemId(Long packageItemId) {
        this.packageItemId = packageItemId;
    }

    public String getCountItemNumber() {
        return countItemNumber;
    }

    public void setCountItemNumber(String countItemNumber) {
        this.countItemNumber = countItemNumber;
    }

}