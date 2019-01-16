/*
 *
 */
package com.lkkhpg.dsis.common.report.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.system.dto.SysReportParams;
import com.lkkhpg.dsis.common.system.dto.SysReportProgram;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 报表程序接口.
 * 
 * @author wangc
 */
public interface ReportProgramService extends ProxySelf<ReportProgramService> {

    /**
     * 保存报表程序.
     * 
     * @param request
     *            请求上下文
     * @param reportPrograms
     *            报表程序dto
     * @return 报表程序列表
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    List<SysReportProgram> saveReportProgram(IRequest request, @StdWho List<SysReportProgram> reportPrograms)
            throws CommSystemProfileException;

    /**
     * 保存报表参数.
     * 
     * @param request
     *            请求上下文
     * @param reportParams
     *            报表参数dto
     */

    void insertReportParams(IRequest request, @StdWho SysReportParams reportParams);

    /**
     * 更新报表参数.
     * 
     * @param request
     *            请求上下文
     * @param reportParams
     *            报表参数dto
     */
    void updateReportParams(IRequest request, @StdWho SysReportParams reportParams);

    /**
     * 删除报表参数.
     * 
     * @param request
     *            请求上下文
     * @param reportParams
     *            报表参数dto
     * @return 报表参数列表
     */
    List<SysReportParams> deleteReportParams(IRequest request, @StdWho List<SysReportParams> reportParams);

    /**
     * 根据Id查询报表程序.
     * 
     * @param request
     *            请求上下文
     * @param reportProgramId
     *            报表程序id
     * @return 报表程序
     */
    SysReportProgram getReportProgram(IRequest request, Long reportProgramId);

    /**
     * 根据code查询报表程序.
     * 
     * @param request
     *            请求上下文
     * @param reportProgramCode
     *            报表程序code
     * @return 报表程序
     */
    SysReportProgram getReportProgramByCode(IRequest request, String reportProgramCode);

    /**
     * 查询报表程序.
     * 
     * @param request
     *            请求上下文
     * @param sysReportProgram
     *            报表程序dto
     * @return 报表程序列表
     */
    List<SysReportProgram> queryReportProgram(IRequest request, SysReportProgram sysReportProgram, int page,
            int pagesize);

    /**
     * 根据报表程序id获取报表参数.
     * 
     * @param request
     *            请求上下文
     * @param reportProgramId
     *            报表程序id
     * @return 报表参数列表
     */
    List<SysReportParams> getReportParams(IRequest request, Long reportProgramId);

    /**
     * 删除报表程序.
     * @param request
     *            请求上下文
     * @param reportProgramId
     *            报表程序id
     * @return 删除报表程序
     */
    Long deleteReportProgram(IRequest request, Long reportProgramId);

}
