/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmBank;
/**
 * 银行mapper.
 * @author liuxuan
 *
 */
public interface SpmBankMapper {
    int deleteByPrimaryKey(Long bankId);

    int insert(SpmBank record);

    int insertSelective(SpmBank record);

    SpmBank selectByPrimaryKey(Long bankId);

    int updateByPrimaryKeySelective(SpmBank record);

    int updateByPrimaryKey(SpmBank record);
    
    List<SpmBank> queryBySpmBank(SpmBank record);
    
    List<SpmBank> queryValidBySpmBank(SpmBank record);
    
    Integer queryBySpmBankNumber(@Param("bankNumber") String bankNumber);
    
    Integer queryBySpmBankDate(@Param("endActiveDate") Date endActiveDate);
    
    List<SpmBank> queryBankByNumber(String bankNumber);
  
}