/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 订单作废响应DTO.
 * 
 * @author fengwanjun
 */
public class OrderCancelResponse {

    private String orderNumber;
    
    private String result;
    
    private String description;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}