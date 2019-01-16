/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetAddressRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetAddressResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 送货地址同步接口.
 * 
 * @author fengwanjun
 */
public interface IGetAddressService {

    List<GetAddressResponse> getAddress(GetAddressRequest getAddressRequest) throws DAppException;
}