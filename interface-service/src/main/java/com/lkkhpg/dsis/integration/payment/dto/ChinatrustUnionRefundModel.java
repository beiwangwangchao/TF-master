/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.payment.dto;

/**
 * 中国信托退款.
 * @author shiliyan
 *
 */

public class ChinatrustUnionRefundModel extends PaymentTransactionModel{
    private String rtnCode;
    private String respCode;
    private String respMsg;

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

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

}
