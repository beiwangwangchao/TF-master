/*
 *
 */
package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.Prompt;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface PromptMapper {
    int deleteByPrimaryKey(Long promptId);

    int insert(Prompt record);

    Prompt selectByPrimaryKey(Long promptId);

    int updateByPrimaryKeySelective(Prompt record);

    int updateByPrimaryKey(Prompt record);

    List<Prompt> select(Prompt prompt);
}