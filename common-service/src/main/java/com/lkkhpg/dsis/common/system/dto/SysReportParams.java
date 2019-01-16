/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 报表程序参数.
 * 
 * @author wangchao
 *
 */
@Table(name = "sys_report_params")
public class SysReportParams extends BaseDTO {
    
    @Id
    @Column(name = "report_params_id")
    private Long reportParamsId;
    
    private Long reportProgramId;

    @NotEmpty
    private String paramName;

    @NotEmpty
    private String display;

    @NotEmpty
    private String fieldSequence;

    @NotEmpty
    private String fieldType;

    private String fieldSelectSource;
    
    private String fieldSelectCode;
    
    private String fieldSelectUrl;
    
    private String fieldSelectVf;
    
    private String fieldSelectTf;
    
    private String fieldTextfield;
    
    private String fieldLovCode;
    
    private String fieldSelectMulti;
    
    private String fieldDateFormat;
    
    @NotEmpty
    private String requireFlag;
    
    private String fieldDefaultValue;
    
    private String fieldLinkEvent;
    
    private String fieldLinkRule;
    
    public String getFieldDefaultValue() {
        return fieldDefaultValue;
    }

    public void setFieldDefaultValue(String fieldDefaultValue) {
        this.fieldDefaultValue = fieldDefaultValue;
    }

    public String getFieldLinkEvent() {
        return fieldLinkEvent;
    }

    public void setFieldLinkEvent(String fieldLinkEvent) {
        this.fieldLinkEvent = fieldLinkEvent;
    }

    public String getFieldLinkRule() {
        return fieldLinkRule;
    }

    public void setFieldLinkRule(String fieldLinkRule) {
        this.fieldLinkRule = fieldLinkRule;
    }

    public String getFieldSelectMulti() {
        return fieldSelectMulti;
    }

    public void setFieldSelectMulti(String fieldSelectMulti) {
        this.fieldSelectMulti = fieldSelectMulti;
    }

    public String getFieldDateFormat() {
        return fieldDateFormat;
    }

    public void setFieldDateFormat(String fieldDateFormat) {
        this.fieldDateFormat = fieldDateFormat;
    }

    public Long getReportParamsId() {
        return reportParamsId;
    }

    public void setReportParamsId(Long reportParamsId) {
        this.reportParamsId = reportParamsId;
    }

    public Long getReportProgramId() {
        return reportProgramId;
    }

    public void setReportProgramId(Long reportProgramId) {
        this.reportProgramId = reportProgramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getFieldSequence() {
        return fieldSequence;
    }

    public void setFieldSequence(String fieldSequence) {
        this.fieldSequence = fieldSequence;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldSelectSource() {
        return fieldSelectSource;
    }

    public void setFieldSelectSource(String fieldSelectSource) {
        this.fieldSelectSource = fieldSelectSource;
    }

    public String getFieldSelectCode() {
        return fieldSelectCode;
    }

    public void setFieldSelectCode(String fieldSelectCode) {
        this.fieldSelectCode = fieldSelectCode;
    }

    public String getFieldSelectUrl() {
        return fieldSelectUrl;
    }

    public void setFieldSelectUrl(String fieldSelectUrl) {
        this.fieldSelectUrl = fieldSelectUrl;
    }

    public String getFieldSelectVf() {
        return fieldSelectVf;
    }

    public void setFieldSelectVf(String fieldSelectVf) {
        this.fieldSelectVf = fieldSelectVf;
    }

    public String getFieldSelectTf() {
        return fieldSelectTf;
    }

    public void setFieldSelectTf(String fieldSelectTf) {
        this.fieldSelectTf = fieldSelectTf;
    }

    public String getFieldTextfield() {
        return fieldTextfield;
    }

    public void setFieldTextfield(String fieldTextfield) {
        this.fieldTextfield = fieldTextfield;
    }

    public String getFieldLovCode() {
        return fieldLovCode;
    }

    public void setFieldLovCode(String fieldLovCode) {
        this.fieldLovCode = fieldLovCode;
    }

    public String getRequireFlag() {
        return requireFlag;
    }

    public void setRequireFlag(String requireFlag) {
        this.requireFlag = requireFlag;
    }
    
}
