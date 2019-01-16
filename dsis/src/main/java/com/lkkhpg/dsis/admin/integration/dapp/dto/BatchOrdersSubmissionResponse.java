/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 批量提交订单响应DTO.
 * 
 * @author shenqb
 *
 */
public class BatchOrdersSubmissionResponse {

    private String orderNo;

    private int result;

    private String description;

    private String appNo;

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
