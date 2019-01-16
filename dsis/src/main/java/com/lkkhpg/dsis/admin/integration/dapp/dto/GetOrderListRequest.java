/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import javax.validation.constraints.NotNull;

/**
 * 查询订单列表DTO.
 * 
 * @author shenqb
 */
public class GetOrderListRequest {

    private String saleOrganization;

    @NotNull
    private String market;

    private String distributorNumber;

    private String startDate;

    private String endDate;

    private String updateStartDate;

    private String updateEndDate;

    private String orderStatus;

    private String shippingStatus;


    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getSaleOrganization() {
        return saleOrganization;
    }

    public void setSaleOrganization(String saleOrganization) {
        this.saleOrganization = saleOrganization;
    }

    public String getDistributorNumber() {
        return distributorNumber;
    }

    public void setDistributorNumber(String distributorNumber) {
        this.distributorNumber = distributorNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUpdateStartDate() {
        return updateStartDate;
    }

    public void setUpdateStartDate(String updateStartDate) {
        this.updateStartDate = updateStartDate;
    }

    public String getUpdateEndDate() {
        return updateEndDate;
    }

    public void setUpdateEndDate(String updateEndDate) {
        this.updateEndDate = updateEndDate;
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

}
