/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetAddressData;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetAddressRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetAddressResponse;
import com.lkkhpg.dsis.admin.integration.dapp.mapper.GetAddressMapper;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetAddressService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 送货地址同步接口.
 * 
 * @author fengwanjun
 */
@Transactional
@Service
public class GetAddressServiceImpl implements IGetAddressService {
    
    @Autowired
    private GetAddressMapper getAddressMapper;
    
    @Override
    @Transactional
    public List<GetAddressResponse> getAddress(GetAddressRequest getAddressRequest) throws DAppException {
        Map<String, Object> stateMap = new HashMap<String, Object>();
        stateMap.put("lang", getAddressRequest.getLang());
        stateMap.put("saleOrganization", getAddressRequest.getSaleOrganization());
        stateMap.put("market", getAddressRequest.getMarket());
        List<GetAddressData> getAddressDatas = getAddressMapper.queryCountryStateCityForDApp(stateMap);
        List<GetAddressResponse> getAddressResponses = new ArrayList<GetAddressResponse>();
        if (getAddressDatas != null && getAddressDatas.size() > 0) {
            for (GetAddressData getAddressData : getAddressDatas) {
                if (getAddressResponses != null && getAddressResponses.size() > 0) {
                    GetAddressResponse lastAddressResponse = 
                            getAddressResponses.get(getAddressResponses.size()-1);
                    if (lastAddressResponse.getCountryCode().equals(getAddressData.getCountryCode())) {
                        List<GetAddressResponse.State> states = lastAddressResponse.getStates();
                        GetAddressResponse.State lastState = states.get(states.size()-1);
                        if (lastState.getStateCode().equals(getAddressData.getStateCode())) {
                            GetAddressResponse.State.City city = new GetAddressResponse.State.City();
                            city.setCity(getAddressData.getCityName());
                            city.setCityCode(getAddressData.getCityCode());
                            lastState.getCities().add(city);
                        } else {
                            GetAddressResponse.State state = new GetAddressResponse.State();
                            state.setState(getAddressData.getStateName());
                            state.setStateCode(getAddressData.getStateCode());
                            List<GetAddressResponse.State.City> cities = new ArrayList<GetAddressResponse.State.City>();
                            GetAddressResponse.State.City city = new GetAddressResponse.State.City();
                            city.setCity(getAddressData.getCityName());
                            city.setCityCode(getAddressData.getCityCode());
                            cities.add(city);
                            state.setCities(cities);
                            lastAddressResponse.getStates().add(state);
                        }
                    } else {
                        getAddressResponses.add(setCountryInfo(getAddressData));
                    }
                } else {
                    getAddressResponses.add(setCountryInfo(getAddressData));
                }
            }
        }
        return getAddressResponses;
    }
    
    private GetAddressResponse setCountryInfo(GetAddressData getAddressData) {
        GetAddressResponse getAddressResponse = new GetAddressResponse();
        getAddressResponse.setCountryCode(getAddressData.getCountryCode());
        getAddressResponse.setCountry(getAddressData.getCountryName());
        List<GetAddressResponse.State> states = new ArrayList<GetAddressResponse.State>();
        GetAddressResponse.State state = new GetAddressResponse.State();
        state.setState(getAddressData.getStateName());
        state.setStateCode(getAddressData.getStateCode());
        states.add(state);
        List<GetAddressResponse.State.City> cities = new ArrayList<GetAddressResponse.State.City>();
        GetAddressResponse.State.City city = new GetAddressResponse.State.City();
        city.setCity(getAddressData.getCityName());
        city.setCityCode(getAddressData.getCityCode());
        cities.add(city);
        state.setCities(cities);
        getAddressResponse.setStates(states);
        return getAddressResponse;
    }
}