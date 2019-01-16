/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.mws.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lkkhpg.dsis.mws.dto.Product;
import com.lkkhpg.dsis.mws.dto.ShopCartItem;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 购物车service.
 * 
 * @author gulin
 *
 */
public interface IShopCartService extends ProxySelf<IShopCartService> {
    /**
     * 将选择的商品信息添加到用户购物车中.
     * 
     * @param shopCartItem
     *            添加信息.
     * @param request
     *            统一上下文.
     * @throws MemberException
     *             抛出添加失败异常.
     */
    void insertShopCartItem(ShopCartItem shopCartItem, IRequest request) throws MemberException;

    /**
     * 从购物车中删除选中信息.
     * 
     * @param shopCartItems
     *            删除的信息.
     * @param request
     *            统一上下文.
     * @throws MemberException
     *             抛出添加失败异常.
     */
    void deleteShopCartItems(List<ShopCartItem> shopCartItems, IRequest request) throws MemberException;

    /**
     * 更新购物车信息（主要是数量）.
     * 
     * @param shopCartItem
     *            更新的商品信息.
     * @param request
     *            统一上下文.
     * @throws MemberException
     *             抛出添加失败异常.
     */
    void updateShopCartItem(ShopCartItem shopCartItem, IRequest request) throws MemberException;

    /**
     * 查询当前登录会员选择销售区域的购物车明细(若flag为Y,则返回确认的商品).
     * 
     * @param irequest
     *            IRequest.
     * @param flag
     *            是否确认标识.
     * @return 购物车明细集合.
     */
    List<Product> queryShopCartItem(IRequest irequest, String flag);

    /**
     * 购物车选择商品后确认订单操作.
     * 
     * @param confirmList
     *            确认的商品明细集合.
     * @param irequest
     *            IRequest.
     * @param request
     *            获取session
     * @return 返回确认结果信息.
     */
    List<String> confirmShopCartItem(List<ShopCartItem> confirmList, IRequest irequest, HttpServletRequest request);

    /**
     * 订单确认界面取消确认，确认商品返回购物车.
     * 
     * @param request
     *            统一上下文.
     */
    void rollbackhopCartItem(IRequest request);

    /**
     * 获取当前购物车中商品条目数.
     * 
     * @param memberId
     *            会员ID.
     * @param salesOrgId
     *            销售区域ID.
     * @return 商品条目数
     */
    Integer getShopCartCount(Long memberId, Long salesOrgId);

    /**
     * 订单确认后删除购物车中确认商品.
     * 
     * @param request
     *            统一上下文.
     * @throws MemberException
     *             删除异常.
     */
    void deleteConfirmItems(IRequest request) throws MemberException;

    /**
     * 根据商品id，商品数量检验最小购买数.
     * 
     * @param request
     *            统一上下文.
     * @param products
     *            商品集合.
     * @return 返回验证结果.
     */
    List<String> checkMinQuantity(IRequest request, List<ShopCartItem> products);
}
