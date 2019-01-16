/*
 *
 */
package com.lkkhpg.dsis.platform.job.mapper;

import java.util.List;

import com.lkkhpg.dsis.platform.job.dto.SchedulerDto;

/**
 *
 * @author shengyang.zhou@hand-china.com
 */
public interface SchedulerMapper {

    SchedulerDto selectByPrimaryKey(SchedulerDto key);

    List<SchedulerDto> selectSchedulers(SchedulerDto example);

}