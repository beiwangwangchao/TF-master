/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkkhpg.dsis.common.config.dto.SpmCompany;
import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmCompanyMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmCurrencyMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.order.dto.SalesAdjustment;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.service.*;
import com.lkkhpg.dsis.mws.constant.MwsOrderConstants;
import com.lkkhpg.dsis.mws.constant.ProductConstants;
import com.lkkhpg.dsis.mws.dto.MemberInfo;
import com.lkkhpg.dsis.mws.dto.Product;
import com.lkkhpg.dsis.mws.dto.ShopCartItem;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.mws.exception.OrderException;
import com.lkkhpg.dsis.mws.service.*;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 会员地址service实现.
 *
 * @author guanghui.liu
 */
@Service
@Transactional
public class MemberOrderConfirmServiceImpl implements IMemberOrderConfirmService {

    private final Logger logger = LoggerFactory.getLogger(MemberOrderConfirmServiceImpl.class);

    @Autowired
    private IMemberSiteService memberSiteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IShopCartService shopCartService;

    @Autowired
    private IParamService paramService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IMemberInfoService memberInfoService;

    @Autowired
    private SpmCurrencyMapper spmCurrencyMapper;

    @Autowired
    private ICommSalesOrderService commSalesOrderService;

    @Autowired
    private IVoucherService voucherService;

    @Autowired
    private ISpmInvOrganizationService spmInvOrganizationService;

    @Autowired
    private ICommMemberService commMemberService;

