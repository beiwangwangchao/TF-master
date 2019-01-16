/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.exception;

import com.lkkhpg.dsis.common.exception.CommInventoryException;

/**
 * 库存统一异常.
 * 
 * @author chenjingxiong
 */
public class InventoryException extends CommInventoryException {

    public static final String MSG_ERROR_EXPIRYDATE_REQUIRED = "msg_error_expirydate_required";

    public static final String SHIPPING_EXIST = "msg.error.delivery.already_exist_the_same_record";

    public static final String MSG_ERROR_QUANTITY_NOT_EQUAL = "msg_error_quantity_not_euqal";

    public static final String MSG_ERROR_HAVE_LOT_WRONG_EXPIRYDATE = "msg_error_have_lot_wrong_expirydate";

    public static final String MSG_ERROR_EXPIRYDATE_MUST_NULL = "msg_error_expirydate_must_null";

    public static final String MSG_ERROR_STOCKIO_ONHAND_WRONG = "msg_error_stockio_onhand_wrong";

    public static final String MSG_ERROR_TrxQty_CAN_NOT_BE_ZERO = "msg_error_trxqty_can_not_be_zero";

    public static final String MSG_ERROR_AMOUNT_CAN_NOT_BE_ZERO = "msg_error_amount_can_not_be_zero";

    public static final String MSG_ERROR_TRANSACTION_REPEAT = "msg_error_transaction_repeat";

    public static final String MSG_ERROR_VENDOR_INUSE = "msg_error_vendor_inuse";

    /**
     * 供应商编码不能为空.
     */
    public static final String MSG_ERROR_PO_VENDOR_CODE_EMPTY = "msg.error.po_vendor_code_empty";
    /**
     * 供应商名称必须唯一.
     */
    public static final String MSG_ERROR_PO_VENDOR_NAME_UNIQUE = "msg.error.po_vendor_name_unique";
    /**
     * 供应商状态不能为空.
     */
    public static final String MSG_ERROR_PO_VENDOR_STATUS_EMPTY = "msg.error.po_vendor_status_empty";
    /**
     * 转入数量大于剩余转入数量.
     */
    public static final String MSG_ERROR_TRF_ENTER_NUM_BIGGER_THAN_REMAINING_NUM = "msg.error.trf_enter_num_bigger_than_remaining_num";
    /**
     * 该月份已生成记录.
     */
    public static final String MSG_ERROR_INV_THIS_MONTH_HAS_GENERATED_RECORD = "msg.error.inv_this_month_has_generated_record";
    /**
     * 未获取成本信息.
     */
    public static final String MSG_ERROR_INV_NO_GENERATED_COST_DETAIL = "msg.error.inv_no_generated_cost_detail";
    /**
     * 成本获取错误.
     */
    public static final String MSG_ERROR_INV_COST_GET_ERROR = "msg.error.inv.cost_get_error";
    /**
     * 该月份有成本为空的数据.
     */
    public static final String MSG_ERROR_THIS_MONTH_HAS_COST_NULL = "msg_error_this_month_has_cost_null";
    /**
     * 不是最新的成本记录表最新的年份和月份.
     */
    public static final String MSG_ERROR_INV_ONLY_CANCLE_LATEST_COST_RECORDS = "msg.error.inv_only_cancle_latest_cost_records";
    /**
     * 批次库存不足.
     */
    public static final String MSG_ERROR_INV_BATCH_INVENTORY_SHORTAGE = "msg.error.item_batch_inventory_shortage";
    /**
     * 库存不足.
     */
    public static final String MSG_ERROR_INV_INVENTORY_SHORTAGE = "msg.error.item_inventory_shortage";
    /**
     * 批次号失效.
     */
    public static final String MSG_ERROR_LOT_NUMBER_NOT_ENABLED = "msg.error.lot_number_not_enabled";
    /**
     * 发运库存事务不能为空.
     */
    public static final String MSG_ERROR_INV_TRANSACTION_CAN_NOT_NULL = "msg.error.inv_transcation_can_not_null";
    /**
     * 当前库存组织不是库存归集中心.
     */
    public static final String MSG_ERROR_INV_COST_NOT_COST_ORG_ID = "msg.error.inv.cost.not_cost_org_id";
    /**
     * 该退货单已入库
     */
    public static final String MSG_ERROR_OUT_REFUND_NO = "msg.error.out.refund.no";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public InventoryException(String descriptionKey, Object[] parameters) {
        super(descriptionKey, parameters);
    }
}
