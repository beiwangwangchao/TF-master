/*
 *
 */

package com.lkkhpg.dsis.platform.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.platform.cache.Cache;
import com.lkkhpg.dsis.platform.cache.ICacheListener;
import com.lkkhpg.dsis.platform.dto.menu.MenuItem;
import com.lkkhpg.dsis.platform.service.menu.IMenuService;

/**
 * MenuCacheListener.
 *
 * @author chenjingxiong
 *         <p>
 *         2016-01-08
 */
public class MenuCacheListener implements ICacheListener {

    private static Logger logger = LoggerFactory.getLogger(MenuCacheListener.class);

    @Autowired
    private IMenuService menuService;

    @Override
    public void cacheInit(Cache cache) {
        List<MenuItem> roleMenuList = menuService.getRoleMenus();
        if (logger.isInfoEnabled()) {
            logger.info("load root role menu, size: {}", roleMenuList.size());
        }
    }
}
