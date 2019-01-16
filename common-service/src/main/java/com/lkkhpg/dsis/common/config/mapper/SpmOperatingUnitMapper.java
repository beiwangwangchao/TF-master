/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmOperatingUnit;
/**
 * 业务实体MAPPER.
 * @author hanrui.huang
 *
 */
public interface SpmOperatingUnitMapper {
    int deleteByPrimaryKey(Long operatingUnitId);

    int insert(SpmOperatingUnit record);

    int insertSelective(SpmOperatingUnit record);

    SpmOperatingUnit selectByPrimaryKey(Long operatingUnitId);

    int updateByPrimaryKeySelective(SpmOperatingUnit record);

    int updateByPrimaryKey(SpmOperatingUnit record);
    
    List<SpmOperatingUnit> queryBySpmOperatingUnit(SpmOperatingUnit record);

    List<SpmOperatingUnit> queryBySpmOperatingUnit2(SpmOperatingUnit record);
    
    int updateBySpmOperatingUnit(SpmOperatingUnit record);
    
    Integer checkRepeatByCode(String operatingUnitCode);
}