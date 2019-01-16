/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISpmPeriodService;
import com.lkkhpg.dsis.platform.core.IRequest;

import javax.servlet.http.HttpSession;

/**
 * 奖金期间差异service实现.
 * 
 * @author Zhaoqi
 *
 */
@Service
@Transactional
public class SpmPeriodServiceImpl implements ISpmPeriodService {

    private final Logger logger = LoggerFactory.getLogger(SpmPeriodServiceImpl.class);

    @Autowired
    private SpmPeriodMapper spmPeriodMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Override
    public List<SpmPeriod> querySpmPeriod(IRequest request, SpmPeriod spmPeriod) {
        return null;
    }

    @Override
    public List<SpmPeriod> queryBonusPeriod(IRequest request, SpmPeriod spmPeriod, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return spmPeriodMapper.querySpmPeriod(spmPeriod);
    }

    @Override
    public SpmPeriod inertSpmPeriod(SpmPeriod spmPeriod) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmPeriod> generateBonusPeriod(IRequest request, SpmPeriod spmPeriod) {
        spmPeriod.setClosingStatus(SystemProfileConstants.CLOSING_STATUS_N);
        spmPeriod.setPeriodType(SystemProfileConstants.PERIOD_TYPE);
        spmPeriod.setOrgType(SystemProfileConstants.ORG_TYPE);
        spmPeriodMapper.insertBonusPeriod(spmPeriod);
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmPeriod> closeBonusPeriod(IRequest request, SpmPeriod spmPeriod) throws CommSystemProfileException {
        SimpleDateFormat sim = new SimpleDateFormat(SystemProfileConstants.DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        String StrDate = sim.format(new Date());
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
            String periodName = year + strMenth;
            spmPeriod.setPeriodName(periodName);
            spmPeriod.setPeriodMonth((long) menth);
            spmPeriod.setEndDate(date);
            spmPeriod.setPeriodType(SystemProfileConstants.PERIOD_TYPE);
        } catch (ParseException e) {
            if(logger.isDebugEnabled()){
                logger.debug(SystemProfileConstants.DATE_FORMAT_ERROR, e);
            }
        }
        if (spmPeriodMapper.isExistsOpenPeriod(spmPeriod) > 0) {
            spmPeriodMapper.closeBonusPeriod(spmPeriod);
        } else {
            throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_NOT_EXISTS_OPEN_PERIOD, null);
        }

        return null;
    }

    @Override
    public SpmPeriod getPeriod(IRequest request, SpmPeriod spmPeriod) {
        List<SpmPeriod> spmPeriods = spmPeriodMapper.selectSpmPeriod(spmPeriod);
        if (!spmPeriods.isEmpty()) {
            return spmPeriods.iterator().next();
        }
        // spmPeriod.setClosingStatus(SystemProfileConstants.CLOSING_STATUS_N);
        // spmPeriod.setPeriodType(SystemProfileConstants.PERIOD_TYPE);
        // spmPeriod.setOrgType(SystemProfileConstants.ORG_TYPE);
        // spmPeriodMapper.insertBonusPeriod(spmPeriod);
        return spmPeriod;
    }

    @Override
    public SpmPeriod getPreviousPeriod(IRequest request, SpmPeriod spmPeriod) {
        SpmPeriod queryParams = new SpmPeriod();
        queryParams.setOrgId(spmPeriod.getOrgId());
        queryParams.setOrgType(spmPeriod.getOrgType());
        queryParams.setPeriodType(spmPeriod.getPeriodType());
        queryParams.setClosingStatus(OrderConstants.NO);
        Long periodMonth = spmPeriod.getPeriodMonth();
        if (periodMonth > SystemProfileConstants.ONE) {
            periodMonth = periodMonth - SystemProfileConstants.ONE;
            queryParams.setPeriodYear(spmPeriod.getPeriodYear());
            queryParams.setPeriodMonth(periodMonth);
        } else {
            periodMonth = SystemProfileConstants.TWELVE;
            Long periodYear = spmPeriod.getPeriodYear();
            periodYear = periodYear - SystemProfileConstants.ONE;
            queryParams.setPeriodYear(periodYear);
            queryParams.setPeriodMonth(periodMonth);
        }
        List<SpmPeriod> spmPeriods = spmPeriodMapper.selectSpmPeriod(queryParams);
        if (spmPeriods == null || spmPeriods.isEmpty()) {
            return null;
        } else {
            return spmPeriods.iterator().next();
        }
    }

    @Override
    public List<SpmMarket> queryMarket(IRequest request, SpmMarket market) {
        Long userId = (Long) request.getAttribute(User.FILED_USER_ID);
      if(userId==1){
          return  spmMarketMapper.queryByMarket(market);
      }
      else  return spmMarketMapper.queryByMarket2(market);
    }

    @Override
    public List<SpmPeriod> getSpmPeriodNameBySalesOrgId(IRequest request, String param) {
        if ("No".equals(param)) {
            return spmPeriodMapper.getSpmPeriodNameBySalesOrgIdNo();
        } else {
            return spmPeriodMapper.getSpmPeriodNameBySalesOrgId();
        }
    }
    
    
    @Override
    public List<SpmPeriod> getSpmPeriodNameBySalesOrgIdA(IRequest request, String param) {
        if ("No".equals(param)) {
            return spmPeriodMapper.getSpmPeriodNameBySalesOrgIdNoA();
        } else {
            return spmPeriodMapper.getSpmPeriodNameBySalesOrgIdA();
        }
    }
    
    @Override
    public List<SpmPeriod> getSpmPeriodNameBySalesOrgIdNoClose(IRequest request) {
        return spmPeriodMapper.getSpmPeriodNameBySalesOrgIdNoClose();
    }

    @Override
    public List<SpmPeriod> queryClosingPeriodInTw(IRequest request) {
        return spmPeriodMapper.queryClosingPeriodInTw();
    }

   
}