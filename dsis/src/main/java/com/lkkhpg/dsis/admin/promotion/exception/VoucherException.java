/*
 *
 */
package com.lkkhpg.dsis.admin.promotion.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 优惠券统一异常.
 * 
 * @author chenjingxiong
 */
public class VoucherException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.dpm";

    /**
     * 优惠券编码必须唯一.
     */
    public static final String MSG_ERROR_PDM_VOUCHER_CODE_UNIQUE = "msg.error.promotion.voucher_code_unique";
    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public VoucherException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }
}
