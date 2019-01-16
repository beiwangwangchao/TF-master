/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lkkhpg.dsis.common.discount.dto.DiscountTrxQuery;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxHeadService;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxQueryService;
import com.lkkhpg.dsis.platform.service.attachment.ISysFileService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.order.dto.Invoice;
import com.lkkhpg.dsis.common.order.dto.OmMwsOrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.service.ICommItemService;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.common.service.IInvoiceService;
import com.lkkhpg.dsis.common.service.IMwsOrderPaymentService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.common.service.ISpmLocationService;
import com.lkkhpg.dsis.mws.constant.ProductConstants;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.exception.TokenException;

/**
 * MWS订单历史controller.
 * 
 * @author zihao.yang
 *
 */

@Controller
public class OrderHistoryController extends BaseController {
    @Autowired
    private ICommSalesOrderService commSalesOrderService;

    @Autowired
    private ISpmLocationService spmLocationService;

    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IMwsOrderPaymentService mwsOrderPaymentService;

    @Autowired
    private IParamService paramService;

    @Autowired
    private ICommItemService itemService;

    @Autowired
    private IDiscountTrxQueryService discountTrxQueryService;

    @Autowired
    private IDiscountTrxHeadService discountTrxHeadService;

    @Autowired
    private ISysFileService sysFileMapper;

    /**
     * 获取订单与对应的商品.
     * 
     * @param request
     *            请求上下文
     * @param salesOrder
     *            销售订单
     * @param page
     *            开始页数
     * @param pagesize
     *            每页大小
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/orderHistory/queryProductsByOrderNumber")
    @ResponseBody
    public ResponseData queryProductsByOrderNumber(HttpServletRequest request, SalesOrder salesOrder,
            @RequestParam(defaultValue = ProductConstants.PAGE) int page,
            @RequestParam(defaultValue = ProductConstants.PAGESIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);

        // 查询订单头
        salesOrder.setMemberId(requestContext.getAttribute(Member.FIELD_MEMBER_ID));
        salesOrder.setMarketId(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        List<SalesOrder> salesOrderList = commSalesOrderService.queryMwsOrders(requestContext, salesOrder, page,
                pagesize);
        for (int i = 0; i < salesOrderList.size(); i++) {
            // List<Object> row = new ArrayList<Object>();
            // 获取订单详细信息
            Long headerId = salesOrderList.get(i).getHeaderId();
            salesOrder = commSalesOrderService.getDetailOrder(requestContext, headerId);
            // 查询订单头对应的订单行
            List<SalesLine> salesLineList = new ArrayList<SalesLine>();
            salesLineList = commSalesOrderService
                    .getOrder(requestContext, salesOrderList.get(i).getHeaderId(), true, false).getLines();

            //加载图片
            for (int j = 0; j < salesLineList.size(); j++) {
                Long imgFileId = sysFileMapper.selectFileIdBySourceKey(salesLineList.get(j).getItemId());
                salesLineList.get(j).setFileId(imgFileId);
            }

            salesOrderList.get(i).setLines(salesLineList);
        }
        ResponseData responseData = new ResponseData(salesOrderList);
        return responseData;
    }

    /**
     * 更加headerId获取订单与对应的商品.
     * 
     * @param request
     *            请求上下文
     * @param salesOrder
     *            销售订单
     * 
     * 
     * @param page
     *            开始页数
     * @param pagesize
     *            每页大小
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/orderHistory/queryProductsByHeadId")
    @ResponseBody
    public ResponseData queryProductsByHeadId(HttpServletRequest request, SalesOrder salesOrder,
            @RequestParam(defaultValue = ProductConstants.PAGE) int page,
            @RequestParam(defaultValue = ProductConstants.PAGESIZE) int pagesize) {
        // 获取前台参数
        IRequest requestContext = createRequestContext(request);
        Long headerId = salesOrder.getHeaderId();

        // 验证订单头Id
        salesOrder.setMemberId(requestContext.getAttribute(Member.FIELD_MEMBER_ID));

        List<SalesOrder> salesOrderList = commSalesOrderService.queryMwsOrders(requestContext, salesOrder, page,
                pagesize);
        if (salesOrderList.size() == 0) {
            ResponseData RDfalse = new ResponseData();
            RDfalse.setSuccess(false);
            return RDfalse;
        }
        salesOrder = commSalesOrderService.getDetailOrder(requestContext, headerId);
        salesOrder.setOrderStatusDesc(salesOrderList.get(0).getOrderStatusDesc());
        salesOrderList.remove(0);
        salesOrderList.add(salesOrder);

        for (SalesLine salesLine : salesOrder.getLines()) {
            InvItem item = itemService.queryItemDetails(requestContext, salesLine.getItemId());
            salesLine.setItemDescription(item.getDescription());

            Long imgFileId = sysFileMapper.selectFileIdBySourceKey(salesLine.getItemId());
            salesLine.setFileId(imgFileId);
        }
        // 设置返回值
        List<Object> row = new ArrayList<Object>();
        // 判断不同配置方式下获取配送地址
        if (salesOrder.getDeliveryType().equals(OrderConstants.ORDER_DELIVERY_SHIPP)) {
            List<SalesSites> ssList = salesOrder.getSalesSites();
            if (ssList.size() != 0) {
                salesOrder.setAttribute1(ssList.get(0).getName() + ", " + ssList.get(0).getPhone() + ", <br>"
                        + ssList.get(0).getAddress());
            } else {
                salesOrder.setAttribute1("");

            }
        } else if (salesOrder.getDeliveryType().equals(OrderConstants.ORDER_DELIVERY_PCKUP)) {
            Long id = salesOrder.getSalesOrganization().getLocationId();

            SpmLocation location = new SpmLocation();
            location.setLocationId(id);
            List<SpmLocation> llist = spmLocationService.queryLocation(requestContext, location, 1, 1);
            if (llist.size() != 0) {
                salesOrder.setAttribute1(
                        salesOrder.getSalesOrganization().getName() + "<br>" + llist.get(0).getAddress());
            } else {
                salesOrder.setAttribute1("");

            }
        } else {
            salesOrder.setAttribute1("");
        }
        // rb是否可见
        List<String> isRBUse = paramService.getParamValues(requestContext, SystemProfileConstants.PARAM_PAY_BY_RB,
                SystemProfileConstants.ORG_TYPE_SALES);
        if (isRBUse.size() < 1) { // 如果没有设置组织参数默认为库存不足允许下单
            row.add(BaseConstants.NO);
        } else {
            row.add(isRBUse.iterator().next());
        }
        return new ResponseData(salesOrderList);
    }

    /**
     * 获取订单与对应的商品.
     * 
     * @param request
     *            请求上下文
     * @param salesOrder
     * 
     * @param _token
     * @return responseData 响应数据
     * @throws CommVoucherException
     * @throws CommOrderException
     * @throws TokenException
     */
    @RequestMapping(value = "/orderHistory/cannelOrder")
    @ResponseBody
    public ResponseData updateOrderStatus(HttpServletRequest request, SalesOrder salesOrder, String _token)
            throws CommOrderException, CommVoucherException, TokenException {
        IRequest requestContext = createRequestContext(request);
        //salesOrder.set__status(__status);
        salesOrder.set_token(_token);
        checkToken(request, salesOrder);
        ResponseData responseData = new ResponseData();

        // ecoupon、RB还原
        OmMwsOrderPayment mwsOrderPayment = new OmMwsOrderPayment();
        mwsOrderPayment.setOrderHeaderId(salesOrder.getHeaderId());
        mwsOrderPayment.setStatus(OrderConstants.SALES_STATUS_CANCELED);
        mwsOrderPaymentService.updateRemainingBalValue(requestContext, mwsOrderPayment);

        // 优惠券还原

        salesOrder.setHeaderId(salesOrder.getHeaderId());
        salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_CANCELED);
        commSalesOrderService.updateOrderBySelective(requestContext, salesOrder);

