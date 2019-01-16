/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import java.io.Serializable;

/**
 * 商品详细信息,前台交互使用.
 * 
 * @author wuyichu
 */
public class ItemDetail implements Serializable {

    private Long itemId;

    private String itemNumber;

    private String barCode;

    private String itemName;

    private String description;

    private Double unitPrice;

    private Double pv;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getPv() {
        return pv;
    }

    public void setPv(Double pv) {
        this.pv = pv;
    }

}
