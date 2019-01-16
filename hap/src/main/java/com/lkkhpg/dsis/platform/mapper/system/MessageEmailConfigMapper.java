/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.MessageEmailConfig;


/**
 * @author Clerifen Li
 */
public interface MessageEmailConfigMapper {
    int deleteByPrimaryKey(Long configId);

    int insert(MessageEmailConfig record);

    int insertSelective(MessageEmailConfig record);

    MessageEmailConfig selectByPrimaryKey(Long configId);
    
    List<MessageEmailConfig> selectMessageEmailConfigs(MessageEmailConfig record);

    int updateByPrimaryKeySelective(MessageEmailConfig record);

    int updateByPrimaryKey(MessageEmailConfig record);
    
    Integer queryMsgConfigQuanties();
}