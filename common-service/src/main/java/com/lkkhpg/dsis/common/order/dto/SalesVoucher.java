/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 销售促销Dto.
 * 
 * @author houmin
 *
 */
public class SalesVoucher extends BaseDTO {
    private Long voucherApplyId;

    private Long headerId;

    private Long salesOrgId;

    private Long voucherId;

    private String name;

    private String voucherCode;

    private String description;

    private String category;

    private String type;

    private String appScope;

    private String usageMode;

    private String applyCriteria;

    private String itemQuantity;

    private String appTax;

    private String useCriteria;

    private String discountType;

    private BigDecimal discountValue;

    public Long getVoucherApplyId() {
        return voucherApplyId;
    }

    public void setVoucherApplyId(Long voucherApplyId) {
        this.voucherApplyId = voucherApplyId;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppScope() {
        return appScope;
    }

    public void setAppScope(String appScope) {
        this.appScope = appScope;
    }

    public String getUsageMode() {
        return usageMode;
    }

    public void setUsageMode(String usageMode) {
        this.usageMode = usageMode;
    }

    public String getApplyCriteria() {
        return applyCriteria;
    }

    public void setApplyCriteria(String applyCriteria) {
        this.applyCriteria = applyCriteria;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getAppTax() {
        return appTax;
    }

    public void setAppTax(String appTax) {
        this.appTax = appTax;
    }

    public String getUseCriteria() {
        return useCriteria;
    }

    public void setUseCriteria(String useCriteria) {
        this.useCriteria = useCriteria;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

}