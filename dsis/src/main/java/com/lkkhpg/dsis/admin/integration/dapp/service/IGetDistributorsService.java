/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetDistributorRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetDistributorResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetDistributorsRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetDistributorsResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 获取业务员列表接口类.
 * 
 * @author linyuheng
 */
public interface IGetDistributorsService extends ProxySelf<IGetDistributorsService> {

    /**
     * 获取业务员列表.
     * 
     * @param getDistributorsRequest
     *            获取业务员列表请求DTO
     * @param maxPerpage
     *            每页记录数
     * @param pageNo
     *            页码
     * @return 业务员列表数据
     * @throws DAppException
     *             接口异常
     */
    GetDistributorsResponse getDistributors(GetDistributorsRequest getDistributorsRequest, int pageNo, int maxPerpage)
            throws DAppException;

    /**
     * 通过ID获取业务员.
     * 
     * @param getDistributorRequest
     *            获取业务员请求DTO
     * @return 业务员数据
     * @throws DAppException
     *             接口异常
     */
    GetDistributorResponse getDistributor(GetDistributorRequest getDistributorRequest) throws DAppException;
}
