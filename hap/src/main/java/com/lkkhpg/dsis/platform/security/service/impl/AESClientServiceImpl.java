/*
 *
 */
package com.lkkhpg.dsis.platform.security.service.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lkkhpg.dsis.platform.security.service.IAESClientService;

/**
 * @author shiliyan
 *
 */
public class AESClientServiceImpl implements IAESClientService {

    private static final String UTF_8 = "UTF-8";

    private static final String HTTPS = "https://";

    private static final String HTTP = "http://";

    public static final String AES_SERVICE_NOT_WORK = "AES_SERVICE_NOT_WORK";

    private Logger logger = LoggerFactory.getLogger(AESClientServiceImpl.class);

    private String encryptUrl;

    private int connectTimeout = HttpUtils.I_5000;

    public String getEncryptUrl() {
        return encryptUrl;
    }

    public void setEncryptUrl(String encryptUrl) {
        this.encryptUrl = encryptUrl;
    }

    public String getDecryptUrl() {
        return decryptUrl;
    }

    public void setDecryptUrl(String decryptUrl) {
        this.decryptUrl = decryptUrl;
    }

    private String decryptUrl;

    @Override
    public String encrypt(String text) {
        if (text == null) {
            return null;
        }
        String result = AES_SERVICE_NOT_WORK;
        try {
            if (StringUtils.startsWithIgnoreCase(encryptUrl, HTTPS)) {
                result = httpsPost(encryptUrl, text);
            }
            if (StringUtils.startsWithIgnoreCase(encryptUrl, HTTP)) {
                result = httpPost(encryptUrl, text);
            }
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        }
        return result;

    }

    private String httpsPost(String url, String text) throws Exception {
        // System.out.println("https is working " + url);
        int indexOf = url.indexOf("?");
        String substring = url.substring(indexOf + 1);
        substring = substring + text;
        url = url.substring(0, indexOf);
        String content = substring;
        return HttpsUtils.doPost(url, content, UTF_8, connectTimeout, connectTimeout);
    }

    public String httpPost(String url, String text) throws IOException {
        // System.out.println("http is working " + url);
        String urlPost;
        urlPost = HttpUtils.urlPost(url, text, connectTimeout);
        return urlPost;
    }

    @Override
    public String decrypt(String encryptedText) {
        //hex check
       // Hex.decode(encryptedText);
        if (encryptedText == null) {
            return null;
        }

        String result = AES_SERVICE_NOT_WORK;
        try {
            if (StringUtils.startsWithIgnoreCase(decryptUrl, HTTPS)) {
                result = httpsPost(decryptUrl, encryptedText);
            }
            if (StringUtils.startsWithIgnoreCase(decryptUrl, HTTP)) {
                result = httpPost(decryptUrl, encryptedText);
            }
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        }
        return result;
    }

    public static void main(String[] args) {
        AESClientServiceImpl service = new AESClientServiceImpl();
        service.setDecryptUrl("https://10.52.10.90:8443/aes/security/aes/decrypt?encryptedText=");
        service.setEncryptUrl("https://10.52.10.90:8443/aes/security/aes/encrypt?text=");

        String encrypt = service.encrypt("23123");
        String decrypt = service.decrypt(encrypt);
//        System.out.println(encrypt);
//        System.out.println(decrypt);
//        System.out.println("+++++++++++++++++++++++++");

        service.setDecryptUrl("https://10.52.10.90:8443/aes/security/aes/decrypt?encryptedText=");
        service.setEncryptUrl("https://10.52.10.90:8443/aes/security/aes/encrypt?text=");

        String encrypt2 = service.encrypt(decrypt);
        String decrypt2 = service.decrypt(encrypt);
//        System.out.println(encrypt2);
//        System.out.println(decrypt2);
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

}
