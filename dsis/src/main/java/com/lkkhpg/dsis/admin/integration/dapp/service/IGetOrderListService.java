/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetOrderListRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetOrderListResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.Product;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 查询订单列表接口Service.
 * 
 * @author shenqb
 *
 */
public interface IGetOrderListService extends ProxySelf<IGetOrderListService> {

    /**
     * 查询订单列表.
     * 
     * @param getOrderListRequest
     *            查询订单列表request
     * @param pageNo 页码
     * @param axPerpage 页行数
     * @return 订单列表DTO
     * @throws DAppException
     *             dapp异常
     */
    List<GetOrderListResponse> getOrderList(GetOrderListRequest getOrderListRequest, Integer pageNo, Integer axPerpage) throws DAppException;
    
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

}
