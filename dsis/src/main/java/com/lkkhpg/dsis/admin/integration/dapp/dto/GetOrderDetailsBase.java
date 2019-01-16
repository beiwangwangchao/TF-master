/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 获取订单详情接口基础DTO.
 * 
 * @author shenqb
 *
 */
public class GetOrderDetailsBase {

    @NotNull
    private String appNo;

    @NotNull
    private String saleOrganization;

    @NotNull
    private String market;

    @NotNull
    private String distributorNumber;

    @NotNull
    private String orderDate;

    @NotNull
    private String deliveryCountry;

    @NotNull
    private String deliveryState;

    @NotNull
    private String deliveryCity;

    @NotNull
    private String deliveryAddress;

    private String deliveryAddress2;

    private String deliveryAddress3;

    private String deliveryZipCode;

    @NotNull
    private String deliveryReceiver;

    private String deliveryAreaCode;

    @NotNull
    private String deliveryPhone;

    @NotNull
    private String billingCountry;

    @NotNull
    private String billingState;

    @NotNull
    private String billingCity;

    @NotNull
    private String billingAddress;

    private String billingAddress2;

    private String billingAddress3;

    private String billingZipCode;

    @NotNull
    private String billingReceiver;

    private String billingAreaCode;

    @NotNull
    private String billingPhone;

    @NotNull
    private String mailing;

    @NotNull
    private String currency;

    private BigDecimal shippingFee;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String salesType;

    private String orderStatus;

    private String shippingStatus;

    private List<Coupons> coupons;
    @NotNull
    private String orderType;

    @NotNull
    private List<Product> products;

    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    public void setDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getDeliveryAddress3() {
        return deliveryAddress3;
    }

    public void setDeliveryAddress3(String deliveryAddress3) {
        this.deliveryAddress3 = deliveryAddress3;
    }

    public String getBillingAddress3() {
        return billingAddress3;
    }

    public void setBillingAddress3(String billingAddress3) {
        this.billingAddress3 = billingAddress3;
    }

    public String getSaleOrganization() {
        return saleOrganization;
    }

    public void setSaleOrganization(String saleOrganization) {
        this.saleOrganization = saleOrganization;
    }

    // public String getCompanyCode() {
    // return companyCode;
    // }
    //
    // public void setCompanyCode(String companyCode) {
    // this.companyCode = companyCode;
    // }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getDistributorNumber() {
        return distributorNumber;
    }

    public void setDistributorNumber(String distributorNumber) {
        this.distributorNumber = distributorNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryAddress2() {
        return deliveryAddress2;
    }

    public void setDeliveryAddress2(String deliveryAddress2) {
        this.deliveryAddress2 = deliveryAddress2;
    }

    public String getDeliveryZipCode() {
        return deliveryZipCode;
    }

    public void setDeliveryZipCode(String deliveryZipCode) {
        this.deliveryZipCode = deliveryZipCode;
    }

    public String getDeliveryReceiver() {
        return deliveryReceiver;
    }

    public void setDeliveryReceiver(String deliveryReceiver) {
        this.deliveryReceiver = deliveryReceiver;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getDeliveryAreaCode() {
        return deliveryAreaCode;
    }

    public void setDeliveryAreaCode(String deliveryAreaCode) {
        this.deliveryAreaCode = deliveryAreaCode;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingAddress2() {
        return billingAddress2;
    }

    public void setBillingAddress2(String billingAddress2) {
        this.billingAddress2 = billingAddress2;
    }

    public String getBillingZipCode() {
        return billingZipCode;
    }

    public void setBillingZipCode(String billingZipCode) {
        this.billingZipCode = billingZipCode;
    }

    public String getBillingReceiver() {
        return billingReceiver;
    }

    public void setBillingReceiver(String billingReceiver) {
        this.billingReceiver = billingReceiver;
    }

    public String getBillingPhone() {
        return billingPhone;
    }

    public void setBillingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
    }

    public String getBillingAreaCode() {
        return billingAreaCode;
    }

    public void setBillingAreaCode(String billingAreaCode) {
        this.billingAreaCode = billingAreaCode;
    }

    public String getMailing() {
        return mailing;
    }

    public void setMailing(String mailing) {
        this.mailing = mailing;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public List<Coupons> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupons> coupons) {
        this.coupons = coupons;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    
}
