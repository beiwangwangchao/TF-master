/*
 *
 */
package com.lkkhpg.dsis.admin.user.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * @author njq.niu@hand-china.com
 * @author xiawang.liu@hand-china.com
 *
 *         2016年3月3日
 */
public class AccountException extends BaseException {

    private static final long serialVersionUID = -3250576758107608016L;

    // 验证码不正确
    public static final String MSG_LOGIN_INVALID_CODE = "msg.error.login.verification_code_error";

    // 用户名密码不匹配
    public static final String MSG_LOGIN_NAME_PASSWORD = "msg.error.login.name_password_not_match";

    // 账户状态未激活
    public static final String MSG_LOGIN_ACCOUNT_NOT_INACTIVE = "msg.error.login.account_not_active";

    // 账户失效
    public static final String MSG_LOGIN_ACCOUNT_INVALID = "msg.error.login.account_invalid";

    // 账户失效
    public static final String MSG_LOGIN_PWD_INVALID = "msg.error.login.account_pwd_invalid";
    
    // 用户密码错误超过一定次数锁定一段时间
    public static final String MSG_LOGIN_ACCOUNT_LOCK_MAX_RETRY_TIMES = "msg.error.mws.login.account_lock_max_retry_times";
    
    /**
     * mws-账号类型错误.
     */
    public static final String ACCOUNT_TYPE_ERROR = "mws.error.account_type_error";

    public AccountException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}
