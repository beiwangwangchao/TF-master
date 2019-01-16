/*
 *
 */
package com.lkkhpg.dsis.mws.constant;

import com.lkkhpg.dsis.platform.constant.BaseConstants;

/**
 * 商品模块常量.
 * 
 * @author xiawang.liu
 */
public class ProductConstants extends BaseConstants {

    /**
     * 默认分页第1页.
     */
    public static final String PAGE = "1";
    
    /**
     * 默认分页大小.
     */
    public static final String PAGESIZE = "12";
    
    /**
     * salesOrgId.
     */
    public static final String SALESORGID = "salesOrgId";
    
    /**
     * 查询币种类型参数SPM.CURRENCY.
     */
    public static final String CURRENCY_CODE_PARAM = "SPM.CURRENCY";
    
    /**
     * 查询税率参数SPM.TAX_CODE.
     */
    public static final String TAX_CODE_PARAM = "SPM.TAX_CODE";
    
    /**
     * 查询产品图片参数PACKAGE_IMAGE.
     */
    public static final String PACKAGE_IMAGE_PARAM = "PACKAGE_IMAGE";
    
    /**
     * 查询是否启用税参数SPM.ENABLE_TAX.
     */
    public static final String ENABLE_TAXE_PARAM = "SPM.ENABLE_TAX";
    
    /**
     * 查询是否含税价格参数SPM.PRICE_INCLUDE_TAX.
     */
    public static final String PRICE_INCLUDE_TAX_PARAM = "SPM.PRICE_INCLUDE_TAX";
    
    /**
     * 市场Id-_marketId.
     */
    public static final String MARKET_ID = "_marketId";
    
    /**
     * 类型ID与类型名连接符"-".
     */
    public static final String CATEGORY_PARAM = "-";
    
    /**
     * 类型名之间分隔符"/".
     */
    public static final String CATEGORYNAME_PARAM = "/";
    
    /**
     * 为空时，默认URL"#".
     */
    public static final String DEFAULT_URL = "#";
    
    /**
     * 下标-0.
     */
    public static final int INDEX = 0;
    
    /**
     * 为空时，默认fileId-0000.
     */
    public static final long DEFAULT_FILEID = 0000;
    
    /**
     * 税率百分比转化数字-除以100.
     */
    public static final int TAX_PERCENT = 100;
    
    /**
     * 查询属于WEB有效的商品WEB.
     */
    public static final String WEB_STYLE = "WEB";
    
    /**
     * 购物车中商品数量最大值.
     */
    public static final int SHOPCART_QUANTITY_MAX = 1000;
    
    /**
     * 购物车查询条目最大值.
     */
    public static final int SHOPCART_QUERY_COUNT = 500;
    
    /**
     * 购物车商品确认flag-Y.
     */
    public static final String SHOPCART_CONFIRM_Y = "Y";
    
    /**
     * 购物车商品确认flag-N.
     */
    public static final String SHOPCART_CONFIRM_N = "N";

}
