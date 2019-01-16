/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.Function;
import com.lkkhpg.dsis.platform.dto.system.Resource;

/**
 * 资源mapper.
 * 
 * @author wuyichu
 */
public interface ResourceMapper {

    List<Resource> selectResources(Resource resource);

    int deleteByPrimaryKey(Resource resource);

    int insert(Resource record);

    Resource selectByPrimaryKey(Long resourceId);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

    Resource selectResourceByUrl(String url);
    
    List<Resource> selectResourcesByFunction(Function function);
}