/*
 *
 */
package com.lkkhpg.dsis.admin.config.job;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.config.service.IServiceCenterBonusJobService;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * Service Center 奖金计算job.
 * 
 * @author houmin
 *
 */
public class ServiceCenterBonusJob extends AbstractJob {

    @Autowired
    private IServiceCenterBonusJobService serviceCenterBonusJobService;

    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        serviceCenterBonusJobService.bonusCalculationOfSCB();
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        // TODO Auto-generated method stub
        return false;
    }

}
