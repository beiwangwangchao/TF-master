/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.constant;

import com.lkkhpg.dsis.platform.constant.BaseConstants;

/**
 * 常量.
 * 
 * @author chenjingxiong
 */
public class MemberConstants extends BaseConstants {

    /**
     * 会员状态 - 激活.
     */
    public static final String MEMBER_STATUS_ACTIVE = "ACTV";

    /**
     * 会员状态 - 审核中.
     */
    public static final String MEMBER_STATUS_PENDING = "PEND";

    /**
     * 会员状态 - 拒绝.
     */
    public static final String MEMBER_STATUS_REJECTED = "RJECT";

    /**
     * 会员状态 - 新.
     */
    public static final String MEMBER_STATUS_NEW = "NEW";

    /**
     * 会员状态 - 暂停.
     */
    public static final String MEMBER_STATUS_SUSPENDED = "SUPSE";

    /**
     * 会员状态 - 终止.
     */
    public static final String MEMBER_STATUS_TERMINATED = "TERM";

    /**
     * 会员状态 - 自动终止.
     */
    public static final String MEMBER_STATUS_AUTO_TERMINATED = "ATERM";

    /**
     * 会员状态 - 待变更.
     */
    public static final String MEMBER_STATUS_WCHG = "WCHG";

    /**
     * 会员变更状态 - 自动终止.
     */
    public static final String MEM_STATUS_CHANGE_AUTO_TERMINATE = "ATERM";

    /**
     * 会员变更状态 - 暂停.
     */
    public static final String MEM_STATUS_CHANGE_SUSPEND = "SUSP";

    /**
     * 会员变更状态 - 无效.
     */
    public static final String MEM_STATUS_CHANGE_RJECT = "RJECT";

    /**
     * 会员变更状态 - 终止.
     */
    public static final String MEM_STATUS_CHANGE_TERMINATE = "TERM";

    /**
     * 会员变更状态 - 重新激活.
     */
    public static final String MEM_STATUS_CHANGE_REACTIVE = "REACT";

    /**
     * 审核状态- 审核通过.
     */
    public static final String SYS_REVIEW_STATUS_YES = "S";

    /**
     * 审核状态- 审核未通过.
     */
    public static final String SYS_REVIEW_STATUS_NO = "F";

    /**
     * 审核状态- 审核中.
     */
    public static final String SYS_REVIEW_STATUS_ING = "P";

    /**
     * 账务类型- Sales Point.
     */
    public static final String ACCOUNTING_TYPE_SP = "SP";

    /**
     * 账务类型- Exchange Balance.
     */
    public static final String ACCOUNTING_TYPE_EB = "EB";

    /**
     * 账务类型- Remaining Balance.
     */
    public static final String ACCOUNTING_TYPE_RB = "RB";

    /**
     * 账务类型- PV.
     */
    public static final String ACCOUNTING_TYPE_PV = "PV";

    /**
     * 事务来源类型- 订单支付.
     */
    public static final String TRX_SOURCE_TYPE_ORDER = "OM_SALES_ORDER";

    /**
     * 事务类型- 支付.
     */
    public static final String TRX_TYPE_PAY = "PAY";

    /**
     * 事务类型- 失效.
     */
    public static final String TRX_TYPE_VOID = "VOID";

    /**
     * 事务来源类型-退货单.
     */
    public static final String TRX_SOURCE_TYPE_RETURN = "OM_RETURN_SALES";

    /**
     * 事务类型-退货.
     */
    public static final String TRX_TYPE_RETURN = "RETRN";

    /**
     * 事务类型-退货行.
     */
    public static final String TRX_TYPE_RETURN_LINE = "OM_SALES_RTN_LINE";

    /**
     * 事务类型-退货批次.
     */
    public static final String TRX_TYPE_RETURN_LOT = "OM_SALES_RTN_LOT";

    /**
     * 申请成功.
     */
    public static final String MSG_APPLY_SUCCESS = "msg.info.member.apply_success";

    public static final String MSG_CHANGE_SUCCESS = "msg.info.member.change_success";

    /**
     * 上线无效异常.
     */
    public static final String MSG_ERROR_INVALID_SPONSOR = "msg.error.member.invalid_sponsor";

    /**
     * 会员列表 - 导入文件MIME类型.
     */
    public static final String[] MEMLIST_CONTENT_TYPE = { "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" };

