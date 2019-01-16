/*
 *
 */
package com.lkkhpg.dsis.common.report.dto;

import java.math.BigDecimal;

/**
 * PRINT-DEIVERY-RECODE-TW 获取发运单的所有数据.
 * 
 * @author zhenyang.he
 *
 */
public class TWPrintDeliveryRecodes {

    // 头记录
    private String deliveryNumber;
    private String orderNumber;
    private String invoiceNumber;
    private String inventory;
    private String saleOrderType;
    private String deliveryType;
    private String deliveryDate;
    private String distributorName;
    private String contactName;
    private String phone;
    private String address;
    private String remark;

    // 行记录
    private BigDecimal rowNum;
    private String lineId;
    private String productCode;
    private String productDesctiption;
    private BigDecimal qty;
    private String lotNumber;
    private String expiryDate;
    private BigDecimal shipmentQty;
    private BigDecimal noShipmentQty;
    private BigDecimal subTotalShipmentQty;
    private BigDecimal subTotalNoShippedQty;
    private BigDecimal totalShipmentQty;
    private BigDecimal totalNoShippedQty;
    private BigDecimal subTotalQty;
    private BigDecimal totalQty;
    private int page;
    private int pages;
    private int pageTotal;
    private String flag;
    private String packCode;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getSaleOrderType() {
        return saleOrderType;
    }

    public void setSaleOrderType(String saleOrderType) {
        this.saleOrderType = saleOrderType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getRowNum() {
        return rowNum;
    }

    public void setRowNum(BigDecimal rowNum) {
        this.rowNum = rowNum;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDesctiption() {
        return productDesctiption;
    }

    public void setProductDesctiption(String productDesctiption) {
        this.productDesctiption = productDesctiption;
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

    public BigDecimal getNoShipmentQty() {
        return noShipmentQty;
    }

    public void setNoShipmentQty(BigDecimal noShipmentQty) {
        this.noShipmentQty = noShipmentQty;
    }

    public BigDecimal getSubTotalShipmentQty() {
        return subTotalShipmentQty;
    }

    public void setSubTotalShipmentQty(BigDecimal subTotalShipmentQty) {
        this.subTotalShipmentQty = subTotalShipmentQty;
    }

    public BigDecimal getSubTotalNoShippedQty() {
        return subTotalNoShippedQty;
    }

    public void setSubTotalNoShippedQty(BigDecimal subTotalNoShippedQty) {
        this.subTotalNoShippedQty = subTotalNoShippedQty;
    }

    public BigDecimal getTotalShipmentQty() {
        return totalShipmentQty;
    }

    public void setTotalShipmentQty(BigDecimal totalShipmentQty) {
        this.totalShipmentQty = totalShipmentQty;
    }

    public BigDecimal getTotalNoShippedQty() {
        return totalNoShippedQty;
    }

    public void setTotalNoShippedQty(BigDecimal totalNoShippedQty) {
        this.totalNoShippedQty = totalNoShippedQty;
    }

    public BigDecimal getSubTotalQty() {
        return subTotalQty;
    }

    public void setSubTotalQty(BigDecimal subTotalQty) {
        this.subTotalQty = subTotalQty;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

}
