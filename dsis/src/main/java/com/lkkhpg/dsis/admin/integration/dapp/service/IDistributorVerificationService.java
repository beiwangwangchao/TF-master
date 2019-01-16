/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.util.Map;

import com.lkkhpg.dsis.admin.integration.dapp.dto.DistributorsVerificationRequest;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 业务员登录校验接口.
 * 
 * @author fengwanjun
 */
public interface IDistributorVerificationService extends ProxySelf<IDistributorVerificationService> {

    /**
     * 业务员登录校验.
     * 
     * @param distributorsVerificationRequest
     *            登陆校验参数
     * @param requestContext
     *            请求上下文
     * @param memberCode
     *            会员号
     * @param password
     *            帐号密码
     * @param marketCode
     *            市场
     * @return 返回数据
     * @throws DAppException
     */
    Map<String, Object> loadVerification(DistributorsVerificationRequest distributorsVerificationRequest)
            throws DAppException;
}