/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkkhpg.dsis.common.config.dto.SpmCompany;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTier;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTierSeg;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxHeadDto;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxLineDto;
import com.lkkhpg.dsis.common.discount.exception.DiscountException;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxHeadService;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.order.dto.OmMwsOrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.service.*;
import com.lkkhpg.dsis.mws.constant.MwsOrderConstants;
import com.lkkhpg.dsis.mws.constant.ProductConstants;
import com.lkkhpg.dsis.mws.dto.NativeSalesOrder;
import com.lkkhpg.dsis.mws.dto.Product;
import com.lkkhpg.dsis.mws.dto.ShopCartItem;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.mws.exception.OrderException;
import com.lkkhpg.dsis.mws.service.*;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


/**
 * 订单确认controller.
 *
 * @author guanghui.liu
 */
@Controller
public class OrderConfirmController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(OrderConfirmController.class);

    @Autowired
    private IMemberOrderConfirmService memberOrderConfirmService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IShippingTierService shippingTierService;

    @Autowired
    private IVoucherService voucherService;

    @Autowired
    private IMwsOrderPaymentService mwsOrderPaymentService;

    @Autowired
    private IMemberSiteService memberSiteService;

    @Autowired
    private IShopCartService shopCartService;

    @Autowired
    private IProductService productService;

    @Autowired
    private ICommMemberService commMemberService;

    @Autowired
    private IDiscountTrxHeadService discountTrxHeadService;
    @Autowired
    private ISpmMarketService spmMarketService;
    /**
     * 访问订单确认页面时,准备数据.
     *
     * @param request    请求上下文
     * @param token      访问订单确认页面的口令,防止支付成功回退时页面重现
     * @param settlement 来自快速购买时带有产品信息
     * @return 跳转到订单确认页面, 带有填充页面的数据
     * @throws IOException    抛出json转换异常
     * @throws OrderException 订单异常
     */
    @RequestMapping(value = "order/order-confirm")
    @ResponseBody
    public ModelAndView orderConfirm(HttpServletRequest request, String token,
            @RequestParam(required = false) String settlement) throws IOException, OrderException {
        ModelAndView mav = new ModelAndView();
        // 先检查访问页面时是否有token
        HttpSession session = request.getSession();
        String sessionToken = (String) session.getAttribute(MwsOrderConstants.ORDER_CONFIRM_TOKEN);
        if (StringUtils.isEmpty(sessionToken) || !sessionToken.equals(token)) { // token不对,未通过验证
            mav.setViewName(MwsOrderConstants.REDIRECT_TO_INDEX);
            return mav;
        }
        IRequest requestContext = createRequestContext(request);
        Long salesOrgId = requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        mav.addObject(MwsOrderConstants.CONSTANT_SALES_ORG_ID, salesOrgId);
        Map<String, Object> map = memberOrderConfirmService.queryBasicInfo(requestContext, settlement);
        mav.addObject(MwsOrderConstants.CONSTANT_PRODUCTS, map.get(MwsOrderConstants.CONSTANT_CARTITEMS));
        mav.addObject(MwsOrderConstants.CONSTANT_PRODS, settlement == null ? "" : settlement);
        mav.addObject(MwsOrderConstants.CONSTANT_SHIPSITES, map.get(MwsOrderConstants.CONSTANT_SHIPSITES));
        mav.addObject(MwsOrderConstants.CONSTANT_BILLSITES, map.get(MwsOrderConstants.CONSTANT_BILLSITES));
        mav.addObject(MwsOrderConstants.CONSTANT_ISRBUSE, map.get(MwsOrderConstants.CONSTANT_ISRBUSE));
        mav.addObject(MwsOrderConstants.CONSTANT_CURRENCY_CODE, map.get(MwsOrderConstants.CONSTANT_CURRENCY_CODE));
        mav.addObject(MwsOrderConstants.CONSTANT_CURRENCY_SYMBOL, map.get(MwsOrderConstants.CONSTANT_CURRENCY_SYMBOL));
//        mav.addObject(MwsOrderConstants.WEIGHT,"kg");
//        mav.addObject(MwsOrderConstants.TOTALKG,map.get(MwsOrderConstants.TOTALKG));
        mav.addObject(MwsOrderConstants.CONSTANT_CURRENCY_PRECISION,
                map.get(MwsOrderConstants.CONSTANT_CURRENCY_PRECISION));
        mav.addObject(MwsOrderConstants.CONSTANT_TAX,
                map.get(MwsOrderConstants.CONSTANT_TAX) != null ? map.get(MwsOrderConstants.CONSTANT_TAX) : 0);
        mav.addObject(MwsOrderConstants.CONSTANT_ALLOW_BACK_ORDER,
                map.get(MwsOrderConstants.CONSTANT_ALLOW_BACK_ORDER));
        // 待付款、付款中状态的暂时扣掉的RB值
        OmMwsOrderPayment OmMwsOrderPayment = mwsOrderPaymentService.queryRemainingBalSum(requestContext);
        BigDecimal remainingBalTotal = (BigDecimal) map.get(MwsOrderConstants.CONSTANT_REMAINING_BALENCE);

        //updated by 11816 at 2018/01/22 11:42
        BigDecimal remainingDCTotal = (BigDecimal) map.get(MwsOrderConstants.CONSTANT_AVAILABLE_DISCOUNT);
        BigDecimal usable = null;
        if (OmMwsOrderPayment != null) {
            usable = remainingBalTotal.subtract(OmMwsOrderPayment.getPaymentAmount());
        } else {
            usable = remainingBalTotal;
        }
        //updated by 11816 at 2018/01/22 19:32 ^start
        mav.addObject(MwsOrderConstants.CONSTANT_REMAINING_BALENCE, usable);
        mav.addObject(MwsOrderConstants.CONSTANT_DISCOUNT, map.get(MwsOrderConstants.CONSTANT_DISCOUNT));
        mav.addObject(MwsOrderConstants.CONSTANT_AVAILABLE_DISCOUNT, remainingDCTotal);
        mav.setViewName(getViewPath() + "/order/order-confirm");

        //modify end
        return mav;
    }

    /**
     * 提交购买请求创建订单.
     *
     * @param request          统一上下文
     * @param nativeSalesOrder 原生不完整的订单信息
     * @return 返回响应信息
     * @throws CommOrderException         抛出业务异常
     * @throws MemberException            清空购物车异常
     * @throws CommVoucherException       优惠券校验出错时抛出
     * @throws CommSystemProfileException
     */
    @RequestMapping(value = "account/submitOrder")
    @ResponseBody
    public ResponseData submitOrder(HttpServletRequest request, @RequestBody NativeSalesOrder nativeSalesOrder)
            throws CommOrderException, MemberException, CommVoucherException, CommSystemProfileException {
        // 前台已传-deliveryType-remark-deliveryLocationId-deliveryTo-billingLocationId-billingTo-logistics-adjustMents
        HttpSession session = request.getSession();
        ResponseData responseData = new ResponseData();
        // 先检查访问页面时是否有token
        String sessionToken = (String) session.getAttribute(MwsOrderConstants.ORDER_CONFIRM_TOKEN);
        if (StringUtils.isEmpty(sessionToken)) { // token不对,未通过验证
            responseData.setSuccess(false);
            Locale locale = request.getLocale();
            String message = getMessageSource().getMessage(MemberException.ORDER_NOT_ALLOW_SUBMIT_TWICE, null, locale);
            responseData.setCode(message);
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);

        // 生成订单信息
        SalesOrder order = memberOrderConfirmService.generateSalesOrder(requestContext,
                nativeSalesOrder.getSalesOrder(), nativeSalesOrder.getProds());

        // 提交订单
        memberOrderConfirmService.submitOrder(requestContext, order);

        //生成折扣/信用额度
        BigDecimal dis=nativeSalesOrder.getSalesOrder().getDiscountAmt();

        SalesOrder salesOrder=new SalesOrder();

        //折扣或者优惠金额大于0时记录 （优惠金额  会员编号 ）update up 15750 at 2018/04/19
        if(dis.compareTo(BigDecimal.ZERO)>0) {
            DiscountTrxHeadDto discountTrxHeadDto=new DiscountTrxHeadDto();
            discountTrxHeadDto.setSalesOrgId(order.getSalesOrgId());//销售组织id
            discountTrxHeadDto.setProcessDate(order.getOrderDate());//处理日期 orderDate
            discountTrxHeadDto.setAdjustType("USED");//调整类型 订单消耗used
            discountTrxHeadDto.setAdjustReason("USED");//调整原因 订单消耗used
            discountTrxHeadDto.setStatus("NEW");//状态  新建 new
            List<DiscountTrxLineDto> discountTrxLineDtos=new ArrayList<>();//行表
            DiscountTrxLineDto discountTrxLineDto=new DiscountTrxLineDto();
            discountTrxLineDto.setMemberId(requestContext.getAttribute("memberId"));//会员id
            discountTrxLineDto.setDiscountAdjustAmt(dis);//折扣金额 dis
            discountTrxLineDto.setAttribute1(order.getOrderNumber());//订单号
            discountTrxLineDtos.add(0,discountTrxLineDto);
            discountTrxHeadDto.setDiscountTrxLineDto(discountTrxLineDtos);
            requestContext.setAttribute("createdBy",requestContext.getAttribute("memberId"));
            requestContext.setAttribute("lastUpdatedBy", requestContext.getAttribute("memberId"));
            requestContext.setAttribute("creationDate", new Date());
            requestContext.setAttribute("lastUpdateDate",new Date() );
            requestContext.setAttribute("userId",requestContext.getAttribute("memberId"));
            List<DiscountTrxHeadDto> discountTrxHeadDtos=discountTrxHeadService.createUPTrx(requestContext, discountTrxHeadDto);

            //查市场所属的公司是不是(不校验了)
           // Long marketId=Long.valueOf(request.getParameter("marketId")) ;
            Long marketId= (Long) requestContext.getAttribute("_marketId");
            //List<SpmMarket> spmMarketList =spmMarketService.selectCompanyId(marketId);

            if (order.getActrualPayAmt().compareTo(BigDecimal.ZERO) == 0 ){
                //提交折扣/信用额度事务
                submitDiscountTrx(requestContext, discountTrxHeadDtos.get(0));
                salesOrder.setAttribute10("N");
            }
        }

        // 提交ecoupon
        for (OmMwsOrderPayment om : nativeSalesOrder.getOmMwsOrderPayment().getPayData()) {
            om.setOrderHeaderId(order.getHeaderId());
            om.setSalesOrgId(order.getSalesOrgId());
        }
        mwsOrderPaymentService.saveOmMwsOrderPayment(requestContext,
                nativeSalesOrder.getOmMwsOrderPayment().getPayData());
        // 更改RB的value
        // mwsOrderPaymentService.updateBalanceByMemberId(requestContext, nativeSalesOrder.getMemAccountingBalance());
        session.removeAttribute(MwsOrderConstants.ORDER_CONFIRM_TOKEN);
        List<SalesOrder> rows = new ArrayList<SalesOrder>();
        rows.add(order);
        rows.add(salesOrder);
        responseData.setRows(rows);
        responseData.setSuccess(true);

        return responseData;
    }

    /**
     * 取得物流信息.
     *
     * @param request      统一上下文
     * @param location     包含location信息
     * @param currencyCode 本位币
     * @return 可用物流list
     */
    @RequestMapping(value = "account/queryShippingTier")
    @ResponseBody
    public ResponseData queryShippingTier(HttpServletRequest request, ShippingTierSeg location, String currencyCode, String apptype) {
        IRequest requestContext = createRequestContext(request);
        Long salesOrgId = requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        List<ShippingTier> shippingTiers = shippingTierService.queryShippingTier(requestContext, location, salesOrgId,
                currencyCode, apptype);
        return new ResponseData(shippingTiers);
    }

    /**
     * 初始化ecoupon下拉框.
     *
     * @param request 统一上下文.
     * @return 是否有ecoupon
     */
    @RequestMapping(value = "/order/getMemberVouchersForVIP")
    public ResponseData queryEcoupon(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Long memberId = requestContext.getAttribute(Member.FIELD_MEMBER_ID);
        List<Voucher> list = voucherService.getMemberVouchersForVIP(requestContext, memberId);
        return new ResponseData(list);
    }

    /**
     * 获取所有地址-包括下面两个存在的原因是安全性.
     *
     * @param request 统一上下文.
     * @return 所有地址
     */
    @RequestMapping(value = "/order/getAllSites")
    public ResponseData getAllSites(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Long memberId = requestContext.getAttribute(Member.FIELD_MEMBER_ID);
        List<MemSite> siteList = new ArrayList<MemSite>();
        MemSite allSite = new MemSite();
        allSite.setMemberId(memberId);
        siteList = memberSiteService.queryMemSites(requestContext, allSite);
        return new ResponseData(siteList);
    }

    /**
     * 获取所有商品.
     *
     * @param request          统一上下文.
     * @param prodsForQueryAll 来自快速购买时带有产品信息
     * @return 所有商品
     * @throws IOException
     */
    @RequestMapping(value = "/order/getAllProds")
    public ResponseData getAllProds(HttpServletRequest request, String prodsForQueryAll) throws IOException {
        IRequest requestContext = createRequestContext(request);
        List<Product> cartItems = new ArrayList<Product>();
        if ("".equals(prodsForQueryAll)) { // 来自购物车时
            cartItems = shopCartService.queryShopCartItem(requestContext, BaseConstants.YES);
        } else { // 来自快速购买时,传多一个参数List-结构是[{productId:001,quantity:2},{productId:002,quantity:3}]
            JavaType javaType = objectMapper.getTypeFactory().constructParametrizedType(ArrayList.class, List.class,
                    ShopCartItem.class);
            List<ShopCartItem> shopCartItems = new ArrayList<ShopCartItem>();
            shopCartItems = (List<ShopCartItem>) objectMapper.readValue(prodsForQueryAll, javaType);
            for (ShopCartItem temp : shopCartItems) {
                Product product = new Product();
                product.setItemId(temp.getProductId());
                List<Product> products = productService.getSimpleProductsByWhereClause(requestContext, product, 1,
                        ProductConstants.SHOPCART_QUERY_COUNT);
                if (products.size() > 0) {
                    Product shopProduct = products.get(0);
                    if (null != temp.getQuantity()) {
                        shopProduct.setItemAmount(Long.parseLong(temp.getQuantity().toString()));
                    }
                    cartItems.add(shopProduct);
                }
            }
        }
        return new ResponseData(cartItems);
    }

    /**
     * 获取所有优惠券.
     *
     * @param request 统一上下文.
     * @return 所有优惠券
     */
    @RequestMapping(value = "/order/getAllVouchers")
    public ResponseData getAllVouchers(HttpServletRequest request, HttpServletResponse response) {
        IRequest requestContext = createRequestContext(request);
        Long memberId = requestContext.getAttribute(Member.FIELD_MEMBER_ID);
        List<Voucher> vouchers = voucherService.getMemberVouchers(requestContext, memberId);
        return new ResponseData(vouchers);
    }
    /**
     *  查询并提交折扣/信用事务（不保存）
     * @param  discountTrxHeadDto 折扣
     * @return void
     * update up 15750 at 2018/04/19
     */
    private void submitDiscountTrx(IRequest request,DiscountTrxHeadDto discountTrxHeadDto){

        //提交事务
        try {
            discountTrxHeadService.submitDiscountTrx(request,discountTrxHeadDto);
        } catch (DiscountException e) {
            e.printStackTrace();
        }
    }

}
