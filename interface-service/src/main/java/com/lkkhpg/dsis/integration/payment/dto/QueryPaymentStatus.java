package com.lkkhpg.dsis.integration.payment.dto;

import com.google.gson.Gson;
import com.lkkhpg.dsis.integration.payment.controllers.MD5Sign;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by hand on 2018/1/4.
 */
public class QueryPaymentStatus {

    private String out_trade_no;
    private String partner;
    private String subpartner;
    private String service;
    private String service_version;
    private String sign;
    private String sign_type;
    private String input_charset;

    private HashMap<String, String> params = new HashMap<String, String>();

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

    public String getSubpartner() {
        return subpartner;
    }

    public void setSubpartner(String subpartner) {
        this.subpartner = subpartner;
        params.put("subpartner",subpartner);
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

    public String getInput_charset() {
        return input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
        params.put("input_charset",input_charset);
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
