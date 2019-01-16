/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.exception.RoleException;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface IRoleService extends ProxySelf<IRoleService> {

    /**
     * 查询.
     * 
     * @param requestContext
     *            请求上下文
     * @param role
     *            包含条件
     * @param page
     *            起始页
     * @param pagesize
     *            页大小
     * @return 查询结果
     */
    List<Role> selectRoles(IRequest requestContext, Role role, int page, int pagesize);

    /**
     * 查询,不属于当前用户角色的数据.
     * 
     * @param requestContext
     *            请求上下文
     * @param role
     *            条件,至少包含 userId
     * @param page
     *            起始页
     * @param pagesize
     *            页大小
     * @return 查询结果
     */
    List<Role> selectRoleNotUserRoles(IRequest requestContext, Role role, int page, int pagesize);

    /**
     * 新建.
     * 
     * @param role
     *            新数据
     * @return 包含主键的数据
     */
    Role createRole(Role role);

    /**
     * 删除.
     * 
     * @param role
     *            待删数据
     * @return 是否成功
     */
    boolean deleteRole(Role role);

    /**
     * 更新.
     * 
     * @param role
     *            新数据
     * @return 原样返回
     */
    Role updateRole(Role role);

    /**
     * 批量增删改.
     * 
     * @param requestContext
     *            请求上下文
     * @param roles
     *            数据
     * @return 原样返回
     */
    List<Role> batchUpdate(IRequest requestContext, @StdWho List<Role> roles);

    /**
     * 批量删除.
     * 
     * @param roles
     *            待删数据
     */
    void batchDelete(List<Role> roles);

    /**
     * 查询用户的所有角色.
     * 
     * @param requestContext
     *            请求上下文
     * @param user
     *            包含 userId
     * @return 查询结果
     */
    List<Role> selectRolesByUser(IRequest requestContext, User user);
    
    /**
     * 判断用户角色是否存在.
     * 
     * @param userId
     * @param roleId
     * @throws RoleException
     */
    void checkUserRoleExists(Long userId, Long roleId) throws RoleException;
    
}
