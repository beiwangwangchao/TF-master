/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.controllers;

import com.lkkhpg.dsis.admin.order.exception.OrderException;
import com.lkkhpg.dsis.admin.order.pojo.SalesOrderDetail;
import com.lkkhpg.dsis.admin.order.service.ISalesOrderService;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxQuery;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxHeadService;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxQueryService;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.order.dto.Invoice;
import com.lkkhpg.dsis.common.order.dto.ReportSales;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.common.service.IVoucherService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 销售订单的控制层.
 *
 * @author wuyichu
 */
@Controller
public class SalesOrderController extends BaseController {

    @Autowired
    private ISalesOrderService salesOrderService;

    @Autowired
    private IVoucherService voucherService;

    @Autowired
    private ICommSalesOrderService commSalesOrderService;

    @Autowired
    private IDiscountTrxQueryService discountTrxQueryService;

    @Autowired
    private IDiscountTrxHeadService discountTrxHeadService;

    /**
     * 快速操作小界面路径.
     */
    public static final String TO_OM_SHORTCUT_OPERATION = "admin/om/om_shortcut_operation";

    /**
     * 销售订单新建路径.
     */
    public static final String TO_OM_SALES_CREATE = "admin/om/om_order_create";

    /**
     * 销售订单确认路径.
     */
    public static final String TO_OM_SALES_CONFIRM = "admin/om/om_order_confirm";

    /**
     * 销售订单详情路径.
     */
    public static final String TO_OM_SALES_DETAIL = "admin/om/om_order_detail";

    /**
     * 根据条件查询销售订单.
     *
     * @param request
     *            http请求
     * @param order
     *            销售订单
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 满足查询条件的销售订单
     */
    @RequestMapping(value = "om/order/queryOrders")
    @ResponseBody
    public ResponseData queryOrders(HttpServletRequest request, SalesOrder order, int page, int pagesize) {

        List<SalesOrder> orders = salesOrderService.queryOrders(createRequestContext(request), order, page, pagesize);

        return new ResponseData(orders);
    }

    /**
     * 根据条件查出销售报表
     * @param request
     * @param reportSales
     * @param page
     * @param pagesize
     * @return
     */
    @RequestMapping(value = "om/order/reportSales")
    @ResponseBody
    public ResponseData reportSales(HttpServletRequest request, ReportSales reportSales, int page, int pagesize) {

        List<ReportSales> reportSalesList = salesOrderService.reportSales(createRequestContext(request), reportSales, page, pagesize);

        return new ResponseData(reportSalesList);
    }





    /**
     * 根据订单编号查订单ID.
     *
     * @param request
     *            请求基础数据
     * @param order
     *            销售订单
     * @return 销售订单信息
     */
    @RequestMapping(value = "om/order/queryOrdersId")
    @ResponseBody
    public ResponseData queryOrdersId(HttpServletRequest request, SalesOrder order) {

        List<SalesOrder> orders = salesOrderService.queryOrdersId(createRequestContext(request), order);

        return new ResponseData(orders);
    }

    /**
     * 查询销售订单明细.
     *
     * @param request
     *            http请求
     * @param orderId
     *            订单头id
     * @return 查询不到则返回null
     */
    @ResponseBody
    @RequestMapping(value = "om/order/getDetailOrder")
    public SalesOrder getDetailOrder(final HttpServletRequest request, final Long orderId) {
        SalesOrder order = salesOrderService.getDetailOrder(createRequestContext(request), orderId);
        return order;
    }

    /**
     * 快速操作小工具页面.
     *
     * @param request
     *            http请求
     * @param order
     *            订单查询条件
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 1.显示本人操作的最新的10条订单，并且提供搜索功能. 2.显示当前用户的信息，且可搜索会员信息.
     */
    @RequestMapping(value = "om/order/om_shortcut_operation")
    public ModelAndView om_shortcut_operation(HttpServletRequest request, SalesOrder order,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        ModelAndView modelAndView = new ModelAndView(TO_OM_SHORTCUT_OPERATION);
        IRequest iRequest = createRequestContext(request);

        order.setCreateUserId(iRequest.getAccountId());
        List<SalesOrder> orders = salesOrderService.queryOrders(iRequest, order, page, pagesize);

        modelAndView.addObject("orders", orders);

        return modelAndView;
    }

