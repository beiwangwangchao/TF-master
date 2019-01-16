/*
 *
 */
package com.lkkhpg.dsis.common.promotion.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 优惠券赠品DTO.
 * @author hanrui.huang
 *
 */
public class PdmVoucherGift extends BaseDTO {
    private Long voucherGiftId;

    private Long voucherId;

    private Long itemId;

    private Long quantity;

    private String itemNumber;

    private String itemName;

    public Long getVoucherGiftId() {
        return voucherGiftId;
    }

    public void setVoucherGiftId(Long voucherGiftId) {
        this.voucherGiftId = voucherGiftId;
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