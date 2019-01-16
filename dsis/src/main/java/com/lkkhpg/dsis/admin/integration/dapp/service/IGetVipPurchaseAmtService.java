/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPurchaseAmountRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPurchaseAmountResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 查询VIP会员的pv总值Service.
 * 
 * @author shenqb
 *
 */
public interface IGetVipPurchaseAmtService extends ProxySelf<IGetVipPurchaseAmtService> {

    /**
     * 查询VIP会员的消费总值.
     * 
     * @param getVipPvRequest
     *            入参
     * @return pv
     * @throws DAppException
     *             dapp异常
     */
    GetVipPurchaseAmountResponse getVipPurchaseAmt(GetVipPurchaseAmountRequest getVipPurchaseAmountRequest) throws DAppException;

}