    /**
     * 销售订单保存.
     *
     * @param request
     *            http请求
     * @param order
     *            销售订单
     * @param result
     *            校验结果
     * @return 传入为空则返回空.
     * @throws CommOrderException
     *             写入或修改出错时抛出
     * @throws CommVoucherException
     *             优惠券校验出错时抛出
     */
    @RequestMapping(value = "om/order/submit")
    @ResponseBody
    public ResponseData submit(final HttpServletRequest request, @RequestBody final SalesOrder order,
            final BindingResult result) throws CommOrderException, CommVoucherException {
        getValidator().validate(order, result);
        ResponseData responseData = new ResponseData();
        final IRequest iRequest = createRequestContext(request);
        order.setOrderStatus("CHECK01");
        salesOrderService.submitOrder(iRequest, order);
        // 同步订单到dapp系统
        if (!"SAV".equals(order.getOrderStatus()) && !"WPAY".equals(order.getOrderStatus())) {
            if (!StringUtils.isEmpty(order.getMemberId())) {
                new Thread(() -> {
                    salesOrderService.updateSyncFlag(order);
                    salesOrderService.dappSync(iRequest, order);
                }).start();
            }
        }

        List<SalesOrder> rows = new ArrayList<SalesOrder>();
        rows.add(order);
        responseData.setRows(rows);
        responseData.setSuccess(true);
        return responseData;
    }

    /**
     * 删除销售订单.
     *
     * @param request
     *            http请求
     * @param order
     *            订单信息
     * @return true/false
     */
    @RequestMapping(value = "om/order/remove")
    @ResponseBody
    public ResponseData delete(final HttpServletRequest request, @RequestBody final SalesOrder order) {
        ResponseData responseData = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        salesOrderService.deleteOrder(iRequest, order);
        responseData.setSuccess(true);
        return responseData;
    }

    /**
     * 删除销售订单行.
     *
     * @param request
     *            http请求
     * @param lines
     *            订单行集合
     * @return true
     */
    @RequestMapping(value = "om/order/line/remove")
    @ResponseBody
    public ResponseData deleteLine(final HttpServletRequest request, @RequestBody final List<SalesLine> lines) {
        ResponseData responseData = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        salesOrderService.deleteLine(iRequest, lines);
        responseData.setSuccess(true);
        return responseData;
    }

    /**
     * 删除支付调整.
     *
     * @param request
     *            http请求
     * @param adjustmentId
     *            支付调整id
     * @return true
     */
    @RequestMapping(value = "om/order/adjust/remove")
    @ResponseBody
    public ResponseData deleteAdjustMent(final HttpServletRequest request, final Long adjustmentId) {
        ResponseData responseData = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        salesOrderService.deleteAdjustment(iRequest, adjustmentId);
        responseData.setSuccess(true);
        return responseData;
    }

    /**
     * 批量删除销售订单.
     *
     * @param request
     *            统一上下文
     * @param orders
     *            订单集合
     * @return true/false
     */
    @RequestMapping(value = "om/order/batch/remove")
    @ResponseBody
    public ResponseData deleteOrderList(final HttpServletRequest request, @RequestBody final List<SalesOrder> orders) {
        ResponseData responseData = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        salesOrderService.deleteOrderList(iRequest, orders);
        responseData.setSuccess(true);
        return responseData;
    }

    /**
     * 批量删除销售订单.
     *
     * @param request
     *            统一上下文
     * @param orders
     *            订单集合
     * @return true/false
     */
    @RequestMapping(value = "om/order/batch/cancel")
    @ResponseBody
    public ResponseData cancelOrderList(final HttpServletRequest request, @RequestBody final List<SalesOrder> orders)
            throws CommOrderException, CommVoucherException {
        ResponseData responseData = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        for (SalesOrder order : orders) {
            order.setOrderStatus(OrderConstants.SALES_STATUS_CANCELED);
            salesOrderService.updateOrderBySelective(iRequest, order);
        }
        responseData.setSuccess(true);
        return responseData;
    }

