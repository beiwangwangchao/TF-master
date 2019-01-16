/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.mws.controllers;

import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.constant.TFInterfaceConstants;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.order.dto.OrderPayTransaction;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.common.service.IMwsOrderPaymentService;
import com.lkkhpg.dsis.common.service.IOrderPayTransactionService;
import com.lkkhpg.dsis.common.service.ISpmSalesOrganizationService;
import com.lkkhpg.dsis.integration.payment.dto.PaymentResult;
import com.lkkhpg.dsis.integration.payment.service.ITianFuPaymentTransationService;
import com.lkkhpg.dsis.integration.utils.IpUtils;
import com.lkkhpg.dsis.mws.constant.MwsOrderConstants;
import com.lkkhpg.dsis.mws.service.IOrderPaymentResultService;
import com.lkkhpg.dsis.mws.system.MwsServiceRequest;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.service.ICodeService;
import com.lkkhpg.dsis.platform.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mws订单支付跳转页面controller.
 *
 * @author Zhaoqi
 */
@Controller
public class OrderPaymentController extends BaseController {

    @Autowired
    private IOrderPaymentResultService orderPaymentResultService;
    @Autowired
    private ICommSalesOrderService salesOrderService;
    @Autowired
    private ISpmSalesOrganizationService spmSalesOrganizationService;


    //20180419
    @Autowired
    private ITianFuPaymentTransationService iTianFuPaymentTransationService;
    @Autowired
    private IMwsOrderPaymentService iMwsOrderPaymentService;

    @Autowired
    private ICodeService codeService;

    /**
     * 支付确认页面.
     */
    public static final String ORDER_PAYMENT = "/order/order-select-payment";

    public static final String HK_MARKET = "HK";

    public static final String ORDER_PAYMENT_DRAFT = "/order-generate-payment-trans";

    public static final String ORDER_PAYMENT_RESULT = "/order/payment_order_result";


    /**
     * 根据订单id去订单信息.
     *
     * @param request  统一上下文
     * @param headerId 订单id
     * @return 返回订单信息
     * @throws CommSystemProfileException
     * @throws CommMemberException
     * @throws CommDeliveryException
     * @throws CommOrderException
     */
    @RequestMapping(value = ORDER_PAYMENT)
    public ModelAndView paymentResult(HttpServletRequest request, Long headerId)
            throws CommOrderException, CommDeliveryException, CommMemberException, CommSystemProfileException {
        ModelAndView result = new ModelAndView();
        IRequest iRequest = createRequestContext(request);
        SalesOrder salesOrder = orderPaymentResultService.paymentResult(iRequest, headerId);
        SpmSalesOrganization spmOrg = spmSalesOrganizationService.getSalesOrganizationDetail(iRequest,
                salesOrder.getSalesOrgId());
        HttpSession session = request.getSession();
        // 先检查访问页面时是否有token
        String sessionToken = (String) session.getAttribute(MwsOrderConstants.ORDER_CONFIRM_TOKEN);
        if (salesOrder.getActrualPayAmt().compareTo(BigDecimal.ZERO) == 0) {
            salesOrder = orderPaymentResultService.payedSalesOrderWithTransaction(iRequest, salesOrder);
            result.setViewName(getViewPath() + ORDER_PAYMENT_RESULT);
            result.addObject("salesOrder", salesOrder);
        } else {
//            if (HK_MARKET.equals(spmOrg.getMarket().getCode())) {
            result.setViewName(getViewPath() + ORDER_PAYMENT);
            result.addObject("marketCode", spmOrg.getMarket().getCode());
            result.addObject("orderNumber", salesOrder.getOrderNumber());
            result.addObject("actrualPayAmt", salesOrder.getActrualPayAmt());
            result.addObject("orderStatus", salesOrder.getOrderStatus());
            result.addObject("sourceType", "ORDER");
            result.addObject("sourceKey", headerId);
/*
} else {
result.setViewName(getViewPath() + ORDER_PAYMENT);
result.addObject("marketCode", spmOrg.getMarket().getCode());
result.addObject("orderNumber", salesOrder.getOrderNumber());
result.addObject("actrualPayAmt", salesOrder.getActrualPayAmt());
result.addObject("orderStatus", salesOrder.getOrderStatus());
result.addObject("sourceType", "ORDER");
result.addObject("sourceKey", headerId);
}
*/
        }

        return result;
    }


    @RequestMapping(value = "/order/order-query-payment")
    public ResponseData paymentSearch(HttpServletRequest request, Long headerId) {
        ResponseData result = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        SalesOrder salesOrder = orderPaymentResultService.paymentResult(iRequest, headerId);
        result.setCode(salesOrder.getOrderStatus());
        return result;
    }


    @RequestMapping(value = ORDER_PAYMENT_DRAFT ,method = RequestMethod.POST)
    public SalesOrder generateTransaction(HttpServletRequest request, String orderNumber)
            throws CommOrderException {
        IRequest iRequest = createRequestContext(request);
        SalesOrder order = new SalesOrder();
        String billIp = null;
        order.setOrderNumber(orderNumber);
        //OrderPayTransaction result;
        // result = orderPayTranactionService.generateTransaction(iRequest, orderPayTransaction);
        List<SalesOrder> list = salesOrderService.queryOrdersId(iRequest, order);
        order = salesOrderService.getOrder(iRequest, list.get(0).getHeaderId(), false, false);
        try {
            billIp = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
         e.printStackTrace();
        }
        order.setIp(billIp);
        return order;
    }

