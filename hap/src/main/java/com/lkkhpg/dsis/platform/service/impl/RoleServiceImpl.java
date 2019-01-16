/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.exception.RoleException;
import com.lkkhpg.dsis.platform.mapper.system.RoleMapper;
import com.lkkhpg.dsis.platform.service.IRoleFunctionService;
import com.lkkhpg.dsis.platform.service.IRoleService;

/**
 * @author shengyang.zhou@hand-china.com
 */

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private IRoleFunctionService roleFunctionService;

    /**
     * 查询被角色分配的功能以外的所有功能.
     * 
     * @author xiawang.liu@hand-china.com
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> selectRoleNotUserRoles(IRequest request, Role role, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return roleMapper.selectRoleNotUserRoles(role);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> selectRoles(IRequest request, Role role, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return roleMapper.selectRoles(role);
    }

    @Override
    public Role createRole(Role role) {
        roleMapper.insert(role);
        return role;
    }

    @Override
    public boolean deleteRole(Role role) {
        if (roleMapper.deleteByPrimaryKey(role) == 1) {
            roleFunctionService.clearRoleFunctionByRoleId(role.getRoleId());
            return true;
        }
        return false;
    }

    @Override
    public Role updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
        return role;
    }

    @Override
    public List<Role> batchUpdate(IRequest requestContext, List<Role> roles) {
        for (Role role : roles) {
            if (role.get__status() != null) {
                switch (role.get__status()) {
                case DTOStatus.ADD:
                    self().createRole(role);
                    break;
                case DTOStatus.UPDATE:
                    self().updateRole(role);
                    break;
                case DTOStatus.DELETE:
                    self().deleteRole(role);
                    break;
                default:
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public void batchDelete(List<Role> roles) {
        for (Role role : roles) {
            if (role.getRoleId() != null) {
                self().deleteRole(role);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> selectRolesByUser(IRequest requestContext, User user) {
        List<Role> rootRoles = roleMapper.selectRolesByUser(user);
        return rootRoles;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void checkUserRoleExists(Long userId, Long roleId) throws RoleException {
        if (roleMapper.selectUserRoleCount(userId, roleId) != 1) {
            throw new RoleException(RoleException.MSG_INVALID_USER_ROLE, RoleException.MSG_INVALID_USER_ROLE, null);
        }
    }
}
