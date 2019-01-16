/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.job;

import java.text.SimpleDateFormat;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.admin.integration.gds.service.IUploadDailyDealerService;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * 每日產品基礎資料上傳.
 * 
 * @author fengwanjun
 */
public class UploadDailyDealerJob extends AbstractJob {
    
    private Logger logger = LoggerFactory.getLogger(UploadDailyDealerJob.class);
    
    @Autowired
    private IUploadDailyDealerService uploadDailyDealerService;
    
    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        /**
         * 每日產品基礎資料上傳.
         */
        IsgGdsProcedureParam isgGdsProcedureParam = new IsgGdsProcedureParam();
        String periodCode = null;
        //      isgGdsProcedureParam.setReturnStatus("");
        //      isgGdsProcedureParam.setReturnMessage("");
        isgGdsProcedureParam.setIntCode(context.getMergedJobDataMap().getString("intCode"));
        if (isgGdsProcedureParam.getIntCode() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Request Format");
            }
        }
        
        isgGdsProcedureParam.setFullOrOffset(context.getMergedJobDataMap().getString("fullOrOffset"));
        if (isgGdsProcedureParam.getFullOrOffset() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Request Format");
            }
        }
        
        try {
            if (context.getScheduledFireTime() == null) {
                isgGdsProcedureParam.setDateFrom(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .parse(context.getMergedJobDataMap().getString("dateFrom")));
            } else {
            isgGdsProcedureParam.setDateFrom(context.getScheduledFireTime());
            }
            if (context.getNextFireTime() == null) {
            isgGdsProcedureParam.setDateTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(context.getMergedJobDataMap().getString("dateTo")));
            } else {
                isgGdsProcedureParam.setDateTo(context.getNextFireTime());
            }
            periodCode = new SimpleDateFormat("yyyy").format(
                    isgGdsProcedureParam.getDateFrom())
                    + new SimpleDateFormat("MM").format(isgGdsProcedureParam.getDateFrom());
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Date Format");
            }
        }
        
        isgGdsProcedureParam.setPeriodCode(periodCode);
        isgGdsProcedureParam.setTimeZone(context.getMergedJobDataMap().getString("timeZone"));
        
        isgGdsProcedureParam.setLangCode(context.getMergedJobDataMap().getString("langCode"));
        if (isgGdsProcedureParam.getLangCode() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Request Format");
            }
        }
    
        isgGdsProcedureParam.setRetryFlag(context.getMergedJobDataMap().getString("retryFlag"));
        isgGdsProcedureParam.setClearFlag(context.getMergedJobDataMap().getString("clearFlag"));
    
        int result = uploadDailyDealerService.uploadDailyDealer(isgGdsProcedureParam);
    
        if (logger.isDebugEnabled()) {
            logger.debug("result: {}", new Object[]{result});
            logger.debug("returnStatus: {}", new Object[]{isgGdsProcedureParam.getReturnStatus()});
            logger.debug("returnMessage: {}", new Object[]{isgGdsProcedureParam.getReturnMessage()});
        }
    }
    
    @Override
    public boolean isRefireImmediatelyWhenException() {
        return false;
    }

}