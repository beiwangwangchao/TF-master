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
import com.lkkhpg.dsis.common.config.dto.SpmCity;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISpmCityService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 城市 Controller.
 * @author shenqb
 */
@Controller
public class SpmCityController extends BaseController {

    @Autowired
    private ISpmCityService spmCityService;

    /**
     * 保存城市.
     * @param cities 城市List
     * @param result 数据绑定结果
     * @param request 请求上下文
     * @return ResponseData 响应数据
     * @throws CommSystemProfileException 系统配置统一异常
     */
    @RequestMapping(value = "/spm/city/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveCity(@RequestBody List<SpmCity> cities, BindingResult result, HttpServletRequest request)

            throws CommSystemProfileException {
        getValidator().validate(cities, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmCityService.saveCity(requestContext, cities));
    }

    /**
     * 删除城市.
     * @param cities 城市List
     * @param result 数据绑定结果
     * @param request 请求上下文
     * @return ResponseData 响应数据
     * @throws SystemProfileException 系统配置统一异常
     */
    @RequestMapping(value = "/spm/city/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteCity(@RequestBody List<SpmCity> cities, BindingResult result, HttpServletRequest request)
            throws SystemProfileException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmCityService.deleteCity(requestContext, cities));
    }

    /**
     * 查询城市.
     * @param city 城市DTO
     * @param page 页
     * @param pagesize 分页大小
     * @param request 请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/city/query")
    @ResponseBody
    public ResponseData queryCity(SpmCity city, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmCityService.queryCity(requestContext, city, page, pagesize));
    }

    /**
     * 查询州省为空的城市.
     * @param request 请求上下文
     * @param city 城市DTO
     * @param pagesize 分页大小
     * @param page 页
     *  @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/citynull/query")
    @ResponseBody
    public ResponseData queryCityNull(HttpServletRequest request, SpmCity city,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmCityService.queryNullCity(requestContext, city, page, pagesize));
    }
    
    /**
     * 查询城市-地址编辑器专用.
     * 
     * @param city 城市DTO 
     * @param request 请求上下文 
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/city/queryforlocation")
    @ResponseBody
    public ResponseData queryCityForLocation(SpmCity city, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmCityService.queryCityForLocation(requestContext, city));
    }

    /**
     * 获取城市.
     * @param cityCode 城市代码
     * @param request 请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/city/get")
    @ResponseBody
    public ResponseData getCity(String cityCode, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<SpmCity> list = new ArrayList<SpmCity>();
        SpmCity city = spmCityService.getCity(requestContext, cityCode);
        list.add(city);
        return new ResponseData(list);
    }
}
