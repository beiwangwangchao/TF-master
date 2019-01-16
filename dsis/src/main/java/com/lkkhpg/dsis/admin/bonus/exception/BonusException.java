/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * Bonus Exception.
 * @author chenjingxiong
 */
public class BonusException extends BaseException {

    public static final String MSG_ERROR_BM_WRONG_RELEASE_TIME = "msg.error.bm_wrong_release_time";

    public static final String MSG_ERROR_BM_WRONG_RELEASE_ROLLBACK = "msg.error.bm_wrong_release_rollback";

    public static final String MSG_ERROR_BM_WRONG_ROLLBACK_AUTO = "msg.error.bm_wrong_rollback_auto";

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.bonus";

    /**
     * 构造方法.
     *
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public BonusException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }
}
