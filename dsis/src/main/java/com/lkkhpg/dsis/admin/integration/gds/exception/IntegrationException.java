/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 接口统一异常.
 * 
 * @author mclin
 */
public class IntegrationException extends BaseException {
    private static final long serialVersionUID = 1L;

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.integration.gds.exception";

    /**
     * 调用GDS接口报错.
     */
    public static final String MSG_ERROR_ISG_INVOKE_GDS_INTERFACE = "msg.error.isg.invoke_gds_interface";

    /**
     * 接口报错-appEligibleSuspend 會員申请资格中止.
     */
    public static final String MSG_ERROR_ISG_APP_ELIGIBLE_SUSPEND = "msg.error.isg.app_eligible_suspend";

    /**
     * 接口报错-applyMoveLine 會員移線申请.
     */
    public static final String MSG_ERROR_ISG_MOVE_LINE = "msg.error.isg.member_move_line";

    /**
     * 接口报错-applyStatusQuery 会员移线、停权状态申请查询.
     */
    public static final String MSG_ERROR_ISG_APPLY_STATUS_QUERY = "msg.error.isg.apply_status_query";

    /**
     * 接口报错-batchSaveDealer 小批量会员资料保存.
     */
    public static final String MSG_ERROR_ISG_BATCH_SAVE_DEALER = "msg.error.isg.batch_save_dealer";

    /**
     * 接口报错-batchSaveSo 小批量同步销售資料.
     */
    public static final String MSG_ERROR_ISG_BATCH_SAVE_SO = "msg.error.isg.batch_save_so";

    /**
     * 接口报错-closePeriod 关闭机构月份.
     */
    public static final String MSG_ERROR_ISG_CLOSE_PERIOD = "msg.error.isg.close_period";

    /**
     * 接口报错-findExpelDealer 下载批删会员资料.
     */
    public static final String MSG_ERROR_ISG_FIND_EXPEL_DEALER = "msg.error.isg.find_expel_dealer";

    /**
     * 接口报错-findForeignSponsors 小批量下载海外推荐人資料.
     */
    public static final String MSG_ERROR_ISG_FIND_FOREIGN_SPONSORS = "msg.error.isg.find_foreign_sponsors";

    /**
     * 接口报错-findGdealerChgOrgAppAuditList 获取其他市場转入本市場（目標市場）申请的待审批列表.
     */
    public static final String MSG_ERROR_ISG_FIND_GDEALER_CHG_ORG_APP_AUDIT_LIST = "msg.error.isg.find_gdealer_chg_org_app_audit_list";

    /**
     * 接口报错-findGdealerChgOrgAppList 获取本市場（源市場）转出申请的列表.
     */
    public static final String MSG_ERROR_ISG_FIND_GDEALER_CHG_ORG_APP_LIST = "msg.error.isg.find_gdealer_chg_org_app_list";

    /**
     * 接口报错-findMoveSponsor 下载移线会员资料.
     */
    public static final String MSG_ERROR_ISG_FIND_MOVE_SPONSOR = "msg.error.isg.find_move_sponsor";

    /**
     * 接口报错-orgList 获取新市场列表.
     */
    public static final String MSG_ERROR_ISG_ORG_LIST = "msg.error.isg.org_list";

    /**
     * 接口报错-findTransInList 下载转入会员清单.
     */
    public static final String MSG_ERROR_ISG_FIND_TRANS_IN_LIST = "msg.error.isg.find_trans_in_list";

    /**
     * 接口报错-findTransOutList 下载转出会员清单.
     */
    public static final String MSG_ERROR_ISG_FIND_TRANS_OUT_LIST = "msg.error.isg.find_trans_out_list";

    /**
     * 接口报错-queryCheckResult 日/月 差异结果查詢.
     */
    public static final String MSG_ERROR_ISG_QUERY_CHECK_RESULT = "msg.error.isg.query_check_result";

    /**
     * 接口报错-saveDealer 即時会员资料保存.
     */
    public static final String MSG_ERROR_ISG_SAVE_DEALER = "msg.error.isg.save_dealer";

    /**
     * 接口报错-saveGdealerChgOrgApp 保存转出本市場（源市場）的申请.
     */
    public static final String MSG_ERROR_ISG_SAVE_GDEALER_CHG_ORG_APP = "msg.error.isg.save_gdealer_chg_org_app";

    /**
     * 接口报错-saveGdealerChgOrgAppAudit 保存其他市場转入本市場（目標市場）申请的审核结果.
     */
    public static final String MSG_ERROR_ISG_SAVE_GDEALER_CHG_ORG_APP_AUDIT = "msg.error.isg.save_gdealer_chg_org_app_audit";

    /**
     * 接口报错-sponsorVerify 推荐人即时鉴别.
     */
    public static final String MSG_ERROR_ISG_SPONSOR_VERIFY = "msg.error.isg.sponsor_verify";

    /**
     * 接口报错-transIn 上传转入会员清单.
     */
    public static final String MSG_ERROR_ISG_TRANS_IN = "msg.error.isg.trans_in";

    /**
     * 接口报错-transOut 上传转出会员清单.
     */
    public static final String MSG_ERROR_ISG_TRANS_OUT = "msg.error.isg.trans_out";

