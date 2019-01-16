package com.lkkhpg.dsis.integration.payment.dto;

import ch.qos.logback.access.pattern.EnsureLineSeparation;
import com.google.gson.Gson;
import com.lkkhpg.dsis.integration.payment.controllers.MD5Sign;
import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import org.springframework.stereotype.Component;
import sun.security.krb5.internal.PAData;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 天府银行银联支付实体类
 * Created by hand on 2017/11/13.
 * updated by li.liu06@hand-chian.com 2018/02/02.
 * 增加注解使能够获取相关的信息与值
 */
@Component
public class TianFuUnion  {
    private String bankcode;// 银行编码  "bankcode": "700"
    private String biz_type;// 业务类型 "biz_type": "0001"
    private String body;   //商品描述 "body": "51配件供应商订单"
    private String fee_type; //币种  "fee_type": "1"
    private String input_charset;//  字符集  "input_charset": "UTF-8",
    private String limit_pay; // 指定支付方式  "limit_pay": "2",
    private String mobile; //手机号 "mobile": "18702809023",
    private String notify_url;//异步通知路径  "notify_url": "http://192.168.100.95:7002/PayInterface/TF_ExPayNotify",
    private String out_trade_no;//商户订单号 "out_trade_no": "22170303000007",
    private String partner;//商户号 "partner": "12038387",
    private String product_fee;//商品费用 "product_fee": "12.00",
    private String return_url;// 同步通知路径 "return_url": "http://localhost:7002/PayInterface/TF_ExPayReturn",
    private String service;//接口名称  "service": "pay_service",
    private String service_version;//接口版本  "service_version": "1.1",
    private String show_url;//商品展示网址  "show_url": "http://gys.51peijian.cn",
    private String sign; //签名 "sign": "3aa86341420152988a60fe521d50702f",
    private String sign_type; //签名方式  "sign_type": "MD5",
    private String spbill_create_ip;//用户IP  "spbill_create_ip": "::1",
    private String subject;//商品名称  "subject": "51配件供应商订单",
    private String total_fee;//总金额  "total_fee": "12.00",
    private String trade_mode; //交易类型   "trade_mode": "0002",
    private String trans_channel; //支付渠道  "trans_channel": "b2c",
    private String trans_pattern;//交易模式 "trans_pattern": "0",
    private String transport_fee;//物流费用  "transport_fee": "0.00"
    private String subpartner;//二级商户号
    private String trade_details;//交易详细
    private String  qr_return_type;//二维码返回的方式
    private String  store_id;  //门店id
    private String  time_start;
    private String  time_expire;

    private HashMap<String, String> params = new HashMap<String, String>();;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
        params.put("store_id",store_id);
    }

    public String getQr_return_type() {
        return qr_return_type;
    }

    public void setQr_return_type(String qr_return_type) {
        this.qr_return_type = qr_return_type;
        params.put("qr_return_type",qr_return_type);
    }

    public String getProduct_fee() {
        return product_fee;
    }

    public void setProduct_fee(String product_fee) {
        this.product_fee = product_fee;
        params.put("product_fee",product_fee);
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
        params.put("total_fee",total_fee);
    }

    public String getTransport_fee() {
        return transport_fee;
    }

    public void setTransport_fee(String transport_fee) {
        this.transport_fee = transport_fee;
        params.put("transport_fee",transport_fee);
    }

    public String getSubpartner() {
        return subpartner;
    }

    public void setSubpartner(String subpartner) {
        this.subpartner = subpartner;
        params.put("subpartner",subpartner);
    }

    public String getTrade_details() {
        return trade_details;
    }

    public void setTrade_details(String trade_details) {
        this.trade_details = trade_details;
        params.put("trade_details",trade_details);
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
        params.put("bankcode",bankcode);
    }

    public String getBiz_type() {
        return biz_type;
    }

    public void setBiz_type(String biz_type) {
        this.biz_type = biz_type;
        params.put("biz_type",biz_type);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
        params.put("body",body);
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
        params.put("fee_type",fee_type);
    }

    public String getInput_charset() {
        return input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
        params.put("input_charset",input_charset);
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
        params.put("limit_pay",limit_pay);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        params.put("mobile",mobile);
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
        params.put("notify_url",notify_url);
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
        params.put("out_trade_no",out_trade_no);
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
        params.put("partner",partner);
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
        params.put("time_start",time_start);
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
        params.put("time_expire",time_expire);
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
        params.put("return_url",return_url);
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
        params.put("service",service);
    }

    public String getService_version() {
        return service_version;
    }

    public void setService_version(String service_version) {
        this.service_version = service_version;
        params.put("service_version",service_version);
    }

    public String getShow_url() {
        return show_url;
    }

    public void setShow_url(String show_url) {
        this.show_url = show_url;
        params.put("show_url",show_url);
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

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
        params.put("spbill_create_ip",spbill_create_ip);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
        params.put("subject",subject);
    }

    public String getTrade_mode() {
        return trade_mode;
    }

    public void setTrade_mode(String trade_mode) {
        this.trade_mode = trade_mode;
        params.put("trade_mode",trade_mode);
    }

    public String getTrans_channel() {
        return trans_channel;
    }

    public void setTrans_channel(String trans_channel) {
        this.trans_channel = trans_channel;
        params.put("trans_channel",trans_channel);
    }

    public String getTrans_pattern() {
        return trans_pattern;
    }

    public void setTrans_pattern(String trans_pattern) {
        this.trans_pattern = trans_pattern;
        params.put("trans_pattern",trans_pattern);
    }

    public String toSign(String app_key,String signType){

        Map<String, String> sortParams = new TreeMap<String, String>(params);
        Set<Map.Entry<String, String>> entrys = sortParams.entrySet();

        int count=0;
        StringBuilder valueSb = new StringBuilder();
        for (Map.Entry<String, String> entry : entrys) {
            valueSb.append(entry.getKey()+"="+entry.getValue());
            count++;
            if(count!=entrys.size())valueSb.append("&");
        }

        valueSb.append(app_key);
        String mySign = MD5Sign.md5(valueSb.toString());
        params.put("sign",mySign);
        params.put("sign_type",signType);
        Gson gson=new Gson();
        return  gson.toJson(params);
    }

}
