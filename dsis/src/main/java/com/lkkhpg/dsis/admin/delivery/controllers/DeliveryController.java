/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.delivery.controllers;

import com.lkkhpg.dsis.admin.delivery.exception.DeliveryException;
import com.lkkhpg.dsis.admin.delivery.service.IDeliveryService;
import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.order.service.ISalesOrderService;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.delivery.dto.DeliveryForQuery;
import com.lkkhpg.dsis.common.delivery.dto.OrderDelivery;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询发运记录controller.
 * 
 * @author li.peng
 */
@Controller
public class DeliveryController extends BaseController {

    @Autowired
    private IDeliveryService deliveryService;

    @Autowired
    private ISalesOrderService salesOrderService;

    @Autowired
    private ICommSalesOrderService commSalesOrderService;

    /**
     * 发运记录查询,查询发运记录dto.
     * 
     * @param request
     *            请求上下文.
     * @param delivery
     *            发运单信息.
     * @param page
     *            页.
     * @param pagesize
     *            总页数.
     * @return 发运单信息.
     * @throws IOException
     *             IO Exception
     * @throws IllegalAccessException
     *             Illegal Access Exception
     * @throws IllegalArgumentException
     *             Illegal Argument Exception
     */
    @RequestMapping("/dm/delivery/query")
    @ResponseBody
    public ResponseData queryDelivery(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute DeliveryForQuery delivery, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize)
            throws IllegalArgumentException, IllegalAccessException, IOException {
        List<OrderDelivery> datas = deliveryService.queryDelivery(createRequestContext(request), delivery, page,
                pagesize);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, datas);
            return null;
        } else {
            return new ResponseData(datas);
        }
    }


    /**
     * 查询销售订单列表.
     * 
     * @param request
     *            请求上下文.
     * @return 订单列表.
     */
    @RequestMapping("/dm/delivery/orderList")
    @ResponseBody
    public List<Map<String, Object>> queryOrderList(HttpServletRequest request) {
        return deliveryService.orderListQuery(createRequestContext(request));
    }

    /**
     * 查询组织库列表.
     * 
     * @param request
     *            请求上下文.
     * @return 组织库列表.
     */
    @RequestMapping("/dm/delivery/invList")
    @ResponseBody
    public List<Map<String, Object>> queryInvList(HttpServletRequest request) {
        return deliveryService.invComboxQuery(createRequestContext(request));
    }

    /**
     * 获取发运单详细信息.
     * 
     * @param request
     *            请求上下文.
     * @param deliveryId
     *            发运单头ID.
     * @return 响应信息.
     */
    @RequestMapping(value = "/dm/delivery/details")
    @ResponseBody
    public ResponseData getDetails(HttpServletRequest request, Long deliveryId) {
        IRequest iRequest = createRequestContext(request);
        OrderDelivery deliveryHead = deliveryService.getDeliveryDetails(iRequest, deliveryId);
        List<OrderDelivery> result = new ArrayList<>();
        result.add(deliveryHead);
        return new ResponseData(result);
    }

    /**
     * 获取发运单行列表.
     * 
     * @param request
     *            请求上下文.
     * @param deliveryId
     *            发运单头ID.
     * @return 响应信息.
     */
    @RequestMapping(value = "/dm/deliveryline/details")
    @ResponseBody
    public ResponseData getDeliveryLine(HttpServletRequest request, Long deliveryId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(deliveryService.getDeliveryDetailsLine(requestContext, deliveryId));
    }

    /**
     * 查询建议批次.
     * 
     * @param request
     *            请求上下文.
     * @param itemId
     *            商品ID.
     * @param orgId
     *            库存组织ID.
     * @return 响应信息.
     */
    @RequestMapping("dm/delivery/getItemLots")
    @ResponseBody
    public ResponseData getItemLots(HttpServletRequest request, @RequestParam long itemId, @RequestParam long orgId) {
        List<Map<String, Object>> list = deliveryService.getItemLots(createRequestContext(request), itemId, orgId);
        return new ResponseData(list);
    }

    /**
     * 保存发运信息.
     * 
     * @param request
     *            请求上下文.
     * @param delivery
     *            发运单信息.
     * @return 响应信息.
     * @throws CommDeliveryException 
     */
    @RequestMapping(value = "/dm/delivery/save", method = RequestMethod.POST)
    public ResponseData saveDelivery(HttpServletRequest request, @RequestBody OrderDelivery delivery)
            throws CommDeliveryException {
        IRequest requestContext = createRequestContext(request);
        delivery = deliveryService.save(requestContext, delivery);
        if (delivery != null) {
            return new ResponseData();
        } else {
            return new ResponseData(false);
        }
    }

    /**
     * 只保存发运单头.
     * 
     * @param request
     *            请求上下文
     * @param delivery
     *            发货单
     * @return 响应信息
     * @throws DeliveryException
     *             发运统一异常.
     */
    @RequestMapping(value = "/dm/delivery/saveHead", method = RequestMethod.POST)
    public ResponseData saveDeliveryHead(HttpServletRequest request, @RequestBody OrderDelivery delivery)
            throws DeliveryException {
        IRequest requestContext = createRequestContext(request);
        delivery = deliveryService.saveDeliveryHead(requestContext, delivery);
        if (delivery != null) {
            return new ResponseData();
        } else {
            return new ResponseData(false);
        }
    }

    /**
     * 提交发运单.
     * 
     * @param request
     *            请求上下文.
     * @param delivery
     *            发运单信息.
     * @return 响应信息.
     * @throws InventoryException
     *             库存统一异常.
     * @throws CommDeliveryException
     *             发运统一异常.
     */
    @RequestMapping(value = "/dm/delivery/submit", method = RequestMethod.POST)
    public ResponseData submitDelivery(HttpServletRequest request, @RequestBody OrderDelivery delivery)
            throws InventoryException, CommDeliveryException {
        IRequest requestContext = createRequestContext(request);
        String orderStatus = commSalesOrderService.queryOrderStatusByKey(delivery.getOrderHeaderId());
        if(orderStatus.equals(OrderConstants.SALES_STATUS_REFUNDING) || orderStatus.equals(OrderConstants.RETURN_STATUS_REFED)){
            ResponseData rd = new ResponseData(false);
            rd.setMessage("该订单在退款流程中，不能进行发货");
            return rd;
        }
        delivery = deliveryService.submit(requestContext, delivery);

        // 如果订单已全部发运，则调用callback同步到dapp
        if (salesOrderService.checkOrderShipStatus(delivery.getOrderHeaderId())) {
            Long orderId = delivery.getOrderHeaderId();
            new Thread(() -> {
                SalesOrder order = new SalesOrder();
                order.setHeaderId(orderId);
                salesOrderService.updateSyncFlag(order);
                salesOrderService.dappSync(requestContext, order);
            }).start();
        }

        if (delivery != null) {
            return new ResponseData();
        } else {
            return new ResponseData(false);
        }
    }

    /**
     * 取消发运单.
     * 
     * @param request
     *            请求上下文.
     * @param deliveryId
     *            发运单头ID.
     * @return 响应信息.
     * @throws DeliveryException
     *             发运统一异常.
     */
    @RequestMapping(value = "/dm/delivery/cancel", method = RequestMethod.POST)
    public ResponseData cancel(HttpServletRequest request, Long deliveryId) throws DeliveryException {
        IRequest requestContext = createRequestContext(request);
        deliveryService.cancel(requestContext, deliveryId);
        return new ResponseData();
    }

}
