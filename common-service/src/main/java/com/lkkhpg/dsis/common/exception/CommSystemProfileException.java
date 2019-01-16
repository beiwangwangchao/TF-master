/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 系统配置统一异常.
 * 
 * @author chenjingxiong
 */
public class CommSystemProfileException extends BaseException {

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.config";

    public static final String ORDER_EXCEPTION = "msg.error.order.sales_order_no_found";

    public static final String USEABLE_NUMBER_EXCEPTION = "msg.error.config.maxnumber_equals_curnumber";

    public static final String CURNUMBER_EXCEPTION = "msg.error.config.curnumber_no_found";

    public static final String INITNUMBER_AND_MAXNUMBER_EXCEPTION = "msg.error.config.initnunber_and_maxnumber_no_found";

    public static final String MARKET_EXCEPTION = "msg.error.config.market_no_found";

    public static final String NUMBERING_EXCEPTION = "msg.error.config.spm_inv_numbering_no_found";

    /**
     * 国家代码重复.
     */
    public static final String COUNTRY_CODE_UNIQUE_EXCEPTION = "msg.error.config.country_code_unique";

    /**
     * 国家名称重复.
     */
    public static final String COUNTRY_NAME_UNIQUE_EXCEPTION = "msg.error.config.country_name_unique";

    /**
     * 州省代码重复.
     */
    public static final String STATE_CODE_UNIQUE_EXCEPTION = "msg.error.config.state_code_unique";

    /**
     * 州省名称重复.
     */
    public static final String STATE_NAME_UNIQUE_EXCEPTION = "msg.error.config.state_name_unique";

    /**
     * 单位代码唯一异常.
     */
    public static final String UNIT_CODE_UNIQUE_EXCEPTION = "msg.error.config.unit_code_unique";

    /**
     * 单位名称唯一异常.
     */
    public static final String UNIT_NAME_UNIQUE_EXCEPTION = "msg.error.config.unit_name_unique";

    /**
     * 单位代码不能为空.
     */
    public static final String UNIT_CODE_NOT_EMPTY_EXCEPTION = "msg.error.config.unit_code_not_empty";

    /**
     * 单位代码长度过长.
     */
    public static final String UNIT_CODE_LARGER_LENGTH_EXCEPTION = "msg.error.config.unit_code_larger_length";

    /**
     * 公司代码必须唯一.
     */
    public static final String MSG_ERROR_SPM_COMPANY_CODE_UNIQUE = "msg.error.config.company_code_unique";

    /**
     * 公司名称必须唯一.
     */
    public static final String MSG_ERROR_SPM_COMPANY_NAME_UNIQUE = "msg.error.config.company_name_unique";

    /**
     * 市场名称必须唯一.
     */
    public static final String MSG_ERROR_SPM_MARKET_CODE_UNIQUE = "msg.error.config.market_code_unique";

    /**
     * 公司地址不能为空.
     */
    public static final String MSG_ERROR_SPM_COMPANY_LOCATION_EMPTY = "msg.error.config.company_location_empty";

    /**
     * 至少选择一种库存组织.
     */
    public static final String MSG_ERROR_SPM_ORG_TYPE_IS_NULL = "msg.error.config.choice_one_org_type";

    /**
     * 邮箱格式错误.
     */
    public static final String EMAIL_FORMAT = "msg.error.user.email_format";

    /**
     * 组织定义编码唯一.
     */
    public static final String ORGDEFINATION_CODE_UNIQUE_EXCEPTION = "msg.error.user.orgdefination_code_unique";

    /**
     * 城市代码必须唯一.
     */
    public static final String MSG_ERROR_SPM_CITY_CODE_UNIQUE = "msg.error.config.city_code_unique";

    /**
     * 城市名称必须唯一.
     */
    public static final String MSG_ERROR_SPM_CITY_NAME_UNIQUE = "msg.error.config.city_name_unique";

    /**
     * 没有输入销售组织.
     */
    public static final String MSG_ERROR_SALES_ORG_IS_NULL = "msg.error.config.sales_org_is_null";

    /**
     * 没有输入库存组织.
     */
    public static final String MSG_ERROR_INV_ORG_IS_NULL = "msg.error.config.inv_org_is_null";

    /**
     * 库存组织至少有一个默认值.
     */
    public static final String MSG_ERROR_INV_ORG_HAS_ONE_DEFALUT = "msg.error.config.inv_org_has_one_defalut";

