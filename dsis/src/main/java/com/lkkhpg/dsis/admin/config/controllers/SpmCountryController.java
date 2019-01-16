/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.config.controllers;

import java.util.ArrayList;
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
import com.lkkhpg.dsis.common.config.dto.SpmCountry;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISpmCountryService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 国家 Controller.
 * 
 * @author shenqb
 */
@Controller
public class SpmCountryController extends BaseController {

    @Autowired
    private ISpmCountryService spmCountryService;
    
    /**
     * 保存国家.
     * 
     * @param countrys
     *            国家DTO
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws CommSystemProfileException 系统配置统一异常
     */
    @RequestMapping(value = "/spm/country/saveDetail", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveCountryDetail(@RequestBody List<SpmCountry> countrys, BindingResult result,
            HttpServletRequest request) throws CommSystemProfileException {
        getValidator().validate(countrys, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        List<SpmCountry> countryDetail = spmCountryService.saveCountryDetail(requestContext, countrys);
        return new ResponseData(countryDetail);
    }
    
    /**
     * 查询国家详情.
     * 
     * @param request
     *            请求上下文
     * @param countryCode
     *            国家code
     * @return 国家dto
     * @throws SystemProfileException
     *             基础设置异常
     */
    @RequestMapping(value = "/spm/country/queryDetail")
    @ResponseBody
    public ResponseData queryCountryDetail(HttpServletRequest request, String countryCode) 
            throws SystemProfileException {
        IRequest requestContext = createRequestContext(request);
        SpmCountry queryCountryDetail = spmCountryService.queryCountryDetail(requestContext, countryCode);
        List<SpmCountry> SpmCountries = new ArrayList<SpmCountry>();
        SpmCountries.add(queryCountryDetail);
        return new ResponseData(SpmCountries);
    }

    /**
     * 删除国家.
     * 
     * @param countries
     *            国家List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/country/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteCountry(@RequestBody List<SpmCountry> countries, BindingResult result,
            HttpServletRequest request) throws SystemProfileException {
        IRequest requestContext = createRequestContext(request);
        spmCountryService.deleteCountry(requestContext, countries);
        return new ResponseData();
    }

    /**
     * 查询国家.
     * 
     * @param country
     *            国家DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/country/query")
    @ResponseBody
    public ResponseData queryCountry(HttpServletRequest request, SpmCountry country, 
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmCountryService.queryCountry(requestContext, country, page, pagesize));
    }
    
    /**
     * 查询国家-地址编辑器专用.
     * 
     * @param country
     *            国家DTO
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/country/queryforlocation")
    @ResponseBody
    public ResponseData queryCountryForLocation(HttpServletRequest request, SpmCountry country) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmCountryService.queryCountryForLocation(requestContext, country));
    }
    
    /**
     * 查询当前语言信息-地址编辑器专用.
     * 
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/country/getLocale")
    @ResponseBody
    public ResponseData queryCountryForLocation(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<String> list = new ArrayList<String>();
        list.add(requestContext.getLocale());
        return new ResponseData(list);
    }
    

}
