/*
 *
 */

package com.lkkhpg.dsis.platform.job;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class HelloWorldJob extends AbstractJob {

    private static Logger log = LoggerFactory.getLogger(HelloWorldJob.class);

    @Override
    public void safeExecute(JobExecutionContext context) {

        JobDetail detail = context.getJobDetail();
        JobKey key = detail.getKey();
        TriggerKey triggerKey = context.getTrigger().getKey();
        String msg = "Hello World! - . jobKey:" + key + ", triggerKey:" + triggerKey + ", execTime:" + new Date();
         System.out.println(msg);
        //Say Hello to the World and display the date/time
        if (log.isInfoEnabled()) {
            log.info(msg);
        }
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        return false;
    }

}