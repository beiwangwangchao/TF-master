/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.cost.service;

import java.util.List;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostAttributes;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostDetails;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostRecords;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 成本记录Service.
 * 
 * @author hanrui.huang
 *
 */
public interface IInvCostService extends ProxySelf<IInvCostService> {

    /**
     * 创建成本记录计算逻辑.
     * 
     * @param request
     *            请求上下文
     * @param CostDetails
     *            成本明细
     * @return 响应数据
     * @throws InventoryException
     *             统一异常处理
     */
    void calculateCost(IRequest request, List<CostDetails> CostDetails) throws InventoryException;

    /**
     * 查询成本记录.
     * 
     * @param request
     *            请求上下文
     * @param costRecords
     *            成本记录DTO
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @return 成本记录List
     */
    List<CostRecords> queryCostRecords(IRequest request, CostRecords costRecords, int page, int pagesize);

    /**
     * 取消成本记录.
     * 
     * @param requestContext
     *            请求上下文 请求上下文
     * @param costRecords
     *            成本记录DTO
     * @return 响应数据
     * @throws InventoryException
     *             统一异常处理
     */
    boolean removeCostRecords(IRequest requestContext, CostRecords costRecords) throws InventoryException;

    /**
     * 加权平均成本计算.
     * 
     * @param costRecord
     *            成本对象.
     * @return 计算完成后的加权平均成本值集合.
     */
    List<CostRecords> calculateAverageCostForTw(CostRecords costRecords) throws InventoryException;

    /**
     * 创建成本记录.
     * 
     * @param request
     *            统一上下文
     * @param invOrgId
     *            库存组织ID
     * @param costRecords
     *            成本记录
     * @return
     */
    List<CostRecords> createCostRecords(IRequest request, Long invOrgId, List<CostRecords> costRecords);

    /**
     * 保存成本属性.
     * 
     * @param request
     *            统一上下文
     * @param costAttributes
     *            成本属性对象集合
     * @return 成本属性信息
     */
    List<CostAttributes> saveOrUpdateCostAttributes(IRequest request, List<CostAttributes> costAttributes);

    /**
     * 根据costRecordId获取成本属性信息.
     * 
     * @param request
     *            统一上下文
     * @param costRecordId
     *            成本记录主键Id
     * @return 成本属性对象集合
     */
    List<CostAttributes> queryCostAttributes(IRequest request, Long costRecordId);
}
