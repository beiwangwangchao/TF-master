/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 库存统一异常.
 * 
 * @author chenjingxiong
 */
public class CommInventoryException extends BaseException {
    private static final long serialVersionUID = 1L;

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.inventory";

    /**
     * 库存组织无效.
     */
    public static final String MSG_ERROR_ORGANIZATION_INVALID = "msg_error_organization_invalid";

    /**
     * 商品无效.
     */
    public static final String MSG_ERROR_ITEM_INVALID = "msg_error_item_invalid";

    /**
     * 批次必输.
     */
    public static final String MSG_ERROR_LOT_REQUIRED = "msg_error_lot_required";

    /**
     * 批次无效.
     */
    public static final String MSG_ERROR_LOT_INVALID = "msg_error_lot_invalid";

    /**
     * 批次要为空.
     */
    public static final String MSG_ERROR_LOT_MUST_NULL = "msg_error_lot_must_null";

    /**
     * 批次已失效.
     */
    public static final String MSG_ERROR_LOT_DISABLED = "msg_error_lot_disabled";

    /**
     * 数量无效.
     */
    public static final String MSG_ERROR_QTY_INVALID = "msg_error_qty_invalid";

    /**
     * 事务处理类型无效.
     */
    public static final String MSG_ERROR_TRX_TYPE_INVALID = "msg_error_trx_type_invalid";

    /**
     * 事务处理来源类型无效.
     */
    public static final String MSG_ERROR_TRX_SOURCE_TYPE_INVALID = "msg_error_trx_source_type_invalid";

    /**
     * 事务处理来源键值无效.
     */
    public static final String MSG_ERROR_TRX_SOURCE_KEY_INVALID = "msg_error_trx_source_key_invalid";

    /**
     * 转出组织和转入组织不能相同.
     */
    public static final String MSG_ERROR_ORGANIZATION_NOT_ALLOWED_SAME = "msg_error_organization_not_allowed_same";

    /**
     * 转出日期必须在系统当前日期之前.
     */
    public static final String MSG_ERROR_TRXDATE_MUST_LESS_THAN_NOW_DATE = "msg_error_trxdate_must_less_than_now_date";

    /**
     * 转出数量大于库存数量.
     */
    public static final String MSG_ERROR_QUTRY_GT_STOCK = "msg_error_qutry_gt_stock";

    /**
     * 商品启用批次控制.
     */
    public static final String MSG_ERROR_ITEM_USE_LOT = "msg_error_item_use_lot";

    /**
     * 转入但数量大于转出单总量.
     */
    public static final String MSG_ERROR_IN_BG_OUT = "msg_error_in_bg_out";

    /**
     * 库存处理数必输.
     */
    public static final String MSG_ERROR_REQUIRED = "msg_error_required";

    /**
     * 出库数量超过当前库存量.
     */
    public static final String MSG_ERROR_OUT_OF_STOCK = "msg.error.inv.not_allowed_out_of_stock";

    /**
     * 处理事件超过当前系统时间.
     */
    public static final String MSG_ERROR_NOT_ALLOWED_TRX_DATE = "msg.error.inv.not_allowed_trx_date";

    /**
     * 查询单据编号不合法.
     */
    public static final String MSG_ERROR_NOT_ALLOWED_TRX_NUMBER = "msg.error.inventory.not_allowed_trx_number";

    /**
     * 库存不足扣减.
     */
    public static final String MSG_ERROR_NOT_ENOUGH_STOCK = "msg.error.inventory.not_enough_stock";

    /**
     * 状态为取消时 备注必填.
     */
    public static final String MSG_ERROR_REMAR_NOT_NULL_WHEN_STATUS_CANCLE = "msg_error_remar_not_null_when_status_cancle";

    /**
     * 不可重复提交.
     */

    public static final String MSG_ERROR_SHALL_NOT_REPEAT_SUBMISSION = "msg_error_shall_not_repeat_submission";

    /**
     * 移库单行不可为空.
     */

    public static final String MSG_ERROR_TRANSFER_LINE_IS_NULL = "msg_error_transfer_line_is_null";
    
    /**
     * 移库单提交校验错误-到期日不一致.
     */
    public static final String MSG_ERROR_TRANSFER_SUBMIT_ERROR_EXPIREDATE_DIFF = "msg_error_transfer_submit_error_expiredate_diff";

    /**
     * 移库单提交校验错误-到期日不一致-pattern1.
     */
    public static final String SUBMIT_ERROR_EXPIREDATE_DIFF_1 = "[";
    
    /**
     * 移库单提交校验错误-到期日不一致-pattern2.
     */
    public static final String SUBMIT_ERROR_EXPIREDATE_DIFF_2 = "] [";
    
    /**
     * 移库单提交校验错误-到期日不一致-pattern3.
     */
    public static final String SUBMIT_ERROR_EXPIREDATE_DIFF_3 = "]\n";
    
    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public CommInventoryException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }
}
