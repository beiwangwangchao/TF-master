/*
 *
 */
package com.lkkhpg.dsis.platform.job.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.mail.PriorityLevelEnum;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.service.IMessageService;

/**
 * @author shiliyan
 *
 */
public class JobNoticeListener extends DefaultJobListener {

    private static final String VETOED = "Vetoed";

    private static final String EXECUTE_FAILED = "Execute Failed";

    private static final String EXECUTE_FINISH = "Execute Finish";

    private static final String STATUS2 = "status";

    private static final String SCHEDULED_FIRE_TIME = "scheduledFireTime";

    private static final String JOB_NAME = "jobName";

    private static final String JOB_INTERNAL_NOTIFICATION = "job_internal_notification";

    private static final String JOB_INTERNAL_EMAIL_ADDRESS = "job_internal_emailAddress";

    private String name = "JobNoticeListener";

    private IMessageService messageService;

    private String mailTemplate;

    // org.quartz.plugin.runningListener.mailTemplate=email_job_running_notification
    public JobNoticeListener(String mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

    public JobNoticeListener() {
        this.mailTemplate = null;
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * JobListener Interface.
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /*
     * Object[] arguments = { new Integer(7), new
     * Date(System.currentTimeMillis()), "a disturbance in the Force" };
     * 
     * String result = MessageFormat.format( "At {1,time} on {1,date}, there was
     * {2} on planet {0,number,integer}.", arguments);
     */

    public String getName() {
        return name;
    }

    /**
     * @see org.quartz.JobListener#jobToBeExecuted(JobExecutionContext)
     */
    public void jobToBeExecuted(JobExecutionContext context) {
    }

    /**
     * @see org.quartz.JobListener#jobWasExecuted(JobExecutionContext,
     *      JobExecutionException)
     */
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        if (jobException != null) {
            sendMail(context, EXECUTE_FAILED);
        } else {
            sendMail(context, EXECUTE_FINISH);
        }
    }

    /**
     * @see org.quartz.JobListener#jobExecutionVetoed(org.quartz.JobExecutionContext)
     */
    public void jobExecutionVetoed(JobExecutionContext context) {
        sendMail(context, VETOED);
    }

    // job结束后发送邮件，
    private void sendMail(JobExecutionContext context, String status) {
        JobDetail jobDetail = context.getJobDetail();
        String messageAddress = (String) jobDetail.getJobDataMap().get(JOB_INTERNAL_EMAIL_ADDRESS);
        String isSend = (String) jobDetail.getJobDataMap().get(JOB_INTERNAL_NOTIFICATION);

        // String msg = "" + "您好，" + "计划任务" + "{}" + "于" + "{}" + "被" + "{}" +
        // "";

        if (Boolean.valueOf(isSend) && StringUtils.isNotBlank(messageAddress)) {

            Map<String, Object> templateData = new HashMap<>();

            String jobName = jobDetail.getKey().getName();

            Date scheduledFireTime = context.getScheduledFireTime();
            templateData.put(JOB_NAME, jobName);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(scheduledFireTime);
            templateData.put(SCHEDULED_FIRE_TIME, format);
            templateData.put(STATUS2, status);

            List<MessageReceiver> receivers = new ArrayList<>();
            MessageReceiver receiver = new MessageReceiver();
            receiver.setMessageAddress(messageAddress);
            receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
            receivers.add(receiver);
            try {
                WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
                messageService = (IMessageService) wac.getBean("messageServiceImpl");
                if (mailTemplate == null) {
                    messageService.sendEmailMessage(null, null, "DO_NOT_REPLY", "JOB INFO",
                            "JOB:" + jobName + "Fire On :" + format + "Status:" + status, PriorityLevelEnum.NORMAL,
                            receivers, null);
                } else {
                    messageService.sendEmailMessage(null, null, mailTemplate, "DO_NOT_REPLY", templateData, receivers,
                            null);
                }
            } catch (Exception e) {
                if (getLog().isErrorEnabled()) {
                    getLog().error(e.getMessage(), e);
                }
            }
        }
    }

}