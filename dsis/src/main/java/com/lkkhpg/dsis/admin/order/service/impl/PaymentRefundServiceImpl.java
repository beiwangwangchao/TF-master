/*
 *
 */
package com.lkkhpg.dsis.admin.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.order.service.IPaymentRefundService;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.PaymentRefund;
import com.lkkhpg.dsis.common.service.ICommPaymentRefundService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 支付退款接口实现类.
 * 
 * @author gulin
 *
 */
@Service
@Transactional
public class PaymentRefundServiceImpl implements IPaymentRefundService {

    @Autowired
    private ICommPaymentRefundService commPaymentRefundService;

    @Override
    public List<PaymentRefund> queryPaymentByHeaderId(IRequest request, Long headerId) {
        return commPaymentRefundService.queryPaymentByHeaderId(request, headerId);
    }
    
    @Override
    public List<PaymentRefund> queryPayOrRefundByHeaderId(IRequest request, Long headerId) {
        return commPaymentRefundService.queryPayOrRefundByHeaderId(request, headerId);
    }

    @Override
    public void insertPaymentRefund(IRequest request, PaymentRefund paymentRefund) {
        commPaymentRefundService.insertPaymentRefund(request, paymentRefund);
    }

    @Override
    public boolean isInvalid(IRequest request, Long headerId) {
        return commPaymentRefundService.isInvalid(request, headerId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean checkRefundInvalid(IRequest request, Long headerId, List<PaymentRefund> paymentRefunds)
            throws CommOrderException {
        return commPaymentRefundService.checkRefundInvalid(request, headerId, paymentRefunds);
    }

    @Override
    public List<String> queryOrderSpmRefund(IRequest request, Long headerId) {
        return commPaymentRefundService.queryOrderSpmRefund(request, headerId);
    }

}
