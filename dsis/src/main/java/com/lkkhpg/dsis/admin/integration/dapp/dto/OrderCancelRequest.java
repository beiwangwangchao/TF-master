/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import javax.validation.constraints.NotNull;

/**
 * 订单作废请求DTO.
 * 
 * @author fengwanjun
 */
public class OrderCancelRequest {

    @NotNull
    private String orderNumber;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

}