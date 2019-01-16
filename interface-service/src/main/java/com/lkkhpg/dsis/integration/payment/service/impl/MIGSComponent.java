/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.integration.payment.service.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.integration.payment.configration.MIGSConfigration;
import com.lkkhpg.dsis.integration.payment.dto.MIGSCallbackModel;
import com.lkkhpg.dsis.integration.payment.dto.MIGSModel;
import com.lkkhpg.dsis.integration.payment.dto.MIGSQueryModel;
import com.lkkhpg.dsis.integration.payment.dto.PaymentOrder;
import com.lkkhpg.dsis.integration.payment.dto.PaymentResult;
import com.lkkhpg.dsis.integration.payment.exception.PaymentException;
import com.lkkhpg.dsis.integration.payment.service.IMIGSPaymentService;

/**
 * @author liyan.shi@hand-china.com
 */
@Component
public class MIGSComponent extends PaymentComponent {

    private static final String MIGS_CALLBACK = "migs_callback";

    private static final String PAYMENT_MIGS_CONTROLLER_PAY_DECODE = "payment.migs.controller.pay.decode {}";

    private static final String PAYMENT_MIGS_CONTROLLER_PAY_CALLBACK = "payment.migs.controller.pay.callback {}";

    private static final String PAYMENT_MIGS_CONTROLLER_PAY_SUBMIT = "payment.migs.controller.pay.submit {}";

    private static final String PAYMENT_MIGS_CONTROLLER_PAY_ENCODE = "payment.migs.controller.pay.encode {}";

    private Logger logger = LoggerFactory.getLogger(MIGSComponent.class);

    @Autowired
    private IMIGSPaymentService migsPaymentService;

    @Autowired
    private MIGSConfigration migsConfigration;

    public Object migsPay(HttpServletRequest request, MIGSModel model) {
        ModelAndView view = new ModelAndView();
        model = initModel(model);
        view.addObject(SOURCE_KEY, model.getSourceKey1());
        view.addObject(SOURCE_TYPE, model.getSourceType());
        model.setVpc_Command(MIGSConfigration.VPC_COMMAND_PAY);
        MIGSModel _model = model;
        this.info(PAYMENT_MIGS_CONTROLLER_PAY_ENCODE, logger, model.getVpc_OrderInfo());
        model = migsPaymentService.pay(model);
        if (model == null) {
            _model.settRequestUrl(getAction());
            this.tpay(_model, true);
            if (logger.isErrorEnabled()) {
                logger.error(PaymentException.MIGS_PAY_ENCODE_ERR);
            }

            return paymentErr(request, PaymentException.MIGS_PAY_ENCODE_ERR, _model.getVpc_MerchTxnRef());
        }
        view.setViewName(this.getMigsPaySubmitUrl());
        view.addObject(ACTION, getAction());
        view.addObject(MODEL, model);
        view.addObject("payType", "MIGS");
        this.info(PAYMENT_MIGS_CONTROLLER_PAY_SUBMIT, logger, model.getVpc_OrderInfo());
        model.settRequestUrl(getAction());
        this.tpay(model);
        return view;
    }

    public String getAction() {
        return migsConfigration.getAction();
    }

    public MIGSModel initModel(MIGSModel model) {
        model.setVpc_AccessCode(migsConfigration.getAccessCode());
        // 1、 vpc_OrderInfo字段改为用 vpc_MerchTxnRef；
        // model.setVpc_MerchTxnRef(migsConfigration.getMerchtxnRef());
        model.setVpc_MerchTxnRef(model.getVpc_OrderInfo());
        // migsConfigration.setMerchtxnRef(model.getVpc_OrderInfo());
        model.setVpc_Merchant(migsConfigration.getMerchantId());
        model.setVpc_ReturnURL(getReturnUrl());
        model.setVpc_SecureHashType(this.migsConfigration.getVpcSecureHashType());
        return model;
    }

    public ModelAndView migsPayCallback(HttpServletRequest request, MIGSCallbackModel para) {
        this.info(PAYMENT_MIGS_CONTROLLER_PAY_CALLBACK, logger, para.getVpc_OrderInfo());
        Map<String, String> fields = new HashMap<String, String>();
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
            String key = e.nextElement();
            String value = request.getParameter(key);
            fields.put(key, value);
        }

