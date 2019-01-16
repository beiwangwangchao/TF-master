/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.constant;

import com.lkkhpg.dsis.platform.constant.BaseConstants;

/**
 * 常量.
 * 
 * @author chenjingxiong
 */
public class ProductConstants extends BaseConstants {

    /**
     * 商品、商品包状态 - 已发布状态.
     */
    public static final String PUBLISH_STATUS_PUBLISHED = "Y";

    /**
     * 商品、商品包状态 - 未发布状态.
     */
    public static final String PUBLISH_STATUS_UNPUBLISHED = "N";

    /**
     * 商品分配组织类型 - 分配库存.
     */
    public static final String ASSIGN_TYPE_INV = "INV";

    /**
     * 商品分配组织类型 - 分配销售.
     */
    public static final String ASSIGN_TYPE_SALES = "SALES";

    /**
     * 商品属性 - 商品简介.
     */
    public static final String ATTR_DESCRIPTION = "SPJJ";

    /**
     * 商品属性 - 规格参数.
     */
    public static final String ATTR_SPECIFICATION = "GGCS";

    /**
     * 商品属性 - 售后服务.
     */
    public static final String ATTR_AFTER_SERVICE = "SHFW";

    /**
     * 商品属性 - 使用说明.
     */
    public static final String ATTR_USER_DIRECTIONS = "SYSM";

    /**
     * 商品属性 - 注意事项.
     */
    public static final String ATTR_NOTICE_ITEM = "ZYSX";

    /**
     * 商品属性 - 保存方法.
     */
    public static final String ATTR_SAVE_METHOD = "BCFS";
    
    /**
     * 商品属性 - 剂量.
     */
    public static final String ATTR_DOSE = "DOSE";

    /**
     * 商品可用性 - 店内.
     */
    public static final String FUNCTION_AREA_IN_STORE = "STORE";

    /**
     * 商品可用性 - 经销商web.
     */
    public static final String FUNCTION_AREA_DISTRIBUTOR_WEB = "WEB";

    /**
     * 商品可用性 - 经销商app.
     */
    public static final String FUNCTION_AREA_DISTRIBUTOR_APP = "APP";

    /**
     * 商品可用性 - 自动发货.
     */
    public static final String FUNCTION_AREA_AUTOSHIP = "AUTOS";

    /**
     * 商品可用性 - 传真.
     */
    public static final String FUNCTION_AREA_FAX = "fax";

    /**
     * 商品类型 - 商品包.
     */
    public static final String ITEM_TYPE_PACKAGE = "PACKG";

    /**
     * 商品类型 - 商品.
     */
    public static final String ITEM_TYPE_ITEM = "ITEM";

    /**
     * 价格类型 - 兑换积分.
     */
    public static final String PRICE_TYPE_REDEEM_POINT = "RP";

    /**
     * 价格类型 - 零售价格.
     */
    public static final String PRICE_TYPE_RETAIL_PRICE = "RE";

    /**
     * 价格类型 - 员工价格.
     */
    public static final String PRICE_TYPE_STUFF_PRICE = "STU";

    /**
     * 价格类型 - 员工价格2.
     */
    public static final String PRICE_TYPE_STUFF_PRICE_2 = "STU2";

    /**
     * 价格类型 - 经销商价格.
     */
    public static final String PRICE_TYPE_DISTRI_PRICE = "DIS";

    /**
     * 价格类型 - PV.
     */
    public static final String PRICE_TYPE_PV = "PV";

    /**
     * 价格类型 - VIP.
     */
    public static final String PRICE_TYPE_VIP = "VIP";

    /**
     * 库存属性 - 计算库存.
     */
    public static final String PROPERTY_TYPE_COUNT_STOCK_FLAG = "COUNT_STOCK_FLAG";

    /**
     * 库存属性 - 库存计算商品.
     */
    public static final String PROPERTY_TYPE_COUNT_ITEM_ID = "COUNT_ITEM_ID";

    /**
     * 库存属性 - 数量预警.
     */
    public static final String PROPERTY_TYPE_QUANTITY_ALERT = "QUANTITY_ALERT";

    /**
     * 库存属性 - 失效预警.
     */
    public static final String PROPERTY_TYPE_EXPIRY_ALERT = "EXPIRY_ALERT";

    /**
     * 库存属性 - 最小订购数量.
     */
    public static final String PROPERTY_TYPE_MIN_ORDER_QUANTITY = "MIN_ORDER_QUANTITY";

    /**
     * 库存属性 - 批次控制.
     */
    public static final String PROPERTY_TYPE_LOT_CONTROL_FLAG = "LOT_CONTROL_FLAG";

    /**
     * 库存属性 - 库存计算方式.
     */
    public static final String PROPERTY_TYPE_COUNT_TYPE = "COUNT_TYPE";

    /**
     * 基本常量 - 编码类型.
     */
    public static final String ENCODING_UTF8 = "UTF-8";

    /**
     * 商品计算库存 - 计算此商品.
     */
    public static final String ITEM_COUNT_STOCK_OWN = "O";

    /**
     * 商品计算库存 - 计算其他商品.
     */
    public static final String ITEM_COUNT_STOCK_OTHER = "R";

    /**
     * 图片上传路径分隔符.
     */
    public static final String DOUBLE_SLASH = "//";

    /**
     * 计算库存方式 － 单个.
     */
    public static final String ITEM_COUNT_TYPE_IDV = "IDV";

    /**
     * 计算库存方式 － 商品包.
     */
    public static final String ITEM_COUNT_TYPE_PACKG = "PACKG";
    
}
