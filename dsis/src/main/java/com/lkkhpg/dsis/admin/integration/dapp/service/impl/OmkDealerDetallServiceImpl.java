/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDealerDetallRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDealerDetallResponse;
import com.lkkhpg.dsis.admin.integration.dapp.mapper.OmkMapper;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOmkDealerDetallService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 获取会员详细信息实现类.
 * 
 * @author zhenyang.he
 *
 */
@Service
@Transactional
public class OmkDealerDetallServiceImpl implements IOmkDealerDetallService {

    @Autowired
    private OmkMapper omkMapper;

    /**
     * 获取会员详细信息.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OmkDealerDetallResponse getDealerDetall(OmkDealerDetallRequest omkDealerDetallRequest) throws DAppException {
        // TODO Auto-generated method stub
        String distributorId = omkDealerDetallRequest.getDistributorId();
        if (StringUtils.isEmpty(distributorId)) {
            throw new DAppException(IntegrationConstant.ISG_ERROR_REQUIRED_MISSING,
                    IntegrationConstant.STATUS_CODE_MEMBER, "distributorId=null");
        }
        OmkDealerDetallResponse omkDealerDetallResponse = omkMapper.getDealerDetall(omkDealerDetallRequest);
        return omkDealerDetallResponse;
    }

}
