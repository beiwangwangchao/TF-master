/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 订单统一异常.
 * 
 * @author chenjingxiong
 */
public class CommOrderException extends BaseException {

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.order";

    public static final String ORDER_EXCEPTION = "msg.error.order.order_no_found";

    public static final String INVOICE_EXCEPTION = "msg.error.order.invoice_no_found";

    public static final String INVOICE_EXIST_EXCEPTON = "msg.error.order.invoice_exist";

    public static final String INVOICE_NUMBER_EXCEPTON = "msg.error.order.invoice_number_create_fail";

    public static final String INVOICE_ASSIGN_CODE_EXCEPTION = "msg.error.order.invoice_number_assign_code_no_found";

    public static final String INVOICE_ASSIGN_VALUE_EXCEPTION = "msg.error.order.invoice_number_assign_value_no_found";

    public static final String MSG_ERROR_OM_NO_SEARCH_CRITERIA = "msg.error.system.no_search_criteria";

    public static final String MSG_ERROR_OM_NO_SEARCH_RECORD = "msg.error.system.search_no_record";

    public static final String MSG_ERROR_OM_AMOUNT_BEYOND = "msg.error.order.amount_beyond";

    public static final String MSG_ERROR_OM_REFUND_EMPTY = "msg.error.order.refund_empty";
    /**
     * 订单支付时支付金额与实际支付金额不一致异常.
     */
    public static final String MSG_ERROR_OM_AMOUNT_ERROR = "msg.error.order.amount_error";
    /**
     * 订单支付时字段不能为空异常.
     */
    public static final String MSG_ERROR_OM_PAYMENT_EMPTY = "msg.error.om.payment_empty";
    /**
     * 订单支付失败异常.
     */
    public static final String MSG_ERROR_OM_FAILURE_PAY_AGAIN = "msg.error.order.failure_pay_again";
    /**
     * 销售积分不足异常.
     */
    public static final String MSG_ERROR_OM_SALES_POINT_INSUFFICIENT = "msg.error.order.sales_point_insufficient";

    /**
     * EB不足异常.
     */
    public static final String MSG_ERROR_OM_EB_INSUFFICIENT = "msg.error.order.eb_insufficient";

    /**
     * RB不足异常.
     */
    public static final String MSG_ERROR_OM_RB_INSUFFICIENT = "msg.error.order.rb_insufficient";
    /**
     * 货到付款只能有一条支付行.
     */
    public static final String MSG_WARNING_OM_CONFIRM_AFTER_PAY = "msg.warning.order.confirm_after_pay";

    /**
     * 订单行金额与积分为空.
     */
    public static final String MSG_ERROR_OM_CALCULATE_PRICE = "msg.error.order.calculate_price";

    /**
     * 订单行数量为空.
     */
    public static final String MSG_ERROR_OM_CALCULATE_QUANTITY = "msg.error.order.calculate_quantity";

    /**
     * 订单行为空.
     */
    public static final String MSG_ORDER_OM_CALCULATE_LINE_EMPTY = "msg.error.order.calculate_line_empty";

    /**
     * 该状态订单不允许支付.
     */
    public static final String MSG_ERROR_NOT_ALLOW_PAY = "msg.error.order.status_not_allow_pay";

    /**
     * 该订单状态不允许修改支付信息.
     */
    public static final String MSG_ERROR_NOT_EDIT_PAYMENT = "msg.error.order.status_not_edit_pay";

    /**
     * Ipoint用户支付方式错误.
     */
    public static final String MSG_ERROR_IPOINT_PAY_METHOD = "msg.error.order.ipoint.pay_method";

    /**
     * 退货单-返还优惠计算错误.
     */
    public static final String MSG_ERROR_OM_RETURN_PROMOTION = "msg.error.order.return_promotion";

    /**
     * 退货单-行上返还优惠计算错误.
     */
    public static final String MSG_ERROR_OM_RETURN_PROMOTION_LINE = "msg.error.order.return_promotion_line";

    /**
     * 退货单-积分兑换订单退货类型只能是“退款”.
     */
    public static final String MSG_ERROR_OM_RETURNTYPE_NOT_ALLOW = "msg.warning.dto.salesreturn.returntype.not_allow";

    /**
     * 退货单-退货日期不允许大于当前日期.
     */
    public static final String MSG_ERROR_OM_RETURN_DATE = "msg.error.order.return_date";

    /**
     * 退货单-行上为选择退货商品.
     */
    public static final String MSG_ERROR_OM_RTN_ITEM_NOT_EMPTY = "msg.warning.dto.inventory.stockio.no_item_selected";

