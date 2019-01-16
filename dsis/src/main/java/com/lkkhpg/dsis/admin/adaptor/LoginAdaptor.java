/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.adaptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.admin.system.service.ISysUserRoleAssignService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.system.dto.SysUserRoleAssign;
import com.lkkhpg.dsis.common.system.mapper.SysUserRoleAssignMapper;
import com.lkkhpg.dsis.platform.adaptor.impl.DefaultLoginAdaptor;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.SysPreferences;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.exception.AccountException;
import com.lkkhpg.dsis.platform.exception.RoleException;
import com.lkkhpg.dsis.platform.mapper.system.RoleMapper;
import com.lkkhpg.dsis.platform.mapper.system.SysPreferencesMapper;
import com.lkkhpg.dsis.platform.mapper.system.UserMapper;
import com.lkkhpg.dsis.platform.service.IAccountService;

/**
 * 登陆适配器.
 * 
 * @author njq.niu@hand-china.com
 *
 * 2016年4月27日
 */
public class LoginAdaptor extends DefaultLoginAdaptor {

    private Logger logger = LoggerFactory.getLogger(LoginAdaptor.class);
    
    private static final Long PREFERENCE_LEVEL = 30L;
    
    public static final String VIEW_UPDATE_PWD = "updatePassword";
    
    public static final String FIRST_LOGIN_TRUE = "Y";
    
    

    @Autowired
    private IAccountService accountService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    
    @Autowired
    private SysUserRoleAssignMapper sysUserRoleAssignMapper;
    
    @Autowired
    private SysPreferencesMapper sysPreferencesMapper;
    
    @Autowired
    private ISysUserRoleAssignService sysUserRoleAssignService;

    @Override
    public void afterLogin(ModelAndView view, Account account, HttpServletRequest request, HttpServletResponse response)
            throws AccountException {
        super.afterLogin(view, account, request, response);
        HttpSession session = request.getSession(false);
        if (session != null && account != null) {
            User user = userMapper.selectUserByAccountId(account.getAccountId());
            if (user != null) {
                session.setAttribute(User.FIELD_USER_NAME, user.getUserName());
                session.setAttribute(User.FIELD_USER_TYPE, user.getUserType());
            }
            
            // 系统版本号
            SysPreferences sysPreferences = new SysPreferences();
            sysPreferences.setPreferencesLevel(PREFERENCE_LEVEL);
            sysPreferences.setPreferences("adminVersion");
            sysPreferences = sysPreferencesMapper.selectPreferLine(sysPreferences);
            
            if (sysPreferences != null) {
                String adminVersion = sysPreferences.getPreferencesValue();
                session.setAttribute(SystemProfileConstants.ADMIN_VERSION, adminVersion);
            }
            
            Long accountId = (Long) session.getAttribute(IRequest.FIELD_ACCOUNTID);
            Account singleAccount = accountService.getAccountByAccountId(accountId);
            if (singleAccount.getFirstLoginFlag() != null
                    && FIRST_LOGIN_TRUE.equals(singleAccount.getFirstLoginFlag())) {
                view.setViewName(VIEW_UPDATE_PWD);
                view.addObject("first", FIRST_LOGIN_TRUE);
            } else {
                List<Role> roles = roleMapper.selectRolesByUserWithoutLang(user);
                // 只有一个角色时，不需跳转到角色选择页
                if (roles.size() == 1) {
                    // 只有一个角色
                    Role role = roles.get(0);
                    session.setAttribute(Role.FIELD_ROLE_ID, role.getRoleId());
                    // 获取该角色分配的组织
                    List<SysUserRoleAssign> assigns = sysUserRoleAssignService.getUserRoleAssigns(user.getUserId(),
                            role.getRoleId());
                    Long salesOrgId = null;
                    Long invOrgId = null;
                    for (SysUserRoleAssign userRoleAssign : assigns) {
                        switch (userRoleAssign.getAssignType()) {
                        // 销售区域
                        case SysUserRoleAssign.SALES_ORG_ASSIGN_TYPE:
                            if (salesOrgId == null) {
                                salesOrgId = userRoleAssign.getAssignValue();
                            }
                            if (SystemProfileConstants.YES.equals(userRoleAssign.getDefaultFlag())) {
                                salesOrgId = userRoleAssign.getAssignValue();
                            }
                            break;
                        // 库存组织
                        case SysUserRoleAssign.INV_ORG_ASSIGN_TYPE:
                            if (invOrgId == null) {
                                invOrgId = userRoleAssign.getAssignValue();
                            }
                            if (SystemProfileConstants.YES.equals(userRoleAssign.getDefaultFlag())) {
                                invOrgId = userRoleAssign.getAssignValue();
                            }
                            break;
                        default: break;        
                        }
                    }
                    session.setAttribute(SystemProfileConstants.INV_ORG_ID, invOrgId);
                    session.setAttribute(SystemProfileConstants.SALES_ORG_ID, salesOrgId);
                    session.setAttribute(User.FILED_USER_ID, user.getUserId());
                    // 将角色 库存组织 销售组织 市场放到cookie中
//                    addCookie(User.FILED_USER_ID, user.getUserId().toString(), request, response);
                    addCookie(Role.FIELD_ROLE_ID, role.getRoleId().toString(), request, response);
                    addCookie(SystemProfileConstants.INV_ORG_ID, invOrgId == null ? null : invOrgId.toString(),
                            request, response);
                    addCookie(SystemProfileConstants.SALES_ORG_ID,
                            salesOrgId == null ? null : salesOrgId.toString(), request, response);
                    // 市场
                    SpmMarket market = spmMarketMapper.selectBySalesOrgId(salesOrgId);
                    if (market != null) {
                        session.setAttribute(SystemProfileConstants.MARKET_ID, market.getMarketId());
                        addCookie(SystemProfileConstants.MARKET_ID, market.getMarketId().toString(), request,
                                response);
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug(" only one role, skip select role page...");
                        logger.debug(" role id :{}", role.getRoleId());
                        logger.debug(" inv org id :{}", invOrgId);
                        logger.debug(" sales org id :{}", salesOrgId);
                        logger.debug(" market id :{}", market != null ? market.getMarketId() : "");
                    }
                    view.setViewName(REDIRECT + getIndexView(request));
                } else {
                    view.setViewName(REDIRECT + getRoleView(request));
                }
                
            }
        }
    }

