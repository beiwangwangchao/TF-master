/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.payment.constant;

import org.springframework.jca.work.SimpleTaskWorkManager;

/**
 * 付款相关常量类.
 * 
 * @author mclin
 */
public class PaymentConstant {
    
    /**
     * 付款类型 - MIGS.
     */
    public static final String PAYMENT_CFG_TYPE_MIGS = "MIGS";
    
    /**
     * 付款类型 - 银联.
     */
    public static final String PAYMENT_CFG_TYPE_UNION = "UNION";
    
    /**
     * 付款类型 - 非银联.
     */
    public static final String PAYMENT_CFG_TYPE_NONUNION = "NONUNION";

    /**
     * 天府银行聚合支付配置文件
     */
    public static final  String TF_PAYMENT_TYPE_URL="TFPAY";

    public static final String TF_DOWNLOAD_ACCOUNT_TYPE_URL="TFACCOUNT";
}
