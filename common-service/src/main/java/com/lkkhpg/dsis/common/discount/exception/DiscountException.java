package com.lkkhpg.dsis.common.discount.exception;

import com.lkkhpg.dsis.common.exception.CommSystemProfileException;

public class DiscountException extends CommSystemProfileException {


    /**
     * 构造方法.
     *
     * @param descriptionKey 消息代码.
     * @param parameters     消息参数.
     */
    public DiscountException(String descriptionKey, Object[] parameters) {
        super(descriptionKey, parameters);
    }


    /**
     * transactino has already finished error;
     */
    public static final String MSG_ERROR_TRX_ALREADY_FINISHED = "discount.message.error.trx_already_finish";

    /**
     * 事务处理日期过早
     */
    public static final String MSG_ERROR_NOT_ALLOWED_TRX_DATE = "discount.message.error.trx_date_is_bigger_than_now";

    /**
     * 调整金额不可为空
     */
    public static final String MSG_ERROR_EMPTY_ADJUST_AMT = "discount.message.error.empty_adjust_amt";

    /**
     * 会员不可为空
     */
    public static final String MSG_ERROR_MEMBER_CAN_NOT_BE_NULL = "discount.message.error.member_can_not_be_null";

    /**
     * 事务处理头id生成出错,请联系管理员！
     */
    public static final String MSG_ERROR_MISSING_TRX_HEAD_ID = "discount.message.error.missing_trx_head_id";

    /**
     * 已存在的用户，请使用调整选项
     */
    public static final String MSG_ERROR_MEMBER_ALREADY_EXISTS = "discount.message.error.member_already_exists";

    /**
     * 会员不存在，请维护会员信息后重试
     */
    public static final String MSG_ERROR_MEMBER_NOT_EXISTS = "discount.message.error.member_not_exists";

    /*
     * 数据库异常，请稍后重试
     */
    public static final String MSG_ERROR_TRX_PROCESS_ERROR = "discount.message.error.transaction_process_error";

    /**
     * 重复的事务处理编码
     */
    public static final String MSG_ERROR_DUPLICATE_TRX_NUM = "discount.message.error.duplicate_trx_num";

    /**
     * 警告：该用户已存在折扣
     */
    public static final String MSG_WARNING_DISCOUNT_AMT_ALREADY_HAVE = "discount.message.warning.discount_amt_already_have";

    //订单保存失败，请联系管理员
    public static final String MSG_ERROR_SAVE_FAIL = "discount.message.error.save_error";

    /*
     * 事务未保存
     */
    public static final String MSG_ERROR_MUST_HAVE_TRX_LINE = "discount.message.error.save_error";

    /*
     * 没有事务行
     */
    public static final String MSG_ERROR_TRX_NOT_SAVE = "discount.message.error.line_not_exists";

    /*
     * 不可用的状态
     */
    public static final String MSG_ERROR_STATUS_NOT_RIGHT = "discount.message.error.status_not_right";


    /*
     *调整类型错误
     */
    public static final String MSG_ERROR_ADJ_TYPE_NOT_RIGHT = "discount.message.error.adj_type_not_right";

    /*
     * 调整金额大于现有折扣金额
     */
    public static final String MSG_ERROR_ADJAMT_IS_BIGGER_THAN_DISCOUNT = "discount.message.error.adjamt_is_bigger_than_discount";
}

