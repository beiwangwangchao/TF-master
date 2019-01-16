/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.delivery.exception;

import com.lkkhpg.dsis.common.exception.CommDeliveryException;

/**
 * 发运统一异常.
 * 
 * @author chenjingxiong
 */
public class DeliveryException extends CommDeliveryException {

    public static final String SHIPPING_TIER_SEG_EXIST = "msg.error.delivery.already_exist_the_same_shipping_tier_seg";

    public static final String SHIPPING_TIER_EXIST = "msg.error.delivery.already_exist_the_same_shipping_tier";

    public static final String SHIPPING_TIER_DTL_EXIST = "msg.error.delivery.already_exist_the_same_shipping_dtl";

    public static final String SHIPPING_TIER_ORG_CURRENCY_NOTEXIST = "msg.error.delivery.shipping_tier_org_currency_notexist";

    public static final String SHIPPING_TIER_AMOUNTFROM_BIGGER_AMOUNTTO = "msg.error.delivery.invamountfrom_invamountto";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public DeliveryException(String descriptionKey, Object[] parameters) {
        super(descriptionKey, parameters);
    }

}
