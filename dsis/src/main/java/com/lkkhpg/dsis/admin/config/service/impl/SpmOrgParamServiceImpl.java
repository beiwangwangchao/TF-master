/*
 *
 */
package com.lkkhpg.dsis.admin.config.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.config.dto.SpmOrgParamValue;
import com.lkkhpg.dsis.admin.config.service.ISpmOrgParamService;
import com.lkkhpg.dsis.common.config.dto.OrgParam;
import com.lkkhpg.dsis.common.config.dto.OrgParamDef;
import com.lkkhpg.dsis.common.config.dto.OrgParamValue;
import com.lkkhpg.dsis.common.config.mapper.OrgParamDefMapper;
import com.lkkhpg.dsis.common.config.mapper.OrgParamMapper;
import com.lkkhpg.dsis.common.config.mapper.OrgParamValueMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 组织参数Service.
 * @author chenjingxiong
 */
@Service
@Transactional
public class SpmOrgParamServiceImpl implements ISpmOrgParamService {

    private static final String MULTY_VALUE_SPLITOR = ";";
    
    @Autowired
    private OrgParamDefMapper orgParamDefMapper;
    
    @Autowired
    private OrgParamMapper orgParamMapper;
    
    @Autowired
    private OrgParamValueMapper orgParamValueMapper;
    
    @Override
    public List<OrgParamDef> getOrgParamDefByOrgType(IRequest request, String orgType) {
        return orgParamDefMapper.selectByOrgType(orgType);
    }

    @Override
    public List<OrgParamValue> getOrgParamValues(IRequest request, String orgType, Long orgId) {
        Long levelId = getLevelIdByOrgType(orgType);
        List<OrgParamValue> orgParamValues = orgParamValueMapper.selectByOrg(levelId, orgId.toString());
        Map<String, OrgParamValue> orgParamValuesMap = new HashMap<>();
        for (OrgParamValue orgParamValue : orgParamValues) {
            String paramName = orgParamValue.getParamName();
            OrgParamValue sameNameOrgParamValue = orgParamValuesMap.get(paramName);
            if (sameNameOrgParamValue != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(sameNameOrgParamValue.getParamValue()).append(MULTY_VALUE_SPLITOR)
                        .append(orgParamValue.getParamValue());
                sameNameOrgParamValue.setParamValue(sb.toString());

                if (!StringUtils.isEmpty(sameNameOrgParamValue.getParamText())) {
                    sb.delete(0, sb.length());
                    sb.append(sameNameOrgParamValue.getParamText()).append(MULTY_VALUE_SPLITOR)
                            .append(orgParamValue.getParamText());
                    sameNameOrgParamValue.setParamText(sb.toString());
                }
            } else {
                sameNameOrgParamValue = orgParamValue;
            }
            orgParamValuesMap.put(paramName, sameNameOrgParamValue);
        }
        List<OrgParamValue> result = new ArrayList<>(orgParamValuesMap.values());
        return result;
    }

    @Override
    public List<OrgParamDef> queryOrgParamDef(IRequest request, OrgParamDef orgParamDef, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return orgParamDefMapper.selectOrgParamDef();
    }

    @Override
    public List<OrgParamDef> saveOrgParamDef(IRequest request, List<OrgParamDef> orgParamDefs) {
        for (OrgParamDef orgParamDef : orgParamDefs) {
            orgParamDefMapper.updateByPrimaryKeySelective(orgParamDef);
        }
        return orgParamDefs;
    }

    @Override
    public List<OrgParamValue> saveOrgParamValues(IRequest request, List<SpmOrgParamValue> spmOrgParamValues) {
        List<OrgParamValue> returnResults = new ArrayList<>();
        if (spmOrgParamValues != null && spmOrgParamValues.size() > 0) {
            String orgType = spmOrgParamValues.get(0).getOrgType();
            Long orgId = spmOrgParamValues.get(0).getOrgId();
            Long levelId = getLevelIdByOrgType(orgType);
            // 先删除表里的组织参数
            orgParamMapper.deleteByOrg(orgType, orgId);
            orgParamValueMapper.deleteByOrg(levelId, orgId.toString());

            // 构建保存参数.
            Map<String, OrgParam> orgParams = new HashMap<>();
            for (SpmOrgParamValue spmOrgParamValue : spmOrgParamValues) {
                String paramName = spmOrgParamValue.getParamName();
                OrgParam orgParam = orgParams.get(paramName);
                if (orgParam == null) {
                    orgParam = new OrgParam();
                    orgParam.setOrgType(orgType);
                    orgParam.setOrgId(orgId);
                    orgParam.setParameter(paramName);
                    orgParam.setMultiLvFlag(SystemProfileConstants.NO);
                    orgParam.setMultiValFlag(SystemProfileConstants.NO);
                    orgParam.setControlLevel(levelId);
                    orgParam.setCreatedBy(request.getAccountId());
                    orgParam.setLastUpdatedBy(request.getAccountId());
                    orgParams.put(paramName, orgParam);
                }

                OrgParamValue orgParamValue = new OrgParamValue();
                orgParamValue.setLevelId(levelId);
                orgParamValue.setLevelValue(orgId.toString());
                orgParamValue.setParamValue(spmOrgParamValue.getParamValue());
                orgParamValue.setDefaultFlag(SystemProfileConstants.NO);
                orgParamValue.setEnabledFlag(SystemProfileConstants.YES);
                orgParamValue.setCreatedBy(request.getAccountId());
                orgParamValue.setLastUpdatedBy(request.getAccountId());

                List<OrgParamValue> orgParamValues = orgParam.getParamValues();
                if (orgParamValues == null) {
                    orgParamValues = new ArrayList<>();
                } else if (orgParamValues.size() > 0) {
                    orgParam.setMultiValFlag(SystemProfileConstants.YES);
                }
                orgParamValues.add(orgParamValue);
                orgParam.setParamValues(orgParamValues);
            }

            // 执行保存
            for (OrgParam orgParam : orgParams.values()) {
                orgParamMapper.insert(orgParam);
                Long orgParamId = orgParam.getOrgParamId();
                for (OrgParamValue orgParamValue : orgParam.getParamValues()) {
                    orgParamValue.setOrgParamId(orgParamId);
                    int row = orgParamValueMapper.insert(orgParamValue);
                    if (row > 0) {
                        returnResults.add(orgParamValue);
                    }
                }
            }
        }
        return returnResults;
    }
    
    /**
     * 映射OrgType和Level Id.
     * TODO:这步映射有点奇怪，后续重新评审设计.
     * @param orgType 组织类型.
     * @return Level Id.
     */
    private Long getLevelIdByOrgType(String orgType) {
        if (SystemProfileConstants.ORG_TYPE_INV.equals(orgType)) {
            return SystemProfileConstants.INV_ORG;
        } else if (SystemProfileConstants.ORG_TYPE_SALES.equals(orgType)) {
            return SystemProfileConstants.SALES_ORG;
        }  else if (SystemProfileConstants.ORG_TYPE_MARKET.equals(orgType)) {
            return SystemProfileConstants.MARKET;
        } else if (SystemProfileConstants.ORG_TYPE_OU.equals(orgType)) {
            return SystemProfileConstants.OU;
        }
        return 0L;
    }
}