    /**
     * 库存组织至少默认值为空.
     */
    public static final String MSG_ERROR_INV_ORG_DEFALUT_IS_NULL = "msg.error.config.inv_org_defalut_is_null";

    /**
     * 没有输入地址.
     */
    public static final String MSG_ERROR_ADDRESS_IS_NULL = "msg.error.config.address_is_null";

    /**
     * 销售区域重复.
     */
    public static final String MSG_ERROR_SALES_ORG_IS_REPEAT = "msg.error.config.sales_org_is_repeat";

    /**
     * 库存组织重复.
     */
    public static final String MSG_ERROR_INV_ORG_IS_REPEAT = "msg.error.config.inv_org_is_repeat";

    /**
     * 地址重复.
     */
    public static final String MSG_ERROR_ADDRESS_IS_REPEAT = "msg.error.config.address_is_repeat";

    /**
     * 地址重复.
     */
    public static final String MSG_ERROR_NOT_EXISTS_OPEN_PERIOD = "msg.error.config.not_exists_open_period";

    /**
     * 人数超过限制.
     */
    public static final String MSG_ERROR_PARTICIPANTS_MORE_THAN_LIMIT = "msg.info.sys.save_exceed";

    /**
     * 会员不能重复参加此活动.
     */
    public static final String MSG_ERROR_JOIN_EVENT_REPEAT = "msg.error.event.join_event_repeat";

    /**
     * 会员和活动不在同一市场.
     */
    public static final String MSG_ERROR_MEMBER_EVENT_NOT_SAME_MARKET = "msg.error.event.member_event_not_same_market";

    /**
     * 活动保密性必须为公开时才能发布.
     */
    public static final String MSG_ERROR_EVENT_SECRET_PUB = "msg.error.event.publish_secret_pub";

    /**
     * 活动状态必须为已发布的才能加入.
     */
    public static final String MSG_ERROR_EVENT_STATUS_PUB = "msg.error.event_status_pub";

    /**
     * 活动参与人数计算错误.
     */
    public static final String MSG_ERROR_EVENT_PARTICIPANTS_ERROR = "msg.error.event_participants_error";

    /**
     * 税码必须唯一.
     */
    public static final String MSG_ERROR_SPM_TAX_CODE_UNIQUE = "msg.error.config.tax_code_unique";

    /**
     * 币种名称必须唯一.
     */
    public static final String MSG_ERROR_SPM_CURRENCY_NAME_UNIQUE = "msg.error.config.currency_name_unique";

    /**
     * 报表程序没有定义参数.
     */
    public static final String MSG_ERROR_REPORT_PROGRAM_NO_PARAMS = "msg.error.config.report_program_no_params";
    
    /**
     * 报表程序编码重复.
     */
    public static final String MSG_ERROR_REPORT_PROGRAM_CODE_REPEAT = "msg.error.config.report_program_code_repeat";
    
    /**
     * 报表程序名称重复.
     */
    public static final String MSG_ERROR_REPORT_PROGRAM_NAME_REPEAT = "msg.error.config.report_program_name_repeat";

    /**
     * 报表参数缺少数据来源.
     */
    public static final String MSG_ERROR_REPORT_PARAMS_NO_SELECT_SOURCE = "msg.error.config.report_params_no_select_source";

    /**
     * 报表参数缺少url.
     */
    public static final String MSG_ERROR_REPORT_PARAMS_NO_SELECT_URL = "msg.error.config.report_params_no_select_url";

    /**
     * 报表参数缺少valueField.
     */
    public static final String MSG_ERROR_REPORT_PARAMS_NO_SELECT_VF = "msg.error.config.report_params_no_select_vf";

    /**
     * 报表参数缺少textField.
     */
    public static final String MSG_ERROR_REPORT_PARAMS_NO_SELECT_TF = "msg.error.config.report_params_no_select_tf";

    /**
     * 报表参数缺少code.
     */
    public static final String MSG_ERROR_REPORT_PARAMS_NO_SELECT_CODE = "msg.error.config.report_params_no_select_code";

    /**
     * 报表参数缺少textField.
     */
    public static final String MSG_ERROR_REPORT_PARAMS_NO_LOV_TF = "msg.error.config.report_params_no_lov_tf";

    /**
     * 报表参数缺少code.
     */
    public static final String MSG_ERROR_REPORT_PARAMS_NO_LOV_CODE = "msg.error.config.report_params_no_lov_code";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public CommSystemProfileException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }

}
