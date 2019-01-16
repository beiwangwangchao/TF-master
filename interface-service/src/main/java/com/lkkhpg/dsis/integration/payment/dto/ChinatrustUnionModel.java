/*
 *
 */
package com.lkkhpg.dsis.integration.payment.dto;

/**
 * 中国信托银联模型.
 * 
 * @author shiliyan
 *
 */
public class ChinatrustUnionModel extends PaymentTransactionModel {

    private int merId;
    // private int merId = ChinaTrustUnionConfigration.MERID;
    private String lidm;
    private int purchAmt;
    private String inMac;
    private String action;
    // public void setMerId(int merId) {
    // this.merId = merId;
    // }

    public void setLidm(String lidm) {
        this.lidm = lidm;
    }

    public void setPurchAmt(int purchAmt) {
        this.purchAmt = purchAmt;
    }

    public void setInMac(String inMac) {
        this.inMac = inMac;
    }

    // public int getMerId() {
    // return merId;
    // }

    public String getLidm() {
        return lidm;
    }

    public int getPurchAmt() {
        return purchAmt;
    }

    public String getInMac() {
        return inMac;
    }

    public int getMerId() {
        return merId;
    }

    public void setMerId(int merId) {
        this.merId = merId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
