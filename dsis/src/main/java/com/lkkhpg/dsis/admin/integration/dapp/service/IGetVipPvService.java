/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPvRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPvResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 查询VIP会员的pv总值Service.
 * 
 * @author shenqb
 *
 */
public interface IGetVipPvService extends ProxySelf<IGetVipPvService> {

    /**
     * 查询VIP会员的pv总值.
     * 
     * @param getVipPvRequest
     *            入参
     * @return pv
     * @throws DAppException
     *             dapp异常
     */
    GetVipPvResponse getVipPv(GetVipPvRequest getVipPvRequest) throws DAppException;

}
