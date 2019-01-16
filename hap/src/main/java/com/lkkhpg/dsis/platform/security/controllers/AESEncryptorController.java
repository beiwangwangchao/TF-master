/*
 *
 */
package com.lkkhpg.dsis.platform.security.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.platform.security.service.IAESEncryptorService;

/**
 * @author shiliyan
 *
 */
// @Controller
public class AESEncryptorController {

    private Logger logger = LoggerFactory.getLogger(AESEncryptorController.class);
    @Autowired
    private IAESEncryptorService aesEncryptorService;

    public IAESEncryptorService getAesEncryptorService() {
        return aesEncryptorService;
    }

    public void setAesEncryptorService(IAESEncryptorService aesEncryptorService) {
        this.aesEncryptorService = aesEncryptorService;
    }

    @RequestMapping(value = "/security/aes/encrypt")
    @ResponseBody
    public String encrypt(String text, HttpServletRequest request) {
        try {
            return aesEncryptorService.encrypt(text);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return text;
    }

    @RequestMapping(value = "/security/aes/decrypt")
    @ResponseBody
    public String decrypt(String encryptedText, HttpServletRequest request) {
        try {
            return aesEncryptorService.decrypt(encryptedText);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return encryptedText;
    }
}
