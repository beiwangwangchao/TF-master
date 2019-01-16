/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.SysUserRoleAssign;
import com.lkkhpg.dsis.common.system.dto.SysUserRoleAssignExample;

/**
 * 用户角色分配组织Mapper.
 * 
 * @author chenjingxiong
 */
public interface SysUserRoleAssignMapper {
    int deleteByPrimaryKey(Short userRoleAssignId);

    int insert(SysUserRoleAssign record);

    int insertSelective(SysUserRoleAssign record);

    List<SysUserRoleAssign> selectByExample(SysUserRoleAssignExample example);

    SysUserRoleAssign selectByPrimaryKey(Short userRoleAssignId);

    int updateByPrimaryKeySelective(SysUserRoleAssign record);

    int updateByPrimaryKey(SysUserRoleAssign record);

    List<SysUserRoleAssign> selectByRecord(SysUserRoleAssign record);

    List<SysUserRoleAssign> selectOrgAssign(SysUserRoleAssign record);

    int deleteById(Long userRoleAssignId);

    int deleteByRecord(SysUserRoleAssign record);

    int selectAssignCount(Long userId, Long roleId, String assignType, Long assignValue);
}