    /**
     * 修改DSIS默认登陆页面.
     * 
     */
    @Override
    public ModelAndView indexModelAndView(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("admin/index");
    }

    @Override
    public ModelAndView doSelectRole(Role role, HttpServletRequest request, HttpServletResponse response)
            throws RoleException {
        ModelAndView result = super.doSelectRole(role, request, response);
        HttpSession session = request.getSession(false);
        if (session != null && role != null && role.getRoleId() != null) {
            Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
            String invOrgId = request.getParameter("orgId");
            String salesOrgId = request.getParameter("salesOrgId");
            checkInvOrgAndSalesOrg(userId, role.getRoleId(), invOrgId, salesOrgId);
            session.setAttribute(Role.FIELD_ROLE_ID, role.getRoleId());
            session.setAttribute(SystemProfileConstants.INV_ORG_ID, invOrgId == null ? null : Long.valueOf(invOrgId));
            session.setAttribute(SystemProfileConstants.SALES_ORG_ID,
                    salesOrgId == null ? null : Long.valueOf(salesOrgId));
            // 将角色 库存组织 销售组织 市场放到cookie中
            addCookie(Role.FIELD_ROLE_ID, role.getRoleId().toString(), request, response);
            addCookie(SystemProfileConstants.INV_ORG_ID, invOrgId, request, response);
            addCookie(SystemProfileConstants.SALES_ORG_ID, salesOrgId, request, response);
            SpmMarket market = null;
            if (salesOrgId != null) {
                market = spmMarketMapper.selectBySalesOrgId(Long.valueOf(salesOrgId));
                if (market != null) {
                    session.setAttribute(SystemProfileConstants.MARKET_ID, market.getMarketId());
                    addCookie(SystemProfileConstants.MARKET_ID, market.getMarketId().toString(), request, response);
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug(" role id :{}", role.getRoleId());
                logger.debug(" inv org id :{}", invOrgId);
                logger.debug(" sales org id :{}", salesOrgId);
                logger.debug(" market id :{}", market != null ? market.getMarketId() : "");
            }
        }
        return result;
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
            int ic = 1;
            int sc = 1;
            if (invOrgId != null) {
                ic = sysUserRoleAssignMapper.selectAssignCount(uid, rid, SystemProfileConstants.ORG_TYPE_INV,
                        Long.valueOf(invOrgId));
            }
            if (saleOrgId != null) {
                sc = sysUserRoleAssignMapper.selectAssignCount(uid, rid, SystemProfileConstants.ORG_TYPE_SALES,
                        Long.valueOf(saleOrgId));
            }

            if (ic == 0 || sc == 0) {
                throw new RoleException(RoleException.MSG_INVALID_PARAMETER, RoleException.MSG_INVALID_PARAMETER, null);
            }
        } catch (RuntimeException e) {
            throw new RoleException(RoleException.MSG_INVALID_PARAMETER, RoleException.MSG_INVALID_PARAMETER, null);
        }
    }
    
    
    @Override
    public ResponseData sessionExpiredLogin(Account account, HttpServletRequest request, HttpServletResponse response)
            throws RoleException {
        ResponseData data = super.sessionExpiredLogin(account, request, response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
            Long roleId = (Long) session.getAttribute(Role.FIELD_ROLE_ID);
            String invOrgId = request.getParameter(SystemProfileConstants.INV_ORG_ID);
            String salOrgId = request.getParameter(SystemProfileConstants.SALES_ORG_ID);
            checkInvOrgAndSalesOrg(userId, roleId, invOrgId, salOrgId);
            session.setAttribute(SystemProfileConstants.INV_ORG_ID, Long.valueOf(invOrgId));
            session.setAttribute(SystemProfileConstants.SALES_ORG_ID, Long.valueOf(salOrgId));
            // TODO:市场是否校验?
            session.setAttribute(SystemProfileConstants.MARKET_ID,
                    Long.valueOf(request.getParameter(SystemProfileConstants.MARKET_ID)));
        }
        return data;
    }
}
