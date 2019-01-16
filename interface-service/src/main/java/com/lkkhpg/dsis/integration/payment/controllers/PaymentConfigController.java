/*
 *
 */
package com.lkkhpg.dsis.integration.payment.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.integration.payment.constant.PaymentConstant;
import com.lkkhpg.dsis.integration.payment.dto.PaymentConfig;
import com.lkkhpg.dsis.integration.payment.exception.PaymentException;
import com.lkkhpg.dsis.integration.payment.service.IPaymentConfigService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 付款配置Controller.
 * 
 * @author mclin
 */

@Controller
public class PaymentConfigController extends BaseController {
    @Autowired
    private IPaymentConfigService paymentConfigService;

    /**
     * 查询 Migs 配置.
     * 
     * @param request
     *            上下文
     * @return ResponseData 返回数据
     */
    @RequestMapping(value = "/sys/payment/queryMigs")
    @ResponseBody
    public ResponseData queryMigs(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(paymentConfigService.queryPaymentConfigsByType(requestContext,
                PaymentConstant.PAYMENT_CFG_TYPE_MIGS));
    }

    /**
     * 查询 union 配置.
     * 
     * @param request
     *            上下文
     * @return ResponseData 返回数据
     */
    @RequestMapping(value = "/sys/payment/queryUnions")
    @ResponseBody
    public ResponseData queryUnions(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(paymentConfigService.queryPaymentConfigsByType(requestContext,
                PaymentConstant.PAYMENT_CFG_TYPE_UNION));
    }

    /**
     * 查询 non-uinon 配置.
     * 
     * @param request
     *            上下文
     * @return ResponseData 返回数据
     */
    @RequestMapping(value = "/sys/payment/queryNonUnions")
    @ResponseBody
    public ResponseData queryNonUnions(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(paymentConfigService.queryPaymentConfigsByType(requestContext,
                PaymentConstant.PAYMENT_CFG_TYPE_NONUNION));
    }

    /**
     * 更新配置信息.
     * 
     * @param paymentConfigs
     *            付款配置信息
     * @param result
     *            验证结果
     * @param request
     *            上下文
     * @return ResponseData 返回值
     * @throws PaymentException
     *             异常
     */
    @RequestMapping(value = "/sys/payment/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitLov(@RequestBody List<PaymentConfig> paymentConfigs, BindingResult result,
            HttpServletRequest request) throws PaymentException {

        getValidator().validate(paymentConfigs, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        paymentConfigService.batchUpdate(requestContext, paymentConfigs);
        return new ResponseData();
    }

    @RequestMapping(value = "/sys/payment/tf_pay_submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData paySubmit(@RequestBody List<PaymentConfig> paymentConfigs, BindingResult result,
                                  HttpServletRequest request) throws PaymentException {

        getValidator().validate(paymentConfigs, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        for (PaymentConfig config:
                paymentConfigs) {
            if(config.getConfigID()==null)
            config.setPaymentType(PaymentConstant.TF_PAYMENT_TYPE_URL);
        }
        paymentConfigService.insertUnit(requestContext, paymentConfigs);
        return new ResponseData();
    }

    @RequestMapping(value = "/sys/payment/down_load_submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitDownLoad(@RequestBody List<PaymentConfig> paymentConfigs, BindingResult result,
                                  HttpServletRequest request) throws PaymentException {
        getValidator().validate(paymentConfigs, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        for (PaymentConfig config:
             paymentConfigs) {
            if(config.getConfigID()==null)
            config.setPaymentType(PaymentConstant.TF_DOWNLOAD_ACCOUNT_TYPE_URL);
        }
        paymentConfigService.insertUnit(requestContext, paymentConfigs);
        return new ResponseData();
    }

    @RequestMapping(value = "/sys/payment/queryTFPAY")
    @ResponseBody
    public ResponseData queryTFPAY(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(paymentConfigService.queryPaymentConfigsByType(requestContext,
                PaymentConstant.TF_PAYMENT_TYPE_URL));
    }

    @RequestMapping(value = "/sys/payment/queryDownLoad")
    @ResponseBody
    public ResponseData queryDownLoad(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(paymentConfigService.queryPaymentConfigsByType(requestContext,
                PaymentConstant.TF_DOWNLOAD_ACCOUNT_TYPE_URL));
    }

}
