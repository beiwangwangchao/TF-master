/*
 *
 */
package com.lkkhpg.dsis.integration.payment.dto;

/**
 * @author shiliyan
 *
 */
public class PaymentOrder {
    private String orderNumber;
    private String amount;
    private Long key;
    private String sourceType;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public PaymentOrder() {
    }

    public PaymentOrder(String orderNumber, String amount, Long key, String sourceType) {
        super();
        this.orderNumber = orderNumber;
        this.amount = amount;
        this.key = key;
        this.sourceType = sourceType;
    }

}
