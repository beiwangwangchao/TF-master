/*
 *
 */
package com.lkkhpg.dsis.common.inventory.purchase.dto;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

import java.math.BigDecimal;

/**
 * 采购订单行表.
 * @author HuangJiaJing
 *
 */
@Table(name = "PO_LINE")
public class PoLine extends BaseDTO {
    
    @Id
    @Column(name = "PO_LINE_ID")
    private Long poLineId;

    private Long poHeaderId;

    private Long itemId;

    private String uomCode;

    private Long packageQuantity;

    private Long minQuantity;

    private Long quantity;
    
    private String itemNumber;
    
    private String itemName;
    
    private String unitName;

    private BigDecimal unitPrice;

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getPoLineId() {
        return poLineId;
    }

    public void setPoLineId(Long poLineId) {
        this.poLineId = poLineId;
    }

    public Long getPoHeaderId() {
        return poHeaderId;
    }

    public void setPoHeaderId(Long poHeaderId) {
        this.poHeaderId = poHeaderId;
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