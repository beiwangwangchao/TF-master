/*
 *
 */

package com.lkkhpg.dsis.platform.job;

import java.util.HashMap;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.platform.service.IEmailService;

/**
 * 发送消息,邮件的job.
 * 
 * @author Clerifen Li
 */
public class SendVipMessageJob extends AbstractJob {

    private Logger logger = LoggerFactory.getLogger(SendVipMessageJob.class);

    @Autowired
    private IEmailService mailService;

    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        try {
            mailService.sendMessage(true, new HashMap<String, Object>());
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
