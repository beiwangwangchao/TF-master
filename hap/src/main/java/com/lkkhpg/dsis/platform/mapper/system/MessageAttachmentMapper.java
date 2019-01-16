/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.MessageAttachment;

/**
 * @author chuangshneg.zhang
 */
public interface MessageAttachmentMapper {
    int deleteByPrimaryKey(Long attachmentId);

    int deleteByMessageId(Long messageId);

    int insert(MessageAttachment record);

    int insertSelective(MessageAttachment record);

    MessageAttachment selectByPrimaryKey(Long attachmentId);

    int updateByPrimaryKeySelective(MessageAttachment record);

    int updateByPrimaryKey(MessageAttachment record);

    List<MessageAttachment> selectByMessageId(Long messageId);
}