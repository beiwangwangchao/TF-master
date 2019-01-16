/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import javax.validation.constraints.NotNull;

/**
 * 查询VIP消费总额请求DTO.
 * 
 * @author shenqb
 */
public class GetVipPurchaseAmountRequest {
    @NotNull
    private String VIPNumber;

    public String getVIPNumber() {
        return VIPNumber;
    }

    public void setVIPNumber(String vIPNumber) {
        VIPNumber = vIPNumber;
    }

}
