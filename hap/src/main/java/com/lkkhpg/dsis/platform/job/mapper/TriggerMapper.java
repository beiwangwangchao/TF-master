/*
 *
 */
package com.lkkhpg.dsis.platform.job.mapper;

import java.util.List;

import com.lkkhpg.dsis.platform.job.dto.TriggerDto;

/**
 *
 * @author shengyang.zhou@hand-china.com
 */
public interface TriggerMapper {
    TriggerDto selectByPrimaryKey(TriggerDto key);

    List<TriggerDto> selectTriggers(TriggerDto example);

}