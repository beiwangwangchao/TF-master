/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.payment.dto;

/**
 * payment支付结果.
 * 
 * @author shiliyan
 *
 */
public class PaymentResult {

    private static final String NO_DATA = "NO_DATA";
    // SUCCESS FAILED ING
    private String status = NO_DATA;
    // 0,00,000,-1...
    private String respCode = NO_DATA;
    private String orderStatus = NO_DATA;

    private String payType = NO_DATA;

    private String lidm = NO_DATA;
    private String authAmt = NO_DATA;

    private String errCode = NO_DATA;

    private String xid = NO_DATA;

    private String authCode = NO_DATA;

    private Object result = NO_DATA;
    
    private String expiry;

    // 末四碼卡號。
    private String last4digitPAN;
    
    
    private PaymentOrder order;
    

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        if (payType == null) {
            return;
        }
        this.payType = payType;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        if (xid == null) {
            return;
        }
        this.xid = xid;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        if (authCode == null) {
            return;
        }
        this.authCode = authCode;
    }

    public String getLidm() {
        return lidm;
    }

    public void setLidm(String lidm) {
        if (lidm == null) {
            return;
        }
        this.lidm = lidm;
    }

    public String getAuthAmt() {
        return authAmt;
    }

    public void setAuthAmt(String authAmt) {
        if (authAmt == null) {
            return;
        }
        this.authAmt = authAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null) {
            return;
        }
        this.status = status;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        if (orderStatus == null) {
            return;
        }
        this.orderStatus = orderStatus;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        if (result == null) {
            return;
        }
        this.result = result;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        if (respCode == null) {
            return;
        }
        this.respCode = respCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        if (errCode == null) {
            return;
        }
        this.errCode = errCode;
    }

    public PaymentOrder getOrder() {
        return order;
    }

    public void setOrder(PaymentOrder order) {
        this.order = order;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getLast4digitPAN() {
        return last4digitPAN;
    }

    public void setLast4digitPAN(String last4digitPAN) {
        this.last4digitPAN = last4digitPAN;
    }

}
