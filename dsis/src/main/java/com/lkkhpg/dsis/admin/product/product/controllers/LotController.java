/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lkkhpg.dsis.admin.product.product.service.ILotService;
import com.lkkhpg.dsis.common.product.dto.Lot;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 批次Controller.
 * 
 * @author mclin
 */
@Controller
public class LotController extends BaseController {

    @Autowired
    private ILotService invLotService;

    /**
     * 获取批次信息(入库).
     * 
     * @param request
     *            请求上下文
     * @param invOrgId
     *            库存组织ID
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/pm/lot/queryIn", method = RequestMethod.POST)
    public ResponseData queryLotsIn(HttpServletRequest request, @RequestParam Long invOrgId) {
        IRequest requestContext = createRequestContext(request);
        if (invOrgId == null) {
            return new ResponseData();
        }
        return new ResponseData(invLotService.queryLotsIn(requestContext, invOrgId));
    }

    /**
     * 获取批次信息(出库).
     * 
     * @param request
     *            请求上下文
     * @param invOrgId
     *            库存组织ID
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/pm/lot/queryOut", method = RequestMethod.POST)
    public ResponseData queryLotsOut(HttpServletRequest request, @RequestParam Long invOrgId) {
        IRequest requestContext = createRequestContext(request);
        if (invOrgId == null) {
            return new ResponseData();
        }
        return new ResponseData(invLotService.queryLotsOut(requestContext, invOrgId));
    }

    /**
     * 根据手动输入的批次号带出批次到期日.
     * 
     * @param request
     *            请求上下文
     * @param invOrgId
     *            库存组织ID
     * @param itemId
     *            商品ID
     * @param hasOnHandQty
     *            出入库标志
     * @param lotNumber
     *            批次号
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/pm/lot/getExpiryDate", method = RequestMethod.POST)
    public ResponseData queryLotsOut(HttpServletRequest request, Lot lot) {
        IRequest requestContext = createRequestContext(request);
        List<Lot> lots = new ArrayList<Lot>();
        lots.add(invLotService.queryLotWithExpiryDate(requestContext, lot));
        return new ResponseData(lots);
    }
}
