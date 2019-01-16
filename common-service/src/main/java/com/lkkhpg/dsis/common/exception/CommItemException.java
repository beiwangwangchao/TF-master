/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 商品统一异常.
 * 
 * @author chenjingxiong
 */
public class CommItemException extends BaseException {

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.product";

    /**
     * 发布时间不在商品有效期内.
     */
    public static final String BEYOND_PUBLISH_TIME_EXCEPTION = "msg.error.product.publish_out_of_time";

    /**
     * 有效日期至大于有效日期从.
     */
    public static final String END_DATE_EXCEED_START_DATE_EXCEPTION 
    = "msg.error.product.end_date_exceed_start_date_exception";

    /**
     * 商品有效日期未覆盖价格行有效日期.
     */
    public static final String BEYOND_PRICE_TIME_EXCEPTION = "msg.error.product.price_date_not_equal_item_date";

    /**
     * 价格有效日期不能重复.
     */
    public static final String REPEAT_PRICE_TIME_EXCEPTION = "msg.error.product.price_time_repeat";

    /**
     * 库存组织重复.
     */
    public static final String REPEAT_ASSIGN_ITEM_EXCEPTION = "msg.error.product.item_assign_repeat";

    /**
     * 包含商品重复.
     */
    public static final String REPEAT_INCLUDE_ITEM_EXCEPTION = "msg.error.product.include_item_repeat";

    /**
     * 价格类型不全.
     */
    public static final String PRICE_TYPE_NOT_ENTIRE_EXCEPTION = "msg.error.product.price_type_not_entire";

    /**
     * 价格类型错误.
     */
    public static final String PRICE_TYPE_NOT_ALLOW_EXCEPTION = "msg.error.product.price_type_not_allow_redeem_point";

    /**
     * 计算库存商品id为空.
     */
    public static final String COUNT_ITEM_ID_NULL_EXCEPTION = "msg.error.product.inv.count_itemid_is_null";

    /**
     * 计算库存方式为空.
     */
    public static final String COUNT_STOCK_TYPE_NULL_EXCEPTION = "msg.error.product.inv.count_stock_type_is_null";

    /**
     * 包含商品列表为空.
     */
    public static final String PACKAGE_INCLUDE_NULL_EXCEPTION = "msg.error.product.inv.package_include_null_exception";

    /**
     * 商品多语言重复.
     */
    public static final String REPEAT_ITEM_LANGUAGE_EXCEPTION = "msg.error.product.inv.item_language_is_repeat";

    /**
     * 商品参数多语言重复.
     */
    public static final String REPEAT_ITEM_ATTRS_LANGUAGE_EXCEPTION 
    = "msg.error.product.inv.item_attrs_language_is_repeat";

    /**
     * 商品参数操作未维护.
     */
    public static final String REPEAT_ITEM_ATTRS_PARAMETERS_NULL
            = "msg.error.product.inv.item_attrs_parameters_is_null";

    /**
     * 库存不包含所有商品.
     */
    public static final String INV_SALE_ORG_IS_NOT_INCLUDE_ITEMS_EXCEPTION 
    = "msg.error.product.inv.inv_sale_org_is_not_include_items";

    /**
     * 商品不属于对应库存.
     */
    public static final String INCLUDE_ITEMS_IS_NOT_BELONG_TO_ORG_EXCEPTION 
    = "msg.error.product.inv.include_items_is_not_belong_to_org";
    
    /**
     * 替代计算库存的商品未在所有当前商品所分配的库存组织中启用.
     */
    public static final String COUNT_ITEMS_IS_NOT_ENABLE_TO_ORG_EXCEPTION 
    = "msg.error.product.inv.count_items_is_not_enable_to_org";
    
    /**
     * 商品包的包含商品未在所有当前商品所分配的库存组织中启用.
     */
    public static final String INCLUDE_ITEMS_IS_NOT_ENABLE_TO_ORG_EXCEPTION 
    = "msg.error.product.inv.include_items_is_not_enable_to_org";

    /**
     * 对应价格类型币种不能有值.
     */
    public static final String CURRENCY_IS_NOT_MATCH_PRICE_TYPE_EXCEPTION 
    = "msg.error.product.inv.currency_is_not_match_price_type";

    /**
     * 商品可兑换时，兑换积分价格不能为空.
     */
    public static final String REDEEM_POINT_PRICE_IS_NULL_EXCEPTION 
    = "msg.error.product.inv.redeem_point_price_is_null";

    /**
     * 商品不可兑换时，兑换积分价格不能有值.
     */
    public static final String REDEEM_POINT_PRICE_IS_NOT_NULL_EXCEPTION 
    = "msg.error.product.inv.redeem_point_price_is_not_null";

    /**
     * 同一市场的价格行中，pv值必须相同.
     */
    public static final String PV_PRICE_NOT_EQUAL_IN_SAME_MARKET 
    = "msg.error.product.inv.pv_price_not_equal_in_same_market";

    /**
     * 商品没有价格数据.
     */
    public static final String ITEM_HAS_NO_PRICE_INFO = "msg.error.product.inv.item_has_no_price_info";

    /**
     * 商品没有库存组织.
     */
    public static final String ITEM_HAS_NO_ASSIGN_INFO = "msg.error.product.inv.item_has_no_assign_info";

    /**
     * 价格不能有负值.
     */
    public static final String UNIT_PRICE_IS_NEGATIVE_VALUE_EXCEPTION 
    = "msg.error.product.inv.unit_price_is_negative_value";

    /**
     * 商品编码有重复.
     */
    public static final String REPEAT_ITEM_NUMBER_EXCEPTION = "msg.error.product.item_number_repeat";

    /**
     * 图片格式错误.
     */
    public static final String IMAGE_FORMAT_ERROR_EXCEPTION = "msg.error.product.image_format_error";

    /**
     * 节点已经分配了商品.
     */
    public static final String SUBCATEGORY_FAIL_EXCEPTION = "msg.error.product.subcategory_fail";

    /**
     * 商品类别删除失败.
     */
    public static final String MSG_ERROR_PM_DELETE_FAIL = "msg.error.product.delete_fail";

    /**
     * 商品有效期从要小于商品有效期至.
     */
    public static final String ITEM_VALIDATE_DATE_ERROR = "msg.error.product.item_validate_date_error";

    /**
     * 数量预警不能为空.
     */
    public static final String ITEM_QUANTITY_ALERT_IS_NULL = "msg.error.product.item_quantity_alert_is_null";

    /**
     * 已在该分类下分配了该商品.
     */
    public static final String PRODUCT_CANT_ASSIGN = "msg.error.product_can_not_assign";

    /**
     * 商品已经发布.
     */
    public static final String PRODUCT_PUBLISH_REPEAT = "msg.error.product_publish_repeat";

    /**
     * 商品已经撤销发布.
     */
    public static final String PRODUCT_UNPUBLISH_REPEAT = "msg.error.product_unpublish_repeat";

    /**
     * 商品未保存，不能发布.
     */
    public static final String PRODUCT_PUBLISH_NOT_SAVE = "msg.error.product_publish_not_save";

    /**
     * 商品发布时，商品可用性里web和app至少勾选一个.
     */
    public static final String PRODUCT_PUBLISH_AVALIABILITY = "msg.error.product_publish_avaliability";
    
    /**
     * 商品图片压缩异常.
     */
    public static final String PRODUCT_IMAGE_COMPRESS_ERROR = "msg.error.product_image_compress_error";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public CommItemException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }

}
