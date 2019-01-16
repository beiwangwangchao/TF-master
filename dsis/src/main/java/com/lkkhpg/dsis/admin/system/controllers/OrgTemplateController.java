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

import com.lkkhpg.dsis.admin.system.service.IOrgTemplateService;
import com.lkkhpg.dsis.common.system.dto.OrgTemplate;
import com.lkkhpg.dsis.common.system.dto.OrgTemplateDtl;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 
 * 组织模板controller.
 * 
 * @author runbai.chen
 *
 */
@Controller
public class OrgTemplateController extends BaseController {

    @Autowired
    private IOrgTemplateService orgTemplateService;

    /**
     * 查询组织模板.
     * @param request 
     * 
     * @param orgTemplate
     *            组织模板查询条件
     * @param page
     *            页面数
     * @param pagesize
     *            页面行数
     * @return 组织模板列表
     */
    @RequestMapping(value = "/sys/orgtemplate/query")
    @ResponseBody
    public ResponseData getOrgTemplate(HttpServletRequest request,OrgTemplate orgTemplate, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(orgTemplateService.selectOrgTemplates(requestContext,orgTemplate, page, pagesize));
    }

    /**
     * 查询组织模板明细.
     * @param request 
     * 
     * @param orgTemplateDtl
     *            组织模板明细查询条件
     * @param page
     *            页面数
     * @param pagesize
     *            页面行数
     * @return 组织模板明细列表
     * 
     */
    @RequestMapping(value = "/sys/orgtemplatedtl/query")
    @ResponseBody
    public ResponseData getOrgTemplateDtl(HttpServletRequest request,OrgTemplateDtl orgTemplateDtl,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(orgTemplateService.selectOrgTemplateDtls(requestContext,orgTemplateDtl, page, pagesize));
    }

    /**
     * 提交模板.
     * 
     * @param request
     *            请求信息
     * @param orgTemplates
     *            组织模板
     * @param result
     *            校验结果
     * @return 提交后的组织模板
     */
    @RequestMapping(value = "/sys/orgtemplate/submit", method = RequestMethod.POST)
    public ResponseData submitOrgTemplates(HttpServletRequest request, @RequestBody List<OrgTemplate> orgTemplates,
            BindingResult result) {
        getValidator().validate(orgTemplates, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(orgTemplateService.batchUpdate(requestContext, orgTemplates));
    }

    /**
     * 删除模板信息.
     * 
     * @param request
     *            请求信息
     * @param orgTemplates
     *            组织模板
     * @return 数据返回对象
     * 
     */
    @RequestMapping(value = "/sys/orgtemplate/remove", method = RequestMethod.POST)
    public ResponseData deleteOrgTemplate(HttpServletRequest request, @RequestBody List<OrgTemplate> orgTemplates) {
        IRequest requestContext = createRequestContext(request);
        orgTemplateService.batchOrgTemplateDelete(requestContext, orgTemplates);
        return new ResponseData();
    }

    /**
     * 删除模板明细信息.
     * 
     * @param request
     *            请求信息
     * @param orgTemplateDtls
     *            模板明细信息
     * @return 数据返回对象
     */
    @RequestMapping(value = "/sys/orgtemplatedtl/remove", method = RequestMethod.POST)
    public ResponseData removeOrgTemplateDtl(HttpServletRequest request,
            @RequestBody List<OrgTemplateDtl> orgTemplateDtls) {
        IRequest requestContext = createRequestContext(request);
        orgTemplateService.batchOrgTemplateDtlDelete(requestContext, orgTemplateDtls);
        return new ResponseData();
    }

}
