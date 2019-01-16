/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.service;

import java.util.List;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 库存扣减程序.
 * 
 * @author chenjingxiong
 */
public interface IInvDeductionService {

    /**
     * 扣减库存操作.
     * 
     * @param request
     *            统一上下文信息.
     * @param transaction
     *            库存事务.
     * @return 所扣减或增加的库存现有量ID集合.
     * @throws InventoryException
     *             库存统一异常
     */
    List<Long> process(IRequest request, InvTransaction transaction) throws InventoryException;

}
