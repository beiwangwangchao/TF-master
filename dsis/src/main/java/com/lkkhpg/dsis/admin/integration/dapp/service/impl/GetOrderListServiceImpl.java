/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.Coupons;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetOrderListRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetOrderListResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.Product;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetOrderListService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOrderSubmissionService;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherMapper;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;

/**
 * 查询订单列表Service实现类.
 *
 * @author shenqb
 */
@Service
@Transactional
public class GetOrderListServiceImpl implements IGetOrderListService {

    private Logger logger = LoggerFactory.getLogger(GetOrderListServiceImpl.class);

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private IOrderSubmissionService orderSubmissionService;

    @Autowired
    private SalesLineMapper salesLineMapper;

    @Autowired
    private IDAppUtilService dAppUtilService;

    @Autowired
    private VoucherMapper voucherMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<GetOrderListResponse> getOrderList(GetOrderListRequest getOrderListRequest, Integer pageNo,
            Integer maxPerpage) throws DAppException {
        if (logger.isInfoEnabled()) {
            logger.info("dapp getOrderDetail begin:");
        }
        Map<String, Object> marketMap = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        marketMap.put("marketCode", getOrderListRequest.getMarket());

        Long marketId = spmMarketMapper.queryMarketIdByCodeAndCompany(marketMap);
        Long salesOrgId = null;
        if (null != getOrderListRequest.getSaleOrganization()) {
            salesOrgId = orderSubmissionService.getSalesOrgIdByCode(getOrderListRequest.getSaleOrganization(),
                    marketId);
        }
        String orderStatus = getOrderListRequest.getOrderStatus();
        String shippingStatus = getOrderListRequest.getShippingStatus();
        String memberCode = getOrderListRequest.getDistributorNumber();
        Date startDate = null;
        Date endDate = null;
        Date updateStartDate = null;
        Date updateEndDate = null;
        IRequest requestContext = RequestHelper.newEmptyRequest();
        requestContext.setLocale(BaseConstants.DEFAULT_LANG);
        requestContext.setAccountId(-1L);
        requestContext.setAttribute(SystemProfileConstants.SALES_ORG_ID, String.valueOf(salesOrgId));
        requestContext.setAttribute(SystemProfileConstants.MARKET_ID, String.valueOf(marketId));
        if (!StringUtils.isEmpty(getOrderListRequest.getStartDate())) {

            startDate = dAppUtilService.dateTimeStringToDate(getOrderListRequest.getStartDate().replaceAll(" ", "+"));

        }
        if (!StringUtils.isEmpty(getOrderListRequest.getEndDate())) {

            endDate = dAppUtilService.dateTimeStringToDate(getOrderListRequest.getEndDate().replaceAll(" ", "+"));

        }
        if (!StringUtils.isEmpty(getOrderListRequest.getUpdateStartDate())) {

            updateStartDate = dAppUtilService
                    .dateTimeStringToDate(getOrderListRequest.getUpdateStartDate().replaceAll(" ", "+"));

        }
        if (!StringUtils.isEmpty(getOrderListRequest.getUpdateEndDate())) {

            updateEndDate = dAppUtilService
                    .dateTimeStringToDate(getOrderListRequest.getUpdateEndDate().replaceAll(" ", "+"));

        }
        if (null != pageNo && null != maxPerpage) {
            PageHelper.startPage(pageNo, maxPerpage);
        }
        queryMap.put("marketId", marketId);
        queryMap.put("salesOrgId", salesOrgId);
        queryMap.put("startDate", startDate);
        queryMap.put("endDate", endDate);
        queryMap.put("updateStartDate", updateStartDate);
        queryMap.put("updateEndDate", updateEndDate);
        queryMap.put("orderStatus", orderStatus);
        queryMap.put("shippingStatus", shippingStatus);
        queryMap.put("memberCode", memberCode);
        List<SalesOrder> salesOrders = salesOrderMapper.queryOrdersForDapp(queryMap);
        List<GetOrderListResponse> responses = parseOrders(requestContext, salesOrders, getOrderListRequest.getMarket(),
                getOrderListRequest.getSaleOrganization());
        return responses;
    }

