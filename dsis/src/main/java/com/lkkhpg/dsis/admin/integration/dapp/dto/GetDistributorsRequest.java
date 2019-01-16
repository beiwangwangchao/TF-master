/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import javax.validation.constraints.NotNull;

/**
 * 获取业务员列表请求DTO.
 * 
 * @author linyuheng
 */
public class GetDistributorsRequest {
    @NotNull
    private String market;

    private String startDate;

    private String endDate;

    private String updateStartDate;

    private String updateEndDate;

    private int maxPerpage;

    private int pageNo;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
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

    public int getMaxPerpage() {
        return maxPerpage;
    }

    public void setMaxPerpage(int maxPerpage) {
        this.maxPerpage = maxPerpage;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

}
