/*
 *
 */
package com.lkkhpg.dsis.integration.payment.dto;

/**
 * 中国信托银联支付查询.
 * @author shiliyan
 *
 */

public class ChinatrustUnionQueryModel extends PaymentTransactionModel{

    private String rtnCode;

    private String respCode;
    private String respMsg;
    private String inMac;
    private String lidm;
    private String xid;
    private String respTime;
    private String requestTime;
    private String orderStatus;
    private String traceNumber;
    private String traceTime;
    private String qid;
    private String settleAmount;
    private String settleCurrency;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getInMac() {
        return inMac;
    }

    public void setInMac(String inMac) {
        this.inMac = inMac;
    }

    public String getLidm() {
        return lidm;
    }

    public void setLidm(String lidm) {
        this.lidm = lidm;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getRespTime() {
        return respTime;
    }

    public void setRespTime(String respTime) {
        this.respTime = respTime;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTraceNumber() {
        return traceNumber;
    }

    public void setTraceNumber(String traceNumber) {
        this.traceNumber = traceNumber;
    }

    public String getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(String settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getSettleCurrency() {
        return settleCurrency;
    }

    public void setSettleCurrency(String settleCurrency) {
        this.settleCurrency = settleCurrency;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(String exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    private String settleDate;
    private String exchangeRate;
    private String exchangeDate;

    public ChinatrustUnionQueryModel() {
        super();
    }

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

}
