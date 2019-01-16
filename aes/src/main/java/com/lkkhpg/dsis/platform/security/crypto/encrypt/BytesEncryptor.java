/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.security.crypto.encrypt;

/**
 * @author shiliyan
 *
 */
public interface BytesEncryptor {
    byte[] encrypt(byte[] paramArrayOfByte);

    byte[] decrypt(byte[] paramArrayOfByte);
}
