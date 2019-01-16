/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.system.service;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.RoleTemplate;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 角色管理显示组织模板接口.
 * 
 * @author liang.rao
 *
 */
public interface IRoleTemplateService extends ProxySelf<IRoleTemplateService> {

    /**
     * 查询组织模板.
     * 
     * @param requestContext
     *            请求上下文.
     * @return 组织模板集合.
     */
    List<RoleTemplate> querytemplateCode(IRequest requestContext);
    
    /**
     * 查询所有角色信息.
     * 
     * @param requestContext
     *            请求上下文.
     * @param role 角色分配界面的组织模板.
     * @return 角色信息集合.
     */
    List<RoleTemplate> selectUserRoles(IRequest requestContext, RoleTemplate role);
    
    /**
     * 查询.
     * 
     * @param requestContext
     *            请求上下文.
     * @param role
     *            包含条件.
     * @param page
     *            起始页.
     * @param pagesize
     *            页大小.
     * @return 查询结果.
     */
    List<RoleTemplate> selectRoles(IRequest requestContext, RoleTemplate role, int page, int pagesize);
    
    /**
     * 批量增删改.
     * 
     * @param requestContext
     *            请求上下文.
     * @param roles
     *            数据.
     * @return 原样返回.
     */
    List<RoleTemplate> batchUpdate(IRequest requestContext, @StdWho List<RoleTemplate> roles);
    
    
    /**
     * 批量删改.
     * 
     * @param requestContext
     *            请求上下文.
     * @param roles
     *            数据.
     * @return 原样返回.
     */
    List<RoleTemplate> batchDelete(IRequest requestContext, @StdWho List<RoleTemplate> roles);
}
