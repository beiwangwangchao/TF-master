/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品属性视图dto.
 * 
 * @author zhang.chuangsheng
 */

public class InvItemPropertyV extends BaseDTO {
    private Long organizationId;

    private Long itemId;

    private String itemNumber;

    private String itemName;

    private String description;

    private String barCode;

    private String itemType;

    private String uomCode;

    private String redeemFlag;

    private String starterAid;

    private String enabledFlag;

    private String countType;

    private String countStockFlag;

    private String countItemId;

    private String lotControlFlag;

    private String quantityAlert;

    private String expiryAlert;

    private String minOrderQuantity;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
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
        this.itemNumber = itemNumber == null ? null : itemNumber.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode == null ? null : uomCode.trim();
    }

    public String getRedeemFlag() {
        return redeemFlag;
    }

    public void setRedeemFlag(String redeemFlag) {
        this.redeemFlag = redeemFlag == null ? null : redeemFlag.trim();
    }

    public String getStarterAid() {
        return starterAid;
    }

    public void setStarterAid(String starterAid) {
        this.starterAid = starterAid == null ? null : starterAid.trim();
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType == null ? null : countType.trim();
    }

    public String getCountStockFlag() {
        return countStockFlag;
    }

    public void setCountStockFlag(String countStockFlag) {
        this.countStockFlag = countStockFlag == null ? null : countStockFlag.trim();
    }

    public String getCountItemId() {
        return countItemId;
    }

    public void setCountItemId(String countItemId) {
        this.countItemId = countItemId == null ? null : countItemId.trim();
    }

    public String getLotControlFlag() {
        return lotControlFlag;
    }

    public void setLotControlFlag(String lotControlFlag) {
        this.lotControlFlag = lotControlFlag == null ? null : lotControlFlag.trim();
    }

    public String getQuantityAlert() {
        return quantityAlert;
    }

    public void setQuantityAlert(String quantityAlert) {
        this.quantityAlert = quantityAlert == null ? null : quantityAlert.trim();
    }

    public String getExpiryAlert() {
        return expiryAlert;
    }

    public void setExpiryAlert(String expiryAlert) {
        this.expiryAlert = expiryAlert == null ? null : expiryAlert.trim();
    }

    public String getMinOrderQuantity() {
        return minOrderQuantity;
    }

    public void setMinOrderQuantity(String minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity == null ? null : minOrderQuantity.trim();
    }
}