    /**
     * 会员列表 - 导入文件类型.
     */
    public static final String[] MEMLIST_CONTENT_EXT = { "xls", "xlsx" };

    /**
     * 会员列表 - 导入文件大小2M.
     */
    public static final long MEMLIST_FILE_SIZE = 2 << 20;

    /**
     * 会员列表 - 返回消息名.
     */
    public static final String MEMLIST_RE_MESSAGE = "message";

    /**
     * 会员列表 - 返回结果名.
     */
    public static final String MEMLIST_RE_RESULT = "result";

    /**
     * 会员列表 - 返回代码名.
     */
    public static final String MEMLIST_RE_CODE = "code";

    /**
     * 会员列表 - 返回数据信息名.
     */
    public static final String MEMLIST_RE_INFO = "info";

    /**
     * 会员列表 - 文件大小超过限制代码.
     */
    public static final String MEMLIST_F_EXCEED_SIZE = "file_exceed_size";

    /**
     * 会员列表 - 文件类型不合法代码.
     */
    public static final String MEMLIST_Q_TYPE_DENIED = "quene_type_denied";

    /**
     * 会员列表 - 文件为空代码.
     */
    public static final String MEMLIST_F_IS_EMPTY = "file_is_empty";

    /**
     * 会员列表 - 不是文件代码.
     */
    public static final String MEMLIST_Q_NOT_FILE = "not_a_file";

    /**
     * 会员列表 - 其他错误代码.
     */
    public static final String MEMLIST_OTH_ERROR = "other_error";

    /**
     * 会员列表 - 上传成功代码.
     */
    public static final String MEMLIST_SUCCESS = "success";

    /**
     * 会员列表 - 文件大小超过限制信息.
     */
    public static final String MEMLIST_MSG_EXCEED_SIZE = "msg.info.member.import.exceed_size";

    /**
     * 会员列表 - 文件类型不合法信息.
     */
    public static final String MEMLIST_MSG_YPE_DENIED = "msg.info.member.import.type_denied";

    /**
     * 会员列表 - 文件为空信息.
     */
    public static final String MEMLIST_MSG_IS_EMPTY = "msg.info.member.import.is_empty";

    /**
     * 会员列表 - 不是文件信息.
     */
    public static final String MEMLIST_MSG_NOT_FILE = "msg.info.member.import.not_file";

    /**
     * 会员列表 - 会员重复.
     */
    public static final String MEMBER_CODE_REPEAT = "msg.info.member.member_code_repeat";

    /**
     * 会员列表 - 其他错误信息.
     */
    public static final String MEMLIST_MSG_OTH_ERROR = "msg.info.member.import.other_error";

    /**
     * 会员列表 - 上传成功信息.
     */
    public static final String MEMLIST_MSG_SUCCESS = "msg.info.member.import.success";

    /**
     * 会员状态 - 修改成功.
     */
    public static final String MSG_STATUS_UPDATE_SUCCESS = "msg.info.member.change_success";

    /**
     * 会员列表 - 导入会员列表所传ID类型.
     */
    public static final String MEMLIST_NAME_IDTYPE = "idType";

    /**
     * 会员列表 - 导入会员列表所传ID值.
     */
    public static final String MEMLIST_NAME_IDVALUE = "mentionId";

    /**
     * 会员列表 - 导入会员列表所传市场ID值.
     */
    public static final String MEMLIST_NAME_IDMARKET = "marketId";

    /**
     * 会员审核 - 批准成功.
     */
    public static final String MSG_APPROVAL_SUCCESS = "msg.infor.member.approve_success";

    /**
     * 会员审核 - 拒绝成功.
     */
    public static final String MSG_REJECT_SUCCESS = "msg.info.member.reject_success";

    /**
     * 会员导入 - 会员ID不存在代码.
     */
    public static final String MEMIMPORT_MEMBER_ID_FAIL = "ID_FAIL";

    /**
     * 会员导入 - 会员已验证，但未做导入处理.
     */
    public static final String MEMIMPORT_MEMBER_VALIDATE_PROCESS_ERROR = "VALIDATE_PROCESS_ERROR";

    /**
     * 会员导入 - 会员状态为终止或自动终止代码.
     */
    public static final String MEMIMPORT_MEMBER_STATUS_FAIL = "STATUS_FAIL";

