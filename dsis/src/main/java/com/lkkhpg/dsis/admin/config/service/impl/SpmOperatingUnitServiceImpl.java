/*
 *
 */
package com.lkkhpg.dsis.admin.config.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmOperatingUnitService;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.dto.SpmOperatingUnit;
import com.lkkhpg.dsis.common.config.mapper.SpmInvOrganizationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmOperatingUnitMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 业务实体Service接口实现.
 * 
 * @author hanrui.huang
 *
 */
@Service
@Transactional
public class SpmOperatingUnitServiceImpl implements ISpmOperatingUnitService {

    private final Logger logger = LoggerFactory.getLogger(SpmOperatingUnitServiceImpl.class);
    
	@Autowired
	MemSiteMapper memSiteMapper;
	
    @Autowired
    private SpmOperatingUnitMapper spmOperatingUnitMapper;

    @Autowired
    private SpmInvOrganizationMapper spmInvOrganizationMapper;

    @Autowired
    private SpmLocationMapper spmLocationMapper;
    
    public static final String en_GB = "en_GB";

    @Override
    public List<SpmOperatingUnit> querySpmOperatingUnit(IRequest request, SpmOperatingUnit spmOperatingUnit, int page,
            int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SpmOperatingUnit> spmOperatingUnits = spmOperatingUnitMapper.queryBySpmOperatingUnit(spmOperatingUnit);
        for (SpmOperatingUnit spmOperatingUnit2 : spmOperatingUnits) {
            SpmLocation spmLocation = spmLocationMapper.selectByPrimaryKey(spmOperatingUnit2.getLocationId());
            if (spmLocation != null) {
            	spmOperatingUnit2.setSpmLocation(spmLocation);
            	
            	String Y = memSiteMapper.isHideState(spmLocation.getCountryCode());
            	
            	if(en_GB.equals(request.getLocale())){
            		StringBuilder sb = new StringBuilder();
                	sb.append(spmLocation.getAddressLine1());
                	if(spmLocation.getAddressLine2() != null){
                		sb.append(",").append(spmLocation.getAddressLine2());
                	}
                	if(spmLocation.getAddressLine3() != null){
                		sb.append(",").append(spmLocation.getAddressLine3());
                	}
                	sb.append(",").append(spmLocation.getCityName());
                	if(!SystemProfileConstants.YES.equals(Y)){
                		sb.append(",").append(spmLocation.getStateName());	
                	}
                	sb.append(",").append(spmLocation.getCountryName());
                	spmOperatingUnit2.setLocationName(sb.toString());
            	} else {
            		StringBuilder sb = new StringBuilder();
            		sb.append(spmLocation.getCountryName());
            		if(!SystemProfileConstants.YES.equals(Y)){
                		sb.append(spmLocation.getStateName());	
                	}
            		sb.append(spmLocation.getCityName());
                	sb.append(spmLocation.getAddressLine1());
                	if(spmLocation.getAddressLine2() != null){
                		sb.append(spmLocation.getAddressLine2());
                	}
                	if(spmLocation.getAddressLine3() != null){
                		sb.append(spmLocation.getAddressLine3());
                	}
                	spmOperatingUnit2.setLocationName(sb.toString());
                	//spmOperatingUnit2.setLocationName(spmLocation.getAddress());
                }
            }
        }
        return spmOperatingUnits;
    }

