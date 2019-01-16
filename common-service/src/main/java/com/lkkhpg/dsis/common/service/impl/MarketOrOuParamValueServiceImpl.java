/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.config.dto.SpmParamValue;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.IMarketParamService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * Market、OU组织参数Service.
 * 
 * @author chenjingxiong
 */
@Service
public class MarketOrOuParamValueServiceImpl extends BaseParamValueServiceImpl implements IMarketParamService {

    @Override
    public List<String> getParamValues(IRequest request, String paramName, Long type, String orgId, String orgType) {
        if (!SystemProfileConstants.MARKET.equals(type) && !SystemProfileConstants.OU.equals(type)) {
            return null;
        }

        // 通过 paramName, type, orgId 找表中对应的值记录
        List<SpmParamValue> values = queryParamLinesTable(request, paramName, type, orgId);
        List<String> result = new ArrayList<String>();
        // 直接命中
        if (values != null && values.size() > 0) {
            for (SpmParamValue value : values) {
                result.add(value.getParamValue());
            }
            return result;
        }
        return result;
    }

    @Override
    public String getParamValue(IRequest request, String paramName, Long type, String orgId, String orgType) {
        if (!SystemProfileConstants.MARKET.equals(type) && !SystemProfileConstants.OU.equals(type)) {
            return null;
        }

        // 通过 paramName, type, orgId 找表中对应的值记录
        List<SpmParamValue> values = queryParamLinesTable(request, paramName, type, orgId);
        // 对于只允许唯一值的组织参数，直接返回第一个值
        if (values != null && values.size() > 0) {
            return values.get(0).getParamValue();
        }
        return null;
    }

    @Override
    public String getLevelValueParamValue(IRequest request, String paramName, Long type, String paramValue) {
        if (!SystemProfileConstants.MARKET.equals(type) && !SystemProfileConstants.OU.equals(type)) {
            return null;
        }

        // 通过 paramName, type, paramValue 找表中对应的值记录
        List<SpmParamValue> values = queryParamLinesByParamValue(request, paramName, type, paramValue);
        // 对于只允许唯一值的组织参数，直接返回第一个值
        if (values != null && values.size() > 0) {
            return values.get(0).getParamValue();
        }
        return null;
    }
}
