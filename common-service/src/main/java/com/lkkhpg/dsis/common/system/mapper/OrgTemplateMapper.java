/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.OrgTemplate;

/**
 * 组织模板Mapper.
 * 
 * @author runbai.chen
 */
public interface OrgTemplateMapper {

    int deleteByPrimaryKey(OrgTemplate orgTemplate);

    int insert(OrgTemplate record);

    int insertSelective(OrgTemplate record);

    OrgTemplate selectByPrimaryKey(Long orgTemplateId);

    int updateByPrimaryKeySelective(OrgTemplate record);

    int updateByPrimaryKey(OrgTemplate orgTemplate);

    List<OrgTemplate> selectOrgTemplatesByParas(OrgTemplate orgTemplate);

}