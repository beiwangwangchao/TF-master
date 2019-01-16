/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 订单行商品DTO.
 * 
 * @author shenqb
 *
 */
public class Product {

    @NotNull
    private String productCode;
    @NotNull
    private BigDecimal quantity;

    private BigDecimal price;

    private BigDecimal priceBeforeTax;
    
    private BigDecimal productAmount;

    private BigDecimal pv;
    
    private String shippingStatus;
    
    private String productName;

    private BigDecimal refundQty;

    private BigDecimal cancelQty;

    private BigDecimal shippedQty;

    private List<Coupons> coupons;


    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public BigDecimal getRefundQty() {
        return refundQty;
    }

    public void setRefundQty(BigDecimal refundQty) {
        this.refundQty = refundQty;
    }

    public BigDecimal getCancelQty() {
        return cancelQty;
    }

    public void setCancelQty(BigDecimal cancelQty) {
        this.cancelQty = cancelQty;
    }

    public BigDecimal getShippedQty() {
        return shippedQty;
    }

    public void setShippedQty(BigDecimal shippedQty) {
        this.shippedQty = shippedQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceBeforeTax() {
        return priceBeforeTax;
    }

    public void setPriceBeforeTax(BigDecimal priceBeforeTax) {
        this.priceBeforeTax = priceBeforeTax;
    }

    public BigDecimal getPv() {
        return pv;
    }

    public void setPv(BigDecimal pv) {
        this.pv = pv;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public List<Coupons> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupons> coupons) {
        this.coupons = coupons;
    }

}
