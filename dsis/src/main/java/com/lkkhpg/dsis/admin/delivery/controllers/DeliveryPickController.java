/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.delivery.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lkkhpg.dsis.integration.payment.controllers.ExportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.delivery.service.IDeliveryPickService;
import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickHead;
import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickLine;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 挑库发放controller.
 * 
 * @author Zhaoqi
 *
 */
@Controller
public class DeliveryPickController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(DeliveryPickController.class);

    @Autowired
    private IDeliveryPickService deliveryPickService;

    /**
     * 挑库确认发放.
     * 
     * @param request
     *            统一上下文.
     * @param deliveryPickHead
     *            挑库单订头.
     * @return 响应信息.
     * @throws CommDeliveryException
     *             发运统一异常
     */
    @RequestMapping(value = "/dm/delivery/insert", method = RequestMethod.POST)
    public ResponseData insert(HttpServletRequest request, @RequestBody DeliveryPickHead deliveryPickHead)
            throws CommDeliveryException {
        IRequest requestContext = createRequestContext(request);
        deliveryPickService.verifyInventory(requestContext, deliveryPickHead);
        deliveryPickService.saveDeliveryPick(requestContext, deliveryPickHead);
        return new ResponseData();
    }

    /**
     * 挑库订单.
     * 
     * @param request
     *            统一上下文.
     * @param deliveryPick
     *            挑库订单行信息.
     * @param page
     *            分页信息page
     * @param pagesize
     *            分页信息pagesize
     * @return 响应信息
     */
    @RequestMapping(value = "/dm/delivery/selectOrder")
    @ResponseBody
    public ResponseData selectOrder(HttpServletRequest request, DeliveryPickLine deliveryPick,HttpServletResponse response,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        /*if (deliveryPick.getOrderNumber() == null) {
            return new ResponseData();
        }*/
        List<DeliveryPickLine> list = deliveryPickService.selectOrder(requestContext, deliveryPick, page, pagesize);
        if (logger.isDebugEnabled()) {
            logger.debug(list.toString());
        }
        ResponseData data = new ResponseData(list);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {

            try {
                ExportUtils.export(request, response,list);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return data;
        }
       // return new ResponseData(list);
    }

    /**
     * 挑库订单行.
     * 
     * @param request
     *            统一上下文.
     * @param deliveryPick
     *            挑库订单行信息.
     * @param page
     *            分页信息page
     * @param pagesize
     *            分页信息pagesize
     * @return 响应信息.
     */
    @RequestMapping(value = "/dm/delivery/selectOrderLine")
    public ResponseData selectOrderLine(HttpServletRequest request,String orderNumber, DeliveryPickLine deliveryPick,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);

        if (deliveryPick.getOrderNumber() == null) {
            return new ResponseData();
        }
        deliveryPick.setOrderNumber(orderNumber.trim());
        List<DeliveryPickLine> list = deliveryPickService.selectOrderLine(requestContext, deliveryPick, page, pagesize);

        if (list.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("商品全部发放！");
            }
        }
        return new ResponseData(list);
    }

    /**
     * 库存组织查询.
     * 
     * @param request
     *            统一上下文.
     * @param orderNumber
     *            订单编号.
     * @return 响应信息.
     */
    @RequestMapping(value = "/dm/delivery/queryInvOrgs", method = RequestMethod.POST)
    public ResponseData queryInvOrgs(HttpServletRequest request, String orderNumber) {
        IRequest requestContext = createRequestContext(request);
        List<Map<String, Object>> list = deliveryPickService.queryInvOrgs(requestContext, orderNumber);
        return new ResponseData(list);
    }

    /**
     * 库存量计算.
     * 
     * @param request
     *            统一上下文.
     * @param deliveryPick
     *            挑库订单行信息.
     * @return 响应信息.
     */
    @RequestMapping(value = "/dm/delivery/queryInventory", method = RequestMethod.POST)
    @ResponseBody
    public List<DeliveryPickLine> queryInventory(HttpServletRequest request,
            @RequestBody List<DeliveryPickLine> deliveryPick) {
        IRequest requestContext = createRequestContext(request);
        List<DeliveryPickLine> list = deliveryPickService.queryInventory(requestContext, deliveryPick);
        if (list.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("该商品在该库存组织没有库存量");
            }
        }
        return list;
    }

    /**
     * 订单类型不同获取供货组织选择不同.
     * 
     * @param request
     *            统一上下文.
     * @param deliveryPickLine
     *            挑库订单行信息
     * @return 供货组织集合.
     */
    @RequestMapping(value = "dm/delivery/selectInvOrg")
    public ResponseData selectInvOrg(HttpServletRequest request, DeliveryPickLine deliveryPickLine) {
        IRequest requestContext = createRequestContext(request);
        List<DeliveryPickLine> list = deliveryPickService.selectInvOrg(requestContext, deliveryPickLine);
        return new ResponseData(list);
    }
}
