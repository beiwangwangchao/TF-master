/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Function;
import com.lkkhpg.dsis.platform.dto.system.ResourceItem;
import com.lkkhpg.dsis.platform.dto.system.RoleResourceItem;
import com.lkkhpg.dsis.platform.mapper.system.ResourceItemMapper;
import com.lkkhpg.dsis.platform.mapper.system.RoleResourceItemMapper;
import com.lkkhpg.dsis.platform.service.IRoleResourceItemService;

/**
 * @author njq.niu@hand-china.com
 *
 *         2016年4月7日
 *         TODO:缓存处理.
 */
@Transactional
@Service
public class RoleResourceItemServiceImpl implements IRoleResourceItemService {

    @Autowired
    private ResourceItemMapper resourceItemMapper;

    @Autowired
    private RoleResourceItemMapper roleResourceItemMapper;

    @Override
    public List<RoleResourceItem> queryRoleResourceItems(IRequest requestContext, Long roleId, Long functionId) {
        Function f = new Function();
        f.setFunctionId(functionId);
        List<RoleResourceItem> result = new ArrayList<>();
        List<RoleResourceItem> roleItems = roleResourceItemMapper.selectResourceItemsByRole(roleId);
        List<ResourceItem> resourceItems = resourceItemMapper.selectResourceItemsByFunctionId(f);
        for (ResourceItem item : resourceItems) {
            RoleResourceItem rri = new RoleResourceItem();
            rri.setResourceItemId(item.getResourceItemId());
            rri.setTargetResourceName(item.getTargetResourceName());
            rri.setOwnerResourceId(item.getOwnerResourceId());
            rri.setTargetResourceId(item.getTargetResourceId());
            rri.setItemId(item.getItemId());
            rri.setItemName(item.getItemName());
            rri.setDescription(item.getDescription());
            for (RoleResourceItem roleItem : roleItems) {
                if (roleItem.getResourceItemId().equals(item.getResourceItemId())) {
                    rri.setRsiId(roleItem.getRsiId());
                    break;
                }
            }
            result.add(rri);
        }
        return result;
    }

    @Override
    public List<RoleResourceItem> batchUpdate(IRequest requestContext, List<RoleResourceItem> roleResourceItems,
            Long roleId, Long functionId) {
        roleResourceItemMapper.deleteByRoleIdAndFunctionId(roleId, functionId);
        for (RoleResourceItem roleResourceItem : roleResourceItems) {
            roleResourceItemMapper.insert(roleResourceItem);
        }
        return roleResourceItems;
    }
    
    @Override
    public boolean hasResourceItem(Long roleId, Long resourceItemId) {
        if (roleId == null || resourceItemId == null) {
            return false;
        }
        return roleResourceItemMapper.selectByRoleIdAndResourceItemId(roleId, resourceItemId) != null;
    }

}
