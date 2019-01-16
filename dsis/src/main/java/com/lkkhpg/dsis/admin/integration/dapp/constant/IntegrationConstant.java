/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.constant;

import com.lkkhpg.dsis.platform.constant.BaseConstants;

/**
 * D-APP接口常量类.
 * 
 * @author frank.li
 */
public class IntegrationConstant extends BaseConstants {

    public static final String VERSION = "v1";

    /**
     * 描述代码-会员卡号不存在.
     */
    public static final int ERROR_20001 = 20001;

    /**
     * 描述代码-中英文姓名至少填一个.
     */
    public static final int ERROR_20002 = 20002;

    /**
     * 描述代码-distributorNumber或idNumber至少填一个.
     */
    public static final int ERROR_20003 = 20003;

    /**
     * 描述代码-姓名总长度超过48位.
     */
    public static final int ERROR_20004 = 20004;

    /**
     * 描述代码-银行不存在.
     */
    public static final int ERROR_20005 = 20005;

    /**
     * 描述代码-公司不存在.
     */
    public static final int ERROR_20006 = 20006;

    /**
     * 描述代码-姓和名没有同时给出.
     */
    public static final int ERROR_20007 = 20007;

    /**
     * 描述代码-相关人生日不能为空.
     */
    public static final int ERROR_20008 = 20008;

    /**
     * 描述代码-相关人id type不能为空.
     */
    public static final int ERROR_20009 = 20009;

    /**
     * 描述代码-相关人id不能为空.
     */
    public static final int ERROR_20010 = 20010;

    /**
     * 描述代码-相关人描述不能为空.
     */
    public static final int ERROR_20011 = 20011;

    /**
     * 描述代码-性别不能为空.
     */
    public static final int ERROR_20012 = 20012;

    /**
     * 描述代码-持有人不能为空.
     */
    public static final int ERROR_20013 = 20013;

    /**
     * 描述代码-账户ID NUMBER不能为空.
     */
    public static final int ERROR_20014 = 20014;

    /**
     * 描述代码-银行代号不能为空.
     */
    public static final int ERROR_20015 = 20015;

    /**
     * 描述代码-持有人不能为空.
     */
    public static final int ERROR_20016 = 20016;

    /**
     * 描述代码-appNo已经存在.
     */
    public static final int ERROR_20017 = 20017;

    /**
     * 描述代码-国家代码不存在.
     */
    public static final int ERROR_20018 = 20018;

    /**
     * 描述代码-州省代码不存在.
     */
    public static final int ERROR_20019 = 20019;

    /**
     * 描述代码-城市代码不存在.
     */
    public static final int ERROR_20020 = 20020;

    /**
     * 描述代码-销售组织不存在.
     */
    public static final int ERROR_20021 = 20021;

    /**
     * 描述代码-市场不存在.
     */
    public static final int ERROR_20022 = 20022;

    /**
     * 描述代码-分行不存在.
     */
    public static final int ERROR_20023 = 20023;

    /**
     * 描述代码-订单通知号已存在.
     */
    public static final int ERROR_30001 = 30001;

    /**
     * 描述代码-Call Order API failed.
     */
    public static final int ERROR_30002 = 30002;

    /**
     * 描述代码-订单编号不存在.
     */
    public static final int ERROR_30003 = 30003;

    /**
     * 描述代码-订单支付入库失败.
     */
    public static final int ERROR_30004 = 30004;

    /**
     * 描述代码-订单的销售渠道非D-APP.
     */
    public static final int ERROR_30005 = 30005;

    /**
     * 描述代码-订单状态不为PAYIN或者COMP或者VOID或者WPAY或者CANCL.
     */
    public static final int ERROR_30006 = 30006;

    /**
     * 描述代码-订单作废失败.
     */
    public static final int ERROR_30007 = 30007;

    /**
     * 描述代码-订单状态错误.
     */
    public static final int ERROR_30008 = 30008;

    /**
     * 描述代码-订单同步失败.
     */
    public static final int ERROR_30009 = 30009;

