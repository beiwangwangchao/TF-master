/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品类别分配商品DTO.
 * 
 * @author chenjingxiong
 */
@Table(name = "inv_item_category")
public class InvItemCategory extends BaseDTO {
    @Id
    @Column(name = "category_item_id")
    private Long categoryItemId;

    @NotNull
    private Long itemId;

    @NotNull
    private Long categoryId;

    public Long getCategoryItemId() {
        return categoryItemId;
    }

    public void setCategoryItemId(Long categoryItemId) {
        this.categoryItemId = categoryItemId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}