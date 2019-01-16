package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.util.Date;

/**
 * @author songyuanhuang 获取优惠券响应DTO
 */
public class GetCouponsResponse {

    private String couponCode;

    private String couponName;

    private String currency;

    private String status;

    private Double couponAmt;

    private Date effectDateFrom;

    private Date effectDateTo;

    private String usedFlag;

    private String distributorNumber;

    private String orderNumber;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getCouponAmt() {
        return couponAmt;
    }

    public void setCouponAmt(Double couponAmt) {
        this.couponAmt = couponAmt;
    }

    public Date getEffectDateFrom() {
        return effectDateFrom;
    }

    public void setEffectDateFrom(Date effectDateFrom) {
        this.effectDateFrom = effectDateFrom;
    }

    public Date getEffectDateTo() { return effectDateTo; }

    public void setEffectDateTo(Date effectDateTo) {
        this.effectDateTo = effectDateTo;
    }

    public String getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(String usedFlag) {
        this.usedFlag = usedFlag;
    }

    public String getDistributorNumber() {
        return distributorNumber;
    }

    public void setDistributorNumber(String distributorNumber) {
        this.distributorNumber = distributorNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


}