    /**
     * 会员导入 - 会员市场不符合代码.
     */
    public static final String MEMIMPORT_MEMBER_MARKET_FAIL = "MARKET_FAIL";

    /**
     * 会员导入 - 会员已经导入活动列表.
     */
    public static final String MEMIMPORT_MEMBER_IMPORT_EVENT_EXIST = "EVENT_EXIST";

    /**
     * 会员导入 - 会员验证通过标志Y.
     */
    public static final String MEMIMPORT_FLAG_ENABLE = "Y";

    /**
     * 会员导入 - 会员验证通过标志N.
     */
    public static final String MEMIMPORT_FLAG_DISABLE = "N";

    /**
     * 会员导入 - 会员导入消息分配列表.
     */
    public static final String MEMIMPORT_TYPE_MESSAGE = "MSG";

    /**
     * 会员导入 - 会员导入优惠券分配列表.
     */
    public static final String MEMIMPORT_TYPE_VOUCHER = "VOUCH";

    /**
     * 会员导入 - 会员导入活动分配列表.
     */
    public static final String MEMIMPORT_TYPE_EVENT = "EVENT";

    /**
     * 奖金日期- 奖金期间已关闭.
     */
    public static final String SPM_PERIOD_STATUS_CLOSED = "Y";

    /**
     * 奖金日期- 奖金期间未关闭.
     */
    public static final String SPM_PERIOD_STATUS_UNCLOSED = "N";

    /**
     * 会员地点类型- 账单地点.
     */
    public static final String SITE_USE_CODE_BILL = "BILL";

    /**
     * 会员地点类型- 配送地点.
     */
    public static final String SITE_USE_CODE_SHIP = "SHIP";

    /**
     * 会员地点类型- 家庭地点.
     */
    public static final String SITE_USE_CODE_HOME = "HOME";

    /**
     * 会员地点类型- 联系地点.
     */
    public static final String SITE_USE_CODE_CTACT = "CTACT";

    /**
     * 变更市场审批- 待审批状态.
     */
    public static final String MM_MARKETCHANGE_NEW = "P";

    /**
     * 变更市场审批- 通过状态.
     */
    public static final String MARKET_CHANGE_APPROVED = "2";

    /**
     * 变更市场审批- 不通过状态.
     */
    public static final String MARKET_CHANGE_REJECT = "9";

    /**
     * 冲销余额- 无效会员通知.
     */
    public static final String MM_MEMWITHDRAW_NO_ACTIVE = "noActive";

    /**
     * 冲销余额- 余额不足通知.
     */
    public static final String MM_MEMWITHDRAW_NO_BALANCE = "noBalance";

    /**
     * 冲销余额- 冲销类型.
     */
    public static final String MM_MEMWITHDRAW_WRITEOFF_TYPE = "WFRB";

    /**
     * 冲销余额- 调整类型.
     */
    public static final String MM_MEMWITHDRAW_ADJUSTMENT_TYPE = "ADD";

    /**
     * 冲销余额- 冲销状态.
     */
    public static final String MM_MEMWITHDRAW_STATUS = "NEW";

    /**
     * 冲销余额- 冲销成功通知.
     */
    public static final String MM_MEMWITHDRAW_SUCCESS = "success";

    /**
     * 变更市场- 目标市场ID（待接口完成即删除）.
     */
    public static final Long TO_MARKET_ID = 3L;

    /**
     * 会员ID流水号名称.
     */
    public static final String DOC_SEQ_MEMBER_DODE = "DOC_SEQ_MEMBER_DODE";

    /**
     * 会员ID流水号长度.
     */
    public static final int DOC_SEQ_MEMBER_DODE_MAX_LENGTH = 7;

    /**
     * 会员ID流水号初始值.
     */
    public static final Long DOC_SEQ_MEMBER_DODE_INI_VALUE = 1L;

    /**
     * 会员ID流水号排除后缀.
     */
    public static final String DOC_SEQ_MEMBER_DODE_EXCELUDE = "4";

    /**
     * 会员同步标记-Y.
     */
    public static final String SYNC_FLAG_Y = "Y";

    /**
     * 会员同步标记-N.
     */
    public static final String SYNC_FLAG_N = "N";

    /**
     * 相关人- 配偶.
     */
    public static final String MEMBER_REL_TYPE_SPOUS = "SPOUS";

    /**
     * 相关人- 受益人.
     */
    public static final String MEMBER_REL_TYPE_BENF = "BENF";

