/*
 *
 */
package com.lkkhpg.dsis.admin.order.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lkkhpg.dsis.platform.exception.TokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.admin.order.service.IOrderQueryService;
import com.lkkhpg.dsis.common.order.dto.QueryOrder;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 订单查询控制层.
 * 
 * @author gulin
 */

@Controller
public class OrderQueryController extends BaseController {

    @Autowired
    private IOrderQueryService orderQueryService;

    private final Logger logger = LoggerFactory.getLogger(OrderQueryController.class);

    /**
     * 依据条件查询订单数据.
     * 
     * @param request
     *            统一上下文.
     * @param response
     *            统一上下文.
     * @param queryOrder
     *            订单查询条件.
     * @param page
     *            页码.
     * @param pagesize
     *            页面size.
     * @return ResponseData 返回订单查询结构集合.
     * @throws IOException
     *             IO Exception
     * @throws IllegalAccessException
     *             Illegal Access Exception
     * @throws IllegalArgumentException
     *             Illegal Argument Exception
     */
    @RequestMapping(value = "/om/order/query")
    @ResponseBody
    public ResponseData selectOrders(HttpServletRequest request, HttpServletResponse response, QueryOrder queryOrder,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize)
            throws IllegalArgumentException, IllegalAccessException, IOException {
        IRequest iRequest = createRequestContext(request);
        List<QueryOrder> orders = orderQueryService.selectOrders(iRequest, queryOrder, page, pagesize);
        if (logger.isDebugEnabled()) {
            logger.debug(orders.toString());
        }
        ResponseData data = new ResponseData(orders);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, orders);
            return null;
        } else {
            return data;
        }
    }

    /**
     * 审核状态保存
     * hand 9633
     * @param queryOrders
     * @param result
     * @param request
     * @return
     * @throws TokenException
     */
     @RequestMapping(value = "/sys/order/submit", method = RequestMethod.POST)
    public ResponseData saveOrders(@RequestBody List<QueryOrder>queryOrders,BindingResult result,
                                   HttpServletRequest request){
         getValidator().validate(queryOrders, result);
         if (result.hasErrors()) {
             ResponseData rd = new ResponseData(false);
             rd.setMessage(getErrorMessage(result, request));
             return rd;
         }
         IRequest requestCtx = createRequestContext(request);
         return new ResponseData(orderQueryService.batchUpdate(requestCtx, queryOrders));
    }

}
