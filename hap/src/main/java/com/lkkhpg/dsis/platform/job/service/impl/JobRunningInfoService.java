/*
 *
 */
package com.lkkhpg.dsis.platform.job.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.job.dto.JobRunningInfoDto;
import com.lkkhpg.dsis.platform.job.mapper.JobRunningInfoDtoMapper;
import com.lkkhpg.dsis.platform.job.service.IJobRunningInfoService;

/**
 * @author shiliyan
 *
 */
@Service
public class JobRunningInfoService implements IJobRunningInfoService {

    @Autowired
    private JobRunningInfoDtoMapper jobRunningInfoDtoMapper;

    @Override
    public List<JobRunningInfoDto> queryJobRunningInfo(IRequest request, JobRunningInfoDto example, int page,
                                                       int pagesize) {
        PageHelper.startPage(page, pagesize);
        return jobRunningInfoDtoMapper.select(example);
    }

    @Override
    public void createJobRunningInfo(JobRunningInfoDto jobCreateDto) {
        jobRunningInfoDtoMapper.insert(jobCreateDto);
    }

    @Override
    public void delete(JobRunningInfoDto jobCreateDto) {
        jobRunningInfoDtoMapper.deleteByNameGroup(jobCreateDto);
    }

}
