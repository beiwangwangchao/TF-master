/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.common.config.mapper;

import com.lkkhpg.dsis.common.config.dto.Location;

/**
 * @author wuyichu
 */
public interface LocationMapper {
    int deleteByPrimaryKey(Long locationId);

    int insert(Location record);

    int insertSelective(Location record);

    Location selectByPrimaryKey(Long locationId);

    int updateByPrimaryKeySelective(Location record);

    int updateByPrimaryKey(Location record);
    
}