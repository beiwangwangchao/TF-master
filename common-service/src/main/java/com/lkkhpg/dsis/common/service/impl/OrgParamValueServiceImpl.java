/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.config.dto.SpmParamValue;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.IOrgParamService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 组织层级组织参数Service.
 * 
 * @author chenjingxiong
 */
@Service
public class OrgParamValueServiceImpl extends BaseParamValueServiceImpl implements IOrgParamService {

    @Override
    public List<String> getParamValues(IRequest request, String paramName, Long type, String orgId, String orgType) {

        if (!SystemProfileConstants.INV_ORG.equals(type) && !SystemProfileConstants.SALES_ORG.equals(type)) {
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
        } else if (SystemProfileConstants.ORG_TYPE_SALES.equals(orgType)) {
            // 父组织ID
            String parentOrgId = getParentOrgID(request, orgType, orgId);
            if (parentOrgId != null) {
                result = self().getParamValues(request, paramName, type, parentOrgId, orgType);
            }
        }
        return result;
    }

}
