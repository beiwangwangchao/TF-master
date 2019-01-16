/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.UserRole;

/**
 * 角色分配功能Service.
 * @author xiawang.liu@hand-china.com
 */
public interface IUserRoleService extends ProxySelf<IUserRoleService> {

    /**
     * 查询用户关联的所有角色.
     * @param requestContext  requestContext
     * @param role role
     * @return list
     */
    List<Role> selectUserRoles(IRequest requestContext, Role role);
    
    /**
     * 保存为用户关联的所有角色.
     * 
     * @param requestContext requestContext
     * @param userRoles userRoles
     * @return list
     */
    List<UserRole> processBatchUserRole(IRequest requestContext, List<UserRole> userRoles);

    /**
     * 保存为用户关联的角色.
     * 
     * @param record record
     * @return UserRole UserRole
     */
    UserRole createUserRole(UserRole record);

}