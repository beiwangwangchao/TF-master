/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 组织参数头表dto.
 * 
 * @author Zhao
 *
 */
public class SpmParam extends BaseDTO {
    private Long orgParamId;

    private Long orgId;

    private String orgType;

    private String parameter;

    private String multiLvFlag;

    private Short controlLevel;

    private String multiValFlag;

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

    public Short getControlLevel() {
        return controlLevel;
    }

    public void setControlLevel(Short controlLevel) {
        this.controlLevel = controlLevel;
    }

    public String getMultiValFlag() {
        return multiValFlag;
    }

    public void setMultiValFlag(String multiValFlag) {
        this.multiValFlag = multiValFlag == null ? null : multiValFlag.trim();
    }
}