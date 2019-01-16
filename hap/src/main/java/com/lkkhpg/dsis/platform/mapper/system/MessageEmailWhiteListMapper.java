/*
 *
 */
package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.MessageEmailWhiteList;

/**
 * @author Clerifen Li
 */
public interface MessageEmailWhiteListMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByConfigId(Long configId);
    
    List<MessageEmailWhiteList> selectByConfigId(Long configId);
    
    int insert(MessageEmailWhiteList record);

    int insertSelective(MessageEmailWhiteList record);

    MessageEmailWhiteList selectByPrimaryKey(Long id);

    List<MessageEmailWhiteList> selectMessageEmailWhiteLists(MessageEmailWhiteList record);
    
    int updateByPrimaryKeySelective(MessageEmailWhiteList record);

    int updateByPrimaryKey(MessageEmailWhiteList record);
}