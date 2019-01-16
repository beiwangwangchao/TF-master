/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.payment.dto;

/**
 * 中国信托银联回调.
 * @author shiliyan
 *
 */
public class ChinatrustUnionCallbackModel extends PaymentTransactionModel{

    private String version;
    private String charset;
    private String xid;
    private String lidm;
    private String purchAmt;
    private String orderStatus;
    private String respCode;
    private String respMsg;
    private String traceNumber;
    private String traceTime;
    private String qid;
    private String settleAmount;
    private String settleCurrency;
    private String settleDate;
    private String exchangeRate;
    private String exchangeDate;
    private String inMac;

    public String getVersion() {
        return version;
    }

    public String getCharset() {
        return charset;
    }

    public String getXid() {
        return xid;
    }

    public String getLidm() {
        return lidm;
    }

    public String getPurchAmt() {
        return purchAmt;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public String getTraceNumber() {
        return traceNumber;
    }

    public String getTraceTime() {
        return traceTime;
    }

    public String getQid() {
        return qid;
    }

    public String getSettleAmount() {
        return settleAmount;
    }

    public String getSettleCurrency() {
        return settleCurrency;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public String getExchangeDate() {
        return exchangeDate;
    }

    public String getInMac() {
        return inMac;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public void setLidm(String lidm) {
        this.lidm = lidm;
    }

    public void setPurchAmt(String purchAmt) {
        this.purchAmt = purchAmt;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public void setTraceNumber(String traceNumber) {
        this.traceNumber = traceNumber;
    }

    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public void setSettleAmount(String settleAmount) {
        this.settleAmount = settleAmount;
    }

    public void setSettleCurrency(String settleCurrency) {
        this.settleCurrency = settleCurrency;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setExchangeDate(String exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public void setInMac(String inMac) {
        this.inMac = inMac;
    }

}
