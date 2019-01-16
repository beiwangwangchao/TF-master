/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 会员统一异常.
 * 
 * @author chenjingxiong
 */
public class CommMemberException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.member";

    /**
     * 会员搜索条件空异常.
     */
    public static final String MSG_NO_SEARCH_CRITERIA = "msg.warning.system.no_search_criteria";

    /**
     * 未选新上线异常.
     */
    public static final String MSG_ERROR_NO_NEW_SPONSOR = "msg.error.member.no_new_sponsor";

    /**
     * 新上线无效异常.
     */
    public static final String MSG_ERROR_INVALID_SPONSOR = "msg.error.member.invalid_sponsor";

    /**
     * 会员不存在异常.
     */
    public static final String MSG_ERROR_NO_MEMBER = "msg.error.member.validate_member_error";

    /**
     * 会员ID不存在异常.
     */
    public static final String MSG_ERROR_MEMBER_CODE_EXIST = "msg.error.member.member_code_exist";

    /**
     * 会员变更状态异常.
     */
    public static final String MSG_WARNING_SYS_STATUSCHANGE_ILLEGAL = "msg.warning.system.statuschange.illegal";

    /**
     * 会员账务事务处理异常.
     */
    public static final String MSG_ERROR_MEMBER_ACCOUNT_TRX_DEAL = "msg.error.member.account_trx_deal";

    /**
     * 事务处理类型无效.
     */
    public static final String MSG_ERROR_TRX_TYPE_INVALID = "msg_error_trx_type_invalid";

    /**
     * 事务处理来源键值无效.
     */
    public static final String MSG_ERROR_TRX_SOURCE_KEY_INVALID = "msg_error_trx_source_key_invalid";

    /**
     * 事务处理来源行键值无效.
     */
    public static final String MSG_ERROR_TRX_SOURCE_LINE_KEY_INVALID = "msg_error_trx_source_line_key_invalid";

    /**
     * 期间已关闭或未打开.
     */
    public static final String MSG_ERROR_OM_BONUS_MONTH_ERROR = "msg.error.order.bonus_month_error";

    /**
     * 不能小于系统限制年龄.
     */
    public static final String MSG_ERROR_AGE_LESS_THAN_LIMIT = "msg.error.member.age_less_than_limit";

    /**
     * 中文姓或英文姓至少输入一个.
     */
    public static final String MSG_ERROR_LAST_NAME_REQUIRE_ONE = "msg.error.member.last_name_require_one";

    /**
     * 中文名或英文名至少输入一个.
     */
    public static final String MSG_ERROR_FIRST_NAME_REQUIRE_ONE = "msg.error.member.first_name_require_one";

    /**
     * chn first name汉字长度超过48个字节.
     */
    public static final String MSG_ERROR_CHINESE_FIRST_NAME_LENGTH_ERROR = "msg.error.member.chinese_first_name_length_error";

    /**
     * chn last name汉字长度超过45个字节.
     */
    public static final String MSG_ERROR_CHINESE_LAST_NAME_LENGTH_ERROR = "msg.error.member.chinese_last_name_length_error";

    /**
     * eng first name长度超过48个字节.
     */
    public static final String MSG_ERROR_ENGLISH_FIRST_NAME_LENGTH_ERROR = "msg.error.member.english_first_name_length_error";

    /**
     * eng last name长度超过45个字节.
     */
    public static final String MSG_ERROR_ENGLISH_LAST_NAME_LENGTH_ERROR = "msg.error.member.english_last_name_length_error";

    /**
     * 不能小于组织参数年龄.
     */
    public static final String MSG_ERROR_AGE_LESS_THAN_PARAM = "msg.error.member.age_less_than_param";

    /**
     * 家庭地址必输.
     */
    public static final String MSG_ERROR_HOME_ADDRESS_REQUIRED = "msg.error.member.home_address_required";

    // /**
    // * 家庭地址必须为空.
    // */
    // public static final String MSG_ERROR_HOME_ADDRESS_MUST_NULL =
    // "msg.error.member.home_address_must_null";

    /**
     * 种族必输.
     */
    public static final String MSG_ERROR_RACE_REQUIRED = "msg.error.member.race_required";

    // /**
    // * 种族必须为空.
    // */
    // public static final String MSG_ERROR_RACE_MUST_NULL =
    // "msg.error.member.race_must_null";

    /**
     * 公民类型必输.
     */
    public static final String MSG_ERROR_CITIZEN_TYPE_REQUIRED = "msg.error.member.citizen_type_required";

    // /**
    // * 公民类型必须为空.
    // */
    // public static final String MSG_ERROR_CITIZEN_TYPE_MUST_NULL =
    // "msg.error.member.citizen_type_must_null";

    /**
     * 健税保外必输.
     */
    public static final String MSG_ERROR_NHI_TAX_EXCLUDED_REQUIRED = "msg.error.member.nhi_tax_excluded_required";

    // /**
    // * 健税保外必须为空.
    // */
    // public static final String MSG_ERROR_NHI_TAX_EXCLUDED_MUST_NULL =
    // "msg.error.member.nhi_tax_excluded_must_null";

    /**
     * GST ID必输.
     */
    public static final String MSG_ERROR_GST_ID_REQUIRED = "msg.error.member.gst_id_required";

    // /**
    // * GST ID必须为空.
    // */
    // public static final String MSG_ERROR_GST_ID_MUST_NULL =
    // "msg.error.member.gst_id_must_null";

    /**
     * ID Number已存在.
     */
    public static final String MSG_ERROR_MEMBER_ID_NUMBER_EXIST = "msg.error.member.member_id_number_exist";
    /**
     * 会员的电话号码已存在
     */
    public static final String MSG_ERROR_MEMBER_PHONE_NO_EXIST="msg.error.member.member_phone_no_exist";

    /**
     * 配偶ID Number已存在.
     */
    public static final String MSG_ERROR_SPOUSE_ID_NUMBER_EXIST = "msg.error.member.spouse_id_number_exist";

    /**
     * 公司商业注册码已存在.
     */
    public static final String MSG_ERROR_BR_NUMBER_EXIST = "msg.error.member.br_number_exist";

    /**
     * 公司ID不存在.
     */
    public static final String MSG_ERROR_SPM_COMPANY_ID_NOT_EXIST = "msg.error.spm.company_id_not_exist";

    /**
     * 销售组织Id不存在.
     */
    public static final String MSG_ERROR_SPM_ORGANIZATION_ID_NOT_EXIST = "msg.error.spm.organization_id_not_exist";

    /**
     * 接口异常.
     */
    public static final String MSG_ERROR_GDS_MEMBER_SYN = "msg_error_gds_member_syn";

    /**
     * 导入会员重复.
     */
    public static final String MSG_ERROR_IMPORT_REPEAT = "msg_error_import_member_repeat";

    /**
     * 创建会员账户错误.
     */
    public static final String MSG_ERROR_CREATE_MEM_ACCOUNT = "msg.error.member.create_mem_account_error";

    /**
     * 会员账户已存在.
     */
    public static final String MSG_ERROR_MEM_ACCOUNT_EXIST = "msg.error.member.mem_account_exist";

    /**
     * 相关人中文名或英文名必输其一.
     */
    public static final String MSG_ERROR_RELATIONSHIP_NAME_REQUIRED = "msg.error.member.relationship_name_required";

    /**
     * 相关人的受益人必输.
     */
    public static final String MSG_ERROR_RELATIONSHIP_BENF_REQUIRED = "msg.error.member.relationship_benf_required";

    /**
     * 相关人的具体关系必输.
     */
    public static final String MSG_ERROR_RELATIONSHIP_REL_DESC_REQUIRED = "msg.error.member.relationship_rel_desc_required";

    /**
     * 联系号码必输.
     */
    public static final String MSG_ERROR_PNHONE_NO_REQUIRED = "msg.error.member.phone_no_required";

    /**
     * 账单地点必输.
     */
    public static final String MSG_ERROR_SHIP_SITE_REQUIRED = "msg.error.member.bill_site_required";

    /**
     * 配送地点必输.
     */
    public static final String MSG_ERROR_BILL_SITE_REQUIRED = "msg.error.member.ship_site_required";

    /**
     * 导入会员列表为空.
     */
    public static final String MSG_ERROR_IMPORT_MEMBER_IS_NULL = "msg.error.member.mport_member_is_null";

    /**
     * 导入会员code为空.
     */
    public static final String MSG_ERROR_IMPORT_MEMBER_CODE_IS_NULL = "msg.error.member.mport_member_code_is_null";

    /**
     * 导入会员code不能重复.
     */
    public static final String MSG_ERROR_IMPORT_MEMBER_CODE_REPEAT = "msg.error.member.mport_member_code_repeat";

    /**
     * 导入会员通过验证但未处理.
     */
    public static final String MSG_ERROR_IMPORT_MEMBER_UNPROCESSED = "msg.error.member.mport_member_unprocessed";

    /**
     * 上传文件大小超过2M.
     */
    public static final String MSG_ERROR_UPLOAD_FILE_SIZE_OVER = "msg.error.member.upload_file_size_over";

    /**
     * 上传文件为空.
     */
    public static final String MSG_ERROR_UPLOAD_FILE_IS_NULL = "msg.error.member.upload_file_is_null";

    /**
     * 上传文件解析异常.
     */
    public static final String MSG_ERROR_UPLOAD_FILE_RESOLVE_ERROR = "msg.error.member.upload_file_resolve_error";

    /**
     * 数字格式转换异常.
     */
    public static final String MSG_ERROR_UPLOAD_NUMBER_FORMAT_ERROR = "msg.error.member.number_format_error";

    /**
     * 验证会员异常.
     */
    public static final String MSG_ERROR_VALIDATE_MEMBER_ERROR = "msg.error.member.validate_member_error";

    /**
     * 会员code为空.
     */
    public static final String MSG_ERROR_UPLOAD_MEMBER_CODE_NULL = "msg.error.member.upload_member_code_null";

    /**
     * 会员code重复.
     */
    public static final String MSG_ERROR_UPLOAD_MEMBER_CODE_REPEAT = "msg.error.member.upload_member_code_repeat";

    /**
     * 卡号加密失败.
     */
    public static final String MSG_ERROR_CARD_NUM_DECRYPT_ERROR = "msg.error.member.card_num_decrypt_error";

    /**
     * 无权限重置临时密码.
     */
    public static final String MSG_ERROR_RESET_PWD_NO_PERMISSION = "msg.error.member.reset_pwd_no_permission";

    /**
     * 发送短信失败.
     */
    public static final String MSG_ERROR_SEND_SMS_MESSAGE_ERROR = "msg.error.member.send_sms_message_error";

    /**
     * 该会员无法转为经销商.
     */
    public static final String MSG_ERROR_CANNOT_CHANGE_TO_DISTRIBUTOR = "msg.error.member.cannot_change_to_distributor";

    /**
     * 该会员已经存在转经销商申请记录.
     */
    public static final String MSG_ERROR_APPLY_ROLE_RECORD_EXIST = "msg.error.member.apply_role_record_exist";

    /**
     * 查询日期超过当前日期.
     */
    public static final String MSG_ERROR_QUERY_DATE_MORE_THAN_CURRENT_TIME = "msg.error.query_date_more_than_current_time";

    /**
     * 账户号码不能少于4位.
     */
    public static final String MSG_ERROR_ACCOUNT_NUMBER_LENGTH_VALIDATE = "msg.error.account_number_length_validate";

    /**
     * 日期格式转换出错.
     */
    public static final String MSG_ERROR_DATE_FORMAT_ERROR = "msg.error.date_format_error";

    /**
     * 该会员不存在当前月份的等级信息.
     */
    public static final String MSG_ERROR_MEMBER_CURRENT_RANK_NOT_EXIST = "msg.error.member_current_rank_not_exist";

    /**
     * 会员的银行账户已存在(针对马来市场).
     */
    public static final String MSG_ERROR_MEMBER_BANK_ACCOUNT_EXIST = "msg.error.member_bank_account_exist";

    /**
     * 当前市场未启用VIP.
     */
    public static final String MSG_ERROR_CURRENT_MARKET_DISABLE_VIP = "msg.error.current_market_disable_vip";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public CommMemberException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }
}
