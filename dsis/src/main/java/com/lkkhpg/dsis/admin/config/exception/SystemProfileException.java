/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.config.exception;

import com.lkkhpg.dsis.common.exception.CommSystemProfileException;

/**
 * 系统配置统一异常.
 * 
 * @author chenjingxiong
 */
public class SystemProfileException extends CommSystemProfileException {
    
    /**
     * 奖金金额不能重叠.
     */
    public static final String MSG_ERROR_BANK_BONUS_AMOUNT =  "msg_error_bank_bonus_amount";
    
    /**
     * 有邮箱为空.
     */
    public static final String MSG_ERROR_MESSAGE_PUBLIC =  "msg_error_message_publicc";
    
    /**
     * 有手机号为空.
     */
    public static final String MSG_ERROR_MESSAGE_PUBLIC_PHONE =  "msg_error_message_public_phone";
    
    /**
     * 银行手续费错误.
     */
    public static final String MSG_ERROR_BANK_CHARCES = "msg_error_bank_charces";
    
    /**
     * 同一市场存在时间重叠的有效规则.
     */
    public static final String MSG_ERROR_OM_HAVE_INVOICE_RULE = "msg.error.order.have_invoice_rule";
    
    /**
     * 业务实体编码必须唯一.
     */
    public static final String MSG_ERROR_SPM_OPERATING_UNIT_CODE_UNIQUE = "msg.error.config.operating_unit_code_unique";

    /**
     * 币种代码必须唯一.
     */
    public static final String MSG_ERROR_SPM_CURRENCY_CODE_UNIQUE = "msg.error.config.currency_code_unique";

    /**
     * 币种名称必须唯一.
     */
    public static final String MSG_ERROR_SPM_CURRENCY_NAME_UNIQUE = "msg.error.config.currency_name_unique";
	
	/**
     * 币种符号必须唯一.
     */
    public static final String MSG_ERROR_SPM_CURRENCY_SYMBOL_UNIQUE = "msg.error.config.currency_symbol_unique";

    /**
     * Service Center负责会员不能为空.
     */
    public static final String MSG_ERROR_SPM_SC_CHARGE_MEMBER_NOT_EMPTY = "msg.error.config.sc_charge_member_not_empty";
    
    /**
     * Service Center编码重复.
     */
    public static final String MSG_ERROR_SPM_SC_CODE_REPEAT = "msg.error.config.service_center_code_repeat";

    /**
     * Service Center负责会员已经存在.
     */
    public static final String MSG_ERROR_SPM_CHARGE_MEMBER_ALREADY_EXIST = "msg.error.cfg.charge_member_already_exist";

    /**
     * 会员已经分配给某个Service Center.
     */
    public static final String MSG_ERROR_SPM_SC_MEMBER_ALREADY_EXIST_SC = "msg.error.config.sc_member_already_exist_sc";

    /**
     * 重复的组织优先级.
     */
    public static final String MSG_ERROR_SPM_PRIORITY_VALUE_REPEAT = "spm.error.priority_value_repeat";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public SystemProfileException(String descriptionKey, Object[] parameters) {
        super(descriptionKey, parameters);
    }

}
