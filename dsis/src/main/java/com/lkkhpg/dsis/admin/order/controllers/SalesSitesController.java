/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.service.ISalesSitesService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 订单地址控制层.
 * 
 * @author wuyichu
 */
@Controller
public class SalesSitesController extends BaseController {

    @Autowired
    private ISalesSitesService salesSitesService;

    /**
     * 提交订单地址.
     * 
     * @param request
     *            请求上下文
     * @param salesSites
     *            订单地址
     * @param result
     *            校验结果
     * @return 提交后的订单地址数据
     * @throws CommOrderException
     *             订单数据校验不通过时抛出
     */
    @RequestMapping(value = "/om/sites/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submit(final HttpServletRequest request, @RequestBody final SalesSites salesSites,
            final BindingResult result) throws CommOrderException {
        getValidator().validate(salesSites, result);
        IRequest iRequest = createRequestContext(request);
        SalesSites row = salesSitesService.submit(iRequest, salesSites);
        List<SalesSites> rows = new ArrayList<SalesSites>();
        rows.add(row);
        ResponseData responseData = new ResponseData(rows);
        return responseData;
    }

    @RequestMapping(value = "/om/sites/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData delete(final HttpServletRequest request, Long siteId) {
        IRequest iRequest = createRequestContext(request);
        int rows = salesSitesService.deleteSite(iRequest, siteId);
        ResponseData responseData = new ResponseData();
        if (rows > 0) {
            responseData.setSuccess(true);
        } else {
            responseData.setSuccess(false);
        }
        return responseData;
    }
}
