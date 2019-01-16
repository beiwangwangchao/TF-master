/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.lading.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.lading.dto.DoHeader;

/**
 * 提货单头mapper.
 * @author liuxuan
 *
 */
public interface DoHeaderMapper {
    int deleteByPrimaryKey(Long doHeaderId);

    int insert(DoHeader record);

    int insertSelective(DoHeader record);

    DoHeader selectByPrimaryKey(Long doHeaderId);

    int updateByPrimaryKeySelective(DoHeader record);

    int updateByPrimaryKey(DoHeader record);
    
    List<DoHeader> queryByDoHeader(DoHeader record);
}