    private List<GetOrderListResponse> parseOrders(IRequest requestContext, List<SalesOrder> salesOrders, String market,
            String salesOrg) {
        List<GetOrderListResponse> responses = new ArrayList<GetOrderListResponse>();
        List<Long> headerIds = new ArrayList<Long>();
        for (SalesOrder salesOrder : salesOrders) {
            headerIds.add(salesOrder.getHeaderId());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("dapp -> get orders info : headers : " + headerIds);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("headerIds", headerIds);
        // 一次查出所有头上的所有行，避免在for循环里调数据库
        List<SalesLine> allSalesLines = salesLineMapper.getLinesByHeadersForDapp(map);

        Map<Long, List<SalesLine>> headerIdToLines = new HashMap<>();
        for (SalesLine salesLine : allSalesLines) {
            List<SalesLine> lines = headerIdToLines.get(salesLine.getHeaderId());
            if (lines == null) {
                lines = new ArrayList<>();
                headerIdToLines.put(salesLine.getHeaderId(), lines);
            }
            lines.add(salesLine);
        }

        for (SalesOrder salesOrder : salesOrders) {
            GetOrderListResponse response = new GetOrderListResponse();

            // 分配头行
            List<SalesLine> salesLines = headerIdToLines.get(salesOrder.getHeaderId());
            salesOrder.setLines(salesLines);
            // List<SalesLine> salesLines =
            // salesLineMapper.getLinesForDapp(salesOrder.getHeaderId());
            String orderNumber = salesOrder.getOrderNumber();
            String orderStatus = salesOrder.getOrderStatus();
            String shippingStatus = salesOrder.getShippingStatus();
            String distributorNumber = salesOrder.getMemberCode();
            String orderDate = dAppUtilService.toDateTimeString(salesOrder.getOrderDate());
            String currency = salesOrder.getCurrency();
            BigDecimal amount = salesOrder.getOrderAmt();
            String mailing = salesOrder.getDeliveryType();
            BigDecimal shippingFee = salesOrder.getShippingFee();
            String orderType = salesOrder.getOrderType();
            salesOrder.setLines(salesLines);
            List<Product> products = getProducts(requestContext, salesOrder);

            response.setCurrency(currency);
            response.setDistributorNumber(distributorNumber);
            response.setMarket(market);
            response.setOrderNumber(orderNumber);
            response.setOrderStatus(orderStatus);
            response.setProducts(products);
            response.setSalesOrg(salesOrg);
            response.setShippingFee(shippingFee);
            response.setShippingStatus(shippingStatus);
            response.setAmount(amount);
            response.setOrderDate(orderDate);
            response.setMailing(mailing);
            response.setOrderType(orderType);
            responses.add(response);
        }
        return responses;
    }

    public List<Product> getProducts(IRequest iRequest, SalesOrder salesOrder) {
        List<SalesLine> salesLines = salesOrder.getLines();
        List<Product> productList = new ArrayList<Product>();
        for (SalesLine salesLine : salesLines) {
            Product product = new Product();
            String productCode = salesLine.getItemNumber();
            BigDecimal productAmount = salesLine.getAmount();
            BigDecimal quantity = salesLine.getQuantity();
            BigDecimal refundQty = BigDecimal.ZERO;
            if (null != salesLine.getReturnQty()) {
                refundQty = BigDecimal.valueOf(salesLine.getReturnQty());
            }
            BigDecimal cancelQty = BigDecimal.ZERO;
            if (IntegrationConstant.ORDER_STATUS_CANCAL.equals(salesOrder.getOrderStatus())
                    || IntegrationConstant.ORDER_STATUS_VOID.equals(salesOrder.getOrderStatus())) {
                cancelQty = salesLine.getQuantity();
            }

            BigDecimal shippedQty = BigDecimal.ZERO;
            if (null != salesLine.getOutstandingQty()) {
                shippedQty = BigDecimal.valueOf(salesLine.getOutstandingQty());
            }
            BigDecimal pv = salesLine.getPv();
            String shippingStatus = salesLine.getStatus();
            String productName = salesLine.getItemName();
            if (null != salesLine.getVoucherId()) {
                Voucher voucher = voucherMapper.selectByPrimaryKey(salesLine.getVoucherId());
                List<Coupons> coupons = new ArrayList<Coupons>();
                Coupons coupon = new Coupons();
                coupon.setCouponCode(voucher.getVoucherCode());
                coupon.setCouponAmt(voucher.getDiscountValue());
                coupons.add(coupon); // 默认一个订单行对应一个优惠券
                product.setCoupons(coupons);
            }
            product.setProductCode(productCode);
            product.setQuantity(quantity);
            product.setRefundQty(refundQty);
            product.setCancelQty(cancelQty);
            product.setShippedQty(shippedQty);
            product.setPrice(salesLine.getUnitSellingPrice()); // 销售价
            product.setPriceBeforeTax(salesLine.getUnitOrigPrice()); // 原价
            product.setPv(pv);
            product.setShippingStatus(shippingStatus);
            product.setProductName(productName);
            product.setProductAmount(productAmount);
            productList.add(product);
        }
        return productList;
    }
}
