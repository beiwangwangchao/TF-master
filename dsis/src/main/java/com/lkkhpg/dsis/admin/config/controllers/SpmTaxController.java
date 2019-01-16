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

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmTaxService;
import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 税率 Controller.
 * 
 * @author shenqb
 */
@Controller
public class SpmTaxController extends BaseController {

    @Autowired
    private ISpmTaxService spmTaxService;

    /**
     * 保存税率.
     * 
     * @param taxes
     *            税率List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws CommSystemProfileException 系统配置统一异常
     */
    @RequestMapping(value = "/spm/tax/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveTax(@RequestBody List<SpmTax> taxes, BindingResult result, HttpServletRequest request)
            throws CommSystemProfileException {
        getValidator().validate(taxes, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmTaxService.saveTax(requestContext, taxes));
    }

    /**
     * 删除税率.
     * 
     * @param taxes
     *            税率List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/tax/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteTax(@RequestBody List<SpmTax> taxes, BindingResult result, HttpServletRequest request)
            throws SystemProfileException {
        IRequest requestContext = createRequestContext(request);
        spmTaxService.deleteTax(requestContext, taxes);
        return new ResponseData();
    }

    /**
     * 查询税率.
     * 
     * @param tax
     *            税率DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/tax/query")
    @ResponseBody
    public ResponseData queryTax(SpmTax tax, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmTaxService.queryTax(requestContext, tax, page, pagesize));
    }

    /**
     * 查询税率.
     * 
     * @param tax
     *            税率DTO
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/tax/queryForOrder")
    @ResponseBody
    public ResponseData queryTax(SpmTax tax, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmTaxService.queryForOrder(requestContext, tax));
    }
}