    /**
     * 接口报错-treeAlterPrecess 移线/批删处理通知GDS.
     */
    public static final String MSG_ERROR_ISG_TREE_ALTER_PRECESS = "msg.error.isg.tree_alter_precess";

    /**
     * 接口报错-deleteDealer 删除会员资料.
     */
    public static final String MSG_ERROR_ISG_DELETE_DEALER = "msg.error.isg.delete_dealer";

    /**
     * 接口报错-删除转出本市场（源市场）的申请.
     */
    public static final String MSG_ERROR_ISG_DELETE_GDEALER_CHG_ORG_APP = "msg.error.isg.delete_gdealer_chg_org_app";

    /**
     * GDS系统orgCode在POS系统无映射.
     */
    public static final String MSG_ERROR_ORG_CODE_NO_MAPPING = "msg_error_gds_org_code_no_mapping";

    /**
     * GDS系统orgCode在POS系统映射的orgCode在市场维护不存在.
     */
    public static final String MSG_ERROR_ORG_CODE_MAPPING_NOT_EXIST_MARKET = "msg_error_org_code_mapping_not_exist_market";

    /**
     * 日期格式转换错误.
     */
    public static final String MSG_ERROR_ISG_DATE_FORMAT_ERROR = "msg.error.isg.date_format_error";

    /**
     * 推荐人无效.
     */
    public static final String MSG_ERROR_ISG_SOPONSOR_INVALID = "msg.error.isg.soponsor_invalid";

    /**
     * 会员卡号不存在.
     */
    public static final String MSG_ERROR_ISG_MEMBER_CODE_NOT_EXIST = "msg.error.isg.member_code_not_exist";

    /**
     * 申请时间格式化失败.
     */
    public static final String APPLY_DATE_FORMAT_ERROR = "msg.error.isg.apply.date.format";

    /**
     * 审批时间格式化失败.
     */
    public static final String AUDIT_DATE_FORMAT_ERROR = "msg.error.isg.audit.date.format";

    /**
     * 找不到对应的市场编码.
     */
    public static final String MARKET_CODE_NOT_FOUND = "msg.error.isg.market.not.found";

    /**
     * 找不到对应的会员.
     */
    public static final String MEMBER_NOT_EXISTS = "msg.error.isg.member.not.exists";

    /**
     * 找不到对应的审批状态编码.
     */
    public static final String AUDIT_CODE_NOT_FOUND = "msg.error.isg.audit.code.not.found";

    /**
     * 该会员不是有效已激活会员.
     */

    public static final String MEMBER_NOT_ACTIVE = "msg.error.isg.member.not.active";

    /**
     * GDS返回会员卡号为空.
     */
    public static final String GDS_MEMBER_CODE_NULL = "msg.error.isg.gds_member_code_null";

    /**
     * GDS返回会员卡号不匹配.
     */
    public static final String GDS_MEMBER_CODE_NOT_MATCH = "msg.error.isg.gds_member_code_not_match";

    /**
     * 当前销售区域ID为空.
     */
    public static final String MSG_ERROR_CURRENT_SALES_ORG_ID_NULL = "msg.error.isg.current_sales_org_id_null";

    /**
     * 当前销售区域所属市场为空.
     */
    public static final String MSG_ERROR_CURRENT_MARKET_CODE_NULL = "msg.error.isg.current_market_code_null";

    /**
     * 无效的推荐人.
     */
    public static final String MSG_ERROR_INVALID_SPONSOR = "msg.error.member.invalid_sponsor";
    
    /**
     * 不允许台湾VIP会员作为推荐人.
     */
    public static final String MSG_ERROR_INVALID_SPONSOR_TW_VIP = "msg.error.member.invalid_sponsor_for_tw_vip";

    /**
     * 删除会员失败.
     */
    public static final String MSG_ERROR_DELETE_MEMBER = "msg.error.integration.gds.delete_member_error";

    /**
     * 调用GDS失败.
     */
    public static final String GDS_INVOKE_FAILED = "msg.error.isg.gds.error";

    /**
     * APP NO 不匹配.
     */
    public static final String MSG_ERROR_APP_NO_NOT_MATCH = "msg.error.app_no_not_match";

    /**
     * 发送站内信失败.
     */
    public static final String SEND_MESSAGE_FAILED = "msg.error.isg.send_message_after_asyn.error";

    /**
     * 原市场不能为空.
     */
    public static final String MSG_ERROR_OLD_MARKET_NOT_NULL = "msg.error.integration.gds.old_market_is_not_null";

    /**
     * 时间格式转化错误.
     */
    public static final String MSG_ERROR_DATE_STR_FORMAT = "msg.error.integration.gds.date_string_format_error";

    /**
     * 返回会员类型为null.
     */
    public static final String MSG_ERROR_MEMBER_TYPE_NOT_NULL = "msg.error.integration.gds.certificate_type_is_null";

    /**
     * GDS关闭报错信息.
     */
    public static final String MSG_ERROR_GDS_IS_SHUTDOWN = "msg.error.integration.gds_is_shutdown";

    /**
     * 会员加入渠道不存在.
     */
    public static final String MSG_ERROR_MEMBER_JOINT_SITE_NOT_EXIST = "member joint site not exist";
    
    /**
     * 当前市场的销售组织不存在.
     */
    public static final String MSG_ERROR_SALES_ORG_NO_EXIST = "msg.error.integration.gds.sales_org_no_exist";

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
}
