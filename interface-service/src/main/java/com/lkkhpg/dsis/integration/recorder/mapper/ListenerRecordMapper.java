/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.recorder.mapper;

import java.util.List;

import com.lkkhpg.dsis.integration.recorder.dto.ListenerRecord;

/**
 * 监听器记录Mapper.
 * @author shengyang.zhou@hand-china.com
 */
public interface ListenerRecordMapper {
    int deleteByPrimaryKey(Long requestId);

    int insert(ListenerRecord record);

    List<ListenerRecord> select(ListenerRecord para);

    ListenerRecord selectByPrimaryKey(Long requestId);

    int updateByPrimaryKeySelective(ListenerRecord record);

    int updateByPrimaryKey(ListenerRecord record);

    int deleteByDate(Long day);
}