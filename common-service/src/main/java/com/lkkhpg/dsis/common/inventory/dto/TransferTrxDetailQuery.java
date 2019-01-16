/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

/**
 * 移库查询DTO.
 * 
 * @author chuangsheng.zhang.
 */
public class TransferTrxDetailQuery extends TransferTrxDetail {
    private static final long serialVersionUID = 1L;

    // 转入id
    private Long inTrxDetailId;

    // 转出id
    private Long outTrxDetailId;

    // 商品编码
    private String itemNumber;

    // 商品名称
    private String itemName;

    // 商品单位
    private String itemUomCode;

    // 转出数量
    private Long outTrxQty;

    // 转入数量
    private Long inTrxQty;

    // 剩余数量
    private Long remainingQty;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUomCode() {
        return itemUomCode;
    }

    public void setItemUomCode(String itemUomCode) {
        this.itemUomCode = itemUomCode;
    }

    public Long getOutTrxQty() {
        return outTrxQty;
    }

    public void setOutTrxQty(Long outTrxQty) {
        this.outTrxQty = outTrxQty;
    }

    public Long getInTrxQty() {
        return inTrxQty;
    }

    public void setInTrxQty(Long inTrxQty) {
        this.inTrxQty = inTrxQty;
    }

    public Long getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(Long remainingQty) {
        this.remainingQty = remainingQty;
    }

    public Long getInTrxDetailId() {
        return inTrxDetailId;
    }

    public void setInTrxDetailId(Long inTrxDetailId) {
        this.inTrxDetailId = inTrxDetailId;
    }

    public Long getOutTrxDetailId() {
        return outTrxDetailId;
    }

    public void setOutTrxDetailId(Long outTrxDetailId) {
        this.outTrxDetailId = outTrxDetailId;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

}
