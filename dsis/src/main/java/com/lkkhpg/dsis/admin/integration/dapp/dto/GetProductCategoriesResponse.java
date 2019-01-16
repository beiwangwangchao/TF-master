/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.Children;

/**
 * 产品类别查询响应DTO.
 * 
 * @author fengwanjun
 *
 */
public class GetProductCategoriesResponse {

    private long parentCateCode;
    
    private long cateCode;

    private String categoryName;

    private String leafFlag;

    @Children
    private List<GetProductCategoriesResponse> categories;

    public long getParentCateCode() {
        return parentCateCode;
    }

    public void setParentCateCode(long parentCateCode) {
        this.parentCateCode = parentCateCode;
    }

    public long getCateCode() {
        return cateCode;
    }

    public void setCateCode(long cateCode) {
        this.cateCode = cateCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLeafFlag() {
        return leafFlag;
    }

    public void setLeafFlag(String leafFlag) {
        this.leafFlag = leafFlag;
    }

    public List<GetProductCategoriesResponse> getCategories() {
        return categories;
    }

    public void setCategories(List<GetProductCategoriesResponse> categories) {
        this.categories = categories;
    }
}