    /**
     * 退货单-可退货数量不等于(已发运数量-已退货数量).
     */
    public static final String MSG_ERROR_OM_EMABLED_RETURN_QUANTITY = "msg.error.order.enabled_return_quantity";

    /**
     * 退货单-订单行退货数量大于可退货数量.
     */
    public static final String MSG_ERROR_OM_RETURN_QUANTITY = "msg.warning.dto.salesreturn.returnquantity.not_match";

    /**
     * 退货单-退货商品在退货仓库中未分配.
     */
    public static final String MSG_ERROR_OM_RETURN_ITEM_NO_EXISTS_ORG = "msg.error.order.return_item_no_exists_org";

    /**
     * 退货单-计算库存=无，则“计入库存”必须=否.
     */
    public static final String MSG_ERROR_OM_RETURN_RETURN_INV_FLAG = "msg.error.om.return.return_inv_flag";

    /**
     * 退货单-计入库存不能为空.
     */
    public static final String MSG_ERROR_OM_RETURN_RTN_INV_FLAG_NOT_EMPTY = "msg.error.order.returninvflag_not_empty";

    /**
     * 批次信息不能为空.
     */
    public static final String MSG_ERROR_DM_DELIVERY_LOT_NOT_EMPTY = "msg.error.dm.delivery_lot_can_not_be_empty";

    /**
     * 手工调整金额需大于等于0.
     */
    public static final String MSG_WARNING_RETURN_MANUAL_ADJUSTAMT_LARGER_ZERO = "msg.warning.dto.salesreturn.manualadjustamt.larger_zero";

    /**
     * 退货单- 当退货类型为退款时退款方式必输.
     */
    public static final String MSG_ERROR_OM_REFUND_WAY_NOT_NULL = "msg.error.rm.refund_way_not_null";

    /**
     * 退货单-重复返还人工调整异常.
     */
    public static final String MSG_ERROR_OM_RETURN_ADJUST_FLAG_REPEAT = "msg.error.om.return_adjust_flag_repeat";

    /**
     * 退货单-重复返还运费异常.
     */
    public static final String MSG_ERROR_OM_RETURN_SHIPFEE_FLAG_REPEAT = "msg.error.om.return_shippingfee_flag_repeat";

    /**
     * 退货单-必须返还人工调整.
     */
    public static final String MSG_ERROR_RM_MUST_RETURN_ADJUST_AMT = "msg.error.rm.must_return_adjust_amt";

    /**
     * 退货单-必须返还人工调整和运费.
     */
    public static final String MSG_ERROR_RM_MUST_RETURN_ALL = "msg.error.rm.must_return_shippingfee_and_adjustamt";

    /**
     * 退货单-必须返还运费.
     */
    public static final String MSG_ERROR_RM_MUST_RETURN_SHIPPING_FEE_AMT = "msg.error.rm.must_return_shipping_fee_amt";

    /**
     * 退货单-返还人工调整金额异常.
     */
    public static final String MSG_ERROR_OM_RETURN_ADJUST_AMOUNT_ERROR = "msg.error.om.return_adjust_amount_error";

    /**
     * 退货单-返还运费金额异常.
     */
    public static final String MSG_ERROR_OM_RETURN_SHIPFEE_AMT_ERROR = "msg.error.om.return_shippingfee_amount_error";

    /**
     * 销售订单-奖金区间关闭.
     */
    public static final String MSG_ERROR_OM_BONUS_MONTH_CLOSED = "msg.error.order.bonus_month_closed";

    /**
     * 退货单-积分兑换类型订单只能选择一条SalesPoint退款方式.
     */
    public static final String MSG_ERROR_OM_RETURN_METHOD_NOT_ONLY = "msg.warning.dto.salesreturn.rtnmethod.not_only";

    /**
     * 退款单-非积分兑换时不允许选择SalesPoint退款方式.
     */
    public static final String MSG_ERROR_OM_RETURN_METHOD_NOT_ALLOW = "msg.warning.dto.salesreturn.rtnmethod.not_allow";

    /**
     * 退货单-退货单的退款金额之和不等于“实退款”.
     */
    public static final String MSG_ERROR_OM_RETURN_AMOUNT_NOT_EQUAL = "msg.error.om.return_amount_not_equal";

    /**
     * 退货单-实退款校验出错.
     */
    public static final String MSG_ERROR_OM_RETURN_ACTRULAMT_CHECK_ERROR = "msg.error.om.return.actrulamt_check_error";

