/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 发运单批次DTO.
 * 
 * @author liang.rao
 *
 */
public class OrderDeliveryLot extends BaseDTO {
    private Long deliveryLotId;

    private Long deliveryLineId;

    private Long deliveryId;

    private String lotNumber;

    private BigDecimal outstandingQty;

    private BigDecimal returnQty;

    private String remark;

    private Date expiryDate;

    private BigDecimal onhandQuantity;

    public Long getDeliveryLotId() {
        return deliveryLotId;
    }

    public void setDeliveryLotId(Long deliveryLotId) {
        this.deliveryLotId = deliveryLotId;
    }

    public Long getDeliveryLineId() {
        return deliveryLineId;
    }

    public void setDeliveryLineId(Long deliveryLineId) {
        this.deliveryLineId = deliveryLineId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber == null ? null : lotNumber.trim();
    }

    public BigDecimal getOutstandingQty() {
        return outstandingQty;
    }

    public void setOutstandingQty(BigDecimal outstandingQty) {
        this.outstandingQty = outstandingQty;
    }

    public BigDecimal getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(BigDecimal returnQty) {
        this.returnQty = returnQty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getOnhandQuantity() {
        return onhandQuantity;
    }

    public void setOnhandQuantity(BigDecimal onhandQuantity) {
        this.onhandQuantity = onhandQuantity;
    }

}