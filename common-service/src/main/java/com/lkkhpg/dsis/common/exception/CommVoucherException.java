/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 优惠券统一异常.
 * 
 * @author chenjingxiong
 */
public class CommVoucherException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.common.promption";

    /**
     * 优惠券编码必须唯一.
     */
    public static final String MSG_ERROR_PDM_VOUCHER_CODE_UNIQUE = "msg.error.promotion.voucher_code_unique";

    /**
     * 市场ID不存在.
     */
    public static final String MSG_ERROR_MARKET_ID_NOT_EXIST = "msg.error.market_id_not_exist";

    /**
     * 销售组织ID不存在.
     */
    public static final String MSG_ERROR_SALES_ORG_ID_NOT_EXIST = "msg.error.sales_org_id_not_exist";

    /**
     * 会员ID不存在.
     */
    public static final String MSG_ERROR_MEMBER_ID_NOT_EXIST = "msg.error.member.invalid_member";

    /**
     * 事务处理的优惠券ID为空.
     */
    public static final String MSG_ERROR_TRX_VOUCHER_ID_INVALID = "msg.error.trx_voucher_id_invalid";

    /**
     * 数量无效.
     */
    public static final String MSG_ERROR_TRX_QTY_INVALID = "msg.error.trx_qty_invalid";

    /**
     * 事务处理类型无效.
     */
    public static final String MSG_ERROR_TRX_TYPE_INVALID = "com.lkkhpg.dsis.admin.inventory.msg_error_trx_type_invalid";

    /**
     * 事务处理来源类型无效.
     */
    public static final String MSG_ERROR_TRX_SOURCE_TYPE_INVALID = "msg_error_trx_source_type_invalid";

    /**
     * 事务处理来源键值无效.
     */
    public static final String MSG_ERROR_TRX_SOURCE_KEY_INVALID = "com.lkkhpg.dsis.admin.inventory.msg_error_trx_source_key_invalid";

    /**
     * 事务处理来源参考无效.
     */
    public static final String MSG_ERROR_TRX_SOURCE_REFERENCE_INVALID = "msg.error.trx_source_reference_invalid";

    /**
     * 失效的优惠券.
     */
    public static final String MSG_ERROR_PDM_VOUCHAER_INVALID = "msg.error.trx_voucher_id_invalid";

    /**
     * 优惠券未分配.
     */
    public static final String MSG_ERROR_PDM_MEMBER_NOT_ASSIGN_VOUCHER = "msg.error.pdm.member_not_assign_voucher";

    /**
     * 优惠券数量不够.
     */
    public static final String MSG_ERROR_PDM_VOUCHER_QUANTITY_NOT_ENOUGH = "msg.error.pdm.voucher_quantity_not_enough";

    /**
     * 不可叠加优惠券使用多张异常.
     */
    public static final String MSG_ERROR_PDM_VOUCHER_NSTAC_QTY_ERROR = "msg.error.pdm.voucher.nstac_qty_error";

    /**
     * 销售订单头上不能使用优惠券应用为“税前”的优惠券.
     */
    public static final String MSG_ERROR_PDM_VOUCHER_AFTERTAX_NOT_APPLY = "msg.error.pdm.voucher.afterTax_not_apply";

    /**
     * 优惠券应用条件错误.
     */
    public static final String MSG_ERROR_PDM_VOUCHER_APPLY_CRITERIA = "msg.error.pdm.voucher.apply_criteria_error";

    /**
     * 优惠券使用时订单额度不满足运算条件.
     */
    public static final String MSG_ERROR_PDM_VOUCHER_ORDER_AMOUNT_ERROR = "msg.error.pdm.voucher.order_amount_error";

    /**
     * 优惠金额校验错误.
     */
    public static final String MSG_ERROR_PDM_VOUCHER_DISCOUNT_AMT_ERROR = "msg.error.pdm.voucher.discount_amt_error";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public CommVoucherException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }
}
