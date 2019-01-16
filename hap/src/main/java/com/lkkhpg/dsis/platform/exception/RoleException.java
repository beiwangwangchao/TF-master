/*
 *
 */
package com.lkkhpg.dsis.platform.exception;

/**
 * @author njq.niu@hand-china.com
 *
 * 2016年4月25日
 */
public class RoleException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 账号角色不存在.
     */
    public static final String MSG_INVALID_USER_ROLE = "mws.error.account_role_invalid";
    
    /**
     * 非法参数.
     */
    public static final String MSG_INVALID_PARAMETER = "mws.error.invalid_parameter";

    public RoleException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}
