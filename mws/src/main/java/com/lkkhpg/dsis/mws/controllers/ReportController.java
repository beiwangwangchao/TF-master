/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.report.service.ISysReportTemplateService;
import com.lkkhpg.dsis.common.report.service.ReportProgramService;
import com.lkkhpg.dsis.common.system.dto.SysReportParams;
import com.lkkhpg.dsis.common.system.dto.SysReportProgram;
import com.lkkhpg.dsis.common.system.dto.SysReportTemplate;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.report.constant.ReportConstant;
import com.lkkhpg.dsis.platform.report.exception.ReportException;
import com.lkkhpg.dsis.platform.service.attachment.IAttachCategoryService;

/**
 * 报表控制器.
 *
 * @author chenjingxiong
 */
@Controller
public class ReportController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ISysReportTemplateService sysReportTemplateService;

    @Autowired
    private IAttachCategoryService attachCategoryService;

    @Autowired
    private ReportProgramService reportProgramService;

    public static final String BASE_PATH = "${basePath}";

    public static final String BIRT_REPORT = "birt";

    public static final String HTML_REPORT = "html";

    @RequestMapping("/report/submit")
    public ModelAndView report(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam Map<String, Object> allRequestParams) throws ReportException, IOException {

        String dataService = (String) allRequestParams.get("dataService");
        String templateCode = (String) allRequestParams.get("templateCode");
        String docType = (String) allRequestParams.get("docType");

        String template = null;
        String fileName = null;

        ModelAndView mv = new ModelAndView();

        IRequest requestContext = createRequestContext(request);

        SysReportTemplate sysReportTemplate = new SysReportTemplate();
        sysReportTemplate.setTemplateCode(templateCode);
        List<SysReportTemplate> list = sysReportTemplateService.queryReportTemplate(requestContext, sysReportTemplate,
                1, 1);
        if (list != null && list.size() > 0) {
            sysReportTemplate = list.get(0);
            // 直接获取模板的绝对路径
            template = sysReportTemplate.getFilePath();
            fileName = sysReportTemplate.getFileName();
        }
        if (template != null && fileName != null) {
            String reportType = determineReportType(fileName, dataService);
            mv = redirectView(reportType, template, docType, allRequestParams);
        }

        return mv;
    }

    @RequestMapping("/report/run")
    public ModelAndView report1(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam Map<String, Object> allRequestParams) throws ReportException, IOException {

        // Long reportProgramId = Long.parseLong((String)
        // allRequestParams.get("reportProgramId"));
        allRequestParams.put("memberCode", createRequestContext(request).getAttribute(Member.FIELD_MEMBER_CODE));
        String reportProgramCode = (String) allRequestParams.get("reportProgramCode");
        SysReportProgram reportProgram = reportProgramService.getReportProgramByCode(createRequestContext(request),
                reportProgramCode);
        String dataService = reportProgram.getDataServiceName();
        String templateCode = reportProgram.getTemplateCode();
        String docType = (String) allRequestParams.get("docType");
        // String dataService = (String) allRequestParams.get("dataService");
        // String templateCode = (String) allRequestParams.get("templateCode");

        String template = null;
        String fileName = null;

        ModelAndView mv = new ModelAndView();

        IRequest requestContext = createRequestContext(request);

        SysReportTemplate sysReportTemplate = new SysReportTemplate();
        sysReportTemplate.setTemplateCode(templateCode);
        List<SysReportTemplate> list = sysReportTemplateService.queryReportTemplate(requestContext, sysReportTemplate,
                1, 1);
        if (list != null && list.size() > 0) {
            sysReportTemplate = list.get(0);
            // 直接获取模板的绝对路径
            template = sysReportTemplate.getFilePath();
            fileName = sysReportTemplate.getFileName();
        }
        if (template != null && fileName != null) {
            String reportType = determineReportType(fileName, dataService);
            mv = redirectView(reportType, template, docType, allRequestParams);
            mv.addObject(ReportConstant.DATA_SERVICE_NAME, dataService);
        }

        return mv;
    }

    private String determineReportType(String fileName, String dataService) {
        return fileName.endsWith(".html") && dataService != null ? HTML_REPORT : BIRT_REPORT;
    }

    private ModelAndView redirectView(String reportType, String template, String docType,
                                      Map<String, Object> allRequestParams) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAllObjects(allRequestParams);
        if (HTML_REPORT.equals(reportType)) {
            modelAndView.setViewName("redirect:/report/view");
        } else {
            modelAndView.setViewName("redirect:/birtReport/view");
        }

        modelAndView.addObject(ReportConstant.TEMPLATE, template);
        modelAndView.addObject(ReportConstant.EXPORT_TYPE, docType);
        allRequestParams.remove("template");
        allRequestParams.remove("dataService");
        allRequestParams.remove("docType");

        return modelAndView;
    }

    /**
     * 保存报表程序定义.
     *
     * @param request        请求上下文
     * @param reportPrograms 报表程序dto
     * @param result         校验结果
     * @return 报表程序定义
     * @throws CommSystemProfileException 系统配置统一异常
     */
    @RequestMapping("/sys/reportProgram/save")
    @ResponseBody
    public ResponseData saveReportProgram(HttpServletRequest request,
                                          @RequestBody List<SysReportProgram> reportPrograms, BindingResult result)
            throws CommSystemProfileException {
        getValidator().validate(reportPrograms, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(reportProgramService.saveReportProgram(requestContext, reportPrograms));
    }

    /**
     * 删除报表程序参数.
     *
     * @param request         请求上下文
     * @param sysReportParams 报表程序参数dto
     * @return 报表程序参数列表
     */
    @RequestMapping(value = "/sys/reportProgram/deleteParams")
    @ResponseBody
    public ResponseData deleteReportParams(HttpServletRequest request,
                                           @RequestBody List<SysReportParams> sysReportParams) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(reportProgramService.deleteReportParams(requestContext, sysReportParams));
    }

    /**
     * 根据id查询报表程序.
     *
     * @param request         请求上下文
     * @param reportProgramId 报表程序id
     * @return 报表程序
     */
    @RequestMapping(value = "/sys/reportProgram/get")
    @ResponseBody
    public ResponseData getReportProgram(HttpServletRequest request, Long reportProgramId) {
        IRequest requestContext = createRequestContext(request);
        List<SysReportProgram> list = new ArrayList<SysReportProgram>();
        SysReportProgram reportProgram = reportProgramService.getReportProgram(requestContext, reportProgramId);
        list.add(reportProgram);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/sys/reportProgram/getByCode")
    @ResponseBody
    public ResponseData getReportProgramByCode(HttpServletRequest request, String programCode) {
        IRequest requestContext = createRequestContext(request);
        List<SysReportProgram> list = new ArrayList<SysReportProgram>();
        SysReportProgram reportProgram = reportProgramService.getReportProgramByCode(requestContext, programCode);
        list.add(reportProgram);
        return new ResponseData(list);
    }

    /**
     * 根据id查询报表程序参数.
     *
     * @param request         请求上下文
     * @param reportProgramId 报表程序id
     * @return 报表程序参数列表
     */
    @RequestMapping(value = "/sys/reportParams/get")
    @ResponseBody
    public ResponseData getReportParams(HttpServletRequest request, Long reportProgramId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(reportProgramService.getReportParams(requestContext, reportProgramId));
    }

    /**
     * 报表运行界面.
     *
     * @param request         请求上下文
     * @param programCode 
     * @param reportProgramId 报表程序id
     * @return 报表运行界面
     */
    @RequestMapping(value = "/sys/report/sys_report_run.html")
    @ResponseBody
    public ModelAndView maintainReportRun(HttpServletRequest request,
                                          @RequestParam(required = false) String programCode) {
        return processMaintainView(request, programCode, "/sys/report/sys_report_run");
    }

    /**
     * 报表运行界面.
     *
     * @param request         请求上下文
     * @param reportProgramId 报表程序id
     * @param viewName        页面名称
     * @return 页面视图
     */
    private ModelAndView processMaintainView(HttpServletRequest request,
                                             @RequestParam(required = false) String programCode, String viewName) {
        ModelAndView view = new ModelAndView(getViewPath() + viewName);
        IRequest requestContext = createRequestContext(request);
        SysReportProgram reportProgram = reportProgramService.getReportProgramByCode(requestContext, programCode);
        //SysReportProgram reportProgram = reportProgramService.getReportProgram(requestContext, reportProgramId);
        List<SysReportParams> reportParams = reportProgram.getReportParams();
        if (reportParams != null) {
            view.addObject("reportParams", reportParams);

            List<SysReportParams> linkParams = new ArrayList<SysReportParams>();
            for (SysReportParams reportParam : reportParams) {
                String fieldLinkEvent = reportParam.getFieldLinkEvent();
                String fieldLinkRule = reportParam.getFieldLinkRule();
                if (fieldLinkEvent != null && !"".equals(fieldLinkEvent)
                        && fieldLinkRule != null && !"".equals(fieldLinkRule)) {
                    linkParams.add(reportParam);
                }
            }
            view.addObject("linkParams", linkParams);
        }
        return view;
    }

    /**
     * 查询报表程序.
     *
     * @param request          请求上下文
     * @param sysReportProgram 报表程序dto
     * @return 报表程序列表
     */
    @RequestMapping(value = "/sys/reportProgram/query")
    @ResponseBody
    public ResponseData queryReportProgram(HttpServletRequest request, SysReportProgram sysReportProgram, int page, int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(reportProgramService.queryReportProgram(requestContext, sysReportProgram, page, pagesize));
    }

    @RequestMapping(value = "/sys/reportProgram/delete")
    @ResponseBody
    public ResponseData deleteReportProgram(HttpServletRequest request, Long reportProgramId) throws Exception {
        IRequest requestContext = createRequestContext(request);
        List<Long> list = new ArrayList<Long>();
        Long result = reportProgramService.deleteReportProgram(requestContext, reportProgramId);
        list.add(result);
        return new ResponseData(list);
    }

}
