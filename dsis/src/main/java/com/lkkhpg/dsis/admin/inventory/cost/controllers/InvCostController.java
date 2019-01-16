/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.cost.controllers;

import java.io.IOException;
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
import com.lkkhpg.dsis.admin.inventory.cost.service.IInvCostService;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostAttributes;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostRecords;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 成本记录controller.
 * 
 * @author hanrui.huang
 *
 */
@Controller
public class InvCostController extends BaseController {

    @Autowired
    private IInvCostService invCostService;

    /**
     * 取消成本记录.
     * 
     * @param costRecords
     *            成本记录DTO
     * 
     * @param request
     *            请求上下文
     * @return 响应数据
     * @throws InventoryException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/inv/cost/remove")
    @ResponseBody
    public ResponseData deleteCost(CostRecords costRecords, HttpServletRequest request) throws InventoryException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(invCostService.removeCostRecords(requestContext, costRecords));
    }

    /**
     * 查询成本记录.
     * 
     * @param costRecords
     *            成本记录DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @RequestMapping(value = "/inv/cost/query")
    @ResponseBody
    public ResponseData queryCostRecords(CostRecords costRecords, HttpServletResponse response,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request,
            BindingResult result) throws IllegalArgumentException, IllegalAccessException, IOException {
        getValidator().validate(costRecords, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        List<CostRecords> list = invCostService.queryCostRecords(requestContext, costRecords, page, pagesize);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, list);
            return null;
        } else {
            return new ResponseData(list);
        }
    }

    /**
     * 保存成本属性.
     * 
     * @param request
     *            统一上下文
     * @param costAttributes
     *            成本属性对象
     * @return 成本属性对象
     */
    @RequestMapping(value = "/inv/costAttr/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveCostAttributes(HttpServletRequest request,
            @RequestBody List<CostAttributes> costAttributes) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(invCostService.saveOrUpdateCostAttributes(requestContext, costAttributes));
    }

    /**
     * 查询成本属性信息.
     * 
     * @param request
     *            统一上下文
     * @param costRecordId
     *            成本记录主键Id
     * @return 对应成本记录的成本属性信息
     */
    @RequestMapping(value = "/inv/costAttr/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryCostAttributes(HttpServletRequest request, Long costRecordId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(invCostService.queryCostAttributes(requestContext, costRecordId));
    }

}
