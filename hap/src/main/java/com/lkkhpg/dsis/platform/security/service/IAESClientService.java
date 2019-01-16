/*
 *
 */
package com.lkkhpg.dsis.platform.security.service;

/**
 * @author shiliyan
 *
 */
public interface IAESClientService {
    String encrypt(String text);

    String decrypt(String encryptedText);
}
