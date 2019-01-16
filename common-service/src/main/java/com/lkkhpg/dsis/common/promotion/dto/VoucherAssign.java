/*
 *
 */
package com.lkkhpg.dsis.common.promotion.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 优惠券分配Dto.
 * 
 * @author frank.li
 */
public class VoucherAssign extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 表ID，主键，供其他表做外键.
     */
    private Long assignId;

    /**
     * 分配类型（新增字段）.
     */
    private String assignType;
    @NotNull
    private Long voucherId;

    private Long memberId;

    private Long marketId;

    private Long salesOrgId;

    private Long quantity;

    @NotEmpty
    private String voucherCode;

    @NotEmpty
    private String name;

    private String description;

    private Date startActiveDate;

    private Date endActiveDate;

    private String category;

    private String type;

    private Integer maxUseQty;

    private String status;

    private String voucherStatus;

    private String enabledFlag;

    private String orderNumber;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getAssignId() {
        return assignId;
    }

    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public String getAssignType() {
        return assignType;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType == null ? null : assignType.trim();
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getMaxUseQty() {
        return maxUseQty;
    }

    public void setMaxUseQty(Integer maxUseQty) {
        this.maxUseQty = maxUseQty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(String voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

}