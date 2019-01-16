/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.lading.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.lading.dto.DoLine;

/**
 * 提货单行mapper.
 * @author liuxuan
 *
 */
public interface DoLineMapper {
    int deleteByPrimaryKey(Long doLineId);

    int insert(DoLine record);

    int insertSelective(DoLine record);

    DoLine selectByPrimaryKey(DoLine doLineId);

    int updateByPrimaryKeySelective(DoLine record);

    int updateByPrimaryKey(DoLine record);
    
    List<DoLine> queryByDoLine(DoLine record);
}