/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.cost.service;

import java.util.List;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostDetails;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 成本数据获取service.
 * 
 * @author HuangJiaJing
 */
public interface IInvCostDetailsService {
    /**
     * 成本数据获取.
     * 
     * @param request
     *            请求上下文
     * @param costDetails
     *            成本明细对象
     * @return 响应数据
     * 
     * @throws InventoryException
     *             库存统一异常
     */
    List<CostDetails> genDetailDatas(IRequest request, CostDetails costDetails) throws InventoryException;

    /**
     * 校验是否满足获取成本条件.
     * 
     * @param request
     *            请求上下文
     * @param costOrgId
     *            库存归集中心Id
     * @param year
     *            年份
     * @param month
     *            月份
     * @return boolean 返回值
     * @throws InventoryException
     *             异常
     */
    boolean checkDetail(IRequest request, Long costOrgId, Integer year, Integer month) throws InventoryException;

    /**
     * 查询是否已生成成本数据.
     * 
     * @param request
     *            请求上下文
     * @param costOrgId
     *            库存归集中心Id
     * @param year
     *            年份
     * @param month
     *            月份
     * @return boolean 返回值
     * @throws InventoryException
     *             异常
     */
    boolean checkDetailNull(IRequest request, Long costOrgId, Integer year, Integer month) throws InventoryException;

    /**
     * 成本数据 查询.
     * 
     * @param request
     *            请求上下文
     * @param costOrgId
     *            库存归集中心Id
     * @param year
     *            年份
     * @param month
     *            月份
     * @return 响应数据
     */
    List<CostDetails> queryDetailDatas(IRequest request, CostDetails costDetails);

    /**
     * 更新成本明细.
     * 
     * @param request
     *            请求上下文
     * @param costDetails
     *            成本维护dto
     * @return 成本对象
     */
    List<CostDetails> updateCostDetails(IRequest request, @StdWho List<CostDetails> costDetails);
}
