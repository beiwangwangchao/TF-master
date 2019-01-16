/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.math.BigDecimal;
import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 币种.
 * 
 * @author hanrui.huang
 *
 */
public interface ISpmCurrencyService extends ProxySelf<ISpmCurrencyService> {

    /**
     * 币种查询.
     * 
     * @param request
     *            请求上下文
     * @param spmCurrency
     *            币种DTO
     * @param page
     *            页
     * @param pageSize
     *            页大小
     * @return 响应数据
     */
    List<SpmCurrency> querySpmCurrency(IRequest request, SpmCurrency spmCurrency, int page, int pageSize);

    /**
     * 保存币种.
     * 
     * @param request
     *            请求上下文
     * @param spmCurrencys
     *            币种DTO
     * @return 响应数据
     * @throws CommSystemProfileException
     *             系统配置异常
     */
    List<SpmCurrency> saveSpmCurrency(IRequest request, @StdWho List<SpmCurrency> spmCurrencys)
            throws CommSystemProfileException;

    /**
     * 截取value对应币种的精确数.
     * 
     * @param currencyCode
     *            币种代码
     * @param value
     *            金额
     * @return 币种精确值
     */
    BigDecimal toPrecisionValue(String currencyCode, BigDecimal value);

    /**
     * 截取value对应币种的精确数.
     * 
     * @param currency
     *            币种
     * @param value
     *            金额
     * @return 币种精确值
     */
    BigDecimal toPrecisionValue(SpmCurrency currency, BigDecimal value);
}
