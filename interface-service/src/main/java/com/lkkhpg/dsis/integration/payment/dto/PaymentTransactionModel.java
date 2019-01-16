/*
 *
 */
package com.lkkhpg.dsis.integration.payment.dto;

import java.util.Date;

/**
 * @author shiliyan
 *
 */
public class PaymentTransactionModel {

    public static final String FAILED = "F";

    public static final String SUCCESS = "S";
    private Long transactionId;
    private String sourceKey1;
    private String sourceKey2;
    private String sourceKey3;
    private String sourceKey4;
    private String sourceKey5;

    private String sourceType = "ORDER";

    private String tRequestUrl;

    private Object tMessage;

    private String transactionType;

    private String tPhase;

    private String tRequestDate;

    private String tStatus = SUCCESS;

    private String urlResEnc;
    private String merID;
    private String sign;
    private String out_trade_no;
    private String partner;




    public String getUrlResEnc() {
        return urlResEnc;
    }

    public void setUrlResEnc(String urlResEnc) {
        this.urlResEnc = urlResEnc;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public PaymentTransactionModel() {
        tRequestDate = String.valueOf(new Date().getTime());
    }

    public String getSourceKey1() {
        return sourceKey1;
    }

    public void setSourceKey1(String sourceKey1) {
        this.sourceKey1 = sourceKey1;
    }

    public String getSourceKey2() {
        return sourceKey2;
    }

    public void setSourceKey2(String sourceKey2) {
        this.sourceKey2 = sourceKey2;
    }

    public String getSourceKey3() {
        return sourceKey3;
    }

    public void setSourceKey3(String sourceKey3) {
        this.sourceKey3 = sourceKey3;
    }

    public String getSourceKey4() {
        return sourceKey4;
    }

    public void setSourceKey4(String sourceKey4) {
        this.sourceKey4 = sourceKey4;
    }

    public String getSourceKey5() {
        return sourceKey5;
    }

    public void setSourceKey5(String sourceKey5) {
        this.sourceKey5 = sourceKey5;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String gettRequestUrl() {
        return tRequestUrl;
    }

    public void settRequestUrl(String tRequestUrl) {
        this.tRequestUrl = tRequestUrl;
    }

    public Object gettMessage() {
        return tMessage;
    }

    public void settMessage(Object tMessage) {
        this.tMessage = tMessage;
    }


    public String gettPhase() {
        return tPhase;
    }

    public void settPhase(String tPhase) {
        this.tPhase = tPhase;
    }

    public String gettRequestDate() {
        return tRequestDate;
    }

    public void settRequestDate(String tRequestDate) {
        this.tRequestDate = tRequestDate;
    }

    public String gettStatus() {
        return tStatus;
    }

    public void settStatus(String tStatus) {
        this.tStatus = tStatus;
    }


    public String getURLResEnc() {
        return urlResEnc;
    }

    public void setURLResEnc(String uRLResEnc) {
        urlResEnc = uRLResEnc;
    }

    public String getMerID() {
        return merID;
    }

    public void setMerID(String merID) {
        this.merID = merID;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

}
