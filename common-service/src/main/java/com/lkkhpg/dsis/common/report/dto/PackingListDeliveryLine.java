/*
 *
 */
package com.lkkhpg.dsis.common.report.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * PACKING-LIST 打印清单 DELIVERY-LINE.
 * 
 * @author zhenyang.he
 *
 */
public class PackingListDeliveryLine extends BaseDTO{
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
    public String getPackageCode() {
        return packageCode;
    }
    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
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
    public BigDecimal getnotShippedQty() {
        return notShippedQty;
    }
    public void setnotShippedQty(BigDecimal notShippedQty) {
        this.notShippedQty = notShippedQty;
    }
    
    
}
