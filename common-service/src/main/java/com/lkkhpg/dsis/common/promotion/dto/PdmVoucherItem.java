/*
 *
 */
package com.lkkhpg.dsis.common.promotion.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 优惠券商品DTO.
 * @author hanrui.huang
 *
 */
public class PdmVoucherItem extends BaseDTO {
    private Long voucherItemId;

    private Long voucherId;

    private Long itemId;

    private Long quantity;

    private String itemNumber;

    private String itemName;

    public Long getVoucherItemId() {
        return voucherItemId;
    }

    public void setVoucherItemId(Long voucherItemId) {
        this.voucherItemId = voucherItemId;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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
}