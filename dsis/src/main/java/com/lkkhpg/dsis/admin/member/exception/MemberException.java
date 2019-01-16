/*
 *
 */
package com.lkkhpg.dsis.admin.member.exception;

import com.lkkhpg.dsis.common.exception.CommMemberException;

/**
 * 会员统一异常.
 * 
 * @author chenjingxiong
 */
public class MemberException extends CommMemberException {
    private static final long serialVersionUID = 1L;
    
    /**
     * IPOINT用户只能查看今天加入的会员.
     */
    public static final String MSG_ERROR_IPOINT_ONLY_QUERY_CURR_JOINT_DATE_MEMBER = "msg.error.member.ipoint_only_query_curr_joint_date_member";
    
    
    /**
     * 会员销售组织数据屏蔽.
     */
    public static final String MSG_ERROR_MEMBER_SALES_ORG_DATA_MASK = "msg.error.member.sales_org_data_mask";
    
    /**
     * 会员市场组织数据屏蔽.
     */
    public static final String MSG_ERROR_MEMBER_MARKET_ORG_DATA_MASK = "msg.error.member.market_org_data_mask";
    
    /**
     * 会员变更上线记录已存在.
     */
    public static final String MSG_ERROR_UPSTREAM_CHG_EXIST = "msg.error.member.upstream_change_exist";
    
    /**
     * 会员停权所选终止日期超出范围.
     */
    public static final String MSG_ERROR_TERMINATE_DATE_INVALID = "msg.error.member.terminate_date_invalid";

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
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public MemberException(String descriptionKey, Object[] parameters) {
        super(descriptionKey, parameters);
    }
}
