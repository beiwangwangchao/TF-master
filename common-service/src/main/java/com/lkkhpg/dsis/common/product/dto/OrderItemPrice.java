package com.lkkhpg.dsis.common.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItemPrice implements Serializable {
    private static final long serialVersionUID = 5489206440998365876L;

    /**
     * 销售价格（金额 及积分）.
     */
    private BigDecimal salesPrice;
    
    /**
     * 销售价格（金额 及积分）.
     */
    private String salesPriceType;

    /**
     * 计税价格.
     */
    private BigDecimal taxPrice;
    
    /**
     * 计税价格类型.
     */
    private String taxPriceType;

    /**
     * 销售积分.
     */
    private BigDecimal pv;

    /**
     * 是否计税 Y=不计税、N=计税
     */
    private String disableFlag;

    public OrderItemPrice(BigDecimal salesPrice, BigDecimal taxPrice, BigDecimal pv, String disableFlag) {
        this.salesPrice = salesPrice;
        this.taxPrice = taxPrice;
        this.pv = pv;
        this.disableFlag = disableFlag;
    }

    public OrderItemPrice() {

    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public String getSalesPriceType() {
        return salesPriceType;
    }

    public void setSalesPriceType(String salesPriceType) {
        this.salesPriceType = salesPriceType;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public String getTaxPriceType() {
        return taxPriceType;
    }

    public void setTaxPriceType(String taxPriceType) {
        this.taxPriceType = taxPriceType;
    }

    public BigDecimal getPv() {
        return pv;
    }

    public void setPv(BigDecimal pv) {
        this.pv = pv;
    }

    public String getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(String disableFlag) {
        this.disableFlag = disableFlag;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }



}
