/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.SysMsMessage;

/**
 * 消息mapper.
 * @author HuangJiaJing
 *
 */
public interface SysMsMessageMapper {
    int deleteByPrimaryKey(Long msMessageId);

    int insert(SysMsMessage record);

    int insertSelective(SysMsMessage record);

    SysMsMessage selectByPrimaryKey(Long msMessageId);

    int updateByPrimaryKeySelective(SysMsMessage record);

    int updateByPrimaryKeyWithBLOBs(SysMsMessage record);

    int updateByPrimaryKey(SysMsMessage record);
    
    List<SysMsMessage> queryBySysMsMessage(SysMsMessage record);
}