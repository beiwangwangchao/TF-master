/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.OrgTemplateDtl;

/**
 * 组织模板明细Mapper.
 * 
 * @author runbai.chen
 */
public interface OrgTemplateDtlMapper {

    int deleteByPrimaryKey(OrgTemplateDtl orgTemplateDtl);

    int deleteByOrgTemplateId(OrgTemplateDtl orgTemplateDtl);

    int insert(OrgTemplateDtl orgTemplateDtl);

    int insertSelective(OrgTemplateDtl orgTemplateDtl);

    OrgTemplateDtl selectByPrimaryKey(Long orgTemplateDtl);

    int updateByPrimaryKeySelective(OrgTemplateDtl orgTemplateDtl);

    int updateByPrimaryKey(OrgTemplateDtl orgTemplateDtl);

    List<OrgTemplateDtl> selectOrgTemplateDtlsByParas(OrgTemplateDtl orgTemplateDtl);
    
    List<OrgTemplateDtl> selectOrgTemplateDtls(OrgTemplateDtl orgTemplateDtl);
    
    List<OrgTemplateDtl> selectOrgTemplateDtlsById(Long orgTemplateDtl);
}