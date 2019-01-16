/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * D-APP接口统一异常.
 * 
 * @author frank.li
 */
public class IntegrationException extends BaseException {
    private static final long serialVersionUID = 1L;

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.integration.dapp.exception";

    /**
     * 调用D-APP接口报错.
     */
    public static final String MSG_ERROR_DAPP_INVOKE_GDS_INTERFACE = "msg.error.integration.dapp.invoke_dapp_int_error";

    /**
     * GDS系统orgCode在POS系统无映射.
     */
    public static final String MSG_ERROR_ORG_CODE_NO_MAPPING = "msg_error_gds_org_code_no_mapping";

    /**
     * GDS系统lang在POS系统无映射.
     */
    public static final String MSG_ERROR_LANG_NO_MAPPING = "msg_error_gds_lang_no_mapping";

    /**
     * 日期格式转换错误.
     */
    public static final String MSG_ERROR_DAPP_DATE_FORMAT_ERROR = "msg.error.integration.dapp.date_format_error";

    /**
     * 根据会员号和市场找不到对应帐号.
     */
    public static final String MSG_ERROR_DISTRIBUTOR_NOT_EXIST = "msg.error.integration.dapp.distributor_not_exist";

    /**
     * 会员卡号不存在.
     */
    public static final String MSG_ERROR_MEMBER_CODE_NOT_EXIST = "msg.error.integration.dapp.member_code_not_exist";

    /**
     * market不能为空.
     */
    public static final String MSG_ERROR_MARKET_NOT_NULL = "msg.error.integration.dapp.market_not_null";

    /**
     * 会员中文first name不能为空.
     */
    public static final String MSG_ERROR_FIRST_NAME_CHN_NOT_NULL = "msg.error.integration.dapp.first_name_chn_not_null";

    /**
     * 会员中文last name不能为空.
     */
    public static final String MSG_ERROR_LAST_NAME_CHN_NOT_NULL = "msg.error.integration.dapp.last_name_chn_not_null";

    /**
     * 会员英文first name不能为空.
     */
    public static final String MSG_ERROR_FIRST_NAME_ENG_NOT_NULL = "msg.error.integration.dapp.first_name_eng_not_null";

    /**
     * 会员英文last name不能为空.
     */
    public static final String MSG_ERROR_LAST_NAME_ENG_NOT_NULL = "msg.error.integration.dapp.last_name_eng_not_null";

    /**
     * 会员中英文名至少填一个.
     */
    public static final String MSG_ERROR_MEMBER_CHN_ENG_LEAST_ONE = "msg.error.integration.dapp.member_first_name_chn_eng_least_one";

    /**
     * 受益人中英文姓名至少填一个.
     */
    public static final String MSG_ERROR_BENEF_CHN_ENG_LEAST_ONE = "msg.error.integration.dapp.benef_name_chn_eng_least_one";

    /**
     * 配偶中英文姓名至少填一个.
     */
    public static final String MSG_ERROR_SPOUSE_CHN_ENG_LEAST_ONE = "msg.error.integration.dapp.spouse_name_chn_eng_least_one";

    /**
     * 相关人中英文姓名至少填一个.
     */
    public static final String MSG_ERROR_CHN_ENG_LEAST_ONE = "msg.error.integration.dapp.chn_eng_least_one";

    /**
     * 姓和名没有同时给出.
     */
    public static final String MSG_ERROR_FIRST_LAST_NO_GIVEN_AT_SAME_TIME = "msg.error.integration.dapp.first_last_no_given_at_same_time";

    /**
     * 当前市场的销售组织不存在.
     */
    public static final String MSG_ERROR_SALES_ORG_NO_EXIST = "msg.error.integration.dapp.sales_org_no_exist";

    /**
     * distributor number 或 idnumber至少填一个.
     */
    public static final String MSG_ERROR_DISNUM_OR_IDNUM_AT_LEAST_ONE = "msg.error.integration.dapp.disnum_idnum_least_one";

    /**
     * 日期格式转化错误.
     */
    public static final String MSG_ERROR_DATE_FORMAT_FAILED = "msg.error.integration.dapp.date.format.failed";

    /**
     * 同步订单-更新同步标识失败.
     */
    public static final String MSG_ERROR_ORDER_UPDATE_ASNC_FLAG = "error to update dappSyncFlag";

    /**
     * 订单作废-订单的销售渠道非D-APP,不允许变更该订单的支付状态.
     */
    public static final String MSG_ERROR_ORDER_CANCEL_ERROR = "error to cancel order";

    /**
     * 订单作废-订单的销售渠道非D-APP,不允许变更该订单的支付状态.
     */
    public static final String MSG_ERROR_ORDER_CANCEL_DAPP = "sourceType isn't DAPP,not allow to change orderStatus";

