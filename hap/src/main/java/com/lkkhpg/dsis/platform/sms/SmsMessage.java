/*
 *
 */
package com.lkkhpg.dsis.platform.sms;


/**
 * sms消息结构体
 * 不要随意删除,不仅仅是pojo
 * 
 * @author Clerifen Li
 */
public class SmsMessage {

    private String title;
    
    private String content;
    
    private String phone;

    /**
     * 增加phone
     * @param phone
     */
    public void addPhone(String phone) {
        if(this.phone == null || this.phone.length() == 0){
            this.phone = phone;
        }else{
            this.phone += SmsSender.getMultipleSendDelimiter() + phone;
        }
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
