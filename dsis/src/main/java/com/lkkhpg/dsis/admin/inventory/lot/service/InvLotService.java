/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.lot.service;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.dto.InvLot;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * InvLotService.
 * 
 * @author runbai.chen
 *
 */
public interface InvLotService extends ProxySelf<InvLotService> {
    /**
     * 插入单条批次信息.
     * 
     * @param request
     *            用户请求
     * @param invLot
     *            单条批次信息
     * @return 批次信息.
     * 
     */
    InvLot insertInvLot(IRequest request, @StdWho InvLot invLot);

    /**
     * 更新单条批次信息.
     * 
     * @param request
     *            请求上下文
     * @param invLot
     *            单条批次信息
     * @return 批次信息.
     * 
     */
    InvLot updateInvLot(IRequest request, @StdWho InvLot invLot);

    /**
     * 删除单条批次信息.
     * 
     * @param request
     *            用户请求
     * @param invLot
     *            单条批次信息
     * @return 批次信息.
     */
    boolean deleteInvLot(IRequest request, InvLot invLot);

    /**
     * 查询出批次数据.
     * 
     * @param request
     *            用户请求
     * @param invLot
     *            库存批次数据
     * @param page
     *            页面数.
     * @param pagesize
     *            页面行数量 @return 批次列表
     * @return 批次信息
     */

    List<InvLot> selectInvLots(IRequest request, InvLot invLot, int page, int pagesize);

    /**
     * 查询批次信息.
     * 
     * @param request
     *            用户请求
     * @param invLot
     *            库存批次查询条件
     * @param page
     *            分页页数，默认1
     * @param pagesize
     *            分页数据数，默认10
     * @return 批次列表
     */

    List<InvLot> selectInvLotsByParas(IRequest request, InvLot invLot, int page, int pagesize);

    /**
     * 检验是否已经存在批次信息.
     * 
     * @param request
     *            用户请求
     * @param invLot
     *            需要检验的批次信息
     * @return true,存在； false,不存在
     */
    boolean isExistInvLot(IRequest request, InvLot invLot);

    /**
     * 批量处理批次信息.
     * 
     * @param request
     *            用户请求
     * @param invLots
     *            批次LIST
     * @return 批次信息.
     */
    List<InvLot> batchProcessInvLot(IRequest request, @StdWho List<InvLot> invLots);

    /**
     * 查询商品的有库存量的批次.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品id
     * @param orgId
     *            库存组织id
     * @return 查询结果
     */
    List<InvLot> queryItemLots(IRequest request, Long itemId, Long orgId);

    /**
     * 根据id查找是否有相同的数据.
     * @param request 请求上下文
     * @param invLot 批次对象
     * @return 返回的数据条数
     */
    int queryCount(IRequest request, InvLot invLot);

    /**
     * 判断是否有重复的数据.
     * @param request 请求上下文
     * @param invLots 批次集合
     * @return false 表示有重复的 true表示没有重复的
     */
    Boolean validateInvLot(IRequest request, List<InvLot> invLots);
}
