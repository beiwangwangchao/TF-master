/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.AutoshipLine;
import com.lkkhpg.dsis.common.order.dto.AutoshipOrder;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 自动订货单查询service.
 * 
 * @author HuangJiaJing
 *
 */
public interface ICommAutoshipOrderService extends ProxySelf<ICommAutoshipOrderService> {

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
     * 保存autoship订单.
     * 
     * @param request
     *            基础数据请求
     * @param autoshipOrder
     *            需要转换的autoship订单
     * @return 传入的autoship为空则返回null
     * @throws CommOrderException
     *             订单数据不能通过校验时抛出
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
     *             订单数据不能通过校验时抛出
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
     * @return 存在则返回ID，不存在返回null
     */
    Long checkMemberAutoship(final IRequest request, final Long memberId);
}
