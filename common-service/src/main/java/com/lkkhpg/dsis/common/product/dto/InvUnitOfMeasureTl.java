/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 单位多语言Dto.
 * 
 * @author wangchao
 *
 */
public class InvUnitOfMeasureTl extends BaseDTO {

    private String uomCode;

    private String lang;

    private String name;

    private String description;

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}