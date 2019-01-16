/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.integration.dapp;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class Token {

    private String appCode;
    private String appSecret;

    private String token;

    private long expireAt;
    private int refreshCount;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(long expireAt) {
        this.expireAt = expireAt;
    }

    public int getRefreshCount() {
        return refreshCount;
    }

    public void setRefreshCount(int refreshCount) {
        this.refreshCount = refreshCount;
    }
}
