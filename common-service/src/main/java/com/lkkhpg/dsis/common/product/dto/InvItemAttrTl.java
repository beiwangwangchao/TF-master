/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品参数多语言dto.
 * 
 * @author wangchao
 *
 */
@Table(name = "inv_item_attr_tl")
public class InvItemAttrTl extends BaseDTO {

    @Id
    @Column(name = "item_attr_id")
    private Long itemAttrId;

    @NotEmpty
    private String lang;

    private Long itemId;

    private String name;

    private byte[] content;

    private String contentStr;

    private String packageDescription;
    private String specification;
    private String afterService;
    private String userDirections;
    private String noticeItem;
    private String saveMethod;
    private String hide;

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = afterService;
    }

    public String getUserDirections() {
        return userDirections;
    }

    public void setUserDirections(String userDirections) {
        this.userDirections = userDirections;
    }

    public String getNoticeItem() {
        return noticeItem;
    }

    public void setNoticeItem(String noticeItem) {
        this.noticeItem = noticeItem;
    }

    public String getSaveMethod() {
        return saveMethod;
    }

    public void setSaveMethod(String saveMethod) {
        this.saveMethod = saveMethod;
    }

    public String getContentStr() {
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }

    public Long getItemAttrId() {
        return itemAttrId;
    }

    public void setItemAttrId(Long itemAttrId) {
        this.itemAttrId = itemAttrId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getHide() {
        return hide;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }

}
