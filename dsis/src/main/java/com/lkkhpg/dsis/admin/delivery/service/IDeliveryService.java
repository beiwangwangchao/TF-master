/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.delivery.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.delivery.exception.DeliveryException;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.delivery.dto.DeliveryForQuery;
import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickHead;
import com.lkkhpg.dsis.common.delivery.dto.OrderDelivery;
import com.lkkhpg.dsis.common.delivery.dto.OrderDeliveryLine;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 发运单Service.
 * 
 * @author peng.li
 */
public interface IDeliveryService extends ProxySelf<IDeliveryService> {

    /**
     * 查询发运单信息.
     * 
     * @param request
     *            统一上下文.
     * @param delivery
     *            发运单信息.
     * @param page
     *            页.
     * @param pageSize
     *            总页数.
     * @return 发运单信息.
     */
    List<OrderDelivery> queryDelivery(IRequest request, DeliveryForQuery delivery, int page, int pageSize);

    List<OrderDelivery>queryDeliveryStatus();

    /**
     * 查询订单列表.
     * 
     * @param request
     *            统一上下文.
     * @return 订单列表.
     */
    List<Map<String, Object>> orderListQuery(IRequest request);

    /**
     * 查询发运单详情.
     * 
     * @param request
     *            请求上下文
     * @param deliveryId
     *            发运单头ID.
     * @return 发运单详情.
     */
    OrderDelivery getDeliveryDetails(IRequest request, Long deliveryId);

    /**
     * 查询库存列表.
     * 
     * @param request
     *            统一上下文.
     * @return 库存列表.
     */
    List<Map<String, Object>> invComboxQuery(IRequest request);

    /**
     * 启用批次时保存发运单.
     * 
     * @param request
     *            统一上下文.
     * @param deliveryHead
     *            发运单信息.
     * @return 发运单详情.
     * @throws DeliveryException
     *             发运统一异常.
     */
    OrderDelivery save(IRequest request, @StdWho OrderDelivery deliveryHead) throws CommDeliveryException;

    /**
     * 保存发运单头.
     * 
     * @param request
     *            统一上下文.
     * @param deliveryHead
     *            发运单信息.
     * @return 发运单详情.
     */
    OrderDelivery saveDeliveryHead(IRequest request, @StdWho OrderDelivery deliveryHead);

    /**
     * 提交发运单.
     * 
     * @param request
     *            统一上下文.
     * @param deliveryHead
     *            发运单信息.
     * @return 发运单详情.
     * @throws CommDeliveryException
     *             发运统一异常.
     * @throws InventoryException
     *             库存统一异常.
     */
    OrderDelivery submit(IRequest request, @StdWho OrderDelivery deliveryHead)
            throws CommDeliveryException, InventoryException;

    /**
     * 取消发运单.
     * 
     * @param request
     *            统一上下文.
     * @param deliverId
     *            发运单头ID.
     * @return 发运单信息.
     * @throws DeliveryException
     *             发运统一异常.
     */
    OrderDelivery cancel(IRequest request, Long deliverId) throws DeliveryException;

    /**
     * 查询发运单页面中订单行信息.
     * 
     * @param request
     *            统一上下文.
     * @param deliveryId
     *            发运单头ID.
     * @return 订单行信息.
     */
    List<OrderDeliveryLine> getDeliveryDetailsLine(IRequest request, long deliveryId);

    /**
     * 查询商品批次.
     * 
     * @param request
     *            统一上下文.
     * @param itemId
     *            商品ID.
     * @param orgId
     *            库存组织Id.
     * @return 商品批次.
     */
    List<Map<String, Object>> getItemLots(IRequest request, long itemId, long orgId);

    /**
     * 自动创建发运单.
     * 
     * @param request
     *            统一上下文.
     * @param pickRelease
     *            调库单.
     * @return 创建发运单Id列表 .
     * @throws CommDeliveryException
     *             发运统一异常.
     */
    List<Long> createDelivery(IRequest request, @StdWho DeliveryPickHead pickRelease) throws CommDeliveryException;

    /**
     * 自动创建发运单.
     * 
     * @param request
     *            统一上下文.
     * @param salesOrder
     *            订单信息.
     * @return 创建的发运单头id集合，如果返回为空，则表示需要手动调整.
     * @throws CommDeliveryException
     *             发运统一异常.
     */
    List<Long> createDeliveriesByOrder(IRequest request, SalesOrder salesOrder) throws CommDeliveryException;

    /**
     * 根据失效订单头ID修改关联发运单状态.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头ID.
     * @param newStatus
     *            新状态.
     * @param status
     *            状态.
     */
    void updateDeliveryStatus(IRequest request, Long headerId, String newStatus, List<String> status);

    /**
     * 循环处理库存事务
     * 
     * @param request
     *            请求上下文
     * @param deliveryHead
     *            发运单行
     * @throws InventoryException
     *             库存事务处理异常
     */
    void processTransaction(IRequest request, OrderDelivery deliveryHead) throws InventoryException;

}
