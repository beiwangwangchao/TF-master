/*
 *
 */

package com.lkkhpg.dsis.integration.dapp.service.impl;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * PROVIDED BY OMK.
 *
 * @author shengyang.zhou@hand-china.com
 */
public class TOTPUtil {

    private static final String TOTP_ALGORITHM = "HmacSHA1";

    private static final int TIME_GAP = 900;

    public static String generateTOTP(String secret, int size, long timeIndex) {
        String totp = "";
        if (size > 0) {
            byte[] secretByte = secret.getBytes();
            try {
                StringBuilder rawString = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    rawString.append("0");
                }
                NumberFormat nf = new DecimalFormat(rawString.toString());
                totp = nf.format(getCode(secretByte, timeIndex, size));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return totp;
    }

    private static long getCode(byte[] secret, long timeIndex, int size)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signKey = new SecretKeySpec(secret, TOTP_ALGORITHM);
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(timeIndex);
        byte[] timeBytes = buffer.array();

        Mac mac = Mac.getInstance(TOTP_ALGORITHM);
        mac.init(signKey);
        byte[] hash = mac.doFinal(timeBytes);
        int offset = hash[19] & 0xf;
        long truncatedHash = hash[offset] & 0x7f;
        for (int i = 1; i < 4; i++) {
            truncatedHash <<= 8;
            truncatedHash |= hash[offset + i] & 0xff;
        }

        int keyModulus = (int) Math.pow(10, size);

        return (truncatedHash %= keyModulus);
    }

    public static String getSysToken(String appKey, String appSecret, long ts) throws Exception {
        // String systoken = Base64.encodeBase64String(DigestUtils.sha256(appKey
        // + "." + totp(ts) + "./callbacks"));
        String totp = generateTOTP(appSecret, 8, ts / (2 * TIME_GAP));
        totp = Base64.encodeBase64String(totp.getBytes());
        String rawData = appKey + "." + totp + "./callbacks";

        String systoken = generateHash(appSecret, rawData, "HmacSHA256");
        return systoken;
    }

    private static String generateHash(String secret, String rawValue, String shaType) throws Exception {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes("UTF-8"), shaType);
            Mac mac = Mac.getInstance(shaType);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(rawValue.getBytes("UTF-8"));

            byte[] hexArray = { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
                    (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
                    (byte) 'f' };

            byte[] hexChars = new byte[rawHmac.length * 2];
            for (int j = 0; j < rawHmac.length; j++) {
                int v = rawHmac[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }

            return new String(hexChars);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