        ModelAndView view = new ModelAndView();
        MIGSCallbackModel callback = null;
        try {
            this.info(PAYMENT_MIGS_CONTROLLER_PAY_DECODE, logger, para.getVpc_OrderInfo());

            callback = migsPaymentService.callback(para, fields);
        } catch (PaymentException e) {
            if (logger.isErrorEnabled()) {
                logger.error(PaymentException.MIGS_PAY_CALLBACK_DECODE_ERR, para.getVpc_OrderInfo());
            }
            para.settRequestUrl(getReturnUrl());
            this.tcallback(para, true);
            return this.paymentErr(request, PaymentException.MIGS_PAY_CALLBACK_DECODE_M, para.getVpc_OrderInfo(),
                    para.getVpc_OrderInfo());
        }
        view.setViewName(this.getResultUrl());

        PaymentResult model = makeResult(callback);
        view.addObject(MODEL, model);
        callback.settRequestUrl(getReturnUrl());
        this.tcallback(callback);
        this.info(PAYMENT_MIGS_CONTROLLER_PAY_CALLBACK, logger, para.getVpc_OrderInfo());
        return view;
    }

    public String getResultStatus(String vpc_TxnResponseCode, String vpc_AcqResponseCode) {
        // callback.getVpc_Message(); = Approved
        // .getVpc_TxnResponseCode() = "0";
        // Approved or completed successfully

        String _0 = migsConfigration.getTxnResponseCode();
        String _00 = migsConfigration.getAcqResponseCode();
        return _0.equals(vpc_TxnResponseCode) && _00.equals(vpc_AcqResponseCode) ? SUCCESS : FAILED;
    }

    public PaymentResult makeResult(MIGSCallbackModel callback) {
        PaymentResult model = new PaymentResult();

        model.setAuthAmt(callback.getVpc_Amount());
        model.setLidm(callback.getVpc_OrderInfo());
        String vpc_TxnResponseCode = callback.getVpc_TxnResponseCode();
        String vpc_AcqResponseCode = callback.getVpc_AcqResponseCode();
        model.setRespCode(vpc_TxnResponseCode);
        model.setStatus(getResultStatus(vpc_TxnResponseCode, vpc_AcqResponseCode));
        model.setAuthCode(callback.getVpc_AuthorizeId());
//    is MC    model.setLast4digitPAN(callback.getVpc_Card());
        model.setPayType(MIGS_CALLBACK);
        // model.setXid(callback.getVpc_3DSXID());
        model.setXid(callback.getVpc_TransactionNo());
        model.setErrCode(vpc_AcqResponseCode);
        String orderStatus = callback.getVpc_3DSstatus();
        model.setOrderStatus(orderStatus);
        model.setResult(callback);
        return model;
    }

    public String getReturnUrl() {
        return migsConfigration.getVpcReturnUrl();
    }

    public MIGSQueryModel query(MIGSModel para) {
        MIGSQueryModel query = migsPaymentService.query(para);
        query.setSourceKey1(para.getSourceKey1());
        query.setSourceType(para.getSourceType());
        return query;
    }

    public PaymentResult query(HttpServletRequest request, PaymentOrder order) {
        return query(order);
    }

    public PaymentResult query(PaymentOrder order) {
        MIGSModel migs = new MIGSModel();
        migs.setVpc_OrderInfo(order.getOrderNumber());
        migs.setSourceKey1(String.valueOf(order.getKey()));
        migs.setSourceType(order.getSourceType() == null ? ORDER : order.getSourceType());
        migs.setVpc_Amount(String.valueOf(order.getAmount()));
        // 1、 vpc_OrderInfo字段改为用 vpc_MerchTxnRef；
        migs.setVpc_MerchTxnRef(order.getOrderNumber());
        MIGSQueryModel query = query(migs);
        PaymentResult model = makeResult(query);
        return model;
    }

    public PaymentResult makeResult(MIGSQueryModel query) {
        PaymentResult model = new PaymentResult();

        model.setAuthAmt(query.getVpc_Amount());
        model.setLidm(query.getOrderInfo());
        // query.getAcqResponseCode() = 00
        // callback.getVpc_Message(); = Approved
        // .getVpc_TxnResponseCode() = "0";
        // Approved or completed successfully
        String vpc_TxnResponseCode = query.getTxnResponseCode();
        String acqResponseCode = query.getAcqResponseCode();
        model.setRespCode(vpc_TxnResponseCode);
        model.setStatus(getResultStatus(vpc_TxnResponseCode, acqResponseCode));
        // success
        // if (PaymentComponent._0.equals(vpc_TxnResponseCode)) {
        // model.setStatus(PaymentComponent.SUCCESS);
        // } else {
        // model.setStatus(PaymentComponent.FAILED);
        // }
        model.setAuthCode(query.getAuthorizeID());
        model.setPayType(MIGS_QUERY);
        model.setXid(query.getTransactionNo());
        model.setErrCode(acqResponseCode);
        String orderStatus = query.getStatus3DS();
        model.setOrderStatus(orderStatus);
        model.setResult(query);
        return model;
    }

}
