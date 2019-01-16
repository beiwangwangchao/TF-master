/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.SysReportTemplate;

/**
 * 报表模板Mapper.
 * 
 * @author hanrui.huang
 *
 */
public interface SysReportTemplateMapper {
    int deleteByPrimaryKey(Long templateId);

    int insert(SysReportTemplate record);

    int insertSelective(SysReportTemplate record);

    SysReportTemplate selectByPrimaryKey(Long templateId);

    int updateByPrimaryKeySelective(SysReportTemplate record);

    int updateByPrimaryKey(SysReportTemplate record);

    List<SysReportTemplate> querySysReportTemplate(SysReportTemplate record);
}