/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.cost.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.inventory.cost.service.IInvCostDetailsService;
import com.lkkhpg.dsis.admin.inventory.cost.service.IInvCostService;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostDetails;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 获取成本数据.
 * 
 * @author HuangJiaJing
 *
 */
@Controller
public class InvCostDetailsController extends BaseController {

    @Autowired
    private IInvCostDetailsService invCostDetailsService;

    @Autowired
    private IInvCostService invCostService;

    /**
     * 1.校验是否满足获取成本条件.
     * 
     * @param request
     *            请求上下文
     * @param invOrgId
     *            库存组织
     * @param year
     *            年份
     * @param month
     *            月份
     * @return ResponseData集合
     * @throws InventoryException
     *             异常
     */
    @RequestMapping(value = "/inv/details/check", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData checkDetails(HttpServletRequest request, Long invOrgId, Integer year, Integer month)
            throws InventoryException {
        IRequest requestContext = createRequestContext(request);
        boolean resut = invCostDetailsService.checkDetail(requestContext, invOrgId, year, month);
        return new ResponseData(resut);
    }

    /**
     * 2.查看是否已获取成本数据.
     * 
     * @param request
     *            请求上下文
     * @param invOrgId
     *            库存组织
     * @param year
     *            年份
     * @param month
     *            月份
     * @return ResponseData集合
     * @throws InventoryException
     *             异常
     */
    @RequestMapping(value = "/inv/details/checkdb", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData checkDetailsNull(HttpServletRequest request, Long invOrgId, Integer year, Integer month)
            throws InventoryException {
        IRequest requestContext = createRequestContext(request);
        boolean resut = invCostDetailsService.checkDetailNull(requestContext, invOrgId, year, month);
        ResponseData re = new ResponseData();
        if (resut) {
            re.setMessage("1");
        } else {
            re.setMessage("2");
        }
        return re;
    }

    /**
     * 3.获取成本数据.
     * 
     * @param request
     *            请求上下文
     * @param invOrgId
     *            库存组织
     * @param year
     *            年份
     * @param month
     *            月份
     * @param page
     *            页数
     * @param pagesize
     *            每页行数
     * @return ResponseData集合
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/inv/details/get", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getDetails(HttpServletRequest request, @RequestBody CostDetails costDetails)
            throws InventoryException {
        IRequest requestContext = createRequestContext(request);
        List<CostDetails> costDetailsList = invCostDetailsService.genDetailDatas(requestContext, costDetails);
        return new ResponseData(costDetailsList);
    }

    /**
     * 4.查询成本数据.
     * 
     * @param request
     *            请求上下文
     * @param invOrgId
     *            库存组织
     * @param year
     *            年份
     * @param month
     *            月份
     * @return ResponseData集合
     */
    @RequestMapping(value = "/inv/details/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryDetails(HttpServletRequest request, CostDetails costDetails, BindingResult result) {
        getValidator().validate(costDetails, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        List<CostDetails> details = invCostDetailsService.queryDetailDatas(requestContext, costDetails);
        return new ResponseData(details);
    }

    /**
     * 保存成本明细数据.
     * 
     * @param request
     *            请求上下文
     * @param costDetailss
     *            对象集合
     * @return ResponseData集合
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/inv/details/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateCostDetails(HttpServletRequest request, @RequestBody List<CostDetails> costDetailss)
            throws InventoryException {
        IRequest requestContext = createRequestContext(request);
        List<CostDetails> details = invCostDetailsService.updateCostDetails(requestContext, costDetailss);
        return new ResponseData(details);
    }

    /**
     * 提交成本记录数据.
     * 
     * @param request
     *            请求上下文
     * @param costDetails
     *            成本明细
     * @return ResponseData集合
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/inv/details/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitCostDetails(HttpServletRequest request, @RequestBody List<CostDetails> costDetails,
            BindingResult result) throws InventoryException {
        getValidator().validate(costDetails, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        invCostService.calculateCost(requestContext, costDetails);
        return new ResponseData();
    }

}
