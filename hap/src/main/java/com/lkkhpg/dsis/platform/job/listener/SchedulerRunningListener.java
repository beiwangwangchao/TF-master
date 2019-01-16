/*
 *
 */
package com.lkkhpg.dsis.platform.job.listener;

import org.quartz.JobKey;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.lkkhpg.dsis.platform.job.dto.JobRunningInfoDto;
import com.lkkhpg.dsis.platform.job.service.IJobRunningInfoService;

/**
 * @author shiliyan
 *
 */
public class SchedulerRunningListener extends DefaultSchedulerListener {

    private static final String JOB_INFO_HAS_DELETED = "Job Info [{}.{}] has deleted.";
    private static final String JOB_WAS_DELETED_FROM_SCHEDULER = "Job [{}.{}] was deleted from Scheduler.";

    /*
     * (non-Javadoc)
     * 
     * @see DefaultSchedulerListener#jobDeleted(org.quartz.JobKey)
     */
    @Override
    public void jobDeleted(JobKey jobKey) {
        JobRunningInfoDto dto = new JobRunningInfoDto();
        String group = jobKey.getGroup();
        String name = jobKey.getName();
        logInfo(JOB_WAS_DELETED_FROM_SCHEDULER, group, name);
        dto.setJobName(name);
        dto.setJobGroup(group);
        deleteJobInfo(dto);
        logInfo(JOB_INFO_HAS_DELETED, group, name);
    }

    private void deleteJobInfo(JobRunningInfoDto jobCreateDto) {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        IJobRunningInfoService jobRunningInfoService = (IJobRunningInfoService) wac.getBean("jobRunningInfoService");
        jobRunningInfoService.delete(jobCreateDto);
    }

}
