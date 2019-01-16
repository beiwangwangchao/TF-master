/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmCurrency;

/**
 * 币种Mapper.
 * @author wuyichu
 */
public interface SpmCurrencyMapper {
    int deleteByPrimaryKey(String currencyCode);

    int insert(SpmCurrency record);

    int insertSelective(SpmCurrency record);

    SpmCurrency selectByPrimaryKey(String currencyCode);

    int updateByPrimaryKeySelective(SpmCurrency record);

    int updateByPrimaryKey(SpmCurrency record);

    List<SpmCurrency> querySpmCurrency(SpmCurrency record);
}