    /**
     * 描述代码-POS系统 pv不存在.
     */
    public static final int ERROR_30010 = 30010;

    /**
     * 描述代码-库存量不足.
     */
    public static final int ERROR_30011 = 30011;

    /**
     * 订单状态不存在.
     */
    public static final int ERROR_30012 = 30012;

    /**
     * 描述代码-订单的发运数量>0，不允许失效.
     */
    public static final int ERROR_30013 = 30013;

    /**
     * 订单类型错误.
     */
    public static final int ERROR_30014 = 30014;

    /**
     * 支付更新 - 订单状态为完成.
     */
    public static final int ERROR_30015 = 30015;

    /**
     * 支付更新 - ECoupons不足.
     */
    public static final int ERROR_30016 = 30016;

    /**
     * 支付更新 - 冻结支付信息失败.
     */
    public static final int ERROR_30017 = 30017;

    /**
     * 描述代码-商品编号不存在.
     */
    public static final int ERROR_40001 = 40001;

    /**
     * 描述代码-根据省州代码找不到所属国家代码.
     */
    public static final int ERROR_60001 = 60001;

    /**
     * 描述代码-save location error.
     */
    public static final int ERROR_60002 = 60002;

    /**
     * 描述代码-location message not found.
     */
    public static final int ERROR_60003 = 60003;

    /**
     * 描述代码-market code not existed.
     */
    public static final int ERROR_60004 = 60004;

    /**
     * 描述代码-salesOrganization code not existed.
     */
    public static final int ERROR_60005 = 60005;

    /**
     * 描述代码-invalid date format.
     */
    public static final int ERROR_60006 = 60006;

    /**
     * 返回状态代码-20001:会员相关错误.
     */
    public static final int STATUS_CODE_MEMBER = 20001;

    /**
     * 返回状态代码-20002:订单相关错误.
     */
    public static final int STATUS_CODE_ORDER = 20002;

    /**
     * 返回状态代码-20003:商品相关错误.
     */
    public static final int STATUS_CODE_PRODUCT = 20003;

    /**
     * 返回状态代码-20004:优惠券相关错误.
     */
    public static final int STATUS_CODE_VOUCHER = 20004;

    /**
     * 返回状态代码-20005:系统设置相关错误.
     */
    public static final int STATUS_CODE_SYSTEM = 20005;

    /**
     * 描述代码-常量4.
     */
    public static final int FOUR = 4;

    /**
     * D-App接口OrgCode映射.
     */
    public static final String GDS_ORG_CODE_MAPPING = "ISG.GDS_ORG_CODE_MAPPING";

    /**
     * D-App接口GDS至POS系统语言映射.
     */
    public static final String GDS_LANG_MAPPING = "ISG.GDS_LANG_MAPPING";

    /**
     * 优惠券状态-有效.
     */
    public static final String VOUCHER_STATUS_ACV = "ACV";

    /**
     * 必输字段缺失.
     */
    public static final String ISG_ERROR_REQUIRED_MISSING = "Required Data is Missing";

    /**
     * 申请表.
     */
    public static final String APPLICATION_FORM = "application_form";

    /**
     * 银行账户复印件.
     */
    public static final String BANCK_ACCOUNT_COPY = "banck_account_copy";

    /**
     * 保密协议.
     */
    public static final String CONFIDENTIALITY_AGREEMENT = "confidentiality_agreement";

    /**
     * ID复印件.
     */
    public static final String ID_COPY = "id_copy";

    /**
     * 零值.
     */
    public static final String ZERO = "0";

    /**
     * 家庭住址.
     */
    public static final String SITE_HOME = "HOME";

    /**
     * 联系地址.
     */
    public static final String SITE_CTACT = "CTACT";

    /**
     * 收货地址.
     */
    public static final String SITE_SHIP = "SHIP";

    /**
     * 账单地址.
     */
    public static final String SITE_BILL = "BILL";