    @Override
    public List<SpmOperatingUnit> querySpmOperatingUnit2(IRequest request, SpmOperatingUnit spmOperatingUnit, int page,
                                                        int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SpmOperatingUnit> spmOperatingUnits = spmOperatingUnitMapper.queryBySpmOperatingUnit2(spmOperatingUnit);
        for (SpmOperatingUnit spmOperatingUnit2 : spmOperatingUnits) {
            SpmLocation spmLocation = spmLocationMapper.selectByPrimaryKey(spmOperatingUnit2.getLocationId());
            if (spmLocation != null) {
                spmOperatingUnit2.setSpmLocation(spmLocation);

                String Y = memSiteMapper.isHideState(spmLocation.getCountryCode());

                if(en_GB.equals(request.getLocale())){
                    StringBuilder sb = new StringBuilder();
                    sb.append(spmLocation.getAddressLine1());
                    if(spmLocation.getAddressLine2() != null){
                        sb.append(",").append(spmLocation.getAddressLine2());
                    }
                    if(spmLocation.getAddressLine3() != null){
                        sb.append(",").append(spmLocation.getAddressLine3());
                    }
                    sb.append(",").append(spmLocation.getCityName());
                    if(!SystemProfileConstants.YES.equals(Y)){
                        sb.append(",").append(spmLocation.getStateName());
                    }
                    sb.append(",").append(spmLocation.getCountryName());
                    spmOperatingUnit2.setLocationName(sb.toString());
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(spmLocation.getCountryName());
                    if(!SystemProfileConstants.YES.equals(Y)){
                        sb.append(spmLocation.getStateName());
                    }
                    sb.append(spmLocation.getCityName());
                    sb.append(spmLocation.getAddressLine1());
                    if(spmLocation.getAddressLine2() != null){
                        sb.append(spmLocation.getAddressLine2());
                    }
                    if(spmLocation.getAddressLine3() != null){
                        sb.append(spmLocation.getAddressLine3());
                    }
                    spmOperatingUnit2.setLocationName(sb.toString());
                    //spmOperatingUnit2.setLocationName(spmLocation.getAddress());
                }
            }
        }
        return spmOperatingUnits;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmOperatingUnit> saveSpmOperatingUnit(IRequest request,
            @StdWho List<SpmOperatingUnit> spmOperatingUnits) throws SystemProfileException {
        for (SpmOperatingUnit spmOperatingUnit : spmOperatingUnits) {
            SpmLocation spmLocationAdd = spmOperatingUnit.getSpmLocation();
            Long locationId = spmOperatingUnit.getLocationId();
            if (spmLocationAdd != null && spmOperatingUnit.getLocationId() == null) {
                spmLocationAdd.setEnabledFlag(SystemProfileConstants.YES);
                spmLocationMapper.insertSelective(spmLocationAdd);
                locationId = spmLocationAdd.getLocationId();
            } else if (spmLocationAdd != null) {
                spmLocationAdd.setLocationId(spmOperatingUnit.getLocationId());
                spmLocationMapper.updateByPrimaryKeySelective(spmLocationAdd);
            }
            if (spmOperatingUnit.getOperatingUnitId() == null) {
                Integer result = spmOperatingUnitMapper.checkRepeatByCode(spmOperatingUnit.getCode());
                if (result > 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(SystemProfileException.MSG_ERROR_SPM_OPERATING_UNIT_CODE_UNIQUE + "{}:",
                                spmOperatingUnit.getCode());
                    }
                    throw new SystemProfileException(SystemProfileException.MSG_ERROR_SPM_OPERATING_UNIT_CODE_UNIQUE,
                            new Object[] { spmOperatingUnit.getCode() });
                }
                spmOperatingUnit.setLocationId(locationId);
                spmOperatingUnit.setEnabledFlag(SystemProfileConstants.YES);
                spmOperatingUnitMapper.insert(spmOperatingUnit);
            } else {
                spmOperatingUnitMapper.updateBySpmOperatingUnit(spmOperatingUnit);
            }
        }
        return spmOperatingUnits;
    }

    @Override
    public List<SpmInvOrganization> querySpmInvOrganization(IRequest request, SpmInvOrganization spmInvOrganization,
            int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return spmInvOrganizationMapper.queryOrgByOperationUnitId(spmInvOrganization);
    }

    @Override
    public List<SpmInvOrganization> saveSpmInvOrganization(IRequest request,
            List<SpmInvOrganization> spmInvOrganizations) {
        for (SpmInvOrganization spmInvOrganization : spmInvOrganizations) {
            spmInvOrganizationMapper.updateOrgByOperationUnitId(spmInvOrganization);
        }
        return spmInvOrganizations;
    }
}
