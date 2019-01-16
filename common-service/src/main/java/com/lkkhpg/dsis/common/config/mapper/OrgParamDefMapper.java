/*
 *
 */

package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.OrgParamDef;
import com.lkkhpg.dsis.common.config.dto.OrgParamDefExample;

/**
 * 组织定义Mapper.
 * @author chenjingxiong
 */
public interface OrgParamDefMapper {
    int deleteByPrimaryKey(Long paramId);

    int insert(OrgParamDef record);

    int insertSelective(OrgParamDef record);

    List<OrgParamDef> selectByExample(OrgParamDefExample example);

    OrgParamDef selectByPrimaryKey(Long paramId);

    int updateByPrimaryKeySelective(OrgParamDef record);

    int updateByPrimaryKey(OrgParamDef record);
    
    List<OrgParamDef> selectByOrgType(@Param("orgType") String orgType);
    
    List<OrgParamDef> selectOrgParamDef();
}