/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.inventory.job;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.inventory.service.IAlertJobService;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * 批次预警job.
 * 
 * @author liang.rao
 *
 */
public class LotAlertJob extends AbstractJob {
    
    @Autowired
    private IAlertJobService alertjobService;
    
    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        alertjobService.lotAlert();
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        return false;
    }

}
