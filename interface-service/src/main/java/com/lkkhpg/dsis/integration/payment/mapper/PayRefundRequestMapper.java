package com.lkkhpg.dsis.integration.payment.mapper;


import com.lkkhpg.dsis.integration.payment.dto.PayRefundRequest;

import java.util.List;

/**
 * tf退货Mapper.
 * Created by miaoyifan on 2017/12/6.
 */
public interface PayRefundRequestMapper{
    List<PayRefundRequest> selectPayRefund(PayRefundRequest payRefundRequest);
    void insert(PayRefundRequest payRefundRequest);
    Long selectOutTradeNo(PayRefundRequest payRefundRequest);
    void update(PayRefundRequest payRefundRequest);
    void deleteRefund(String out_trade_no);
    List<PayRefundRequest> queryPayRefundLov(PayRefundRequest payRefundRequest);
    /*退货单如果已入库 更新*/
    void update1(PayRefundRequest payRefundRequest);
}