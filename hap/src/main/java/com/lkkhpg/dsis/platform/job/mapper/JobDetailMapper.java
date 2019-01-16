/*
 *
 */

package com.lkkhpg.dsis.platform.job.mapper;

import java.util.List;

import com.lkkhpg.dsis.platform.job.dto.JobDetailDto;
import com.lkkhpg.dsis.platform.job.dto.JobInfoDetailDto;

/**
 *
 * @author shengyang.zhou@hand-china.com
 */
public interface JobDetailMapper {
    JobDetailDto selectByPrimaryKey(JobDetailDto key);

    List<JobDetailDto> selectJobDetails(JobDetailDto example);

    List<JobInfoDetailDto> selectJobInfoDetails(JobDetailDto example);
}