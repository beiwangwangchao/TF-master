package com.lkkhpg.dsis.admin.order.job;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.member.job.ApplyRoleJob;
import com.lkkhpg.dsis.common.service.IVoucherService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.job.AbstractJob;

public class InvalidVoucherJob extends AbstractJob {
    
    private Logger logger = LoggerFactory.getLogger(ApplyRoleJob.class);
    
    @Autowired
    private IVoucherService voucherService;
    
    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        IRequest request = RequestHelper.newEmptyRequest();
        request.setLocale(BaseConstants.DEFAULT_LANG);
        request.setAccountId(-1L);
        voucherService.invalidVoucherForJob(request);
        this.setExecutionSummary("success to run the apply role job");
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        if (logger.isErrorEnabled()) {
            logger.error("error to run the invalidVoucherJob");
        }
        return false;
    }

}
