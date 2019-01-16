/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.security.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.platform.security.service.IAESEncryptorService;

/**
 * @author shiliyan
 *
 */
@Controller
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

    @ModelAttribute
    public Object checkAccess(HttpServletRequest request) {
        boolean f = false;
        String from = request.getHeader("from");
        String magicCode = request.getHeader("magicCode");
        if ("db".equalsIgnoreCase(from)) {
            f = "FRxOdNYZbAojcohrvWMH9Sxy14P6pMBuiFp".equals(magicCode);
        }
        if ("dsis".equalsIgnoreCase(from)) {
            f = "FRxOdNYZbAojcohrvWMH9Sxy14P6pMBuiUK".equals(magicCode);
        }
        if (f == false) {
            throw new RuntimeException("Access Denied.");
        }
        return new Object();
    }

    @ExceptionHandler(value = Exception.class)
    public Object handleException(Throwable throwable) {
        return new ResponseEntity<>(throwable.getMessage(), null, HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/security/aes/encrypt", method = RequestMethod.POST)
    @ResponseBody
    public String encrypt(String text, HttpServletRequest request) {
        try {
            return aesEncryptorService.encrypt(text);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        }
        return text;
    }

    @RequestMapping(value = "/security/aes/decrypt", method = RequestMethod.POST)
    @ResponseBody
    public String decrypt(String encryptedText, HttpServletRequest request) {
        try {
            return aesEncryptorService.decrypt(encryptedText);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        }
        return encryptedText;
    }

    @RequestMapping(value = "/security/aes/encrypt", method = RequestMethod.GET)
    @ResponseBody
    public String encryptGet(String text, HttpServletRequest request) {
        return text;
    }

    @RequestMapping(value = "/security/aes/decrypt", method = RequestMethod.GET)
    @ResponseBody
    public String decryptGet(String encryptedText, HttpServletRequest request) {
        return encryptedText;
    }
}
