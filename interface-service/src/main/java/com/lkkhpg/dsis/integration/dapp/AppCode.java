/*
 *
 */

package com.lkkhpg.dsis.integration.dapp;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class AppCode {
    private String code;
    private String secret;

    private List<String> whiteIps = Collections.emptyList();

    private boolean whiteIpListEnabled = false;

    // 2099-12-31
    private Date expire = new Date(4102358400000L);

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public List<String> getWhiteIps() {
        return whiteIps;
    }

    /**
     * localhost,127.0.0.1 is always allowed.
     * <p>
     * you can add some ip like:
     * <ul>
     * <li>219.137.178.248</li>
     * <li>219.137.178.*</li>
     * <li>219.137.11-123.*</li>
     * <li>219.137.178.10-222</li>
     * </ul>
     * 
     * @param whiteIps
     *            white ip List
     */
    public void setWhiteIps(List<String> whiteIps) {
        this.whiteIps = whiteIps;
    }

    public boolean isWhiteIpListEnabled() {
        return whiteIpListEnabled;
    }

    public void setWhiteIpListEnabled(boolean whiteIpListEnabled) {
        this.whiteIpListEnabled = whiteIpListEnabled;
    }

    /**
     * @return 是否过期
     */
    public boolean expired() {
        return expire != null && expire.getTime() < System.currentTimeMillis();
    }
}