    /**
     * 会员地点用途-家庭地址.
     */
    public static final String SITE_USE_HOME = "HOME";

    /**
     * 会员地点用途-联系地址.
     */
    public static final String SITE_USE_CTACT = "CTACT";

    /**
     * gst地址.
     */
    public static final String SITE_USE_GST = "GST";

    /**
     * 银行卡状态 - 启用.
     */
    public static final String BANKCARD_STATUS_ACTIVE = "ACTV";

    /**
     * POS系统会员年龄限制.
     */
    public static final int POS_MEMBER_AGE_LIMIT = 18;

    /**
     * 证件类型-身份证.
     */
    public static final String ID_TYPE_IC = "IC";

    /**
     * 会员类型-公司.
     */
    public static final String MEMBER_TYPE_COMP = "COMP";
    /**
     * 整数 1.
     */
    public static final int ONE = 1;
    /**
     * 整数 -11.
     */
    public static final int ELEVEN = -11;

    /**
     * 整数 0.
     */
    public static final int ZERO = 0;

    /**
     * mws-忘记密码-定义rediskey值.
     */
    public static final String FORGER_PWD_KEY = "mws:account:forget_pass";
    /**
     * mws-忘记密码-重定向.
     */
    public static final String REDIRECT = "redirect:";
    /**
     * mws-忘记密码-跳转到第一步.
     */
    public static final String FORGER_PWD_REDIRECT = "/user/retrieve_password_before.html";
    /**
     * mws-忘记密码-跳转到第二不.
     */
    public static final String FORGER_PWD_WAY = "/user/retrieve_password_way";
    /**
     * mws-忘记账号-页面.
     */
    public static final String FORGER_MEMBER_ACCOUNT = "retrieve_user";
    /**
     * mws-忘记密码-发送验证码没超过60秒验证.
     */
    public static final Long VERIFICATION_WAITING_TIME = 60000L;
    /**
     * mws-忘记密码-缓存到redis10分钟过期.
     */
    public static final Long REDIS_EXPIRATION_TIME = 600000L;
    /**
     * mws-随机生成校验码for.
     */
    public static final Long BASE_MENBER_SIX = 6L;
    /**
     * mws-随机生成校验码for.
     */
    public static final Long BASE_MEMBER_NINE = 9L;
    /**
     * mws-账户id.
     */
    public static final String FIELD_MEMBER_ACCOUNT_ID = "accountId";
    /**
     * mws-忘记账号-发送校验时间.
     */
    public static final String FIELD_SEND_TIME_USER = "userSendTime";
    /**
     * mws-忘记密码-发送校验时间.
     */
    public static final String FIELD_SEND_VERIFICODE = "passwordSend";
    /**
     * mws-忘记密码-第一次发送校验码是假.
     */
    public static final String FIELD_WAY_TIME_PASSWORD_PHONE = "phonePasswordWayTime";
    /**
     * mws-忘记密码-第一次发送校验码是假.
     */
    public static final String FIELD_WAY_TIME_PASSWORD_EMAIL = "emailPasswordWayTime";
    /**
     * mws-忘记账号-第一次发送校验码时间-邮箱.
     */
    public static final String FIELD_WAY_TIME_EMAIL = "emailWayTime";
    /**
     * mws-忘记账号-第一次发送校验码时间-电话.
     */
    public static final String FIELD_WAY_TIME_PHONE = "phoneWayTime";
    /**
     * mws-redis过期时间.
     */
    public static final Long FIELD_TIME_OUT = 600L;//300L;
    /**
     * mws-忘记密码-电话格式.
     */
    public static final String PHONE_REGEX = "^[0-9]{1,14}$";
    /**
     * mws-忘记密码-邮箱格式.
     */
    public static final String EMAIL_REGEX = "^([\\s\\S]*)+@([\\S\\s]*)+(\\.([\\S\\s]*)+)+$";
    /**
     * mws-uuidkey.
     */
    public static final String FIELD_JUMP_UUID = "checkUuid";
    /**
     * mws-校验方式-电话.
     */
    public static final String FIELD_VERIFICATION_PHONE = "PHONE";
    /**
     * mws-校验方式-邮箱.
     */
    public static final String FIELD_VERIFICATION_EMAIL = "EMAIL";
    /**
     * mws-区分校验码是发送到电话还是邮箱.
     */
    public static final String FIELD_VERIFICATION = "way";
    /**
     * mws-memberId.
     */
    public static final String MWS_MEMBER_ID = "memberId";
    /**
     * mws-地址类型-CTACT.
     */
    public static final String MEM_SITE_TYPE_CTACT = "CTACT";
    /**
     * mws-地址类型-SHIP.
     */
    public static final String MEM_SITE_TYPE_SHIP = "SHIP";
    /**
     * mws-地址类型-BILL.
     */
    public static final String MEM_SITE_TYPE_BILL = "BILL";
    /**
     * 忘记账号-消息模板-发送验证码到电话.
     */
    public static final String PHONE_FORGET_ACCOUNT_VCODE = "PHONE_FORGET_ACCOUNT_VCODE";
    /**
     * 忘记账号-消息模板-发送验证码到邮箱.
     */
    public static final String EMAIL_FORGET_ACCOUNT_VCODE = "EMAIL_FORGET_ACCOUNT_VCODE";
    /**
     * 忘记账号-消息模板-发送账号到电话消息模板.
     */
    public static final String FORGET_ACCOUNT_PHONE = "FORGET_ACCOUNT_PHONE";
    /**
     * 忘记账号-消息模板-发送账号到邮箱.
     */
    public static final String FORGET_ACCOUNT_EMAIL = "FORGET_ACCOUNT_EMAIL";
    /**
     * 新建用户或管理员修改密码时产出临时口令-发送账号到短信.
     */
    public static final String PHONE_USER_TEMP_PWD = "PHONE_USER_TEMP_PWD";
    /**
     * 消息模板-新会员欢迎短信.
     */
    public static final String PHONE_NEW_MEMBER_WELCOME = "PHONE_NEW_MEMBER_WELCOME";
    /**
     * 消息模板-新会员欢迎短信(VIP).
     */
    public static final String PHONE_NEW_MEMBER_WELCOME_VIP = "PHONE_NEW_MEMBER_WELCOME_VIP";
    /**
     * 消息模板-VIP转Distributor(JOB).
     */
    public static final String VIP_CHANGE_TO_DISTRIBUTOR = "VIP_CHANGE_TO_DISTRIBUTOR";
    /**
     * 消息模板-VIP转Distributor.
     */
    public static final String VIP_CHANGE_TO_DISTRIBUTOR_WELCOME = "VIP_CHANGE_TO_DISTRIBUTOR_WELCOME";
    /**
     * 消息模板-会员重置密码短信.
     */
    public static final String PHONE_NEW_MEMBER_PASSWORD = "PHONE_NEW_MEMBER_PASSWORD";
    /**
     * 消息模板-忘记密码-发送短信.
     */
    public static final String PHONE_SC_FORGET_PASSWORD = "PHONE_SC_FORGET_PASSWORD";