        //信用额度还原
        //查询订单详细信息
        SalesOrder salesOrder1=commSalesOrderService.getOrder(requestContext,salesOrder.getHeaderId(),false,false);
        BigDecimal dis=salesOrder1.getDiscountAmt();
        if(dis.compareTo(BigDecimal.ZERO) > 0) {
            //先查一下是否有包含该订单的信用额度调整
            DiscountTrxQuery discountTrxQuery=new DiscountTrxQuery();
            discountTrxQuery.setAttribute1(salesOrder1.getOrderNumber());
            //如果有则将该信用额度调整的状态设为取消
            List<DiscountTrxQuery> list =discountTrxQueryService.query(discountTrxQuery);
            if(CollectionUtils.isNotEmpty(list)){
                discountTrxHeadService.canclTrx(salesOrder1.getOrderNumber());
            }
        }

        int flag = commSalesOrderService.updateOrderStatus(requestContext, salesOrder.getHeaderId(),
                OrderConstants.SALES_STATUS_CANCELED);
        if (flag == 0) {
            responseData.setSuccess(false);
        }

        return responseData;
    }

    /**
     * 获取当前订单发票附件id.
     * 
     * @param request
     *            统一上下文.
     * @param orderId
     *            订单ID.
     * @return 查询结果.
     * @throws CommSystemProfileException
     * @throws CommOrderException
     */
    @RequestMapping(value = "/orderHistory/getInvoice")
    @ResponseBody
    public ResponseData getInvoice(HttpServletRequest request, Long orderId)
            throws CommOrderException, CommSystemProfileException {
        IRequest requestContext = createRequestContext(request);
        Invoice invoice = invoiceService.printInvoice(requestContext, orderId);
        List<Invoice> list = new ArrayList<Invoice>();
        list.add(invoice);
        return new ResponseData(list);
    }
}
