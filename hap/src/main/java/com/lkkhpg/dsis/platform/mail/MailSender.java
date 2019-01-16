/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.mail;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 消息服务,继承了Spring mail sender
 * 
 * @author Clerifen Li
 */
public class MailSender extends JavaMailSenderImpl {

    private Integer tryTimes = 3;


    private String environment;


    private List<String> whiteList;


    private String messageAccount;


    public String getMessageAccount() {
        return messageAccount;
    }

    public void setMessageAccount(String messageAccount) {
        this.messageAccount = messageAccount;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Integer getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(Integer tryTimes) {
        this.tryTimes = tryTimes;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }


    public boolean checkWhiteList(String address) {
        if (this.whiteList != null) {
            for (String string : whiteList) {
                if (string.equalsIgnoreCase(address)) {
                    return true;
                }
            }
        }
        return false;
    }
}
