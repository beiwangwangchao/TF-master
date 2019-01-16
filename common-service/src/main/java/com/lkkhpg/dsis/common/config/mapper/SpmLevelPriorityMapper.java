/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmLevelPriority;

/**
 * 
 *组织层次优先级.
 * @author zhaoqi
 */
public interface SpmLevelPriorityMapper {
    
    /**
     * 优先级排序.
     * 
     * @param orgType 组织类型.
     * @param paramName 参数名称.
     * @return 优先级从高到低的组织类型.
     */
    List<SpmLevelPriority> queryLevelValue(@Param("orgType")String orgType, @Param("paramName")String paramName);
    
    /**
     * 查询组织层次优先级.
     * @param spmLevelPriority 
     */
    List<SpmLevelPriority> selectAllLevel(SpmLevelPriority spmLevelPriority);
    
    /**
     * 查询组织层次优先级.
     * @param spmLevelPriority
     */
    int updateLevel(SpmLevelPriority spmLevelPriority);
    
    /**
     * 查询是否有一样优先级的组织优先级.
     * @param spmLevelPriority
     * @return 结果个数.
     */
    int selectSamePriority(SpmLevelPriority spmLevelPriority);
}