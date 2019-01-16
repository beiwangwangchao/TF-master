/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.cost.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.cost.dto.CostAttributes;

/**
 * 成本属性类映射Mapper.
 * 
 * @author houmin
 *
 */
public interface CostAttributesMapper {
    int insert(CostAttributes record);

    int insertSelective(CostAttributes record);

    CostAttributes selectByPrimaryKey(Long costAttrId);

    /**
     * 根据成本记录主键Id查询对应成本属性信息.
     * 
     * @param costRecordId
     *            成本记录主键Id
     * @return 对应成本信息
     */
    List<CostAttributes> queryCostAttrByCostRecordId(Long costRecordId);

    /**
     * 删除成本属性.
     * 
     * @param costAttrId
     *            主键Id
     * @return
     */
    int deleteByPrimaryKey(Long costAttrId);

    /**
     * 删除成本属性.
     * 
     * @param costAttribute
     *            属性对象
     * @return
     */
    int deleteByAttributes(CostAttributes costAttribute);

    /**
     * 更新成本属性.
     * 
     * @param costAttribute
     *            属性对象
     * @return
     */
    int updateByPrimaryKeySelective(CostAttributes costAttribute);

    /**
     * 更新成本属性.
     * 
     * @param costAttribute
     *            属性对象P
     * @return
     */
    int updateByAttributes(CostAttributes costAttribute);
}