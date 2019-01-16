/*
 *
 */
package com.lkkhpg.dsis.admin.config.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.config.service.ISpmClosePeriodService;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IClosePeriodService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.message.IMessagePublisher;

/**
 * 关闭期间-调用接口的实现.
 * 
 * @author Zhaoqi
 *
 */
@Service
public class SpmClosePeriodServiceImpl implements ISpmClosePeriodService {

    private final Logger logger = LoggerFactory.getLogger(SpmClosePeriodServiceImpl.class);

    @Autowired
    private IClosePeriodService closePeriodService;

    @Autowired
    private SpmPeriodMapper spmPeriodMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private IMessagePublisher publish;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmPeriod> closeBonusPeriod(IRequest request, SpmPeriod spmPeriod)
            throws IntegrationException, CommSystemProfileException {
        List<SpmPeriod> spmPeriods = new ArrayList<SpmPeriod>();
        // Long marketId =
        // request.getAttribute(SystemProfileConstants.MARKET_ID);
        SpmMarket smpMarket = new SpmMarket();
        if (spmPeriod.getOrgId() != null || !"".equals(spmPeriod.getOrgId())) {
            smpMarket.setMarketId(spmPeriod.getOrgId());
        }
        SimpleDateFormat sim = new SimpleDateFormat(SystemProfileConstants.DATE_FORMAT);
        List<SpmMarket> list = spmMarketMapper.queryByMarket(smpMarket);
        Calendar cal = Calendar.getInstance();
        String StrDate = sim.format(new Date());
        String periodName = "";
        try {
            Date date = sim.parse(StrDate);
            spmPeriod.setEndDate(date);
            spmPeriod.setClosingStatus(SystemProfileConstants.CLOSING_STATUS_Y);
            cal.setTime(new Date());
            int menth = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            String strMenth = "";
            if (menth < SystemProfileConstants.MONTH_FORMAT) {
                strMenth = SystemProfileConstants.DATE_MONTH + menth;
            } else {
                strMenth = String.valueOf(menth);
            }
            periodName = year + strMenth;
            // Mclin修改：添加设置，将periodName设置到spmPeriod对象里面
            spmPeriod.setPeriodName(periodName);
        } catch (ParseException e) {
            logger.debug(SystemProfileConstants.DATE_FORMAT_ERROR, e);
        }
        for (SpmMarket sk : list) {
            closePeriodService.closePeriod(request, sk.getCode(), periodName);
            // houmin add 组装奖金期间集合
            SpmPeriod sp1 = new SpmPeriod();
            sp1.setOrgId(sk.getMarketId());
            sp1.setPeriodName(periodName);
            spmPeriods.add(sp1);
            // Mclin修改：关闭区间联动关闭
            List<Long> refMarketIds = gdsUtilService.getRefMarketIds(sk.getCode());
            for (Long marketId : refMarketIds) {
                // spmPeriod.setOrgId(sk.getMarketId());
                spmPeriod.setOrgId(marketId);
                if (spmPeriodMapper.isExistsOpenPeriod(spmPeriod) > 0) {
                    spmPeriodMapper.closeBonusPeriod(spmPeriod);
                    // houmin add 组装奖金期间集合
                    if (!marketId.equals(sk.getMarketId())) {
                        SpmPeriod sp2 = new SpmPeriod();
                        sp2.setOrgId(marketId);
                        sp2.setPeriodName(periodName);
                        spmPeriods.add(sp2);
                    }
                } else {
                    // Mclin修改：改为debug，不抛出异常
                    // throw new
                    // CommSystemProfileException(CommSystemProfileException.MSG_ERROR_NOT_EXISTS_OPEN_PERIOD,
                    // null);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Market id {} is closed yet, ignore.", marketId);
                    }
                }
            }
        }
        logger.info("Starting do Service Center and Ipoint Center bonus adjust After closed [{}] BonusPeriod.");
        // houmin 关闭奖金期间后，执行生成奖金记录处理
        self().doBonusAdjust(spmPeriods);
        logger.info("Do Service Center and Ipoint Center bonus adjust ended.");
        return null;
    }

    @Override
    public void doBonusAdjust(List<SpmPeriod> spmPeriods) {
        for (SpmPeriod spmPeriod : spmPeriods) {
            publish.rPush("BIZ:CAL_IC_BONUS", spmPeriod);
            publish.rPush("BIZ:CAL_SC_BONUS", spmPeriod);
        }
    }
}
