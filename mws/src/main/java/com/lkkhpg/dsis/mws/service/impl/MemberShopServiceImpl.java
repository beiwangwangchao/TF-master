/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.mws.service.IMemberShopService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 选择店铺service实现.
 * @author Zhaoqi
 *
 */
@Service
public class MemberShopServiceImpl implements IMemberShopService {
    
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Override
    public List<SpmMarket> queryMarket(IRequest request, Long marketId) {
        return spmMarketMapper.queryMarket(marketId);
    }

    @Override
    public List<SpmSalesOrganization> selectByMarketName(IRequest request, Long marketId) {
        return spmSalesOrganizationMapper.selectByMarketId(marketId);
    }

    @Override
    public SpmMarket selectMarketByCode(IRequest request, String code) {
        return spmMarketMapper.selectMarketByCode(code);
    }

    @Override
    public SpmSalesOrganization selectBySalesOrgId(IRequest request, Long salesOrgId) {
        return spmSalesOrganizationMapper.selectByPrimaryKey(salesOrgId);
    }

    @Override
    public SpmSalesOrganization showShopBySalesOrgId(IRequest request, Long salesOrgId) {
        return spmSalesOrganizationMapper.selectBySalesOrgId(salesOrgId);
    }

}
