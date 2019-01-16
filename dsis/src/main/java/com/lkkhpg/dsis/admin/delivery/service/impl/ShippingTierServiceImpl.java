/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.delivery.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.delivery.exception.DeliveryException;
import com.lkkhpg.dsis.admin.delivery.service.IShippingTierService;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.DeliveryConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTier;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTierAvail;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTierDtl;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTierSeg;
import com.lkkhpg.dsis.common.delivery.mapper.ShippingTierAvailMapper;
import com.lkkhpg.dsis.common.delivery.mapper.ShippingTierDtlMapper;
import com.lkkhpg.dsis.common.delivery.mapper.ShippingTierMapper;
import com.lkkhpg.dsis.common.delivery.mapper.ShippingTierSegMapper;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 物流资料实现层.
 * 
 * @author HuangJiaJing
 *
 */
@Service
@Transactional
public class ShippingTierServiceImpl implements IShippingTierService {

    @Autowired
    private ShippingTierMapper shippingTierMapper;

    @Autowired
    private ShippingTierDtlMapper shippingTierDtlMapper;

    @Autowired
    private ShippingTierSegMapper shippingTierSegMapper;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;
    @Autowired
    private IParamService paramService;
    @Autowired
    private ShippingTierAvailMapper shippingTierAvailMapper;

    /**
     * 根据销售组织以及地区获取承运商.
     * 
     * @param request
     *            上下文请求
     * @param location
     *            地址
     * @param salesOrgId
     *            销售组织
     * @return 满足条件的承运商
     */
    @Override
    public List<ShippingTier> queryShippingTiersByLocation(IRequest request, SpmLocation location, Long salesOrgId,
            String apptype) {
        return shippingTierMapper.selectByLocation(location, salesOrgId, apptype);
    }

