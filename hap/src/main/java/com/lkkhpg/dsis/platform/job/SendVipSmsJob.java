/*
 *
 */

package com.lkkhpg.dsis.platform.job;

import java.util.HashMap;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.platform.service.ISmsService;

/**
 * 发送消息,Sms的job.
 * 
 * @author Clerifen Li
 */
public class SendVipSmsJob extends AbstractJob {

    private Logger logger = LoggerFactory.getLogger(SendVipSmsJob.class);

    @Autowired
    private ISmsService smsService;

    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        try {
            smsService.sendMessage(true, new HashMap<String, Object>());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        // TODO Auto-generated method stub
        return false;
    }
}
