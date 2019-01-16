/*
 *
 */
package com.lkkhpg.dsis.admin.order.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.admin.order.service.ISalesReturnService;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.exception.CommInventoryException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesReturn;
import com.lkkhpg.dsis.common.order.dto.SalesRtnLine;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 退货单Controller.
 * 
 * @author houmin
 *
 */
@Controller
public class SalesReturnController extends BaseController {

    @Autowired
    private ISalesReturnService salesReturnService;

    /**
     * 获取组织地址.
     * 
     * @param request
     *            统一上下文
     * @param salesOrgId
     *            销售组织ID
     * @param invOrgId
     *            库存组织ID
     * @return 组织对应地址
     */
    @RequestMapping(value = "om/return/getLocation", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getLocation(HttpServletRequest request,
            @RequestParam(value = "salesOrgId", required = false) Long salesOrgId,
            @RequestParam(value = "invOrgId", required = false) Long invOrgId) {
        IRequest iRequest = createRequestContext(request);
        List<SpmLocation> location = new ArrayList<SpmLocation>();
        location.add(salesReturnService.getLocations(iRequest, salesOrgId, invOrgId));
        return new ResponseData(location);
    }

    /**
     * 退货单详细信息保存.
     * 
     * @param request
     *            统一上下文
     * @param salesReturn
     *            订单详细信息对象
     * @param result
     *            结果集
     * @return 传入值集合，传入为空则返回为空
     * @throws CommOrderException
     *             订单统一异常
     */
    @RequestMapping(value = "om/return/saveRtnDetail", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveReturnOrderDetail(HttpServletRequest request, @RequestBody SalesReturn salesReturn,
            BindingResult result) throws CommOrderException {
        getValidator().validate(salesReturn, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest iRequest = createRequestContext(request);
        List<SalesReturn> salesReturns = new ArrayList<SalesReturn>();
        salesReturns.add(salesReturnService.saveReturnOrderDetail(iRequest, salesReturn));
        return new ResponseData(salesReturns);
    }

    /**
     * 删除退货单.
     * 
     * @param request
     *            统一上下文
     * @param salesReturns
     *            退货单信息
     * @return 删除成功或失败标识
     * @throws CommOrderException
     *             订单统一异常
     */
    @RequestMapping(value = "om/return/deleteSalesReturn", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteSalesReturn(HttpServletRequest request, @RequestBody List<SalesReturn> salesReturns)
            throws CommOrderException {
        IRequest iRequest = createRequestContext(request);
        salesReturnService.deleteSalesReturn(iRequest, salesReturns);
        ResponseData response = new ResponseData();
        response.setSuccess(true);
        return response;
    }

    /**
     * 删除退货单行.
     * 
     * @param request
     *            统一上下文
     * @param salesRtnLines
     *            退货单行对象集合
     * @return 删除成功标识
     * @throws CommOrderException
     *             订单统一异常
     */
    @RequestMapping(value = "om/return/deleteSalesRtnLine", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteReturnLine(HttpServletRequest request, @RequestBody List<SalesRtnLine> salesRtnLines)
            throws CommOrderException {
        IRequest iRequest = createRequestContext(request);
        salesReturnService.deleteSalesRtnLine(iRequest, salesRtnLines);
        ResponseData response = new ResponseData();
        response.setSuccess(true);
        return response;
    }

    /**
     * 退货单提交.
     * 
     * @param request
     *            统一上下文
     * @param salesReturn
     *            退货单详情
     * @param result
     *            结果集
     * @return 退货单信息
     * @throws CommOrderException
     *             统一订单异常
     * @throws CommInventoryException
     *             统一库存异常
     * @throws CommMemberException
     *             统一会员异常
     */
    @RequestMapping(value = "om/return/submitRtnDetail", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitReturnOrderDetail(HttpServletRequest request, @RequestBody SalesReturn salesReturn,
            BindingResult result) throws CommOrderException, CommInventoryException, CommMemberException {
        getValidator().validate(salesReturn, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest iRequest = createRequestContext(request);
        List<SalesReturn> salesReturns = new ArrayList<SalesReturn>();
        salesReturns.add(salesReturnService.submitReturnOrderDetail(iRequest, salesReturn));
        return new ResponseData(salesReturns);
    }

    /**
     * 获取退货单详情.
     * 
     * @param request
     *            统一上下文
     * @param returnId
     *            退货单头ID
     * @return 退货单详情
     * @throws CommOrderException
     *             订单统一异常
     */
    @RequestMapping(value = "om/return/getReturnDetail")
    @ResponseBody
    public ResponseData getReturnDetail(HttpServletRequest request, @RequestParam Long returnId)
            throws CommOrderException {
        IRequest iRequest = createRequestContext(request);
        List<SalesReturn> salesReturns = new ArrayList<SalesReturn>();
        salesReturns.add(salesReturnService.getReturnDetail(iRequest, returnId));
        return new ResponseData(salesReturns);
    }

    /**
     * 退货单查询.
     * 
     * @param request
     *            统一上下文
     * @param response
     *            应答请求
     * @param salesReturn
     *            退货单详情
     * @param page
     *            页码
     * @param pagesize
     *            每页显示记录数
     * @return 退货单详细信息
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @RequestMapping(value = "/om/sales/return")
    @ResponseBody
    public ResponseData queryReturn(HttpServletRequest request, HttpServletResponse response, SalesReturn salesReturn,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize)
            throws IllegalArgumentException, IllegalAccessException, IOException {
        IRequest requestContext = createRequestContext(request);
        List<SalesReturn> list = salesReturnService.selectSalesReturn(requestContext, salesReturn, page, pagesize);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, list);
            return null;
        } else {
            return new ResponseData(list);
        }
    }

    /**
     * 获取商品包含行信息.
     * 
     * @param request
     *            统一上下文
     * @param salesLine
     * @return 商品包详细信息
     */
    @RequestMapping(value = "/om/sales/getHierarchyByParentLineId")
    @ResponseBody
    public ResponseData getHierarchyByParentItemId(HttpServletRequest request, @RequestBody SalesLine salesLine) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(salesReturnService.selectDetailByReturnId(requestContext, salesLine));
    }

}
