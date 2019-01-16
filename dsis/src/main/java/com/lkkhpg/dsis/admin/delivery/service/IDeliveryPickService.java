/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.delivery.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.delivery.exception.DeliveryException;
import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickHead;
import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickLine;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 挑库发放服务接口.
 * 
 * @author Zhaoqi
 *
 */
public interface IDeliveryPickService extends ProxySelf<IDeliveryPickService> {

    /**
     * 挑库-挑库订单头表insert.
     * 
     * @param request
     *            统一上下文
     * @param deliveryPickHead
     *            挑库订单头信息.
     * @return 生成的发运单ID.
     * @throws DeliveryException
     *             发运统一异常.
     * @throws CommDeliveryException
     */
    List<Long> saveDeliveryPick(IRequest request, @StdWho DeliveryPickHead deliveryPickHead)
            throws DeliveryException, CommDeliveryException;

    /**
     * 挑库-挑库订单查询.
     * 
     * @param request
     *            统一上下文
     * @param deliveryPickLine
     *            挑库订单行信息.
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 挑库订单行信息 list.
     */
    List<DeliveryPickLine> selectOrder(IRequest request, DeliveryPickLine deliveryPickLine, int page, int pagesize);

    /**
     * 挑库-订单行查询.
     * 
     * @param request
     *            统一上下文
     * @param deliveryPickLine
     *            挑库订单行信息.
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 挑库订单行信息 list.
     */
    List<DeliveryPickLine> selectOrderLine(IRequest request, DeliveryPickLine deliveryPick, int page, int pagesize);

    /**
     * 挑库-库存组织查询.
     * 
     * @param request
     *            统一上下文
     * @param orderNumber
     *            订单编号.
     * @return 库存组织list.
     */
    List<Map<String, Object>> queryInvOrgs(IRequest request, String orderNumber);

    /**
     * 挑库-库存量查询.
     * 
     * @param request
     *            统一上下文
     * @param deliveryPickLines
     *            挑库订单行信息.
     * @return 挑库订单行信息.
     */
    List<DeliveryPickLine> queryInventory(IRequest request, List<DeliveryPickLine> deliveryPickLines);

    /**
     * 订单类型不同获取供货组织选择不同.
     * 
     * @param request
     *            统一上下文
     * @param deliveryPickLine
     *            挑库行表dto.
     * @return get挑库订单头集合.
     */
    List<DeliveryPickLine> selectInvOrg(IRequest request, DeliveryPickLine deliveryPickLine);

    /**
     * 验证库存.
     * 
     * @param request
     *            请求上下文
     * @param deliveryPickHead
     *            挑库单行
     * @throws DeliveryException
     *             发运统一异常.
     * @throws CommDeliveryException
     *             发运统一异常.
     */

    void verifyInventory(IRequest request, @StdWho DeliveryPickHead deliveryPickHead)
            throws DeliveryException, CommDeliveryException;

}
