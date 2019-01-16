/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.config.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISpmCurrencyService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 币种 Controller.
 * 
 * @author hanrui.huang
 */
@Controller
public class SpmCurrencyController extends BaseController {

    @Autowired
    private ISpmCurrencyService spmCurrencyService;

    /**
     * 编辑币种.
     * 
     * @param spmCurrencys
     *            币种List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return 响应数据
     * @throws CommSystemProfileException 系统配置统一异常
     */
    @RequestMapping(value = "/spm/currency/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveCurrency(@RequestBody List<SpmCurrency> spmCurrencys, BindingResult result,
            HttpServletRequest request) throws CommSystemProfileException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmCurrencyService.saveSpmCurrency(requestContext, spmCurrencys));
    }

    /**
     * 查询币种.
     * 
     * @param currency
     *            币种DTO
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @param request
     *            请求上下文
     * @return 响应数据
     */
    @RequestMapping(value = "/spm/currency/query")
    @ResponseBody
    public ResponseData queryCurrency(SpmCurrency currency, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmCurrencyService.querySpmCurrency(requestContext, currency, page, pagesize));
    }
}
