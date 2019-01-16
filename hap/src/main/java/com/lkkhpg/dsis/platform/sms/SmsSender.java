/*
 *
 */
package com.lkkhpg.dsis.platform.sms;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.api.response.message.MessageResponse;
import com.plivo.helper.exception.PlivoException;



/**
 * <dependency> <groupId>com.plivo</groupId> <artifactId>plivo-java</artifactId>
 * <version>3.0.8</version> </dependency> plivo sms
 * <p>
 * api: https://www.plivo.com/docs/getting-started/send-a-single-sms/
 *
 * @author Clerifen Li
 */
public class SmsSender {

    private static final String API_VERSION = "v1";
    private static final String FROM = "src";
    private static final String TO = "dst";
    private static final String CONTENT = "text";
    private static final String METHOD = "method";
    private static final String POST = "POST";
    private static final String TYPE = "type";
    private static final String SMS = "sms";
    private static final String MULTIPLE_SEND_DELIMITER = "<";
    private static final int SUCCESS = 202;

    private String authId;

    private String authToken;

    private String organization;

    private Integer tryTimes = 3;

    private List<String> whiteList;

    private String environment;

    /**
     * 生成一个sms数据结构体.
     *
     * @return
     */
    public SmsMessage createSmsMessage() {
        return new SmsMessage();
    }

    public SmsSender() {

    }

    /**
     * 类似用户名密码.
     *
     * @param authId       "MAYZK2MDK5MWY3NWQ5M2"
     * @param authToken    "ZjkxZGY0ZmU4MmEzNDI3MmY3NjYwNDlkZTNhNWFk"
     * @param organization "Infinitus"
     */
    public SmsSender(String authId, String authToken, String organization) {
        this.authId = authId;
        this.authToken = authToken;
        this.organization = organization;
    }

    /**
     * @return
     * @throws Exception
     */
    public String send(SmsMessage msg) throws Exception {
        RestAPI api = new RestAPI(this.authId, this.authToken, API_VERSION);
        LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();

        parameters.put(FROM, this.organization); // Sender's identification
        parameters.put(TO, getPhoneNO(msg)); // Receiver's phone number with
        // country code
        parameters.put(CONTENT, msg.getContent()); // Your SMS text message
        parameters.put(TYPE, SMS); // Message Type is SMS
        parameters.put(METHOD, POST); // The method used to call the url
        try {
            // Send the message
            MessageResponse msgResponse = api.sendMessage(parameters);
            if (msgResponse.serverCode == SUCCESS) {
                StringBuilder sb = new StringBuilder();
                int count = msgResponse.messageUuids.size();
                for (int i = 0; i < count; i++) {
                    sb.append("Message UUID : ").append(msgResponse.messageUuids.get(i).toString()).append("\n");
                }
                return sb.toString();
            } else {
                throw new Exception(msgResponse.error);
            }
        } catch (PlivoException e) {
            throw e;
        }
    }



    public String getPhoneNO(SmsMessage msg) {
        String phone = msg.getPhone();
        // fix tw 8860--886
        if (phone != null && phone.startsWith("8860")) {
            phone = phone.replaceFirst("8860", "886");
        }
        return phone;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(Integer tryTimes) {
        this.tryTimes = tryTimes;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public static String getMultipleSendDelimiter() {
        return MULTIPLE_SEND_DELIMITER;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }

}
