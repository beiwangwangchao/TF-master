/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.RoleResourceItem;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 * 2016年4月8日
 */
public interface RoleResourceItemMapper {
    
    int deleteByResourceItemId(Long itemId);
    
    int deleteByRoleIdAndFunctionId(Long roleId, Long functionId);

    int insert(RoleResourceItem record);
    
    List<RoleResourceItem> selectResourceItemsByRole(Long roleId);
    
    
    RoleResourceItem selectByRoleIdAndResourceItemId(Long roleId, Long resourceItemId);
}