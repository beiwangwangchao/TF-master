/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.constant;

import com.lkkhpg.dsis.platform.constant.BaseConstants;

/**
 * 接口常量类.
 * 
 * @author linyuheng
 */
public class IntegrationConstant extends BaseConstants {
    /**
     * ISG接口appNo流水号 - 类型.
     */
    public static final String SEQ_TYPE = "GDS_APP_NO";
    /**
     * ISG接口appNo流水号 - 长度.
     */
    public static final int SEQ_LENGTH = 10;
    /**
     * ISG接口appNo流水号 - 初始值.
     */
    public static final Long SEQ_INIT = 1L;

    /**
     * ISG接口OrgCode映射.
     */
    public static final String GDS_ORG_CODE_MAPPING = "ISG.GDS_ORG_CODE_MAPPING";

    /**
     * 会员角色 - 分销商.
     */
    public static final String MEMBER_ROLE_DISTRIBUTOR = "DIS";

    /**
     * 会员角色 - 运维.
     */
    public static final String MEMBER_ROLE_SUPPORT = "SUP";

    /**
     * 身份类型 - 优惠卡顾客.
     */
    public static final String DEALERTYPE_SUPPORT = "1";

    /**
     * 身份类型 - 直销员.
     */
    public static final String DEALERTYPE_DISTRIBUTOR = "2";

    /**
     * 身份子类型 - 准经销商.
     */
    public static final String DEALERSUBTYPE_PRODEALER = "3";

    /**
     * 身份子类型 - 普通业务员.
     */
    public static final String DEALERSUBTYPE_SALE = "1";

    /**
     * 会员类型 - 个人.
     */
    public static final String INDIVIDUAL = "IDV";

    /**
     * 会员类型 - 公司.
     */
    public static final String COMPANY = "COMP";

    /**
     * 会员类型 - 公司.
     */
    public static final String CHINESE = "CHN";

    /**
     * 会员类型 - 公司.
     */
    public static final String FOREIGN = "FORE";

    /**
     * 证件类型 - 个人.
     */
    public static final String CERTIFICATE_TYPE_INDIVIDUAL = "61";

    /**
     * 证件类型 - 公司.
     */
    public static final String CERTIFICATE_TYPE_COMPANY = "62";

    /**
     * 申请类型 - 0.
     */
    public static final String APP_TYPE = "0";

    /**
     * 香港市场.
     */
    public static final Long MARKET_ID_HK = 1L;

    /**
     * 大陆市场.
     */
    public static final Long MARKET_ID_HH = 2L;

    /**
     * 台湾市场.
     */
    public static final Long MARKET_ID_TW = 3L;

    /**
     * 马来西亚市场.
     */
    public static final Long MARKET_ID_ML = 4L;

    /**
     * 中国.
     */
    public static final String COUNTRY_CN = "CN";

    /**
     * 中国香港.
     */
    public static final String COUNTRY_HK = "HK";

    /**
     * 中国台湾.
     */
    public static final String COUNTRY_TW = "TW";

    /**
     * 马来西亚.
     */
    public static final String COUNTRY_ML = "MY";

    /**
     * 处理状态，P：待定.
     */
    public static final String PROCESS_STATUS_P = "P";
    /**
     * 处理状态，W：警告.
     */
    public static final String PROCESS_STATUS_W = "W";
    /**
     * 处理状态 E：失败.
     */
    public static final String PROCESS_STATUS_E = "E";

    /**
     * 处理状态 S：成功.
     */
    public static final String PROCESS_STATUS_S = "S";

    /**
     * 获取待审批列表-操作类型-转入.
     */
    public static final String ISG_MARKET_CHANGE_OPERATION_TYPE_IN = "IN";

    /**
     * 获取待审批列表-失败.
     */
    public static final String ISG_MARKET_CHANGE_PROCESS_ERROR = "E";

    /**
     * 获取待审批列表-成功.
     */
    public static final String ISG_MARKET_CHANGE_PROCESS_SUCCESS = "S";

    /**
     * 接口回传状态 - 成功.
     */
    public static final String INT_SUCCESS = "S";

    /**
     * 接口回传状态 - 失败.
     */
    public static final String INT_ERROR = "E";

