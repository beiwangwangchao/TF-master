/*
 *
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.UserRole;
import com.lkkhpg.dsis.platform.mapper.system.RoleMapper;
import com.lkkhpg.dsis.platform.mapper.system.UserRoleMapper;
import com.lkkhpg.dsis.platform.service.IUserRoleService;

/**
 * 角色分配功能ServiceImpl.
 * 
 * @author xiawang.liu@hand-china.com
 */
@Transactional
@Service
public class UserRoleServiceImpl implements IUserRoleService {

    private Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> selectUserRoles(IRequest requestContext, Role role) {
        List<Role> selectUserRoles = roleMapper.selectUserRoles(role);
        return selectUserRoles;
    }

    @Override
    public UserRole createUserRole(UserRole record) {
        userRoleMapper.insert(record);
        return record;
    }

    @Override
    public List<UserRole> processBatchUserRole(IRequest requestContext, List<UserRole> UserRoles) {
        List<UserRole> result = new ArrayList<>();
        for (UserRole userrole : UserRoles) {
            if (DTOStatus.ADD.equals(userrole.get__status())) {
                userrole.setRoleId(userrole.getRoleId());
                userrole.setUserId(userrole.getUserId());
                userrole.setDefaultFlag(userrole.getDefaultFlag());
                // 默认字段
                userrole.setCreationDate(new Date());
                userrole.setCreatedBy((long) -1);
                userrole.setLastUpdatedBy((long) -1);
                userrole.setLastUpdateDate(new Date());
                self().createUserRole(userrole);
                result.add(userrole);
            } else if (DTOStatus.DELETE.equals(userrole.get__status())) {
                userRoleMapper.deleteByRecord(userrole);
            } else if (DTOStatus.UPDATE.equals(userrole.get__status())) {
                userRoleMapper.updateByPrimaryKeySelective(userrole);
            }
        }
        return result;
    }

}