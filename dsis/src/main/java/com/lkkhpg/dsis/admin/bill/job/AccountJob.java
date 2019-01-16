package com.lkkhpg.dsis.admin.bill.job;

import com.lkkhpg.dsis.admin.bill.service.IBillDocument;
import com.lkkhpg.dsis.platform.job.AbstractJob;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class AccountJob extends AbstractJob{

    private static Logger log = LoggerFactory.getLogger(AccountJob.class);

@Autowired
private IBillDocument billDocument;
    @Override
    public void safeExecute(JobExecutionContext context) {
        try {
            billDocument.listeningDownloadAccountList();
            log.info("the Download Account Job completed !!!");
            this.setExecutionSummary("Finish as " + new Date());
        } catch (Exception e) {
            this.setExecutionSummary("Finish failed " + e.getMessage());
        }
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        return false;
    }

}