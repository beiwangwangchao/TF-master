/*
 *
 */
package com.lkkhpg.dsis.admin.config.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.config.service.ISpmSupplyService;
import com.lkkhpg.dsis.common.config.dto.SpmSupplies;
import com.lkkhpg.dsis.common.config.dto.SpmSupply;
import com.lkkhpg.dsis.common.config.mapper.SpmSupplyMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织定义Service接口实现.
 * 
 * @author wangc
 */
@Service
@Transactional
public class SpmSupplyServiceImpl implements ISpmSupplyService {

    private final Logger logger = LoggerFactory.getLogger(SpmSupplyServiceImpl.class);

    @Autowired
    private SpmSupplyMapper spmSupplyMapper;

    @Override
    public List<?> querySupply(IRequest requestContext, SpmSupply supply, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<SpmSupply>supplies=spmSupplyMapper.queryBySupply(supply);
        return supplies;
    }

    @Override
    public List<SpmSupply> querySupplyByUserAndRole(IRequest requestContext, SpmSupply supply, int page, int pagesize){
        PageHelper.startPage(page,pagesize);
        List<SpmSupply> supplies=spmSupplyMapper.queryBySupplyByUserAndRole(supply);
//        List<SpmSupply> spms=spmSupplyMapper.queryBySupply(supply);
//        for(SpmSalesOrganization organization:salesOrgIds){
//              Long orgId= organization.getSalesOrgId();
//            for (SpmSupply supply1:spms){
//               if(supply1.getSalesOrgId().equals(orgId)){
//                   supplies.add(supply1);
//               }
//            }
//        }
        return supplies;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SpmSupplies saveSupplies(IRequest requestContext, SpmSupplies supplies) throws CommSystemProfileException {
        String supplyType = supplies.getSupplyType();
        List<SpmSupply> spmSupplies = supplies.getSupplies();
        if (SystemProfileConstants.SUPPLY_TYPE_ORG.equals(supplyType)) {
            for (SpmSupply spmSupply : spmSupplies) {
                if (DTOStatus.DELETE.equals(spmSupply.get__status())) {
                    SpmSupply delSupply = new SpmSupply();
                    delSupply.setSalesOrgId(spmSupply.getSalesOrgId());
                    delSupply.setSupplyType(supplyType);
                    spmSupplyMapper.deleteBySelective(delSupply);
                } else {
                    Long salesOrgId = spmSupply.getSalesOrgId();
                    if (DTOStatus.ADD.equals(spmSupply.get__status())) {
                        SpmSupply addSupply = new SpmSupply();
                        addSupply.setSupplyType(supplyType);
                        addSupply.setSalesOrgId(salesOrgId);
                        Integer result = spmSupplyMapper.querySpmSupply(addSupply);
                        if (result > 0) {
                            throw new CommSystemProfileException(
                                    CommSystemProfileException.MSG_ERROR_SALES_ORG_IS_REPEAT, new Object[] {});
                        }
                    }
                    List<SpmSupply> invSupplies = spmSupply.getInvs();
                    if (invSupplies == null || invSupplies.isEmpty()) {
                        throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_INV_ORG_IS_NULL,
                                new Object[] {});
                    }
                    checkDefaultFlag(invSupplies);
                    for (SpmSupply supply : invSupplies) {
                        if (DTOStatus.DELETE.equals(supply.get__status())) {
                            SpmSupply delSupply = new SpmSupply();
                            delSupply.setSupplyType(supplyType);
                            delSupply.setSupplyId(supply.getSupplyId());
                            spmSupplyMapper.deleteBySelective(delSupply);
                        }
                        if (DTOStatus.ADD.equals(supply.get__status())) {
                            supply.setSupplyType(supplyType);
                            supply.setSalesOrgId(salesOrgId);
                            Integer result = spmSupplyMapper.querySpmSupply(supply);
                            if (result > 0) {
                                throw new CommSystemProfileException(
                                        CommSystemProfileException.MSG_ERROR_INV_ORG_IS_REPEAT, new Object[] {});
                            }
                            spmSupplyMapper.insertSelective(supply);
                        }
                    }
                }

            }
        }
        if (SystemProfileConstants.SUPPLY_TYPE_SITE.equals(supplyType)) {
            for (SpmSupply spmSupply : spmSupplies) {
                if (DTOStatus.DELETE.equals(spmSupply.get__status())) {
                    SpmSupply delSupply = new SpmSupply();
                    delSupply.setSupplyType(supplyType);
                    delSupply.setCountryCode(spmSupply.getCountryCode());
                    delSupply.setStateCode(spmSupply.getStateCode());
                    delSupply.setCityCode(spmSupply.getCityCode());
                    spmSupplyMapper.deleteBySelective(delSupply);
                } else {
                    String countryCode = spmSupply.getCountryCode();
                    String stateCode = spmSupply.getStateCode();
                    String cityCode = spmSupply.getCityCode();
                    if (DTOStatus.ADD.equals(spmSupply.get__status())) {
                        SpmSupply addSupply = new SpmSupply();
                        addSupply.setSupplyType(supplyType);
                        addSupply.setCountryCode(spmSupply.getCountryCode());
                        addSupply.setStateCode(spmSupply.getStateCode());
                        addSupply.setCityCode(spmSupply.getCityCode());
                        Integer result = spmSupplyMapper.querySpmSupply(addSupply);
                        if (result > 0) {
                            throw new CommSystemProfileException(
                                    CommSystemProfileException.MSG_ERROR_ADDRESS_IS_REPEAT, new Object[] {});
                        }
                    }
                    List<SpmSupply> invs = spmSupply.getInvs();
                    if (invs == null || invs.isEmpty()) {
                        throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_INV_ORG_IS_NULL,
                                new Object[] {});
                    }
                    checkDefaultFlag(invs);
                    for (SpmSupply supply : invs) {
                        if (DTOStatus.DELETE.equals(supply.get__status())) {
                            SpmSupply delSupply = new SpmSupply();
                            delSupply.setSupplyType(supplyType);
                            delSupply.setCountryCode(countryCode);
                            delSupply.setStateCode(stateCode);
                            delSupply.setCityCode(cityCode);
                            delSupply.setInvOrgId(supply.getInvOrgId());
                            spmSupplyMapper.deleteBySelective(delSupply);
                        }
                        if (DTOStatus.ADD.equals(supply.get__status())) {
                            supply.setSupplyType(supplyType);
                            supply.setCountryCode(countryCode);
                            supply.setStateCode(stateCode);
                            supply.setCityCode(cityCode);
                            Integer result = spmSupplyMapper.querySpmSupply(supply);
                            if (result > 0) {
                                throw new CommSystemProfileException(
                                        CommSystemProfileException.MSG_ERROR_INV_ORG_IS_REPEAT, new Object[] {});
                            }
                            spmSupplyMapper.insertSelective(supply);
                        }
                    }
                }
            }
        }
        return supplies;
    }

    private void checkDefaultFlag(List<SpmSupply> invs) throws CommSystemProfileException {
        int flag = 0;
        for (SpmSupply spmSupply : invs) {
            if (!DTOStatus.DELETE.equals(spmSupply.get__status())) {
                String defaultFlag = spmSupply.getDefaultFlag();
                if (defaultFlag == null || "".equals(defaultFlag)) {
                    throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_INV_ORG_DEFALUT_IS_NULL,
                            new Object[] {});
                }
                if (SystemProfileConstants.YES.equals(defaultFlag)) {
                    flag++;
                }
            }
        }
        if (flag != 1) {
            throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_INV_ORG_HAS_ONE_DEFALUT,
                    new Object[] {});
        }
    }

    @Override
    public List<SpmSupply> query(IRequest createRequestContext, SpmSupply supply, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return spmSupplyMapper.queryBySpmSupply(supply);
    }

    @Override
    public List<SpmSupply> querySalesOrganization(IRequest request, SpmSupply spmSupply, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return spmSupplyMapper.queryBySupply(spmSupply);
    }

    @Override
    public List<SpmSupply> queryAddress(IRequest request, SpmSupply supply, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<SpmSupply> queryBySupply = spmSupplyMapper.queryByAddress(supply);
        return queryBySupply;
    }

    @Override
    public boolean deleteSpmSupply(IRequest requestContext, List<SpmSupply> supplies)
            throws CommSystemProfileException {
        for (SpmSupply spmSupplies : supplies) {
            if (spmSupplies.getSupplyId() != null) {
                spmSupplyMapper.deleteByPrimaryKey(spmSupplies.getSupplyId()); 
            }
        }
        return true;
    }

    @Override
    public void deleteSupplySales(IRequest requestContext, List<SpmSupply> spmSupplies) {
        for (SpmSupply spmSupply : spmSupplies) {
            SpmSupply del = new SpmSupply();
            del.setSupplyType(SystemProfileConstants.SUPPLY_TYPE_ORG);
            del.setSalesOrgId(spmSupply.getSalesOrgId());
            spmSupplyMapper.deleteBySelective(del);
        }
    }

    @Override
    public void deleteSupplyAddress(IRequest requestContext, List<SpmSupply> spmSupplies) {
        for (SpmSupply spmSupply : spmSupplies) {
            SpmSupply del = new SpmSupply();
            del.setSupplyType(SystemProfileConstants.SUPPLY_TYPE_SITE);
            del.setCountryCode(spmSupply.getCountryCode());
            del.setStateCode(spmSupply.getStateCode());
            del.setCityCode(spmSupply.getCityCode());
            spmSupplyMapper.deleteBySelective(del);
        }
    }
}
