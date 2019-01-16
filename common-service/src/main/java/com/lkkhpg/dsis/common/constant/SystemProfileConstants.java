/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.constant;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.constant.BaseConstants;

/**
 * 系统配置常量.
 * 
 * @author chenjingxiong
 */
public class SystemProfileConstants extends BaseConstants {

    /**
     * 奖金月份状态-打开.
     */
    public static final String PEROID_CLOSE_STATUS_N = "N";

    /**
     * 供货关系类型 - 组织.
     */
    public static final String SUPPLY_TYPE_ORG = "ORG";

    /**
     * 供货关系类型 - 地点.
     */
    public static final String SUPPLY_TYPE_SITE = "SITE";

    /**
     * 组织参数 - type - market.
     */
    public static final Long MARKET = 10002L;

    /**
     * 组织参数 - type - ou.
     */
    public static final Long OU = 11001L;

    /**
     * 组织参数 - type - sales_org.
     */
    public static final Long SALES_ORG = 10003L;

    /**
     * 组织参数 - type - inv_org.
     */
    public static final Long INV_ORG = 11002L;

    /**
     * 组织参数 - type - country.
     */
    public static final Long COUNTRY = 10004L;

    /**
     * 组织参数 - type - status.
     */
    public static final Long STATE = 10005L;

    /**
     * 组织参数 - type - city.
     */
    public static final Long CITY = 10006L;

    /**
     * 组织参数 - 组织类型 - SALES.
     */
    public static final String ORG_TYPE_SALES = "SALES";

    /**
     * 组织参数 - 组织类型 - INV.
     */
    public static final String ORG_TYPE_INV = "INV";

    /**
     * 组织参数 - 组织类型 - MARKET.
     */
    public static final String ORG_TYPE_MARKET = "MKT";

    /**
     * 组织参数 - 组织类型 - OU.
     */
    public static final String ORG_TYPE_OU = "OU";

    /**
     * 默认销售组织id.
     */
    public static final String DEFAULT_SALES_ORG_ID = "_defaultSalesOrgId";

    /**
     * 默认库存组织id.
     */
    public static final String DEFAULT_INV_ORG_ID = "_defaultInvOrgId";

    /**
     * 销售组织id集合.
     */
    public static final String SALES_ORG_IDS = "_salesOrgIds";

    /**
     * 库存组织id集合.
     */
    public static final String INV_ORG_IDS = "_invOrgIds";

    /**
     * 销售组织id.
     */
    public static final String SALES_ORG_ID = "_salesOrgId";

    /**
     * 销售组织name.
     */
    public static final String SHOP_NAME = "_salesOrgName";

    /**
     * 库存组织id.
     */
    public static final String INV_ORG_ID = "_invOrgId";

    /**
     * 单位维护-单位代码长度限制.
     */
    public static final int INV_UNIT_CODE_LENGTH = 5;

    /**
     * 市场id.
     */
    public static final String MARKET_ID = "_marketId";

    /**
     * 市场code.
     */
    public static final String MARKET_CODE = "_marketCode";
    /**
     * 奖金期间-关闭状态-关闭.
     */
    public static final String CLOSING_STATUS_Y = "Y";
    /**
     * 奖金期间-关闭状态-进行中.
     */
    public static final String CLOSING_STATUS_N = "N";
    /**
     * 奖金期间-奖金类型.
     */
    public static final String PERIOD_TYPE = "BM";
    /**
     * 奖金期间-组织类型.
     */
    public static final String ORG_TYPE = "MKT";
    /**
     * 奖金期间-日期格式.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 奖金期间-月份格式.
     */
    public static final String DATE_MONTH = "0";
    
    /**
     * 奖金期间-期间名称格式.
     */
    public static final String PERIOD_NAME_FORMAT = "yyyyMM";
    
    


    /**
     * 市场本位币.
     */
    public static final String SPM_CURRENCY = "SPM.CURRENCY";

    /**
     * 组织参数名称-会员卡号前缀.
     */
    public static final String PARAM_MEMBER_CODE_PREFIX = "SPM.MEMBER_ID_PREFIX";

    /**
     * 组织参数名称-种族.
     */
    public static final String PARAM_MEMBER_RACE = "SPM.MEMBER_RACE";

    /**
     * 组织参数名称-会员年龄预警.
     */
    public static final String PARAM_MEMBER_AGE_ALERT = "SPM.MEMBER_AGE_ALERT";

    /**
     * 组织参数名称-启用会员姓编辑.
     */
    public static final String PARAM_MEMBER_LAST_NAME_EDITABLE = "SPM.SPM_EDITABLE";

    /**
     * 组织参数名称-支行.
     */
    public static final String PARAM_MEMBER_BANK_BRANCH = "SPM.MEMBER_BANK_BRANCH";

    /**
     * 组织参数名称-家庭住址.
     */
    public static final String PARAM_MEMBER_HOME_ADDRESS = "SPM.MEMBER_HOME_ADDRESS";

    /**
     * 组织参数名称-公民类型.
     */
    public static final String PARAM_MEMBER_HOME_CITIZEN_TYPE = "SPM.MEMBER_HOME_CITIZEN_TYPE";

