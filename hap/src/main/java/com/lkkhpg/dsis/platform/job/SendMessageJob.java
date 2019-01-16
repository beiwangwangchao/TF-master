/*
 *
 */

package com.lkkhpg.dsis.platform.job;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.platform.service.IEmailService;
import com.lkkhpg.dsis.platform.service.ISmsService;

/**
 * 发送消息,邮件的job.
 * 
 * @author Clerifen Li
 */
public class SendMessageJob extends AbstractJob {


    public static final String SUMMARY = "summary";

    private Logger logger = LoggerFactory.getLogger(SendMessageJob.class);

    @Autowired
    private IEmailService mailService;

    @Autowired
    private ISmsService smsService;

    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        try {
            String priority = context.getMergedJobDataMap().getString("priority");
            String type = context.getMergedJobDataMap().getString("type");
            String batchStr = context.getMergedJobDataMap().getString("batch");
            Integer batch = 20;
            try {
                batch = Integer.parseInt(batchStr);
            } catch (Exception e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("batch not specified.");
                }
            }
            boolean vip = "VIP".equalsIgnoreCase(priority);
            if (logger.isDebugEnabled()) {
                logger.debug("begin send message,type:{}, priority:{}, batch:{}", type, priority, batch);
            }

            Map<String, Object> param = new HashMap<>();
            param.put("batch", batch);

            if ("EMAIL".equalsIgnoreCase(type)) {
                mailService.sendMessage(vip, param);
            } else {
                smsService.sendMessage(vip, param);
            }
            setExecutionSummary((String) param.get(SUMMARY));
        } catch (Exception e) {
            setExecutionSummary(e.getMessage());
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        return false;
    }
}
