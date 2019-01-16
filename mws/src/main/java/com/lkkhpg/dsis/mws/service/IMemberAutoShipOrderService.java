/*
 *
 */
package com.lkkhpg.dsis.mws.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.order.dto.AutoshipOrder;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.mws.dto.AutoShipCriteria;
import com.lkkhpg.dsis.mws.dto.Product;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员自动订单Service接口.
 * 
 * @author gulin
 *
 */
public interface IMemberAutoShipOrderService extends ProxySelf<IMemberAutoShipOrderService> {
    /**
     * 获取当前登录会员以及销售组织区域会员自动订单商品数据.
     * 
     * @param request
     *            统一上下文.
     * @return 返回商品集合.
     */
    Map<String, Object> queryAutoShipProducts(IRequest request);

    /**
     * 去除已添加的商品，查询可添加自动订货商品集合.
     * 
     * @param request
     *            统一上下文.
     * @param criteria
     *            查询条件.
     * @return 返回可添加商品集合.
     */
    List<Product> queryOptionalProducts(IRequest request, AutoShipCriteria criteria);

    /**
     * 更新当前用户自动订货商品列表.
     * 
     * @param request
     *            统一上下文.
     * @param products
     *            最新自动订货商品信息.
     * @param autoshipOrder
     *            订单地址信息及配送信息.
     * @return 更新结果信息
     * @throws CommOrderException
     *             自动订单地址插入异常
     */
    List<String> updateAutoShipLine(IRequest request, List<Product> products, @StdWho AutoshipOrder autoshipOrder)
            throws CommOrderException;

    /**
     * 查询地址信息.
     * 
     * @param request
     *            统一上下文.
     * @param list
     *            地址信息集合.
     * @return 返回详细信息map.
     * @throws JsonProcessingException
     *             抛出异常.
     */
    Map<String, Object> querySites(IRequest request, List<SalesSites> list) throws JsonProcessingException;

    /**
     * 查询当前会员自动订货单的商品信息.
     * 
     * @param request
     *            统一上下文.
     * @return 返回结果.
     */
    List<Product> getProduct(IRequest request);

    /**
     * 查询当前会员自动订货单所有地址信息.
     * 
     * @param requerst
     *            统一上下文.
     * @return 返回结果.
     */
    List<MemSite> getAllSites(IRequest request);
}
