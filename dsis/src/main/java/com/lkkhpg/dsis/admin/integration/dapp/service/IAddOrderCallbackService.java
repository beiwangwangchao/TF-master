/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 创建订单callback service.
 * 
 * @author shenqb
 *
 */
public interface IAddOrderCallbackService {

    /**
     * 创建订单callback,更新所有未同步记录.
     * 
     * @return 成功标识
     * @throws DAppException
     *             dapp异常
     */
    boolean addOrderCallback() throws DAppException;

    /**
     * 创建订单callback,更新单条未同步记录.
     * 
     * @param iRequest
     *            请求上下文
     * @param order
     *            订单DTO
     * @throws IntegrationException
     *             dapp异常
     */
    void updateSyncFlag(IRequest iRequest, SalesOrder order) throws IntegrationException;
}
