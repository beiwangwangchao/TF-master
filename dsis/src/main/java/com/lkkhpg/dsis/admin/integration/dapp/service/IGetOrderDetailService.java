/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetOrderDetailRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetOrderDetailsBase;
import com.lkkhpg.dsis.admin.integration.dapp.dto.Product;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 查询订单详情接口Service.
 * 
 * @author shenqb
 *
 */
public interface IGetOrderDetailService extends ProxySelf<IGetOrderDetailService> {

    /**
     * 查询订单详情.
     * 
     * @param getOrderDetailRequest
     *            查询订单详情request
     * @return 订单详情DTO
     * @throws DAppException
     *             dapp异常
     */
    GetOrderDetailsBase getOrderDetail(GetOrderDetailRequest getOrderDetailRequest) throws DAppException;

    /**
     * dapp-根据订单头构造商品列表.
     * 
     * @param iRequest
     *            请求上下文
     * @param salesOrder
     *            销售订单头DTO
     * @return 商品列表
     */
    List<Product> getProducts(IRequest iRequest, SalesOrder salesOrder);

    /**
     * 获取订单头的发运状态.
     * 
     * @param salesOrder
     *            销售订单
     * @return 订单头发运状态
     */
    String getShippingStatus(SalesOrder salesOrder);

}
