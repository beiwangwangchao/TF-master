/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.common.service.ISpmMarketService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.mapper.system.MessageSmsAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 市场Service接口实现.
 * 
 * @author shenqb
 */
@Service
@Transactional
public class SpmMarketServiceImpl implements ISpmMarketService {
	
	@Autowired
    MemSiteMapper memSiteMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private SpmLocationMapper locationMapper;
    
    @Autowired
    private MessageSmsAccountMapper messageSmsAccountMapper;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    public static final String en_GB = "en_GB";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SpmMarket> saveMarket(IRequest request, List<SpmMarket> markets) throws CommSystemProfileException {
        for (SpmMarket market : markets) {

            String status = market.get__status();
            market.setEnabledFlag(SystemProfileConstants.YES);
            switch (status) {
            case DTOStatus.ADD:
                if (spmMarketMapper.queryCount(market) > 0) {
                    throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_SPM_MARKET_CODE_UNIQUE,
                            new Object[] {});
                }
                SpmLocation location = market.getSpmLocation();
                location.setEnabledFlag(SystemProfileConstants.YES);
                locationMapper.insertSelective(location);
                market.setLocationId(location.getLocationId());
                spmMarketMapper.insert(market);
                break;
            case DTOStatus.UPDATE:
                //updated by liliu06@hand-china.com 已挂过的销售组织是不准在跟新，除非解除关系。
                SpmMarket spmMarket=spmMarketMapper.selectByPrimaryKey(market.getMarketId());
                List<SpmSalesOrganization> lists=spmSalesOrganizationMapper.selectByMarketId(market.getMarketId());
                if(!spmMarket.getCompanyId().equals(market.getCompanyId()) && lists.size()!=0){
                    return null;
                }
                SpmLocation locations = market.getSpmLocation();
                if (market.getLocationId() != null && locations != null) {
                    locations.setLocationId(market.getLocationId());
                    locationMapper.updateByPrimaryKeySelective(locations);
                }
                spmMarketMapper.updateByPrimaryKeySelective(market);
                break;
            case DTOStatus.DELETE:
                spmMarketMapper.deleteByPrimaryKey(market);
                break;
            default:
                break;
            }
        }

        return markets;
    }

    @Override
    public List<SpmMarket> selectCompanyId(Long marketId) {
        return spmMarketMapper.selectCompanyId(marketId);
    }

    ;

    @Override
    public boolean deleteMarket(IRequest request, List<SpmMarket> markets) {
        for (SpmMarket market : markets) {
            spmMarketMapper.deleteByPrimaryKey(market);
        }

        return true;
    };

    @Override
    public List<SpmMarket> queryMarket(IRequest request, SpmMarket market){
        return spmMarketMapper.queryByMarket(market);
    }


    @Override
    public List<SpmMarket> queryMarket(IRequest request, SpmMarket market, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<SpmMarket> list = spmMarketMapper.queryByMarket(market);
        for (SpmMarket spmMarket : list) {
            SpmLocation location = locationMapper.selectByPrimaryKey(spmMarket.getLocationId());
            if (location != null) {
                spmMarket.setSpmLocation(location);
                String Y = memSiteMapper.isHideState(location.getCountryCode());
                if(en_GB.equals(request.getLocale())){
                	StringBuilder sb = new StringBuilder();
                	sb.append(location.getAddressLine1());
                	if(location.getAddressLine2() != null){
                		sb.append(",").append(location.getAddressLine2());
                	}
                	if(location.getAddressLine3() != null){
                		sb.append(",").append(location.getAddressLine3());
                	}
                	sb.append(",").append(location.getCityName());
                	if(!"Y".equals(Y)){
                		sb.append(",").append(location.getStateName());
                	}
                	sb.append(",").append(location.getCountryName());
                	spmMarket.setLocationName(sb.toString());
                } else {
                	StringBuilder sb = new StringBuilder();
                	sb.append(location.getCountryName());
                	if(!"Y".equals(Y)){
                		sb.append(location.getStateName());
                	}
                	sb.append(location.getCityName());
                	sb.append(location.getAddressLine1());
                	if(location.getAddressLine2() != null){
                		sb.append(location.getAddressLine2());
                	}
                	if(location.getAddressLine3() != null){
                		sb.append(location.getAddressLine3());
                	}
                	spmMarket.setLocationName(sb.toString());
                	//spmMarket.setLocationName(location.getAddress());
                }
            }
        }
        return list;

    };

    @Override
    public List<SpmMarket> queryMarket2(IRequest request, SpmMarket market, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<SpmMarket> list = spmMarketMapper.queryByMarketId(market);
        for (SpmMarket spmMarket : list) {
            SpmLocation location = locationMapper.selectByPrimaryKey(spmMarket.getLocationId());
            if (location != null) {
                spmMarket.setSpmLocation(location);
                String Y = memSiteMapper.isHideState(location.getCountryCode());
                if(en_GB.equals(request.getLocale())){
                    StringBuilder sb = new StringBuilder();
                    sb.append(location.getAddressLine1());
                    if(location.getAddressLine2() != null){
                        sb.append(",").append(location.getAddressLine2());
                    }
                    if(location.getAddressLine3() != null){
                        sb.append(",").append(location.getAddressLine3());
                    }
                    sb.append(",").append(location.getCityName());
                    if(!"Y".equals(Y)){
                        sb.append(",").append(location.getStateName());
                    }
                    sb.append(",").append(location.getCountryName());
                    spmMarket.setLocationName(sb.toString());
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(location.getCountryName());
                    if(!"Y".equals(Y)){
                        sb.append(location.getStateName());
                    }
                    sb.append(location.getCityName());
                    sb.append(location.getAddressLine1());
                    if(location.getAddressLine2() != null){
                        sb.append(location.getAddressLine2());
                    }
                    if(location.getAddressLine3() != null){
                        sb.append(location.getAddressLine3());
                    }
                    spmMarket.setLocationName(sb.toString());
                    //spmMarket.setLocationName(location.getAddress());
                }
            }
        }
        return list;

    };

    @Override
    public SpmMarket queryByMarketId(IRequest request, Long marketId) {
        return spmMarketMapper.selectByPrimaryKey(marketId);
    }
    
    
    /**
     * 根据销售组织Id获取公司和市场.
     * 
     * @param salesOrgId
     *            销售组织Id
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/market/getCompAndMarket")
    @ResponseBody
    
    public Map<String, Object> getCompAndMarket(IRequest request, Long salesOrgId) {
        return spmMarketMapper.selectCompAndMarketBySalesOrgId(salesOrgId);
    }

    @Override
    public Map<String, Object> queryMarketsAndSmsQuanties(IRequest request) {
        Map<String, Object> map = new HashMap<>();
        Integer smsAmount = messageSmsAccountMapper.querySmsQuanties();
        Integer mktAmount = spmMarketMapper.queryMarketsQuanties();
        map.put("smsAmount", smsAmount);
        map.put("mktAmount", mktAmount);
        return map;
    }

    @Override
    public List<SpmMarket> queryMarketsByRole(IRequest request) {
        return spmMarketMapper.queryMarketsByRole(request.getRoleId(),request.getAttribute("userId"));
    }

}
