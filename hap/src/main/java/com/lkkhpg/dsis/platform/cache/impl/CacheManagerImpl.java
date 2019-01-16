/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.cache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.platform.cache.Cache;
import com.lkkhpg.dsis.platform.cache.CacheManager;
import com.lkkhpg.dsis.platform.message.IMessagePublisher;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class CacheManagerImpl implements CacheManager {

    private HashMap<String, Cache> cacheMap = new HashMap<>();
    private List<Cache> caches;

    @Autowired
    private IMessagePublisher messagePublisher;

    public void setCaches(List<Cache> caches) {
        this.caches = caches;
        if (caches != null) {
            for (Cache c : caches) {
                cacheMap.put(c.getName(), c);
                c.init();
            }
        }
    }

    public List<Cache> getCaches() {
        return caches;
    }

    @Override
    public <T> Cache<T> getCache(String name) {
        return cacheMap.get(name);
    }

    @Override
    public void addCache(Cache<?> cache) {
        if (caches == null) {
            caches = new ArrayList<>();
        }
        if (!caches.contains(cache)) {
            caches.add(cache);
        }
        cacheMap.put(cache.getName(), cache);
    }

    @Override
    public List<Cache> getAllCaches() {
        return caches;
    }

    @Override
    public void reloadAll() {
        for (Cache c : caches) {
            messagePublisher.publish("queue:cache:reload", c.getName());
        }
    }
}
