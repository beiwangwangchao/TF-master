/*
 *
 */
package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.MessageSmsWhiteList;

/**
 * @author Clerifen Li
 */
public interface MessageSmsWhiteListMapper {
    
    int deleteByPrimaryKey(Long id);

    int deleteByAccountId(Long configId);
    
    List<MessageSmsWhiteList> selectByAccountId(Long accountId);
    
    List<MessageSmsWhiteList> selectMessageSmsWhiteLists(MessageSmsWhiteList record);
    
    int insert(MessageSmsWhiteList record);

    int insertSelective(MessageSmsWhiteList record);

    MessageSmsWhiteList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageSmsWhiteList record);

    int updateByPrimaryKey(MessageSmsWhiteList record);
}