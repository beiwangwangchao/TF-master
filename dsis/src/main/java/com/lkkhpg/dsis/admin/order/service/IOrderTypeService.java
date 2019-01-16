/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service;

import com.lkkhpg.dsis.common.order.dto.OrderType;
import com.lkkhpg.dsis.common.order.dto.SalesType;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

import java.util.List;
import java.util.Map;

/**
 * 订单类型维护Service.
 * 
 * @author zhiwei.zhang@hand-china.com [#PE20-4][ADD],2016-12-06 14:20:03.
 */
public interface IOrderTypeService extends ProxySelf<IOrderTypeService> {

    /**
     * 批量修改或新增订单类型.
     * 
     * @param requestContext
     * @param orderTypes
     * @return List<OrderType>
     */
    List<OrderType> batchUpdate(IRequest requestContext, List<OrderType> orderTypes);

    /**
     * 更新订单类型.
     * 
     * @param orderType
     * @return OrderType
     */
    OrderType updateOrderType(OrderType orderType);

    /**
     * 新增订单类型.
     * 
     * @param orderType
     * @return OrderType
     */
    OrderType createOrderType(OrderType orderType);

    /**
     * 根据当前用户查询订单类型.
     * 
     * @param createRequestContext
     * @param orderType
     * @return List<OrderType>
     */
    List<OrderType> queryByOrderType(IRequest createRequestContext, OrderType orderType, int page, int pagesize);

    /**
     * 批量删除订单类型
     * 
     * @param requestContext
     * @param orderTypes
     */
    void batchDelete(IRequest requestContext, List<OrderType> orderTypes);

    /**
     * 根据销售组织、订单类型、用户类型查询 销售类型
     * 
     * @param request
     *            请求上下文
     * @param salesOrgId
     *            销售组织
     * @param orderType
     *            订单类型
     * @param userType
     *            用户类型
     * @return 销售类型list
     */
    List<SalesType> querySalesTypeForOrder(IRequest request, Long salesOrgId, String orderType, String userType);

    /**
     * 根据当前用户查询所有订单类型
     * 
     * @param requestContext
     *            请求上下文
     * @return 订单类型list
     */
    List<OrderType> queryAll(IRequest requestContext);

    /**
     * 通过销售组织查询订单类型.
     * 
     * @param requestContext
     *            请求上下文
     * @param salesOrgId
     *            销售组织ID
     * @return 订单类型列表
     */
    List<Map> queryBySalesOrgId(IRequest requestContext, Long salesOrgId);

}
