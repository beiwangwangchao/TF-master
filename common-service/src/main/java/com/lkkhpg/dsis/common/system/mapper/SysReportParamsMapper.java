/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.SysReportParams;

/**
 * 报表程序Mapper.
 * 
 * @author wangc
 *
 */
public interface SysReportParamsMapper {
    int deleteByPrimaryKey(Long reportParamsId);

    int insert(SysReportParams record);

    int insertSelective(SysReportParams record);

    SysReportParams selectByPrimaryKey(Long reportParamsId);

    int updateByPrimaryKeySelective(SysReportParams record);

    int updateByPrimaryKey(SysReportParams record);

    List<SysReportParams> querySysReportParams(SysReportParams record);

    List<SysReportParams> queryByReportProgramId(Long reportProgramId);

}