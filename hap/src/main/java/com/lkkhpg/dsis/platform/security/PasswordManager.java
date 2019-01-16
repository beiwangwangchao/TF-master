/*
 *
 */
package com.lkkhpg.dsis.platform.security;

import org.springframework.util.DigestUtils;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年1月31日
 */
public class PasswordManager {

    private String salt;
    private String defaultPassword = "111111";

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * MD5处理.
     * <p>
     * 加盐,变大写,MD5,大写
     * 
     * @param password
     *            原始明文密码
     * @return 大写的 MD5
     */
    public String encode(String password) {
        return DigestUtils.md5DigestAsHex(mergePasswordAndSalt(password).toUpperCase().getBytes()).toUpperCase();
    }

    /**
     * 加盐处理.
     * 
     * @param password
     *            原始铭文密码
     * @return 加盐的密码
     */
    private String mergePasswordAndSalt(String password) {
        if (password == null) {
            password = "";
        }
        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "." + salt;
        }
    }

    public static void main(String[] args) {
        PasswordManager pm = new PasswordManager();
        pm.setSalt("infinitus");
    }
}
