/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.order.exception.OrderException;
import com.lkkhpg.dsis.admin.order.service.IOrderPaymentService;
import com.lkkhpg.dsis.admin.order.service.ISalesOrderService;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.member.dto.MemAttribute;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 订单支付Controller.
 * 
 * @author houmin
 *
 */
@Controller
public class OrderPaymentController extends BaseController {

    @Autowired
    private IOrderPaymentService orderPaymentService;

    @Autowired
    private ICommSalesOrderService commSalesOrderService;

    @Autowired
    private ISalesOrderService salesOrderService;

    /**
     * 查询订单对应的商品信息.
     * 
     * @param request
     *            统一上下文
     * @param orderHeaderId
     *            订单头ID
     * @return 对应订单包含商品列表信息
     * @throws OrderException
     *             查询订单商品信息异常
     */
    @RequestMapping(value = "/orderPayment/queryCommodityList")
    @ResponseBody
    public ResponseData queryCommodityList(HttpServletRequest request, Long orderHeaderId) throws OrderException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(orderPaymentService.selectByOrderHeaderId(requestContext, orderHeaderId));
    }

    /**
     * 订单支付.
     * 
     * @param request
     *            统一上下文
     * @param orderPayments
     *            订单支付行信息
     * @param orderHeaderId
     *            订单头id
     * @return 包含有发运头ID的List集合;可为空
     * @throws CommSystemProfileException
     *             系统异常
     * @throws CommOrderException
     *             订单异常
     * @throws CommDeliveryException
     *             发运异常
     * @throws CommMemberException
     *             会员异常
     * @throws CommVoucherException
     *             优惠券异常
     */
    @RequestMapping(value = "/orderPayment/createOrderPayment", method = RequestMethod.POST)
    public ResponseData insertPaymentInfo(HttpServletRequest request, @RequestBody List<OrderPayment> orderPayments,
            @RequestParam Long orderHeaderId) throws CommSystemProfileException, CommOrderException,
            CommDeliveryException, CommMemberException, CommVoucherException {
        IRequest iRequest = createRequestContext(request);
        List<Long> deliveryIds = orderPaymentService.createOrderPayment(iRequest, orderPayments, orderHeaderId);

        // 同步订单到dapp系统 - 订单状态为“已完成”时
        new Thread(() -> {
            // 获取订单详细信息
            SalesOrder order = commSalesOrderService.getOrder(iRequest, orderHeaderId, true, true);
            if (!StringUtils.isEmpty(order.getMemberId())) {
                salesOrderService.updateSyncFlag(order);
                salesOrderService.dappSync(iRequest, order);
            }
        }).start();

        return new ResponseData(deliveryIds);
    }

    /**
     * 根据订单头id获取可用Ecupon.
     * 
     * @param request
     *            统一上下文.
     * @param orderHeaderId
     *            订单头Id.
     * @return 返回结果.
     */
    @RequestMapping(value = "/orderPayment/queryEcupon")
    @ResponseBody
    public ResponseData queryEcupon(HttpServletRequest request, Long orderHeaderId) {
        IRequest iRequest = createRequestContext(request);
        List<Voucher> ecupons = orderPaymentService.queryEcupon(iRequest, orderHeaderId);
        ResponseData data = new ResponseData(ecupons);
        return data;
    }

    /**
     * 校验银行卡口令是否正确.
     * 
     * @param request
     *            统一上下文.
     * @param attribute
     *            会员属性值.
     * @return 返回结果.
     */
    @RequestMapping(value = "/om/payment/checkBank")
    @ResponseBody
    public ResponseData checkPassword(HttpServletRequest request, @RequestBody MemAttribute attribute) {
        IRequest iRequest = createRequestContext(request);
        List<String> result = orderPaymentService.checkPassword(iRequest, attribute);
        ResponseData data = new ResponseData(result);
        return data;
    }

    /**
     * 获取当前会员完整的银行卡信息（解密卡号）.
     * 
     * @param memberId
     *            会员id.
     * @param request
     *            统一上下文.
     * @return 返回结果.
     */
    @RequestMapping(value = "/om/query/bankInfo")
    @ResponseBody
    public ResponseData queryBankCard(Long memberId, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        List<MemCard> result = orderPaymentService.queryBankInfo(iRequest, memberId);
        ResponseData data = new ResponseData(result);
        return data;
    }

    /**
     * 已完成订单，订单详情页更新支付信息.
     * 
     * @param request
     *            统一上下文
     * @param orderPayments
     *            支付信息
     * @param orderHeaderId
     *            订单头Id
     * @return 结果集合
     * @throws CommOrderException
     */
    @RequestMapping(value = "/om/update/paymentInfo")
    @ResponseBody
    public ResponseData updatePaymentData(HttpServletRequest request, @RequestBody List<OrderPayment> orderPayments,
            @RequestParam Long orderHeaderId) throws CommOrderException {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(orderPaymentService.updatePaymentAfterPay(iRequest, orderHeaderId, orderPayments));
    }

}