    /**
     * 接口回传状态 - 警告.
     */
    public static final String INT_WARNING = "W";

    /**
     * 移线状态 - 已处理.
     */
    public static final String TRANSTATUS_S = "S";

    /**
     * 移线状态 - 处理失败.
     */
    public static final String TRANSTATUS_F = "F";

    /**
     * 申请类型 - 移线状态查询.
     */
    public static final String APPLY_TYPE_UPSTREAMCHANGE = "UpstreamChange";

    /**
     * 申请类型 - 停权状态查询.
     */
    public static final String APPLY_TYPE_STATUSCHANGE = "StatusChange";

    /**
     * 操作类型 - 即时保存.
     */
    public static final String OPERATION_TYPE_SAVE = "SAVE";

    /**
     * 操作类型 - 小批量同步.
     */
    public static final String OPERATION_TYPE_SYNC = "SYNC";

    /**
     * 计税客户代号 - 中国全体用户.
     */
    public static final String TAXCUXCODE_KCN1 = "KCN1";

    /**
     * 计税客户代号 - 香港个人户.
     */
    public static final String TAXCUXCODE_KHK1 = "KHK1";

    /**
     * 计税客户代号 - 香港公司户.
     */
    public static final String TAXCUXCODE_KHK2 = "KHK2";

    /**
     * 计税客户代号 - 香港外国人.
     */
    public static final String TAXCUXCODE_KHK3 = "KHK3";

    /**
     * 计税客户代号 - 香港大陆人.
     */
    public static final String TAXCUXCODE_KHK4 = "KHK4";

    /**
     * 计税客户代号 - 马来西亚个人户.
     */
    public static final String TAXCUXCODE_KMY1 = "KMY1";

    /**
     * 计税客户代号 - 马来西亚公司户.
     */
    public static final String TAXCUXCODE_KMY2 = "KMY2";

    /**
     * 计税客户代号 - 马来西亚外国人.
     */
    public static final String TAXCUXCODE_KMY3 = "KMY3";

    /**
     * 计税客户代号 - 台湾个人户.
     */
    public static final String TAXCUXCODE_KTW1 = "KTW1";

    /**
     * 计税客户代号 - 台湾公司户.
     */
    public static final String TAXCUXCODE_KTW2 = "KTW2";

    /**
     * 计税客户代号 - 台湾外国人.
     */
    public static final String TAXCUXCODE_KTW3 = "KTW3";

    /**
     * 计税客户代号 - 台湾大陆人.
     */
    public static final String TAXCUXCODE_KTW4 = "KTW4";

    /**
     * 接口调用成功 - message.
     */
    public static final String PROCESS_MESSAGE_SUCCESS = "Success to call the interface.";

    /**
     * 接口调用失败 - message.
     */
    public static final String PROCESS_MESSAGE_FAIL = "Fail to call the interface.";

    /**
     * Flag取值(Y/N) - Y.
     */
    public static final String FLAG_Y = "Y";

    /**
     * Flag取值(Y/N) - N.
     */
    public static final String FLAG_N = "N";

    /**
     * 关闭月份 - period_type取值:BM.
     */
    public static final String CLOSE_PERIOD_TYPE_BM = "BM";

    /**
     * 转入转出会员标志 - 转出.
     */
    public static final String OUT_MARKET_CHANGE = "OUT";

    /**
     * 转入转出会员标志 - 转入.
     */
    public static final String IN_MARKET_CHANGE = "IN";

    /**
     * 处理状态 - 待定.
     */
    public static final String PARK_STATUS = "P";

    /**
     * 上传状态 - 否.
     */
    public static final String DOWNLOAD_STATUS = "N";

    /**
     * 处理状态 - 待定.
     */
    public static final String PENT_STATUS = "P";

    /**
     * 处理状态 - 失败.
     */
    public static final String ERROR_STATUS = "E";

    /**
     * 处理状态 - 成功.
     */
    public static final String SUCCESS_STATUS = "S";

    /**
     * 上传状态 - 是.
     */
    public static final String UPLOAD_STATUS = "Y";

    /**
     * 下载上传会员清单 - 变更市场异常.
     */
    public static final String UPDATE_ERROR_MESSAGE = "msg.error.update_change_market_error";

