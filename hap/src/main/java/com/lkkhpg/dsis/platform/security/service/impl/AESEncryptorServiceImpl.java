/*
 *
 */
package com.lkkhpg.dsis.platform.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.platform.security.crypto.encrypt.AESEncryptors;
import com.lkkhpg.dsis.platform.security.service.IAESEncryptorService;

/**
 * @author shiliyan
 *
 */
public class AESEncryptorServiceImpl implements IAESEncryptorService {

    @Autowired
    private AESEncryptors aesEncryptors;

    @Override
    public String encrypt(String text) throws Exception {
        if (text == null) {
            return null;
        }
        return aesEncryptors.encrypt(text);
    }

    @Override
    public String decrypt(String encryptedText) throws Exception {
        if (encryptedText == null) {
            return null;
        }
        String decrypt = aesEncryptors.decrypt(encryptedText);
        return decrypt;
    }

    public AESEncryptors getAesEncryptors() {
        return aesEncryptors;
    }

    public void setAesEncryptors(AESEncryptors aesEncryptors) {
        this.aesEncryptors = aesEncryptors;
    }

}
