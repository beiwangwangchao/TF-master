/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品参数dto.
 * 
 * @author wangchao
 *
 */
@Table(name = "inv_item_attr_b")
public class InvItemAttr extends BaseDTO {

    @Id
    @Column(name = "item_attr_id")
    private Long itemAttrId;

    @NotNull
    private Long itemId;

    @NotEmpty
    private String name;

    @NotEmpty
    private byte[] content;

    public Long getItemAttrId() {
        return itemAttrId;
    }

    public void setItemAttrId(Long itemAttrId) {
        this.itemAttrId = itemAttrId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}