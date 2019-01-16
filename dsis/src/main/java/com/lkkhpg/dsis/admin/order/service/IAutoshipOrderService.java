/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.order.exception.OrderException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.AutoshipLine;
import com.lkkhpg.dsis.common.order.dto.AutoshipOrder;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 自动订货单查询service.
 * 
 * @author HuangJiaJing
 *
 */
public interface IAutoshipOrderService extends ProxySelf<IAutoshipOrderService> {

    /**
     * 查询订单.
     * 
     * @param request
     *            基础数据请求
     * @param autoshipOrder
     *            订单查询对象
     * @param page
     *            分页页数，默认1
     * @param pagesize
     *            分页数据数，默认10
     * @return 满足条件的autoship集合
     */
    List<AutoshipOrder> selectAutoshipOrderParas(IRequest request, AutoshipOrder autoshipOrder, int page, int pagesize);

    /**
     * 执行自动发货单.
     * 
     * @param request
     *            基础数据请求
     * @param autoshipOrders
     *            需要执行的autoship
     * @return 批次号
     */
    String executeAutoshipOrders(IRequest request, List<AutoshipOrder> autoshipOrders);

    /**
     * 转换为销售订单.
     * 
     * @param request
     *            基础数据请求
     * @param autoshipOrder
     *            autoship订单
     * @param batchNum
     *            批次号
     * @return 如果传入的销售订单为空则返回null
     */
    SalesOrder convertAutoshipOrder(final IRequest request, final AutoshipOrder autoshipOrder, final String batchNum);

    /**
     * autoship订单转换销售订单.
     * 
     * @param request
     *            基础数据请求
     * @param autoshipOrders
     *            需要转换的autoship订单集合
     * @return 批次号
     */
    String convertAutoshipOrders(final IRequest request, final List<AutoshipOrder> autoshipOrders);

    /**
     * 保存autoship订单.
     * 
     * @param request
     *            基础数据请求
     * @param autoshipOrder
     *            需要转换的autoship订单
     * @return 传入的autoship为空则返回null
     * @throws CommOrderException
     *             订单数据校验不通过时抛出
     */
    AutoshipOrder submitAutoshipOrder(final IRequest request, @StdWho final AutoshipOrder autoshipOrder)
            throws CommOrderException;

    /**
     * 修改autoship的状态.
     * 
     * @param request
     *            基础数据请求
     * @param autoShipId
     *            autoshipId
     * @param status
     *            状态
     * @return 修改的行数
     * @throws CommOrderException
     *             订单数据校验不通过时抛出
     */
    int updateOrderStatus(final IRequest request, final Long autoShipId, final String status) throws CommOrderException;

    /**
     * autoship详情.
     * 
     * @param request
     *            基础数据请求
     * @param autoShipId
     *            autoshipId
     * @return 查询不到则返回null
     */
    AutoshipOrder getDetail(final IRequest request, final Long autoShipId);

    /**
     * autoship订单行删除.
     * 
     * @param request
     *            基础数据请求
     * @param lines
     *            autoship订单集合
     */
    void deleteLine(final IRequest request, final List<AutoshipLine> lines);

    /**
     * 校验会员是否存在激活或暂停的autoship.
     * 
     * @param request
     *            基础数据请求
     * @param memberId
     *            会员Id
     * @return 存在则autoship的ID，不存在返回空集合
     */
    List<AutoshipOrder> checkMemberAutoship(final IRequest request, final Long memberId);

    /**
     * 验证自动发货单数据是否合法.
     * <p>
     * 数据全部合法则返回true,若不合法抛出异常
     * 
     * @param request
     *            基础数据请求
     * @param autoshipOrder
     *            autoship订单
     * @return 验证结果
     * @throws CommOrderException
     *             订单统一异常
     */
    boolean validateAutoshipOrder(IRequest request, AutoshipOrder autoshipOrder) throws CommOrderException;

    /**
     * 更新自动订货单.
     * 
     * @param request
     *            请求上下文
     * @param autoshipOrder
     *            自动订货单
     * @return 插入结果
     */
    int updateAutoshipOrderStatus(final IRequest request, AutoshipOrder autoshipOrder);

    /**
     * 为自动订货单增加赠品.
     * 
     * @param request
     *            请求上下文
     * @param autoshipOrder
     *            自动订货单
     * @param salesOrder
     *            订单
     * @return 订单
     */

    SalesOrder addAutoShipGift(final IRequest request, AutoshipOrder autoshipOrder, SalesOrder salesOrder);

    /**
     * 取消代付款自定订货单.
     */
    void cancelAutoshipOrder();

    /**
     * 获取自动订货单必要的数据
     * 
     * @param request
     *            请求上下文
     * @param autoshipOrder
     *            自动订货单
     * @return 基础数据
     */
    Map<String, Object> getBasicDataForCreate(final IRequest request, AutoshipOrder autoshipOrder)
            throws OrderException;

}
