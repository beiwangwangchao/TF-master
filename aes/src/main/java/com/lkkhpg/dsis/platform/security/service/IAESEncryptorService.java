/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.security.service;

/**
 * @author shiliyan
 *
 */
public interface IAESEncryptorService {

    String encrypt(String text) throws Exception;

    String decrypt(String encryptedText) throws Exception;

}
