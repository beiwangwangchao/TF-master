/*
 *
 */
package com.lkkhpg.dsis.admin.config.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 组织参数简易DTO.
 * @author chenjingxiong
 */
public class SpmOrgParamValue {
    
    @NotEmpty
    private String orgType;
    
    @NotNull
    private Long orgId;
    
    @NotEmpty
    private String paramName;
    
    @NotEmpty
    private String paramValue;

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

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
    
}
