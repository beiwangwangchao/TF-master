/*
 *
 */
package com.lkkhpg.dsis.platform.report.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 报表异常.
 * @author chenjingxiong
 */
public class ReportException extends BaseException {

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.platform.report";
    
    
    /**
     * 报表 - 生成HTML错误.
     */
    public static final String MSG_ERROR_REPORT_HTML_ERROR = "msg.error.report.html.generate.error";
    
    
    /**
     * 报表 - 不支持导出类型.
     */
    public static final String MSG_ERROR_REPORT_TYPE_NOT_SUPPORT = "msg.error.report.type.not.support";
    
    /**
     * 构造方法.
     *
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public ReportException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }

    /**
     * 构造方法.
     *
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     * @param cause
     *            原因.
     *
     */
    public ReportException(String descriptionKey, Object[] parameters, Throwable cause) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
        this.initCause(cause);
    }
}
