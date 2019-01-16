/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.cost.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.inventory.cost.dto.CostRecords;

/**
 * 成本记录mapper.
 * 
 * @author hanrui.huang
 *
 */
public interface CostRecordsMapper {
    int deleteByPrimaryKey(Long costRecordId);

    int insert(CostRecords record);

    int insertSelective(CostRecords record);

    CostRecords selectByPrimaryKey(Long costRecordId);

    int updateByPrimaryKeySelective(CostRecords record);

    int updateByPrimaryKey(CostRecords record);

    /**
     * 获取先进先出成本记录(生成成本记录时).
     * 
     * @param record
     *            成本记录对象
     * @return
     */
    List<CostRecords> getCostRecordsByFIFO(CostRecords record);

    /**
     * 成本加权平均数据获取.
     * 
     * @param record
     *            成本记录对象.
     * @return 计算加权平均所需数据
     */
    List<CostRecords> getCostRecordsByAvragForTW(CostRecords record);

    /**
     * 查询先进先出成本记录(查询成本历史页).
     * 
     * @param record
     *            成本记录对象
     * @return 先进先出成本记录集合
     */
    List<CostRecords> queryCostRecordsByFIFO(CostRecords record);

    /**
     * 查询加权平均成本记录(查询成本历史页)
     * 
     * @param record
     *            成本记录对象
     * @return 先进先出成本记录集合
     */
    List<CostRecords> queryCostRecordsByAvrag(CostRecords record);

    /**
     * 获取库存归集中心组织、参数年月份下成本记录(FIFO).
     * 
     * @param record
     *            成本信息参数.
     * @return 成本信息记录
     */
    List<CostRecords> queryCostRecords(CostRecords record);

    /**
     * 获取年月份下库存归集中心组织下成本记录.
     * 
     * @param record
     *            成本信息参数.
     * @return 成本信息记录
     */
    List<CostRecords> getCostRecordsByParams(CostRecords record);

    /**
     * 获取库存归集中心组织下最后生成的成本信息.
     * 
     * @param invOrgId
     *            库存归集中心组织
     * @return
     */
    List<CostRecords> queryTheMaxMonthOfInvOrgId(@Param("costOrgId") Long costOrgId);

    /**
     * 更新成本records状态
     * 
     * @param record
     *            成本records对象
     * @return
     */
    int updateCostRecords(CostRecords record);

    /**
     * 查询库存组织下最近年份月份的成本信息.
     * 
     * @param invOrgId
     *            库存组织
     * @return
     */
    List<CostRecords> getNewCostRecords(Long invOrgId);

    /**
     * 比较records表与transaction表中，获取上月存在records但本月transaction中不存在的item记录.
     * 
     * @param costRecords
     *            成本记录对象
     * @return
     */
    List<CostRecords> getLastRecordsAndNonCurrentTrx(CostRecords costRecords);

    /**
     * 查询Records记录.
     * 
     * @param costRecords
     *            Records对象
     * @return
     */
    List<CostRecords> getRecordsInfoByCostRecords(CostRecords costRecords);

}