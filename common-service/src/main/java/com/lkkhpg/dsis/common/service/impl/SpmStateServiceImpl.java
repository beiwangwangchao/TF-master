/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.config.dto.SpmCity;
import com.lkkhpg.dsis.common.config.dto.SpmState;
import com.lkkhpg.dsis.common.config.mapper.SpmCityMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmStateMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISpmStateService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 省/州Service接口实现.
 * 
 * @author huangjiajing
 */
@Service
@Transactional
public class SpmStateServiceImpl implements ISpmStateService {

    @Autowired
    private SpmStateMapper spmStateMapper;

    @Autowired
    private SpmCityMapper spmCityMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmState> saveState(IRequest request, List<SpmState> states) throws CommSystemProfileException {

        for (SpmState state : states) {
            if (DTOStatus.ADD.equals(state.get__status())) {
                SpmState spmState = spmStateMapper.selectByPrimaryKey(state.getStateCode());
                if (spmState != null) {
                    throw new CommSystemProfileException(CommSystemProfileException.STATE_CODE_UNIQUE_EXCEPTION,
                            new Object[] {});
                }
                Integer nameCount = spmStateMapper.selectByStateName(state.getName());
                if (nameCount > 0) {
                    throw new CommSystemProfileException(CommSystemProfileException.STATE_NAME_UNIQUE_EXCEPTION,
                            new Object[] {});
                }
            }
            if (DTOStatus.ADD.equals(state.get__status())) {
                state.setEnabledFlag(SystemProfileConstants.YES);
                spmStateMapper.insert(state);
            } else if (DTOStatus.UPDATE.equals(state.get__status())) {
                spmStateMapper.updateByPrimaryKeySelective(state);
            }
            for (SpmCity city : state.getSpmCities()) {
                if (DTOStatus.ADD.equals(city.get__status())) {
                    city.setStateCode(state.getStateCode());
                    spmCityMapper.updateByPrimaryKeySelective(city);
                } else if (DTOStatus.UPDATE.equals(city.get__status())) {
                	city.setStateCode(state.getStateCode());
                    spmCityMapper.updateByPrimaryKeySelective(city);
                }
            }
        }

        return states;
    };

    @Override
    public List<SpmState> queryState(IRequest request, SpmState state, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return spmStateMapper.queryByState(state);
    }

    @Override
    public List<SpmState> queryStateNoCountry(IRequest request, SpmState state, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return spmStateMapper.queryByStateNoCountry(state);
    }

    @Override
    public boolean deleteState(IRequest request, List<SpmState> states) {
        for (SpmState spmState : states) {
            spmState.setCountryCode(null);
            spmStateMapper.updateByCountry(spmState);
        }
        return true;
    };
    
    @Override
    public List<SpmState> queryStateForLocation(IRequest request, SpmState state) {
        return spmStateMapper.queryByStateForLocation(state);
    }
}
