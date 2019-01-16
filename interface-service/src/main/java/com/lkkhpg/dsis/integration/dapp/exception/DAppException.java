/*
 *
 */

package com.lkkhpg.dsis.integration.dapp.exception;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class DAppException extends Exception {
    private int code;
    private String value;

    public DAppException(String message, int code, String value) {
        super(message);
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
