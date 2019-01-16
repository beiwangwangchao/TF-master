/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickHead;
import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickLine;
import com.lkkhpg.dsis.common.delivery.mapper.DeliveryPickHeadMapper;
import com.lkkhpg.dsis.common.delivery.mapper.DeliveryPickLineMapper;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.service.ICommDeliveryPickService;
import com.lkkhpg.dsis.common.service.ICommDeliveryService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 挑库发放服务实现.
 * 
 * @author Zhaoqi
 *
 */
@Service
@Transactional
public class CommDeliveryPickServiceImpl implements ICommDeliveryPickService {

    private Logger logger = LoggerFactory.getLogger(CommDeliveryPickServiceImpl.class);

    @Autowired
    private DeliveryPickHeadMapper deliveryPickHeadMapper;

    @Autowired
    private DeliveryPickLineMapper deliveryPickLineMapper;

    @Autowired
    private ICommDeliveryService commDeliveryService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> saveDeliveryPick(IRequest request, @StdWho DeliveryPickHead deliveryPickHead)
            throws CommDeliveryException {
        DeliveryPickLine deliveryPick = new DeliveryPickLine();
        int i = deliveryPickHeadMapper.insertPick(deliveryPickHead);
        if (i == 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("insert  fail,{}", i);
            }
        }
        List<DeliveryPickLine> list = deliveryPickHead.getLines();
        for (DeliveryPickLine dp : list) {
            deliveryPick.setPickReleaseId(deliveryPickHead.getPickReleaseId());
            deliveryPick.setOrderLineId(dp.getOrderLineId());
            deliveryPick.setInvOrgId(dp.getInvOrgId());
            deliveryPick.setPickQuantity(dp.getPickQuantity());
            deliveryPick.setCreatedBy(deliveryPickHead.getCreatedBy());
            deliveryPick.setLastUpdatedBy(deliveryPickHead.getLastUpdatedBy());
            deliveryPick.setLastUpdateLogin(deliveryPickHead.getLastUpdateLogin());
            deliveryPick.setPackageItemId(dp.getPackageItemId());
            if (dp.getPickQuantity().compareTo(dp.getSurplusQty()) == 1) {
                // 挑库数量>未挑库数量
                throw new CommDeliveryException(CommDeliveryException.MORE_THAN_UNPICKED, null);
            }
            if (dp.getPickQuantity().compareTo(dp.getInventory()) == 1) {
                // 挑库数量>库存量
                throw new CommDeliveryException(CommDeliveryException.OUT_OF_STOCK, null);
            }
            deliveryPickLineMapper.insertPickLine(deliveryPick); // 批量存---存多条记录
            
            dp.setPickReleaseLineId(deliveryPick.getPickReleaseLineId());
            dp.setPickReleaseId(deliveryPickHead.getPickReleaseId());
        }
        
        // 生成发运单
        List<Long> deliveryIds = commDeliveryService.createDelivery(request, deliveryPickHead);
        return deliveryIds;
    }

}