    /**
     * 关系人类型 - 配偶.
     */
    public static final String REL_TYPE_SPOUS = "SPOUS";

    /**
     * 关系人类型 - 受益人.
     */
    public static final String REL_TYPE_BENF = "BENF";

    /**
     * 日期格式yyyy/MM/dd.
     */
    public static final String DATE_FORMATTER = "yyyy/MM/dd";

    /**
     * 订单提交-来源类型.
     */
    public static final String ORDER_SUBMIT_SOURCE_TYPE = "MANUL";

    /**
     * 订单提交-备注.
     */
    public static final String ORDER_SUBMIT_REMARK = "from DApp Data";

    /**
     * 订单提交-是否货到付款.
     */
    public static final String ORDER_SUBMIT_COD_FLAG = "N";

    /**
     * 订单提交-销售类型.
     */
    public static final String ORDER_SUBMIT_ITEM_SALES_TYPE = "PURC";

    /**
     * 订单提交-发运类型-物流配送.
     */
    public static final String ORDER_SUBMIT_DELIVERY_TYPE = "SHIPP";
    
    /**
     * 订单提交-发运类型-自提.
     */
    public static final String ORDER_SUBMIT_DELIVERY_TYPE_PCKUP = "PCKUP";

    /**
     * 订单提交-订单类型 - STDP.
     */
    public static final String ORDER_SUBMIT_ORDER_TYPE = "STDP";

    /**
     * 订单提交-订单类型 - VIP.
     */
    public static final String ORDER_SUBMIT_ORDER_TYPE_VIP = "VIPP";

    /**
     * 订单提交-同步标识.
     */
    public static final String ORDER_SUBMIT_ASYC_FLAG = "Y";

    /**
     * 会员卡号不存在.
     */
    public static final String MEMBER_CODE_NOT_EXISTED = "member code not existed";

    /**
     * 订单通知号已存在.
     */
    public static final String APP_NO_EXISTED = "appNo has existed";

    /**
     * Call Order API failed.
     */
    public static final String CALL_ORDER_API_FAILED = "Call Order API failed.";

    /**
     * 订单编号不存在.
     */
    public static final String ORDER_NUMBER_NOT_EXISTED = " order number not existed";

    /**
     * 商品编号不存在.
     */
    public static final String PRODUCT_CODE_NOT_EXISTED = "product code not existed";

    /**
     * 根据省州代码找不到所属国家代码.
     */
    public static final String UNABLED_TO_GET_COUNTRY_BY_STATE = "unable to get country code by state code";

    /**
     * save location error.
     */
    public static final String SAVE_LOCATION_ERROR = "save location error.";

    /**
     * market code not existed.
     */
    public static final String MARKET_CODE_NOT_EXISTED = "market code not existed.";

    /**
     * salesOrganization code not existed.
     */
    public static final String SALESORGANIZATION_CODE_NOT_EXISTED = "salesOrganization code not existed";

    /**
     * invalid date format.
     */
    public static final String INVALID_DATE_FORMAT = "invalid date format";

    /**
     * 订单支付入库失败.
     */
    public static final String ORDER_PAYMENT_SAVE_ERROR = "order payment save error";

    /**
     * 订单销售渠道非D-APP.
     */
    public static final String ORDER_SOURCE_TYPE_ERROR = "Order source type not D-APP";

    /**
     * 订单状态不为PAYIN或者COMP或者FAIL.
     */
    public static final String ORDER_STATUS_ERROR = "The order status not in 'PAYIN' or 'COMP' or 'FAIL'";

    /**
     * location message not found.
     */
    public static final String LOCATION_MESSAGE_NOT_FOUND = "location message not found";

    /**
     * 数据操作类型-I 新增.
     */
    public static final String OPER_TYPE_I = "NEW";

    /**
     * 数据操作类型-U 更新.
     */
    public static final String OPER_TYPE_U = "UPDATE";

    /**
     * 数据操作类型-D 删除.
     */
    public static final String OPER_TYPE_D = "D";

