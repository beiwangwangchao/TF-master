/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 组织参数值.
 * @author chenjingxiong
 */
public class OrgParamValue extends BaseDTO {
    private Long orgParamValId;

    private Long orgParamId;

    private Long levelId;

    private String levelValue;

    private String paramValue;
    
    private String paramText;

    private String defaultFlag;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;
    
    private String paramName;

    public Long getOrgParamValId() {
        return orgParamValId;
    }

    public void setOrgParamValId(Long orgParamValId) {
        this.orgParamValId = orgParamValId;
    }

    public Long getOrgParamId() {
        return orgParamId;
    }

    public void setOrgParamId(Long orgParamId) {
        this.orgParamId = orgParamId;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getLevelValue() {
        return levelValue;
    }

    public void setLevelValue(String levelValue) {
        this.levelValue = levelValue == null ? null : levelValue.trim();
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag == null ? null : defaultFlag.trim();
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamText() {
        return paramText;
    }

    public void setParamText(String paramText) {
        this.paramText = paramText;
    }
}