/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.inventory.job;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.inventory.service.IAlertJobService;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * 库存预警job.
 * 
 * @author liang.rao
 *
 */
public class QuantityAlertJob extends AbstractJob {

    @Autowired
    private IAlertJobService alertjobService;
    
    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        alertjobService.quantityAlert();
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        return false;
    }

}
