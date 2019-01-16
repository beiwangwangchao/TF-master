/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamBalanceRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamBalanceResponse;
import com.lkkhpg.dsis.admin.integration.dapp.mapper.OmkMapper;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOmkTeamBalanceService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 团队余额查询实现类.
 * 
 * @author zhenyang.he
 *
 */
@Service
public class OmkTeamBalanceServiceImpl implements IOmkTeamBalanceService {

    @Autowired
    private OmkMapper omkMapper;

    /**
     * 团队余额查询实现方法.
     */
    @Override
    public OmkTeamBalanceResponse getTeamBalance(OmkTeamBalanceRequest omkTeamBalanceRequest) throws DAppException {
        String distributorId = omkTeamBalanceRequest.getDistributorId();
        if (StringUtils.isEmpty(distributorId)) {
            throw new DAppException(IntegrationConstant.ISG_ERROR_REQUIRED_MISSING,
                    IntegrationConstant.STATUS_CODE_MEMBER, "distributorId = null");
        }
        OmkTeamBalanceResponse omkTeamBalanceResponse = omkMapper.getTeamBalance(omkTeamBalanceRequest);
        return omkTeamBalanceResponse;
    }

}