    /**
     * 更新订单为支付中状态.
     *
     * @param request             统一上下文
     * @param orderPayTransaction
     * @return 返回处理结果
     */
    @RequestMapping(value = "/salesorder/paying")
    public Map<Object, Object> payingSalesOrder(HttpServletRequest request, OrderPayTransaction orderPayTransaction) {
        IRequest iRequest = createRequestContext(request);
        return orderPaymentResultService.payingSalesOrder(iRequest, orderPayTransaction);
    }

    @RequestMapping(value = "/salesorder/result")
    public SalesOrder payedSalesOrder(HttpServletRequest request, PaymentResult paymentResult)
            throws CommOrderException, CommDeliveryException, CommMemberException, CommSystemProfileException {
        // HashMap<Object, Object> result = new HashMap<Object, Object>();
        IRequest iRequest = createRequestContext(request);
        return orderPaymentResultService.payedSalesOrder(iRequest, paymentResult);
    }

    @RequestMapping(value = "account/orderNumber/selectHeaderId")
    public SalesOrder selectHeaderId(HttpServletRequest request,String orderNumber){
        IRequest iRequest = createRequestContext(request);

        SalesOrder salesOrder = orderPaymentResultService.queryOrderByOrderNumber(iRequest, orderNumber);

        return salesOrder;
    }



    @RequestMapping(value = "/salesorder/error")
    public SalesOrder failSalesOrder(HttpServletRequest request, PaymentResult paymentResult)
            throws CommOrderException, CommDeliveryException, CommMemberException, CommSystemProfileException {
        // HashMap<Object, Object> result = new HashMap<Object, Object>();
        // IRequest iRequest = createRequestContext(request);
        IRequest iRequest = new MwsServiceRequest();

        return orderPaymentResultService.failSalesOrder(iRequest, paymentResult);
    }

    // @RequestMapping(value = "/salesorder/listening")
    // public void listeningSalesOrder()
    // throws CommOrderException, CommDeliveryException, CommMemberException,
    // CommSystemProfileException {
    //
    // orderPaymentResultService.listenPayTransaction();
    // }


    @RequestMapping(value = "/order/payment_success")
    public ModelAndView paymentSuccess(HttpServletRequest request, Long headerId1)
            throws CommOrderException, CommDeliveryException, CommMemberException, CommSystemProfileException {
        System.out.println(headerId1);
        ModelAndView resultSucc = new ModelAndView();
        IRequest iRequest = createRequestContext(request);
        SalesOrder salesOrder = orderPaymentResultService.paymentResult(iRequest, headerId1);
        SpmSalesOrganization spmOrg = spmSalesOrganizationService.getSalesOrganizationDetail(iRequest,
                salesOrder.getSalesOrgId());

        String orderNumber = salesOrder.getOrderNumber();
        HttpSession session = request.getSession();
        // 先检查访问页面时是否有token
        String sessionToken = (String) session.getAttribute(MwsOrderConstants.ORDER_CONFIRM_TOKEN);
       if (salesOrder.getActrualPayAmt().compareTo(BigDecimal.ZERO) == 0) {

            Date payDate=null;
            SimpleDateFormat myFmt=new SimpleDateFormat("yy-MM-ddHH:mm:ss");
            try{
                payDate= myFmt.parse(myFmt.format(new Date()));
            } catch (Exception e) {
               e.printStackTrace();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderStatus","COMP");
            map.put("payDate",payDate);
            map.put("dappSyncFlag","N");

           SalesLogistics feeShippingInfo = iMwsOrderPaymentService.queryFreightByHeaderId(headerId1);
           //如果没有运费信息，则设为N，否则设为Y
            if (feeShippingInfo == null) {
                map.put("freeShipping", "N");
            } else {
                map.put("freeShipping", "Y");
            }
            iTianFuPaymentTransationService.updateSalesOrderStatus(map,orderNumber);

        }

//        salesOrder = orderPaymentResultService.paymentResult(iRequest, headerId1);

        resultSucc.setViewName(getViewPath() + "/payment/pay/payment_success");
        resultSucc.addObject("orderNumber", salesOrder.getOrderNumber());
        resultSucc.addObject("actrualPayAmt", salesOrder.getActrualPayAmt());
        Long headerId=headerId1;
        resultSucc.addObject("sourceKey", headerId);
        resultSucc.addObject("marketCode", spmOrg.getMarket().getCode());
        resultSucc.addObject("orderStatus", salesOrder.getOrderStatus());
        resultSucc.addObject("sourceType", "ORDER");
        return resultSucc;

    }


    @RequestMapping(value = "/pay/getBankCode")
    public ResponseData getBankCode(HttpServletRequest request, String bankName){
        IRequest iRequest = createRequestContext(request);
        List<String> stringList =  codeService.getBankCodeByBankName(iRequest,bankName);
        return new ResponseData(stringList);
    }
}
