/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 订单支付事务处理.
 * 
 * @author runbai.chen
 */
public class OrderPayTransaction extends BaseDTO {
    private Long transactionId;

    private String transactionCode;

    private String sourceType;

    private Long sourceKey;

    private String bankPaymentCode;

    private String transactionType;

    private String transactionStatus;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode == null ? null : transactionCode.trim();
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public Long getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(Long sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getBankPaymentCode() {
        return bankPaymentCode;
    }

    public void setBankPaymentCode(String bankPaymentCode) {
        this.bankPaymentCode = bankPaymentCode == null ? null : bankPaymentCode.trim();
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType == null ? null : transactionType.trim();
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus == null ? null : transactionStatus.trim();
    }
}