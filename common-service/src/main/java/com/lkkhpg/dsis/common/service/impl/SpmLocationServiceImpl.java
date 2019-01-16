/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.service.ISpmLocationService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 地址Service接口实现.
 * 
 * @author frank.li
 */
@Service
@Transactional
public class SpmLocationServiceImpl implements ISpmLocationService {

    private final Logger logger = LoggerFactory.getLogger(SpmLocationServiceImpl.class);

    @Autowired
    private SpmLocationMapper spmLocationMapper;

    /**
     * 保存地点.
     * 
     * @param request
     *            请求上下文
     * @param locations
     *            地点List
     * @return 地点List
     */
    @Override
    public List<SpmLocation> saveLocation(IRequest request, List<SpmLocation> locations) {
        for (SpmLocation location : locations) {

            String status = location.get__status();
            if (logger.isDebugEnabled()) {
                logger.debug("status: {}", status);
            }

            switch (status) {
            case DTOStatus.ADD:
                spmLocationMapper.insert(location);
                break;
            case DTOStatus.UPDATE:
                spmLocationMapper.updateByPrimaryKeySelective(location);
                break;
            case DTOStatus.DELETE:
                SpmLocation spmLocation = new SpmLocation();
                spmLocation.setLocationId(location.getLocationId());
                spmLocationMapper.deleteByPrimaryKey(spmLocation);
                break;
            default:
                break;
            }
        }

        return locations;
    };

    /**
     * 删除地点.
     * 
     * @param request
     *            请求上下文
     * @param locations
     *            地点List
     * @return boolean
     */
    @Override
    public boolean deleteLocation(IRequest request, List<SpmLocation> locations) {
        for (SpmLocation location : locations) {

            String status = location.get__status();
            if (logger.isDebugEnabled()) {
                logger.debug("status: {}", status);
            }
            SpmLocation spmLocation = new SpmLocation();
            spmLocation.setLocationId(location.getLocationId());
            spmLocationMapper.deleteByPrimaryKey(spmLocation);
        }

        return true;
    };

    /**
     * 查询地点.
     * 
     * @param request
     *            请求上下文
     * @param location
     *            地点DTO
     * @return 地点List
     */
    @Override
    public List<SpmLocation> queryLocation(IRequest request, SpmLocation location, int page, int pagesize) {
        return spmLocationMapper.queryByLocation(location);
    };
}
