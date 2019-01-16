/*
 *
 */
package com.lkkhpg.dsis.mws.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * mws账户信息异常.
 * 
 * @author guanghui.liu
 */
public class MemberException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.mws.accinfo";


    /**
     * 未登录.
     */
    public static final String LOGIN_REQUIRE = "msg.error.mws.login.require";
    /**
     * mws - 邮箱不存在.
     */
    public static final String EMAIL_NOT_EXIST = "msg.error.um.email_not_exist";
    /**
     * mws - 邮箱不能为空.
     */
    public static final String EMAIL_NOT_EMPTY = "msg.error.um.email_not_isempty";
    /**
     * mws- 邮箱格式错误.
     */
    public static final String EMAIL_FORMAT_ERROR = "msg.error.um.email_format";
    /**
     * mws - 手机不存在.
     */
    public static final String PHONE_NOT_EXIST = "msg.error.um.phone_not_exist";
    /**
     * mws - 手机已存在.
     */
    public static final String PHONE_EXIST_ALREADY = "msg.error.um.phone_exist_already";

    /**
     * mws 用户已存在
     */
    public static final String USER_ALREADY_EXIST = "msg.error.um.user_already_exist";

    /**
     * mws 密码太过于简单
     */
    public static final String PASSWORD_TOO_SIMPLE="msg.error.um.password_too_simple";
    /**
     * mws - 手机不能为空.
     */
    public static final String PHONE_NOT_EMPTY = "msg.error.um.phone_not_isempty";
    /**
     * mws - 手机格式错误.
     */
    public static final String PHONE_FORMAT_ERROR = "msg.error.um.phone_format";
    /**
     * mws - 忘记密码 - 验证码出错.
     */
    public static final String ERROR_SEND_VERIFICATION = "msg.error.login.verification_code_error";
    /**
     * mws-校验码失效.
     */
    public static final String VERIFICATION_OVERDUE = "msg.error.sys.verification_expired";
    /**
     * mws-校验码不能为空.
     */
    public static final String VERIFICATION_NOT_EMPTY = "mws.error.verification_not_empty";
    /**
     * mws-会员状态异常.
     */
    public static final String MEMBER_STATUS_EXCEPTION = "mws.error.member_status_exception";
    /**
     * mws-更改密码-账号不存在.
     */
    public static final String MENBER_NOT_EXIST = "mws.error.account_not_exsit";
    /**
     * mws-验证码发送没超过60秒.
     */
    public static final String SEND_TIME = "mws.info.send_time_out";
    /**
     * mws修改密码-新密码不能为空.
     */
    public static final String NEW_PASSWORD_NOT_NULL = "msg.error.um.new_password_not_empty";
    /**
     * mws修改密码-两次密码不一致.
     */
    public static final String TWE_PASSWORD_NOT_MATCH = "msg.error.um.two_password_not_match";
    /**
     * mws修改密码-密码格式错误.
     */
    public static final String PASSWORD_FORMAT_ERROR = "msg.error.paasword.format.not.correct";
    
    /**
     * mws删除地址-只有一条地址,不允许删除.
     */
    public static final String ONLY_ONE_SITE_NOT_DELETE = "msg.error.only.one.site.not.delete";
    
    /**
     * 购物车添加失败.
     */
    public static final String SHOPCART_ADD_ITEM_ERROR = "msg.error.shopcart.add.item.error";
    
    /**
     * 查询不到购物车uuid.
     */
    public static final String SHOPCART_UUID_NOT_FOUND = "msg.error.shopcart.uuid.not.found";
    
    /**
     * 商品ID不存在.
     */
    public static final String PRODUCT_ID_NOT_SPECIFIED = "msg.error.productid.not.specified";
    
    /**
     * 购物车记录不存在.
     */
    public static final String SHOPCART_ITEM_NOT_EXISTS = "msg.error.shopcart.item.not.exists";
    
    /**
     * 购物车记录删除失败.
     */
    public static final String SHOPCART_ITEM_DELETE_ERROR = "msg.error.shopcart.item.delete.fail";
    
    /**
     * 购物车记录更新失败.
     */
    public static final String SHOPCART_ITEM_UPDATE_ERROR = "msg.error.shopcart.item.upadte.fail";
    
    /**
     * 订单已失效.
     */
    public static final String ORDER_NOT_ALLOW_SUBMIT_TWICE = "msg.error.order.expired";
    
    /**
     * 用户管理 - 密码不能为空.
     */
    public static final String PASSWORD_NOT_EMPTY = "msg.error.user.password_not_empty";
    
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
    
    public static final String NOT_YET_DISTRIBUTOR = "msg.error.mws.not_yet_distributor";

    public static final String COMPANY_NOT_PAY_ZERO = "msg.error.mws.company_not_pay_zero";
    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public MemberException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }

}
