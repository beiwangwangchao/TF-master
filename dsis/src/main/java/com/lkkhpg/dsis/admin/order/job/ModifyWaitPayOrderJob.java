package com.lkkhpg.dsis.admin.order.job;

import com.lkkhpg.dsis.admin.order.service.ISalesOrderService;
import com.lkkhpg.dsis.platform.job.AbstractJob;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 定时修改待付款状态下的订单.
 *
 * @author furong.tang.
 *
 *  2018.1.17
 */
public class ModifyWaitPayOrderJob extends AbstractJob {

    @Autowired
    private ISalesOrderService salesOrderService;



    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        try {
            salesOrderService.modifyWPayOrder();
            this.setExecutionSummary("Finish as " + new Date());
        } catch (Exception e) {
            e.printStackTrace();
            this.setExecutionSummary("Finish failed " + e.getMessage());
        }
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        return false;
    }
}
