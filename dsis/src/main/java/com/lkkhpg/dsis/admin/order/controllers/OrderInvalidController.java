/*
 *
 */
package com.lkkhpg.dsis.admin.order.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.order.service.IPaymentRefundService;
import com.lkkhpg.dsis.admin.order.service.ISalesOrderService;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.order.dto.PaymentRefund;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.common.service.IVoucherService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 
 * 订单失效Controller.
 * 
 * @author gulin
 *
 */
@Controller
public class OrderInvalidController extends BaseController {

    @Autowired
    private IPaymentRefundService paymentRefundService;

    @Autowired
    private ISalesOrderService salesOrderService;
    
    @Autowired
    private ICommSalesOrderService commSalesOrderService;

    @Autowired
    private IVoucherService voucherService;

    /**
     * 订单失效（情况一），支付金额退回至Remaining Balance.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头ID.
     * @param remark
     *            退款备注.
     * @return ResponseData 退款成功，返回ture.
     * @throws CommMemberException
     *             会员异常
     * @throws CommVoucherException
     *             优惠券冲销事务处理异常
     */
    @RequestMapping(value = "/om/order/invalidToRemaining")
    public ResponseData invalidOrder(HttpServletRequest request, @RequestParam("headerId") String headerId,
            @RequestBody String remark) throws CommMemberException, CommVoucherException {
        IRequest iRequest = createRequestContext(request);
        Long headId = Long.parseLong(headerId);
        voucherService.validateEcoupon(iRequest, headId);
        salesOrderService.invalidOrderToRemaining(iRequest, headId, remark);
     // 同步订单状态到dapp
        new Thread(() -> {
            SalesOrder order = commSalesOrderService.getOrder(iRequest, Long.valueOf(headerId), true, true);
            if(!StringUtils.isEmpty(order.getMemberId())){
                SalesOrder salesOrder = new SalesOrder();
                salesOrder.setHeaderId(Long.valueOf(headerId));
                salesOrderService.updateSyncFlag(salesOrder);
                salesOrderService.dappSync(iRequest, salesOrder);
            }
        }).start();
        ResponseData data = new ResponseData(true);
        return data;
    }

    /**
     * 订单失效（情况二），退款信息保存至PaymentRefund.
     * 
     * @param request
     *            统一上下文.
     * @param paymentRefunds
     *            退款信息集合.
     * @param headerId
     *            订单头Id.
     * @return ResponseData 退款成功，返回ture.
     * @throws CommMemberException
     *             会员异常
     * @throws CommOrderException
     *             订单异常
     * @throws CommVoucherException
     *             优惠券冲销事务处理异常
     */
    @RequestMapping(value = "/om/order/updatePaymentRefund")
    public ResponseData updateInvalidInfo(HttpServletRequest request, @RequestBody List<PaymentRefund> paymentRefunds,
            @RequestParam("headerId") String headerId)
                    throws CommOrderException, CommMemberException, CommVoucherException {
        IRequest iRequest = createRequestContext(request);
        Long headId = Long.parseLong(headerId);
        voucherService.validateEcoupon(iRequest, headId);
        salesOrderService.invalidOrderToRefund(iRequest, headId, paymentRefunds);
        // 同步订单状态到dapp
        new Thread(() -> {
            SalesOrder order = commSalesOrderService.getOrder(iRequest, Long.valueOf(headerId), true, true);
            if(!StringUtils.isEmpty(order.getMemberId())){
            SalesOrder salesOrder = new SalesOrder();
            salesOrder.setHeaderId(Long.valueOf(headerId));
            salesOrderService.updateSyncFlag(salesOrder);
            salesOrderService.dappSync(iRequest, salesOrder);
            }
        }).start();
        ResponseData data = new ResponseData(true);
        return data;
    }

    /**
     * 判断订单是否允许失效，若允许，获取支付信息返回页面.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头Id.
     * @return ResponseData 返回订单支付信息.
     */
    @RequestMapping(value = "/om/order/queryPayments")
    @ResponseBody
    public ResponseData queryPaymentsByHeaderId(HttpServletRequest request, @RequestParam("headerId") String headerId) {
        IRequest iRequest = createRequestContext(request);
        Long headId = Long.parseLong(headerId);
        boolean check = paymentRefundService.isInvalid(iRequest, headId);
        if (check) {
            List<PaymentRefund> payments = paymentRefundService.queryPaymentByHeaderId(iRequest, headId);
            ResponseData data = new ResponseData(payments);
            return data;
        } else {
            List<String> temp = new ArrayList<String>();
            temp.add("error");
            ResponseData data = new ResponseData(temp);
            return data;
        }
    }

    /**
     * 根据失效订单头Id获取市场组织参数.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头Id.
     * @return 返回组织参数信息
     */
    @RequestMapping(value = "/om/order/querySpmRefund")
    @ResponseBody
    public ResponseData queryOrderSpmRefund(HttpServletRequest request, @RequestParam("headerId") String headerId) {
        IRequest iRequest = createRequestContext(request);
        List<String> spmMarket = paymentRefundService.queryOrderSpmRefund(iRequest, Long.parseLong(headerId));
        ResponseData data = new ResponseData(spmMarket);
        return data;
    }

    /**
     * 根据订单头Id获取支付或退款信息集合.
     * 
     * @param request
     *            统一上下问.
     * @param headerId
     *            订单头Id.
     * @return 返回支付或退款信息集合
     */
    @RequestMapping(value = "/om/order/queryPaymentRefund")
    @ResponseBody
    public ResponseData queryRefundByHeaderId(HttpServletRequest request, @RequestParam("headerId") Long headerId) {
        IRequest iRequest = createRequestContext(request);
        List<PaymentRefund> payments = paymentRefundService.queryPayOrRefundByHeaderId(iRequest, headerId);
        ResponseData data = new ResponseData(payments);
        return data;
    }

    /**
     * 根据订单头id获取支付信息，用于订单详情也显示.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头id.
     * @return 返回支付信息.
     */
    @RequestMapping(value = "/om/orderinfo/queryPayments")
    @ResponseBody
    public ResponseData queryPaymentByHeaderId(HttpServletRequest request, @RequestParam("headerId") Long headerId) {
        IRequest iRequest = createRequestContext(request);
        List<PaymentRefund> payments = paymentRefundService.queryPaymentByHeaderId(iRequest, headerId);
        ResponseData data = new ResponseData(payments);
        return data;
    }
}
