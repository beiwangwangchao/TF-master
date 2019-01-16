/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 退货批次DTO.
 * 
 * @author houmin
 *
 */
public class SalesRtnLot extends BaseDTO {
    private Long returnLotId;

    private Long returnLineId;

    private String lotNumber;

    /**
     * 批次退货数量.
     */
    private BigDecimal quantity;

    private String remark;
    /**
     * 批次到期日.
     */
    private Date deliveryEndDate;

    private Long salesOrgId;
    /**
     * 对应订单行及批次的已发运数量.
     */
    private BigDecimal outstandingQty;
    /**
     * 退回数量.
     */
    private BigDecimal backQty;
    /**
     * 退货数量.
     */
    private BigDecimal returnQty;
    /**
     * 同一订单行和批次可退货数量.
     */
    private BigDecimal enabledLotQty;

    public Long getReturnLotId() {
        return returnLotId;
    }

    public void setReturnLotId(Long returnLotId) {
        this.returnLotId = returnLotId;
    }

    public Long getReturnLineId() {
        return returnLineId;
    }

    public void setReturnLineId(Long returnLineId) {
        this.returnLineId = returnLineId;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber == null ? null : lotNumber.trim();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getDeliveryEndDate() {
        return deliveryEndDate;
    }

    public void setDeliveryEndDate(Date deliveryEndDate) {
        this.deliveryEndDate = deliveryEndDate;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public BigDecimal getOutstandingQty() {
        return outstandingQty;
    }

    public void setOutstandingQty(BigDecimal outstandingQty) {
        this.outstandingQty = outstandingQty;
    }

    public BigDecimal getBackQty() {
        return backQty;
    }

    public void setBackQty(BigDecimal backQty) {
        this.backQty = backQty;
    }

    public BigDecimal getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(BigDecimal returnQty) {
        this.returnQty = returnQty;
    }

    public BigDecimal getEnabledLotQty() {
        return enabledLotQty;
    }

    public void setEnabledLotQty(BigDecimal enabledLotQty) {
        this.enabledLotQty = enabledLotQty;
    }

}