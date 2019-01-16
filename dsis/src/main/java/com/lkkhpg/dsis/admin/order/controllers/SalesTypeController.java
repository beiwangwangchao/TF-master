/*
 *
 */
package com.lkkhpg.dsis.admin.order.controllers;

import com.lkkhpg.dsis.admin.order.service.ISalesTypeService;
import com.lkkhpg.dsis.common.order.dto.OrderType;
import com.lkkhpg.dsis.common.order.dto.SalesType;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 销售类型维护Controller.
 * 
 * @author zhiwei.zhang@hand-china.com 
 * [#PE20-4][ADD],2016-12-06 14:20:03.
 */
@Controller
public class SalesTypeController extends BaseController {
	
	@Autowired
	private ISalesTypeService salesTypeService;

    /**
     * 保存销售类型.
     * @param request 统一上下文
     * @param salesTypes 销售类型对象集合
     * @param result  结果集
     * @return 传入值集合，传入为空则返回为空
     * @throws BaseException 基本异常
     */
	@RequestMapping(value = "/order/salesType/submit")
    @ResponseBody
	public ResponseData submitSalesTypeDetail(HttpServletRequest request, @RequestBody List<SalesType> salesTypes,
                                              BindingResult result) throws BaseException {
		getValidator().validate(salesTypes, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        
		OrderType orderType = new OrderType();
		if(!"undefined".equals(request.getParameter("orderTypeId"))){
			orderType.setOrderTypeId(Long.parseLong(request.getParameter("orderTypeId")));
			for (SalesType salesType : salesTypes) {
	        	salesType.setOrderTypeId(Long.parseLong(request.getParameter("orderTypeId")));
	        }
		}
		orderType.setSalesOrgId(Long.parseLong(request.getParameter("salesOrgId")));
		orderType.setOrderType(request.getParameter("orderType"));
		orderType.setPriceType(request.getParameter("priceType"));
        
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(salesTypeService.batchUpdate(requestContext, salesTypes, orderType));
	}
	
	/**
     * 查询销售类型.
     * @param request 统一上下文
     * @return 传入值集合
     */
	@RequestMapping(value = "/order/salesType/query")
    @ResponseBody
	public ResponseData querySalesType(HttpServletRequest request){
		Long orderTypeId = Long.parseLong(request.getParameter("orderTypeId"));
		return new ResponseData(salesTypeService.queryByOrderTypeId(createRequestContext(request), orderTypeId));
	}
	
    /**
     * 删除销售类型.
     * @param request 统一上下文
     * @param salesTypes 销售类型对象集合
     * @return 返回一个ResponseData对象
     * @throws BaseException 基础异常
     */
	@RequestMapping(value = "/order/salesType/delete", method = RequestMethod.POST)
    @ResponseBody
	public ResponseData deleteOrderType(HttpServletRequest request, @RequestBody List<SalesType> salesTypes)
		throws BaseException {
		IRequest requestContext = createRequestContext(request);
		salesTypeService.batchDelete(requestContext, salesTypes);
		return new ResponseData();
	}

}
