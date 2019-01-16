package com.lkkhpg.dsis.platform.utils;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;


public class SmsSend {
    public Boolean anOtherWaySendSms(String phoneNum, String code, String sms_interface_address,
                                     String sms_userid, String sms_username, String sms_password) {
        Boolean flag = false;
        try {
            System.out.println("''''''''''''''''''''''''''''''''");
            System.out.println(sms_interface_address);
            System.out.println(sms_userid);
            System.out.println(sms_username);
            System.out.println(sms_password);
            System.out.println(phoneNum);
            System.out.println(code);

            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod(sms_interface_address); //"http://www.lcqxt.com/sms.aspx"
            //PostMethod post = new PostMethod("http://www.lcqxt.com/sms.aspx"); //"http://www.lcqxt.com/sms.aspx"

            post.addRequestHeader("Content-Type",
                    "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
            phoneNum = phoneNum.substring(phoneNum.length()-11,phoneNum.length());
            NameValuePair[] data = {
                    new NameValuePair("userid", sms_userid), // 企业id "29431"
                    new NameValuePair("account", sms_username), // 账号
                    new NameValuePair("password", sms_password), //账号密码
                    new NameValuePair("action", "send"),//行为
                    new NameValuePair("content", code), //内容
                    new NameValuePair("mobile", phoneNum), // 手机号码
                    new NameValuePair("sendTime", ""), // 发送时间，为空则表示立即发出
                    new NameValuePair("checkcontent", "1")
            };// 是否检查短信内容，1为检查
            post.setRequestBody(data);

            System.out.println("---------------------test 2018/01/30-----------------------");
            System.out.println(data);

            client.executeMethod(post);
//            String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
           /* String result = "aaaa";
            System.out.println(result);
            XMLTransfer xmlTransfer = new XMLTransfer();
            flag = xmlTransfer.parseXML(result, "Success"); //校验短信发送后的结果
            System.out.println("flag: " + flag);*/
            post.releaseConnection();
           // client.executeMethod(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;


    }
}