    /**
     * 更新订单.
     *
     * @param request
     *            统一上下文
     * @param order
     *            订单集合
     * @return 相应数据
     * @throws CommOrderException
     *             订单统一异常
     * @throws CommVoucherException
     *             优惠券统一异常
     */
    @RequestMapping(value = "om/order/update")
    @ResponseBody
    public ResponseData updateOrder(HttpServletRequest request, @RequestBody SalesOrder order)
            throws CommOrderException, CommVoucherException {
        ResponseData responseData = new ResponseData();
        final IRequest iRequest = createRequestContext(request);
        voucherService.validateEcoupon(iRequest, order.getHeaderId());
        salesOrderService.updateOrderBySelective(iRequest, order);
        // 同步更新后的订单到dapp
        if (!"SAV".equals(order.getOrderStatus()) && !"WPAY".equals(order.getOrderStatus())) {
            SalesOrder salesOrder = commSalesOrderService.getOrder(iRequest, order.getHeaderId(), true, true);
            if (!StringUtils.isEmpty(salesOrder.getMemberId())) {
                new Thread(() -> {
                    salesOrderService.updateSyncFlag(order);
                    salesOrderService.dappSync(iRequest, order);
                }).start();
            }
        }
        //如果为取消订单 查看是否有优惠，
        //信用额度还原
        //查询订单详细信息
        if("CANCL".equals(order.getOrderStatus())) {
            SalesOrder salesOrder1=commSalesOrderService.getOrder(iRequest,order.getHeaderId(),false,false);
            BigDecimal dis=salesOrder1.getDiscountAmt();
            if (dis.compareTo(BigDecimal.ZERO) > 0) {
                //先查一下是否有包含该订单的信用额度调整
                DiscountTrxQuery discountTrxQuery=new DiscountTrxQuery();
                discountTrxQuery.setAttribute1(salesOrder1.getOrderNumber());
                //如果有则将该信用额度调整的状态设为取消
                List<DiscountTrxQuery> list =discountTrxQueryService.query(discountTrxQuery);
                if(CollectionUtils.isNotEmpty(list)){
                    discountTrxHeadService.canclTrx(salesOrder1.getOrderNumber());
                }
            }
        }

        responseData.setSuccess(true);
        return responseData;
    }

