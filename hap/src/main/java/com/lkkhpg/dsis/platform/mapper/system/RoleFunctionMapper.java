/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.platform.dto.system.Function;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.RoleFunction;

/**
 * 角色功能mapper.
 * 
 * @author wuyichu
 */
public interface RoleFunctionMapper {

    int deleteByRoleId(Long roleId);

    int insert(RoleFunction record);

    List<RoleFunction> selectRoleFunctions(RoleFunction record);

    int deleteByPrimaryKey(Long srfId);

    int insertSelective(RoleFunction record);

    RoleFunction selectByPrimaryKey(Long srfId);

    int updateByPrimaryKeySelective(RoleFunction record);

    int updateByPrimaryKey(RoleFunction record);

    List<Map<String, Object>> selectAllRoleResouces();

    int deleteByFunction(Function function);

    int deleteByRole(Role role);

    int selectCountByFunctionCode(@Param("roleId") Long roleId, @Param("functionCode") String functionCode);
}