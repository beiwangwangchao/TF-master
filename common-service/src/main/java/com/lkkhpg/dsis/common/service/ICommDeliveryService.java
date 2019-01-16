/*
 *
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickHead;
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
public interface ICommDeliveryService extends ProxySelf<ICommDeliveryService> {

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

}
