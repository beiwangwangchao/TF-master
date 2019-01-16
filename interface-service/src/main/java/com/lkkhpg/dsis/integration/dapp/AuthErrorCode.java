/*
 *
 */

package com.lkkhpg.dsis.integration.dapp;

import org.springframework.http.HttpStatus;

/**
 * Error Code about security.
 * 
 * @author shengyang.zhou@hand-china.com
 */
public enum AuthErrorCode {

    /**
     * Required Header is Missing.
     */
    REQUIRED_HEAD_MISSING(10001, IDAConstants.MSG_10001, HttpStatus.BAD_REQUEST),
    // /**
    // * Required Field is Missing.
    // */
    // REQUIRED_FIELD_MISSING(10002, IDAConstants.MSG_10002, HttpStatus.OK),
    /**
     * App Token is Invalid or Expired .
     */
    APP_TOKEN_INVALID(10003, IDAConstants.MSG_10003, HttpStatus.UNAUTHORIZED),
    /**
     * Ip is blocked.
     */
    IP_BLOCKED(10004, IDAConstants.MSG_10004, HttpStatus.FORBIDDEN),
    /**
     * Sign is Incorrect.
     */
    SIGN_INCORRECT(10005, IDAConstants.MSG_10005, HttpStatus.BAD_REQUEST),

    /**
     * AppCode is Invalid.
     */
    APP_CODE_INVALID(10006, IDAConstants.MSG_10006, HttpStatus.FORBIDDEN),
    /**
     * AppCode is Expired.
     */
    APP_CODE_EXPIRED(10007, IDAConstants.MSG_10007, HttpStatus.FORBIDDEN),
    /**
     * AppSecret is not correct.
     */
    APP_SECRET_INCORRECT(10008, IDAConstants.MSG_10008, HttpStatus.FORBIDDEN);

    private int code;
    private String message;
    private HttpStatus status;

    AuthErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public HttpStatus status() {
        return status;
    }

    public static AuthErrorCode fromCode(int code) {
        for (AuthErrorCode c : values()) {
            if (c.code() == code) {
                return c;
            }
        }
        return null;
    }
}
