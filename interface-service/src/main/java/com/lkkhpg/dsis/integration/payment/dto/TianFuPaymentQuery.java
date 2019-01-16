package com.lkkhpg.dsis.integration.payment.dto;

/**
 * Created by hand on 2017/11/28.
 */
public class TianFuPaymentQuery {
    private String service;//接口名称  "service": "pay_service",
    private String service_version;//接口版本  "service_version": "1.1",
    private String input_charset;//
    private String sign; //签名 "sign": "3aa86341420152988a60fe521d50702f",
    private String sign_type; //签名方式  "sign_type": "MD5",
    private String  partner;
    private String transaction_id;
    private String subpartner;
    private String out_trade_no;//商户订单号 "out_trade_no": "22170303000007",

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getService_version() {
        return service_version;
    }

    public void setService_version(String service_version) {
        this.service_version = service_version;
    }

    public String getInput_charset() {
        return input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getSubpartner() {
        return subpartner;
    }

    public void setSubpartner(String subpartner) {
        this.subpartner = subpartner;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
