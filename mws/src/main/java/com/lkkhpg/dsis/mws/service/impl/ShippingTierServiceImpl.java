/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.delivery.dto.ShippingTier;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTierSeg;
import com.lkkhpg.dsis.mws.mapper.MwsShippingTierMapper;
import com.lkkhpg.dsis.mws.service.IShippingTierService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员地址service实现.
 * 
 * @author guanghui.liu
 */
@Service
@Transactional
public class ShippingTierServiceImpl implements IShippingTierService {

    @Autowired
    private MwsShippingTierMapper mwsShippingTierMapper;

    @Override
    public List<ShippingTier> queryShippingTier(IRequest iRequest, ShippingTierSeg shippingTierSeg, Long salesOrgId,
            String currencyCode,String apptype) {
        return mwsShippingTierMapper.selectShippingTierByLocation(shippingTierSeg, salesOrgId, currencyCode,apptype);
    }

}