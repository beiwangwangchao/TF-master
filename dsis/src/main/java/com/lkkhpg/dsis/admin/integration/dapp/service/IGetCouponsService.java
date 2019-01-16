/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetCouponsRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetCouponsResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * @author songyuanhuang 获取优惠券接口类
 */
public interface IGetCouponsService extends ProxySelf<IGetCouponsService> {

    /**
     * 获取优惠券.
     * @param getCouponsRequest 
     *            获取优惠券列表请求DTO
     * @return 业务员列表数据
     * @throws DAppException
     *             接口异常
     */
    List<GetCouponsResponse> getCoupons(GetCouponsRequest getCouponsRequest) throws DAppException;

}
