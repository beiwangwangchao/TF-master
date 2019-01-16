/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetAddressData;

/**
 * 送货地址Mapper.
 * 
 * @author fengwanjun
 */

public interface GetAddressMapper {
    
    List<GetAddressData> queryCountryStateCityForDApp(Map<String, Object> stateMap);
    
}