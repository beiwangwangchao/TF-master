/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.member.service.IMemberJobService;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * 会员定时终止(每个月的4号批量执行一次).
 * 
 * @author yuchuan.zeng@hand-china.com
 *
 */
public class TerminateMemberJob extends AbstractJob {

    private Logger logger = LoggerFactory.getLogger(TerminateMemberJob.class);

    @Autowired
    private IMemberJobService memberJobService;

    @Override
    public void safeExecute(JobExecutionContext context) throws IntegrationException {
        // TODO Auto-generated method stub
        /**
         * 定时执行逻辑方法.
         */
        String memberCode = context.getMergedJobDataMap().getString("memberCode");
        
        if (logger.isDebugEnabled()) {
            logger.debug("memberCode: {}", new Object[] { memberCode });
        }
        
        memberJobService.autoTerminateMemberJob(memberCode);
        
        this.setExecutionSummary("Finish as " + new Date());
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        // TODO Auto-generated method stub
        return false;
    }

}
