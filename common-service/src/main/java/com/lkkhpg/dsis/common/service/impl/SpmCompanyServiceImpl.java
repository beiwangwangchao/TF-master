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
import com.lkkhpg.dsis.common.config.dto.SpmCompany;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.mapper.SpmCompanyMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.constant.UserConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.common.service.ISpmCompanyService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 公司查询实现类.
 * 
 * @author liang.rao
 *
 */
@Service
public class SpmCompanyServiceImpl implements ISpmCompanyService {

	@Autowired
	MemSiteMapper memSiteMapper;
	
    @Autowired
    private SpmCompanyMapper spmCompanyMapper;

    @Autowired
    private SpmLocationMapper spmLocationMapper;
    
    public static final String en_GB = "en_GB";

    /**
     * 保存公司.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SpmCompany saveCompany(IRequest request, @StdWho SpmCompany company) throws CommSystemProfileException {
        if (company.getSpmLocation() == null) {
            throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_SPM_COMPANY_LOCATION_EMPTY,
                    new Object[] {});
        }
        if (company.getEmail() != null && !"".equals(company.getEmail())) {
            // 邮箱格式验证
            if (!company.getEmail().matches(UserConstants.EMAIL_REGEX)) {
                throw new CommSystemProfileException(CommSystemProfileException.EMAIL_FORMAT, new Object[] {});
            }
        }
        if (company.getCompanyId() != null) {
            company.setEnabledFlag(SystemProfileConstants.YES);
            spmLocationMapper.updateByPrimaryKeySelective(company.getSpmLocation());
            spmCompanyMapper.updateByPrimaryKey(company);
        } else {
            SpmCompany sc = new SpmCompany();
            SpmCompany scn = new SpmCompany();
            sc.setCode(company.getCode());
            scn.setName(company.getName());
            int codeCount = spmCompanyMapper.getUniqueCount(sc);
            if (codeCount > 0) {
                throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_SPM_COMPANY_CODE_UNIQUE,
                        new Object[] {});
            }
            int nameCount = spmCompanyMapper.getUniqueCount(scn);
            if (nameCount > 0) {
                throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_SPM_COMPANY_NAME_UNIQUE,
                        new Object[] {});
            }
            company.setEnabledFlag(SystemProfileConstants.YES);
            SpmLocation spml = company.getSpmLocation();
            spml.setEnabledFlag(SystemProfileConstants.YES);
            spmLocationMapper.insertSelective(spml);
            company.setLocationId(spml.getLocationId());
            spmCompanyMapper.insert(company);
        }
        return company;
    }

    @Override
    public List<SpmCompany> queyBrNo(SpmCompany company) {
       List<SpmCompany> scp=spmCompanyMapper.queryNo(company);
        return scp ;
    }



    @Override
    public SpmCompany selectPartner(Long marketId) {
        return spmCompanyMapper.selectPartner(marketId) ;
    }

    /**
     * 查询公司.
     */
    @Override
    public List<SpmCompany> queryCompany(IRequest request, SpmCompany company, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<SpmCompany> sc = spmCompanyMapper.queryCompany(company);
        return sc;
    }
    /**
     * 查询公司.
     * 数据屏蔽
     */
    @Override
    public List<SpmCompany> queryCompany2(IRequest request, SpmCompany company, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);

//        List<SpmCompany> sc = spmCompanyMapper.queryCompany2(company);
        List<SpmCompany> sc=spmCompanyMapper.queryCompanyByMarketId(company);
        return sc;
    }

    @Override
    public List<SpmCompany> queryCompanyById(IRequest request, SpmCompany company) {
        List<SpmCompany> sc = spmCompanyMapper.queryCompany(company);
        //System.out.println(sc.get(0).getGstId());
        if(sc.get(0).getGstId() !=null) {
            SpmCompany sc1 = new SpmCompany();
            sc1.setCompanyId(Long.valueOf(sc.get(0).getGstId()));
            sc1 = spmCompanyMapper.queryParentName(sc1);
            //System.out.println("+=++++++++++++++++++++++++++++++");
            //System.out.println(sc1.getCompanyName());
            sc.get(0).setParentCompanyName(sc1.getCompanyName());
        }
        if (sc.size() != 0) {
            SpmLocation spml = spmLocationMapper.selectByPrimaryKey(sc.get(0).getLocationId());
            String Y = memSiteMapper.isHideState(spml.getCountryCode());
            if(en_GB.equals(request.getLocale())){
            	StringBuilder sb = new StringBuilder();
            	sb.append(spml.getAddressLine1());
            	if(spml.getAddressLine2() != null){
            		sb.append(",").append(spml.getAddressLine2());
            	}
            	if(spml.getAddressLine3() != null){
            		sb.append(",").append(spml.getAddressLine3());
            	}
            	sb.append(",").append(spml.getCityName());
            	if(!"Y".equals(Y)){
            		sb.append(",").append(spml.getStateName());
            	}
            	sb.append(",").append(spml.getCountryName());
            	sc.get(0).setLocationName(sb.toString());
            } else {
            	//sc.get(0).setLocationName(spml.getAddress());
            	StringBuilder sb = new StringBuilder();
            	sb.append(spml.getCountryName());
            	if(!"Y".equals(Y)){
            		sb.append(spml.getStateName());
            	}
            	sb.append(spml.getCityName());
            	sb.append(spml.getAddressLine1());
            	if(spml.getAddressLine2() != null){
            		sb.append(spml.getAddressLine2());
            	}
            	if(spml.getAddressLine3() != null){
            		sb.append(spml.getAddressLine3());
            	}
            	sc.get(0).setLocationName(sb.toString());
            }
            sc.get(0).setCountryName(spml.getCountryName());
            sc.get(0).setCountryCode(spml.getCountryCode());
            sc.get(0).setStateName(spml.getStateName());
            sc.get(0).setStateCode(spml.getStateCode());
            sc.get(0).setCityName(spml.getCityName());
            sc.get(0).setCityCode(spml.getCityCode());
            sc.get(0).setAddressLine1(spml.getAddressLine1());
            sc.get(0).setAddressLine2(spml.getAddressLine2());
            sc.get(0).setAddressLine3(spml.getAddressLine3());
            sc.get(0).setZipCode(spml.getZipCode());
        }
        return sc;
    }

}
