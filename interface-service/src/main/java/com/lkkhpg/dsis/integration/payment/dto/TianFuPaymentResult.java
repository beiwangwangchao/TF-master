package com.lkkhpg.dsis.integration.payment.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hand on 2017/11/28.
 */
public class TianFuPaymentResult {

    private String service;
    private String sign;
    private String returnSerNo;
    private int fee_type;
    private String out_trade_no;
    private String transaction_id;
    private String time_end;
    private String total_fee;
    private String partner;
    private String trade_state;//接受到的数据 0 1 3 TRADE_SUCEESS
    private int trade_status;//0 1 3
    private String retcode;
    private String service_version;
    private String input_charset;
    private String sign_type;
    private String trans_channel;
    private String notify_id;
    private String product_fee;
    private Date payDate;
    private BigDecimal totalSum;

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
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

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getTrans_channel() {
        return trans_channel;
    }

    public void setTrans_channel(String trans_channel) {
        this.trans_channel = trans_channel;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public String getProduct_fee() {
        return product_fee;
    }

    public void setProduct_fee(String product_fee) {
        this.product_fee = product_fee;
    }

    public int getFee_type() {
        return fee_type;
    }

    public void setFee_type(int fee_type) {
        this.fee_type = fee_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public int getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(int trade_status) {
        this.trade_status = trade_status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getReturnSerNo() {
        return returnSerNo;
    }

    public void setReturnSerNo(String returnSerNo) {
        this.returnSerNo = returnSerNo;
    }


    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }



    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }
}
