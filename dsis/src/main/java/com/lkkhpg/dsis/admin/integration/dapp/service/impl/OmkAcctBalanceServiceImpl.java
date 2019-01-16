/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkAcctBalanceRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkAcctBalanceResponse;
import com.lkkhpg.dsis.admin.integration.dapp.mapper.OmkMapper;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOmkAcctBalanceService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 获取会员账号余额实现类.
 * 
 * @author zyhe
 *
 */
@Transactional
@Service
public class OmkAcctBalanceServiceImpl implements IOmkAcctBalanceService {

    @Autowired
    private OmkMapper omkMapper;

    @Autowired
    private IDAppUtilService dappUtilService;

    /**
     * 获取账号余额数据.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OmkAcctBalanceResponse getAcctBalance(OmkAcctBalanceRequest omkAcctBalanceRequest) throws DAppException {
        // TODO Auto-generated method stub
        String distributorId = omkAcctBalanceRequest.getDistributorId();
        String language = omkAcctBalanceRequest.getLanguage();
        if (StringUtils.isEmpty(distributorId) || StringUtils.isEmpty(language)) {
            throw new DAppException(IntegrationConstant.ISG_ERROR_REQUIRED_MISSING,
                    IntegrationConstant.STATUS_CODE_MEMBER, "distributorId=null or language=null");
        }
        String lang = dappUtilService.getGdsLang(language);
        OmkAcctBalanceRequest acctBalanceRequest = new OmkAcctBalanceRequest();
        acctBalanceRequest.setDistributorId(distributorId);
        acctBalanceRequest.setLanguage(lang);
        OmkAcctBalanceResponse omkAcctBalanceResponse = omkMapper.getAccountBalance(acctBalanceRequest);
        return omkAcctBalanceResponse;
    }

}
