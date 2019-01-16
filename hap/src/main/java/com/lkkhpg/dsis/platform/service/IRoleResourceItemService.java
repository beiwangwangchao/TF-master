/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.RoleResourceItem;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 * 2016年4月7日
 */
public interface IRoleResourceItemService extends ProxySelf<IRoleResourceItemService> {

   
    /**
     * 查询角色拥有的权限项.
     * 
     * @param requestContext
     * @param roleId
     * @param functionId
     * @return List
     */
    List<RoleResourceItem> queryRoleResourceItems(IRequest requestContext, Long roleId, Long functionId);
    
    
    
    /**
     * 保存角色拥有的权限项.
     * 
     * @param requestContext
     * @param roleResourceItems
     * @param roleId
     * @param functionId
     * @return
     */
    List<RoleResourceItem> batchUpdate(IRequest requestContext, @StdWho List<RoleResourceItem> roleResourceItems,
            Long roleId, Long functionId);

    /**
     * 判断是否拥有权限项.
     * 
     * @param roleId
     * @param resourceItemId
     * @return boolean
     */
    boolean hasResourceItem(Long roleId, Long resourceItemId);

    
}
