/*
 *
 */
package com.lkkhpg.dsis.platform.dto.system;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 语言DTO.
 * 
 * @author shengyang.zhou@hand-china.com
 */
public class Language extends BaseDTO {

    private static final long serialVersionUID = -2978619661646886631L;

    private String baseLang;

    private String description;

    @NotEmpty
    private String langCode;

    public String getBaseLang() {
        return baseLang;
    }

    public String getDescription() {
        return description;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setBaseLang(String baseLang) {
        this.baseLang = baseLang == null ? null : baseLang.trim();
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode == null ? null : langCode.trim();
    }

}