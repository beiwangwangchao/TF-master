/*
 *
 */

package com.lkkhpg.dsis.common.product.dto;

import javax.persistence.Table;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品类别多语言Dto.
 * 
 * @author wangchao
 *
 */
@Table(name = "inv_category_tl")
public class InvCategoryTl extends BaseDTO {

    private Long categoryId;

    private String lang;

    private String categoryName;

    private String description;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

}