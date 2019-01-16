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
public class MIGSQueryModel extends MIGSModel {

    private String virtualPaymentClientURL;

    public String getVirtualPaymentClientURL() {
        return virtualPaymentClientURL;
    }

    public void setVirtualPaymentClientURL(String virtualPaymentClientURL) {
        this.virtualPaymentClientURL = virtualPaymentClientURL;
    }

    private String message;

    private String batchNo;
    private String cardType;
    private String orderInfo;
    private String receiptNo;
    private String authorizeID;
    private String transactionNo;
    private String acqResponseCode;
    private String txnResponseCode;

    // CSC Receipt Data
    private String cscResultCode;
    private String cscRequestCode;
    private String cscACQRespCode;

    // AVS Receipt Data
    private String avs_City;
    private String avs_Country;
    private String avs_Street01;
    private String avs_PostCode;
    private String avs_StateProv;
    private String avsResultCode;
    private String avsRequestCode;
    private String avsACQRespCode;

    // 3-D Secure Data
    private String transType3DS;
    private String verStatus3DS;
    private String token3DS;
    private String secureLevel3DS;
    private String enrolled3DS;
    private String xid3DS;
    private String eci3DS;
    private String status3DS;

    // Financial Transaction Data
    private String shopTransNo;
    private String authorisedAmount;
    private String capturedAmount;
    private String refundedAmount;
    private String ticketNumber;

    // Specific QueryDR Data
    private String drExists;
    private String multipleDRs;

    private String error;
    private boolean amaTransaction;

