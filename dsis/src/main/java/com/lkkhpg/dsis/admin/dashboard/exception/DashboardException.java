/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.dashboard.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * Dashborad统一异常.
 * 
 * @author chenjingxiong
 */
public class DashboardException extends BaseException {

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.dashboard";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public DashboardException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }
}
