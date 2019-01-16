/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.exception;

import com.lkkhpg.dsis.common.exception.CommOrderException;

/**
 * 订单统一异常.
 * 
 * @author chenjingxiong
 */
public class OrderException extends CommOrderException {

    /**
     * 销售订单-市场或组织信息缺失.
     */
    public static final String MSG_ERROR_OM_ORG_MISS = "msg.error.order.org_miss";

    /**
     * 销售订单-订单状态比较出错.
     */
    public static final String MSG_ERROR_OM_STATUS_COMPARE_ERROR = "msg.error.order.status.compare.error";

    /**
     * 销售订单-订单日期比较出错.
     */
    public static final String MSG_ERROR_OM_DATE_COMPARE_ERROR = "msg.error.order.date.compare.error";

    /**
     * 销售订单-权限校验出错.
     */
    public static final String MSG_ERROR_OM_ACCESS_ERROR = "msg.error.order.access.error";

    /**
     * 销售订单-可用奖金区间出错.
     */
    public static final String MSG_ERROR_OM_AVAILABLE_PERIOD_ERROR = "msg.error.order.available.period.error";

    /**
     * 销售订单-修改奖金月份的时间出错.
     */
    public static final String MSG_ERROR_OM_BONUS_MONTH_DATE_ERROR = "msg.error.order.bonus.month.date.error";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public OrderException(String descriptionKey, Object[] parameters) {
        super(descriptionKey, parameters);
    }

}
