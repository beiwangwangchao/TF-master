/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 库存检查程序.
 * 
 * @author chenjingxiong
 */
public interface IInvCheckService extends ProxySelf<IInvCheckService> {

    /**
     * 检查库存可用量是否满足.
     * 
     * @param request
     *            统一上下文
     * @param itemId
     *            商品ID
     * @param invOrgId
     *            库存组织ID
     * @param subInventoryId
     *            子库存ID
     * @param locationId
     *            地点ID
     * @param lotNumber
     *            批次号
     * @param quantity
     *            数量
     * @return 检查结果
     */
    boolean check(IRequest request, Long itemId, Long invOrgId, Long subInventoryId, Long locationId, String lotNumber,
            BigDecimal quantity);

    /**
     * 检查库存量是否足够
     * 
     * @param request
     *            统一上下文
     * @param itemId
     *            商品ID
     * @param invOrgId
     *            库存组织ID
     * @param subInventoryId
     *            子库存ID
     * @param locationId
     *            地点ID
     * @param lotNumber
     *            批次号
     * @param quantity
     *            数量
     * @return 检查结果
     */
    boolean checkInv(IRequest request, Long itemId, Long invOrgId, Long subInventoryId, Long locationId, String lotNumber, BigDecimal quantity);
}
