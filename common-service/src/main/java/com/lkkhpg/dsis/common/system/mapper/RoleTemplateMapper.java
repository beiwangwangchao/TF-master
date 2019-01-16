/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.RoleTemplate;
import com.lkkhpg.dsis.platform.dto.system.User;

/**
 * 角色分配界面的组织模板Mapper.
 * 
 * @author liang.rao
 *
 */
public interface RoleTemplateMapper {

    List<RoleTemplate> querytemplateCode();
    
    List<RoleTemplate> selectUserRoles(RoleTemplate role);
    
    List<RoleTemplate> selectRoleNotUserRoles(RoleTemplate example);

    int deleteByPrimaryKey(RoleTemplate role);

    int insert(RoleTemplate record);

    RoleTemplate selectByPrimaryKey(RoleTemplate role);

    List<RoleTemplate> selectRoles(RoleTemplate example);

    int updateByPrimaryKey(RoleTemplate record);

    List<RoleTemplate> selectRolesByUser(User user);
    
    List<RoleTemplate> querytemplate(RoleTemplate role);
}