/*
 *
 */
package com.lkkhpg.dsis.admin.order.service;

import java.util.List;

import com.lkkhpg.dsis.common.order.dto.QueryOrder;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 订单查询service接口.
 *
 * @author gulin
 */
public interface IOrderQueryService extends ProxySelf<IOrderQueryService> {

    /**
     * 查询销售订单.
     * 
     * @param iRequest
     *            统一上下文.
     * @param queryOrder
     *            查询条件dto.
     * @param page
     *            页码.
     * @param pageSize
     *            页面size.
     * @return 订单查询结果集合.
     */
    List<QueryOrder> selectOrders(IRequest iRequest, QueryOrder queryOrder, int page, int pageSize);

    /**
     * 保存审核状态
     * @param request
     * @param queryOrders
     * @return
     */
    List<QueryOrder> batchUpdate(IRequest request, @StdWho List<QueryOrder> queryOrders);

    /**
     * 批量更新数据
     * @param attribute15,orderNumber
     * @return
     */
//    boolean updateQueryOrder(String attribute15, String orderNumber);

}
