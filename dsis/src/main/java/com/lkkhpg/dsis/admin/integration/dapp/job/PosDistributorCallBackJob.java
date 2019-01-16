/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.integration.dapp.service.IDistributorCallBackService;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * POS distributor callback JOB.
 * 
 * @author linyuheng
 */
public class PosDistributorCallBackJob extends AbstractJob {

    @Autowired
    private IDistributorCallBackService distributorCallBackService;

    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        distributorCallBackService.callbackDistributorNoSync();

        this.setExecutionSummary("Pos distributor callback successfully!" + new Date());
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        // TODO Auto-generated method stub
        return false;
    }

}
