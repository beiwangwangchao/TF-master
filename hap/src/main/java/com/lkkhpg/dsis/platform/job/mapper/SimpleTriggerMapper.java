/*
 *
 */
package com.lkkhpg.dsis.platform.job.mapper;

import com.lkkhpg.dsis.platform.job.dto.SimpleTriggerDto;

/**
 *
 * @author shengyang.zhou@hand-china.com
 */
public interface SimpleTriggerMapper {

    SimpleTriggerDto selectByPrimaryKey(SimpleTriggerDto key);
}