/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.system.dto.SysReportProgram;

/**
 * 报表程序Mapper.
 * 
 * @author wangc
 *
 */
public interface SysReportProgramMapper {
    int deleteByPrimaryKey(Long reportProgramId);

    int insert(SysReportProgram record);

    int insertSelective(SysReportProgram record);

    SysReportProgram selectByPrimaryKey(@Param(value = "reportProgramId") Long reportProgramId);

    int updateByPrimaryKeySelective(SysReportProgram record);

    int updateByPrimaryKey(SysReportProgram record);

    List<SysReportProgram> querySysReportProgram(SysReportProgram record);
    
    int checkReportNameRepeat(String programName);
    
    int checkReportCodeRepeat(String programCode);

    SysReportProgram selectProgramByCode(@Param(value = "programCode") String programCode);
}