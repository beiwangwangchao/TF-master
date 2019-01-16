/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.Coupons;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetOrderDetailRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetOrderDetailResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.Product;
import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetOrderDetailService;
import com.lkkhpg.dsis.admin.order.service.ISalesOrderService;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmTaxMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.delivery.mapper.OrderDeliveryLineMapper;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesRtnLineMapper;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.mapper.InvItemMapper;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherMapper;
import com.lkkhpg.dsis.common.service.IMarketParamService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;

/**
 * 查询订单详情接口实现类.
 *
 * @author shenqb
 */
@Service
@Transactional
public class GetOrderDetailServiceImpl implements IGetOrderDetailService {

    private Logger logger = LoggerFactory.getLogger(GetOrderDetailServiceImpl.class);

    @Autowired
    private ISalesOrderService salesOrderService;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private OrderDeliveryLineMapper orderDeliveryLineMapper;

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private InvItemMapper invItemMapper;

    @Autowired
    private SalesLineMapper salesLineMapper;

    @Autowired
    private SalesRtnLineMapper salesReturnLineMapper;

    @Autowired
    private SpmTaxMapper spmTaxMapper;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private IMarketParamService marketParamService;

    @Autowired
    private IParamService paramService;

    @Autowired
    private IDAppUtilService dAppUtilService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GetOrderDetailResponse getOrderDetail(GetOrderDetailRequest getOrderDetailRequest) throws DAppException {
        if (logger.isInfoEnabled()) {
            logger.info("dapp getOrderDetail begin:");
        }
        SalesOrder salesOrder = new SalesOrder();
        String orderNumber = getOrderDetailRequest.getOrderNumber();
        salesOrder.setOrderNumber(orderNumber);
        List<SalesOrder> salesOrderList = salesOrderMapper.queryOrdersId(salesOrder);
        if (salesOrderList.size() == 0) {
            if (logger.isErrorEnabled()) {
                logger.error("invalid orderNumber");
            }
            throw new DAppException(IntegrationConstant.ORDER_NUMBER_NOT_EXISTED, IntegrationConstant.ERROR_30003,
                    "orderNumber = " + orderNumber);
        }
        Map<String, Object> marketMap = new HashMap<String, Object>();
        marketMap.put("marketCode", getOrderDetailRequest.getMarket());
        Long headId = salesOrderList.get(0).getHeaderId();
        Long marketId = spmMarketMapper.queryMarketIdByCodeAndCompany(marketMap);
        Long salesOrgId = salesOrderList.get(0).getSalesOrgId();

        IRequest requestContext = RequestHelper.newEmptyRequest();
        requestContext.setLocale(BaseConstants.DEFAULT_LANG);
        requestContext.setAccountId(-1L);
        requestContext.setAttribute(SystemProfileConstants.SALES_ORG_ID, salesOrgId);
        requestContext.setAttribute(SystemProfileConstants.MARKET_ID, marketId);

        // 校验market入参 ：订单必须属于此市场
        boolean validateFlag = false;
        List<SpmSalesOrganization> salesOrganizations = spmSalesOrganizationMapper.selectByMarketId(marketId);
        for (SpmSalesOrganization salesOrganization : salesOrganizations) {
            if (salesOrgId.equals(salesOrganization.getSalesOrgId())) {
                validateFlag = true;
            }
        }
        if (!validateFlag) {
            if (logger.isErrorEnabled()) {
                logger.error("dapp get order : the order is not in this market");
            }
            throw new DAppException("the order is not in this market", IntegrationConstant.STATUS_CODE_ORDER,
                    "orderNumber = " + orderNumber);
        }

        try {
            salesOrder = salesOrderService.getDetailOrder(requestContext, headId);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("dapp调用salesOrderService的getDetailOrder方法报错：{}", IntegrationException.getErrorStackTrace(e));
            }
            throw new DAppException(IntegrationException.getErrorStackTrace(e), IntegrationConstant.STATUS_CODE_ORDER,
                    "orderNumber = " + orderNumber);
        }
        GetOrderDetailResponse getOrderDetailResponse = parseOrder(requestContext, salesOrder);
        // getOrderDetailResponse.setSaleOrganization(getOrderDetailRequest.getSalesOrg());
        getOrderDetailResponse.setMarket(getOrderDetailRequest.getMarket());
        return getOrderDetailResponse;
    }

    private GetOrderDetailResponse parseOrder(IRequest requestContext, SalesOrder salesOrder) {
        GetOrderDetailResponse getOrderDetailResponse = new GetOrderDetailResponse();

        Member member = salesOrder.getMember();
        String distributorNumber = (null == member) ? null : member.getMemberCode();
        String orderDate = dAppUtilService.toDateTimeString(salesOrder.getOrderDate());
        // List<MemSite> memSites = (null == member) ? null :
        // member.getMemSites();
        List<SalesSites> memSites = (null == member) ? null : salesOrder.getSalesSites();
        SalesLogistics salesLogistics = salesOrder.getLogistics();
        String deliveryCountry = "";
        String deliveryState = "";
        String deliveryCity = "";
        String deliveryAddress = "";
        String deliveryAddress2 = "";
        String deliveryAddress3 = "";
        String deliveryZipCode = "";
        String deliveryReceiver = "";
        String deliveryPhone = "";
        String deliveryAreaCode = "";
        String billingCountry = "";
        String billingState = "";
        String billingCity = "";
        String billingAddress = "";
        String billingAddress2 = "";
        String billingAddress3 = "";
        String billingZipCode = "";
        String billingReceiver = "";
        String billingPhone = "";
        String billingAreaCode = "";
        BigDecimal taxRate = null;
        // 订单头的发运状态，对应所有行上的状态的结果
        String shippingStatus = getShippingStatus(salesOrder);
        String appNo = salesOrder.getDappAppNo();
        String mailing = salesOrder.getDeliveryType();
        String currency = salesOrder.getCurrency();
        BigDecimal shippingFee = null;
        String orderNumber = salesOrder.getOrderNumber();
        String orderStatus = salesOrder.getOrderStatus();
        String saleOrganization = null;
        if (null != salesOrder.getSalesOrganization()) {
            saleOrganization = salesOrder.getSalesOrganization().getCode();
        }
        if (null != salesLogistics) {
            shippingFee = salesLogistics.getShippingFee();
        }
        BigDecimal amount = salesOrder.getOrderAmt();
        String salesType = salesOrder.getChannel();
        String orderType = salesOrder.getOrderType();
        SpmSalesOrganization spmSalesOrganization = spmSalesOrganizationMapper
                .selectByPrimaryKey(salesOrder.getSalesOrgId());
        if (null != spmSalesOrganization) {
            // 是否启用税
            String enableTax = new String();
            List<String> enableTaxs = paramService.getSalesParamValues(requestContext, "SPM.ENABLE_TAX",
                    salesOrder.getSalesOrgId());
            if (!(enableTaxs == null || enableTaxs.isEmpty())) {
                enableTax = enableTaxs.get(0);
            }
            if (IntegrationConstant.ORDER_ENABLED_TAX.equals(enableTax)) {
                // 获取税码
                List<String> taxCodes = paramService.getSalesParamValues(requestContext, "SPM.TAX_CODE",
                        salesOrder.getSalesOrgId());
                if (!(taxCodes == null || taxCodes.isEmpty())) {
                    SpmTax spmTax = spmTaxMapper.getTaxByCode(taxCodes.get(0));
                    if (null != spmTax) {
                        taxRate = new BigDecimal(spmTax.getTaxPercent().doubleValue() / 100d);
                    }
                }
            }

        }
        // String orderStatus = salesOrder.getOrderStatus();
        // String shippingInitStatus = (salesLines.size() > 0) ?
        // salesLines.get(0).getStatus() : null;
        if (memSites.size() > 0) {
            // 获取会员发运地址，账单订单
            for (SalesSites memSite : memSites) {
                if (IntegrationConstant.MEMBER_SITE_TYPE_SHIP.equals(memSite.getSiteType())) {
                    deliveryCountry = memSite.getCountryCode();
                    deliveryReceiver = memSite.getName();
                    deliveryPhone = memSite.getPhone();
                    deliveryState = memSite.getStateCode();
                    deliveryCity = memSite.getCityCode();
                    deliveryAddress = memSite.getAddress1();
                    deliveryAddress2 = memSite.getAddress2();
                    deliveryAddress3 = memSite.getAddress3();
                    deliveryZipCode = memSite.getZipCode();
                    deliveryAreaCode = memSite.getAreaCode();
                }
                if (IntegrationConstant.MEMBER_SITE_TYPE_BILL.equals(memSite.getSiteType())) {
                    billingCountry = memSite.getCountryCode();
                    billingReceiver = memSite.getName();
                    billingPhone = memSite.getPhone();
                    billingState = memSite.getStateCode();
                    billingCity = memSite.getCityCode();
                    billingAddress = memSite.getAddress1();
                    billingAddress2 = memSite.getAddress2();
                    billingAddress3 = memSite.getAddress3();
                    billingZipCode = memSite.getZipCode();
                    billingAreaCode = memSite.getAreaCode();
                }
            }
        }
        // 构造商品列表
        List<Product> productList = getProducts(requestContext, salesOrder);

        getOrderDetailResponse.setDistributorNumber(distributorNumber);
        getOrderDetailResponse.setOrderDate(orderDate);
        getOrderDetailResponse.setDeliveryState(deliveryState);
        getOrderDetailResponse.setDeliveryCity(deliveryCity);
        getOrderDetailResponse.setDeliveryAddress(deliveryAddress);
        getOrderDetailResponse.setDeliveryAddress2(deliveryAddress2);
        getOrderDetailResponse.setDeliveryZipCode(deliveryZipCode);
        getOrderDetailResponse.setDeliveryReceiver(deliveryReceiver);
        getOrderDetailResponse.setDeliveryPhone(deliveryPhone);
        getOrderDetailResponse.setDeliveryAreaCode(deliveryAreaCode);
        getOrderDetailResponse.setBillingState(billingState);
        getOrderDetailResponse.setBillingCity(billingCity);
        getOrderDetailResponse.setBillingAddress(billingAddress);
        getOrderDetailResponse.setBillingAddress2(billingAddress2);
        getOrderDetailResponse.setBillingZipCode(billingZipCode);
        getOrderDetailResponse.setBillingReceiver(billingReceiver);
        getOrderDetailResponse.setBillingPhone(billingPhone);
        getOrderDetailResponse.setBillingAreaCode(billingAreaCode);
        getOrderDetailResponse.setMailing(mailing);
        getOrderDetailResponse.setCurrency(currency);
        getOrderDetailResponse.setShippingFee(shippingFee);
        getOrderDetailResponse.setAmount(amount);
        getOrderDetailResponse.setSalesType(salesType);
        // getOrderDetailResponse.setBuyerIsMember(buyerIsMember);
        // getOrderDetailResponse.setOrderStatus(orderStatus);
        getOrderDetailResponse.setOrderNumber(orderNumber);
        getOrderDetailResponse.setOrderStatus(orderStatus);
        getOrderDetailResponse.setShippingStatus(shippingStatus);
        getOrderDetailResponse.setDeliveryCountry(deliveryCountry);
        getOrderDetailResponse.setDeliveryAddress3(deliveryAddress3);
        getOrderDetailResponse.setBillingCountry(billingCountry);
        getOrderDetailResponse.setBillingAddress3(billingAddress3);
        getOrderDetailResponse.setAppNo(appNo);
        getOrderDetailResponse.setSaleOrganization(saleOrganization);
        getOrderDetailResponse.setTaxRate(taxRate);
        getOrderDetailResponse.setOrderType(orderType);
        // getOrderDetailResponse.setShippingInitStatus(shippingInitStatus);
        getOrderDetailResponse.setProducts(productList);
        return getOrderDetailResponse;

    }

    public List<Product> getProducts(IRequest iRequest, SalesOrder salesOrder) {
        List<SalesLine> salesLines = salesOrder.getLines();
        List<Product> productList = new ArrayList<Product>();
        for (SalesLine salesLine : salesLines) {
            Product product = new Product();
            String productCode = salesLine.getItemNumber();
            BigDecimal productAmount = salesLine.getAmount();
            BigDecimal quantity = salesLine.getQuantity();
            BigDecimal refundQty = salesReturnLineMapper.selectSumQtyByOrderLineIdForDapp(salesLine.getLineId());
            if (null == refundQty) {
                refundQty = BigDecimal.ZERO;
            }
            BigDecimal cancelQty = BigDecimal.ZERO;
            if (IntegrationConstant.ORDER_STATUS_CANCAL.equals(salesOrder.getOrderStatus())
                    || IntegrationConstant.ORDER_STATUS_VOID.equals(salesOrder.getOrderStatus())) {
                cancelQty = salesLine.getQuantity();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderLineId", salesLine.getLineId());
            map.put("deliveryType", salesOrder.getDeliveryType());
            BigDecimal shippedQty = null;
            // 判断是否商品包获取已发运数量
            if (salesLineMapper.checkOrderItemType(salesLine.getLineId()) > 0) {
                // 非商品包
                shippedQty = orderDeliveryLineMapper.getQtySumByOrderLineIdForDapp(map);
            } else {
                // 商品包
                shippedQty = orderDeliveryLineMapper.getDeliveryQtyForPackg(map);
            }

            BigDecimal pv = salesLine.getPv();
            String shippingStatus = salesLine.getStatus();
            InvItem _product = invItemMapper.selectByPrimaryKey(salesLine.getItemId());
            String productName = (null == _product) ? null : _product.getItemName();
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

    public String getShippingStatus(SalesOrder salesOrder) {
        List<SalesLine> salesLines = salesOrder.getLines();
        if (null == salesLines) {
            IRequest requestContext = RequestHelper.newEmptyRequest();
            requestContext.setLocale(BaseConstants.DEFAULT_LANG);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("headerId", salesOrder.getHeaderId());
            map.put("salesOrgId", salesOrder.getSalesOrgId());
            salesLines = salesLineMapper.selectByHeaderId(map);
        }
        int shipCount = 0;
        int unshipCount = 0;
        for (SalesLine salesLine : salesLines) {
            if (IntegrationConstant.DELIVERY_STATUS_SHIPP.equals(salesLine.getStatus())) {
                shipCount++;
            } else if (IntegrationConstant.DELIVERY_STATUS_USHIP.equals(salesLine.getStatus())) {
                unshipCount++;
            }
        }
        if (shipCount == salesLines.size()) {
            return IntegrationConstant.DELIVERY_STATUS_SHIPP;
        } else if (unshipCount == salesLines.size()) {
            return IntegrationConstant.DELIVERY_STATUS_USHIP;
        } else {
            return IntegrationConstant.DELIVERY_STATUS_PSHIP;
        }
    }

    private BigDecimal processPrice(IRequest request, Long marketId, BigDecimal price, String marketCode) {
        // 获取组织参数 - 是否启用税
        String enable_tax = marketParamService.getParamValue(request, SystemProfileConstants.SPM_ENABLE_TAX,
                SystemProfileConstants.MARKET, marketId.toString(), SystemProfileConstants.ORG_TYPE_MARKET);
        // 获取组织参数 - 商品价格是否含税
        String price_include_tax = marketParamService.getParamValue(request,
                SystemProfileConstants.SPM_PRICE_INCLUDE_TAX, SystemProfileConstants.MARKET, marketId.toString(),
                SystemProfileConstants.ORG_TYPE_MARKET);
        // 按马来的计算方式
        if (BaseConstants.YES.equals(enable_tax) && BaseConstants.NO.equals(price_include_tax)) {
            SpmTax tax = spmTaxMapper.getTaxByCode(marketCode);
            if (null != tax) {
                price = price.divide(new BigDecimal(1 + tax.getTaxPercent().doubleValue() / 100d), 2,
                        BigDecimal.ROUND_HALF_DOWN);
            }
        }
        return price;
    }

}
