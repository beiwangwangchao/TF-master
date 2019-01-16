/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkAcctBalanceRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkAcctBalanceResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 会员账号余额接口类.
 * 
 * @author zhenyang.he
 *
 */
public interface IOmkAcctBalanceService {

    /**
     * 通过会员卡号，语言类型获取会员账号余额.
     * 
     * @param request
     *            請求上下文
     * @param omkAcctBalanceRequest
     *            获取会员账号余额信息DTO
     * @return 会员账号余额
     * @throws DAppException
     *             接口异常类
     */
    OmkAcctBalanceResponse getAcctBalance(OmkAcctBalanceRequest omkAcctBalanceRequest) throws DAppException;
}
