/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.product.dto.InvCategoryB;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.dto.InvItemPropertyV;
import com.lkkhpg.dsis.common.product.dto.OrderItemPrice;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 商品Common Service.
 * 
 * @author chenjingxiong
 */
public interface ICommItemService extends ProxySelf<ICommItemService> {

    /**
     * 条件查询.
     * 
     * @param request
     *            统一上下文.
     * @param invCategoryB
     *            查询条件.
     * @return 类别列表.
     */
    List<InvCategoryB> queryCategory(IRequest request, InvCategoryB invCategoryB);

    /**
     * 根据itemId查询商品包.
     * 
     * @param request
     *            统一上下文
     * @param itemId
     *            商品Id
     * @return 商品记录
     */
    InvItem queryItemDetails(IRequest request, Long itemId);

    /**
     * 
     * 查询商品.
     * 
     * @param request
     *            统一上下文
     * @param item
     *            查询条件
     * @param page
     *            页码
     * @param pageSize
     *            每页记录数
     * @return 商品列表
     */
    List<InvItem> queryItems(IRequest request, InvItem item, int page, int pageSize);
    
    /**
     * 查询商品，不限制市场，供订单使用.
     * 
     * @param request
     *            统一上下文
     * @param item
     *            查询条件
     * @param page
     *            页码
     * @param pageSize
     *            每页记录数
     * @return 商品列表
     */
    List<InvItem> queryItemsForOrder(IRequest request, InvItem item, int page, int pageSize);

    /**
     * 单位页面中商品信息查询.
     * 
     * @param request 统一上下文
     * @param invItem 查询条件
     * @param page 页码
     * @param pageSize 每页显示记录数
     * @return 单位转换中包含的商品列表
     */
    List<InvItem> queryItemsOfUnitConvert(IRequest request, InvItem invItem, int page, int pageSize);
    
    /**
     * 获取商品已经商品属性.
     * @param request 统一上下文
     * @param itemId 商品ID
     * @param invOrgId 库存组织ID
     * @return 商品属性
     */
    InvItemPropertyV getItemProperties(IRequest request, Long itemId, Long invOrgId);



    /**
     * 查询商品价格
     *
     * @param request
     *            请求上下文
     * @param itemId
     *            商品ID
     * @param currency
     *            货币
     * @param uomCode
     *            单位
     * @param salesOrgId
     *            销售组织ID
     * @param orderType
     *            订单类型
     * @param itemSalestype
     *            销售类型
     * @return 订单使用的商品价格
     */
    OrderItemPrice queryItemPriceForOrder(IRequest request, Long itemId, String currency, String uomCode,
                                          Long salesOrgId, String orderType, String itemSalestype);

}
