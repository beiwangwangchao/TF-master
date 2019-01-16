/*
 *
 */
package com.lkkhpg.dsis.platform.report.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.report.Report;
import com.lkkhpg.dsis.platform.report.constant.ReportConstant;
import com.lkkhpg.dsis.platform.report.exception.ReportException;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;
import com.lkkhpg.dsis.platform.report.service.IReportService;
import com.lkkhpg.dsis.platform.report.util.ReportUtil;

/**
 * 报表Controller.
 * @author chenjingxiong
 */
@Controller
public class ReportViewController extends ReportBaseController {

    private static Logger logger = LoggerFactory.getLogger(ReportViewController.class);
    
    @Autowired
    private IReportService reportService;

    @Autowired
    private BeanFactory beanFactory;

    @RequestMapping("/report/view")
    public void report(HttpServletRequest request, HttpServletResponse response,
            @RequestParam Map<String, Object> allRequestParams) throws ReportException, IOException {
        String dataServiceName = (String) allRequestParams.get(ReportConstant.DATA_SERVICE_NAME);
        String template = (String) allRequestParams.get(ReportConstant.TEMPLATE);
        String exportType = (String) allRequestParams.get(ReportConstant.EXPORT_TYPE);
        IRequest requestContext = createReportRequest(request, response);
        IReportDataService dataService = (IReportDataService) beanFactory.getBean(dataServiceName);
        Report report = reportService.generateReport(requestContext, dataService, allRequestParams, template,
                exportType, null, false);
        ReportUtil.viewReport(response, report);
    }

    @RequestMapping("/birtReport/view")
    public void birtReport(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam Map<String, Object> allRequestParams) throws ReportException, IOException {
        String template = (String) allRequestParams.get(ReportConstant.TEMPLATE);
        String exportType = (String) allRequestParams.get(ReportConstant.EXPORT_TYPE);
        IRequest requestContext = createReportRequest(request, response);
        Report report = reportService.generateReport(requestContext, allRequestParams, template,
                exportType, null, false);
        ReportUtil.viewReport(response, report);
    }
}
