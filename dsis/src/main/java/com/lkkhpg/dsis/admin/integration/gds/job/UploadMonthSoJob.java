/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.job;

import java.text.SimpleDateFormat;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.admin.integration.gds.service.IUploadDailySoService;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * 月結銷售資料上傳JOB.
 * 
 * @author zhenyang.he
 *
 */
public class UploadMonthSoJob extends AbstractJob {

    private Logger logger = LoggerFactory.getLogger(UploadMonthSoJob.class);

    @Autowired
    private IUploadDailySoService uploadDailySoService;

    /**
     * 執行月結銷售資料上傳的JOB （每月的3號，12號）.
     */
    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        // TODO Auto-generated method stub
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(IntegrationException.MSG_ERROR_DAPP_DATE_FORMAT_ERROR);
            String dateFrom = context.getMergedJobDataMap().getString("dateFrom");
            String dateTo = context.getMergedJobDataMap().getString("dateTo");
            IsgGdsProcedureParam isgGdsProcedureParam = new IsgGdsProcedureParam();
            isgGdsProcedureParam.setIntCode(context.getMergedJobDataMap().getString("intCode"));
            isgGdsProcedureParam.setFullOrOffset(context.getMergedJobDataMap().getString("fullOrOffset"));
            isgGdsProcedureParam.setPeriodCode(context.getMergedJobDataMap().getString("periodCode"));
            isgGdsProcedureParam.setPeriodCode(context.getMergedJobDataMap().getString("periodCode"));
            isgGdsProcedureParam.setDateFrom(formatter.parse(dateFrom));
            isgGdsProcedureParam.setDateTo(formatter.parse(dateTo));
            isgGdsProcedureParam.setTimeZone(context.getMergedJobDataMap().getString("timeZone"));
            isgGdsProcedureParam.setLangCode(context.getMergedJobDataMap().getString("langCode"));
            isgGdsProcedureParam.setRetryFlag(context.getMergedJobDataMap().getString("retryFlag"));
            isgGdsProcedureParam.setClearFlag(context.getMergedJobDataMap().getString("clearFlag"));
            int result = uploadDailySoService.uploadDailySo(isgGdsProcedureParam);
            if (logger.isDebugEnabled()) {
                logger.debug("result: {}", new Object[] { result });
                logger.debug("resultStatus: {}", new Object[] { isgGdsProcedureParam.getReturnStatus() });
                logger.debug("returnMessage: {}", new Object[] { isgGdsProcedureParam.getReturnMessage() });
            }
        } catch (Exception e) {
            // TODO: handle exception
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
