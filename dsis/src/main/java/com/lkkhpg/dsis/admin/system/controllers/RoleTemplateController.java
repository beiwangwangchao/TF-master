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

import com.lkkhpg.dsis.admin.system.service.IRoleTemplateService;
import com.lkkhpg.dsis.common.system.dto.RoleTemplate;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.exception.BaseException;


/**
 * 角色管理显示组织模板.
 * 
 * @author liang.rao
 *
 */
@Controller
public class RoleTemplateController extends BaseController {
    
    @Autowired
    private IRoleTemplateService roleTemplateService;
    
    /**
     * 查询组织模板.
     * 
     * @param request
     *            应用上下文.
     * @return 组织模板信息.
     */
    @RequestMapping(value = "/sys/role/querytemplateCode", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData querytemplateCode(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(roleTemplateService.querytemplateCode(requestContext));
    }
    
    /**
     * 查询所有角色信息.
     * 
     * @param request
     *            应用上下文.
     * @param role 查询条件.
     * @return 角色信息.
     */
    @RequestMapping(value = "/sys/role/queryUserRoles")
    @ResponseBody
    public ResponseData getUserRoleIds(HttpServletRequest request, RoleTemplate role) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(roleTemplateService.selectUserRoles(requestContext, role));
    }
    
    /**
     * 角色查询.
     * 
     * @param role
     *            角色对象.
     * @param page
     *            起始页.
     * @param pagesize
     *            分页大小.
     * @param request
     *            请求上下文.
     * @return ResponseData.
     */
    @RequestMapping(value = "/sys/role/queryTemplate")
    @ResponseBody
    public ResponseData getRoles(RoleTemplate role, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(roleTemplateService.selectRoles(requestContext, role, page, pagesize));
    }
    
    /**
     * 提交角色信息.
     * 
     * @param roles 提交的参数，包含角色信息.
     * @param result BindingResult.
     * @param request
     *            应用上下文.
     * @return 返回结果.
     * @throws BaseException
     *             BaseException.
     */
    @RequestMapping(value = "/sys/role/submitTemplate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitRoleTemplate(@RequestBody List<RoleTemplate> roles, BindingResult result, 
            HttpServletRequest request)
            throws BaseException {
        getValidator().validate(roles, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(roleTemplateService.batchUpdate(requestContext, roles));
    }
    
    
    /**
     * 提交角色信息.
     * 
     * @param roles 提交的参数，包含角色信息.
     * @param request
     *            应用上下文.
     * @return 返回结果.
     * @throws BaseException
     *             BaseException.
     */
    @RequestMapping(value = "/sys/role/deleteTemplate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteRoleTemplate(@RequestBody List<RoleTemplate> roles, HttpServletRequest request)
            throws BaseException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(roleTemplateService.batchDelete(requestContext, roles));
    }
}
