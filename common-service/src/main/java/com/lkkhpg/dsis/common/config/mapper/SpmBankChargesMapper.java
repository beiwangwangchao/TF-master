/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmBankCharges;

/**
 * 银行手续费Mapper.
 * @author liuxuan
 *
 */
public interface SpmBankChargesMapper {
    int deleteByPrimaryKey(Long bankChargesId);

    int insert(SpmBankCharges record);

    int insertSelective(SpmBankCharges record);

    SpmBankCharges selectByPrimaryKey(Short bankChargesId);

    int updateByPrimaryKeySelective(SpmBankCharges record);

    int updateByPrimaryKey(SpmBankCharges record);
    
    int queryCount(SpmBankCharges bankCharces);
    
    List<SpmBankCharges> querySpmBankCharces(SpmBankCharges record);
    
}