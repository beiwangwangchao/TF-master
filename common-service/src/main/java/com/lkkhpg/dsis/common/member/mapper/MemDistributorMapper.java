/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.member.dto.MemDistributor;

public interface MemDistributorMapper {
    int insert(MemDistributor record);

    int insertSelective(MemDistributor record);

    int update(MemDistributor record);

    List<MemDistributor> selectByAttributes(MemDistributor record);
}