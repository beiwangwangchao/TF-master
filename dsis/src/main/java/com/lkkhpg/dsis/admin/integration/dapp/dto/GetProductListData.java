/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.math.BigDecimal;

/**
 * 产品列表DTO.
 * 
 * @author fengwanjun
 *
 */
public class GetProductListData {

    private String saleOrganization;

    private String cateCode;

    private long productId;

    private String productCode;

    private String productName;

    private String dapp;

    private Long pv;

    private long inventory;

    private long orderedQty;

    private String backOrder;

    private long removeFlag;

    private String productType;

    private String productCountType;

    private String stockCountFlag;

    private long countItemId;

    private Long salesOrgId;

    private BigDecimal taxPercent;

    private String enableTax;

    private String priceIncludeTax;

    // private List<GetProductListPriceData> prices;

    public String getSaleOrganization() {
        return saleOrganization;
    }

    public void setSaleOrganization(String saleOrganization) {
        this.saleOrganization = saleOrganization;
    }

    public String getCateCode() {
        return cateCode;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDapp() {
        return dapp;
    }

    public void setDapp(String dapp) {
        this.dapp = dapp;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(long inventory) {
        this.inventory = inventory;
    }

    public long getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(long orderedQty) {
        this.orderedQty = orderedQty;
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(BigDecimal taxPercent) {
        this.taxPercent = taxPercent;
    }

    public String getBackOrder() {
        return backOrder;
    }

    public void setBackOrder(String backOrder) {
        this.backOrder = backOrder;
    }

    public long getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(long removeFlag) {
        this.removeFlag = removeFlag;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getProductCountType() {
        return productCountType;
    }

    public void setProductCountType(String productCountType) {
        this.productCountType = productCountType == null ? null : productCountType.trim();
    }

    public String getStockCountFlag() {
        return stockCountFlag;
    }

    public void setStockCountFlag(String stockCountFlag) {
        this.stockCountFlag = stockCountFlag == null ? null : stockCountFlag.trim();
    }

    public long getCountItemId() {
        return countItemId;
    }

    public void setCountItemId(long countItemId) {
        this.countItemId = countItemId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getEnableTax() {
        return enableTax;
    }

    public void setEnableTax(String enableTax) {
        this.enableTax = enableTax;
    }

    public String getPriceIncludeTax() {
        return priceIncludeTax;
    }

    public void setPriceIncludeTax(String priceIncludeTax) {
        this.priceIncludeTax = priceIncludeTax;
    }
    
    

}