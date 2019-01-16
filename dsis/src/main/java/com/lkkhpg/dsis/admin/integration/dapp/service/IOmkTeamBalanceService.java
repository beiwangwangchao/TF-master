/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamBalanceRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamBalanceResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 团队余额查询接口.
 * 
 * @author zhenyang.he
 *
 */
public interface IOmkTeamBalanceService {

    /**
     * 团队余额信息查询.
     * 
     * @param omkTeamBalanceRequest
     *            请求数据集
     * @return 团队余额信息集合
     * 
     * @throws DAppException
     *             异常接口
     */
    OmkTeamBalanceResponse getTeamBalance(OmkTeamBalanceRequest omkTeamBalanceRequest) throws DAppException;
}
