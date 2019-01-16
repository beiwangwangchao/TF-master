/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.system.service.ISysUserRoleAssignService;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.system.dto.OrgTemplateDtl;
import com.lkkhpg.dsis.common.system.dto.RoleTemplate;
import com.lkkhpg.dsis.common.system.dto.SysUserRoleAssign;
import com.lkkhpg.dsis.common.system.dto.SysUserRoleAssignExample;
import com.lkkhpg.dsis.common.system.mapper.OrgTemplateDtlMapper;
import com.lkkhpg.dsis.common.system.mapper.RoleTemplateMapper;
import com.lkkhpg.dsis.common.system.mapper.SysUserRoleAssignMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.UserRole;
import com.lkkhpg.dsis.platform.mapper.system.UserRoleMapper;
import com.lkkhpg.dsis.platform.service.IUserRoleService;

/**
 * 用户角色分配组织Service实现类.
 * 
 * @author chenjingxiong
 */
@Service
public class SysUserRoleAssignServiceImpl implements ISysUserRoleAssignService {

    @Autowired
    private SysUserRoleAssignMapper sysUserRoleAssignMapper;
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    @Autowired
    private RoleTemplateMapper roleTemplateMapper;
    @Autowired
    private OrgTemplateDtlMapper orgTemplateDtlMapper;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<SysUserRoleAssign> getUserRoleAssigns(Long userId, Long roleId) {
        SysUserRoleAssignExample example = new SysUserRoleAssignExample();
        example.createCriteria().andUserIdEqualTo(userId).andRoleIdEqualTo(roleId);
        return sysUserRoleAssignMapper.selectByExample(example);
    }