    /**
     * 默认加入渠道 -1.
     */
    public static final String DEFAULT_JOIN_SITE = "-1";

    /**
     * call back类型 - 会员.
     */
    public static final String CALLBACK_TYPE_DISTRIBUTOR = "distributor";

    /**
     * call back方式 - add.
     */
    public static final String CALLBACK_STATE_ADD = "A";

    /**
     * call back方式 - update.
     */
    public static final String CALLBACK_STATE_UPDATE = "U";

    /**
     * call back方式 - delete.
     */
    public static final String CALLBACK_STATE_DELETE = "D";

    /**
     * 订单提交 - 销售渠道.
     */
    public static final String ORDER_CHANNEL = "DISAP";

    /**
     * 配偶快码映射.
     */
    public static final String MEMBER_SPOUSE_REL_CODE = "MM.MEMBER_SPOUSE_REL";

    /**
     * 各市场默认销售区域快码映射.
     */
    public static final String DAPP_DEFAULT_JOINT_SITE = "ISG.DAPP_DEFAULT_JOINT_SITE";

    /**
     * 会员默认国家快码映射.
     */
    public static final String MM_DEFAULT_COUNTRY = "MM.MEMBER_DEFAULT_COUNTRY";

    /**
     * 旅行计划月快码映射.
     */
    public static final String MM_TRAVEL_PLAN_MONTH = "MM.TRAVEL_PLAN_MONTH";

    /**
     * 订单提交 - 自动发运 - false.
     */
    public static final String ORDER_AUTO_FLAG = "N";

    /**
     * 页数.
     */
    public static final String PAGE_NO = "1";

    /**
     * 每页最大记录数.
     */
    public static final String MAX_PERPAGE = "100";

    /**
     * 香港市场.
     */
    public static final String MARKET_CODE_HK = "HK";

    /**
     * 台湾市场 .
     */
    public static final String MARKET_CODE_TW = "TW";

    /**
     * 马来西亚市场.
     */
    public static final String MARKET_CODE_MY = "MY";

    /**
     * 铜锣湾实体店销售组织.
     */
    public static final String SALE_ORG_HK = "10201";

    /**
     * 复兴南路销售组织.
     */
    public static final String SALE_ORG_TW = "10301";

    /**
     * 西马店销售组织.
     */
    public static final String SALE_ORG_MY = "10326";

    /**
     * POS系统 pv不存在.
     */
    public static final String PV_NOT_EXIST = "POS pv not exists";

    /**
     * 库存量不足.
     */
    public static final String PRODUCT_QUANTITY_NOT_ENOUGH = " the inventory is not enough for the supply";

    /**
     * 会员不是VIP会员.
     */
    public static final String MEMBER_NOT_VIP = "invalid VIP member error";

    /**
     * 订单状态不存在.
     */
    public static final String ORDER_STATUS_NOT_EXIST = " POS order status not exists";

    /**
     * 订单状态快码名称.
     */
    public static final String OM_ORDER_STATUS = "OM.SALES_STATUS";

    /**
     * 订单类型错误.
     */
    public static final String ORDER_TYPE_ERROR = "order type error";

    /**
     * 支付更新 - 订单状态为完成.
     */
    public static final String ORDER_STATUS_COMP = "the order has completed , not allow to change to PAYIN or FAIL";

    /**
     * 支付更新 - Ecoupons不足.
     */
    public static final String ECOUPONS_NOT_ENOUGH = "Ecoupon not enough for member ";

    /**
     * 支付更新 - 冻结支付失败.
     */
    public static final String ERROR_LOCK_ORDER_PAYMENT = "Error to lock orderPayment ";

    /**
     * 登录账户名 - dapp.
     */
    public static final String DAPP_ACCOUNT_NAME = "distributorapp";

    /**
     * callback job 类型 - 会员.
     */
    public static final String CALLBACK_JOB_TYPE_MEMBER = "member";

    /**
     * callback job 类型 - 订单.
     */
    public static final String CALLBACK_JOB_TYPE_ORDER = "order";

