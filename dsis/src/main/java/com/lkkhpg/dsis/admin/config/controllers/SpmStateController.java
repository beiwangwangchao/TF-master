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

import com.lkkhpg.dsis.common.config.dto.SpmState;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISpmStateService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 省/州 Controller.
 * @author huangjiajing
 */
@Controller
public class SpmStateController extends BaseController {

    @Autowired
    private ISpmStateService spmStateService;

    /**
     * 保存省/州.
     * @param states
     *            省/州List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/state/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveState(HttpServletRequest request, @RequestBody List<SpmState> states, BindingResult result)
            throws CommSystemProfileException {
        getValidator().validate(states, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmStateService.saveState(requestContext, states));
    }

    /**
     * 查询省/州.
     * @param state
     *            省/州DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/state/query")
    @ResponseBody
    public ResponseData queryState(HttpServletRequest request, SpmState state,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmStateService.queryState(requestContext, state, page, pagesize));
    }

    /**
     * 删除省/州.
     * @param states
     *            省/州
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/state/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteState(HttpServletRequest request, @RequestBody List<SpmState> states) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmStateService.deleteState(requestContext, states));
    }

    /**
     * 查询省/州(未分配国家).
     * @param state
     *            省/州DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/state/queryNoCountry")
    @ResponseBody
    public ResponseData queryStateNoCountry(HttpServletRequest request, SpmState state,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmStateService.queryStateNoCountry(requestContext, state, page, pagesize));
    }

    /**
     * 查询省/州-地址编辑器专用.
     * 
     * @param state
     *            省/州DTO
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/state/queryforlocation")
    @ResponseBody
    public ResponseData queryStateForLocation(HttpServletRequest request, SpmState state) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmStateService.queryStateForLocation(requestContext, state));
    }
}
