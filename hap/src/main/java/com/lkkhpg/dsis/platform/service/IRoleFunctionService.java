/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.RoleFunction;

/**
 * @author liuxiawang
 * @author njq.niu@hand-china.com
 */
public interface IRoleFunctionService extends ProxySelf<IRoleFunctionService> {

    /**
     * 保存角色分配的功能.
     * 
     * @param requestContext requestContext
     * @param rolefunctions functions
     * @return list
     */
    List<RoleFunction> batchUpdate(IRequest requestContext, @StdWho List<RoleFunction> rolefunctions);

    /**
     * 从缓存中查询角色的所有功能ID的集合. 
     * @param roleId 角色id
     * @return roleFunction
     */
    Long[] getRoleFunctionById(Long roleId);
    
    
    /**
     * 清空角色功能.
     * 
     * @param roleId 角色id
     */
    void clearRoleFunctionByRoleId(Long roleId);
    
    /**
     * 重新加载角色资源缓存.
     * 
     * @param roleId 角色id
     */
    void reloadRoleResourceCache(Long roleId);
}
