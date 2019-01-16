/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.dto.OrgSelection;
import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmCurrencyMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmInvOrganizationMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.common.service.ISpmInvOrganizationService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 库存组织Service接口实现.
 * 
 * @author shenqb
 */
@Service
@Transactional
public class SpmInvOrganizationServiceImpl implements ISpmInvOrganizationService {

    private final Logger logger = LoggerFactory.getLogger(SpmInvOrganizationServiceImpl.class);

    @Autowired
    private SpmInvOrganizationMapper spmInvOrganizationMapper;

    @Autowired
    private IParamService paramService;

    @Autowired
    private SpmCurrencyMapper spmCurrencyMapper;

    /**
     * 保存库存组织.
     * 
     * @param request
     *            请求上下文
     * @param invOrganizations
     *            库存组织List
     * @return 库存组织List
     */
    @Override
    public List<SpmInvOrganization> saveInvOrganization(IRequest request, List<SpmInvOrganization> invOrganizations) {
        for (SpmInvOrganization invOrganization : invOrganizations) {

            String status = invOrganization.get__status();
            if (logger.isDebugEnabled()) {
                logger.debug("status: {}", status);
            }

            switch (status) {
            case DTOStatus.ADD:
                spmInvOrganizationMapper.insert(invOrganization);
                break;
            case DTOStatus.UPDATE:
                spmInvOrganizationMapper.updateByPrimaryKeySelective(invOrganization);
                break;
            case DTOStatus.DELETE:
                spmInvOrganizationMapper.deleteByPrimaryKey(invOrganization.getInvOrgId());
                break;
            default:
                break;
            }
        }

        return invOrganizations;
    };

    /**
     * 删除库存组织.
     * 
     * @param request
     *            请求上下文
     * @param invOrganizations
     *            库存组织List
     * @return boolean
     */
    @Override
    public boolean deleteInvOrganization(IRequest request, List<SpmInvOrganization> invOrganizations) {
        for (SpmInvOrganization invOrganization : invOrganizations) {

            String status = invOrganization.get__status();
            if (logger.isDebugEnabled()) {
                logger.debug("status: {}", status);
            }

            spmInvOrganizationMapper.deleteByPrimaryKey(invOrganization.getInvOrgId());
        }

        return true;
    };

    /**
     * 查询库存组织.
     * 
     * @param request
     *            请求上下文
     * @param invOrganization
     *            库存组织DTO
     * @return 库存组织List
     */
    @Override
    public List<SpmInvOrganization> queryInvOrganizations(IRequest request, SpmInvOrganization invOrganization,
            int page, int pagesize) {
        return spmInvOrganizationMapper.querySpmInvOrganizations(invOrganization);
    }

    @Override
    public List<SpmInvOrganization> querySpmInvOrganizations(IRequest request, SpmInvOrganization invOrganization) {
        // TODO Auto-generated method stub
        return spmInvOrganizationMapper.querySpmInvOrganizations(invOrganization);
    };

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public OrgSelection getOrgSelection(IRequest request, Long defaultOrgId) {
        SpmInvOrganization invOrg = new SpmInvOrganization();
        invOrg.setInvOrgId(defaultOrgId);
        return spmInvOrganizationMapper.getOrgSelection(invOrg);
    }

    @Override
    public List<SpmInvOrganization> getAssignInvOrganization(IRequest request) {
        return spmInvOrganizationMapper.queryByUserAndRole();
    }

    @Override
    public List<SpmInvOrganization> getValidOrg(IRequest request) {
        // TODO Auto-generated method stub
        return spmInvOrganizationMapper.getValidOrg();
    }

    @Override
    public List<SpmInvOrganization> getValidOrg2(IRequest request) {
        // TODO Auto-generated method stub
        return spmInvOrganizationMapper.getValidOrg2();
    }

    @Override
    public List<SpmInvOrganization> getCurrentSupplyInvOrgs(IRequest request) {
        return spmInvOrganizationMapper.getCurrentSupplyInvOrgs();
    }

    @Override
    public Map<String, Object> getSupplyInvOrgsAndCurrencyBySalesOrg(IRequest request, Long salesOrgId) {
        // TODO Auto-generated method stub
        Map<String, Object> map = new HashMap<String, Object>();
        List<SpmInvOrganization> spmInvOrganizations = spmInvOrganizationMapper.getSupplyInvOrgsBySalesOrg(salesOrgId);
        map.put("inv", spmInvOrganizations);

        List<String> currencyParams = paramService.getParamValues(request, SystemProfileConstants.SPM_CURRENCY,
                SystemProfileConstants.ORG_TYPE_SALES, salesOrgId);
        if (currencyParams == null || currencyParams.isEmpty()) {
            if (logger.isErrorEnabled()) {
                logger.error("order basic data currency error");
            }
            return map;
        }
        String currencyCode = currencyParams.iterator().next();

        SpmCurrency spmCurreny = spmCurrencyMapper.selectByPrimaryKey(currencyCode);

        map.put("currency", spmCurreny);
        return map;
    }

    @Override
    public List<SpmInvOrganization> getSupplyInvOrgsBySalesOrg(IRequest request, Long salesOrgId) {
        // TODO Auto-generated method stub
        return spmInvOrganizationMapper.getSupplyInvOrgsBySalesOrg(salesOrgId);
    }

    @Override
    public List<SpmInvOrganization> getSupplyInvOrgsByOrderId(IRequest request, Long orderId) {
        // TODO Auto-generated method stub
        return spmInvOrganizationMapper.getSupplyInvOrgsByOrderId(orderId);
    }

    @Override
    public List<SpmInvOrganization> queryInvOrgsByRole(IRequest request) {
        return spmInvOrganizationMapper.queryInvOrgsByRole(request.getRoleId(), request.getAttribute("userId"));
    }

    @Override
    public List<SpmInvOrganization> queryCostOrgsByRole(IRequest requestContext) {
        return spmInvOrganizationMapper.queryCostOrgsByRole();
    }
}
