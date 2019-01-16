/*
 *
 */
package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.User;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface RoleMapper {

   
    List<Role> selectUserRoles(Role role);

    List<Role> selectRoleNotUserRoles(Role example);

    int deleteByPrimaryKey(Role role);

    int insert(Role record);

    Role selectByPrimaryKey(Role role);

    List<Role> selectRoles(Role example);

    int updateByPrimaryKey(Role record);

    List<Role> selectRolesByUser(User user);
    
    List<Role> selectRolesByUserWithoutLang(User user);
    
    int selectUserRoleCount(Long accountId, Long roleId);
}