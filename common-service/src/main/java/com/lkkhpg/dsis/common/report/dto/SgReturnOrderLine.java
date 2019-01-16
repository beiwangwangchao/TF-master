/*
 * #{copyright}#
 */
package com.lkkhpg.dsis.common.report.dto;

import java.math.BigDecimal;

/**
 * 新加坡退货单报表的行数据DTO.
 * 
 * @author zhenyang.he
 *
 */
public class SgReturnOrderLine {

    private Long rowNum;

    private String itemNumber;

    private String itemName;

    private BigDecimal quantity;

    private BigDecimal pv;

    private BigDecimal unitSellingPrice;

    private BigDecimal unitOrigPrice;

    private BigDecimal amount;

    private BigDecimal sgt;

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPv() {
        return pv;
    }

    public void setPv(BigDecimal pv) {
        this.pv = pv;
    }

    public BigDecimal getUnitSellingPrice() {
        return unitSellingPrice;
    }

    public void setUnitSellingPrice(BigDecimal unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }

    public BigDecimal getUnitOrigPrice() {
        return unitOrigPrice;
    }

    public void setUnitOrigPrice(BigDecimal unitOrigPrice) {
        this.unitOrigPrice = unitOrigPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSgt() {
        return sgt;
    }

    public void setSgt(BigDecimal sgt) {
        this.sgt = sgt;
    }

}
