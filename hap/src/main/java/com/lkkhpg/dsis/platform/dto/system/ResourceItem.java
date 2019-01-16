/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.dto.system;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 资源权限项.
 * 
 * @author njq.niu@hand-china.com
 *
 * 2016年4月7日
 */
@MultiLanguage
@Table(name = "sys_resource_item_b")
public class ResourceItem extends BaseDTO {
    
    private static final long serialVersionUID = -2814559439360957338L;


    @Id
    @Column(name = "resource_item_id")
    private Long resourceItemId;

    
    private Long ownerResourceId;

    private Long targetResourceId;

    //仅用于显示
    private String targetResourceName;
    
    
    
    @NotEmpty
    private String itemId;

    @MultiLanguageField
    @Column(name = "item_name")
    private String itemName;
    
    @MultiLanguageField
    @Column(name = "description")
    private String description;

    private String itemType;
    
    

    /**
     * @return the targetResourceName
     */
    public String getTargetResourceName() {
        return targetResourceName;
    }

    /**
     * @param targetResourceName the targetResourceName to set
     */
    public void setTargetResourceName(String targetResourceName) {
        this.targetResourceName = targetResourceName;
    }

    /**
     * @return the resourceItemId
     */
    public Long getResourceItemId() {
        return resourceItemId;
    }

    /**
     * @param resourceItemId the resourceItemId to set
     */
    public void setResourceItemId(Long resourceItemId) {
        this.resourceItemId = resourceItemId;
    }

    /**
     * @return the ownerResourceId
     */
    public Long getOwnerResourceId() {
        return ownerResourceId;
    }

    /**
     * @param ownerResourceId the ownerResourceId to set
     */
    public void setOwnerResourceId(Long ownerResourceId) {
        this.ownerResourceId = ownerResourceId;
    }

    /**
     * @return the targetResourceId
     */
    public Long getTargetResourceId() {
        return targetResourceId;
    }

    /**
     * @param targetResourceId the targetResourceId to set
     */
    public void setTargetResourceId(Long targetResourceId) {
        this.targetResourceId = targetResourceId;
    }

    /**
     * @return the itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the itemType
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

}