    /**
     * 订单新建页面信息获取.
     *
     * @param request
     *            请求上下文
     * @param salesOrderDetail
     *            订单详情数据
     * @return 订单创建需要的基础数据.
     * @throws OrderException
     *             订单异常
     */
    @RequestMapping(value = "om/om_order_create.html")
    public ModelAndView createOrder(HttpServletRequest request, SalesOrderDetail salesOrderDetail)
            throws OrderException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(TO_OM_SALES_CREATE);
        IRequest requestContext = createRequestContext(request);
        Map<String, Object> basicData = salesOrderService.getBasicDataForCreate(requestContext, salesOrderDetail);
        modelAndView.addAllObjects(basicData);
        return modelAndView;
    }

    /**
     * 订单确认页面信息获取.
     *
     * @param request
     *            请求上下文
     * @param salesOrderDetail
     *            订单详情数据
     * @return 订单创建需要的基础数据.
     * @throws OrderException
     *             订单异常
     */
    @RequestMapping(value = "om/om_order_confirm.html")
    public ModelAndView confirmOrder(HttpServletRequest request, SalesOrderDetail salesOrderDetail)
            throws OrderException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(TO_OM_SALES_CONFIRM);
        IRequest requestContext = createRequestContext(request);
        Map<String, Object> basicData = salesOrderService.getBasicDataForConfirm(requestContext, salesOrderDetail);
        modelAndView.addAllObjects(basicData);
        return modelAndView;
    }
    /**
     * 订单确认页面信息获取.
     *
     * @param request
     *            请求上下文
     * @param salesOrderDetail
     *            订单详情数据
     * @return 订单创建需要的基础数据.
     * @throws OrderException
     *             订单异常
     */
    @RequestMapping(value = "om/om_order_detail.html")
    public ModelAndView confirmDetail(HttpServletRequest request, SalesOrderDetail salesOrderDetail)
            throws OrderException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(TO_OM_SALES_DETAIL);
        IRequest requestContext = createRequestContext(request);
        Map<String, Object> basicData = salesOrderService.getBasicDataForConfirm(requestContext, salesOrderDetail);
        modelAndView.addAllObjects(basicData);
        return modelAndView;
    }

    /**
     * 订单信息页面跳转.
     *
     * @param request
     *            请求上下文
     * @param orderId
     *            订单头id
     * @return 订单信息的展示页面跳转
     */
    @RequestMapping(value = "om/om_order_info")
    @ResponseBody
    public Map<String, String> orderInfo(HttpServletRequest request, Long orderId) {
        Locale locale = RequestContextUtils.getLocale(request);
        IRequest requestContext = createRequestContext(request);
        Map<String, String> returnUrl = salesOrderService.orderInfoUrl(requestContext, orderId);
        String titleCode = returnUrl.get(OrderConstants.ORDER_TAB_TITLE);
        String titleMsg = getMessageSource().getMessage(titleCode, null, locale);
        returnUrl.put(OrderConstants.ORDER_TAB_TITLE, titleMsg);

        return returnUrl;
    }

    /**
     * 查询headerId
     * @param request
     * @param orderNumber
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "om/orderInfo/headerId")
    @ResponseBody
    public ResponseData selectHeaderId(HttpServletRequest request,String orderNumber)throws Exception{
        System.out.println(orderNumber);
        List<SalesOrder> salesOrderList=salesOrderService.selectHeaderId(orderNumber);
        return  new ResponseData(salesOrderList);
    }

    /**
     * 获取订单可用的奖金区间.
     *
     * @param request
     *            请求上下文
     * @param orderId
     *            订单头ID
     * @return 可用的奖金区间
     */
    @RequestMapping(value = "om/check_order_period")
    @ResponseBody
    public ResponseData checkOrderPeriod(HttpServletRequest request, Long orderId) {
        IRequest requestContext = createRequestContext(request);
        List<SpmPeriod> spmPeriods = salesOrderService.checkOrderPeriod(requestContext, orderId);
        return new ResponseData(spmPeriods);
    }

    /**
     * 修改订单的奖金区间以及支付日期.
     *
     * @param request
     *            统一上下文
     * @param orderDetail
     *            请求订单数据
     * @return 是否修改成功
     * @throws OrderException
     *             数据校验出错时抛出
     */
    @RequestMapping(value = "om/update_period_paydate")
    @ResponseBody
    public ResponseData updateOrderPeriodAndPayDate(HttpServletRequest request, SalesOrderDetail orderDetail)
            throws OrderException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(salesOrderService.updateOrderPeriod(requestContext, orderDetail));
    }

    /**
     * 查询发票文件id.
     *
     * @param request
     *            统一上下文
     * @param orderId
     *            订单id
     * @return 发票id
     *
     * @throws CommOrderException
     *             订单统一异常
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "om/print_invoice")
    @ResponseBody
    public ResponseData printInvoice(HttpServletRequest request, Long orderId)
            throws CommOrderException, CommSystemProfileException {
        IRequest requestContext = createRequestContext(request);
        Invoice invoice = salesOrderService.printInvoice(requestContext, orderId);
        List<Invoice> list = new ArrayList<Invoice>();
        list.add(invoice);
        return new ResponseData(list);
    }

    /**
     * 订单有待支付状态改到已保存状态.
     *
     * @param request
     *            请求上下文
     * @param orderId
     *            订单id
     * @return 响应数据集
     * @throws CommOrderException
     *             订单异常
     * @throws CommVoucherException
     *             优惠券异常
     */
    @RequestMapping(value = "om/order/paytosave")
    @ResponseBody
    public ResponseData payToSave(HttpServletRequest request, Long orderId)
            throws CommOrderException, CommVoucherException {
        IRequest requestContext = createRequestContext(request);
        salesOrderService.orderWPayToSave(requestContext, orderId);
        return new ResponseData();
    }

    @RequestMapping(value = "test/job")
    public void testJob(){
        try {
            //salesOrderService.publishSysMessage("test123456",61000001L,"61000278");
            salesOrderService.modifyWPayOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
