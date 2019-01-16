/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.report.service.ISysReportTemplateService;
import com.lkkhpg.dsis.common.system.dto.SysReportTemplate;
import com.lkkhpg.dsis.common.system.dto.SysTemplateAssign;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 模板管理Controller.
 * 
 * @author hanrui.huang
 */
@Controller
public class SysReportTemplateController extends BaseController {

    @Autowired
    private ISysReportTemplateService sysReportTemplateService;

    /**
     * 查询模板.
     * 
     * @param request
     *            请求上下文
     * @param sysReportTemplate
     *            模板DTO
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @return 响应数据
     */
    @RequestMapping(value = "/sys/reportTemplate/query")
    @ResponseBody
    public ResponseData queryReportTemplate(HttpServletRequest request, SysReportTemplate sysReportTemplate,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                sysReportTemplateService.queryReportTemplate(requestContext, sysReportTemplate, page, pagesize));
    }

    /**
     * 保存模板.
     * 
     * @param request
     *            请求上下文
     * @param sysReportTemplates
     *            模板DTO
     * @return 响应数据
     */
    @RequestMapping(value = "/sys/reportTemplate/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveReportTemplate(HttpServletRequest request,
            @RequestBody List<SysReportTemplate> sysReportTemplates) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(sysReportTemplateService.saveReportTemplate(requestContext, sysReportTemplates));
    }

    /**
     * 查询模板分配市场.
     * 
     * @param request
     *            请求上下文
     * @param templateId
     *            模板ID
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @return 响应数据
     */
    @RequestMapping(value = "/sys/templateAssign/query")
    @ResponseBody
    public ResponseData querySysTemplateAssign(HttpServletRequest request, Long templateId,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                sysReportTemplateService.querySysTemplateAssign(requestContext, templateId, page, pagesize));
    }

    /**
     * 删除模板分配市场.
     * 
     * @param sysTemplateAssigns
     *            模板分配市场DTO
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return 响应数据
     */
    @RequestMapping(value = "/sys/templateAssign/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteSysTemplateAssign(@RequestBody List<SysTemplateAssign> sysTemplateAssigns,
            BindingResult result, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(sysReportTemplateService.deleteSysTemplateAssign(requestContext, sysTemplateAssigns));
    }
}
