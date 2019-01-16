/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import com.lkkhpg.dsis.common.system.dto.SysMsMessagePublishResult;

/**
 * @author shiliyan
 *
 */
public interface SysMsMessageResultMapper {

    int insertSelective(SysMsMessagePublishResult record);

    SysMsMessagePublishResult selectByPrimaryKey(Long resultId);

    SysMsMessagePublishResult selectByMsMessageId(Long msMessageId);
}
