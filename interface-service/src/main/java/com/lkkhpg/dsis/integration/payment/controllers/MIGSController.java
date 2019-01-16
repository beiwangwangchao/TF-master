/*
 *
 */

package com.lkkhpg.dsis.integration.payment.controllers;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.integration.payment.dto.MIGSCallbackModel;
import com.lkkhpg.dsis.integration.payment.dto.MIGSModel;
import com.lkkhpg.dsis.integration.payment.dto.PaymentOrder;
import com.lkkhpg.dsis.integration.payment.dto.PaymentResult;
import com.lkkhpg.dsis.integration.payment.service.impl.MIGSComponent;
import com.lkkhpg.dsis.integration.payment.service.impl.PaymentComponent;

/**
 * @author liyan.shi@hand-china.com
 */
@Controller
public class MIGSController {

    @Autowired
    private MIGSComponent migsComponent;

    @RequestMapping("/payment/pay/migs")
    public Object migsPay(HttpServletRequest request, String orderNumber, String amount, Long key, String sourceType) {
        MIGSModel model = new MIGSModel();
        model.setVpc_OrderInfo(orderNumber);
        BigDecimal amoutnLong = new BigDecimal(amount);
        model.setVpc_Amount(String.valueOf(amoutnLong.multiply(new BigDecimal(100)).setScale(0)));
        model.setSourceKey1(String.valueOf(key));
        model.setSourceType(sourceType == null ? PaymentComponent.ORDER : sourceType);
        model.settRequestUrl(migsComponent.getAction());
        model.setAction(migsComponent.getAction());
        Object migsPay = migsComponent.migsPay(request, model);
        return migsPay;
    }

    @RequestMapping("/payment/pay/callback/migs")
    public ModelAndView migsPayCallback(HttpServletRequest request, MIGSCallbackModel para) {
        return migsComponent.migsPayCallback(request, para);
    }

    @RequestMapping("/payment/query/migs")
    @ResponseBody
    public PaymentResult migsQuery(HttpServletRequest request, @RequestBody PaymentOrder order) {
        return migsComponent.query(request, order);
    }

}
