/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.dto.system;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * LovDTO.
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年2月1日
 */
public class Lov extends BaseDTO {
    private static final long serialVersionUID = -466598144320311424L;

    @NotEmpty
    private String code;

    private String description;

    private Integer height;

    private Long lovId;

    @Children
    private List<LovItem> lovItems;

    private String placeholder;

    @NotEmpty
    private String sqlId;

    @NotEmpty
    private String textField;

    private String title;

    @NotEmpty
    private String valueField;

    private Integer width;
    
    private String delayLoad = BaseConstants.NO;
    
    private String needQueryParam = BaseConstants.NO;
    
    private String editable = BaseConstants.NO;
    
    private String canPopup = BaseConstants.YES;

    private String sortName;
    
    private String sortOrder;
 

    public String getNeedQueryParam() {
        return needQueryParam;
    }

    public void setNeedQueryParam(String needQueryParam) {
        this.needQueryParam = needQueryParam;
    }

    public String getDelayLoad() {
        return delayLoad;
    }

    public void setDelayLoad(String delayLoad) {
        this.delayLoad = delayLoad;
    }
    
    

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getCanPopup() {
        return canPopup;
    }

    public void setCanPopup(String canPopup) {
        this.canPopup = canPopup;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Integer getHeight() {
        return height;
    }

    public Long getLovId() {
        return lovId;
    }

    public List<LovItem> getLovItems() {
        return lovItems;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getSqlId() {
        return sqlId;
    }

    public String getTextField() {
        return textField;
    }

    public String getTitle() {
        return title;
    }

    public String getValueField() {
        return valueField;
    }

    public Integer getWidth() {
        return width;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setLovId(Long lovId) {
        this.lovId = lovId;
    }

    public void setLovItems(List<LovItem> lovItems) {
        this.lovItems = lovItems;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId == null ? null : sqlId.trim();
    }

    public void setTextField(String textField) {
        this.textField = textField == null ? null : textField.trim();
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public void setValueField(String valueField) {
        this.valueField = valueField == null ? null : valueField.trim();
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
    
    
    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

}