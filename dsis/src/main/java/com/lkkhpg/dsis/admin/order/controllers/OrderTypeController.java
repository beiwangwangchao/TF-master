/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkkhpg.dsis.admin.order.service.IOrderTypeService;
import com.lkkhpg.dsis.common.order.dto.OrderType;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单类型维护Controller.
 * 
 * @author zhiwei.zhang@hand-china.com [#PE20-4][ADD],2016-12-06 14:20:03.
 */
@Controller
public class OrderTypeController extends BaseController {

    @Autowired
    private IOrderTypeService orderTypeService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 保存订单类型.
     * 
     * @param request
     *            统一上下文 HttpServletRequest
     * @param orderTypes
     *            订单类型集合 OrderType
     * @param result
     *            结果集 BindingResult
     * @return ResponseData 传入值集合，传入为空则返回为空 ResponseData
     * @throws BaseException
     *             BaseException 基础异常
     * 
     */
    @RequestMapping(value = "/order/orderType/submit")
    @ResponseBody
    public ResponseData submitOrderTypeDetail(HttpServletRequest request, @RequestBody List<OrderType> orderTypes,
                                              BindingResult result) throws BaseException {

        getValidator().validate(orderTypes, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest requestContext = createRequestContext(request);
        return new ResponseData(orderTypeService.batchUpdate(requestContext, orderTypes));
    }

    /**
     * 根据当前用户查询订单类型.
     * 
     * @param request
     *            统一上下文
     * @param orderType
     *            订单类型
     * @return ResponseData 传入值集合
     */
    @RequestMapping(value = "/order/orderType/query")
    @ResponseBody
    public ResponseData queryOrderType(HttpServletRequest request, OrderType orderType,
                                       @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(orderTypeService.queryByOrderType(requestContext, orderType, page, pagesize));
    }

    /**
     * 查询所有订单类型
     * 
     * @param request
     *            统一上下文
     * @return ResponseData 传入值集合
     */
    @RequestMapping(value = "/order/orderType/queryAll")
    @ResponseBody
    public ResponseData queryAllOrderType(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(orderTypeService.queryAll(requestContext));
    }

    /**
     * 删除订单类型
     * 
     * @param request
     *            统一上下文
     * @param orderTypes
     *            订单类型集合
     * @return 返回一个ResponseData对象
     * @throws BaseException
     *             基础异常
     */
    @RequestMapping(value = "/order/orderType/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteOrderType(HttpServletRequest request, @RequestBody List<OrderType> orderTypes)
            throws BaseException {
        IRequest requestContext = createRequestContext(request);
        orderTypeService.batchDelete(requestContext, orderTypes);
        return new ResponseData();
    }

    /**
     * 订单界面查询销售类型
     * 
     * @param request
     *            请求上下文
     * @param salesOrgId
     *            销售组织ID
     * @param orderType
     *            订单类型
     * @param userType
     *            用户类型
     * @return 销售类型
     */
    @RequestMapping(value = "/order/salesType/queryfororder")
    @ResponseBody
    public ResponseData querySalesTypeForOrder(HttpServletRequest request, Long salesOrgId, String orderType,
                                               String userType) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                orderTypeService.querySalesTypeForOrder(requestContext, salesOrgId, orderType, userType));
    }

    @RequestMapping(value = "/order/salesType/queryBySalesOrgId")
    @ResponseBody
    public ResponseData queryOrderTypeBySalesOrgId(HttpServletRequest request, Long salesOrgId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(orderTypeService.queryBySalesOrgId(requestContext, salesOrgId));
    }


}
