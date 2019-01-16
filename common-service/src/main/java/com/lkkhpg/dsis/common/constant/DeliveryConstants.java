/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.constant;

import com.lkkhpg.dsis.platform.constant.BaseConstants;

/**
 * 发运模块常量.
 * 
 * @author chenjingxiong
 */
public class DeliveryConstants extends BaseConstants {

    /**
     * 发运类型 - 自提.
     */
    public static final String DELIVERY_TYPE_PICK_UP = "PCKUP";

    /**
     * 发运类型 - 物流配送.
     */
    public static final String DELIVERY_TYPE_SHIPPMENT = "SHIPP";

    /**
     * 发运状态 - 新建.
     */
    public static final String DELIVERY_STATUS_NEW = "NEW";

    /**
     * 发运状态 - 待发运.
     */
    public static final String DELIVERY_STATUS_PENDDING = "PDDL";

    /**
     * 发运状态 - 已自提.
     */
    public static final String DELIVERY_STATUS_PCKUP = "PCKUP";

    /**
     * 发运状态 - 已发运.
     */
    public static final String DELIVERY_STATUS_SHIPPED = "SHIPP";

    /**
     * 发运状态 - 已取消.
     */
    public static final String DELIVERY_STATUS_CANCLED = "CANCL";

    /**
     * 物流信息 - 本位币.
     */
    public static final String SHIPPING_TIER_ORG_CURRENCY = "SPM.CURRENCY";

    /**
     * 挑库管理 - 组织类型org.
     */
    public static final String ORG_TYPE = "ORG";

    /**
     * 挑库管理 - 组织类型site.
     */
    public static final String SITE_TYPE = "SITE";

    /**
     * 启用批次标识.
     */
    public static final String LOT_FALG = "Y";

    /**
     * 商品类型-虚拟商品包，不计算库存.
     */
    public static final String ITEM_TYPE_VN = "VN";
    /**
     * 商品类型-商品包，计算库存.
     */
    public static final String ITEM_TYPE_PKG = "PKG";
    /**
     * 商品类型-商品，计算库存.
     */
    public static final String ITEM_TYPE_ITEM = "ITEM";
    /**
     * 商品类型-虚拟商品包，计算部分库存.
     */
    public static final String ITEM_TYPE_VY = "VY";
    /**
     * 商品类型-虚拟商品，不计算库存.
     */
    public static final String ITEM_TYPE_VI = "VI";

    /**
     * 给不计算库存的商品一个固定库存.
     */
    public static final Long VI_MAX_INV = 999999999L;
}
