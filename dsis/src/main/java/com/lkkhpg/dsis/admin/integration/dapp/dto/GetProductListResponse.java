/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品列表查询响应DTO.
 * 
 * @author fengwanjun
 *
 */
public class GetProductListResponse {
    
    private String saleOrganization;
    
    private String cateCode;

    private String productCode;

    private String productName;

    private Long pv;

    private long inventory;

    private long orderedQty;
    
    private String dapp;

    private Boolean backOrder;
    
    private Double taxRate;

    private List<Price> prices;

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

    public long getInventory() {
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

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Boolean getBackOrder() {
        return backOrder;
    }

    public void setBackOrder(Boolean backOrder) {
        this.backOrder = backOrder;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    /**
     * 产品-价格.
     * 
     * @author fengwanjun
     *
     */
    public static class Price {
        
        private BigDecimal price;

        private String type;

        private String currency;
        
        private BigDecimal priceBeforeTax;

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public BigDecimal getPriceBeforeTax() {
            return priceBeforeTax;
        }

        public void setPriceBeforeTax(BigDecimal priceBeforeTax) {
            this.priceBeforeTax = priceBeforeTax;
        }
        
        
    }
}