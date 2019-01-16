/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.system.dto.SysUserRoleAssign;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.UserRole;

/**
 * 用户角色分配组织Service.
 * 
 * @author chenjingxiong
 */
public interface ISysUserRoleAssignService extends ProxySelf<ISysUserRoleAssignService> {

    String RESULT_INV_ORG = "invOrg";

    String RESULT_SALES_ORG = "salesOrg";

    /**
     * 获取当前用户当前角色所分配的组织.
     * 
     * @param userId
     *            当前登陆用户.
     * @param roleId
     *            当前选择角色.
     * @return 已分配的组织.
     */
    List<SysUserRoleAssign> getUserRoleAssigns(Long userId, Long roleId);

    /**
     * 获取用户的角色获取库存组织和销售区域.
     * 
     * @param request
     *            统一上下文.
     * @param userId
     *            用户ID.
     * @param roleId
     *            角色Id.
     * @return 已分配的库存组织和销售区域.
     */
    Map<String, Object> getOrgs(IRequest request, Long userId, Long roleId);
    
    /**
     * 获取用户的角色获取库存组织和销售区域.
     * 
     * @param request
     *            统一上下文.
     * @param userId
     *            用户ID.
     * @param roleId
     *            角色Id.
     * @return 已分配的销售区域.
     */
    List<SpmSalesOrganization> getSalesOrgs(IRequest request, Long userId, Long roleId);
    
    
    /**
     * 获取用户的角色获取库存组织.
     * 
     * @param request
     *            统一上下文.
     * @param userId
     *            用户ID.
     * @param roleId
     *            角色Id.
     * @return 已分配的库存组织.
     */
    List<SpmInvOrganization> getInvOrgs(IRequest request, Long userId, Long roleId);

    /**
     * 根据roleId查询SysUserRoleAssign.
     * 
     * @param request
     *            统一上下文.
     * @param sur
     *            SysUserRoleAssign对象.
     * @return SysUserRoleAssign的结果集.
     */
    List<SysUserRoleAssign> queryAssign(IRequest request, SysUserRoleAssign sur);

    /**
     * 删除SysUserRoleAssign.
     * 
     * @param request
     *            统一上下文.
     * @param sur
     *            SysUserRoleAssign对象.
     */
    void deleteAssign(IRequest request, List<SysUserRoleAssign> sur);

    /**
     * 保存SysUserRoleAssign信息.
     * 
     * @param request
     *            统一上下文.
     * @param sur
     *            SysUserRoleAssign信息集合.
     */
    void saveAssign(IRequest request, List<SysUserRoleAssign> sur);

    /**
     * 如果角色分配有模板则插入assign.
     * 
     * @param request
     *            统一上下文.
     * @param userRoles
     *            用户角色信息.
     * @return list.
     */
    List<UserRole> saveUserAssign(IRequest request, List<UserRole> userRoles);

    /**
     * 删除角色分配信息.
     * 
     * @param request
     *            统一上下文
     * @param userRoles
     *            用户角色信息
     */
    void deleteUserAssign(IRequest request, List<UserRole> userRoles);
}
