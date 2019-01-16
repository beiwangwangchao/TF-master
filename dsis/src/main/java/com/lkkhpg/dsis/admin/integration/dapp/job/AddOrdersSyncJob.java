/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.integration.dapp.service.IAddOrderCallbackService;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * 创建订单同步任务.
 * 
 * @author shenqb
 *
 */
public class AddOrdersSyncJob extends AbstractJob {

    @Autowired
    private IAddOrderCallbackService addOrderCallbackService;

    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        try {
            addOrderCallbackService.addOrderCallback();
            this.setExecutionSummary("Finish : " + new Date());
        } catch (Exception e) {
            this.setExecutionSummary("errorMsg : " + e.getMessage());
        }
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        // TODO Auto-generated method stub
        return false;
    }

}
