/*
 *
 */
package com.lkkhpg.dsis.platform.sms;

/**
 * sms状态枚举
 * @author Clerifen Li
 */
public enum SmsStatusEnum {
    
    SUCCESS("SUCCESS"),
    ERROR("ERROR");
    
    private String code;
    
    private SmsStatusEnum(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }
}