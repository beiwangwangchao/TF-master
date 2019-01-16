/*
 *
 */
package com.lkkhpg.dsis.admin.config.job;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.config.service.IIpointCenterBonusJobService;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * iPoint Center奖金计算job.
 * 
 * @author houmin
 *
 */
public class IpointCenterBonusJob extends AbstractJob {

    @Autowired
    private IIpointCenterBonusJobService ipointCenterBonusJobService;

    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        ipointCenterBonusJobService.bonusCalculationOfICB();
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        // TODO Auto-generated method stub
        return false;
    }

}
