/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.dashboard.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.dashboard.service.IFunctionShortcutService;
import com.lkkhpg.dsis.common.constant.DashboardConstants;
import com.lkkhpg.dsis.common.dashboard.dto.FunctionShortcut;
import com.lkkhpg.dsis.common.dashboard.mapper.FunctionShortcutMapper;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.menu.MenuItem;
import com.lkkhpg.dsis.platform.dto.system.Function;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.service.IFunctionService;

/**
 * 快捷方式实现类.
 * 
 * @author runbai.chen
 */
@Service
@Transactional
public class FunctionShortcutServiceImpl implements IFunctionShortcutService {

    private final Logger log = LoggerFactory.getLogger(FunctionShortcutServiceImpl.class);
    @Autowired
    private FunctionShortcutMapper functionShortcutMapper;
    @Autowired
    private IFunctionService functionService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public FunctionShortcut createFunctionShortcut(IRequest request, @StdWho FunctionShortcut functionShortcut) {
        functionShortcutMapper.insert(functionShortcut);
        return functionShortcut;
    }

    @Override
    public FunctionShortcut updateFunctionShortcut(IRequest request, @StdWho FunctionShortcut functionShortcut) {
        functionShortcutMapper.updateByPrimaryKey(functionShortcut);
        return functionShortcut;
    }

    @Override
    public void deleteBatchShortcut(IRequest request, Long userId, Long roleId, List<Long> functionIdList) {
        functionShortcutMapper.deleteBatch(userId, roleId, functionIdList);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<FunctionShortcut> selectFunctionShortcuts(IRequest request, FunctionShortcut functionShortcut, int page,
            int pagesize) {
        PageHelper.startPage(page, pagesize);
        return functionShortcutMapper.selectFunctionShortcuts(functionShortcut);
    }

    @Override
    public List<FunctionShortcut> processBatchShortcut(IRequest request, Long userId, Long roleId,
            List<FunctionShortcut> shortcutList) {
        // 先批量删除LIST以外的快捷方式
        List<Long> functionIdList = new ArrayList<Long>();
        for (FunctionShortcut shortcut : shortcutList) {
            functionIdList.add(shortcut.getFunctionId());
        }
        if (!functionIdList.isEmpty()) {
            functionShortcutMapper.deleteBatch(userId, roleId, functionIdList);

        }else{
            functionIdList.add((long) -1);
            functionShortcutMapper.deleteBatch(userId, roleId, functionIdList);
        }

        // 判断id是否有值，再创建或者更新相应行数据
        for (FunctionShortcut shortcut : shortcutList) {
            shortcut.setUserId(userId);
            shortcut.setRoleId(roleId);
            shortcut.setSortNumber((long) shortcutList.indexOf(shortcut));
            if (shortcut.getShortcutId() == null) {
                self().createFunctionShortcut(request, shortcut);
            }
        }

        return shortcutList;

    }

    @Override
    public List<MenuItem> selectFunctionShortcuts(IRequest request, User user, Long roleId) {

        List<MenuItem> result = new ArrayList<>();
        FunctionShortcut functionShortcut = new FunctionShortcut();
        functionShortcut.setUserId(user.getUserId());
        functionShortcut.setRoleId(roleId);

        if (log.isDebugEnabled()) {
            log.debug("userId {}, roleId {}", functionShortcut.getUserId(), roleId);
        }

        List<FunctionShortcut> userShortcuts = selectFunctionShortcuts(request, functionShortcut,
                DashboardConstants.DEFAULT_PAGE, DashboardConstants.DEFAULT_PAGESIZE);

        List<MenuItem> menuItem = functionService.selectRoleFunctions(request);
        updateMenuShortcutId(menuItem, userShortcuts);
        result = menuItem;
        return result;
    }

    /**
     * 修改功能的快捷方式.
     * 
     * @param menus
     *            功能集合
     * @param userShortcuts
     *            用户快捷方式
     */
    public void updateMenuShortcutId(final List<MenuItem> menus, final List<FunctionShortcut> userShortcuts) {
        if (userShortcuts.isEmpty()) {
            if (log.isDebugEnabled()) {
                log.debug("userShortcuts is null");
            }
            return;
        }
        for (MenuItem menuItem : menus) {
            if (menuItem.getChildren() != null && !menuItem.getChildren().isEmpty()) {
                updateMenuShortcutId(menuItem.getChildren(), userShortcuts);
            }
            for (FunctionShortcut userShortcut : userShortcuts) {
                if (menuItem.getId() != null && userShortcut.getFunctionId().equals(menuItem.getId())) {
                    if (log.isDebugEnabled()) {
                        log.debug("{},{},{}", menuItem.getId(), userShortcut.getFunctionId(),
                                userShortcut.getShortcutId());
                    }
                    menuItem.setIschecked(Boolean.TRUE);
                    menuItem.setShortcutId(userShortcut.getShortcutId());
                }
            }
        }
    }

    @Override
    public List<MenuItem> selectFunctionShortcutsMenu(IRequest request, User user, Long roleId) {
        List<MenuItem> result = new ArrayList<MenuItem>();
        List<Map<String, Object>> menus = functionShortcutMapper.selectFunctionShortcutsMenu(roleId, user.getUserId(),
                request.getLocale());
        Locale locale = LocaleUtils.toLocale(request.getLocale());
        for (Map<String, Object> menu : menus) {
            MenuItem menuItem = new MenuItem();
            menuItem.setId(((Number) menu.get("FUNCTION_ID")).longValue());
            menuItem.setText((String) menu.get("FUNCTION_DESCRIPTION"));
            menuItem.setUrl((String) menu.get("URL"));
            menuItem.setIcon((String) menu.get("FUNCTION_ICON"));
            menuItem.setFunctionCode((String) menu.get("FUNCTION_CODE"));
            result.add(menuItem);
        }
        // 当小于6个(DashboardConstants.FUNCTION_QUANTITY)快捷功能，则提供快速添加快捷方式功能
        if (result.size() < DashboardConstants.FUNCTION_QUANTITY) {
            MenuItem menuItem = new MenuItem();
            menuItem.setFunctionCode(DashboardConstants.FUNCTION_CODE_ADD);
            Function function = new Function();
            function.setFunctionCode(DashboardConstants.FUNCTION_CODE_ADD);
            Function fun = functionShortcutMapper.selectFunctionNameByCode(function);
            menuItem.setText(fun.getFunctionName());
            menuItem.setUrl(DashboardConstants.FUNCTION_SHORT_URL);
            menuItem.setIcon(DashboardConstants.FUNCTION_SHORT_ICON);
            result.add(menuItem);
        }
        return result;
    }

}
