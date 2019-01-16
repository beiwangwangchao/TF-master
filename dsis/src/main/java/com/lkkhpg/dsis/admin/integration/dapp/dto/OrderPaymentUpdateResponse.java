/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 订单支付接口返回数据 DTO.
 * 
 * @author zhenyang.he
 *
 */
public class OrderPaymentUpdateResponse {

    private String orderNumber;
    private Long result;
    private String description;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
