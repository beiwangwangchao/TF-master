/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.system.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 组织模板明细.
 * 
 * @author runbai.chen
 */
public class OrgTemplateDtl extends BaseDTO {
    private Long orgTemplateDtlId;

    private String defaultFlag;

    private Long orgTemplateId;

    private String assignType;

    private Long assignValue;

    private String assignValueName;

    public Long getOrgTemplateDtlId() {
        return orgTemplateDtlId;
    }

    public void setOrgTemplateDtlId(Long orgTemplateDtlId) {
        this.orgTemplateDtlId = orgTemplateDtlId;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public Long getOrgTemplateId() {
        return orgTemplateId;
    }

    public void setOrgTemplateId(Long orgTemplateId) {
        this.orgTemplateId = orgTemplateId;
    }

    public String getAssignType() {
        return assignType;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType == null ? null : assignType.trim();
    }

    public Long getAssignValue() {
        return assignValue;
    }

    public void setAssignValue(Long assignValue) {
        this.assignValue = assignValue;
    }

    public String getAssignValueName() {
        return assignValueName;
    }

    public void setAssignValueName(String assignValueName) {
        this.assignValueName = assignValueName;
    }

}