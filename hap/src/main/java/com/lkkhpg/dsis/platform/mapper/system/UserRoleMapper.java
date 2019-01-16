/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.mapper.system;

import com.lkkhpg.dsis.platform.dto.system.UserRole;

/**
 * 角色分配功能Mapper.
 * 
 * @author xiawang.liu@hand-china.com
 */
public interface UserRoleMapper {

    int deleteUserRoleByUserId(Long userId);

    int insert(UserRole record);

    int deleteByPrimaryKey(Long surId);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long surId);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
    
    int deleteByRecord(UserRole record);
}