    /**
     * 退货单-销售组织是否含税组织参数不存在.
     */
    public static final String MSG_ERROR_OM_SPM_ENABLE_TAX_NOT_EXISTS = "msg.error.order.spm_enable_tax_not_exists";

    /**
     * 退货单-当前退货单状态已发生改变.
     */
    public static final String MSG_WARNING_DTO_SALESRETURN_STATUS_CHANGED = "msg.warning.dto.salesreturn.status_changed";

    /**
     * 订单基础数据与当前订单不匹配.
     */
    public static final String MSG_ERROR_OM_BASIC_CHECK_ERROR = "msg.error.order.basic.check.error";

    /**
     * 最小订单金额.
     */
    public static final String MSG_ERROR_OM_AUTOSHIP_MIN_SUM = "msg.error.order.autoship.min.sum";

    /**
     * 最小pv值.
     */
    public static final String MSG_ERROR_OM_AUTOSHIP_MIN_PV = "msg.error.order.autoship.min.pv";

    /**
     * 订单会员信息与系统不匹配.
     */
    public static final String MSG_ERROR_OM_MEMBER_CHECK_ERROR = "msg.error.order.member.check.error";

    /**
     * 订单会员不在生日月份或者已经有生日订单.
     */
    public static final String MSG_ERROR_OM_MEMBER_HAD_GIFT_ORDER = "msg.error.om.member.had.gift.order";
  
    /**
     * 订单会员不在生日月份或者已经有生日订单.
     */
    public static final String MSG_ERROR_OM_MEMBER_IS_NOT_BIRTHDAY_MONTH= "msg.error.om.member.is.not.birthday.month";
   
    /**
     * 订单支付调整校验出错.
     */
    public static final String MSG_ERROR_OM_ADJUSTMENT_CHECK_ERROR = "msg.error.order.adjustment.check.error";

    /**
     * 订单物流配送校验出错.
     */
    public static final String MSG_ERROR_OM_DELIVERY_CHECK_ERROR = "msg.error.order.delivery.check.error";

    /**
     * 订单行校验出错.
     */
    public static final String MSG_ERROR_OM_LINE_CHECK_ERROR = "msg.error.order.line.check.error";

    /**
     * AUTOSHIPORDER提交验证错误.
     */
    public static final String MSG_ERROR_AUTOSHIPORDER_VALIDATE_ERROR = "msg_error_autoshiporder_validate_error";

    /**
     * 销售订单行-商品出错.
     */
    public static final String MSG_ERROR_OM_LINE_ITEM_ERROR = "msg.error.order.line.item.error";

    /**
     * 销售订单-地址出错.
     */
    public static final String MSG_ERROR_OM_SITES_ERROR = "msg.error.order.sites.error";

    /**
     * 销售订单行-商品最小购买量出错.
     */
    public static final String MSG_ERROR_OM_LINE_MIN_QUANTITY_ERROR = "msg.error.order.line.min.quantity.error";

    /**
     * 自动订货赠品规则-规则代码必须唯一.
     */
    public static final String MSG_ERROR_OM_AUTOSHIPGIFTRULR_CODE_UNIQUE = "msg_error_autoshipgiftrulr_code_unique";

    /**
     * 自动订货赠品规则-规则名称必须唯一.
     */
    public static final String MSG_ERROR_OM_AUTOSHIPGIFTRULR_NAME_UNIQUE = "msg_error_autoshipgiftrulr_name_unique";

    /**
     * autoship-激活或暂停重复出错.
     */
    public static final String MSG_ERROR_AUTOSHIP_DUPLICATE_ERROR = "msg.error.autoship.duplicate.error";

    /**
     * 同一个市场下只能有一个启用的规则.
     */
    public static final String MSG_ERROR_OM_AUTOSHIPGIFTRULR_STATUS_Y_UNIQUE = "msg_error_autoshipgiftrulr_status_y_unique";

    /**
     * 订单实付款金额校验错误.
     */
    public static final String MSG_ERROR_OM_ACTUAL_ERROR = "msg.error.om.actual_payment_amount_error";

    /**
     * ecupon未分配或数量不足.
     */
    public static final String MSG_ERROR_PDM_MEMBER_NOT_ASSIGN_ECUPON = "msg.error.pdm.member_not_assign_ecupon";

    /**
     * 会员信息不完整.
     */
    public static final String MSG_ERROR_MEMBERSHIP_INFORMATION_IS_NOT_COMPLETE = "msg.error.membership_information_is_not_complete";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public CommOrderException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }

}
