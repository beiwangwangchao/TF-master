/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 奖金统一异常.
 * @author gulin
 *
 */
public class CommBonusException extends BaseException {
    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.bonus";
    
    public static final String MSG_ERROR_BONUS_CREATE_FILE_FAIL = "msg.error.bonus.create.file.fail";
    
    public static final String MSG_ERROR_BONUS_FINAL_RECORD_EMPTY = "msg.error.bonus.final.record.empty";
    
    /**
     * ipoint奖金提交状态异常.
     */
    public static final String MSG_ERROR_IPOINT_BONUS_SUBMIT_EXCEPTION = "msg.error.bonus.ipoint_submit_status_error";
    
    /**
     * ipoint奖金审核状态异常.
     */
    public static final String MSG_ERROR_IPOINT_BONUS_APPROVE_EXCEPTION = "msg.error.bonus.ipoint_approve_status_error";
    
    /**
     * ipoint奖金拒绝状态异常.
     */
    public static final String MSG_ERROR_IPOINT_BONUS_REJECT_EXCEPTION = "msg.error.bonus.ipoint_reject_status_error";

    /**
     * 报表生成失败.
     */
    public static final String MSG_ERROR_REPORT_FILE_GENERATION_FAIL = "msg.error.Report_file_generation_failed";
    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public CommBonusException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }
}