    /**
     * 加入渠道-DSIS.
     */
    public static final String JOINT_SITE_DSIS = "DSIS";
    /**
     * 加入渠道-MWS.
     */
    public static final String JOINT_SITE_MWS = "MWS";
    /**
     * 加入渠道-DAPP.
     */
    public static final String JOINT_SITE_DAPP = "DISAP";
    /**
     * 会员导入 - 会员已经导入优惠券分配列表.
     */
    public static final String MEMIMPORT_MEMBER_IMPORT_VOUCHER_EXIST = "VOUCHER_EXIST";
    /**
     * 会员导入 - 会员已经导入消息分配列表.
     */
    public static final String MEMIMPORT_MEMBER_IMPORT_MESSAGE_EXIST = "MESSAGE_EXIST";
    /**
     * 会员导入 - 文件A1单元格的值必须是memberCode.
     */
    public static final String MSG_ERROR_VALUE_OF_THE_CELL_MUST_BE_MEMBERCODE = "MEMBERCODE_ERROR";

    /**
     * 会员导入 - 文件不规范.
     */
    public static final String MSG_ERROR_THE_FILE_IS_NOT_STANDARD = "FILE_ERROR";

    /**
     * 配置文件 - 创建会员发送短信.
     */
    public static final String PROFILE_MM_SEND_SMS_MSG = "MM.SEND_SMS_MSG";

    /**
     * 数据操作类型-I 新增.
     */
    public static final String OPER_TYPE_I = "I";

    /**
     * 数据操作类型-U 更新.
     */
    public static final String OPER_TYPE_U = "U";

