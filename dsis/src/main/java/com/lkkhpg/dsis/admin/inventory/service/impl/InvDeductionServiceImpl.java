/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.service.IInvDeductionService;
import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.mapper.InvOnhandQuantityMapper;
import com.lkkhpg.dsis.common.service.IInvCheckService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 库存扣减程序.
 * 
 * @author chenjingxiong
 */
@Service
@Transactional
public class InvDeductionServiceImpl implements IInvDeductionService {

    private Logger logger = LoggerFactory.getLogger(InvDeductionServiceImpl.class);

    @Autowired
    private InvOnhandQuantityMapper invOnhandQuantityMapper;
    
    @Autowired
    private IInvCheckService invCheckService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> process(IRequest request, InvTransaction transaction) throws InventoryException {
        List<Long> effectiveOnhandQuantityIds = new ArrayList<>();
        InvOnhandQuantity criteria = createCriteria(transaction);
        // 锁表记录
        // 对于同样的参数，可找到多条与之对应的库存现有量记录
        // 按照初始事务处理编号进行排序
        List<InvOnhandQuantity> onhandQuantities = invOnhandQuantityMapper.selectForLock(criteria);

        // 找不到记录，无此库存信息
        if (onhandQuantities == null || onhandQuantities.isEmpty()) {
            throw new InventoryException(InventoryException.MSG_ERROR_NOT_ENOUGH_STOCK, new Object[] {});
        }

        BigDecimal trxQuantity = transaction.getTrxQty();

        if (logger.isDebugEnabled()) {
            logger.debug("trxQuantity is : {}", trxQuantity);
        }

        boolean flag = invCheckService.checkInv(request, criteria.getItemId(), criteria.getOrganizationId(),
                criteria.getSubinventoryId(), criteria.getLocationId(), criteria.getLotNumber(), trxQuantity.negate());
        // 库存量不足
        if (!flag) {
            throw new InventoryException(InventoryException.MSG_ERROR_NOT_ENOUGH_STOCK, new Object[] {});
        }

        // 按照初始事务处理ID排序后，一个一个扣减，直到扣完为止
        for (InvOnhandQuantity onhandQuantity : onhandQuantities) {
            effectiveOnhandQuantityIds.add(onhandQuantity.getOnhandId());
            if (logger.isDebugEnabled()) {
                logger.debug("processing onhandQuantity with id:{}", onhandQuantity.getOnhandId());
            }

            BigDecimal currentQuantity = onhandQuantity.getQuantity();
            BigDecimal newQuantity = currentQuantity.add(trxQuantity);

            if (logger.isDebugEnabled()) {
                logger.debug("newQuantity is :{}", newQuantity);
            }

            if (newQuantity.compareTo(BigDecimal.ZERO) >= 0) {
                onhandQuantity.setQuantity(newQuantity);
                // 更新库存量
                invOnhandQuantityMapper.updateByPrimaryKey(onhandQuantity);
                // 足够扣减，跳出循环
                if (logger.isDebugEnabled()) {
                    logger.debug("The stock is enough.");
                }
                // 库存量 = 0 删除该条记录
                if (BigDecimal.ZERO.equals(newQuantity)) {
                    invOnhandQuantityMapper.deleteByPrimaryKey(onhandQuantity.getOnhandId());
                }
                break;
            } else {
                onhandQuantity.setQuantity(BigDecimal.ZERO);
                // 更新库存量
                invOnhandQuantityMapper.updateByPrimaryKey(onhandQuantity);
                // 不足扣减,继续循环
                trxQuantity = newQuantity;
                if (logger.isDebugEnabled()) {
                    logger.debug("The stock is not enough, continue to count.");
                }
                // 库存量 = 0 删除该条记录
                invOnhandQuantityMapper.deleteByPrimaryKey(onhandQuantity.getOnhandId());
            }
        }
        return effectiveOnhandQuantityIds;
    }

    private InvOnhandQuantity createCriteria(InvTransaction transaction) {
        InvOnhandQuantity criteria = new InvOnhandQuantity();
        criteria.setOrganizationId(transaction.getOrganizationId());
        criteria.setItemId(transaction.getItemId());
        criteria.setSubinventoryId(transaction.getSubinventoryId());
        criteria.setLocationId(transaction.getLocatorId());
        criteria.setLotNumber(transaction.getLotNumber());
        return criteria;
    }
}
