/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.integration.payment.service.impl;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkkhpg.dsis.integration.payment.configration.IPaymentLogger;
import com.lkkhpg.dsis.integration.payment.configration.PaymentConfigration;
import com.lkkhpg.dsis.integration.payment.dto.PaymentTransactionModel;
import com.lkkhpg.dsis.integration.payment.mapper.PaymentTransactionMapper;

/**
 * @author liyan.shi@hand-china.com
 */

public class PaymentComponent implements IPaymentLogger {
    public static final String FAILED = "FAILED";

    public static final String SUCCESS = "SUCCESS";

    public static final String PAYING = "PAYING";

    public static final String SOURCE_TYPE = "sourceType";

    public static final String SOURCE_KEY = "sourceKey";

    public static final String MIGS_QUERY = "MIGS_QUERY";

    public static final String NON_UNION_QUERY = "NON_UNION_QUERY";

    public static final String UNION_QUERY = "UNION_QUERY";
    public static final String NONE = "NONE";

    public static final String ORDER = "ORDER";

    // public static final String _0 = "0";
    // "query";
    private static final String QUERY = "Q";

    // "refund";
    private static final String REFUND = "RF";

    // "mac";
    private static final String MAC = "MAC";

    // "callback";
    private static final String CALLBACK = "CB";

    // "callback";
    private static final String CALLBACK_BACKEND = "CBE";

    // "pay";
    private static final String PAY = "PAY";

    // public static final String URL_CHINATRUST_UNION_PAY_SUBMIT =
    // "admin/payment/chinatrust_unionpay";
    //
    // public static final String URL_MIGS_PAY_SUBMIT =
    // "admin/payment/migs_submit";
    //
    // public static final String URL_CHINATRUST_NON_UNION_PAY_SUBMIT =
    // "admin/payment/chinatrust_non_unionpay";
    //
    // public static final String URL_PAYMENT_RESULT =
    // "admin/payment/payment_result";
    //
    // public static final String URL_PAYMENT_ERROR =
    // "admin/payment/payment_error";

    public static final String MODEL = "model";

    public static final String ACTION = "action";

    public static final String MESSAGE = "message";

    public static final String ERR_CODE = "errCode";

    @Autowired
    private PaymentConfigration paymentConfigration;

    public String getUnionPaySubmitUrl() {
        return paymentConfigration.getUnionPaySubmitUrl();
    }

    public String getNonUnionPaySubmitUrl() {
        return paymentConfigration.getNonUnionPaySubmitUrl();
    }

    public String getMigsPaySubmitUrl() {
        return paymentConfigration.getMigsPaySubmitUrl();
    }

    public String getResultUrl() {
        return paymentConfigration.getResultUrl();
    }

    public String getErrorUrl() {
        return paymentConfigration.getErrorUrl();
    }

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PaymentTransactionMapper paymentTransationMapper;

    protected String m(HttpServletRequest request, String code, Object... args) {
        Locale locale = RequestContextUtils.getLocale(request);
        return messageSource.getMessage(code, args, code, locale);
    }

    protected ModelAndView paymentErr(HttpServletRequest request, String code, String order, Object... args) {
        ModelAndView view = new ModelAndView();
        view.setViewName(this.getErrorUrl());
        view.addObject(ERR_CODE, code);
        String m = m(request, code, args);
        view.addObject(MESSAGE, m);
        // PaymentResult result = new PaymentResult();
        // result.setErrCode(code);
        // result.setResult(result);
        view.addObject("orderNumber", order);
        return view;
    }

    public void tpay(PaymentTransactionModel model, boolean isFailed) {
        if (isFailed) {
            model.settStatus(PaymentTransactionModel.FAILED);
        }
        tpay(model);
    }

    public void tquery(PaymentTransactionModel model, boolean isFailed) {
        if (isFailed) {
            model.settStatus(PaymentTransactionModel.FAILED);
        }
        tquery(model);
    }

    public void tcallback(PaymentTransactionModel model, boolean isFailed) {
        if (isFailed) {
            model.settStatus(PaymentTransactionModel.FAILED);
        }
        tcallback(model);
    }

    public void trefund(PaymentTransactionModel model, boolean isFailed) {
        if (isFailed) {
            model.settStatus(PaymentTransactionModel.FAILED);
        }
        trefund(model);
    }

    public void tcallback(PaymentTransactionModel model) {
        model.setSourceKey1("-1");
        model.setTransactionType(PAY);
        model.settPhase(CALLBACK);
        save(model);
    }

    public void tcallbackBackend(PaymentTransactionModel model) {
        model.setSourceKey1("-1");
        model.setTransactionType(PAY);
        model.settPhase(CALLBACK_BACKEND);
        save(model);
    }

    private void save(PaymentTransactionModel model) {
        // model.setSourceKey1("111");
        String sourceKey1 = model.getSourceKey1();
        Assert.notNull(sourceKey1);
        model.settMessage(toMessage(model));
        paymentTransationMapper.insert(model);
    }

    private String toMessage(PaymentTransactionModel model) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String writeValueAsString = mapper.writeValueAsString(model);
            return writeValueAsString;
        } catch (JsonProcessingException e) {
        }

        return "";
    }

    public void tpay(PaymentTransactionModel model) {
        model.setTransactionType(PAY);
        model.settPhase(MAC);
        save(model);
    }

    public void tquery(PaymentTransactionModel model) {
        model.setTransactionType(QUERY);
        model.settPhase(QUERY);
        save(model);
    }

    public void trefund(PaymentTransactionModel model) {
        model.setTransactionType(REFUND);
        model.settPhase(REFUND);
        save(model);
    }

    // {
    //
    //
    //
    //// migs/callback
    // //service1
    //// nonenunion/callback
    // //service2
    // //union/callback
    // //service3
    // //union/callback/backend
    //// service4
    //
    // }
    //
    //
    // {
    // IOrderPaymentService.pay(paymentCode,orderNumber,amount,key,source_type,callbackUrl)
    // paymentCode
    //// order,amount
    // //kkkkk,
    ////
    //
    //// orderPayServe
    //// source_type
    //
    // switch ("") {
    // case "MIGS":
    //
    // //order_info
    // //
    //// migsService.p
    // break;
    // case "Union":
    //// unionservice.
    // break;
    // case "None":
    //// noneservice
    // break;
    //
    // default:
    // break;
    // }
    //
    // }

}
