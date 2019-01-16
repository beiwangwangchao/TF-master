/*
 *
 */
package com.lkkhpg.dsis.common.report.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * PACKING-LIST-HK LineDto.
 * 
 * @author zyhe
 *
 */
public class PackingListHkSalesLine extends BaseDTO {

    private String itemName;
    private String itemNumber;
    private String inventory;
    private BigDecimal notShippedQty;
    private BigDecimal qty;
    private BigDecimal shipmentQty;
    private BigDecimal totalShipmentQty;
    private BigDecimal totalNotShippedQty;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public BigDecimal getNotShippedQty() {
        return notShippedQty;
    }

    public void setNotShippedQty(BigDecimal notShippedQty) {
        this.notShippedQty = notShippedQty;
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

}
