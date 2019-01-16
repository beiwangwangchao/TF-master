package com.lkkhpg.dsis.common.report.dto;

import java.math.BigDecimal;

/**
 * 打印清单 TWDELIVERY-LINE.
 * 
 * @author lipeng.lin
 *
 */
public class TWPrintDeliveryRecordLine {

    // private String deliveryLineId;
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

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
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

    public BigDecimal getRowNum() {
        return rowNum;
    }

    public void setRowNum(BigDecimal rowNum) {
        this.rowNum = rowNum;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

}
