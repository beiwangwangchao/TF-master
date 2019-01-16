/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.dashboard.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.dashboard.service.IFunctionShortcutService;
import com.lkkhpg.dsis.common.dashboard.dto.FunctionShortcut;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.menu.MenuItem;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.User;

/**
 * 
 * 快捷方式.
 * 
 * @author runbai.chen
 * 
 */
@Controller
public class FunctionShortcutController extends BaseController {

    @Autowired
    private IFunctionShortcutService functionShortcutService;

    private final Logger log = LoggerFactory.getLogger(FunctionShortcutController.class);

    /**
     * 获取快捷方式.
     * 
     * @param request
     *            用户请求
     * @return 快捷方式
     */
    @RequestMapping(value = "/sys/dashboard/functionshortcut/query")
    @ResponseBody
    public Object getFunctionShortcuts(HttpServletRequest request) {
        // 创建单行记录，设置userId，roleId.
        User user = getCurrentUser(request);
        Long roleId = (Long) request.getSession().getAttribute(Role.FIELD_ROLE_ID);
        IRequest requestContext = createRequestContext(request);
        return functionShortcutService.selectFunctionShortcutsMenu(requestContext, user, roleId);
    }

    /**
     * 提交用户所选菜单到数据库.
     * 
     * @param request
     *            用户请求
     * @param functionShortcuts
     *            用户提交快捷方式菜单
     * @param result
     *            验证结果
     * @return 菜单
     */
    @RequestMapping(value = "/sys/dashboard/functionshortcut/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitRole(HttpServletRequest request, @RequestBody List<FunctionShortcut> functionShortcuts,
            BindingResult result) {
        getValidator().validate(functionShortcuts, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        Long userId = getUserId(request);
        Long roleId = getRoleId(request);
        IRequest iRequest = createRequestContext(request);
        if (log.isDebugEnabled()) {
            log.debug("userId{}, roleId{}", userId, roleId);
        }
        return new ResponseData(
                functionShortcutService.processBatchShortcut(iRequest, userId, roleId, functionShortcuts));
    }

    /**
     * 获取menu树.
     * 
     * @param request
     *            用户请求
     * @return 快捷方式树
     */
    @RequestMapping("/sys/dashboard/functionshortcut/fetch")
    @ResponseBody
    public Object getMenus(HttpServletRequest request) {
        User user = getCurrentUser(request);
        Long roleId = getRoleId(request);
        IRequest requestContext = createRequestContext(request);
        List<MenuItem> items = functionShortcutService.selectFunctionShortcuts(requestContext, user, roleId);

        return items;
    }

    /**
     * 获取当前登录用户.
     * 
     * @param request
     *            用户请求
     * @return 当前用户
     */
    private User getCurrentUser(HttpServletRequest request) {
        User user = new User();
        user.setUserId(getUserId(request));
        return user;
    }
}
