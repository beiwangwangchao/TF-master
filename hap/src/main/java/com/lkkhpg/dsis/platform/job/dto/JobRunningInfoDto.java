/*
 *
 */
package com.lkkhpg.dsis.platform.job.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 *
 * @author liyan.shi@hand-china.com
 */
public class JobRunningInfoDto extends BaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = -6714732735643965630L;

    private Long jobRunningInfoId;

    private String jobName;

    private String jobGroup;

    private String jobResult;

    private String jobStatus;

    private String jobStatusMessage;

    private String triggerName;

    private String triggerGroup;
    
    private String executionSummary;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date previousFireTime;
    
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date fireTime;
    
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date nextFireTime;

    private Long refireCount;

    private String fireInstanceId;

    private String schedulerInstanceId;
    
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date scheduledFireTime;

    public Long getJobRunningInfoId() {
        return jobRunningInfoId;
    }

    public void setJobRunningInfoId(Long jobRunningInfoId) {
        this.jobRunningInfoId = jobRunningInfoId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup == null ? null : jobGroup.trim();
    }

    public String getJobResult() {
        return jobResult;
    }

    public void setJobResult(String jobResult) {
        this.jobResult = jobResult == null ? null : jobResult.trim();
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus == null ? null : jobStatus.trim();
    }

    public String getJobStatusMessage() {
        return jobStatusMessage;
    }

    public void setJobStatusMessage(String jobStatusMessage) {
        this.jobStatusMessage = jobStatusMessage == null ? null : jobStatusMessage.trim();
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName == null ? null : triggerName.trim();
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup == null ? null : triggerGroup.trim();
    }

    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public Date getFireTime() {
        return fireTime;
    }

    public void setFireTime(Date fireTime) {
        this.fireTime = fireTime;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public Long getRefireCount() {
        return refireCount;
    }

    public void setRefireCount(Long refireCount) {
        this.refireCount = refireCount;
    }

    public String getFireInstanceId() {
        return fireInstanceId;
    }

    public void setFireInstanceId(String fireInstanceId) {
        this.fireInstanceId = fireInstanceId == null ? null : fireInstanceId.trim();
    }

    public String getSchedulerInstanceId() {
        return schedulerInstanceId;
    }

    public void setSchedulerInstanceId(String schedulerInstanceId) {
        this.schedulerInstanceId = schedulerInstanceId == null ? null : schedulerInstanceId.trim();
    }

    public Date getScheduledFireTime() {
        return scheduledFireTime;
    }

    public void setScheduledFireTime(Date scheduledFireTime) {
        this.scheduledFireTime = scheduledFireTime;
    }

    public String getExecutionSummary() {
        return executionSummary;
    }

    public void setExecutionSummary(String executionSummary) {
        this.executionSummary = executionSummary;
    }
}