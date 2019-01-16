package com.lkkhpg.dsis.integration.payment.mapper;


import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.integration.payment.dto.TianFuPaymentTransaction;


import java.util.List;

import java.util.Map;


public interface TianFuPaymentTransactionMapper{

    void insertUnit(TianFuPaymentTransaction transaction);

    TianFuPaymentTransaction selectByPrimaryKey(TianFuPaymentTransaction transaction);

    void deleteByPrimaryKey(TianFuPaymentTransaction transaction);

    void updateStatus(Map maps);

    Long  queryByOrderNumber(String orderNumber);

    void updateOrders(SalesOrder orders);

    void  updateByPrimaryKeySelective(Map maps);

    String selectList(String appCode);

    List selectPartner(String outTradeNo);
}