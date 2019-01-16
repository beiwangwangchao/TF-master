/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.product.product.service.ILotService;
import com.lkkhpg.dsis.common.inventory.dto.StockTrxDetail;
import com.lkkhpg.dsis.common.product.dto.Lot;
import com.lkkhpg.dsis.common.product.mapper.LotMapper;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 批次接口实现类.
 * 
 * @author mclin
 */
@Transactional
@Service
public class LotServiceImpl implements ILotService {

    @Autowired
    private LotMapper invLotMapper;

    @Override
    public Lot getLot(IRequest requestContext, Long organizationId, StockTrxDetail stockTrxDetail) {
        return invLotMapper.getLot(organizationId, stockTrxDetail);
    }

    @Override
    public List<Lot> queryLotsIn(IRequest requestContext, Long orgId) {
        return invLotMapper.queryLotsIn(orgId);
    }

    @Override
    public List<Lot> queryLotsOut(IRequest requestContext, Long orgId) {
        return invLotMapper.queryLotsOut(orgId);
    }

    @Override
    public Lot queryLotWithExpiryDate(IRequest requestContext, Lot lot) {
        List<Lot> lots = invLotMapper.queryLotsByItemAndOrg(lot);
        if (lots.isEmpty()) {
            return new Lot();
        } else {
            return lots.get(0);
        }
    }

}
