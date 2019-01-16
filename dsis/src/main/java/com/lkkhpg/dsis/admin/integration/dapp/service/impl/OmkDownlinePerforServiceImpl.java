/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDownlinePerforRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDownlinePerforResponse;
import com.lkkhpg.dsis.admin.integration.dapp.mapper.OmkMapper;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOmkDownlinePerforService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * @author songyuanhuang 获取下线绩效列表实现类.
 */
@Service
@Transactional
public class OmkDownlinePerforServiceImpl implements IOmkDownlinePerforService {

    @Autowired
    private OmkMapper omkMapper;

    @Autowired
    private IDAppUtilService dAppUtilService;

    /**
     * 获取下线绩效数据.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<OmkDownlinePerforResponse> getOmkDownlinePerfor(OmkDownlinePerforRequest OmkDownlinePerforRequest)
            throws DAppException {
        /*
         * String distributorId = OmkDownlinePerforRequest.getDistributorId();
         * String lang = OmkDownlinePerforRequest.getLang(); String period =
         * OmkDownlinePerforRequest.getPeriod(); String omkPv =
         * OmkDownlinePerforRequest.getOmkPv(); if
         * (StringUtils.isEmpty(distributorId) || StringUtils.isEmpty(lang) ||
         * StringUtils.isEmpty(period) || StringUtils.isEmpty(omkPv)) { throw
         * new DAppException(IntegrationConstant.ISG_ERROR_REQUIRED_MISSING,
         * IntegrationConstant.STATUS_CODE_MEMBER,
         * "distributorId=null or lang=null or period=null or omkPv=null"); }
         */

        List<OmkDownlinePerforResponse> omkDownlinePerfor = omkMapper.getOmkDownlinePerfor(OmkDownlinePerforRequest);
        return omkDownlinePerfor;
    }
}
