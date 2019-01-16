/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.cache.Cache;
import com.lkkhpg.dsis.platform.cache.CacheManager;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Resource;
import com.lkkhpg.dsis.platform.mapper.system.FunctionResourceMapper;
import com.lkkhpg.dsis.platform.mapper.system.ResourceMapper;
import com.lkkhpg.dsis.platform.service.IResourceService;

/**
 * @author wuyichu
 */
@Transactional
@Service
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private FunctionResourceMapper functionResourceMapper;

    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Resource selectResourceByUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        Cache<Resource> cache = getResourceByURL();
        Resource resource = cache.getValue(url);
        if (resource == null) {
            resource = resourceMapper.selectResourceByUrl(url);
            if (resource != null) {
                flushCache(resource);
            }
        }
        return resource;
    }

  
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Resource selectResourceById(Long id) {
        if (id == null) {
            return null;
        }
        Cache<Resource> cache = getResourceById();
        Resource resource = cache.getValue(id.toString());
        if (resource == null) {
            resource = resourceMapper.selectByPrimaryKey(id);
            flushCache(resource);
        }
        return resource;
    }

    @Override
    public Resource createResource(Resource resource) {
        if (StringUtils.isEmpty(resource.getUrl())) {
            return null;
        }
        resourceMapper.insert(resource);
        flushCache(resource);
        return resource;
    }

    private Cache<Resource> getResourceByURL() {
        return cacheManager.getCache(BaseConstants.CACHE_RESOURCE_URL);
    }

    private Cache<Resource> getResourceById() {
        return cacheManager.getCache(BaseConstants.CACHE_RESOURCE_ID);
    }

    private void flushCache(Resource resource) {
        if (resource == null) {
            return;
        }

        Cache<Resource> resourceCache = getResourceByURL();
        resourceCache.setValue(resource.getUrl(), resource);
        Cache<Resource> resourceCache2 = getResourceById();
        resourceCache2.setValue(resource.getResourceId().toString(), resource);
    }

    private void removeCache(Resource resource) {
        if (resource == null) {
            return;
        }
        Cache<Resource> resourceCache = getResourceByURL();
        resourceCache.remove(resource.getUrl());
        Cache<Resource> resourceCache2 = getResourceById();
        resourceCache2.remove(resource.getResourceId().toString());
    }

    
    @Override
    public Resource updateResource(Resource resource)  {
        Resource old = resourceMapper.selectByPrimaryKey(resource.getResourceId());
        removeCache(old);
        resourceMapper.updateByPrimaryKeySelective(resource);
        flushCache(resource);
        return resource;
    }

   
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Resource> selectResources(IRequest requestContext, Resource resource, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return resourceMapper.selectResources(resource);
    }

    @Override
    public List<Resource> batchUpdate(IRequest requestContext, List<Resource> resources)  {
        for (Resource resource : resources) {
            if (resource.getResourceId() != null) {
                self().updateResource(resource);
            } else {
                self().createResource(resource);
            }
        }
        return resources;
    }

    @Override
    public void deleteResource(Resource resource)  {
        if (resource == null || resource.getResourceId() == null || StringUtils.isEmpty(resource.getUrl())) {
            return;
        }
        functionResourceMapper.deleteByResource(resource);
        resourceMapper.deleteByPrimaryKey(resource);
        removeCache(resource);
    }

    @Override
    public void batchDelete(IRequest requestContext, List<Resource> resources)  {
        if (resources == null || resources.isEmpty()) {
            return;
        }
        for (Resource resource : resources) {
            self().deleteResource(resource);
        }
    }

}
