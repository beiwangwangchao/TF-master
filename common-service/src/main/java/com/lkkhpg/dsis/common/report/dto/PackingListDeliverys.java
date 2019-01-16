/*
 *
 */
package com.lkkhpg.dsis.common.report.dto;

import java.math.BigDecimal;

/**
 * PACKING-LIST DELIVERY ALL 获取发运单的所有数据.
 * 
 * @author zhenyang.he
 *
 */
public class PackingListDeliverys {

    // 头数据字段
    private String inventory;
    private String distributorName;
    private String distributorNo;
    private String ReceiptNo;
    private String shipmentNo;
    private String deliveryDate;

    // 行记录字段
    private BigDecimal lineId;
    private String productCode;
    private String packageCode;
    private String desctiption;
    private BigDecimal qty;
    private String lotNumber;
    private String expiryDate;
    private BigDecimal shipmentQty;
    private BigDecimal notShippedQty;
    private BigDecimal totalShipmentQty;
    private BigDecimal totalNotShippedQty;
    private BigDecimal page;
    private BigDecimal pages;
    private String flag;

    public BigDecimal getPages() {
        return pages;
    }

    public void setPages(BigDecimal pages) {
        this.pages = pages;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    public String getReceiptNo() {
        return ReceiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        ReceiptNo = receiptNo;
    }

    public String getShipmentNo() {
        return shipmentNo;
    }

    public void setShipmentNo(String shipmentNo) {
        this.shipmentNo = shipmentNo;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getLineId() {
        return lineId;
    }

    public void setLineId(BigDecimal lineId) {
        this.lineId = lineId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getDesctiption() {
        return desctiption;
    }

    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(BigDecimal shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public BigDecimal getNotShippedQty() {
        return notShippedQty;
    }

    public void setNotShippedQty(BigDecimal notShippedQty) {
        this.notShippedQty = notShippedQty;
    }

    public BigDecimal getTotalShipmentQty() {
        return totalShipmentQty;
    }

    public void setTotalShipmentQty(BigDecimal totalShipmentQty) {
        this.totalShipmentQty = totalShipmentQty;
    }

    public BigDecimal getTotalNotShippedQty() {
        return totalNotShippedQty;
    }

    public void setTotalNotShippedQty(BigDecimal totalNotShippedQty) {
        this.totalNotShippedQty = totalNotShippedQty;
    }

    public BigDecimal getPage() {
        return page;
    }

    public void setPage(BigDecimal page) {
        this.page = page;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
