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
import com.lkkhpg.dsis.common.config.dto.SpmCountry;
import com.lkkhpg.dsis.common.config.dto.SpmState;
import com.lkkhpg.dsis.common.config.mapper.SpmCountryMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmStateMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISpmCountryService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 国家Service接口实现.
 * 
 * @author shenqb
 */
@Service
@Transactional
public class SpmCountryServiceImpl implements ISpmCountryService {

    @Autowired
    private SpmCountryMapper spmCountryMapper;
    @Autowired
    private SpmStateMapper spmStateMapper;

    /**
     * 删除国家.
     * 
     * @param request
     *            请求上下文
     * @param countries
     *            国家List
     * @return boolean
     */
    @Override
    public boolean deleteCountry(IRequest request, List<SpmCountry> countries) {
        for (SpmCountry country : countries) {
            spmCountryMapper.deleteByPrimaryKey(country.getCountryCode());
        }

        return true;
    };

    /**
     * 查询国家.
     * 
     * @param request
     *            请求上下文
     * @param country
     *            国家DTO
     * @return 国家List
     */
    @Override
    public List<SpmCountry> queryCountry(IRequest request, SpmCountry country, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return spmCountryMapper.queryCountry(country);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmCountry> saveCountryDetail(IRequest request, List<SpmCountry> countrys)
            throws CommSystemProfileException {
        for (SpmCountry spmCountry : countrys) {

            System.out.println(spmCountry.toString());

            if (DTOStatus.ADD.equals(spmCountry.get__status())) {
                SpmCountry country = spmCountryMapper.selectByPrimaryKey(spmCountry.getCountryCode());
                if (country != null) {
                    throw new CommSystemProfileException(CommSystemProfileException.COUNTRY_CODE_UNIQUE_EXCEPTION,
                            new Object[] {});
                }
                Integer queryByName = spmCountryMapper.selectByCountryName(spmCountry.getName());
                if (queryByName > 0) {
                    throw new CommSystemProfileException(CommSystemProfileException.COUNTRY_NAME_UNIQUE_EXCEPTION,
                            new Object[] {});
                }
            }
            if (DTOStatus.ADD.equals(spmCountry.get__status())) {
                spmCountry.setEnabledFlag(SystemProfileConstants.YES);
                spmCountryMapper.insertSelective(spmCountry);//changed
            } else if (DTOStatus.UPDATE.equals(spmCountry.get__status())) {
                SpmCountry country = spmCountryMapper.selectByPrimaryKey(spmCountry.getCountryCode());
                if (!country.getName().equals(spmCountry.getName())) {
                    Integer queryByNames = spmCountryMapper.selectByCountryName(spmCountry.getName());
                    if (queryByNames > 0) {
                        throw new CommSystemProfileException(CommSystemProfileException.COUNTRY_NAME_UNIQUE_EXCEPTION,
                                new Object[] {});
                    }
                }
                spmCountryMapper.updateByPrimaryKeySelective(spmCountry);
            }
            List<SpmState> states = spmCountry.getStates();
            if (states != null && !states.isEmpty()) {
                for (SpmState spmState : states) {
                    if (DTOStatus.ADD.equals(spmState.get__status())) {
                        spmState.setCountryCode(spmCountry.getCountryCode());
                        spmStateMapper.updateByPrimaryKeySelective(spmState);
                    } else if (DTOStatus.UPDATE.equals(spmState.get__status())){
                    	spmState.setCountryCode(spmCountry.getCountryCode());
                        spmStateMapper.updateByPrimaryKeySelective(spmState);
                    }
                }
            }
        }
        return countrys;
    }

    @Override
    public SpmCountry queryCountryDetail(IRequest request, String countryCode) {
        return spmCountryMapper.selectByPrimaryKey(countryCode);
    }

    /**
     * 查询国家-地址编辑器专用.
     * 
     * @param request
     *            请求上下文
     * @param country
     *            国家DTO
     * @return 国家List
     */
    @Override
    public List<SpmCountry> queryCountryForLocation(IRequest request, SpmCountry country) {
        return spmCountryMapper.queryByCountryForLocation(country);
    }

}
