package com.lkkhpg.dsis.integration.payment.service;


import com.lkkhpg.dsis.common.delivery.dto.OrderDelivery;
import com.lkkhpg.dsis.integration.payment.dto.ReportRefunds;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.integration.payment.dto.PayRefundRequest;

import java.util.List;

/**
 * Created by miaoyifan on 2017/12/6.
 */
public interface IPayRefundRequestService extends ProxySelf<IPayRefundRequestService> {
    List<PayRefundRequest> select(IRequest request, PayRefundRequest payRefundRequest, int page, int pagesize);
    String payRefundquest(PayRefundRequest payRefundRequest)throws Exception;
    boolean payRefundResult(PayRefundRequest payRefundRequest)throws Exception;
    boolean deleteRefund(String out_trade_no);
    List<OrderDelivery>queryDeliveryStatus(String orderNumber)throws Exception;
    List<ReportRefunds>reportRefunds(IRequest request, ReportRefunds reportRefunds, int page, int pagesize);
}
