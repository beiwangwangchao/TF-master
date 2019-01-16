/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.OrgParamValue;

/**
 * 组织参数值Mapper.
 * 
 * @author chenjingxiong
 */
public interface OrgParamValueMapper {
    int deleteByPrimaryKey(Long orgParamValId);

    int deleteByOrg(@Param("levelId") Long levelId, @Param("levelValue") String levelValue);

    int insert(OrgParamValue record);

    int insertSelective(OrgParamValue record);

    OrgParamValue selectByPrimaryKey(Long orgParamValId);

    List<OrgParamValue> selectByOrg(@Param("levelId") Long levelId, @Param("levelValue") String levelValue);

    int updateByPrimaryKeySelective(OrgParamValue record);

    int updateByPrimaryKey(OrgParamValue record);

}