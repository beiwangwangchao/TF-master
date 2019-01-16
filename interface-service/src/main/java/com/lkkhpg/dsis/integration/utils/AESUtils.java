/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.integration.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * for interface use only.
 * 
 * @author shengyang.zhou@hand-china.com
 */
public class AESUtils {

    public static final String AES = "AES";

    public static final String ALG_SHA1PRNG = "SHA1PRNG";

    public static byte[] encrypt(byte[] key, byte[] data, int keySize, String alg) throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance(AES);
        SecureRandom seed = SecureRandom.getInstance(alg);
        seed.setSeed(key);
        generator.init(keySize, seed);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(generator.generateKey().getEncoded(), AES));
        return cipher.doFinal(data);
    }

    public static byte[] encrypt(byte[] key, byte[] data, int keySize) throws Exception {
        return encrypt(key, data, keySize, ALG_SHA1PRNG);
    }

    public static byte[] encrypt(String encrypt, byte[] data) throws Exception {
        return encrypt(encrypt.getBytes(), data, 256);
    }

    public static byte[] decrypt(byte[] key, byte[] data, int keySize, String alg) throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance(AES);
        SecureRandom seed = SecureRandom.getInstance(alg);
        seed.setSeed(key);
        generator.init(keySize, seed);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(generator.generateKey().getEncoded(), AES));
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] key, byte[] data, int keySize) throws Exception {
        return decrypt(key, data, keySize, ALG_SHA1PRNG);
    }

    public static byte[] decrypt(String key, byte[] data) throws Exception {
        return decrypt(key.getBytes(), data, 256);
    }

}
