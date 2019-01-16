/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.config.mapper.SpmCurrencyMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISpmCurrencyService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 币种维护.
 * 
 * @author hanrui.huang
 *
 */
@Service
@Transactional
public class SpmCurrencyServiceImpl implements ISpmCurrencyService {

    private final Logger logger = LoggerFactory.getLogger(SpmCurrencyServiceImpl.class);

    @Autowired
    private SpmCurrencyMapper spmCurrencyMapper;

    @Override
    public List<SpmCurrency> querySpmCurrency(IRequest request, SpmCurrency spmCurrency, int page, int pageSize) {
        if (page != -1) {
            PageHelper.startPage(page, pageSize);
        }
        return spmCurrencyMapper.querySpmCurrency(spmCurrency);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmCurrency> saveSpmCurrency(IRequest request, @StdWho List<SpmCurrency> spmCurrencys)
            throws CommSystemProfileException {
        for (SpmCurrency spmCurrency : spmCurrencys) {
            if (spmCurrencyMapper.selectByPrimaryKey(spmCurrency.getCurrencyCode()) == null) {
                // 检查币种名称唯一
                SpmCurrency sc = new SpmCurrency();
                sc.setCurrencyName(spmCurrency.getCurrencyName());
                if (!spmCurrencyMapper.querySpmCurrency(sc).isEmpty()) {
                    // 币种名称唯一性
                    if (logger.isDebugEnabled()) {
                        logger.debug(CommSystemProfileException.MSG_ERROR_SPM_CURRENCY_NAME_UNIQUE + "{}:",
                                spmCurrency.getCurrencyName());
                    }
                    throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_SPM_CURRENCY_NAME_UNIQUE,
                            new Object[] { spmCurrency.getCurrencyName() });
                } else {
                    spmCurrency.setEnabledFlag(SystemProfileConstants.YES);
                    spmCurrencyMapper.insert(spmCurrency);
                }

            } else {
                // 检查币种名称唯一性
                SpmCurrency sc = new SpmCurrency();
                sc.setCurrencyCode(spmCurrency.getCurrencyCode());
                sc.setCurrencyName(spmCurrency.getCurrencyName());
                SpmCurrency sc2 = new SpmCurrency();
                sc2.setCurrencyName(spmCurrency.getCurrencyName());

                if (spmCurrencyMapper.querySpmCurrency(sc).isEmpty()
                        && !spmCurrencyMapper.querySpmCurrency(sc2).isEmpty()) {
                    // 币种名称唯一性
                    if (logger.isDebugEnabled()) {
                        logger.debug(CommSystemProfileException.MSG_ERROR_SPM_CURRENCY_NAME_UNIQUE + "{}:",
                                spmCurrency.getCurrencyName());
                    }
                    throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_SPM_CURRENCY_NAME_UNIQUE,
                            new Object[] { spmCurrency.getCurrencyName() });
                } else {
                    spmCurrencyMapper.updateByPrimaryKeySelective(spmCurrency);
                }
            }
        }
        return spmCurrencys;
    }

    @Override
    public BigDecimal toPrecisionValue(String currencyCode, BigDecimal value) {
        SpmCurrency spmCurrency = spmCurrencyMapper.selectByPrimaryKey(currencyCode);
        return toPrecisionValue(spmCurrency, value);
    }

    @Override
    public BigDecimal toPrecisionValue(SpmCurrency currency, BigDecimal value) {
        if (currency == null || currency.getPrecision() == null || value == null) {
            return value;
        }
        return value.setScale(Integer.parseInt(currency.getPrecision().toString()), BigDecimal.ROUND_HALF_UP);
    }

}
