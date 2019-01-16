/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.config.dto.SpmParamValue;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.ILocationParamService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 地点层级组织参数Service.
 * 
 * @author chenjingxiong
 */
@Service
public class LocationParamValueServiceImpl extends BaseParamValueServiceImpl implements ILocationParamService {

    @Override
    public List<String> getParamValues(IRequest request, String paramName, Long type, String orgId, String orgType) {

        // 此接口不处理其他类型的type
        if (!SystemProfileConstants.COUNTRY.equals(type) && !SystemProfileConstants.STATE.equals(type)
                && !SystemProfileConstants.CITY.equals(type)) {
            return null;
        }

        // 取code从spm_location表取得
        String locationCode = getLocationCode(request, orgType, orgId, type);
        if (locationCode == null) {
            return null;
        }
        // 通过 paramName, type, locationCode 找表中对应的值记录
        List<SpmParamValue> values = queryParamLinesTable(request, paramName, type, locationCode);

        List<String> result = new ArrayList<String>();
        // 直接命中
        if (values != null && values.size() > 0) {
            for (SpmParamValue value : values) {
                result.add(value.getParamValue());
            }
            return result;
        } else if (!SystemProfileConstants.ORG_TYPE_MARKET.equals(orgType)
                && !SystemProfileConstants.ORG_TYPE_OU.equals(orgType)) {
            // 父组织ID
            String parentOrgId = getParentOrgID(request, orgType, orgId);
            if (parentOrgId != null) {
                result = self().getParamValues(request, paramName, type, parentOrgId, orgType);
            } else {
                // 根据orgType是库存还是销售,可找对应的Market或者Ou
                String marketOrOuId = getMarketOrOuId(request, orgType, orgId);
                if (marketOrOuId == null) {
                    // 取不到市场的情况下
                    return null;
                }
                if (SystemProfileConstants.ORG_TYPE_SALES.equals(orgType)) { // 销售线取市场ID
                    orgType = SystemProfileConstants.ORG_TYPE_MARKET;
                } else if (SystemProfileConstants.ORG_TYPE_INV.equals(orgType)) { // 库存线取OU_ID
                    orgType = SystemProfileConstants.ORG_TYPE_OU;
                }
                result = self().getParamValues(request, paramName, type, marketOrOuId, orgType);
            }
        }
        return result;
    }

}
