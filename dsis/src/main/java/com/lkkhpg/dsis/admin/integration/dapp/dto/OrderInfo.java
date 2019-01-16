/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 订单callback参数 DTO.
 * 
 * @author shenqb
 */
public class OrderInfo {

    private String orderID;
    
    private String market;
    
    private String orderType;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    
    
    
}
