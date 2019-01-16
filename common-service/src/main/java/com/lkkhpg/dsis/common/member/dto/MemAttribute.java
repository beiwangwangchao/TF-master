/*
 *
 */
package com.lkkhpg.dsis.common.member.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员属性DTO.
 * 
 * @author frank.li
 */
@AuditEnabled
@Table(name = "MM_MEM_ATTRIBUTE")
public class MemAttribute extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "attribute_id")
    private Long attributeId;

    @NotNull
    private String attribute;

    @NotNull
    private String value;

    //@NotNull 由保存Member对象时赋值，加上@NotNull会影响saveMember的validate
    private Long memberId;

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute == null ? null : attribute.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}