/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.payment.dto;

/**
 * MIGS查询结果.
 * 
 * @author shiliyan
 *
 */
public class MIGSRefundModel extends MIGSModel {

    private String message;
    private String batchNo;
    private String cardType;
    private String orderInfo;
    private String receiptNo;
    private String authorizeID;
    private String transactionNr;
    private String acqResponseCode;
    private String txnResponseCode;

    // Capture Data
    private String shopTransNo;
    private String authorisedAmount;
    private String capturedAmount;
    private String refundedAmount;
    private String ticketNumber;

    private String error = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getAuthorizeID() {
        return authorizeID;
    }

    public void setAuthorizeID(String authorizeID) {
        this.authorizeID = authorizeID;
    }

    public String getTransactionNr() {
        return transactionNr;
    }

    public void setTransactionNr(String transactionNr) {
        this.transactionNr = transactionNr;
    }

    public String getAcqResponseCode() {
        return acqResponseCode;
    }

    public void setAcqResponseCode(String acqResponseCode) {
        this.acqResponseCode = acqResponseCode;
    }

    public String getTxnResponseCode() {
        return txnResponseCode;
    }

    public void setTxnResponseCode(String txnResponseCode) {
        this.txnResponseCode = txnResponseCode;
    }

    public String getShopTransNo() {
        return shopTransNo;
    }

    public void setShopTransNo(String shopTransNo) {
        this.shopTransNo = shopTransNo;
    }

    public String getAuthorisedAmount() {
        return authorisedAmount;
    }

    public void setAuthorisedAmount(String authorisedAmount) {
        this.authorisedAmount = authorisedAmount;
    }

    public String getCapturedAmount() {
        return capturedAmount;
    }

    public void setCapturedAmount(String capturedAmount) {
        this.capturedAmount = capturedAmount;
    }

    public String getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(String refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public MIGSRefundModel(String message, String batchNo, String cardType, String orderInfo, String receiptNo,
            String authorizeID, String transactionNr, String acqResponseCode, String txnResponseCode,
            String shopTransNo, String authorisedAmount, String capturedAmount, String refundedAmount,
            String ticketNumber, String error) {
        super();
        this.message = message;
        this.batchNo = batchNo;
        this.cardType = cardType;
        this.orderInfo = orderInfo;
        this.receiptNo = receiptNo;
        this.authorizeID = authorizeID;
        this.transactionNr = transactionNr;
        this.acqResponseCode = acqResponseCode;
        this.txnResponseCode = txnResponseCode;
        this.shopTransNo = shopTransNo;
        this.authorisedAmount = authorisedAmount;
        this.capturedAmount = capturedAmount;
        this.refundedAmount = refundedAmount;
        this.ticketNumber = ticketNumber;
        this.error = error;
    }
  
    
    
}
