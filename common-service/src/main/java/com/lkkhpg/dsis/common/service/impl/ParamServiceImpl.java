/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.config.dto.SpmLevelPriority;
import com.lkkhpg.dsis.common.config.mapper.SpmLevelPriorityMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.ILocationParamService;
import com.lkkhpg.dsis.common.service.IMarketParamService;
import com.lkkhpg.dsis.common.service.IOrgParamService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 组织属性获取Service实现.
 * 
 * @author Zhao
 *
 */
@Service
public class ParamServiceImpl implements IParamService {

    @Autowired
    private ILocationParamService locationParamService;

    @Autowired
    private IOrgParamService orgParamService;

    @Autowired
    private IMarketParamService marketParamService;

    @Autowired
    private SpmLevelPriorityMapper spmLevelPriorityMapper;

    @Override
    public List<String> getParamValues(IRequest request, String paramName, String orgType, Long orgId) {
    	// 无组织信息，返回空
        if (orgId == null) {
            return null;
        }
        String orgIdStr = orgId.toString();
        List<SpmLevelPriority> list = spmLevelPriorityMapper.queryLevelValue(orgType, paramName); // get优先级类型
        List<String> result = new ArrayList<String>();
        for (SpmLevelPriority aList : list) {
            Long level = aList.getLevelId();
            if (SystemProfileConstants.COUNTRY.equals(level) || SystemProfileConstants.STATE.equals(level)
                    || SystemProfileConstants.CITY.equals(level)) {
                result = locationParamService.getParamValues(request, paramName, level, orgIdStr, orgType);
                if (result != null && result.size() > 0) {
                    return result;
                }
            } else if (SystemProfileConstants.INV_ORG.equals(level) || SystemProfileConstants.SALES_ORG.equals(level)) {
                result = orgParamService.getParamValues(request, paramName, level, orgIdStr, orgType);
                if (result != null && result.size() > 0) {
                    return result;
                }
            } else if (SystemProfileConstants.OU.equals(level) || SystemProfileConstants.MARKET.equals(level)) {
                // 获取OU或MarketId
                String ouOrMarketId = marketParamService.getMarketOrOuId(request, orgType, orgIdStr);
                result = marketParamService.getParamValues(request, paramName, level, ouOrMarketId, orgType);
                if (result != null && result.size() > 0) {
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * 根据组织类型获取相关参数.
     * 
     * @param request
     *            请求上下文
     * @param orgType
     *            组织类型
     * @return 组织 ID
     */
    private Long getOrgId(IRequest request, String orgType) {
        Object orgId = null;
        if (SystemProfileConstants.ORG_TYPE_SALES.equals(orgType)) {
            orgId = request.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        } else if (SystemProfileConstants.ORG_TYPE_INV.equals(orgType)) {
            orgId = request.getAttribute(SystemProfileConstants.INV_ORG_ID);
        }
        if (orgId != null) {
            return (Long) orgId;
        }
        return null;
    }

    @Override
    public List<String> getParamValues(IRequest request, String paramName, String orgType) {
        Long orgId = getOrgId(request, orgType);
        return self().getParamValues(request, paramName, orgType, orgId);
    }

    @Override
    public List<String> getSalesParamValues(IRequest request, String paramName, Long salesOrgId) {
        return self().getParamValues(request, paramName, SystemProfileConstants.ORG_TYPE_SALES, salesOrgId);
    }

    @Override
    public List<String> getInvParamValues(IRequest request, String paramName, Long invOrgId) {
        return self().getParamValues(request, paramName, SystemProfileConstants.ORG_TYPE_INV, invOrgId);
    }

    @Override
    public List<String> getMarketParamValues(IRequest request, String paramName, Long marketId) {
        return marketParamService.getParamValues(request, paramName, SystemProfileConstants.MARKET, marketId.toString(),
                SystemProfileConstants.ORG_TYPE_SALES);
    }

    @Override
    public List<String> getOuParamValues(IRequest request, String paramName, Long opertingUnitId) {
        return marketParamService.getParamValues(request, paramName, SystemProfileConstants.OU,
                opertingUnitId.toString(), SystemProfileConstants.ORG_TYPE_INV);
    }

}
