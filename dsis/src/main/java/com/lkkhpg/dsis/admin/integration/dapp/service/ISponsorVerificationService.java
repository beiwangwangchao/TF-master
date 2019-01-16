/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import com.lkkhpg.dsis.admin.integration.dapp.dto.SponsorVerificationRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.SponsorVerificationResponse;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 推荐人鉴别接口类.
 * 
 * @author linyuheng
 */
public interface ISponsorVerificationService {

    /**
     * 推荐人鉴别.
     * 
     * @param sponsorVerificationRequest
     *            推荐人鉴别请求数据
     * @return 推荐人鉴别结果
     * @throws DAppException
     *             DAPP接口异常
     * @throws IntegrationException GDS异常
     */
    SponsorVerificationResponse sponsorVerfication(SponsorVerificationRequest sponsorVerificationRequest)
            throws DAppException, IntegrationException;
}
