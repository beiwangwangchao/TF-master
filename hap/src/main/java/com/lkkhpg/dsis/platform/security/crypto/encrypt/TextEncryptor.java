/*
 *
 */
package com.lkkhpg.dsis.platform.security.crypto.encrypt;

/**
 * @author shiliyan
 *
 */
public interface TextEncryptor {
    String encrypt(String paramString);

    String decrypt(String paramString);
}
