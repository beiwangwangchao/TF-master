/*
 *
 */
package com.lkkhpg.dsis.platform.report.service;

import java.util.Map;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.report.Report;
import com.lkkhpg.dsis.platform.report.exception.ReportException;

/**
 * 报表生成Service.
 *
 * @author chenjingxiong
 */
public interface IReportService {
    /**
     * 报表生成工具.dataService
     *
     * @param request     统一上下文.
     * @param dataService 数据服务.
     * @param param       参数.
     * @param template    模板.
     * @param exportType  导出类型.
     * @param fileName    文件输出名,为空时生成随机文件名.
     * @param saveOutput  保存输出.
     * @return 报表.
     * @throws ReportException 报表异常.
     */
    public Report generateReport(IRequest request, IReportDataService dataService, Map<String, Object> param, String template, String exportType, String fileName, boolean saveOutput) throws ReportException;

    /**
     * 报表生成工具.
     *
     * @param request    统一上下文.
     * @param param      参数.
     * @param template   模板.
     * @param exportType 导出类型.
     * @param fileName   文件输出名,为空时生成随机文件名.
     * @param saveOutput 保存输出.
     * @return 报表.
     * @throws ReportException 报表异常.
     */
    Report generateReport(IRequest request, Map<String, Object> param, String template, String exportType, String fileName, boolean saveOutput) throws ReportException;
}
