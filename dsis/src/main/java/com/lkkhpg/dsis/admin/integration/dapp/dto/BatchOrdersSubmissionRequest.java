/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 批量提交订单请求DTO.
 * 
 * @author shenqb
 *
 */
public class BatchOrdersSubmissionRequest {
//    @NotNull
//    private String lang;
   /* @NotNull
    private String market;*/

    private String username;
    @NotNull
    private List<OrderSubmission> orders;
    
    private String sourceOrderNo;

//    public String getLang() {
//        return lang;
//    }
//
//    public void setLang(String lang) {
//        this.lang = lang;
//    }

/*    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }*/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderSubmission> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderSubmission> orders) {
        this.orders = orders;
    }

    public String getSourceOrderNo() {
        return sourceOrderNo;
    }

    public void setSourceOrderNo(String sourceOrderNo) {
        this.sourceOrderNo = sourceOrderNo;
    }

    
    
    

}
