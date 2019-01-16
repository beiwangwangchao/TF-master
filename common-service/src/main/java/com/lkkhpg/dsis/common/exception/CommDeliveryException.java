/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 发运统一异常.
 * 
 * @author chenjingxiong
 */
public class CommDeliveryException extends BaseException {

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.delivery";

    /**
     * 发运批次信息不能为空.
     */
    public static final String DELIVERY_LOT_EMPTY = "msg.error.delivery.delivery_lot_can_not_be_empty";

    /**
     * 本次发运数量不能大于挑库数减退回数.
     */
    public static final String MORE_THAN_PICKEDQTY = "msg.error.delivery.can_not_more_than_picked_qty";

    /**
     * 退回数量不允许大于发运数量.
     */
    public static final String MORE_THAN_DELIVERYQTY = "msg.error.delivery.can_not_more_than_delivery_qty";

    /**
     * 发运行退回数量必须等于批次行退回数量之和.
     */
    public static final String MORE_THAN_LL = "msg.error.delivery.picked_qty_dl_not_more_than_ll";

    /**
     * 本次发运数量必须等于批次行发运数量和减退回数量.
     */
    public static final String MUST_EQUAL_DELIVERYQTY = "msg.error.delivery.must_equal_with_the_delivery_qty";

    /**
     * 发运数量不能大于所选批次的库存量.
     */
    public static final String MORE_THAN_ONHAND = "msg.error.delivery.not_allowed_more_than_onhand";

    /**
     * 发运状态为新建或待发运才能提交.
     */
    public static final String STATUS_NOT_MATCH = "msg.error.delivery.status_not_match";

    /**
     * 不允许大于未挑库数量.
     */
    public static final String MORE_THAN_UNPICKED = "msg.error.delivery.not_allowed_more_than_unpicked_qty";

    /**
     * 挑库数量大于库存量.
     */
    public static final String OUT_OF_STOCK = "msg.error.delivery.out_of_stock";

    /**
     * 库存量不足.
     */
    public static final String STOCK_NOT_ENOUGH = "msg.error.stock_not_enough";
    /**
     * 发运日期为空.
     */
    public static final String NULL_TRANSACTION_DATE = "msg.error.delivery.not_allowed_transaction_date_null";

    /**
     * 当前状态不可提交.
     */
    public static final String CURRENT_STATUS_CANNOT_BE_COMMITTED = "msg.error.delivery.current_status_cannot_be_committed";
    /**
     * 当前状态不可提交.
     */
    public static final String CURRENT_STATUS_CANNOT_BE_SAVE = "msg.error.delivery.current_status_cannot_be_save";

    /**
     * 数量必须组成商品包.
     */
    public static final String RETURN_MUST_MAKE_UP_PACKAGE = "msg.error.return_must_make_up_package";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public CommDeliveryException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }

}
