/*
 *
 */

package com.lkkhpg.dsis.platform.dto.system;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 功能的DTO.
 *
 * @author wuyichu
 */
@MultiLanguage
@Table(name = "sys_function_b")
public class Function extends BaseDTO {

    private static final long serialVersionUID = -8645010673309468576L;

    /**
     * 区域ID.
     */
    private String areaCode;

    /**
     * 功能编码.
     */
    @NotEmpty
    private String functionCode;

    /**
     * 描述.
     */
    @Column(name = "function_description")
    @com.lkkhpg.dsis.platform.annotation.MultiLanguageField
    private String functionDesc;
    
    /**
     * 功能图标.
     */
    private String functionIcon;
    
    /**
     * 排序号.
     */
    @NotNull
    private Long functionSequence;

    

    /**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @Column(name = "function_id", nullable = false)
    private Long functionId;

    /**
     * 功能名称.
     */
    @NotEmpty
    @Column(name = "function_name")
    @com.lkkhpg.dsis.platform.annotation.MultiLanguageField
    private String functionName;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lang;

    /**
     * 模块编码.
     */
    @NotEmpty
    private String moduleCode;

    private Long parentFunctionId;

    /**
     * 资源ID.
     */
    private Long resourceId;

    @Children
    private List<Resource> resources;

    /**
     * 功能类型.
     * TODO:配置类型，界面上选择
     */
    private String type = "PAGE";

    public String getAreaCode() {
        return areaCode;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public String getFunctionDesc() {
        return functionDesc;
    }

    public String getFunctionIcon() {
        return functionIcon;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public String getFunctionName() {
        return functionName;
    }
    
    public String getLang() {
        return lang;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public Long getParentFunctionId() {
        return parentFunctionId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public String getType() {
        return type;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode == null ? null : functionCode.trim();
    }

    public void setFunctionDesc(String functionDesc) {
        this.functionDesc = functionDesc == null ? null : functionDesc.trim();
    }

    public void setFunctionIcon(String functionIcon) {
        this.functionIcon = functionIcon == null ? null : functionIcon.trim();
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName == null ? null : functionName.trim();
    }

    public void setLang(String sourceLang) {
        this.lang = sourceLang;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode == null ? null : moduleCode.trim();
    }

    public void setParentFunctionId(Long parentFunctionId) {
        this.parentFunctionId = parentFunctionId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
    
    public Long getFunctionSequence() {
        return functionSequence;
    }

    public void setFunctionSequence(Long functionSequence) {
        this.functionSequence = functionSequence;
    }
}