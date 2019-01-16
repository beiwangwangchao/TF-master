/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 单位Dto.
 * 
 * @author wangchao
 *
 */
@MultiLanguage
@Table(name = "inv_unit_of_measure_b")
public class InvUnitOfMeasureB extends BaseDTO {

    @Id
    @Column(name = "uom_code")
    private String uomCode;

    @NotEmpty
    @MultiLanguageField
    @Column(name = "NAME")
    private String name;

    @MultiLanguageField
    @Column(name = "DESCRIPTION")
    private String description;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode == null ? null : uomCode.trim();
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

}