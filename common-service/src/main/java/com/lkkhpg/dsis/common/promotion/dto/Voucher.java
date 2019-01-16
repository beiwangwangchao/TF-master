/*
 *
 */
package com.lkkhpg.dsis.common.promotion.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 优惠券Dto.
 * 
 * @author frank.li
 */
public class Voucher extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private Long voucherId;

    private Long marketId;

    private Long salesOrgId;

    @NotEmpty
    private String voucherCode;

    @NotEmpty
    private String name;

    private String description;

    @NotEmpty
    private String enabledFlag;

    @NotNull
    private Date startActiveDate;

    @NotNull
    private Date endActiveDate;

    @NotEmpty
    private String category;

    @NotEmpty
    private String type;

    @NotNull
    private Long quantity;

    private Integer maxUseQty;

    @NotEmpty
    private String status;

    @NotEmpty
    private String appScope;

    @NotEmpty
    private String usageMode;

    @NotEmpty
    private String applyCriteria;

    private Long itemQuantity;

    private Long giftQuantity;

    @NotEmpty
    private String appTax;

    private String operation;

    private BigDecimal orderAmount;

    private String useCriteria;

    @NotEmpty
    private String discountType;

    private BigDecimal discountValue;

    /**
     * 剩余数量.
     */
    private Long surplus;
    
    private Long sourceOrderId;
    
    /**
     * 优惠券分配会员.
     */
    @Children
    private List<PdmVoucherMember> pdmVoucherMembers;

    /**
     * 优惠券分配商品.
     */
    @Children
    private List<PdmVoucherItem> pdmVoucherItems;

    /**
     * 优惠券分配赠品.
     */
    @Children
    private List<PdmVoucherGift> pdmVoucherGifts;
    
    private String marketCode;
    
    private String memberCode;
    
    private String orderNumber;
    
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
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

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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

    public Long getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Long getGiftQuantity() {
        return giftQuantity;
    }

    public void setGiftQuantity(Long giftQuantity) {
        this.giftQuantity = giftQuantity;
    }

    public String getAppTax() {
        return appTax;
    }

    public void setAppTax(String appTax) {
        this.appTax = appTax;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
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

    public List<PdmVoucherMember> getPdmVoucherMembers() {
        return pdmVoucherMembers;
    }

    public void setPdmVoucherMembers(List<PdmVoucherMember> pdmVoucherMembers) {
        this.pdmVoucherMembers = pdmVoucherMembers;
    }

    public List<PdmVoucherItem> getPdmVoucherItems() {
        return pdmVoucherItems;
    }

    public void setPdmVoucherItems(List<PdmVoucherItem> pdmVoucherItems) {
        this.pdmVoucherItems = pdmVoucherItems;
    }

    public List<PdmVoucherGift> getPdmVoucherGifts() {
        return pdmVoucherGifts;
    }

    public void setPdmVoucherGifts(List<PdmVoucherGift> pdmVoucherGifts) {
        this.pdmVoucherGifts = pdmVoucherGifts;
    }

    public Long getSurplus() {
        return surplus;
    }

    public void setSurplus(Long surplus) {
        this.surplus = surplus;
    }

    public Long getSourceOrderId() {
        return sourceOrderId;
    }

    public void setSourceOrderId(Long sourceOrderId) {
        this.sourceOrderId = sourceOrderId;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    
}