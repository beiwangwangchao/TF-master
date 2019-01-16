/**
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.PaymentConstants;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.OrderPayTransaction;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.OrderPayTransactionMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.service.IOrderPayTransactionService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * @author runbai.chen
 */
@Service
@Transactional
public class OrderPayTransactionServiceImpl implements IOrderPayTransactionService {
    @Autowired
    private OrderPayTransactionMapper orderPayTransactionMapper;
    @Autowired
    private IDocSequenceService docSequenceServie;
    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Override
    public OrderPayTransaction generateTransaction(IRequest request, OrderPayTransaction orderPayTransaction)
            throws CommOrderException {
        SalesOrder salesOrder = salesOrderMapper.selectByPrimaryKeyOnly(orderPayTransaction.getSourceKey());
        if (!(salesOrder.getOrderStatus().equals(OrderConstants.SALES_STATUS_WAIT_PAYMENT))) {
            throw new CommOrderException(CommOrderException.MSG_ERROR_NOT_ALLOW_PAY, null);
        }

        // 生成事务处理code
        SimpleDateFormat sdf = new SimpleDateFormat(OrderConstants.GENERATECODE_DATE_FORMAT);
        String date = sdf.format(new Date());
        /*
         * DocSequence docSequence = new
         * DocSequence(PaymentConstants.SALES_ORDER_PAYMENT, date, null, null,
         * null, null); String transCode =
         * docSequenceServie.getSequence(request, docSequence, "TRANS" + date,
         * OrderConstants.SALESORDER_DIGIT, OrderConstants.BEGIN_NUM);
         */
        DocSequence docSequence = new DocSequence(PaymentConstants.SALES_ORDER_PAYMENT,
                salesOrder.getHeaderId().toString(), null, null, null, null);
        String transCode = docSequenceServie.getSequence(request, docSequence, salesOrder.getOrderNumber(),
                OrderConstants.BATCH_DIGIT, OrderConstants.BEGIN_NUM);
        orderPayTransaction.setTransactionCode(transCode);

        orderPayTransaction.setTransactionStatus(PaymentConstants.TRANS_STATUS_DRAFT);
        orderPayTransaction.setTransactionType(PaymentConstants.TRANS_TYPE_PAY);

        orderPayTransactionMapper.insert(orderPayTransaction);

        return orderPayTransaction;
    }

    @Override
    public OrderPayTransaction lauchTransaction(IRequest request, OrderPayTransaction orderPayTransaction) {

        orderPayTransaction.setTransactionType(PaymentConstants.TRANS_TYPE_PAY);
        orderPayTransaction.setTransactionStatus(PaymentConstants.TRANS_STATUS_START);
        orderPayTransactionMapper.updateTransactionByTransCode(orderPayTransaction);

        return orderPayTransaction;
    }

    @Override
    public OrderPayTransaction finishTransaction(IRequest request, OrderPayTransaction orderPayTransaction) {
        // 获取事务里支付信息
        OrderPayTransaction orderPayTransaction1 = orderPayTransactionMapper
                .selectTransactionByTransCode(orderPayTransaction);
        orderPayTransaction.setTransactionType(PaymentConstants.TRANS_TYPE_RESUL);
        // 获取事务里支付结果信息
        OrderPayTransaction orderPayTransaction2 = orderPayTransactionMapper
                .selectTransactionByTransCode(orderPayTransaction);
        orderPayTransaction.setSourceKey(orderPayTransaction1.getSourceKey());
        orderPayTransaction.setSourceType(orderPayTransaction1.getSourceType());
        orderPayTransaction.setBankPaymentCode(orderPayTransaction1.getBankPaymentCode());
        if (orderPayTransaction2 != null) {
            orderPayTransactionMapper.updateTransactionByTransCode(orderPayTransaction);

        } else {
            orderPayTransactionMapper.insert(orderPayTransaction);
        }
        return orderPayTransaction;
    }

}
