/*
 *
 */
package com.lkkhpg.dsis.platform.dto.system;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * CodeValueDTO.
 *
 * @author runbai.chen
 */
@MultiLanguage
@Table(name = "sys_code_value_b")
public class CodeValue extends BaseDTO {

    private static final long serialVersionUID = 7078027762943933806L;

    /**
     * 快速编码类型.
     */
    @Column(name = "code_id")
    @NotEmpty
    private Long codeId;

    /**
     * ID.
     */
    @Id
    @Column(name = "code_value_id")
    @NotEmpty
    private Long codeValueId;

    /**
     * 快码编码值描述.
     */
    @Column(name = "description")
    @com.lkkhpg.dsis.platform.annotation.MultiLanguageField
    private String description;

    /**
     * 快码编码意义描述.
     */
    @Column(name = "meaning")
    @com.lkkhpg.dsis.platform.annotation.MultiLanguageField
    @NotEmpty
    private String meaning;

    /**
     * 快速编码code.
     */
    @Column(name = "value")
    @NotEmpty
    private String value;
    /**
     * 快速编码-排序.
     */
    private Long sortNum;

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public Long getCodeId() {
        return codeId;
    }

    public Long getCodeValueId() {
        return codeValueId;
    }

    public String getDescription() {
        return description;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getValue() {
        return value;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public void setCodeValueId(Long codeValueId) {
        this.codeValueId = codeValueId;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning == null ? null : meaning.trim();
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

}