/*
 *
 */
package com.lkkhpg.dsis.admin.system;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lkkhpg.dsis.common.system.dto.SysMsMessageAssign;
import com.lkkhpg.dsis.common.system.dto.SysMsScheduleMessage;
import com.lkkhpg.dsis.platform.job.AbstractJob;
import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;

/**
 * @author shiliyan
 *
 */
public abstract class ManualMessagePublishJob extends AbstractJob {

    protected void afterPublish(Map<SysMsScheduleMessage, List<SysMsMessageAssign>> publishSchedulUserMessage) {
        int sms = 0;
        int site = 0;
        int email = 0;
        Set<SysMsScheduleMessage> keySet = publishSchedulUserMessage.keySet();
        if (keySet != null) {
            for (SysMsScheduleMessage sysMsScheduleMessage : keySet) {
                String publishChannel = sysMsScheduleMessage.getPublishChannel();
                MessageTypeEnum valueOf = MessageTypeEnum.valueOf(publishChannel);
                switch (valueOf) {
                case SMS:
                    sms++;
                    break;
                case EMAIL:
                    email++;
                    break;
                case DSIS:
                    site++;
                    break;
                default:
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[EMAIL: ");
        sb.append(email);
        sb.append("][SMS: ");
        sb.append(sms);
        sb.append("][SITE: ");
        sb.append(site);
        sb.append("]");
        this.setExecutionSummary(sb.toString());
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        return false;
    }

}
