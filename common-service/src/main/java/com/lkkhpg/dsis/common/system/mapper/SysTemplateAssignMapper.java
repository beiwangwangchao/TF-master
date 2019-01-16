/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.SysTemplateAssign;

/**
 * 模板分配市场Mapper.
 * 
 * @author hanrui.huang
 *
 */
public interface SysTemplateAssignMapper {
    int deleteByPrimaryKey(Long assignId);

    int insert(SysTemplateAssign record);

    int insertSelective(SysTemplateAssign record);

    SysTemplateAssign selectByPrimaryKey(Long assignId);

    int updateByPrimaryKeySelective(SysTemplateAssign record);

    int updateByPrimaryKey(SysTemplateAssign record);

    List<SysTemplateAssign> querySysTemplateAssign(Long templateId);
}