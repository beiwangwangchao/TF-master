/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lkkhpg.dsis.admin.system.report.service.OrganizationService;
import com.lkkhpg.dsis.platform.core.IRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.StringUtil;
import com.lkkhpg.dsis.admin.system.service.ISysUserRoleAssignService;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.system.mapper.SysUserRoleAssignMapper;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.exception.RoleException;

/**
 * 切换组织Controller.
 * 
 * @author zhangYang
 *
 */
@Controller
public class ChangeOrgController {
    private Logger logger = LoggerFactory.getLogger(ChangeOrgController.class);

    @Autowired
    private ISysUserRoleAssignService sysUserRoleAssignService;
    @Autowired
    private SysUserRoleAssignMapper sysUserRoleAssignMapper;
    @Autowired
    private SpmMarketMapper spmMarketMapper;

  @Autowired
   private OrganizationService organizationService;

    /**
     * 根据当前用户角色查询分配组织.
     * 
     * @param request
     *            统一上下文
     * @param response
     * @return 返回查询结果
     */
    @RequestMapping(value = "/sys/changeOrg/queryOrg")
    @ResponseBody
    public ResponseData queryOrgBy(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
        Long roleId = (Long) session.getAttribute(Role.FIELD_ROLE_ID);
        Map<String, Object> map = new HashMap<>();
        map = sysUserRoleAssignService.getOrgs(RequestHelper.createServiceRequest(request), userId, roleId);
        List<Map> list = new ArrayList<>();
        list.add(map);
        return new ResponseData(list);
    }
    @RequestMapping(value = "/sys/om/marketId")
    @ResponseBody
    public ResponseData querOrg(HttpServletRequest request, HttpServletResponse response, Long marketId){
        System.out.println(marketId);
           List<SpmSalesOrganization>listOr  = organizationService.queryOrganization(marketId);
        return  new ResponseData(listOr);
    }


    @RequestMapping(value = "/sys/salesOrg/queryOrg")
    @ResponseBody
    public ResponseData querySalesOrg(HttpServletRequest request, HttpServletResponse response,Long marketId) {
        System.out.println(marketId);
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
        Long roleId = (Long) session.getAttribute(Role.FIELD_ROLE_ID);
        List<SpmSalesOrganization> list = sysUserRoleAssignService
                .getSalesOrgs(RequestHelper.createServiceRequest(request), userId, roleId);

        return new ResponseData(list);
    }
    
    @RequestMapping(value = "/sys/invOrg/queryOrg")
    @ResponseBody
    public ResponseData queryInvOrg(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
        Long roleId = (Long) session.getAttribute(Role.FIELD_ROLE_ID);
        List<SpmInvOrganization> list = sysUserRoleAssignService
                .getInvOrgs(RequestHelper.createServiceRequest(request), userId, roleId);

        return new ResponseData(list);
    }
    /**
     * 切换库存组织与销售组织.
     * 
     * @param request
     * @param response
     * @param invOrgId
     *            库存组织iD
     * @param salesOrgId
     *            销售组织Id
     * @return 切换成功返回true
     */
    @RequestMapping(value = "/sys/changeOrg/changeOrg")
    @ResponseBody
    public ResponseData changeOrg(HttpServletRequest request, HttpServletResponse response, Long invOrgId,
            Long salesOrgId) throws RoleException {
        HttpSession session = request.getSession(false);
        boolean returnSign = true;
        if (session != null) {
            Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
            Long roleId = (Long) session.getAttribute(Role.FIELD_ROLE_ID);
            String invOrgIdStr = null;
            if (invOrgId != null) {
                invOrgIdStr = invOrgId.toString();
                addCookie(SystemProfileConstants.INV_ORG_ID, invOrgId.toString(), request, response);
            }
            String salOrgIdStr = null;
            if (salesOrgId != null) {
                salOrgIdStr = salesOrgId.toString();
                addCookie(SystemProfileConstants.SALES_ORG_ID, salesOrgId.toString(), request, response);
            }
            checkInvOrgAndSalesOrg(userId, roleId, invOrgIdStr, salOrgIdStr); // 验证传入数据正确性
            // 数据正确，切换组织
            session.setAttribute(SystemProfileConstants.INV_ORG_ID, invOrgId);
            session.setAttribute(SystemProfileConstants.SALES_ORG_ID, salesOrgId);
            // 切换市场
            SpmMarket market = spmMarketMapper.selectBySalesOrgId(salesOrgId);
            if (market != null) {
                session.setAttribute(SystemProfileConstants.MARKET_ID, market.getMarketId());
                addCookie(SystemProfileConstants.MARKET_ID, market.getMarketId().toString(), request, response);
            }
            if (logger.isDebugEnabled()) {
                logger.debug(" invOrg and salesOrg has been changed...");
                logger.debug(" role id :{}", roleId);
                logger.debug(" inv org id :{}", invOrgId);
                logger.debug(" sales org id :{}", salesOrgId);
                logger.debug(" market id :{}", market != null ? market.getMarketId() : "");
            }
        }
        return new ResponseData(returnSign);
    }

    /**
     * 检查库存组织ID以及销售组织ID参数的合法性.
     *
     * @param uid
     * @param rid
     * @param request
     * @param response
     * @throws RoleException
     */
    private void checkInvOrgAndSalesOrg(Long uid, Long rid, String invOrgId, String saleOrgId) throws RoleException {
        try {
            if (!StringUtil.isEmpty(invOrgId)) {
                int ic = sysUserRoleAssignMapper.selectAssignCount(uid, rid, SystemProfileConstants.ORG_TYPE_INV,
                        Long.valueOf(invOrgId));
                if (ic == 0) {
                    throw new RoleException(RoleException.MSG_INVALID_PARAMETER, RoleException.MSG_INVALID_PARAMETER,
                            null);
                }
            }
            if (!StringUtil.isEmpty(saleOrgId)) {
                int sc = sysUserRoleAssignMapper.selectAssignCount(uid, rid, SystemProfileConstants.ORG_TYPE_SALES,
                        Long.valueOf(saleOrgId));
                if (sc == 0) {
                    throw new RoleException(RoleException.MSG_INVALID_PARAMETER, RoleException.MSG_INVALID_PARAMETER,
                            null);
                }
            }
        } catch (RuntimeException e) {
            throw new RoleException(RoleException.MSG_INVALID_PARAMETER, RoleException.MSG_INVALID_PARAMETER, null);
        }
    }

    protected void addCookie(String cookieName, String cookieValue, HttpServletRequest request,
            HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }
}
