/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.dashboard.service;

import java.util.List;

import com.lkkhpg.dsis.common.dashboard.dto.FunctionShortcut;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.menu.MenuItem;
import com.lkkhpg.dsis.platform.dto.system.User;

/**
 * 功能快捷方式接口.
 * 
 * @author paco.chan
 */
public interface IFunctionShortcutService extends ProxySelf<IFunctionShortcutService> {
    /**
     * 删除快捷方式.
     * 
     * @param request
     *            用户请求
     * @param userId
     *            用户ID
     * @param roleId
     *            角色ID
     * @param functionIdList
     *            功能ID列表
     */
    void deleteBatchShortcut(IRequest request, Long userId, Long roleId, List<Long> functionIdList);

    /**
     * 创建快捷方式.
     * 
     * @param request
     *            用户请求
     * @param functionShortcut
     *            单条快捷方式行
     * @return 快捷方式
     */
    FunctionShortcut createFunctionShortcut(IRequest request, @StdWho FunctionShortcut functionShortcut);

    /**
     * 根据条件查询快捷方式.
     * 
     * @param request
     *            用户请求
     * @param functionShortcut
     *            单条快捷方式行
     * @param page
     *            页面数
     * @param pagesize
     *            页面数据行数
     * @return 快捷方式列表
     */
    List<FunctionShortcut> selectFunctionShortcuts(IRequest request, FunctionShortcut functionShortcut, int page,
            int pagesize);

    /**
     * 修改快捷方式.
     * 
     * @param request
     *            用户请求
     * @param functionShortcut
     *            快捷方式行
     * @return 修改过后的快捷方式信息
     */
    FunctionShortcut updateFunctionShortcut(IRequest request, @StdWho FunctionShortcut functionShortcut);

    /**
     * 批处理快捷方式.
     * 
     * @param request
     *            用户请求
     * @param userId
     *            用户id
     * @param roleId
     *            角色id
     * @param shortcutList
     *            快捷方式集合
     * @return 处理后的快捷方式信息集合
     */
    List<FunctionShortcut> processBatchShortcut(IRequest request, Long userId, Long roleId,
            @StdWho List<FunctionShortcut> shortcutList);

    /**
     * 根据用户以及角色查询用户的菜单.
     * 
     * @param request
     *            用户请求
     * @param user
     *            用户对象
     * @param roleId
     *            角色ID
     * @return 菜单项列表
     */
    List<MenuItem> selectFunctionShortcuts(IRequest request, User user, Long roleId);

    /**
     * 根据用户以及角色查询用户的快捷菜单.
     * 
     * @param request
     *            用户请求
     * @param user
     *            用户对象
     * @param roleId
     *            角色ID
     * @return 用户快捷方式列表
     */
    List<MenuItem> selectFunctionShortcutsMenu(IRequest request, User user, Long roleId);
}
