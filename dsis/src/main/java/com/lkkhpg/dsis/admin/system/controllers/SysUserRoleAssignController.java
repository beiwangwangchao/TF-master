/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.system.service.ISysUserRoleAssignService;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.system.dto.SysUserRoleAssign;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.UserRole;

/**
 * 
 * @author peng.li
 */
@Controller
public class SysUserRoleAssignController extends BaseController {

    @Autowired
    private ISysUserRoleAssignService sysUserRoleAssignService;
    
    /**
     * 获取用户销售组织和库存组织.
     * 
     * @param request
     *            请求信息.
     * @param roleId
     *            角色ID.
     * @return Map&lt;String, Object&gt; key:invOrg ,库存组织信息 key:salesOrg ,销售组织信息.
     */
    @RequestMapping("sys/getOrgs")
    @ResponseBody
    public Map<String, Object> getOrgs(HttpServletRequest request, @RequestParam Long roleId) {

        Map<String, Object> result = sysUserRoleAssignService.getOrgs(createRequestContext(request), getUserId(request),
                roleId);

        return result;
    }

    /**
     * 获取当前用户角色可访问的库存组织.
     * 
     * @param request
     *            请求信息.
     * @return 可访问的库存组织.
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("sys/getInvOrgs")
    @ResponseBody
    public List<SpmInvOrganization> getAccessibleInvOrgs(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Long roleId = (Long) session.getAttribute(Role.FIELD_ROLE_ID);
            if (roleId != null) {
                Map<String, Object> result = sysUserRoleAssignService.getOrgs(createRequestContext(request),
                        getUserId(request), roleId);
                return (List<SpmInvOrganization>) result.get(ISysUserRoleAssignService.RESULT_INV_ORG);
            }
        }
        return null;
    }

    /**
     * 获取当前用户角色可访问的销售区域组织.
     * 
     * @param request
     *            请求信息.
     * @return 可访问的销售区域.
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("sys/getSalesOrgs")
    @ResponseBody
    public List<SpmSalesOrganization> getAccessibleSalesOrgs(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Long roleId = (Long) session.getAttribute(Role.FIELD_ROLE_ID);
            if (roleId != null) {
                Map<String, Object> result = sysUserRoleAssignService.getOrgs(createRequestContext(request),
                        getUserId(request), roleId);
                return (List<SpmSalesOrganization>) result.get(ISysUserRoleAssignService.RESULT_SALES_ORG);
            }
        }
        return null;
    }
    
    /**
     * 根据roleId查询SysUserRoleAssign.
     * 
     * @param request
     *            请求上下文.
     * @param roleId 角色Id.
     * @param userId 用户Id.
     * @return 响应信息.
     */
    @RequestMapping(value = "/sys/assign/queryById")
    @ResponseBody
    public ResponseData queryById(HttpServletRequest request, Long roleId, Long userId) {
        IRequest requestContext = createRequestContext(request);
        SysUserRoleAssign sur = new SysUserRoleAssign();
        sur.setRoleId(roleId);
        sur.setUserId(userId);
        return new ResponseData(sysUserRoleAssignService.queryAssign(requestContext, sur));
    }
    
    /**
     * 删除SysUserRoleAssign信息.
     * 
     * @param request
     *            请求上下文.
     * @param sur SysUserRoleAssign的信息集合.
     * @return 响应信息.
     */
    @RequestMapping(value = "/sys/assign/delete")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<SysUserRoleAssign> sur) {
        IRequest requestContext = createRequestContext(request);
        sysUserRoleAssignService.deleteAssign(requestContext, sur);
        return new ResponseData();
    }
    
    /**
     * 保存SysUserRoleAssign信息.
     * 
     * @param request
     *            统一上下文.
     * @param sur SysUserRoleAssign信息集合.
     * @return 响应信息.
     */
    @RequestMapping(value = "/sys/assign/save")
    @ResponseBody
    public ResponseData save(HttpServletRequest request, @RequestBody List<SysUserRoleAssign> sur) {
        IRequest requestContext = createRequestContext(request);
        sysUserRoleAssignService.saveAssign(requestContext, sur);
        return new ResponseData();
    }
    
    /**
     * 如果角色分配有模板则插入assign.
     * 
     * @param request
     *            统一上下文.
     * @param userRoles 用户角色信息.
     * @param result
     *            BindingResult.
     * @return 响应信息.
     */
    @RequestMapping(value = "/sys/userassign/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitUserRole(HttpServletRequest request, @RequestBody List<UserRole> userRoles,
            BindingResult result) {
        getValidator().validate(userRoles, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        sysUserRoleAssignService.saveUserAssign(requestContext, userRoles);
        return new ResponseData();
    }
    
    /**
     * 删除SysUserRoleAssign信息.
     * 
     * @param request 
     *            请求上下文.
     * @param userRoles
     *            用户角色信息.
     * @return 相应信息.
     */
    @RequestMapping(value = "/sys/userassign/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteUserRole(HttpServletRequest request, @RequestBody List<UserRole> userRoles) {
        IRequest requestContext = createRequestContext(request);
        sysUserRoleAssignService.deleteUserAssign(requestContext, userRoles);
        return new ResponseData();
    }
}
