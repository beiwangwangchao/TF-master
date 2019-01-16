/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 组织参数定义.
 * @author chenjingxiong
 */
public class OrgParamDef extends BaseDTO {
    private Long paramId;

    private String paramName;

    private String description;

    @NotEmpty
    private String category;
    
    private String categoryDesc;

    @NotEmpty
    private String marketFlag;

    @NotEmpty
    private String ouFlag;

    @NotEmpty
    private String salesFlag;

    @NotEmpty
    private String invFlag;

    private String multiValueFlag;

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc == null ? null : categoryDesc.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getMarketFlag() {
        return marketFlag;
    }

    public void setMarketFlag(String marketFlag) {
        this.marketFlag = marketFlag == null ? null : marketFlag.trim();
    }

    public String getOuFlag() {
        return ouFlag;
    }

    public void setOuFlag(String ouFlag) {
        this.ouFlag = ouFlag == null ? null : ouFlag.trim();
    }

    public String getSalesFlag() {
        return salesFlag;
    }

    public void setSalesFlag(String salesFlag) {
        this.salesFlag = salesFlag == null ? null : salesFlag.trim();
    }

    public String getInvFlag() {
        return invFlag;
    }

    public void setInvFlag(String invFlag) {
        this.invFlag = invFlag == null ? null : invFlag.trim();
    }

    public String getMultiValueFlag() {
        return multiValueFlag;
    }

    public void setMultiValueFlag(String multiValueFlag) {
        this.multiValueFlag = multiValueFlag == null ? null : multiValueFlag.trim();
    }
}