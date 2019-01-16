/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.config.dto.SpmCity;
import com.lkkhpg.dsis.common.config.mapper.SpmCityMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISpmCityService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 城市Service接口实现.
 * @author shenqb
 */
@Service
@Transactional
public class SpmCityServiceImpl implements ISpmCityService {

    private final Logger logger = LoggerFactory.getLogger(SpmCityServiceImpl.class);

    @Autowired
    private SpmCityMapper spmCityMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmCity> saveCity(IRequest request, @StdWho List<SpmCity> cities) throws CommSystemProfileException {
        for (SpmCity city : cities) {
            String status = city.get__status();
            switch (status) {
            case DTOStatus.ADD:
                // 校验城市代码的唯一性
                if (spmCityMapper.selectByPrimaryKey(city.getCityCode()) != null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("msg.error.config.city_code_unique: {}", new Object[] { city.getCityCode() });
                    }
                    throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_SPM_CITY_CODE_UNIQUE,
                            new Object[] { city.getCityCode() });
                }
                // 启用不能为空
                city.setEnabledFlag(SystemProfileConstants.YES);
                spmCityMapper.insert(city);
                break;
            case DTOStatus.UPDATE:
                spmCityMapper.updateSpmCity(city);
                break;
            case DTOStatus.DELETE:
                spmCityMapper.deleteByPrimaryKey(city.getCityCode());
                break;
            default:
                break;
            }

        }
        return cities;
    }

    @Override
    public boolean deleteCity(IRequest request, List<SpmCity> cities) {
        for (SpmCity city : cities) {
            city.setStateCode(null);
            spmCityMapper.updateByCity(city);
        }

        return true;
    };

    /**
     * 查询城市.
     * 
     * @param request 请求上下文
     * @param city 城市DTO 
     * @return 城市List
     */
    @Override
    public List<SpmCity> queryCity(IRequest request, SpmCity city, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return spmCityMapper.selectByName(city);
    }

    @Override
    public List<SpmCity> queryNullCity(IRequest request, SpmCity city, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return spmCityMapper.queryNullByCity(city);
    }


    /**
     * 查询城市-地址编辑器专用.
     * @param request 请求上下文
     * @param city 城市DTO
     * @return 城市List
     */
    @Override
    public List<SpmCity> queryCityForLocation(IRequest request, SpmCity city) {
        return spmCityMapper.queryCityForLocation(city);
    }

    @Override
    public SpmCity getCity(IRequest request, String cityCode) {
        return spmCityMapper.selectByPrimaryKey(cityCode);
    }

}
