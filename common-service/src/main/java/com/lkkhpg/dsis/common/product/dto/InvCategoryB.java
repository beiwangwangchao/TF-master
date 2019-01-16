/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * 商品类别DTO.
 * 
 * @author chenjingxiong
 */
@MultiLanguage
@Table(name = "inv_category_b")
public class InvCategoryB extends BaseDTO {

    @Id
    @Column(name = "category_id")
    private Long categoryId;

    @NotEmpty
    @MultiLanguageField
    @Column(name = "category_name")
    private String categoryName;

    @MultiLanguageField
    @Column(name = "description")
    private String description;

    private Long parentCategoryId;

    private String defaultValue;

    private String enabledFlag = "Y";

    private Date startActiveDate;

    private Date endActiveDate;

    private String leafFlag;

    private List<InvItem> items;

    @Children
    private List<InvCategoryB> children;

    private Long categoryItemId;
    
    private Date creationDate;
    
    private Long sortNum;

    private String attribute14;

    private String attribute15;

    @Override
    public String getAttribute14() {
        return attribute14;
    }

    @Override
    public void setAttribute14(String attribute14) {
        this.attribute14 = attribute14;
    }

    @Override
    public String getAttribute15() {
        return attribute15;
    }

    @Override
    public void setAttribute15(String attribute15) {
        this.attribute15 = attribute15;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public Long getCategoryItemId() {
        return categoryItemId;
    }

    public void setCategoryItemId(Long categoryItemId) {
        this.categoryItemId = categoryItemId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public List<InvCategoryB> getChildren() {
        return children;
    }

    public void setChildren(List<InvCategoryB> children) {
        this.children = children;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }

    public List<InvItem> getItems() {
        return items;
    }

    public void setItems(List<InvItem> items) {
        this.items = items;
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

    public String getLeafFlag() {
        return leafFlag;
    }

    public void setLeafFlag(String leafFlag) {
        this.leafFlag = leafFlag;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    

}