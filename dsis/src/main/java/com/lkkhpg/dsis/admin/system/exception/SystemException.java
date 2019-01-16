/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 系统统一异常.
 * 
 * @author chenjingxiong
 */
public class SystemException extends BaseException {

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.system";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public SystemException(String descriptionKey, Object... parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }

}
