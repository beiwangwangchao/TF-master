/*
 *
 */
package com.lkkhpg.dsis.admin.config.dto;

import java.util.List;
import java.util.Map;

/**
 * 获取多个组织参数使用的DTO.
 * @author chenjingxiong
 */
public class OrgParams {

    private String orgType;
    
    private Long orgId;

    private List<String> paramNames;
    
    private Map<String, List<String>> paramValues;
    
    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

    public void setParamNames(List<String> paramNames) {
        this.paramNames = paramNames;
    }

    public Map<String, List<String>> getParamValues() {
        return paramValues;
    }

    public void setParamValues(Map<String, List<String>> paramValues) {
        this.paramValues = paramValues;
    }

}
