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
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.service.ISpmLocationService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 地址 Controller.
 * 
 * @author frank.li
 */
@Controller
public class SpmLocationController extends BaseController {

    @Autowired
    private ISpmLocationService spmLocationService;

    /**
     * 保存地点.
     * 
     * @param locations
     *            地点List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/location/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveLocation(@RequestBody List<SpmLocation> locations, BindingResult result,
            HttpServletRequest request) throws SystemProfileException {
        getValidator().validate(locations, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmLocationService.saveLocation(requestContext, locations));
    }

    /**
     * 删除地点.
     * 
     * @param locations
     *            地点List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/location/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteLocation(@RequestBody List<SpmLocation> locations, BindingResult result,
            HttpServletRequest request) throws SystemProfileException {
        IRequest requestContext = createRequestContext(request);
        spmLocationService.deleteLocation(requestContext, locations);
        return new ResponseData();
    }

    /**
     * 查询地点.
     * 
     * @param location
     *            地点DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/location/query")
    @ResponseBody
    public ResponseData queryLocation(SpmLocation location, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmLocationService.queryLocation(requestContext, location, page, pagesize));
    }

}
