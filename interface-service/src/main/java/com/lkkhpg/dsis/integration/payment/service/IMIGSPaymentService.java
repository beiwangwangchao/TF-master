/*
 *
 */
package com.lkkhpg.dsis.integration.payment.service;

import java.util.Map;

import com.lkkhpg.dsis.integration.payment.dto.MIGSCallbackModel;
import com.lkkhpg.dsis.integration.payment.dto.MIGSModel;
import com.lkkhpg.dsis.integration.payment.dto.MIGSQueryModel;
import com.lkkhpg.dsis.integration.payment.dto.MIGSRefundModel;
import com.lkkhpg.dsis.integration.payment.exception.PaymentException;

/**
 * MIGS支付Service.
 * 
 * @author shiliyan
 *
 */
public interface IMIGSPaymentService {

    /**
     * 
     * migs支付.
     * 
     * @param model
     *            model
     * @return MIGSModel
     */
    MIGSModel pay(MIGSModel model);

    /**
     * migs 查询.
     * 
     * @param model
     *            model
     * @return MIGSModel
     */
    MIGSQueryModel query(MIGSModel model);

    /**
     * migs支付回调函数.
     * 
     * @param model
     *            model
     * @param fields
     * @return MIGSCallbackModel
     * @throws PaymentException
     *             签名验证错误时
     * 
     */
    MIGSCallbackModel callback(MIGSCallbackModel model, Map<String, String> fields) throws PaymentException;

    MIGSRefundModel refund(MIGSCallbackModel model);

}