    @Override
    public Map<String, Object> getOrgs(IRequest request, Long userId, Long roleId) {
        List<SysUserRoleAssign> userRoleAssigns = self().getUserRoleAssigns(userId, roleId);
        List<SpmSalesOrganization> salesOrgIds = new ArrayList<SpmSalesOrganization>();
        List<SpmInvOrganization> invOrgIds = new ArrayList<SpmInvOrganization>();
        for (SysUserRoleAssign userRoleAssign : userRoleAssigns) {
            switch (userRoleAssign.getAssignType()) {
            // 销售区域
            case SysUserRoleAssign.SALES_ORG_ASSIGN_TYPE:
                SpmSalesOrganization salesOrg = new SpmSalesOrganization();
                salesOrg.setSalesOrgId(userRoleAssign.getAssignValue());
                salesOrg.setName(userRoleAssign.getAssignValueName());
                salesOrg.setDefaultFlag(userRoleAssign.getDefaultFlag());
                SpmMarket market = spmMarketMapper.selectBySalesOrgId(userRoleAssign.getAssignValue());
                salesOrg.setMarket(market);
                salesOrgIds.add(salesOrg);
                break;
            // 库存组织
            case SysUserRoleAssign.INV_ORG_ASSIGN_TYPE:
                SpmInvOrganization organization = new SpmInvOrganization();
                organization.setInvOrgId(userRoleAssign.getAssignValue());
                organization.setName(userRoleAssign.getAssignValueName());
                organization.setDefaultFlag(userRoleAssign.getDefaultFlag());
                invOrgIds.add(organization);
                break;
            default:
                break;
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_SALES_ORG, salesOrgIds);
        result.put(RESULT_INV_ORG, invOrgIds);
        return result;
    }

    @Override
    public List<SpmSalesOrganization> getSalesOrgs(IRequest request, Long userId, Long roleId) {
        List<SysUserRoleAssign> userRoleAssigns = self().getUserRoleAssigns(userId, roleId);
        List<SpmSalesOrganization> salesOrgIds = new ArrayList<SpmSalesOrganization>();
        List<SpmInvOrganization> invOrgIds = new ArrayList<SpmInvOrganization>();
        for (SysUserRoleAssign userRoleAssign : userRoleAssigns) {
            switch (userRoleAssign.getAssignType()) {
            // 销售区域
            case SysUserRoleAssign.SALES_ORG_ASSIGN_TYPE:
                SpmSalesOrganization salesOrg = new SpmSalesOrganization();
                salesOrg.setSalesOrgId(userRoleAssign.getAssignValue());
                salesOrg.setName(userRoleAssign.getAssignValueName());
                salesOrg.setDefaultFlag(userRoleAssign.getDefaultFlag());
                SpmMarket market = spmMarketMapper.selectBySalesOrgId(userRoleAssign.getAssignValue());
                salesOrg.setMarket(market);
                salesOrgIds.add(salesOrg);
                break;
            // 库存组织
           /* case SysUserRoleAssign.INV_ORG_ASSIGN_TYPE:
                SpmInvOrganization organization = new SpmInvOrganization();
                organization.setInvOrgId(userRoleAssign.getAssignValue());
                organization.setName(userRoleAssign.getAssignValueName());
                organization.setDefaultFlag(userRoleAssign.getDefaultFlag());
                invOrgIds.add(organization);
                break;*/
            default:
                break;
            }
        }
        return salesOrgIds;
    }
    
    @Override
    public List<SpmInvOrganization> getInvOrgs(IRequest request, Long userId, Long roleId){
        List<SysUserRoleAssign> userRoleAssigns = self().getUserRoleAssigns(userId, roleId);
        List<SpmSalesOrganization> salesOrgIds = new ArrayList<SpmSalesOrganization>();
        List<SpmInvOrganization> invOrgIds = new ArrayList<SpmInvOrganization>();
        for (SysUserRoleAssign userRoleAssign : userRoleAssigns) {
            switch (userRoleAssign.getAssignType()) {
            // 销售区域
           /* case SysUserRoleAssign.SALES_ORG_ASSIGN_TYPE:
                SpmSalesOrganization salesOrg = new SpmSalesOrganization();
                salesOrg.setSalesOrgId(userRoleAssign.getAssignValue());
                salesOrg.setName(userRoleAssign.getAssignValueName());
                salesOrg.setDefaultFlag(userRoleAssign.getDefaultFlag());
                SpmMarket market = spmMarketMapper.selectBySalesOrgId(userRoleAssign.getAssignValue());
                salesOrg.setMarket(market);
                salesOrgIds.add(salesOrg);
                break;*/
            // 库存组织
            case SysUserRoleAssign.INV_ORG_ASSIGN_TYPE:
                SpmInvOrganization organization = new SpmInvOrganization();
                organization.setInvOrgId(userRoleAssign.getAssignValue());
                organization.setName(userRoleAssign.getAssignValueName());
                organization.setDefaultFlag(userRoleAssign.getDefaultFlag());
                invOrgIds.add(organization);
                break;
            default:
                break;
            }
        }
        return invOrgIds;
    }
    
    @Override
    public List<SysUserRoleAssign> queryAssign(IRequest request, SysUserRoleAssign sur) {
        return sysUserRoleAssignMapper.selectOrgAssign(sur);
    }

    @Override
    public void deleteAssign(IRequest request, List<SysUserRoleAssign> surs) {
        for (SysUserRoleAssign sur : surs) {
            sysUserRoleAssignMapper.deleteById(sur.getUserRoleAssignId());
        }
    }

    @Override
    public void saveAssign(IRequest request, List<SysUserRoleAssign> surs) {
        for (SysUserRoleAssign sur : surs) {
            if (DTOStatus.ADD.equals(sur.get__status())) {
                List<SysUserRoleAssign> qsurs = sysUserRoleAssignMapper.selectByRecord(sur);
                if (qsurs.size() == 0) {
                    sysUserRoleAssignMapper.insertSelective(sur);
                }
            } else if (DTOStatus.UPDATE.equals(sur.get__status())) {
                sysUserRoleAssignMapper.updateByPrimaryKeySelective(sur);
            } else if (DTOStatus.DELETE.equals(sur.get__status())) {
                sysUserRoleAssignMapper.deleteById(sur.getUserRoleAssignId());
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<UserRole> saveUserAssign(IRequest request, List<UserRole> userRoles) {
        for (UserRole sur : userRoles) {
            if (sur.get__status() != null) {
                switch (sur.get__status()) {
                case DTOStatus.ADD:
                    RoleTemplate rt = new RoleTemplate();
                    rt.setRoleId(sur.getRoleId());
                    List<RoleTemplate> rtlist = roleTemplateMapper.querytemplate(rt);
                    if (rtlist.size() > 0 && rtlist.get(0) != null) {
                        List<OrgTemplateDtl> orgts = orgTemplateDtlMapper
                                .selectOrgTemplateDtlsById(rtlist.get(0).getTemplateId());
                        for (OrgTemplateDtl orgt : orgts) {
                            // 插入assign表
                            SysUserRoleAssign sura = new SysUserRoleAssign();
                            sura.setRoleId(sur.getRoleId());
                            sura.setUserId(sur.getUserId());
                            sura.setAssignType(orgt.getAssignType());
                            sura.setAssignValue(orgt.getAssignValue());
                            sura.setDefaultFlag(orgt.getDefaultFlag());
                            sysUserRoleAssignMapper.insertSelective(sura);
                        }
                    }
                    break;
                case DTOStatus.DELETE:
                    SysUserRoleAssign sura = new SysUserRoleAssign();
                    sura.setRoleId(sur.getRoleId());
                    sura.setUserId(sur.getUserId());
                    sysUserRoleAssignMapper.deleteByRecord(sura);
                    break;
                default:
                    break;
                }
            }
        }
        userRoleService.processBatchUserRole(request, userRoles);
        return userRoles;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void deleteUserAssign(IRequest request, List<UserRole> userRoles) {
        for (UserRole userRole : userRoles) {
            SysUserRoleAssign sura = new SysUserRoleAssign();
            sura.setRoleId(userRole.getRoleId());
            sura.setUserId(userRole.getUserId());
            // 删除组织分配关系
            sysUserRoleAssignMapper.deleteByRecord(sura);
            // 删除角色分配关系
            userRoleMapper.deleteByRecord(userRole);
        }
    }

}
