/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.Resource;
import com.lkkhpg.dsis.platform.dto.system.ResourceItem;

/**
 * @author njq.niu@hand-china.com
 *
 * 2016年4月7日
 */
public interface IResourceItemService extends ProxySelf<IResourceItemService> {
    
   
    
    /**
     * 查询资源权限项.
     * 
     * @param requestContext
     * @param resource
     * @return ResourceItem
     */
    List<ResourceItem> selectResourceItems(IRequest requestContext, Resource resource);
    
    
    /**
     * 批量修改或新增资源权限项.
     * 
     * @param requestContext
     * @param resourceItems
     * @return List
     */
    List<ResourceItem> batchUpdate(IRequest requestContext, @StdWho List<ResourceItem> resourceItems);
    
    
    /**
     * 批量删除资源权限项.
     * 
     * @param requestContext requestContext
     * @param resourceItems resourceItems
     */
    void batchDelete(IRequest requestContext, List<ResourceItem> resourceItems);
    
    /**
     * 按照资源ID和权限ID查询权限项.
     * 
     * @param resourceItem
     * @return ResourceItem
     */
    ResourceItem selectResourceItemByResourceIdAndItemId(ResourceItem resourceItem);

}
