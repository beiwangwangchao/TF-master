/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;


import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品参数信息dto.
 * 
 * @author wangchao
 *
 */
public class ItemAttrDto extends BaseDTO {
    
    private Long itemId;

    @NotEmpty
    private String language;

    @NotEmpty
    private String packageIntroduce;

    @NotEmpty
    private String standardParam;
    
    @NotEmpty
    private String dose;

    @NotEmpty
    private String afterService;

    @NotEmpty
    private String userGuide;

    @NotEmpty
    private String notes;

    @NotEmpty
    private String saveMethod;
    
    private String hideProductIntroduce;
    
    private String hideUseInstruction;
    
    private String hideStandardParam;
    
    private String hideDose;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPackageIntroduce() {
        return packageIntroduce;
    }

    public void setPackageIntroduce(String packageIntroduce) {
        this.packageIntroduce = packageIntroduce;
    }

    public String getStandardParam() {
        return standardParam;
    }

    public void setStandardParam(String standardParam) {
        this.standardParam = standardParam;
    }

    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = afterService;
    }

    public String getUserGuide() {
        return userGuide;
    }

    public void setUserGuide(String userGuide) {
        this.userGuide = userGuide;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSaveMethod() {
        return saveMethod;
    }

    public void setSaveMethod(String saveMethod) {
        this.saveMethod = saveMethod;
    }

    public String getHideProductIntroduce() {
        return hideProductIntroduce;
    }

    public void setHideProductIntroduce(String hideProductIntroduce) {
        this.hideProductIntroduce = hideProductIntroduce;
    }

    public String getHideUseInstruction() {
        return hideUseInstruction;
    }

    public void setHideUseInstruction(String hideUseInstruction) {
        this.hideUseInstruction = hideUseInstruction;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getHideStandardParam() {
        return hideStandardParam;
    }

    public void setHideStandardParam(String hideStandardParam) {
        this.hideStandardParam = hideStandardParam;
    }

    public String getHideDose() {
        return hideDose;
    }

    public void setHideDose(String hideDose) {
        this.hideDose = hideDose;
    }

}
