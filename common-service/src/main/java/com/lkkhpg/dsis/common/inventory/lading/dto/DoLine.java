/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.lading.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 提货单行dto.
 * @author liuxuan
 *
 */
@Table(name = "DO_LINE")
public class DoLine extends BaseDTO {
    @Id
    @Column(name = "DO_LINE_ID")
    private Long doLineId;

    private Long doHeaderId;

    private Long itemId;

    private String uomCode;

    private Long packageQuantity;

    private Long minQuantity;

    private Long quantity;
    
    private String itemNumber;
    
    private String itemName;
    
    private String unitName;

    
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getDoLineId() {
        return doLineId;
    }

    public void setDoLineId(Long doLineId) {
        this.doLineId = doLineId;
    }

    public Long getDoHeaderId() {
        return doHeaderId;
    }

    public void setDoHeaderId(Long doHeaderId) {
        this.doHeaderId = doHeaderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode == null ? null : uomCode.trim();
    }

    public Long getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(Long packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public Long getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Long minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}