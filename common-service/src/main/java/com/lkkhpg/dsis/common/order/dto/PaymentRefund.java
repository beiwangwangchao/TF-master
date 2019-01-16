/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 退款dto.
 * 
 * @author gulin
 *
 */
public class PaymentRefund extends BaseDTO {
    // 添加支付信息字段，查询支付信息记录以便于退款
    private Long orderHeaderId;

    private Long refundId;

    private String sourceType;

    private Long sourceId;

    private Long salesOrgId;

    private String paymentMethod;

    private String paymentMethodInfo;

    private BigDecimal paymentAmount;

    private String transactionNumber;

    private String bankCode;

    private String creditCardType;

    private String tailNumber;

    private String remark;

    /**
     * ecupon的id.
     */
    private Long voucherId;

    /**
     * ecupon的抵扣金额.
     */
    private BigDecimal discountValue;

    /**
     * ecupon的名称.
     */
    private String name;

    /**
     * 支付行主键Id.
     */
    private Long paymentId;

    public Long getOrderHeaderId() {
        return orderHeaderId;
    }

    public void setOrderHeaderId(Long orderHeaderId) {
        this.orderHeaderId = orderHeaderId;
    }

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod == null ? null : paymentMethod.trim();
    }

    public String getPaymentMethodInfo() {
        return paymentMethodInfo;
    }

    public void setPaymentMethodInfo(String paymentMethodInfo) {
        this.paymentMethodInfo = paymentMethodInfo == null ? null : paymentMethodInfo.trim();
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber == null ? null : transactionNumber.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType == null ? null : creditCardType.trim();
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber == null ? null : tailNumber.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

}