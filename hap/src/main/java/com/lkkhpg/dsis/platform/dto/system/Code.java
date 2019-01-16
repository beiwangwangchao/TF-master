/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.dto.system;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * CodeDTO.
 *
 * @author runbai.chen
 */
@MultiLanguage
@Table(name = "sys_code_b")
public class Code extends BaseDTO {
    
    private static final long serialVersionUID = 2776430709705510697L;

    /**
     * 快码类型.
     */
    @NotEmpty
    private String code;

    /**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @Column(name = "code_id", nullable = false)
    private Long codeId;

    @Children
    private List<CodeValue> codeValues;

    /**
     * 快码描述.
     */
    @Column(name = "description")
    @MultiLanguageField
    @NotEmpty
    private String description;

    public String getCode() {
        return code;
    }

    public Long getCodeId() {
        return codeId;
    }

    public List<CodeValue> getCodeValues() {
        return codeValues;
    }

    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public void setCodeValues(List<CodeValue> codeValues) {
        this.codeValues = codeValues;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

}