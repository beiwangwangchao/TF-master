/*
 *
 */
package com.lkkhpg.dsis.platform.job.mapper;

import com.lkkhpg.dsis.platform.job.dto.CronTriggerDto;

/**
 *
 * @author shengyang.zhou@hand-china.com
 */
public interface CronTriggerMapper {
    CronTriggerDto selectByPrimaryKey(CronTriggerDto key);
}