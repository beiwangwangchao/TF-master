/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.payment.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 中国信托银联支付查询结果.
 * @author shiliyan
 *
 */
public class ChinatrustUnionQueryResult extends PaymentTransactionModel{
    private String rtnCode;

    private List<ChinatrustUnionQueryModel> result = new ArrayList<ChinatrustUnionQueryModel>();

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public void addModel(ChinatrustUnionQueryModel model) {
        getResult().add(model);
    }

    public List<ChinatrustUnionQueryModel> getResult() {
        return result;
    }

    public void setResult(List<ChinatrustUnionQueryModel> result) {
        this.result = result;
    }

}