    /**
     * 订单作废-不允许变更该订单的支付状态.
     */
    public static final String MSG_ERROR_ORDER_CANCEL_STATUS = "orderStatus is %s,not allow to change orderStatus";

    /**
     * 订单作废-订单不存在.
     */
    public static final String MSG_ERROR_ORDER_CANCEL_EXIST = "order is not exist";
    
    /**
     * 订单作废-订单的发运数量>0，不允许失效.
     */
    public static final String MSG_ERROR_ORDER_CANCEL_SHIPPING_QTY = "Shipping Quantity is more than zero";

    /**
     * 姓名总长度超过69位.
     */
    public static final String MSG_ERROR_FIRST_PLUS_LAST_TOO_LONG = "msg.error.integration.dapp.first_plus_last_too_long";

    /**
     * 银行不存在.
     */
    public static final String MSG_ERROR_BANK_IS_NOT_EXIST = "msg.error.integration.dapp.the_bank_is_not_exist";

    /**
     * 分行不存在.
     */
    public static final String MSG_ERROR_BRANCH_BANK_IS_NOT_EXIST = "msg.error.integration.dapp.the_branch_bank_is_not_exist";
    
    /**
     * 公司不存在.
     */
    public static final String MSG_ERROR_COMPANY_IS_NOT_EXIST = "msg.error.integration.dapp.the_company_is_not_exist";

    /**
     * 相关人生日不能为空.
     */
    public static final String MSG_ERROR_RELATIONSHIP_BIRTHDAY_CAN_NOT_NULL = "msg.error.integration.dapp.relationship_birthday_can_not_null";

    /**
     * 相关人id type不能为空.
     */
    public static final String MSG_ERROR_RELATIONSHIP_ID_TYPE_CAN_NOT_NULL = "msg.error.integration.dapp.relationship_id_type_can_not_null";

    /**
     * 相关人id不能为空.
     */
    public static final String MSG_ERROR_RELATIONSHIP_ID_CAN_NOT_NULL = "msg.error.integration.dapp.relationship_id_can_not_null";

    /**
     * 相关人描述不能为空.
     */
    public static final String MSG_ERROR_RELATIONSHIP_DESC_CAN_NOT_NULL = "msg.error.integration.dapp.relationship_desc_can_not_null";

    /**
     * 性别不能为空.
     */
    public static final String MSG_ERROR_RELATIONSHIP_GENDER_CAN_NOT_NULL = "msg.error.integration.dapp.relationship_gender_can_not_null";

    /**
     * 账户号码不能为空.
     */
    public static final String MSG_ERROR_BANK_ACCOUNT_CAN_NOT_NULL = "msg.error.integration.dapp.bank_account_can_not_null";

    /**
     * 账户人ID NUMBER不能为空.
     */
    public static final String MSG_ERROR_BANK_ACCOUNT_ID_NUMBER_CAN_NOT_NULL = "msg.error.integration.dapp.bank_account_id_number_can_not_null";

    /**
     * 银行代号不能为空.
     */
    public static final String MSG_ERROR_BANK_CODE_CAN_NOT_NULL = "msg.error.integration.dapp.bank_code_can_not_null";

    /**
     * 持有人不能为空.
     */
    public static final String MSG_ERROR_PAYEE_NAME_CAN_NOT_NULL = "msg.error.integration.dapp.payee_name_can_not_null";

    /**
     * appNo已经存在.
     */
    public static final String MSG_ERROR_DAPP_NO_EXIST = "msg.error.integration.dapp.dapp_no_exist";

    /**
     * 国家代码不存在.
     */
    public static final String MSG_ERROR_COUNTRY_CODE_IS_NOT_EXIST = "msg.error.integration.dapp.country_code_is_not_exist_";

    /**
     * 州省代码不存在.
     */
    public static final String MSG_ERROR_STATE_CODE_IS_NOT_EXIST = "msg.error.integration.dapp.state_code_is_not_exist_";

    /**
     * 城市代码不存在.
     */
    public static final String MSG_ERROR_CITY_CODE_IS_NOT_EXIST = "msg.error.integration.dapp.city_code_is_not_exist_";

    /**
     * 同步订单-返回信息错误.
     */
    public static final String MSG_ERROR_ORDER_CALLBACK_RETURN_MSG_ERROR = "addOrderCallback ：callback msg null or errorCode != 0";
    
    public static final String MSG_ERROR_MARKET_NOT_EXIST = "msg.error.integration.dapp.market_code_not_exist";
    
    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public IntegrationException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }
    
    public static String getErrorStackTrace(Exception e){
        String errorMsg = e.getStackTrace()[0].toString();
        for(StackTraceElement element : e.getStackTrace()){
            if(element.getClassName().contains("com.lkkhpg")){
                errorMsg = element.toString();
                break;
            }
            }
        return errorMsg + " -> " + e.getMessage();
    }
}