    /**
     * 组织参数名称-健税保外.
     */
    public static final String PARAM_MEMBER_NHI_TAX_EXCLUDED = "SPM.MEMBER_NHI_TAX_EXCLUDED";

    /**
     * 组织参数-税码.
     */
    public static final String SPM_TAX_CODE = "SPM.TAX_CODE";

    /**
     * 组织参数-是否启用税.
     */
    public static final String SPM_ENABLE_TAX = "SPM.ENABLE_TAX";

    /**
     * 组织参数-商品价格是否含税.
     */
    public static final String SPM_PRICE_INCLUDE_TAX = "SPM.PRICE_INCLUDE_TAX";

    /**
     * 组织参数名称-是否可作推荐人.
     */
    public static final String SPM_MEMBER_VIP_SPONSOR = "SPM.MEMBER_VIP_SPONSOR";

    /**
     * 税率百分比-100.
     */
    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    /**
     * 组织参数名称-GST ID.
     */
    public static final String PARAM_MEMBER_GST_ID = "SPM.MEMBER_GST_ID";

    /**
     * 组织参数名称-申请清单.
     */
    public static final String PARAM_MEMBER_APPLICITION_LIST = "SPM.MEMBER_APPLICITION_LIST";
    /**
     * 奖金期间-月份小于10加0.
     */
    public static final Long MONTH_FORMAT = 10L;
    /**
     * 奖金期间-日期格式错误.
     */
    public static final String DATE_FORMAT_ERROR = "日期格式错误";

    /**
     * 组织参数名称-RB支付规则.
     */
    public static final String PARAM_PAY_BY_RB = "SPM.PAY_BY_RB";

    /**
     * 组织参数名称-Autoship税后最小金额.
     */
    public static final String PARAM_AUTOSHIP_MIN_SUM = "SPM.AUTOSHIP_MIN_SUM";

    /**
     * 组织参数名称-新建会员自动激活.
     */
    public static final String PARAM_MEMBER_AUTO_APPROVED = "SPM.MEMBER_AUTO_APPROVED";

    /**
     * 审核状态 － 审核中.
     */
    public static final String APPROVE_STATUS_ALING = "ALING";

    /**
     * 审核状态 － 关闭.
     */
    public static final String APPROVE_STATUS_CLOSD = "CLOSD";

    /**
     * 事件编码长度 - 5.
     */
    public static final int CODE_LENGTH_FIVE = 5;

    /**
     * 活动分配类型 - 会员.
     */
    public static final String EVENT_MEMBER_TYPE = "MEM";

    /**
     * 活动状态 - 无效.
     */
    public static final String EVENT_STATUS_VOID = "VOID";

    /**
     * 活动状态 - 已发布.
     */
    public static final String EVENT_STATUS_PUB = "PUB";

    /**
     * 活动状态 - 有效.
     */
    public static final String EVENT_STATUS_ACTV = "ACTV";

    /**
     * 活动保密性 - 公开.
     */
    public static final String EVENT_SECRET_PUB = "PUB";

    /**
     * 组织参数名称-奖金发放基准.
     */
    public static final String BONUS_RELEASE_THRESHOLD = "SPM.BONUS_RELEASE_THRESHOLD";

    public static final String ADMIN_VERSION = "adminVersion";

    /**
     * 最小pv值.
     */
    public static final String AUTOSHIP_MIN_PV = "SPM.AUTOSHIP_MIN_PV";

    /**
     * BigDecimal - 精确长度.
     */
    public static final int BIGDECIMAL_SCALE = 2;

    /**
     * 组织参数名称 - 会员变更市场审批人.
     */
    public static final String PARAM_CHANGE_MARKET_AUDITER = "SPM.CHANGE_MARKET_AUDITER";

    /**
     * 组织参数名称 - 允许backorder.
     */
    public static final String PARAM_ALLOW_BACK_ORDER = "SPM.ALLOW_BACK_ORDER";

    /**
     * 数字-12.
     */
    public static final Long TWELVE = 12L;

    /**
     * 数字-1.
     */
    public static final Long ONE = 1L;

    /**
     * 组织参数名称 - 修改奖金日期.
     */
    public static final String PARAM_BONUS_MONTH_DATE = "SPM.BONUS_MONTH_DATE";

    /**
     * 报表参数类型 - text.
     */
    public static final String REPORT_PARAMS_FILE_TYPE_TEXT = "text";

    /**
     * 报表参数类型 - int.
     */
    public static final String REPORT_PARAMS_FILE_TYPE_INT = "int";

    /**
     * 报表参数类型 - select.
     */
    public static final String REPORT_PARAMS_FILE_TYPE_SELECT = "select";

    /**
     * 报表参数类型 - popup.
     */
    public static final String REPORT_PARAMS_FILE_TYPE_POPUP = "popup";

    /**
     * 报表参数select类型 - url.
     */
    public static final String REPORT_PARAMS_FILE_TYPE_SELECT_URL = "url";

    /**
     * 报表参数select类型 - code.
     */
    public static final String REPORT_PARAMS_FILE_TYPE_SELECT_CODE = "code";
}