    /**
     * 账号临时密码失效时间.
     */
    public static final String TEMP_PWD_EXPIRY_DATE = "SYS.TEMP_PWD_EXPIRY_DATE";

    /**
     * 默认账号临时密码.
     */
    public static final String TEMP_PWD = "SYS.TEMP_PWD";

    /**
     * 启用账号临时密码重置.
     */
    public static final String TEMP_PWD_RESET = "SYS.TEMP_PWD_RESET";

    /**
     * 市场变更- 发送站内信模板编号.
     */
    public static final String MM_MARKETCHANGE_SEND_MESSAGE = "LETTER_CHANGE_MARKET_AUDIT";

    /**
     * 会员角色 - 字段.
     */
    public static final String FIELD_MEMBER_ROLE = "memberRole";

    /**
     * 左括号.
     */
    public static final String MM_LEFT_BRACKET = "(";

    /**
     * 右括号.
     */
    public static final String MM_RIGHT_BRACKET = ")";

    /**
     * 会员角色 - 分销商.
     */
    public static final String MM_ROLE_DIS = "DIS";

    /**
     * 会员角色 - 运维.
     */
    public static final String MM_ROLE_SUP = "SUP";

    /**
     * 会员角色 - VIP.
     */
    public static final String MM_ROLE_VIP = "VIP";

    /**
     * 账务事务处理类型 - 冲销余额.
     */
    public static final String TRX_TYPE_WITHDRAW = "WTFB";

    /**
     * 冲销节余表 - 表名称.
     */
    public static final String MM_MEM_WITHDRAW = "MM_MEM_WITHDRAW";

    /**
     * 快码 - 会员等级.
     */
    public static final String MM_MEMBER_RANK = "MM.MEMBER_RANK";

    /**
     * 转经销商符合要求的PV总值.
     */
    public static final double DIS_STANDARD_PV_SUM = 3500.0;

    /**
     * 当前日期的一个月前.
     */
    public static final int ONE_MONTH_AGO = 1;

    /**
     * 当前日期的两个月前.
     */
    public static final int TWO_MONTH_AGO = 2;

    /**
     * 变更所有权结果信息最大长度.
     */
    public static final int CHANGE_OWNER_RESULT_MAX_LENGTH = 2000;

    /**
     * 默认日期-yyyyMM.
     */
    public static final String DEFAULT_DATE = "197001";

    /**
     * 奖金领取方式-银行.
     */
    public static final String BONUS_RCV_METHOD_BANK = "BANK";

    /**
     * 功能代码-最终奖金.
     */
    public static final String FUNCTION_CODE_BONUS_FINAL = "BONUS_FINAL";

    /**
     * 市场代码-香港
     */
    public static final String MARKET_HK = "HK";

    /**
     * 市场代码-香港
     */
    public static final String MARKET_TW = "TW";

    /**
     * 市场代码-马来
     */
    public static final String MARKET_MY = "MY";

    /**
     * 市场代码-加拿大
     */
    public static final String MARKET_CA = "CA";

    /**
     * 市场代码-新加坡
     */
    public static final String MARKET_SG = "SG";

    /**
     * 市场代码-新加坡
     */
    public static final String MARKET_CN = "CN";

    /**
     * 快码 - VIP转经销商日期分水岭.
     */
    public static final String VIP_CHANGE_DISTRIBUTOR_SHED = "MM.VIP_CHANGE_DISTRIBUTOR_SHED";

    /**
     * 快码 - VIP转经销商销售额标准.
     */
    public static final String VIP_SALES_AMOUNT_CRITERIA = "MM.VIP_SALES_AMOUNT_CRITERIA";
    
    /**
     * 流水号类型 - 会员变更角色.
     */
    public static final String SEQ_APPLY_ROLE = "MEM_APPLY_ROLE";

    /**
     * 会员变更角色流水号长度.
     */
    public static final int APPLY_ROLE_LENGTH = 9;

    /**
     * 会员变更角色流水号起始值.
     */
    public static final Long APPLY_ROLE_START_NUMBER = 100000001L;

    /**
     * 会员更改角色审核状态 - 待审核.
     */
    public static final String CHANGE_ROLE_APPROVE_STATUS_APING = "APING";

    /**
     * 会员更改角色审核状态 - 已审核.
     */
    public static final String CHANGE_ROLE_APPROVE_STATUS_APED = "APED";
}