    /**
     * 销售订单类型-常规.
     */
    public static final String SO_TYPE_NORMAL = "01";

    /**
     * 销售订单录入种类-POS.
     */
    public static final String SO_ENTRY_P = "P";

    /**
     * 销售订单通用日期格式.
     */
    public static final String SO_COMMON_TIME_FORMAT = "yyyy-MM-dd";

    /**
     * 需要校验是否发送过的订单状态.
     */
    public static final String SO_CHECK_STATUS = "VOID";

    /**
     * 变更市场审核状态 - 通过.
     */
    public static final String MARKET_CHANGE_PASS = "2";

    /**
     * 变更市场审核状态 - 不通过.
     */
    public static final String MARKET_CHANGE_NO_PASS = "9";

    /**
     * 变更市场审核状态 - 待审批.
     */
    public static final String MARKET_CHANGE_NEW = "待审批";

    /**
     * 下载资料处理模式 - 停权.
     */
    public static final String TRANMODE_DEL = "*DEL";

    /**
     * 下载资料处理模式 - 移线.
     */
    public static final String TRANMODE_MOV = "*MOV";

    /**
     * 变更记录的审核状态 - 审核通过.
     */
    public static final String APPROVAL_STATUS_S = "S";

    /**
     * 变更记录的审核状态 - 审核未通过.
     */
    public static final String APPROVAL_STATUS_F = "F";

    /**
     * 变更记录的审核状态 - 审核中.
     */
    public static final String APPROVAL_STATUS_P = "P";

    /**
     * 零值.
     */
    public static final String ZERO = "0";

    /**
     * 证件类型-个人.
     */
    public static final String CERTIFICATE_TYPE_INDV = "61";

    /**
     * 证件类型-公司.
     */
    public static final String CERTIFICATE_TYPE_COMP = "62";

    /**
     * 性别-男.
     */
    public static final String SEX_M = "M";

    /**
     * 性别-女.
     */
    public static final String SEX_F = "F";

    /**
     * 未审核.
     */
    public static final String NOT_APPROVAL = "未审核";

    /**
     * 销售订单类型-失效.
     */
    public static final String SO_TYPE_VOID = "12";

    /**
     * 快码 - 角色.
     */
    public static final String MM_MEMBER_ROLE = "MM.MEMBER_ROLE";

    /**
     * 快码 - 性别.
     */
    public static final String MM_MEMBER_GENDER = "MM.MEMBER_GENDER";

    /**
     * 快码 - 会员类型.
     */
    public static final String MM_MEMBER_TYPE = "MM.MEMBER_TYPE";

    /**
     * 快码 - 种族.
     */
    public static final String MM_MEMBER_RACE = "MM.MEMBER_RACE";

    /**
     * 快码 - 学历.
     */
    public static final String MM_MEMBER_EDUCATION = "MM.MEMBER_EDUCATION";

    /**
     * 快码 - 计税客户代号.
     */
    public static final String ISG_GDS_TAX_CUST_CODE = "ISG.GDS_TAX_CUST_CODE";

    /**
     * callback类型 - 新增.
     */
    public static final String ACTION_TYPE_ADD = "NEW";

    /**
     * callback类型 - 更新.
     */
    public static final String ACTION_TYPE_UPDATE = "UPDATE";

    /**
     * 销售区域代号最大长度.
     */
    public static final int SALE_BRANCH_NO_MAX_LENGTH = 5;

    /**
     * 销售区域代号截取长度.
     */
    public static final int BRANCH_NO_SUB_NUMBER = 4;

    /**
     * 地址最大长度.
     */
    public static final int ADDRESS_MAX_LENGTH = 50;

    /**
     * 差异实体类型 - 会员.
     */
    public static final String CHECK_ENTITY_TYPE_DLR = "*DLR";

    /**
     * 差异实体类型 - 销售单.
     */
    public static final String CHECK_ENTITY_TYPE_SO = "*SO";

    /**
     * 差异实体类型 - 产品.
     */
    public static final String CHECK_ENTITY_TYPE_PRD = "*PRD";

    /**
     * 差异实体类型 - 上传文件.
     */
    public static final String CHECK_ENTITY_TYPE_FIL = "*FIL";
}
