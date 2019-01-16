/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderPaymentUpdate;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderPaymentUpdateResponse;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 订单支付接口类.
 * 
 * @author zhenyang.he
 *
 */
public interface IOrderPaymentUpdateService extends ProxySelf<IOrderPaymentUpdateService> {
    /**
     * 更新订单状态.
     * 
     * @param orderPaymentUpdate
     *            訂單支付接口DTO.
     * 
     * @return 响应数据.
     * 
     * @throws DAppException
     *             異常接口.
     * 
     */
    OrderPaymentUpdateResponse updatePaymentStatus(OrderPaymentUpdate orderPaymentUpdate) throws DAppException;

    /**
     * 如果订单已完成，则调用comm service生成发运单.
     * 
     * @param iRequest
     *            请求上下文
     * @param salesOrder
     *            订单
     * @param orderPaymentUpdateResponse
     *            支付信息响应
     * @return 支付信息响应
     */
    OrderPaymentUpdateResponse createDeliveriesByOrder(IRequest iRequest, SalesOrder salesOrder,
            OrderPaymentUpdateResponse orderPaymentUpdateResponse);
}
