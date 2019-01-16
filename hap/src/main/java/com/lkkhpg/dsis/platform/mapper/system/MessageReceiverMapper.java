/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;

/**
 * @author chuangsheng.zhang
 * @author xiawang.liu@hand-china.com
 */
public interface MessageReceiverMapper {
    int deleteByPrimaryKey(Long receiverId);

    int deleteByMessageId(Long messageId);

    int insert(MessageReceiver record);

    int insertSelective(MessageReceiver record);

    MessageReceiver selectByPrimaryKey(Long receiverId);

    int updateByPrimaryKeySelective(MessageReceiver record);

    int updateByPrimaryKey(MessageReceiver record);

    List<MessageReceiver> selectByMessageId(Long messageId);
    
    
    /*
     * 根据MessageId查询消息地址
     */
    List<MessageReceiver> selectMessageAddressesByMessageId(MessageReceiver messageReceiver);
}