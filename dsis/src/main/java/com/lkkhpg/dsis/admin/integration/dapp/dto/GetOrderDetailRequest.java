/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import javax.validation.constraints.NotNull;

/**
 * 查询订单详情DTO.
 * 
 * @author shenqb
 */
public class GetOrderDetailRequest {
    @NotNull
    private String market;
    @NotNull
    private String orderNumber;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

}
