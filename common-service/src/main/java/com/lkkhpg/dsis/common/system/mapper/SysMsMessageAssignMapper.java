/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.SysMsMessageAssign;
/**
 * 消息会员mapper.
 * @author HuangJiaJing
 *
 */
public interface SysMsMessageAssignMapper {
    int deleteByPrimaryKey(Long assignId);

    int insert(SysMsMessageAssign record);

    int insertSelective(SysMsMessageAssign record);

    SysMsMessageAssign selectByPrimaryKey(Long assignId);

    int updateByPrimaryKeySelective(SysMsMessageAssign record);

    int updateByPrimaryKey(SysMsMessageAssign record);
    
    List<SysMsMessageAssign> queryByMemAssign(SysMsMessageAssign record);
    
    List<SysMsMessageAssign> queryPublicAssign(SysMsMessageAssign record);
    
    List<SysMsMessageAssign> queryByAssignAll(Long record);
    
    List<SysMsMessageAssign> queryByUserAssign(SysMsMessageAssign record);
}