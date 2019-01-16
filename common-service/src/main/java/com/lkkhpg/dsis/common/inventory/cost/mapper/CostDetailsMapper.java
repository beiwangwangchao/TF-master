/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.cost.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.inventory.cost.dto.CostDetails;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostRecords;

/**
 * 成本维护数据mapper.
 * 
 * @author huangjiajing
 *
 */
public interface CostDetailsMapper {
    int deleteByPrimaryKey(Long costDetailId);

    int insert(CostDetails record);

    int insertSelective(CostDetails record);

    CostDetails selectByPrimaryKey(Long costDetailId);

    int updateByPrimaryKeySelective(CostDetails record);

    int updateByPrimaryKey(CostDetails record);

    /**
     * 查询是否已生成了成本明细.
     * 
     * @param invOrgId
     *            库存归集中心Id
     * @param year
     *            年份
     * @param month
     *            月份
     * @return
     */
    int queryTotal(@Param("invOrgId") Long invOrgId, @Param("year") Integer year, @Param("month") Integer month);

    /**
     * 删除已存在的成本明细信息.
     * 
     * @param costDetails
     *            成本明细对象
     * @return
     */
    void deleteCostDetail(CostDetails costDetails);

    /**
     * 先进先出成本查询.
     * 
     * @param costDetails
     *            成本明细对象
     * @return 成本详细信息
     */
    List<CostDetails> queryCostDetailsOfFIFO(CostDetails costDetails);

    /**
     * 加权平均成本查询.
     * 
     * @param costDetails
     *            成本明细对象
     * @return 成本详细信息
     */
    List<CostDetails> queryCostDetailsOfAvray(CostDetails costDetails);

    int updateCostDetail(CostRecords record);

    /**
     * 查询库存归集中心下，成本明细中成本值是否为空.
     * 
     * @param record
     *            成本明细对象
     * @return 成本记录
     */
    List<CostDetails> queryCostUnitIsNull(CostDetails record);

    /**
     * 查询是否已获取成本信息
     * 
     * @param record
     *            成本详情对象.
     * @return 成本详情对象
     */
    List<CostDetails> queryIsGetUnitCostInfo(CostDetails record);

    /**
     * 更新成本明细.
     * 
     * @param record
     *            成本明细
     * @return
     */
    int updateCostDetailsByParams(CostDetails record);

}