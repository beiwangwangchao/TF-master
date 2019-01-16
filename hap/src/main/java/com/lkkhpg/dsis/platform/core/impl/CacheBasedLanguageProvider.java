/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.core.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.lkkhpg.dsis.platform.cache.CacheManager;
import com.lkkhpg.dsis.platform.cache.impl.HashStringRedisCache;
import com.lkkhpg.dsis.platform.core.ILanguageProvider;
import com.lkkhpg.dsis.platform.dto.system.Language;
import com.lkkhpg.dsis.platform.message.IMessageConsumer;
import com.lkkhpg.dsis.platform.message.TopicMonitor;

/**
 * @author shengyang.zhou@hand-china.com
 */
@TopicMonitor(channel = {"cache.language", "topic:cache:reloaded"})
public class CacheBasedLanguageProvider implements ILanguageProvider, IMessageConsumer<String>, BeanFactoryAware {

    private BeanFactory beanFactory;

    private HashStringRedisCache<Language> languageCache;

    private String cacheName;

    private boolean enableSecondaryCache = false;

    private List<Language> tempList;
    private Logger logger = LoggerFactory.getLogger(CacheBasedLanguageProvider.class);

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public boolean isEnableSecondaryCache() {
        return enableSecondaryCache;
    }

    public void setEnableSecondaryCache(boolean enableSecondaryCache) {
        this.enableSecondaryCache = enableSecondaryCache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Language> getSupportedLanguages() {
        if (tempList == null) {
            tempList = getFromCache();
        }
        if (tempList == null) {
            return Collections.EMPTY_LIST;
        }
        return tempList;
    }

    @SuppressWarnings("unchecked")
    private List<Language> getFromCache() {
        if (languageCache == null) {
            CacheManager cacheManager = beanFactory.getBean(CacheManager.class);
            languageCache = (HashStringRedisCache) cacheManager.getCache(getCacheName());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("load languages from cache.");
        }
        return languageCache.getAll();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void onMessage(String message, String pattern) {
        if ("cache.language".equals(pattern)) {
            if (logger.isDebugEnabled()) {
                logger.debug("language cache changed, now reload secondary cache.", message);
            }
            tempList = getFromCache();
        } else if ("topic:cache:reloaded".equals(pattern) && cacheName.equals(message)) {
            if (logger.isDebugEnabled()) {
                logger.debug("language cache reloaded, now reload secondary cache.");
            }
            tempList = getFromCache();
        }
    }

}
