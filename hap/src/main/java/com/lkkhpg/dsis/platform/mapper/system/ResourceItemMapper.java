/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.Function;
import com.lkkhpg.dsis.platform.dto.system.Resource;
import com.lkkhpg.dsis.platform.dto.system.ResourceItem;

public interface ResourceItemMapper {
    
    int deleteByPrimaryKey(Long resourceItemId);

    int insert(ResourceItem record);

    ResourceItem selectByPrimaryKey(Long resourceItemId);

    int updateByPrimaryKey(ResourceItem record);
    
    List<ResourceItem> selectResourceItemsByResourceId(Resource resource);
    
    List<ResourceItem> selectResourceItemsByFunctionId(Function function);
    
    
    ResourceItem selectResourceItemByResourceIdAndItemId(ResourceItem record);
    
}