package com.lkkhpg.dsis.integration.payment.dto;

import java.math.BigDecimal;

/**
 * Created by hand on 2017/11/28.
 */
public class TianFuPaymentQueryResult {
    private String service;//接口名称  "service": "pay_service",
    private String service_version;//接口版本  "service_version": "1.1",
    private String input_charset;//
    private String sign; //签名 "sign": "3aa86341420152988a60fe521d50702f",
    private String sign_type; //签名方式  "sign_type": "MD5",
    private String returnSerNo;
    private  String retcode;
    private  String retmsg;
    private  String trade_mode;
    private  String trade_type;
    private  int trade_state;
    private int refund_state;
    private  int clear_state;
    private String bank_transno;
    private String out_trade_no ;
    private String out_refund_no;
    private String transaction_id;
    private String  time_end;
    private String attach;
    private BigDecimal discount;
    private BigDecimal product_fee;
    private BigDecimal subPlatCharge;
    private BigDecimal platCharge;
    private BigDecimal transport_fee;
    private boolean is_split;
    private BigDecimal total_fee;
    private int fee_type;

    public String getBank_transno() {
        return bank_transno;
    }

    public void setBank_transno(String bank_transno) {
        this.bank_transno = bank_transno;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
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

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getProduct_fee() {
        return product_fee;
    }

    public void setProduct_fee(BigDecimal product_fee) {
        this.product_fee = product_fee;
    }

    public BigDecimal getSubPlatCharge() {
        return subPlatCharge;
    }

    public void setSubPlatCharge(BigDecimal subPlatCharge) {
        this.subPlatCharge = subPlatCharge;
    }

    public BigDecimal getPlatCharge() {
        return platCharge;
    }

    public void setPlatCharge(BigDecimal platCharge) {
        this.platCharge = platCharge;
    }

    public BigDecimal getTransport_fee() {
        return transport_fee;
    }

    public void setTransport_fee(BigDecimal transport_fee) {
        this.transport_fee = transport_fee;
    }

    public boolean isIs_split() {
        return is_split;
    }

    public void setIs_split(boolean is_split) {
        this.is_split = is_split;
    }

    public BigDecimal getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(BigDecimal total_fee) {
        this.total_fee = total_fee;
    }

    public int getFee_type() {
        return fee_type;
    }

    public void setFee_type(int fee_type) {
        this.fee_type = fee_type;
    }

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

    public String getReturnSerNo() {
        return returnSerNo;
    }

    public void setReturnSerNo(String returnSerNo) {
        this.returnSerNo = returnSerNo;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public String getTrade_mode() {
        return trade_mode;
    }

    public void setTrade_mode(String trade_mode) {
        this.trade_mode = trade_mode;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public int getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(int trade_state) {
        this.trade_state = trade_state;
    }

    public int getRefund_state() {
        return refund_state;
    }

    public void setRefund_state(int refund_state) {
        this.refund_state = refund_state;
    }

    public int getClear_state() {
        return clear_state;
    }

    public void setClear_state(int clear_state) {
        this.clear_state = clear_state;
    }
}
