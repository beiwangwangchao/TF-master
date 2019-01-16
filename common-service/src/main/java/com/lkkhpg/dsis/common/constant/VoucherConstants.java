/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.constant;

import com.lkkhpg.dsis.platform.constant.BaseConstants;

/**
 * 优惠券模块常量.
 * 
 * @author houmin
 *
 */
public class VoucherConstants extends BaseConstants {

    /**
     * 优惠券事务处理类型.
     */
    public static final String TRX_TYPE = "PDM.TRANSACTION_TYPE";

    /**
     * 优惠券事务处理来源类型.
     */
    public static final String TRX_SOURCE_TYPE = "PDM.TRANSACTION_SOURCE_TYPE";

    /**
     * 优惠券事务处理类型-使用.
     */
    public static final String TRX_TYPE_APPLY = "APPLY";

    /**
     * 优惠券事务处理类型-退回.
     */
    public static final String TRX_TYPE_BACK = "BACK";

    /**
     * 优惠券事务来源类型-订单(头).
     */
    public static final String TRX_SOURCE_TYPE_ORDER_HEAD = "OM_SALES_ORDER";

    /**
     * 优惠券事务来源类型-订单(行).
     */
    public static final String TRX_SOURCE_TYPE_ORDER_LINE = "OM_SALES_LINE";

    /**
     * 优惠券分配类型-会员.
     */
    public static final String VOUCHER_ASSIGN_TYPE_MEM = "MEM";

    /**
     * 优惠券分配类型-销售组织.
     */
    public static final String VOUCHER_ASSIGN_TYPE_SALES = "SALES";

    /**
     * 优惠券使用方式-可叠加.
     */
    public static final String PDM_USAGE_MODE_STACK = "STACK";

    /**
     * 优惠券使用方式-不可叠加.
     */
    public static final String PDM_USAGE_MODE_NSTAC = "NSTAC";

    /**
     * 优惠券-含税.
     */
    public static final String PDM_APPLY_ON_ICLD = "ICLD";

    /**
     * 优惠券-不含税.
     */
    public static final String PDM_APPLY_ON_ECLD = "ECLD";

    /**
     * 优惠券优惠层次-订单.
     */
    public static final String PDM_APPLY_CRITERIA_INVOI = "INVOI";

    /**
     * 优惠券优惠层级-商品.
     */
    public static final String PDM_APPLY_CRITERIA_PRODU = "PRODU";

    /**
     * 优惠券类型-金额.
     */
    public static final String PDM_VOUCHER_TYPE_AM = "AM";

    /**
     * 优惠券类型-数量.
     */
    public static final String PDM_VOUCHER_TYPE_NM = "NM";

    /**
     * 优惠券类型-商品.
     */
    public static final String PDM_VOUCHER_TYPE_PD = "PD";

    /**
     * 优惠券折扣-固定金额.
     */
    public static final String PDM_DISCOUNT_TYPE_FIX = "FIX";

    /**
     * 优惠券折扣类型-折扣.
     */
    public static final String PDM_DISCOUNT_TYPE_PERCE = "PERCE";

    /**
     * 优惠券折扣类型-赠品.
     */
    public static final String PDM_DISCOUNT_TYPE_GIFT = "GIFT";

    /**
     * 运算-大于.
     */
    public static final String PDM_CALCULATE_OPERATOR_MT = "MT";

    /**
     * 运算-大于等于.
     */
    public static final String PDM_CALCULATE_OPERATOR_MOE = "MOE";

    /**
     * 运算-小于.
     */
    public static final String PDM_CALCULATE_OPERATOR_LT = "LT";

    /**
     * 运算-小于等于.
     */
    public static final String PDM_CALCULATE_OPERATOR_LOE = "LOE";

    /**
     * 百分比转换.
     */
    public static final int PERCENT_CONVERT = 100;

}
