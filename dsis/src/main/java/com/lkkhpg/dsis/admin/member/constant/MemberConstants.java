/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.constant;

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
     * 会员变更状态 - 自动终止.
     */
    public static final String MEM_STATUS_CHANGE_AUTO_TERMINATE = "ATERM";

    /**
     * 会员变更状态 - 暂停.
     */
    public static final String MEM_STATUS_CHANGE_SUSPEND = "SUSP";

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
     * 申请成功.
     */
    public static final String MSG_APPLY_SUCCESS = "msg.info.member.apply_success";

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
     * 会员列表 - 其他错误信息.
     */
    public static final String MEMLIST_MSG_OTH_ERROR = "msg.info.member.import.other_error";

    /**
     * 会员列表 - 上传成功信息.
     */
    public static final String MEMLIST_MSG_SUCCESS = "msg.info.member.import.success";

    /**
     * 会员列表 - 导入会员列表所传ID类型.
     */
    public static final String MEMLIST_NAME_IDTYPE = "idType";

    /**
     * 会员列表 - 导入会员列表所传ID值.
     */
    public static final String MEMLIST_NAME_IDVALUE = "mentionId";

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
    public static final String MEMIMPORT_MEMBER_ID_FAIL = "member_id_fail";

    /**
     * 会员导入 - 会员状态为终止或自动终止代码.
     */
    public static final String MEMIMPORT_MEMBER_STATUS_FAIL = "member_status_fail";

    /**
     * 会员导入 - 会员市场不符合代码.
     */
    public static final String MEMIMPORT_MEMBER_MARKET_FAIL = "member_market_fail";

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
    public static final String MEMIMPORT_TYPE_MESSAGE = "message";

    /**
     * 会员导入 - 会员导入优惠券分配列表.
     */
    public static final String MEMIMPORT_TYPE_VOUCHER = "voucher";

    /**
     * 奖金日期- 奖金期间已关闭.
     */
    public static final String SPM_PERIOD_STATUS_CLOSED = "Y";

    /**
     * 奖金日期- 奖金期间未关闭.
     */
    public static final String SPM_PERIOD_STATUS_UNCLOSED = "N";

    /**
     * 会员地点类型- 家庭地点.
     */
    public static final String SITE_USE_CODE_HOME = "HOME";

    /**
     * 会员地点类型- 联系地点.
     */
    public static final String SITE_USE_CODE_CTACT = "CTACT";

    /**
     * 变更市场审批- 通过状态.
     */
    public static final String MARKET_CHANGE_APPROVED = "approved";

    /**
     * 变更市场审批- 不通过状态.
     */
    public static final String MARKET_CHANGE_REJECT = "reject";

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
    public static final String MM_MEMWITHDRAW_WRITEOFF_TYPE = "Write-Off Remaining Balance";

    /**
     * 冲销余额- 调整类型.
     */
    public static final String MM_MEMWITHDRAW_ADJUSTMENT_TYPE = "Add";

    /**
     * 冲销余额- 冲销状态.
     */
    public static final String MM_MEMWITHDRAW_STATUS = "new";

    /**
     * 冲销余额- 冲销成功通知.
     */
    public static final String MM_MEMWITHDRAW_SUCCESS = "success";

    /**
     * 市场变更审核- 新建状态.
     */
    public static final String MM_MARKETCHANGE_NEW = "new";

    /**
     * 市场变更- 发送站内信模板编号.
     */
    public static final String MM_MARKETCHANGE_SEND_MESSAGE = "LETTER_CHANGE_MARKET_AUDIT";
    
    /**
     * 会员类型- 个人.
     */
    public static final String MM_TYPE_IDV = "IDV";
    
    /**
     * 证件类型- IC.
     */
    public static final String MM_CERTIFICATE_TYPE_IC = "IC";
    
    /**
     * 领取奖金方式- BANK.
     */
    public static final String MM_BONUS_RECV_TYPE_BANK = "BANK";
}
