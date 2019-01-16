/*
 *
 */
package com.lkkhpg.dsis.admin.order.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.order.service.ISalesOrderService;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * 定时取消以保存状态下的订单.
 * 
 * @author chuangsheng.zhang.
 */
public class CancelSaveOrderJob extends AbstractJob {

    @Autowired
    private ISalesOrderService salesOrderService;

    /*
     * (non-Javadoc)
     * 
     * @see com.lkkhpg.dsis.platform.job.AbstractJob#safeExecute(org.quartz.
     * JobExecutionContext)
     */
    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        // TODO Auto-generated method stub
        try {
            salesOrderService.cancelWPayORSavedOrder();
            this.setExecutionSummary("Finish as " + new Date());
        } catch (Exception e) {
            e.printStackTrace();
            this.setExecutionSummary("Finish failed " + e.getMessage());
        }
        

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
