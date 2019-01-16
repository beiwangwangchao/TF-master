/*
 *
 */
package com.lkkhpg.dsis.admin.order.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.order.service.IAutoshipOrderService;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * 定时删除自动订货单job.
 * 
 * @author chuangsheng.zhang.
 */
public class DeleteAutoOrderJob extends AbstractJob {

    @Autowired
    private IAutoshipOrderService autoshipOrderService;

    /*
     * (non-Javadoc)
     * 
     * @see com.lkkhpg.dsis.platform.job.AbstractJob#safeExecute(org.quartz.
     * JobExecutionContext)
     */
    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        // TODO Auto-generated method stub
        autoshipOrderService.cancelAutoshipOrder();
        this.setExecutionSummary("Finish as " + new Date());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.lkkhpg.dsis.platform.job.AbstractJob#isRefireImmediatelyWhenException
     * ()
     */
    @Override
    public boolean isRefireImmediatelyWhenException() {
        // TODO Auto-generated method stub
        return false;
    }

}