    /**
     * 批量操作判断id是否存在进行增加或修改运费信息.
     * 
     * @param shippingTier
     *            承运商对象
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void processShippingTierDtls(IRequest request, ShippingTier shippingTier) throws DeliveryException {

        if (shippingTier.getShippingTierDtls() != null) {
            for (ShippingTierDtl shippingTierDtl : shippingTier.getShippingTierDtls()) {

                String currencies = paramService.getParamValues(request, DeliveryConstants.SHIPPING_TIER_ORG_CURRENCY,
                        SystemProfileConstants.ORG_TYPE_SALES, shippingTier.getSalesOrgId()).iterator().next();

                if (DTOStatus.ADD.equals(shippingTierDtl.get__status())) {
                    shippingTierDtl.setShippingTierId(shippingTier.getShippingTierId());
                }
                if (shippingTierDtl.getInvAmountFrom().compareTo(shippingTierDtl.getInvAmountTo()) >= 0) {
                    throw new DeliveryException(DeliveryException.SHIPPING_TIER_AMOUNTFROM_BIGGER_AMOUNTTO, null);
                }

                /*
                 * if (isExistShippingTierDtl(shippingTierDtl)) { throw new
                 * DeliveryException(DeliveryException.SHIPPING_TIER_DTL_EXIST,
                 * null); }
                 */
                shippingTierDtl.setCurrencyCode(currencies);
                if (DTOStatus.ADD.equals(shippingTierDtl.get__status())) {
                    shippingTierDtlMapper.insert(shippingTierDtl);
                } else if (DTOStatus.UPDATE.equals(shippingTierDtl.get__status())) {
                    shippingTierDtlMapper.updateByPrimaryKey(shippingTierDtl);
                } else if (DTOStatus.DELETE.equals(shippingTierDtl.get__status())) {
                    shippingTierDtlMapper.deleteByPrimaryKey(shippingTierDtl);
                }
            }
        }
    }

    private Boolean isExistShippingTierDtl(ShippingTierDtl shippingTierDtl) {
        int i = shippingTierDtlMapper.queryCount(shippingTierDtl);
        if (i <= 0) {
            return false;
        }

        return true;
    }

    /**
     * 批量操作判断id是否存在进行增加或修改地点信息.
     * 
     * @param shippingTier
     *            承运商对象
     */
    private void processShippingTierSegs(ShippingTier shippingTier) {
        if (shippingTier.getShippingTierSegs() != null) {
            for (ShippingTierSeg shippingTierSeg : shippingTier.getShippingTierSegs()) {
                if (DTOStatus.ADD.equals(shippingTierSeg.get__status())) {
                    shippingTierSeg.setShippingTierId(shippingTier.getShippingTierId());
                    shippingTierSegMapper.insert(shippingTierSeg);
                } else if (DTOStatus.UPDATE.equals(shippingTierSeg.get__status())) {
                    shippingTierSegMapper.updateByPrimaryKey(shippingTierSeg);
                }
            }
        }
    }

    @Override
    public List<ShippingTier> selectShippingTiers(IRequest request, ShippingTier shippingTier, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<ShippingTier> shippingTiers = shippingTierMapper.selectShippingTiers(shippingTier);
        return shippingTiers;
    }

    @Override
    public List<ShippingTierDtl> selectShippingTierDtls(IRequest request, ShippingTierDtl shippingTierDtls) {
        return shippingTierDtlMapper.selectShippingTierDtls(shippingTierDtls);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ShippingTier createShippingTier(IRequest request, ShippingTier shippingTier) throws DeliveryException {
        SpmSalesOrganization spmSalesOrganization = spmSalesOrganizationMapper
                .selectByPrimaryKey(shippingTier.getSalesOrgId());
        shippingTier.setCompanyId(spmSalesOrganization.getMarketId());
        shippingTierMapper.insert(shippingTier);
        if (shippingTier.getShippingTierDtls() != null) {
            processShippingTierDtls(request, shippingTier);
        }
        if (shippingTier.getShippingTierSegs() != null) {
            processShippingTierSegs(shippingTier);
        }
        ShippingTierAvail shippingTierAvail1 = new ShippingTierAvail();
        ShippingTierAvail shippingTierAvail2 = new ShippingTierAvail();
        ShippingTierAvail shippingTierAvail3 = new ShippingTierAvail();
        shippingTierAvail1.setShippingTierId(shippingTier.getShippingTierId());
        shippingTierAvail1.setFunctionArea("DSIS");
        shippingTierAvail1.setEnabledFlag(shippingTier.getDsis());
        shippingTierAvail1.setCreatedBy(shippingTier.getCreatedBy());
        shippingTierAvail1.setLastUpdatedBy(shippingTier.getLastUpdatedBy());
        shippingTierAvail2.setShippingTierId(shippingTier.getShippingTierId());
        shippingTierAvail2.setFunctionArea("APP");
        shippingTierAvail2.setEnabledFlag(shippingTier.getAgencyApp());
        shippingTierAvail2.setCreatedBy(shippingTier.getCreatedBy());
        shippingTierAvail2.setLastUpdatedBy(shippingTier.getLastUpdatedBy());
        shippingTierAvail3.setShippingTierId(shippingTier.getShippingTierId());
        shippingTierAvail3.setFunctionArea("WEB");
        shippingTierAvail3.setEnabledFlag(shippingTier.getAgencyWeb());
        shippingTierAvail3.setCreatedBy(shippingTier.getCreatedBy());
        shippingTierAvail3.setLastUpdatedBy(shippingTier.getLastUpdatedBy());
        shippingTierAvailMapper.insert(shippingTierAvail1);
        shippingTierAvailMapper.insert(shippingTierAvail2);
        shippingTierAvailMapper.insert(shippingTierAvail3);
        return shippingTier;
    }

    @Override
    public void batchDelete(IRequest request, List<ShippingTier> shippingTiers) {
        for (ShippingTier shippingTier : shippingTiers) {
            ShippingTierDtl shippingTierDtl = new ShippingTierDtl();
            shippingTierDtl.setShippingTierId(shippingTier.getShippingTierId());
            shippingTierDtlMapper.deleteByPrimaryKey(shippingTierDtl);
        }
    }

    @Override
    public void batchDeleteShippingTierDtls(IRequest request, List<ShippingTierDtl> shippingTierDtls) {
        for (ShippingTierDtl shippingTierDtl : shippingTierDtls) {
            shippingTierDtlMapper.deleteByPrimaryKey(shippingTierDtl);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ShippingTier updateShippingTier(IRequest request, ShippingTier shippingTier) throws DeliveryException {
        SpmSalesOrganization spmSalesOrganization = spmSalesOrganizationMapper
                .selectByPrimaryKey(shippingTier.getSalesOrgId());
        shippingTier.setCompanyId(spmSalesOrganization.getMarketId());
        shippingTierMapper.updateByPrimaryKey(shippingTier);
        if (shippingTier.getShippingTierDtls() != null) {
            processShippingTierDtls(request, shippingTier);
        }
        if (shippingTier.getShippingTierSegs() != null) {
            processShippingTierSegs(shippingTier);
        }
        List<ShippingTierAvail> shippingTierAvails = shippingTierAvailMapper
                .selectShippingTierAvails(shippingTier.getShippingTierId());
        if (!shippingTierAvails.isEmpty()) {
            if (shippingTier.getDsis().equals("Y")) {
                ShippingTierAvail shippingTierAvail = new ShippingTierAvail();
                shippingTierAvail.setShippingTierId(shippingTier.getShippingTierId());
                shippingTierAvail.setFunctionArea("DSIS");
                shippingTierAvail.setEnabledFlag(shippingTier.getDsis());
                shippingTierAvail.setLastUpdatedBy(shippingTier.getLastUpdatedBy());
                shippingTierAvailMapper.updateByShippingTierId(shippingTierAvail);
            } else {
                ShippingTierAvail shippingTierAvail = new ShippingTierAvail();
                shippingTierAvail.setShippingTierId(shippingTier.getShippingTierId());
                shippingTierAvail.setFunctionArea("DSIS");
                shippingTierAvail.setEnabledFlag(shippingTier.getDsis());
                shippingTierAvail.setLastUpdatedBy(shippingTier.getLastUpdatedBy());
                shippingTierAvailMapper.updateByShippingTierId(shippingTierAvail);
            }
            if (shippingTier.getAgencyApp().equals("Y")) {
                ShippingTierAvail shippingTierAvail = new ShippingTierAvail();
                shippingTierAvail.setShippingTierId(shippingTier.getShippingTierId());
                shippingTierAvail.setFunctionArea("APP");
                shippingTierAvail.setEnabledFlag(shippingTier.getAgencyApp());
                shippingTierAvail.setLastUpdatedBy(shippingTier.getLastUpdatedBy());
                shippingTierAvailMapper.updateByShippingTierId(shippingTierAvail);
            } else {
                ShippingTierAvail shippingTierAvail = new ShippingTierAvail();
                shippingTierAvail.setShippingTierId(shippingTier.getShippingTierId());
                shippingTierAvail.setFunctionArea("APP");
                shippingTierAvail.setEnabledFlag(shippingTier.getAgencyApp());
                shippingTierAvail.setLastUpdatedBy(shippingTier.getLastUpdatedBy());
                shippingTierAvailMapper.updateByShippingTierId(shippingTierAvail);
            }
            if (shippingTier.getAgencyWeb().equals("Y")) {
                ShippingTierAvail shippingTierAvail = new ShippingTierAvail();
                shippingTierAvail.setShippingTierId(shippingTier.getShippingTierId());
                shippingTierAvail.setFunctionArea("WEB");
                shippingTierAvail.setEnabledFlag(shippingTier.getAgencyWeb());
                shippingTierAvail.setLastUpdatedBy(shippingTier.getLastUpdatedBy());
                shippingTierAvailMapper.updateByShippingTierId(shippingTierAvail);
            } else {
                ShippingTierAvail shippingTierAvail = new ShippingTierAvail();
                shippingTierAvail.setShippingTierId(shippingTier.getShippingTierId());
                shippingTierAvail.setFunctionArea("WEB");
                shippingTierAvail.setEnabledFlag(shippingTier.getAgencyWeb());
                shippingTierAvail.setLastUpdatedBy(shippingTier.getLastUpdatedBy());
                shippingTierAvailMapper.updateByShippingTierId(shippingTierAvail);
            }
        } else {
            ShippingTierAvail shippingTierAvail1 = new ShippingTierAvail();
            ShippingTierAvail shippingTierAvail2 = new ShippingTierAvail();
            ShippingTierAvail shippingTierAvail3 = new ShippingTierAvail();
            shippingTierAvail1.setShippingTierId(shippingTier.getShippingTierId());
            shippingTierAvail1.setFunctionArea("DSIS");
            shippingTierAvail1.setEnabledFlag(shippingTier.getDsis());
            shippingTierAvail1.setCreatedBy(shippingTier.getCreatedBy());
            shippingTierAvail1.setLastUpdatedBy(shippingTier.getLastUpdatedBy());
            shippingTierAvail2.setShippingTierId(shippingTier.getShippingTierId());
            shippingTierAvail2.setFunctionArea("APP");
            shippingTierAvail2.setEnabledFlag(shippingTier.getAgencyApp());
            shippingTierAvail2.setCreatedBy(shippingTier.getCreatedBy());
            shippingTierAvail2.setLastUpdatedBy(shippingTier.getLastUpdatedBy());
            shippingTierAvail3.setShippingTierId(shippingTier.getShippingTierId());
            shippingTierAvail3.setFunctionArea("WEB");
            shippingTierAvail3.setEnabledFlag(shippingTier.getAgencyWeb());
            shippingTierAvail3.setCreatedBy(shippingTier.getCreatedBy());
            shippingTierAvail3.setLastUpdatedBy(shippingTier.getLastUpdatedBy());
            shippingTierAvailMapper.insert(shippingTierAvail1);
            shippingTierAvailMapper.insert(shippingTierAvail2);
            shippingTierAvailMapper.insert(shippingTierAvail3);
        }
        return shippingTier;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<ShippingTier> batchUpdate(IRequest request, List<ShippingTier> shippingTiers) throws DeliveryException {

        if (!self().validateShippingTier(request, shippingTiers)) {
            throw new DeliveryException(DeliveryException.SHIPPING_TIER_EXIST, null);
        }

        if (!self().validateShippingTierSeg(request, shippingTiers)) {
            throw new DeliveryException(DeliveryException.SHIPPING_TIER_SEG_EXIST, null);
        }

        for (ShippingTier shippingTier : shippingTiers) {
            if (shippingTier.getShippingTierId() == null) {
                self().createShippingTier(request, shippingTier);
            } else if (shippingTier.getShippingTierId() != null) {
                self().updateShippingTier(request, shippingTier);
            }
        }
        return shippingTiers;
    }

    /**
     * 校验物流信息头.
     * 
     * @param request
     *            请求上下文
     * @param shippingTiers
     *            物流信息
     * @return true 校验成功,false 校验失败
     */
    @Override
    public Boolean validateShippingTier(IRequest request, List<ShippingTier> shippingTiers) {
        // 循环列表
        for (ShippingTier shippingTier : shippingTiers) {
            // 如果存在数据，返回false 校验失败
            if (self().isExistShippingTier(request, shippingTier)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验物流收费信息.
     * 
     * @param request
     *            请求上下文
     * @param shippingTiers
     *            物流信息
     * @return true 校验成功,false 校验失败
     */
    public Boolean validateShippingTierDtl(IRequest request, List<ShippingTier> shippingTiers) {

        return true;

    }

    /**
     * 校验物流地址信息.
     * 
     * @param request
     *            请求上下文
     * @param shippingTiers
     *            物流信息
     * @return true 校验成功,false 校验失败
     */
    @Override
    public Boolean validateShippingTierSeg(IRequest request, List<ShippingTier> shippingTiers) {

        for (ShippingTier shippingTier : shippingTiers) {
            // 如果子行不为空，则校验
            if (shippingTier.getShippingTierSegs() != null) {

                // 创建新集合
                HashSet<String> shippingTierSet = new HashSet<String>();
                for (ShippingTierSeg shippingTierSeg : shippingTier.getShippingTierSegs()) {

                    // 校验集合数据
                    Object[] ids = { shippingTierSeg.getCountryCode(), shippingTierSeg.getStateCode(),
                            shippingTierSeg.getCityCode() };
                    String key = StringUtils.join(ids, ":");
                    if (shippingTierSet.contains(key)) {
                        return false;
                    } else {
                        shippingTierSet.add(key);
                    }

                }

            }
        }

        for (ShippingTier shippingTier : shippingTiers) {
            // 如果ID为空，则表示更新数据，需要校验数据库
            if (shippingTier.getShippingTierId() != null) {
                if (shippingTier.getShippingTierSegs() != null) {
                    for (ShippingTierSeg shippingTierSeg : shippingTier.getShippingTierSegs()) {
                        // 如果插入数据，需要校验数据库是否已经存在
                        if (DTOStatus.ADD.equals(shippingTierSeg.get__status())) {
                            shippingTierSeg.setShippingTierId(shippingTier.getShippingTierId());
                            if (self().isExistShippingTierSeg(request, shippingTierSeg)) {
                                return false;
                            }
                        }
                    }

                }
            }

        }
        return true;
    }

    @Override
    public List<ShippingTierSeg> selectShippingTierDtlSegs(IRequest request, ShippingTierSeg shippingTierSegs) {
        return shippingTierSegMapper.selectShippingTierDtlSegs(shippingTierSegs);
    }

    @Override
    public void batchDeleteShippingTierDtlSegs(IRequest request, List<ShippingTierSeg> shippingTierDtlSegs) {
        for (ShippingTierSeg shippingTierDtlSeg : shippingTierDtlSegs) {
            shippingTierSegMapper.deleteByPrimaryKey(shippingTierDtlSeg);
        }
    }

    @Override
    public ShippingTierDtl insertShippingTierDtl(ShippingTierDtl shippingTierDtl) {
        shippingTierDtlMapper.insert(shippingTierDtl);
        return shippingTierDtl;
    }

    @Override
    public ShippingTierSeg insertShippingTierSeg(ShippingTierSeg shippingTierDtlSeg) {
        shippingTierSegMapper.insert(shippingTierDtlSeg);
        return shippingTierDtlSeg;
    }

    @Override
    public Boolean isExistShippingTierSeg(IRequest request, ShippingTierSeg shippingTierSeg) {
        if (shippingTierSeg.getShippingTierId() == null) {
            return false;
        }
        int i = shippingTierSegMapper.queryCount(shippingTierSeg);
        if (i <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean isExistShippingTier(IRequest request, ShippingTier shippingTier) {
        int i = shippingTierMapper.queryTierCount(shippingTier);
        if (i <= 0) {
            return false;
        }
        return true;
    }

}
