/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 地址Service接口.
 * 
 * @author frank.li
 */
public interface ISpmLocationService extends ProxySelf<ISpmLocationService> {

    /**
     * 保存地点.
     * 
     * @param request
     *            请求上下文
     * @param locations
     *            地点List
     * @return 地点List
     */
    List<SpmLocation> saveLocation(IRequest request, @StdWho List<SpmLocation> locations);

    /**
     * 删除地点.
     * 
     * @param request
     *            请求上下文
     * @param locations
     *            地点List
     * @return boolean
     */
    boolean deleteLocation(IRequest request, List<SpmLocation> locations);

    /**
     * 查询地点.
     * 
     * @param request
     *            请求上下文
     * @param location
     *            地点DTO
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @return 地点List
     */
    List<SpmLocation> queryLocation(IRequest request, SpmLocation location, int page, int pagesize);
}
