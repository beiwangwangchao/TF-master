/*
 *
 */
package com.lkkhpg.dsis.admin.user.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 用户统一异常.
 * 
 * @author chenjingxiong
 */
public class UserException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.user";
    
    /**
     * 用户管理 - 登录名被占用.
     */
    public static final String USER_EXIST = "msg.error.user.um_exist_already";
    /**
     * 用户管理 - 登录名不能为空.
     */
    public static final String USER_NOT_ISEMPTY = "msg.error.user.user_not_isempty";
    /**
     * 用户管理 - IPONT邮箱不能为空.
     */
    public static final String IPONT_EMAIL_NOT_EMPTY = "msg.error.um.ipoint_member_email_not_null";
    /**
     * 用户管理 - 登录名格式错误.
     */
    public static final String USER_FORMAT = "msg.error.system.user.loginname.format.not.correct";
    /**
     * 用户管理 - 手机被占用.
     */
    public static final String PHONE_EXIST = "msg.error.user.phone_exist_already";
    /**
     * 用户管理 - 手机不存在.
     */
    public static final String PHONE_NOT_EXIST = "msg.error.user.phone_not_exist";
    /**
     * 用户管理 - 手机不能为空.
     */
    public static final String PHONE_NOT_ISEMPTY = "msg.error.user.phone_not_isempty";
    /**
     * 用户管理 - 手机格式错误.
     */
    public static final String PHONE_FORMAT = "msg.error.user.phone_format";
    /**
     * 用户管理 - 邮箱被占用.
     */
    public static final String EMAIL_EXIST = "msg.error.user.email_exist";
    /**
     * 用户管理 - 邮箱不存在.
     */
    public static final String EMAIL_NOT_EXIST = "msg.error.user.email_not_exist";
    /**
     * 用户管理 - 邮箱不能为空.
     */
    public static final String EMAIL_NOT_ISEMPTY = "msg.error.user.email_not_isempty";
    /**
     * 用户管理 - 邮箱格式错误.
     */
    public static final String EMAIL_FORMAT = "msg.error.user.email_format";
    /**
     * 密码管理 - 忘记密码 - 验证码出错.
     */
    public static final String LOGIN_VERIFICATION_CODE_ERROR = "msg.error.login.verification_code_error";
    /**
     * 密码管理 - 忘记密码 - 校验码出错.
     */
    public static final String CAPTCHA_ERROR = "msg.error.captcha_error";
    /**
     * 密码管理 - 忘记密码 - 校验码验证方式出错.
     */
    public static final String CAPTCHA_WAY_ERROR = "msg.error.captcha_error_wrong_way";
    /**
     * 密码管理 - 忘记密码 - 请先发送校验码.
     */
    public static final String NOT_SENT_CAPTCHA = "msg.error.system.not_sent_captcha";
    /**
     * 密码管理 - 忘记密码 - 请输入验证码.
     */
    public static final String ENTER_VERIFICATION = "msg.error.system.enter_verification";
    /**
     * 密码管理 - 忘记密码 - 请输入校验码.
     */
    public static final String ENTER_CAPTCHA = "msg.error.system.enter_captcha";
    /**
     * 密码管理 - 忘记密码 - 校验码已过期.
     */
    public static final String CAPTCHA_EXPIRED = "msg.error.system.captcha_expired";
    /**
     * 密码管理 - 修改密码 - 两次密码不能一致.
     */
    public static final String USER_PASSWORD_NOT_SAME_TWICE = "msg.error.password.not_same_twice";
    /**
     * 密码管理 - 修改密码 - 当前密码校验.
     */
    public static final String USER_PASSWORD_WRONG = "msg.error.password.current_password";
    /**
     * 密码管理 - 修改密码 - 密码格式不正确.
     */
    public static final String USER_PASSWORD_REQUIREMENT = "msg.error.user.password_format_error";
    /**
     * 密码管理 - 修改密码 - 新密码不能与旧密码一致.
     */
    public static final String USER_PASSWORD_SAME = "msg.error.system.not_allowed_same_with_old_password";
    /**
     * 用户管理 - 个人信息 - 用户名不存在.
     */
    public static final String USER_NOT_EXIST = "msg.error.system.user_not_exist";
    /**
     * 用户管理 - 个人信息 - 用户已失效.
     */
    public static final String USER_EXPIRED = "msg.error.system.user_expired";
    /**
     * 用户管理 - 个人信息 - 创建用户失败.
     */
    public static final String USER_INSERT_FAIL = "msg.error.system.user_insert_fail";
    /**
     * 用户管理 - 个人信息 - 更新用户失败.
     */
    public static final String USER_UPDATE_FAIL = "msg.error.system.user_update_fail";
    /**
     * 用户管理 - 密码不能为空.
     */
    public static final String PASSWORD_NOT_EMPTY = "msg.error.user.password_not_empty";
    /**
     * 用户管理 - 临时口令已失效.
     */
    public static final String TEMP_PASSWORD_EXPIRED = "msg.error.user.temp_password_expired";
    /**
     * 密码管理 - 忘记密码 - 请输入用户名.
     */
    public static final String ENTER_USERNAME = "msg.error.system.enter_username";
    /**
     * logger-日期格式捕获异常.
     */
    public static final String DATE_FORMAT = "msg.error.integration.dapp.date.format.failed";
    
    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public UserException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }

}
