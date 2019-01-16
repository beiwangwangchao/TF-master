/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品多语言dto.
 * 
 * @author wangchao
 *
 */
public class InvItemTl extends BaseDTO {

    private Long itemId;

    @NotEmpty
    private String lang;

    @NotEmpty
    private String itemName;

    private String description;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

}