/*
 *
 */
package com.lkkhpg.dsis.common.bonus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.bonus.dto.IpointBonus;

/**
 * 奖金记录Mapper.
 * 
 * @author houmin
 *
 */
public interface IpointBonusMapper {
    int deleteByPrimaryKey(Long bonusId);

    int insert(IpointBonus record);

    int insertSelective(IpointBonus record);

    IpointBonus selectByPrimaryKey(Long bonusId);

    int updateByPrimaryKeySelective(IpointBonus record);

    int updateByPrimaryKey(IpointBonus record);

    List<IpointBonus> queryIpointBonus(@Param(value = "ipointBonus") IpointBonus ipointBonus,
            @Param(value = "statusList") List<String> statusList);

    int submitIpointBonus(IpointBonus record);

    int approveIpointBonus(IpointBonus record);

    int rejectIpointBonus(IpointBonus record);
}