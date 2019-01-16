/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.constant;

import com.lkkhpg.dsis.platform.constant.BaseConstants;

/**
 * @author runbai.chen
 */
public class PaymentConstants extends BaseConstants {
    /**
     * 支付过程类型.
     */
    public static final String TRANS_TYPE_PAY = "PAY";
    public static final String TRANS_TYPE_RESUL = "RESUL";
    /**
     * 状态类型.
     */
    public static final String TRANS_STATUS_START = "START";
    public static final String TRANS_STATUS_DRAFT = "DRAFT";
    public static final String TRANS_STATUS_COMPL = "COMPL";

    public static final String TRANS_STATUS_PROCE = "PROCE";
    public static final String TRANS_STATUS_FAIL = "FAIL";

    public static final String SOURCE_TYPE_ORDER = "ORDER";
    public static final String PAYMENT_METHOD_ONLPA = "ONLPA";

    public static final String ORDER_STATUS_PAYIN = "PAYIN";

    public static final String PROCESS_RESULT_SUCCESS = "success";
    public static final String PAYMENT_TYPE_MYHOL = "MYHOL";
    public static final String PAYMENT_TYPE_TWNON = "TWNON";
    public static final String PAYMENT_TYPE_TWUNN = "TWUNN";

    public static final String SALES_ORDER_PAYMENT = "SALES_ORDER_PAYMENT";

}
