/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.math.BigDecimal;

/**
 * 查询订单详情响应DTO.
 * 
 * @author shenqb
 *
 */
public class GetOrderDetailResponse extends GetOrderDetailsBase {
    private String orderNumber;
    private String orderStatus;
    private String shippingStatus;
    
    private BigDecimal taxRate;
    
    
    
    public String getShippingStatus() {
        return shippingStatus;
    }
    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public BigDecimal getTaxRate() {
        return taxRate;
    }
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
    
    
}
