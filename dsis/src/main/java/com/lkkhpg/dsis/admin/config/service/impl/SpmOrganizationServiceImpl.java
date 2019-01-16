/*
 *
 */
package com.lkkhpg.dsis.admin.config.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmOrganizationService;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.dto.SpmOrgDefination;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.*;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 组织定义Service接口实现.
 * 
 * @author wangc
 */
@Service
@Transactional
public class SpmOrganizationServiceImpl implements ISpmOrganizationService {

    @Autowired
    MemSiteMapper memSiteMapper;
    @Autowired
    private SpmOrgDefinationMapper spmOrgDefinationMapper;
    @Autowired
    private SpmLocationMapper spmLocationMapper;
    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;
    @Autowired
    private SpmInvOrganizationMapper spmInvOrganizationMapper;
    @Autowired
    private SpmMarketMapper spmMarketMapper;

    public static final String en_GB = "en_GB";

    @Override
    public List<SpmOrgDefination> queryByOrganization(IRequest request, SpmOrgDefination record, int page,
                                                      int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SpmOrgDefination> orgs = spmOrgDefinationMapper.queryByOrganization(record);
        for (SpmOrgDefination org : orgs) {
            SpmLocation spmLocation = spmLocationMapper.selectByPrimaryKey(org.getLocationId());
            if (spmLocation != null && spmLocation.getAddress() != null && !"".equals(spmLocation.getAddress())) {
                org.setSpmLocation(spmLocation);
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
                    org.setLocationName(sb.toString());
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
                    org.setLocationName(sb.toString());
                    //org.setLocationName(spmLocation.getAddress());
                }
            }
        }
        return orgs;
    }

   public List<SpmOrgDefination> queryByOrganization(IRequest request, SpmOrgDefination spmOrgDefination){
        List<SpmOrgDefination> orgs = spmOrgDefinationMapper.queryByOrganization(spmOrgDefination);
        return orgs;
    }


    @Override
    public List<SpmOrgDefination> queryByOrganization2(IRequest request, SpmOrgDefination record, int page,
                                                       int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SpmOrgDefination> orgs = spmOrgDefinationMapper.queryByOrganization2(record);
        for (SpmOrgDefination org : orgs) {
            SpmLocation spmLocation = spmLocationMapper.selectByPrimaryKey(org.getLocationId());
            if (spmLocation != null && spmLocation.getAddress() != null && !"".equals(spmLocation.getAddress())) {
                org.setSpmLocation(spmLocation);
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
                    org.setLocationName(sb.toString());
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
                    org.setLocationName(sb.toString());
                    //org.setLocationName(spmLocation.getAddress());
                }
            }
        }
        return orgs;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmOrgDefination> saveOrgDefinations(IRequest request, @StdWho List<SpmOrgDefination> spmOrgDefinations)
            throws SystemProfileException {
        for (SpmOrgDefination spmOrgDefination : spmOrgDefinations) {
            String salesOrgFlag = spmOrgDefination.getSalesOrgFlag();
            String invOrgFlag = spmOrgDefination.getInvOrgFlag();
            if (SystemProfileConstants.NO.equals(salesOrgFlag) && SystemProfileConstants.NO.equals(invOrgFlag)) {
                throw new SystemProfileException(SystemProfileException.MSG_ERROR_SPM_ORG_TYPE_IS_NULL,
                        new Object[] {});
            }
            if (DTOStatus.ADD.equals(spmOrgDefination.get__status())) {
                // 校验编码是否重复
                Integer result = spmOrgDefinationMapper.checkRepeatByCode(spmOrgDefination.getCode());
                if (result > 0) {
                    throw new SystemProfileException(CommSystemProfileException.ORGDEFINATION_CODE_UNIQUE_EXCEPTION,
                            new Object[] {});
                }
                SpmLocation spmLocationAdd = spmOrgDefination.getSpmLocation();
                spmLocationAdd.setEnabledFlag(SystemProfileConstants.YES);
                spmLocationMapper.insertSelective(spmLocationAdd);
                Long locationId = spmLocationAdd.getLocationId();
                spmOrgDefination.setLocationId(locationId);
                if (SystemProfileConstants.YES.equals(salesOrgFlag)) {
                    SpmSalesOrganization salesOrg = new SpmSalesOrganization();
                    salesOrg.setCode(spmOrgDefination.getCode());
                    salesOrg.setName(spmOrgDefination.getName());
                    salesOrg.setLocationId(locationId);
                    salesOrg.setEndActiveDate(spmOrgDefination.getEndActiveDate());
                    salesOrg.setSalesFlag(SystemProfileConstants.YES);
                    salesOrg.setDefaultFlag(SystemProfileConstants.YES);
                    salesOrg.setEnabledFlag(SystemProfileConstants.YES);
                    salesOrg.setStartActiveDate(new Date());
                    salesOrg.set__tls(spmOrgDefination.get__tls());
                    salesOrg = self().saveSpmSalesOrganization(request, salesOrg, true);
                    spmOrgDefination.setSalesOrgId(salesOrg.getSalesOrgId());
                }
                if (SystemProfileConstants.YES.equals(invOrgFlag)) {
                    SpmInvOrganization invOrg = new SpmInvOrganization();
                    invOrg.setCode(spmOrgDefination.getCode());
                    invOrg.setName(spmOrgDefination.getName());
                    invOrg.setLocationId(locationId);
                    invOrg.setEndActiveDate(spmOrgDefination.getEndActiveDate());
                    invOrg.setDefaultFlag(SystemProfileConstants.YES);
                    invOrg.setEnabledFlag(SystemProfileConstants.YES);
                    invOrg.setStartActiveDate(new Date());
                    invOrg.set__tls(spmOrgDefination.get__tls());
                    invOrg = self().saveSpmInvOrganization(request, invOrg, true);
                    spmOrgDefination.setInvOrgId(invOrg.getInvOrgId());
                }
                spmOrgDefinationMapper.insertSelective(spmOrgDefination);
            } else if (DTOStatus.UPDATE.equals(spmOrgDefination.get__status())
                    && spmOrgDefination.getLocationId() != null) {
                //认证如果此是销售组织，就查看它是否已经被市场挂起，如果有；就不在保存；返回错误信息
                //updated by 2018/1/23--li.liu06@hand-china.com
                SpmOrgDefination defination=new SpmOrgDefination();
                defination.setCode(spmOrgDefination.getCode());
                List<SpmOrgDefination>definationLists=spmOrgDefinationMapper.queryByOrganization(defination);
                if (SystemProfileConstants.YES.equals(salesOrgFlag)&& spmOrgDefination.getSalesOrgId() != null){
                    SpmSalesOrganization saleOrg = spmSalesOrganizationMapper
                            .selectByPrimaryKey(spmOrgDefination.getSalesOrgId());
                    if(!definationLists.get(0).getCompanyId().equals(spmOrgDefination.getCompanyId())&&
                            saleOrg.getParentOrgId()!=null && saleOrg.getMarketId()!=null)
                        return null;
                }
                Long locationId = spmOrgDefination.getLocationId();
                SpmLocation location = spmOrgDefination.getSpmLocation();
                location.setLocationId(locationId);
                spmLocationMapper.updateByPrimaryKeySelective(location);
                spmOrgDefinationMapper.updateByPrimaryKeySelective(spmOrgDefination);
                if (SystemProfileConstants.YES.equals(salesOrgFlag) && spmOrgDefination.getSalesOrgId() != null) {
                    SpmSalesOrganization salesOrg = spmSalesOrganizationMapper
                            .selectByPrimaryKey(spmOrgDefination.getSalesOrgId());
                    salesOrg.setName(spmOrgDefination.getName());
                    salesOrg.setLocationId(spmOrgDefination.getLocationId());
                    salesOrg.setEndActiveDate(spmOrgDefination.getEndActiveDate());
                    salesOrg.set__tls(spmOrgDefination.get__tls());
                    salesOrg = self().saveSpmSalesOrganization(request, salesOrg, false);
                }
                if (SystemProfileConstants.YES.equals(invOrgFlag) && spmOrgDefination.getInvOrgId() != null) {
                    SpmInvOrganization invOrg = spmInvOrganizationMapper
                            .selectByPrimaryKey(spmOrgDefination.getInvOrgId());
                    invOrg.setName(spmOrgDefination.getName());
                    invOrg.setLocationId(spmOrgDefination.getLocationId());
                    invOrg.setEndActiveDate(spmOrgDefination.getEndActiveDate());
                    invOrg.set__tls(spmOrgDefination.get__tls());
                    invOrg = self().saveSpmInvOrganization(request, invOrg, false);
                }
            }
        }
        return spmOrgDefinations;
    }

    @Override
    public SpmSalesOrganization saveSpmSalesOrganization(IRequest request,
                                                         @StdWho SpmSalesOrganization spmSalesOrganization, boolean fal) {
        if (fal) {
            spmSalesOrganizationMapper.insert(spmSalesOrganization);
        } else {
            spmSalesOrganizationMapper.updateByPrimaryKeySelective(spmSalesOrganization);
        }
        return spmSalesOrganization;
    }

    @Override
    public SpmInvOrganization saveSpmInvOrganization(IRequest request, @StdWho SpmInvOrganization spmInvOrganization,
                                                     boolean fal) {
        if (fal) {
            spmInvOrganizationMapper.insert(spmInvOrganization);
        } else {
            spmInvOrganizationMapper.updateByPrimaryKeySelective(spmInvOrganization);
        }
        return spmInvOrganization;
    }

}
