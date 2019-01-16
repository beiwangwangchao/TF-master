/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.order.exception.OrderException;
import com.lkkhpg.dsis.admin.order.service.ICreditCardService;
import com.lkkhpg.dsis.common.order.dto.CreditCard;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 信用卡的控制层.
 * 
 * @author wuyichu
 */
@Controller
public class CreditCardController extends BaseController {

    @Autowired
    private ICreditCardService creditCardService;

    /**
     * 提交信用卡信息.
     * 
     * @param request
     *            http请求
     * @param creditCard
     *            信用卡信息
     * @param result
     *            校验结果
     * @return 处理后的信用卡信息
     * @throws OrderException
     *             写入或更新失败时抛出
     * @throws IOException I/O 异常
     */
    @RequestMapping(value = "om/creditCard/submit")
    @ResponseBody
    public ResponseData submit(final HttpServletRequest request, final CreditCard creditCard,
            final BindingResult result) throws OrderException, IOException {
        getValidator().validate(creditCard, result);
        creditCardService.submit(createRequestContext(request), creditCard);
        ResponseData responseData = new ResponseData();
        List<CreditCard> rows = new ArrayList<CreditCard>();
        rows.add(creditCard);
        responseData.setSuccess(true);
        responseData.setRows(rows);
        return responseData;
    }
}