    public String getMessage() {
        return message;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public String getCardType() {
        return cardType;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public String getAuthorizeID() {
        return authorizeID;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public String getAcqResponseCode() {
        return acqResponseCode;
    }

    public String getTxnResponseCode() {
        return txnResponseCode;
    }

    public String getCscResultCode() {
        return cscResultCode;
    }

    public String getCscRequestCode() {
        return cscRequestCode;
    }

    public String getCscACQRespCode() {
        return cscACQRespCode;
    }

    public String getAvs_City() {
        return avs_City;
    }

    public String getAvs_Country() {
        return avs_Country;
    }

    public String getAvs_Street01() {
        return avs_Street01;
    }

    public String getAvs_PostCode() {
        return avs_PostCode;
    }

    public String getAvs_StateProv() {
        return avs_StateProv;
    }

    public String getAvsResultCode() {
        return avsResultCode;
    }

    public String getAvsRequestCode() {
        return avsRequestCode;
    }

    public String getAvsACQRespCode() {
        return avsACQRespCode;
    }

    public String getTransType3DS() {
        return transType3DS;
    }

    public String getVerStatus3DS() {
        return verStatus3DS;
    }

    public String getToken3DS() {
        return token3DS;
    }

    public String getSecureLevel3DS() {
        return secureLevel3DS;
    }

    public String getEnrolled3DS() {
        return enrolled3DS;
    }

    public String getXid3DS() {
        return xid3DS;
    }

    public String getEci3DS() {
        return eci3DS;
    }

    public String getStatus3DS() {
        return status3DS;
    }

    public String getShopTransNo() {
        return shopTransNo;
    }

    public String getAuthorisedAmount() {
        return authorisedAmount;
    }

    public String getCapturedAmount() {
        return capturedAmount;
    }

    public String getRefundedAmount() {
        return refundedAmount;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getDrExists() {
        return drExists;
    }

    public String getMultipleDRs() {
        return multipleDRs;
    }

    public String getError() {
        return error;
    }

    public boolean isAmaTransaction() {
        return amaTransaction;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public void setAuthorizeID(String authorizeID) {
        this.authorizeID = authorizeID;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public void setAcqResponseCode(String acqResponseCode) {
        this.acqResponseCode = acqResponseCode;
    }

    public void setTxnResponseCode(String txnResponseCode) {
        this.txnResponseCode = txnResponseCode;
    }

    public void setCscResultCode(String cscResultCode) {
        this.cscResultCode = cscResultCode;
    }

    public void setCscRequestCode(String cscRequestCode) {
        this.cscRequestCode = cscRequestCode;
    }

    public void setCscACQRespCode(String cscACQRespCode) {
        this.cscACQRespCode = cscACQRespCode;
    }

    public void setAvs_City(String avs_City) {
        this.avs_City = avs_City;
    }

    public void setAvs_Country(String avs_Country) {
        this.avs_Country = avs_Country;
    }

    public void setAvs_Street01(String avs_Street01) {
        this.avs_Street01 = avs_Street01;
    }

    public void setAvs_PostCode(String avs_PostCode) {
        this.avs_PostCode = avs_PostCode;
    }

    public void setAvs_StateProv(String avs_StateProv) {
        this.avs_StateProv = avs_StateProv;
    }

    public void setAvsResultCode(String avsResultCode) {
        this.avsResultCode = avsResultCode;
    }

    public void setAvsRequestCode(String avsRequestCode) {
        this.avsRequestCode = avsRequestCode;
    }

    public void setAvsACQRespCode(String avsACQRespCode) {
        this.avsACQRespCode = avsACQRespCode;
    }

    public void setTransType3DS(String transType3DS) {
        this.transType3DS = transType3DS;
    }

    public void setVerStatus3DS(String verStatus3DS) {
        this.verStatus3DS = verStatus3DS;
    }

    public void setToken3DS(String token3ds) {
        token3DS = token3ds;
    }

    public void setSecureLevel3DS(String secureLevel3DS) {
        this.secureLevel3DS = secureLevel3DS;
    }

    public void setEnrolled3DS(String enrolled3ds) {
        enrolled3DS = enrolled3ds;
    }

    public void setXid3DS(String xid3ds) {
        xid3DS = xid3ds;
    }

    public void setEci3DS(String eci3ds) {
        eci3DS = eci3ds;
    }

    public void setStatus3DS(String status3ds) {
        status3DS = status3ds;
    }

    public void setShopTransNo(String shopTransNo) {
        this.shopTransNo = shopTransNo;
    }

    public void setAuthorisedAmount(String authorisedAmount) {
        this.authorisedAmount = authorisedAmount;
    }

    public void setCapturedAmount(String capturedAmount) {
        this.capturedAmount = capturedAmount;
    }

    public void setRefundedAmount(String refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void setDrExists(String drExists) {
        this.drExists = drExists;
    }

    public void setMultipleDRs(String multipleDRs) {
        this.multipleDRs = multipleDRs;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setAmaTransaction(boolean amaTransaction) {
        this.amaTransaction = amaTransaction;
    }

    public MIGSQueryModel(String virtualPaymentClientURL, String message, String batchNo, String cardType,
            String orderInfo, String receiptNo, String authorizeID, String transactionNo, String acqResponseCode,
            String txnResponseCode, String cscResultCode, String cscRequestCode, String cscACQRespCode, String avs_City,
            String avs_Country, String avs_Street01, String avs_PostCode, String avs_StateProv, String avsResultCode,
            String avsRequestCode, String avsACQRespCode, String transType3DS, String verStatus3DS, String token3ds,
            String secureLevel3DS, String enrolled3ds, String xid3ds, String eci3ds, String status3ds,
            String shopTransNo, String authorisedAmount, String capturedAmount, String refundedAmount,
            String ticketNumber, String drExists, String multipleDRs, String error, boolean amaTransaction) {
        super();
        this.virtualPaymentClientURL = virtualPaymentClientURL;
        this.message = message;
        this.batchNo = batchNo;
        this.cardType = cardType;
        this.orderInfo = orderInfo;
        this.receiptNo = receiptNo;
        this.authorizeID = authorizeID;
        this.transactionNo = transactionNo;
        this.acqResponseCode = acqResponseCode;
        this.txnResponseCode = txnResponseCode;
        this.cscResultCode = cscResultCode;
        this.cscRequestCode = cscRequestCode;
        this.cscACQRespCode = cscACQRespCode;
        this.avs_City = avs_City;
        this.avs_Country = avs_Country;
        this.avs_Street01 = avs_Street01;
        this.avs_PostCode = avs_PostCode;
        this.avs_StateProv = avs_StateProv;
        this.avsResultCode = avsResultCode;
        this.avsRequestCode = avsRequestCode;
        this.avsACQRespCode = avsACQRespCode;
        this.transType3DS = transType3DS;
        this.verStatus3DS = verStatus3DS;
        token3DS = token3ds;
        this.secureLevel3DS = secureLevel3DS;
        enrolled3DS = enrolled3ds;
        xid3DS = xid3ds;
        eci3DS = eci3ds;
        status3DS = status3ds;
        this.shopTransNo = shopTransNo;
        this.authorisedAmount = authorisedAmount;
        this.capturedAmount = capturedAmount;
        this.refundedAmount = refundedAmount;
        this.ticketNumber = ticketNumber;
        this.drExists = drExists;
        this.multipleDRs = multipleDRs;
        this.error = error;
        this.amaTransaction = amaTransaction;
    }

    public MIGSQueryModel() {
    }

}
