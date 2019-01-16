package com.lkkhpg.dsis.integration.payment.mapper;

import com.lkkhpg.dsis.integration.payment.dto.TianFuPaymentResult;

import java.util.List;

public interface TianFuPaymentResultMapper {
    List selectUnit(String out_trade_no);

    void insert(TianFuPaymentResult result);

//    void deleteRefund(String out_trade_no);

    TianFuPaymentResult selectByPrimaryKey(String orderNumber);


}