/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.service.ISpmSalesOrganizationService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 销售区域Service接口实现.
 * 
 * @author shenqb
 */
@Service
@Transactional
public class SpmSalesOrganizationServiceImpl implements ISpmSalesOrganizationService {

    private final Logger logger = LoggerFactory.getLogger(SpmSalesOrganizationServiceImpl.class);

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;
    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Override
    public List<SpmSalesOrganization> saveSalesOrganization(IRequest request,
                                                            List<SpmSalesOrganization> salesOrganizations) {
        for (SpmSalesOrganization salesOrganization : salesOrganizations) {

            String status = salesOrganization.get__status();
            if (logger.isDebugEnabled()) {
                logger.debug("status: {}", status);
            }

            if (status.equals(DTOStatus.ADD)) {
                spmSalesOrganizationMapper.insert(salesOrganization);
            } else if (status.equals(DTOStatus.UPDATE)) {
                spmSalesOrganizationMapper.updateByPrimaryKeySelective(salesOrganization);
            } else if (status.equals(DTOStatus.DELETE)) {
                spmSalesOrganizationMapper.deleteByPrimaryKey(salesOrganization.getSalesOrgId());
            }
        }

        return salesOrganizations;
    };

    /**
     * 删除销售区域.
     * 
     * @param request
     *            请求上下文
     * @param salesOrganizations
     *            销售区域List
     * @return boolean
     */
    @Override
    public boolean deleteSalesOrganization(IRequest request, List<SpmSalesOrganization> salesOrganizations) {
        for (SpmSalesOrganization salesOrganization : salesOrganizations) {

            String status = salesOrganization.get__status();
            if (logger.isDebugEnabled()) {
                logger.debug("status: {}", status);
            }

            spmSalesOrganizationMapper.deleteByPrimaryKey(salesOrganization.getSalesOrgId());
        }

        return true;
    };

    /**
     * 查询销售区域.
     * 
     * @param request
     *            请求上下文
     * @param salesOrganization
     *            销售区域DTO
     * @return 销售区域List
     */
    @Override
    public List<SpmSalesOrganization> querySalesOrganization(IRequest request, SpmSalesOrganization salesOrganization,
                                                             int page, int pagesize) {
        return spmSalesOrganizationMapper.queryBySalesOrganization(salesOrganization);
    }



    @Override
    public List<SpmSalesOrganization> querySalesOrgByTime(IRequest request, SpmSalesOrganization salesOrganization,
                                                          int page, int pagesize){
        return spmSalesOrganizationMapper.queryBySalesOrgByTime(salesOrganization);

    }
    /**
     * 查询所有销售区域.
     * 
     *            请求上下文
     * @param salesOrganization
     *            销售区域DTO
     * @return 销售区域List
     */
	@Override
	public List<SpmSalesOrganization> queryAllSalesOrganization(IRequest requestContext,
                                                                SpmSalesOrganization salesOrganization, int page, int pagesize) {
		return spmSalesOrganizationMapper.queryAllSalesOrganization(salesOrganization);
	}

    /**
     * 查询销售区域明细，主要返回包含销售区域的市场明细以及默认货币明细.
     * 
     * @param request
     *            请求上下文
     * @param salesOrgId
     *            销售区域id
     * @return 销售区域详细信息
     */
    @Override
    public SpmSalesOrganization getSalesOrganizationDetail(IRequest request, Long salesOrgId) {
        SpmSalesOrganization salesOrg = new SpmSalesOrganization();
        salesOrg.setSalesOrgId(salesOrgId);


        List<SpmSalesOrganization> salesOrgs = spmSalesOrganizationMapper.queryBySalesOrganization(salesOrg);
        if (salesOrgs.isEmpty()) {
            return salesOrg;
        }
        SpmSalesOrganization result = salesOrgs.iterator().next();
        SpmMarket market = new SpmMarket();
        market.setMarketId(result.getMarketId());
        List<SpmMarket> markets = spmMarketMapper.queryByMarket(market);
        if (!markets.isEmpty()) {
            result.setMarket(markets.iterator().next());
        }
        // TODO 根据销售区域获取默认货币明细.
        return result;
    }

    @Override
    public List<SpmSalesOrganization> getAssignSalesOrganization(IRequest request) {
        return spmSalesOrganizationMapper.queryByUserAndRole();
    }

    @Override
    public List<SpmSalesOrganization> queryBySalesFu(IRequest request, SpmSalesOrganization organization) {
        List<SpmSalesOrganization> list = spmSalesOrganizationMapper.queryBySalesFu(organization);
        if (list == null) {
            return null;
        }
        for (SpmSalesOrganization spmSalesOrganization : list) {
            List<SpmSalesOrganization> list2 = self().queryBySalesZi(request, spmSalesOrganization);
            spmSalesOrganization.setChildren(list2);
        }
        return list;
    }

    @Override
    public List<SpmSalesOrganization> queryBySalesZi(IRequest request, SpmSalesOrganization organization) {
        List<SpmSalesOrganization> list = spmSalesOrganizationMapper.queryBySalesZi(organization);
        if (list == null) {
            return null;
        }
        for (SpmSalesOrganization spmSalesOrganization : list) {
            List<SpmSalesOrganization> list2 = self().queryBySalesZi(request, spmSalesOrganization);
            spmSalesOrganization.setChildren(list2);
        }
        return list;
    }

    @Override
    public SpmSalesOrganization submitSalesOrganization(IRequest request, SpmSalesOrganization salesOrganization) {
        spmSalesOrganizationMapper.updateByPrimaryKeyMarket(salesOrganization);
        return salesOrganization;
    }

    @Override
    public List<SpmSalesOrganization> queryNull(IRequest request, SpmSalesOrganization salesOrganization, int page,
                                                int pagesize) {
        List<SpmSalesOrganization> list = spmSalesOrganizationMapper.queryNullBy(salesOrganization);
        return list;
    }

    @Override
    public List<SpmSalesOrganization> querySalesOrganizationByRole(IRequest request){
        System.out.println("--------------test started at 2018/01/17 13:34 ---------------");
        System.out.println(request.getAttributeNames());
        long roleId = (long)request.getAttribute("_roleId");

        List<SpmSalesOrganization> list = spmSalesOrganizationMapper.querySalesOrganizationByRole(roleId);

        System.out.println(list.get(0).toString());
        System.out.println(list.get(0).getSalesOrgId());
        return list;
    }
}
