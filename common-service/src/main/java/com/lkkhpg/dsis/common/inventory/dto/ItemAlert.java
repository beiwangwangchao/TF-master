/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.common.inventory.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 库存预警程序DTO.
 * 
 * @author liang.rao
 *
 */
public class ItemAlert extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long orgId;

    private Long itemId;

    private String itemNumber;

    private Date expiryDate;

    private String enabledFlag;

    private Long quantityAlert;

    private String expiryAlert;

    private String lotNumber;

    private BigDecimal quantity;

    private String itemName;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Long getQuantityAlert() {
        return quantityAlert;
    }

    public void setQuantityAlert(Long quantityAlert) {
        this.quantityAlert = quantityAlert;
    }

    public String getExpiryAlert() {
        return expiryAlert;
    }

    public void setExpiryAlert(String expiryAlert) {
        this.expiryAlert = expiryAlert;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
