/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import javax.validation.constraints.NotNull;

/**
 * 订单支付接口 DTO.
 * 
 * @author zhenyang.he
 */
public class OrderPaymentUpdate {

    @NotNull
    private String orderNumber;

    private String authCode;

    private String authDate;

    private String lastFourDigitPAN;

    @NotNull
    private String orderStatus;
    
    private String transactionRefNo;
    
    private String paymentMethod;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthDate() {
        return authDate;
    }

    public void setAuthDate(String authDate) {
        this.authDate = authDate;
    }

    public String getLastFourDigitPAN() {
        return lastFourDigitPAN;
    }

    public void setLastFourDigitPAN(String lastFourDigitPAN) {
        this.lastFourDigitPAN = lastFourDigitPAN;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTransactionRefNo() {
        return transactionRefNo;
    }

    public void setTransactionRefNo(String transactionRefNo) {
        this.transactionRefNo = transactionRefNo;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
}
