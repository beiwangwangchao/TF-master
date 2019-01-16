/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 退货单退款方式DTO.
 * 
 * @author houmin
 *
 */
public class SalesRtnRefund extends BaseDTO {
    private Long returnRefundId;

    private Long returnId;

    private String refundMethod;

    private BigDecimal refundAmount;

    private String remark;

    private Long salesOrgId;

    public Long getReturnRefundId() {
        return returnRefundId;
    }

    public void setReturnRefundId(Long returnRefundId) {
        this.returnRefundId = returnRefundId;
    }

    public Long getReturnId() {
        return returnId;
    }

    public void setReturnId(Long returnId) {
        this.returnId = returnId;
    }

    public String getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(String refundMethod) {
        this.refundMethod = refundMethod == null ? null : refundMethod.trim();
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

}