    @Autowired
    private SpmCompanyMapper spmCompanyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> queryBasicInfo(IRequest request, String settlement) throws IOException, OrderException {
        Long memberId = (Long) request.getAttribute(MemberConstants.MWS_MEMBER_ID);
        Long salesOrgId = request.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        Map<String, Object> basicInfo = new HashMap<String, Object>();
        // 1.取出订单信息
        List<Product> cartItems = new ArrayList<Product>();
        if (settlement == null) { // 来自购物车时
            cartItems = shopCartService.queryShopCartItem(request, BaseConstants.YES);
            if (!cartItems.isEmpty()) {
                if (cartItems.get(0).getTax() == null) {
                    basicInfo.put(MwsOrderConstants.CONSTANT_TAX, BigDecimal.ZERO);
                } else {
                    basicInfo.put(MwsOrderConstants.CONSTANT_TAX, cartItems.get(0).getTax());
                }
            } else {
                throw new OrderException(OrderException.SHOPPING_CART_ERROR, new Object[]{});
            }
        } else { // 来自快速购买时,传多一个参数List-结构是[{productId:001,quantity:2},{productId:002,quantity:3}]
            JavaType javaType = objectMapper.getTypeFactory().constructParametrizedType(ArrayList.class, List.class,
                    ShopCartItem.class);
            List<ShopCartItem> shopCartItems = new ArrayList<ShopCartItem>();
            shopCartItems = (List<ShopCartItem>) objectMapper.readValue(settlement, javaType);
            for (ShopCartItem temp : shopCartItems) {
                Product product = new Product();
                product.setItemId(temp.getProductId());
                List<Product> products = productService.getSimpleProductsByWhereClause(request, product, 1,
                        ProductConstants.SHOPCART_QUERY_COUNT);
                if (products.size() > 0) {
                    Product shopProduct = products.get(0);
                    if (shopProduct.getTax() != null) {
                        basicInfo.put(MwsOrderConstants.CONSTANT_TAX, shopProduct.getTax());
                    } else {
                        basicInfo.put(MwsOrderConstants.CONSTANT_TAX, BigDecimal.ZERO);
                    }
                    if (null != temp.getQuantity()) {
                        shopProduct.setItemAmount(Long.parseLong(temp.getQuantity().toString()));
                    }
                    cartItems.add(shopProduct);
                }
            }
        }
        basicInfo.put(MwsOrderConstants.CONSTANT_CARTITEMS, cartItems);
        // 2.配送信息和账单信息
        // 2.1.通过memberId,查询出所有地址的集合
        List<MemSite> siteList = new ArrayList<MemSite>();
        MemSite allSite = new MemSite();
        allSite.setMemberId(memberId);
        siteList = memberSiteService.queryMemSites(request, allSite);
        // 2.2.分离出收货地址列表和账单地址列表
        List<MemSite> shipSites = new ArrayList<MemSite>();
        List<MemSite> billSites = new ArrayList<MemSite>();
        List<MemSite> sites = new ArrayList<MemSite>();
        for (MemSite ms : siteList) {
            if (MemberConstants.MEM_SITE_TYPE_SHIP.equals(ms.getSiteUseCode())) { // 默认收货地址
                shipSites.add(ms);
                sites.add(ms);
            } else if (MemberConstants.MEM_SITE_TYPE_BILL.equals(ms.getSiteUseCode())) { // 默认账单地址
                billSites.add(ms);
                sites.add(ms);
            }
        }
        basicInfo.put(MwsOrderConstants.CONSTANT_SHIPSITES, shipSites);
        basicInfo.put(MwsOrderConstants.CONSTANT_BILLSITES, billSites);
        // EscapeUtils.escapeList(sites);
        // basicInfo.put(MwsOrderConstants.CONSTANT_SITES, sites);
        // 3.RB是否可用
        List<String> isRBUse = paramService.getParamValues(request, SystemProfileConstants.PARAM_PAY_BY_RB,
                SystemProfileConstants.ORG_TYPE_SALES);
        if (isRBUse.size() < 1) { // 如果没有设置组织参数默认为库存不足允许下单
            basicInfo.put(MwsOrderConstants.CONSTANT_ISRBUSE, BaseConstants.NO);
        } else {
            basicInfo.put(MwsOrderConstants.CONSTANT_ISRBUSE, isRBUse.iterator().next());
        }
        // 4.取出会员RemainingBalance
        /*modified by furong.tang 取出会员可用*/
        MemberInfo member = memberInfoService.queryRemBal(request, memberId);
        basicInfo.put(MwsOrderConstants.CONSTANT_REMAINING_BALENCE,
                member != null ? member.getRemainingBalance() : BigDecimal.ZERO);

        //updated by 11816 at 2018/01/22 10:57
        basicInfo.put(MwsOrderConstants.CONSTANT_AVAILABLE_DISCOUNT,
                member != null ? member.getAvailableDiscount() : BigDecimal.ZERO);
        basicInfo.put(MwsOrderConstants.CONSTANT_DISCOUNT,
                member != null ? member.getDiscount() : BigDecimal.ZERO);
        // 5.取出税相关信息
        // 税信息由于放在了产品里！所以已经在产品的循环里取出了
        // 6.本位币和币种符号
        SpmCurrency currency = new SpmCurrency();
        List<String> currencyParams = paramService.getParamValues(request, SystemProfileConstants.SPM_CURRENCY,
                SystemProfileConstants.ORG_TYPE_SALES, salesOrgId);
        currency.setCurrencyCode(currencyParams.iterator().next());
        List<SpmCurrency> currencies = spmCurrencyMapper.querySpmCurrency(currency);
        basicInfo.put(MwsOrderConstants.CONSTANT_CURRENCY_CODE, currencyParams.iterator().next());
        basicInfo.put(MwsOrderConstants.CONSTANT_CURRENCY_SYMBOL, currencies.iterator().next().getSymbol());
        basicInfo.put(MwsOrderConstants.CONSTANT_CURRENCY_PRECISION, currencies.iterator().next().getPrecision());
        // 7.优惠券
        // List<Voucher> vouchers = voucherService.getMemberVouchers(request,
        // memberId);
        // basicInfo.put(MwsOrderConstants.CONSTANT_VOUCHERS, vouchers);
        // 8.是否允许back order
        List<String> allowBackOrder = paramService.getParamValues(request, MwsOrderConstants.MWS_ALLOW_BACK_ORDER,
                SystemProfileConstants.ORG_TYPE_SALES);
        if (allowBackOrder.size() < 1) { // 如果没有设置组织参数默认为库存不足允许下单
            basicInfo.put(MwsOrderConstants.CONSTANT_ALLOW_BACK_ORDER, BaseConstants.NO);
        } else {
            basicInfo.put(MwsOrderConstants.CONSTANT_ALLOW_BACK_ORDER, allowBackOrder.iterator().next());
        }
        return basicInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SalesOrder generateSalesOrder(IRequest request, SalesOrder order, List<ShopCartItem> cartItems)
            throws CommOrderException {
        Long memberId = request.getAttribute(MemberConstants.MWS_MEMBER_ID);
        Long salesOrgId = request.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        // 设置订单头
        /*sign by furong.tang 需修改订单状态*/
        order.setOrderStatus(OrderConstants.SALES_STATUS_NEW);
        order.setCreateUserId(memberId);
        order.setSalesOrgId(salesOrgId);
        order.setOrderDate(new Date(System.currentTimeMillis()));
        order.setOrderType(OrderConstants.ORDER_TYPE_STDP);
        order.setChannel(OrderConstants.ORDER_CHANNEL_DISWB);
        order.setMemberId(memberId);
        order.setPeriod(new SimpleDateFormat(OrderConstants.DEFAULT_PERIOD_FORMAT).format(order.getOrderDate()));
        // 取出并设置本位币
        List<String> currencyParams = paramService.getParamValues(request, SystemProfileConstants.SPM_CURRENCY,
                SystemProfileConstants.ORG_TYPE_SALES, order.getSalesOrgId());
        if (currencyParams == null || currencyParams.isEmpty()) {
            if (logger.isErrorEnabled()) {
                logger.error("order basic data currency error");
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_BASIC_CHECK_ERROR,
                    new Object[]{request, order});
        }
        order.setCurrency(currencyParams.iterator().next());
        order.setCodFlag(BaseConstants.NO); // 货到付款
        order.setSourceType(OrderConstants.ORDER_SOURCE_TYPE_MWS);
        // 自提类型必须提供默认供货组织
        List<SpmInvOrganization> invOrgs = spmInvOrganizationService.getCurrentSupplyInvOrgs(request);
        Long defaultInvOrg = null;
        for (SpmInvOrganization invOrg : invOrgs) {
            if (BaseConstants.YES.equals(invOrg.getDefaultFlag())) {
                defaultInvOrg = invOrg.getInvOrgId();
                break;
            }
        }
        // 获取产品详细信息
        List<Product> prods = new ArrayList<Product>();
        for (ShopCartItem temp : cartItems) {
            Product product = new Product();
            product.setItemId(temp.getProductId());
            List<Product> products = productService.getSimpleProductsByWhereClause(request, product, 1,
                    ProductConstants.SHOPCART_QUERY_COUNT);
            if (products.size() > 0) {
                Product shopProduct = products.get(0);
                if (null != temp.getQuantity()) {
                    shopProduct.setItemAmount(Long.parseLong(temp.getQuantity().toString()));
                }
                prods.add(shopProduct);
            }
        }
        // 设置订单行
        List<SalesLine> lineList = new ArrayList<SalesLine>();
        Long lineNum = 0L;
        for (Product product : prods) {
            lineNum++;
            SalesLine sl = new SalesLine();
            sl.setItemId(product.getItemId());
            sl.setItemName(product.getItemName());
            sl.setUnitOrigPrice(product.getOrginalAmt());
            sl.setUnitSellingPrice(product.getDisAmt());
            sl.setItemSalesType(OrderConstants.LINE_SALETYPE_PURC);
            sl.setPv(product.getPvAmt());
            sl.setQuantity(BigDecimal.valueOf(product.getItemAmount()));
            sl.setStatus(OrderConstants.LINE_DELIVERY_STATUS_USHIP);
            sl.setSourceType(OrderConstants.ORDER_SOURCE_TYPE_MWS);
            sl.setUomCode(product.getUomCode());
            sl.setUnpickQuantity(sl.getQuantity()); // 与数量相同
            sl.setLineNum(lineNum);
            sl.setItemType(product.getItemType());
            // 优惠金额
            sl.setUnitDiscountPrice(new BigDecimal(0));
            sl.setAmount(new BigDecimal(0));
            if (OrderConstants.ORDER_DELIVERY_PCKUP.equals(order.getDeliveryType())) {
                //modified by furong.tang 商品默认供货组织
                sl.setDefaultInvOrgId(product.getIvnOrgId());
                //sl.setDefaultInvOrgId(defaultInvOrg);// 默认供货组织
            }
            lineList.add(sl);
        }
        order.setLines(lineList);

        //mingqing.wen 0111设置指定公司的订单为待处理
        /*if (cartItems.size() > 0) {
            //int sum = productService.getXtfSum(cartItems.get(0).getProductId());
            int sum = productService.getXtfSum(order.getSalesOrgId());
            if (sum > 0) {
                order.setOrderStatus(OrderConstants.SALES_STATUS_CHECK);
            } else {
                order.setOrderStatus(OrderConstants.WAIT_PAT);
            }
        }*/
        //Long memberId = request.getAttribute(Member.FIELD_MEMBER_ID);
        Member member = commMemberService.getMember(request, memberId);
        SpmCompany spmCompany = spmCompanyMapper.selectByPrimaryKey(member.getCompanyId());
        String isCheck = spmCompany.getAttribute3() == null ? "N" : spmCompany.getAttribute3();
        if (isCheck.equals(OrderConstants.SALES_ORDER_CHECK_Y)) {
            order.setOrderStatus(OrderConstants.SALES_STATUS_CHECK);
        } else {
            order.setOrderStatus(OrderConstants.WAIT_PAT);
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SalesOrder submitOrder(IRequest request, SalesOrder order)
            throws CommOrderException, MemberException, CommVoucherException {
        List<Voucher> vl = order.getVouchers();
        SalesLogistics sl = order.getLogistics();
        List<SalesAdjustment> sal = order.getAdjustMents();
        BigDecimal actrualPay = order.getActrualPayAmt();
        BigDecimal discount = order.getDiscountAmt();
        BigDecimal orderPay = order.getOrderAmt();
        /*modified by furong.tang*/
        /*Long memberId = request.getAttribute(Member.FIELD_MEMBER_ID);
        Member member = commMemberService.getMember(request, memberId);
        SpmCompany spmCompany = spmCompanyMapper.selectByPrimaryKey(member.getCompanyId());
        String isCheck = spmCompany.getAttribute3() == null ? "N" : spmCompany.getAttribute3();*/
        if (OrderConstants.MEMBER_TYPE_VIP.equals(request.getAttribute(MemberConstants.FIELD_MEMBER_ROLE))) {
            order.setSourceType(OrderConstants.SOURCE_TYPE_VIPP);
        }
        order.setVouchers(null);
        order.setLogistics(null);
        order.setAdjustMents(null);
        order.setActrualPayAmt(orderPay);
        order.setDiscountAmt(BigDecimal.ZERO);
        String status = order.getOrderStatus();

        order.setOrderStatus(OrderConstants.SALES_STATUS_SAVED);
        // 提交并创建不含物流和优惠以及金额调整的订单
        commSalesOrderService.submitOrder(request, order);
        // 订单创建成功后,设置优惠/物流/调整,更新订单
        order.setVouchers(vl);
        order.setLogistics(sl);
        order.setAdjustMents(sal);
        order.setActrualPayAmt(actrualPay);
        order.setDiscountAmt(discount);

        /*sign by furong.tang 不是新天丰公司的将已保存状态改为待付款*/
        /*order.setOrderStatus(OrderConstants.SALES_STATUS_WAIT_PAYMENT);
        order.setWaitPayDate(new Date(System.currentTimeMillis()));

        if (isCheck.equals(OrderConstants.SALES_ORDER_CHECK_Y)) {
            order.setOrderStatus(OrderConstants.SALES_STATUS_CHECK);

        }*/

        //TODO:0111
        if (OrderConstants.SALES_STATUS_CHECK.equals(status)) {
            order.setOrderStatus(OrderConstants.SALES_STATUS_CHECK);
        } else {
            order.setOrderStatus(OrderConstants.SALES_STATUS_WAIT_PAYMENT);
            order.setWaitPayDate(new Date(System.currentTimeMillis()));
        }

        commSalesOrderService.submitOrder(request, order);
        // 根据“是否允许backOrderFlag”和“库存是否足够”判断是否清空购物车
        List<String> allowBackOrder = paramService.getParamValues(request, MwsOrderConstants.MWS_ALLOW_BACK_ORDER,
                SystemProfileConstants.ORG_TYPE_SALES);
        if (allowBackOrder.size() > 0) { // 设置了backOrder组织参数
            String flag = allowBackOrder.iterator().next();
            if (BaseConstants.NO.equals(flag) && order.getAttribute1() != null) { // 库存不足且不允许下单
                return order;
            }
        } else { // 未设置了backOrder组织参数,此时无库存不下单
            if (order.getAttribute1() != null) { // 库存不足不下单
                return order;
            }
        }
        // 清空购物车中已确认的商品
        shopCartService.deleteConfirmItems(request);
        return order;
    }

}