    /**
     * 回调失败报错信息.
     */
    public static final String FAILED_TO_CALLBACK = "distributor callback failed -> code:{},message:{}";

    /**
     * 默认加入渠道类型.
     */
    public static final String DEFAULT_JOINT_SITE_TYPE = "DISAP";

    /**
     * 默认奖金接收方式.
     */
    public static final String DEFAULT_BONUS_RCV_METHOD = "BANK";

    /**
     * 訂單action - update.
     */
    public static final String DAPP_CALLBACK_UPDATE = "UPDATE";
    
    /**
     * 訂單action - cancel.
     */
    public static final String DAPP_CALLBACK_CANCEL = "CANCEL";

    /**
     * 訂單jobType - 訂單.
     */
    public static final String DAPP_CALLBACK_JOB_TYPE = "order";

    /**
     * 訂單狀態 - 失效.
     */
    public static final String ORDER_STATUS_VOID = "VOID";

    /**
     * 訂單狀態 - 取消.
     */
    public static final String ORDER_STATUS_CANCAL = "CANCL";

    /**
     * 訂單狀態 - 完成.
     */
    public static final String ORDER_STATUS_COMP_ED = "COMP";

    /**
     * 訂單狀態 - 失败.
     */
    public static final String ORDER_STATUS_FAIL = "FAIL";

    /**
     * 訂單狀態 - 支付中.
     */
    public static final String ORDER_STATUS_PAYIN = "PAYIN";

    /**
     * 訂單狀態 - 待付款.
     */
    public static final String ORDER_STATUS_WPAY = "WPAY";

    /**
     * 是否启用税 - 启用.
     */
    public static final String ORDER_ENABLED_TAX = "Y";

    /**
     * 订单发运状态 - 已发运.
     */
    public static final String DELIVERY_STATUS_SHIPP = "SHIPP";

    /**
     * 订单发运状态 - 未发运.
     */
    public static final String DELIVERY_STATUS_USHIP = "USHIP";

    /**
     * 订单发运状态 - 部分发运.
     */
    public static final String DELIVERY_STATUS_PSHIP = "PSHIP";

    /**
     * 会员地址类型 - 配送地址.
     */
    public static final String MEMBER_SITE_TYPE_SHIP = "SHIP";

    /**
     * 会员地址类型 - 账单地址.
     */
    public static final String MEMBER_SITE_TYPE_BILL = "BILL";

    /**
     * 订单来源类型 - dapp.
     */
    public static final String ORDER_SOURCE_TYPE_DAPP = "DAPP";

    /**
     * 支付方式 - 在线支付.
     */
    public static final String ORDER_PAYMETHOD_ONLPA = "ONLPA";

    /**
     * 支付方式 - 优惠券支付.
     */
    public static final String ORDER_PAYMETHOD_ECUP = "ECUP";

    /**
     * 会员类型 - VIP.
     */
    public static final String MEMBER_ROLE_VIP = "VIP";

    /**
     * 会员类型 - 员工.
     */
    public static final String MEMBER_ROLE_DIS = "DIS";

    /**
     * 中文姓名长度70位.
     */
    public static final int CHN_NAME_LENGTH = 70;

    /**
     * 英文姓名长度69位(不含空格).
     */
    public static final int ENG_NAME_LENGTH = 69;
    
    /**
     * 英文姓名长度70位(含空格).
     */
    public static final int ENG_NAME_LENGTH_SPACE = 70;
    
    /**
     * 缺少dapp用户.
     */
    public static final String ISG_ERROR_DAPP_USER_MISSING = "dapp account is missing, please new one which login name is 'distributorapp'";
    
    
    /**
     * 金额默认精度 - 2位.
     */
    public static final int defaultScale = 2;
    
    /**
     * dapp默认语言快码.
     */
    public static final String ISG_DAPP_DEFAULT_LANGUAGE = "ISG.DAPP_DEFAULT_LANGUAGE";
}
