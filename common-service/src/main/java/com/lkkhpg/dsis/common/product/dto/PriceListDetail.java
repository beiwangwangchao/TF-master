/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品价格行.
 * 
 * @author wuyichu
 */
@Table(name = "OM_PRICE_LIST_DETAIL")
public class PriceListDetail extends BaseDTO {

    @Id
    @Column(name = "LIST_DETAIL_ID", nullable = false, unique = true)
    private Long listDetailId;

    private Long priceListId;

    private Long itemId;

    private BigDecimal unitPrice;
    
    private String uomCode;

    private String currency;

    @NotEmpty
    private String priceType;

    private String enabledFlag;

   // @NotNull
    private Date startActiveDate;

//    @NotNull
    private Date endActiveDate;

    @NotNull
    private Long salesOrgId;

    private String salesOrgName;
    
    private String salesOrgCode;

    private String priceTypeDesc;
    
    private Long marketId;
    
    private String disableTaxFlag;
    /**
     * 物流重量kg
     */
    private String attribute15;

    @Override
    public String getAttribute15() {
        return attribute15;
    }

    @Override
    public void setAttribute15(String attribute15) {
        this.attribute15 = attribute15;
    }

    public String getSalesOrgCode() {
        return salesOrgCode;
    }

    public void setSalesOrgCode(String salesOrgCode) {
        this.salesOrgCode = salesOrgCode;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getListDetailId() {
        return listDetailId;
    }

    public void setListDetailId(Long listDetailId) {
        this.listDetailId = listDetailId;
    }

    public Long getPriceListId() {
        return priceListId;
    }

    public void setPriceListId(Long priceListId) {
        this.priceListId = priceListId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
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

    public String getSalesOrgName() {
        return salesOrgName;
    }

    public void setSalesOrgName(String salesOrgName) {
        this.salesOrgName = salesOrgName;
    }

    public String getPriceTypeDesc() {
        return priceTypeDesc;
    }

    public void setPriceTypeDesc(String priceTypeDesc) {
        this.priceTypeDesc = priceTypeDesc;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getDisableTaxFlag() {
        return disableTaxFlag;
    }

    public void setDisableTaxFlag(String disableTaxFlag) {
        this.disableTaxFlag = disableTaxFlag;
    }

}