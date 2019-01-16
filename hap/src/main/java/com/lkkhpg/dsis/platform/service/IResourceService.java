/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.Resource;

/**
 * @author wuyichu
 */
public interface IResourceService extends ProxySelf<IResourceService> {

   /**
    * 根据资源的url查询资源数据.
    * @param url url
    * @return Resource
    */
    Resource selectResourceByUrl(String url);

    /**
     * 根据id查询资源数据.
     * @param id resourceId
     * @return Resource
     */
    Resource selectResourceById(Long id);

    /**
     * 新增资源记录.
     * @param resource resource
     * @return Resource 
     */
    Resource createResource(Resource resource);

    /**
     * 更新资源记录.
     * 
     * @param resource resource
     * @return Resource
     */
    Resource updateResource(Resource resource);

    /**
     * 删除资源记录.
     * 
     * @param resource resource
     */
    void deleteResource(Resource resource);

    /**
     *  根据页面输入字段查询资源记录.
     *  
     * @param requestContext 请求上下文
     * @param resource resource
     * @param page 起始页
     * @param pagesize 分页大小
     * @return Resource
     */
    List<Resource> selectResources(IRequest requestContext, Resource resource, int page, int pagesize);


    /**
     * 批量修改或新增资源记录.
     * @param requestContext requestContext
     * @param resources resources
     * @return Resource
     */
    List<Resource> batchUpdate(IRequest requestContext, @StdWho List<Resource> resources);

    /**
     * 批量删除资源记录.
     * 
     * @param requestContext requestContext
     * @param resources resources
     */
    void batchDelete(IRequest requestContext, List<Resource> resources);

}
