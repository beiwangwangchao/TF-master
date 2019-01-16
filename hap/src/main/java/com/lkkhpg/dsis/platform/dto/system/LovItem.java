/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.dto.system;

import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * LovItem.
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年2月1日
 */
public class LovItem extends BaseDTO {

    private static final long serialVersionUID = -1573793167997659244L;

    private Long lovItemId;

    private Long lovId;

    private String display;

    private String gridFieldName;

    private Integer gridFieldWidth;
    
    private String gridFieldAlign;
    
    private String autocompleteField = BaseConstants.YES;

    private String conditionField = BaseConstants.NO;

    private String isAutocomplete = BaseConstants.NO;

    private String gridField = BaseConstants.YES;

    private Integer conditionFieldWidth;

    private String conditionFieldType;

    private String conditionFieldName;
    
    private String conditionFieldTextField;

    private String conditionFieldNewline = BaseConstants.NO;

    private String conditionFieldSelectUrl;
    
    private String conditionFieldSelectVf;
    
    private String conditionFieldSelectTf;
    
    private String conditionFieldSelectCode;

    private String conditionFieldLovCode;
    
    private Integer conditionFieldSequence = 1;
    
    private Integer gridFieldSequence = 1;
    
    private String isSort;
    
    
    
    public String getConditionFieldTextField() {
        return conditionFieldTextField;
    }


    public void setConditionFieldTextField(String conditionFieldTextField) {
        this.conditionFieldTextField = conditionFieldTextField;
    }


    /**
     * @return the conditionFieldSelectUrl
     */
    public String getConditionFieldSelectUrl() {
        return conditionFieldSelectUrl;
    }


    /**
     * @param conditionFieldSelectUrl the conditionFieldSelectUrl to set
     */
    public void setConditionFieldSelectUrl(String conditionFieldSelectUrl) {
        this.conditionFieldSelectUrl = conditionFieldSelectUrl;
    }


    /**
     * @return the conditionFieldSelectVf
     */
    public String getConditionFieldSelectVf() {
        return conditionFieldSelectVf;
    }


    /**
     * @param conditionFieldSelectVf the conditionFieldSelectVf to set
     */
    public void setConditionFieldSelectVf(String conditionFieldSelectVf) {
        this.conditionFieldSelectVf = conditionFieldSelectVf;
    }


    /**
     * @return the conditionFieldSelectTf
     */
    public String getConditionFieldSelectTf() {
        return conditionFieldSelectTf;
    }


    /**
     * @param conditionFieldSelectTf the conditionFieldSelectTf to set
     */
    public void setConditionFieldSelectTf(String conditionFieldSelectTf) {
        this.conditionFieldSelectTf = conditionFieldSelectTf;
    }


    /**
     * @return the autocompleteField
     */
    public String getAutocompleteField() {
        return autocompleteField;
    }


    /**
     * @param autocompleteField the autocompleteField to set
     */
    public void setAutocompleteField(String autocompleteField) {
        this.autocompleteField = autocompleteField;
    }


    public String getGridFieldAlign() {
        return gridFieldAlign;
    }


    public void setGridFieldAlign(String gridFieldAlign) {
        this.gridFieldAlign = gridFieldAlign != null ? gridFieldAlign.toLowerCase() : "center";
    }


    public Integer getConditionFieldSequence() {
        return conditionFieldSequence;
    }

    
    public void setConditionFieldSequence(Integer conditionFieldSequence) {
        this.conditionFieldSequence = conditionFieldSequence;
    }

    
    public Integer getGridFieldSequence() {
        return gridFieldSequence;
    }
    
    public void setGridFieldSequence(Integer gridFieldSequence) {
        this.gridFieldSequence = gridFieldSequence;
    }

    public String getConditionFieldType() {
        return conditionFieldType;
    }

    public void setConditionFieldType(String conditionFieldType) {
        this.conditionFieldType = conditionFieldType;
    }

    public String getConditionFieldName() {
        return conditionFieldName;
    }

    
    public void setConditionFieldName(String conditionFieldName) {
        this.conditionFieldName = conditionFieldName;
    }

    
    public String getConditionFieldNewline() {
        return conditionFieldNewline;
    }

    
    public void setConditionFieldNewline(String conditionFieldNewline) {
        this.conditionFieldNewline = conditionFieldNewline;
    }
   
    public String getConditionFieldSelectCode() {
        return conditionFieldSelectCode;
    }

    public void setConditionFieldSelectCode(String conditionFieldSelectCode) {
        this.conditionFieldSelectCode = conditionFieldSelectCode;
    }

   
    public String getConditionFieldLovCode() {
        return conditionFieldLovCode;
    }

    public void setConditionFieldLovCode(String conditionFieldLovCode) {
        this.conditionFieldLovCode = conditionFieldLovCode;
    }

    public Integer getConditionFieldWidth() {
        return conditionFieldWidth;
    }

    public void setConditionFieldWidth(Integer conditionFieldWidth) {
        this.conditionFieldWidth = conditionFieldWidth;
    }

    public String getGridField() {
        return gridField;
    }

    public void setGridField(String gridField) {
        this.gridField = gridField;
    }

    public Long getLovItemId() {
        return lovItemId;
    }

    public void setLovItemId(Long lovItemId) {
        this.lovItemId = lovItemId;
    }

    public Long getLovId() {
        return lovId;
    }

    public void setLovId(Long lovId) {
        this.lovId = lovId;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display == null ? null : display.trim();
    }

    public String getGridFieldName() {
        return gridFieldName;
    }

    public void setGridFieldName(String name) {
        this.gridFieldName = name == null ? null : name.trim();
    }

    public Integer getGridFieldWidth() {
        return gridFieldWidth;
    }

    public void setGridFieldWidth(Integer width) {
        this.gridFieldWidth = width;
    }

    public String getConditionField() {
        return conditionField;
    }

    public void setConditionField(String conditionField) {
        this.conditionField = conditionField == null ? null : conditionField.trim();
    }

    public void setIsAutoComplete(String isAutoComplete) {
        this.isAutocomplete = isAutoComplete;
    }

    public String getIsAutoComplete() {
        return isAutocomplete;
    }


    public String getIsSort() {
        return isSort;
    }


    public void setIsSort(String isSort) {
        this.isSort = isSort;
    }
}