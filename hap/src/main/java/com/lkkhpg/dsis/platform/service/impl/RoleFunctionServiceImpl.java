/*
 *
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.platform.cache.impl.RoleFunctionCache;
import com.lkkhpg.dsis.platform.cache.impl.RoleResourceCache;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.RoleFunction;
import com.lkkhpg.dsis.platform.mapper.system.RoleFunctionMapper;
import com.lkkhpg.dsis.platform.service.IRoleFunctionService;

/**
 * @author liuxiawang
 * @author njq.niu@hand-china.com
 */
@Transactional
@Service
public class RoleFunctionServiceImpl implements IRoleFunctionService {

    @Autowired
    private RoleFunctionMapper rolefunctionMapper;

    @Autowired
    private RoleFunctionCache roleFunctionCache;

    @Autowired
    private RoleResourceCache roleResourceCache;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Long[] getRoleFunctionById(Long roleId) {
        // TODO:需要考虑缓存没有的情况
        return roleFunctionCache.getValue(roleId.toString());
    }

    private RoleFunction createRoleFunction(RoleFunction record) {
        rolefunctionMapper.insert(record);
        return record;
    }

    @Override
    public List<RoleFunction> batchUpdate(IRequest requestContext, List<RoleFunction> rolefunctions) {
        if (rolefunctions != null && !rolefunctions.isEmpty()) {
            RoleFunction rf = rolefunctions.get(0);
            Long[] ids = new Long[rolefunctions.size()];
            Long roleId = rf.getRoleId();
            int i = 0;
            rolefunctionMapper.deleteByRoleId(roleId);
            for (RoleFunction rolefunction : rolefunctions) {
                createRoleFunction(rolefunction);
                ids[i++] = rolefunction.getFunctionId();
            }
            clearRoleFunctionCacheByRoleId(roleId);
            roleFunctionCache.setValue(roleId.toString(), ids);
            self().reloadRoleResourceCache(roleId);
        }
        return rolefunctions;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void clearRoleFunctionByRoleId(Long roleId) {
        rolefunctionMapper.deleteByRoleId(roleId);
        clearRoleFunctionCacheByRoleId(roleId);
    }

    private void clearRoleFunctionCacheByRoleId(Long roleId) {
        roleFunctionCache.remove(roleId.toString());
        roleResourceCache.remove(roleId.toString());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void reloadRoleResourceCache(Long roleId) {
        roleResourceCache.remove(roleId.toString());
        roleResourceCache.loadRoleResource(roleId);

    }
}
