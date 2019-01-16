/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import javax.validation.constraints.NotNull;

/**
 * 查询VIP pvDTO.
 * 
 * @author shenqb
 */
public class GetVipPvRequest {
    @NotNull
    private String VIPNumber;

    private String dateFrom;

    private String dateTo;

    public String getVIPNumber() {
        return VIPNumber;
    }

    public void setVIPNumber(String vIPNumber) {
        VIPNumber = vIPNumber;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    

    

}
