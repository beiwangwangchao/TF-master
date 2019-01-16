/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderCancelRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderCancelResponse;
import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOrderCancelService;
import com.lkkhpg.dsis.common.delivery.mapper.OrderDeliveryLineMapper;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 订单作废接口.
 * 
 * @author fengwanjun
 */
@Service
public class OrderCancelServiceImpl implements IOrderCancelService {

    @Autowired
    private SalesOrderMapper salesOrderMapper;
    
    @Autowired
    private OrderDeliveryLineMapper orderDeliveryLineMapper;

    /**
     * 订单作废接口.
     * 
     * @param orderCancelRequestList
     *            saleOrganization 销售组织 companyCode 公司代码 market 市场 orderNumber
     *            订单编号
     * @throws DAppException
     *             DApp接口异常
     */
    @Override
    public List<OrderCancelResponse> orderCancel(List<OrderCancelRequest> orderCancelRequestList) throws DAppException {
        Map<String, Object> mapRequest = new HashMap<String, Object>();
        List<OrderCancelResponse> orderCancelResponseList = new ArrayList<OrderCancelResponse>();
        OrderCancelResponse orderCancelResponse;
        for (OrderCancelRequest orderCancelRequest : orderCancelRequestList) {
            orderCancelResponse = new OrderCancelResponse();
            mapRequest.put("orderNumber", orderCancelRequest.getOrderNumber());

            SalesOrder salesOrder = salesOrderMapper.selectByOrderNumberForDApp(mapRequest);

            if (salesOrder == null) {
                orderCancelResponse.setOrderNumber(mapRequest.get("orderNumber").toString());
                orderCancelResponse.setResult("-1");
                orderCancelResponse.setDescription(String.valueOf(IntegrationConstant.ERROR_30007) + ":"
                        + IntegrationException.MSG_ERROR_ORDER_CANCEL_EXIST);
                // throw new
                // DAppException(IntegrationException.MSG_ERROR_ORDER_CANCEL_ERROR,
                // IntegrationConstant.ERROR_30007, null);
            } else {
                int shippingQty = orderDeliveryLineMapper.selectByOrderId(salesOrder.getHeaderId());
                if (shippingQty > 0) {
                    orderCancelResponse.setOrderNumber(mapRequest.get("orderNumber").toString());
                    orderCancelResponse.setResult("-1");
                    orderCancelResponse.setDescription(String.valueOf(IntegrationConstant.ERROR_30013) + ":"
                            + IntegrationException.MSG_ERROR_ORDER_CANCEL_SHIPPING_QTY);
                    // throw new
                    // DAppException(IntegrationException.MSG_ERROR_ORDER_CANCEL_DAPP,
                    // IntegrationConstant.ERROR_30005, "orderNumber=" +
                    // mapRequest.get("orderNumber"));
                } else if (!salesOrder.getSourceType().equals(IntegrationConstant.ORDER_SOURCE_TYPE_DAPP)) {
                    orderCancelResponse.setOrderNumber(mapRequest.get("orderNumber").toString());
                    orderCancelResponse.setResult("-1");
                    orderCancelResponse.setDescription(String.valueOf(IntegrationConstant.ERROR_30005) + ":"
                            + IntegrationException.MSG_ERROR_ORDER_CANCEL_DAPP);
                    // throw new
                    // DAppException(IntegrationException.MSG_ERROR_ORDER_CANCEL_DAPP,
                    // IntegrationConstant.ERROR_30005, "orderNumber=" +
                    // mapRequest.get("orderNumber"));
                } else if (salesOrder.getOrderStatus().equals(IntegrationConstant.ORDER_STATUS_COMP_ED) || salesOrder.getOrderStatus().equals(IntegrationConstant.ORDER_STATUS_VOID)) {
                    SalesOrder order = new SalesOrder();
                    order.setHeaderId(salesOrder.getHeaderId());
                    order.setOrderStatus(IntegrationConstant.ORDER_STATUS_VOID);
                    salesOrderMapper.updateOrderStatus(order);
                    orderCancelResponse.setOrderNumber(mapRequest.get("orderNumber").toString());
                    orderCancelResponse.setResult("1");
                     orderCancelResponse.setDescription(null);

                } else if (salesOrder.getOrderStatus().equals(IntegrationConstant.ORDER_STATUS_CANCAL) || salesOrder.getOrderStatus().equals(IntegrationConstant.ORDER_STATUS_PAYIN)
                        || salesOrder.getOrderStatus().equals(IntegrationConstant.ORDER_STATUS_FAIL)) {
                    SalesOrder order = new SalesOrder();
                    order.setHeaderId(salesOrder.getHeaderId());
                    order.setOrderStatus(IntegrationConstant.ORDER_STATUS_CANCAL);
                    salesOrderMapper.updateOrderStatus(order);
                    orderCancelResponse.setOrderNumber(mapRequest.get("orderNumber").toString());
                    orderCancelResponse.setResult("1");
                    orderCancelResponse.setDescription(null);
                } else {
                    orderCancelResponse.setOrderNumber(mapRequest.get("orderNumber").toString());
                    orderCancelResponse.setResult("-1");
                    orderCancelResponse.setDescription(String.valueOf(IntegrationConstant.ERROR_30008) + ":"
                            + IntegrationException.MSG_ERROR_ORDER_CANCEL_STATUS);
                    // throw new DAppException(
                    // String.format(IntegrationException.MSG_ERROR_ORDER_CANCEL_STATUS,
                    // salesOrder.getOrderStatus()),
                    // IntegrationConstant.ERROR_30008, "orderNumber=" +
                    // mapRequest.get("orderNumber"));
                }
            }
            orderCancelResponseList.add(orderCancelResponse);
        }
        return orderCancelResponseList;
    }

}
