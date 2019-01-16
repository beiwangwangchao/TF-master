/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.math.BigDecimal;

/**
 * dapp 优惠券DTO.
 * 
 * @author shenqb
 *
 */
public class Coupons {

    private String couponCode;
    
    private BigDecimal couponAmt;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public BigDecimal getCouponAmt() {
        return couponAmt;
    }

    public void setCouponAmt(BigDecimal couponAmt) {
        this.couponAmt = couponAmt;
    }

    
}
