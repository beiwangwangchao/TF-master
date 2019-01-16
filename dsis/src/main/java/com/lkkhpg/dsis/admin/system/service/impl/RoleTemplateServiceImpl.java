/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.system.service.IRoleTemplateService;
import com.lkkhpg.dsis.common.system.dto.RoleTemplate;
import com.lkkhpg.dsis.common.system.mapper.RoleTemplateMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.service.IRoleFunctionService;

/**
 * 角色管理显示组织模板实现类.
 * 
 * @author liang.rao
 *
 */
@Service
@Transactional
public class RoleTemplateServiceImpl implements IRoleTemplateService {

    @Autowired
    private RoleTemplateMapper roleTemplateMapper;
    
    @Autowired
    private IRoleFunctionService roleFunctionService;
    
    @Override
    public List<RoleTemplate> querytemplateCode(IRequest requestContext) {
        List<RoleTemplate> Roles = roleTemplateMapper.querytemplateCode();
        return Roles;
    }

    @Override
    public List<RoleTemplate> selectUserRoles(IRequest requestContext, RoleTemplate role) {
        List<RoleTemplate> selectUserRoles = roleTemplateMapper.selectUserRoles(role);
        return selectUserRoles;
    }

    @Override
    public List<RoleTemplate> batchUpdate(IRequest requestContext, List<RoleTemplate> roles) {
        for (RoleTemplate role : roles) {
            if (role.get__status() != null) {
                switch (role.get__status()) {
                case DTOStatus.ADD:
                    roleTemplateMapper.insert(role);
                    break;
                case DTOStatus.UPDATE:
                    roleTemplateMapper.updateByPrimaryKey(role);
                    break;
                case DTOStatus.DELETE:
                    deleteRole(role);
                    break;
                default:
                    break;
                }
            }
        }
        return roles;
    }
    
    /**
     * 删除角色模板.
     * 
     * @param role 角色信息.
     * @return 结果.
     */
    public boolean deleteRole(RoleTemplate role) {
        if (roleTemplateMapper.deleteByPrimaryKey(role) == 1) {
            roleFunctionService.clearRoleFunctionByRoleId(role.getRoleId());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<RoleTemplate> selectRoles(IRequest request, RoleTemplate role, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return roleTemplateMapper.selectRoles(role);
    }
    
    
    @Override
    public List<RoleTemplate> batchDelete(IRequest requestContext, List<RoleTemplate> roles) {
        for (RoleTemplate role : roles) {
            deleteRole(role);
        }
        return roles;
    }
}
