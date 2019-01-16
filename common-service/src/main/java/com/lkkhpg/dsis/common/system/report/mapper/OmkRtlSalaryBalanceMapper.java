/*
 *
 */
package com.lkkhpg.dsis.common.system.report.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.report.dto.OmkRtlSalaryBalance;

/**
 * dto.
 * @author HuangJiaJing
 *
 */
public interface OmkRtlSalaryBalanceMapper {
    int insert(OmkRtlSalaryBalance record);

    int insertSelective(OmkRtlSalaryBalance record);
    
    List<OmkRtlSalaryBalance> queryRtlSalaryBalance(OmkRtlSalaryBalance record);
    
}