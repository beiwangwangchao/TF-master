/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * @author houmin 订单支付.
 *
 */
public class OrderPayment extends BaseDTO {
    // 头Id
    @NotEmpty
    private Long paymentId;

    // 销售订单头ID
    @NotEmpty
    private Long orderHeaderId;

    // 订单类型
    @NotEmpty
    private String orderType;

    // 支付总积分
    private BigDecimal redeemPointCount;

    // 销售区域ID
    @NotEmpty
    private Long salesOrgId;

    // 支付方式
    @NotEmpty
    private String paymentMethod;

    // 支付方式具体信息：POS机、银行
    private String paymentMethodInfo;

    // 支付金额
    @NotEmpty
    private BigDecimal paymentAmount;

    // 交易凭证号
    private String transactionNumber;

    // 银行
    private String bankCode;

    // 信用卡类型
    private String creditCardType;

    // 尾号
    private String tailNumber;

    // 备注
    private String remark;

    // 支票号
    private String chequeNumber;

    // 作废事务参考信息
    private String voidTrxReference;

    // 作废失败原因
    private String voidFailReason;

    // 状态
    private String status;

    // 失败原因
    private String failReason;

    // 每种支付方式金额
    private BigDecimal paymethodAmtSum;
    
    private Long voucherId;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getOrderHeaderId() {
        return orderHeaderId;
    }

    public void setOrderHeaderId(Long orderHeaderId) {
        this.orderHeaderId = orderHeaderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getRedeemPointCount() {
        return redeemPointCount;
    }

    public void setRedeemPointCount(BigDecimal redeemPointCount) {
        this.redeemPointCount = redeemPointCount;
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
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodInfo() {
        return paymentMethodInfo;
    }

    public void setPaymentMethodInfo(String paymentMethodInfo) {
        this.paymentMethodInfo = paymentMethodInfo;
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
        this.transactionNumber = transactionNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getVoidTrxReference() {
        return voidTrxReference;
    }

    public void setVoidTrxReference(String voidTrxReference) {
        this.voidTrxReference = voidTrxReference;
    }

    public String getVoidFailReason() {
        return voidFailReason;
    }

    public void setVoidFailReason(String voidFailReason) {
        this.voidFailReason = voidFailReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public BigDecimal getPaymethodAmtSum() {
        return paymethodAmtSum;
    }

    public void setPaymethodAmtSum(BigDecimal paymethodAmtSum) {
        this.paymethodAmtSum = paymethodAmtSum;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

}