/*
 * #{copyright}#
 */
package com.lkkhpg.dsis.admin.config.job;

import com.lkkhpg.dsis.admin.config.service.ISpmInvNumberingService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.platform.job.AbstractJob;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 监控空白字轨数量.
 * 
 * @author linyuheng
 */
public class MonitorAvailableBlankTrackcodeJob extends AbstractJob {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISpmInvNumberingService spmInvNumberingService;
    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {
        List<SpmMarket> spmMarkets = spmMarketMapper.getEnableEinvoiceMarkets(null, null);
        if (spmMarkets != null) {
            for (SpmMarket spmMarket : spmMarkets) {
                try {
                    spmInvNumberingService.monitorAvailableBlankTrackcode(spmMarket.getCode());
                } catch (Exception e) {
                    this.setExecutionSummary(e.getMessage());
                    if (logger.isErrorEnabled()) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        this.setExecutionSummary("success to run the job");
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        logger.error("Fail to run job");
        return false;
    }

}
