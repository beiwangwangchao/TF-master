/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 组织参数.
 * @author chenjingxiong
 */
public class OrgParam extends BaseDTO {
    private Long orgParamId;

    private Long orgId;

    private String orgType;

    private String parameter;

    private String multiLvFlag;

    private Long controlLevel;

    private String multiValFlag;
    
    private List<OrgParamValue> paramValues;
    
    public Long getOrgParamId() {
        return orgParamId;
    }

    public void setOrgParamId(Long orgParamId) {
        this.orgParamId = orgParamId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    public String getMultiLvFlag() {
        return multiLvFlag;
    }

    public void setMultiLvFlag(String multiLvFlag) {
        this.multiLvFlag = multiLvFlag == null ? null : multiLvFlag.trim();
    }

    public Long getControlLevel() {
        return controlLevel;
    }

    public void setControlLevel(Long controlLevel) {
        this.controlLevel = controlLevel;
    }

    public String getMultiValFlag() {
        return multiValFlag;
    }

    public void setMultiValFlag(String multiValFlag) {
        this.multiValFlag = multiValFlag == null ? null : multiValFlag.trim();
    }

    public List<OrgParamValue> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<OrgParamValue> paramValues) {
        this.paramValues = paramValues;
    }
    
    
}