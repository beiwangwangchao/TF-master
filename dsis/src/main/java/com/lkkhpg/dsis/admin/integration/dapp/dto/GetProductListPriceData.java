/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 产品价格列表DTO.
 * 
 * @author fengwanjun
 *
 */
public class GetProductListPriceData {
    
    private Double price;

    private String type;

    private String currency;
    
    private Long precision;
    
    private String disableTaxFlag;
    
    
    
    public Long getPrecision() {
        return precision;
    }

    public void setPrecision(Long precision) {
        this.precision = precision;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public String getDisableTaxFlag() {
        return disableTaxFlag;
    }

    public void setDisableTaxFlag(String disableTaxFlag) {
        this.disableTaxFlag = disableTaxFlag;
    }
    
    
}