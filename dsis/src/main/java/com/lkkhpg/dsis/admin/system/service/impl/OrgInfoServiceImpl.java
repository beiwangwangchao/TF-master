/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.system.service.IOrgInfoService;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmInvOrganizationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 获取当前组织信息的Service实现类.
 * @author chenjingxiong
 */
@Service
@Transactional
public class OrgInfoServiceImpl implements IOrgInfoService {

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;
    
    @Autowired
    private SpmInvOrganizationMapper spmInvOrganizationMapper;
    
    @Override
    public SpmSalesOrganization querySalesInfo(IRequest request) {
        Long salesOrgId = (Long) request.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        if (salesOrgId != null) {
            return spmSalesOrganizationMapper.queryBaseInfo(salesOrgId);
        }
        return null;
    }

    @Override
    public SpmInvOrganization queryInvInfo(IRequest request) {
        Long invOrgId = (Long) request.getAttribute(SystemProfileConstants.INV_ORG_ID);
        if (invOrgId != null) {
            return spmInvOrganizationMapper.queryBaseInfo(invOrgId); 
        }
        return null;
    }

}
