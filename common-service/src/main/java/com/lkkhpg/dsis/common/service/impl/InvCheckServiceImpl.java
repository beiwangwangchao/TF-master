/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
import com.lkkhpg.dsis.common.product.dto.InvItemPropertyV;
import com.lkkhpg.dsis.common.product.mapper.InvItemPropertyMapper;
import com.lkkhpg.dsis.common.service.IInvCheckService;
import com.lkkhpg.dsis.common.service.IInvOnhandQuantityService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 库存检查实现类.
 * 
 * @author chenjingxiong
 */
@Service
public class InvCheckServiceImpl implements IInvCheckService {

    private Logger logger = LoggerFactory.getLogger(InvCheckServiceImpl.class);

    @Autowired
    private IInvOnhandQuantityService invOnhandQuantityService;

    @Autowired
    private InvItemPropertyMapper invItemPropertyMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean check(IRequest request, Long itemId, Long invOrgId, Long subInventoryId, Long locationId,
            String lotNumber, BigDecimal quantity) {

        if (logger.isDebugEnabled()) {
            logger.debug("ItemId:{}", itemId);
            logger.debug("invOrgId:{}", invOrgId);
            logger.debug("subInventoryId:{}", subInventoryId);
            logger.debug("locationId:{}", locationId);
            logger.debug("lotNumber:{}", lotNumber);
            logger.debug("quantity:{}", quantity);
        }

        InvOnhandQuantity criteria = new InvOnhandQuantity();
        criteria.setItemId(itemId);
        criteria.setOrganizationId(invOrgId);
        criteria.setSubinventoryId(subInventoryId);
        criteria.setLocationId(locationId);
        criteria.setLotNumber(lotNumber);

        // 查出当前实际可用库存量
        //BigDecimal currentQuantity = invOnhandQuantityService.getAvailableQuantity(request, criteria);

        //modified by furong.tang
        //查询当前实际库存数量
        BigDecimal currentQuantity = invOnhandQuantityService.getQuantity(request, criteria);

        if (currentQuantity == null) {
            currentQuantity = BigDecimal.ZERO;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("the current quantity is:{}", currentQuantity);
        }

        return currentQuantity.compareTo(quantity) >= 0;
    }

    @Override
    public boolean checkInv(IRequest request, Long itemId, Long invOrgId, Long subInventoryId, Long locationId, String lotNumber, BigDecimal quantity) {
        if (logger.isDebugEnabled()) {
            logger.debug("ItemId:{}", itemId);
            logger.debug("invOrgId:{}", invOrgId);
            logger.debug("subInventoryId:{}", subInventoryId);
            logger.debug("locationId:{}", locationId);
            logger.debug("lotNumber:{}", lotNumber);
            logger.debug("quantity:{}", quantity);
        }
        // 商品是否计算库存
        InvItemPropertyV invItemPropertyV = invItemPropertyMapper.queryItemPropertyVByItemIdAndOrgId(itemId, invOrgId);
        if (invItemPropertyV != null && BaseConstants.NO.equals(invItemPropertyV.getCountStockFlag())) {
            // 不计算库存
            return true;
        }

        InvOnhandQuantity criteria = new InvOnhandQuantity();
        criteria.setItemId(itemId);
        criteria.setOrganizationId(invOrgId);
        criteria.setSubinventoryId(subInventoryId);
        criteria.setLocationId(locationId);
        criteria.setLotNumber(lotNumber);

        // 查出当前实际可用库存量
        BigDecimal currentQuantity = invOnhandQuantityService.queryOnhandQuantity(request, criteria);

        if (currentQuantity == null) {
            currentQuantity = BigDecimal.ZERO;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("the current quantity is:{}", currentQuantity);
        }

        return currentQuantity.compareTo(quantity) >= 0;

    }

}
