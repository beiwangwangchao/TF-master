/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.dto.SpmSupply;
import com.lkkhpg.dsis.common.config.mapper.SpmSupplyMapper;
import com.lkkhpg.dsis.common.constant.DeliveryConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.order.mapper.SalesSitesMapper;
import com.lkkhpg.dsis.common.service.IDeliveryInvOrgMatchService;
import com.lkkhpg.dsis.common.service.IInvCheckService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 挑库规则匹配Service.
 * 
 * @author chenjingxiong
 */
@Service
@Transactional
public class DeliveryInvOrgMatchServiceImpl implements IDeliveryInvOrgMatchService {

    private static final String NULL_STRING = "null";

    @Autowired
    private SpmSupplyMapper spmSupplyMapper;

    @Autowired
    private SalesSitesMapper salesSitesMapper;

    @Autowired
    private IInvCheckService invCheckService;
    // @Override
    // @Transactional(propagation = Propagation.SUPPORTS)
    // public Long matchInvOrg(IRequest request, Long salesOrgId, String
    // deliveryType, Long sitesId) {
    // if (DeliveryConstants.DELIVERY_TYPE_PICK_UP.equals(deliveryType)) {
    // return matchInvOrg(request, salesOrgId, deliveryType, new SalesSites());
    // } else {
    // SalesSites sites = salesSitesMapper.selectByPrimaryKey(sitesId);
    // if (sites == null) {
    // return null;
    // } else {
    // return matchInvOrg(request, salesOrgId, deliveryType, sites);
    // }
    // }
    // }

    @Override
    public Long matchInvOrg(IRequest request, Long salesOrgId, String deliveryType, SalesSites deliverySite) {
        List<SpmSupply> spmSupplies = null;
        if (DeliveryConstants.DELIVERY_TYPE_PICK_UP.equals(deliveryType)) {
            // 自提
            // 根据当前订单销售组织和供货组织的默认关联关系取值（类型=组织）
            SpmSupply criteria = new SpmSupply();
            criteria.setSupplyType(SystemProfileConstants.SUPPLY_TYPE_ORG);
            criteria.setSalesOrgId(salesOrgId);

            spmSupplies = spmSupplyMapper.selectBySpmSupply(criteria);

        } else {
            // 物流配送
            // 根据收货地址中“country”、“state”和“city”，
            // 匹配销售组织与供货组织关联关系表中的“country”、“state”和“city”（类型=地点）
            // 按照最明细层级依次进行匹配，即country+state+city>country+state>country，
            // 若无法匹配到供货组织，则取类型=组织，当前销售组织与供货组织的默认关联关系。

            // 没有地址
            if (deliverySite == null) {
                return null;
            }

            SpmSupply criteria = new SpmSupply();
            criteria.setSupplyType(SystemProfileConstants.SUPPLY_TYPE_SITE);
            criteria.setCityCode(deliverySite.getCityCode());

            spmSupplies = spmSupplyMapper.selectBySpmSupply(criteria);

            if (spmSupplies == null || spmSupplies.isEmpty()) {
                criteria.setSupplyType(SystemProfileConstants.SUPPLY_TYPE_SITE);
                criteria.setCityCode(NULL_STRING);
                criteria.setStateCode(deliverySite.getStateCode());
                spmSupplies = spmSupplyMapper.selectBySpmSupply(criteria);

                if (spmSupplies == null || spmSupplies.isEmpty()) {
                    criteria.setSupplyType(SystemProfileConstants.SUPPLY_TYPE_SITE);
                    criteria.setCityCode(NULL_STRING);
                    criteria.setStateCode(NULL_STRING);
                    criteria.setCountryCode(deliverySite.getCountryCode());
                    spmSupplies = spmSupplyMapper.selectBySpmSupply(criteria);

                    if (spmSupplies == null || spmSupplies.isEmpty()) {
                        // 通过地点无法获取值，只好使用销售区域上维护的供货关系
                        criteria.setSupplyType(SystemProfileConstants.SUPPLY_TYPE_ORG);
                        criteria.setSalesOrgId(salesOrgId);
                        criteria.setCityCode(NULL_STRING);
                        criteria.setStateCode(NULL_STRING);
                        criteria.setCountryCode(NULL_STRING);

                        spmSupplies = spmSupplyMapper.selectBySpmSupply(criteria);

                    }
                }
            }
        }
        // 无法找到
        if (spmSupplies == null || spmSupplies.isEmpty()) {
            return null;
        }
        // 取默认值
        for (SpmSupply spmSupply : spmSupplies) {
            if (DeliveryConstants.YES.equals(spmSupply.getDefaultFlag())) {
                return spmSupply.getInvOrgId();
            }
        }

        return spmSupplies.get(0).getInvOrgId();
    }

    @Override
    public Long matchInvOrg(IRequest request, Long salesOrgId, SalesSites deliverySite, Long itemId,
            BigDecimal quantity) {
        // TODO Auto-generated method stub

        Long invOrgId = self().matchInvOrg(request, salesOrgId, DeliveryConstants.DELIVERY_TYPE_SHIPPMENT,
                deliverySite);

        if (invOrgId != null) {
            // 检查地点匹配出的库存组中库存时候够用
            boolean flag = invCheckService.check(request, itemId, invOrgId, null, null, null, quantity);
            if (!flag) {
                invOrgId = self().matchInvOrg(request, salesOrgId, DeliveryConstants.DELIVERY_TYPE_PICK_UP,
                        deliverySite);
            }
        }

        return invOrgId;
    }

}
