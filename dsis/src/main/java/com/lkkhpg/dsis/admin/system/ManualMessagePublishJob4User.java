/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system;

import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.common.service.IManualMessageService;
import com.lkkhpg.dsis.common.system.dto.SysMsMessageAssign;
import com.lkkhpg.dsis.common.system.dto.SysMsScheduleMessage;

/**
 * @author shiliyan
 *
 */
public class ManualMessagePublishJob4User extends ManualMessagePublishJob {

    @Autowired
    private IManualMessageService manualMessageService;

    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        try {
            Map<SysMsScheduleMessage, List<SysMsMessageAssign>> publishSchedulUserMessage = manualMessageService
                    .publishSchedulUserMessage();
            afterPublish(publishSchedulUserMessage);
        } catch (Exception e) {
            this.setExecutionSummary(e.getMessage());
            throw e;
        }
    }


    @Override
    public boolean isRefireImmediatelyWhenException() {
        return false;
    }

}
