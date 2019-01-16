/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.job;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.member.service.IMemberJobService;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * VIP转Distributor定时Job.
 * 
 * @author linyuheng
 */
public class VIPToDistributorJob extends AbstractJob {

    private Logger logger = LoggerFactory.getLogger(VIPToDistributorJob.class);

    @Autowired
    private IMemberJobService memberJobService;

    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        memberJobService.vipToDistributor();
        this.setExecutionSummary("success to run the job");
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        logger.error("error");
        return false;
    }

}
