/*
 *
 */
package com.lkkhpg.dsis.common.config.mapper;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.OrgParam;

/**
 * 组织参数Mapper.
 * @author chenjingxiong
 */
public interface OrgParamMapper {
    int deleteByPrimaryKey(Long orgParamId);

    int insert(OrgParam record);

    int insertSelective(OrgParam record);

    OrgParam selectByPrimaryKey(Long orgParamId);
    
    int deleteByOrg(@Param("orgType") String orgType, @Param("orgId") Long orgId);

    int updateByPrimaryKeySelective(OrgParam record);

    int updateByPrimaryKey(OrgParam record);
}