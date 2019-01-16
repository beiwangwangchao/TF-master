/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.common.service.ISpmCurrencyService;
import com.lkkhpg.dsis.mws.dto.Product;
import com.lkkhpg.dsis.mws.dto.ShopCartItem;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.mws.service.IShopCartService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * MWS购物车controller.
 * 
 * @author gulin
 *
 */

@Controller
public class ShopCartController extends BaseController {
    @Autowired
    private IShopCartService shopCartService;

    @Autowired
    private IParamService paramService;

    @Autowired
    private ISpmCurrencyService spmCurrencyService;

    /**
     * 更新购物车商品信息.
     * 
     * @param cart
     *            更新信息.
     * @param request
     *            统一上下文.
     * @return 返回更新结果
     * 
     * 
     * @throws MemberException
     *             更新失败异常.
     */

    @RequestMapping(value = "/shopcart/update")
    @ResponseBody
    public ResponseData updateShopCart(ShopCartItem cart, HttpServletRequest request) throws MemberException {
        shopCartService.updateShopCartItem(cart, createRequestContext(request));
        ResponseData data = new ResponseData(true);
        return data;
    }

    /**
     * 删除购物车中商品信息.
     * 
     * @param cartItems
     *            删除的商品集合.
     * @param request
     *            统一上下文.
     * @return 返回删除结果.
     * @throws MemberException
     *             抛出删除失败异常.
     */
    @RequestMapping(value = "/shopcart/delete")
    @ResponseBody
    public ResponseData deleteShopCart(@RequestBody List<ShopCartItem> cartItems, HttpServletRequest request)
            throws MemberException {
        shopCartService.deleteShopCartItems(cartItems, createRequestContext(request));
        ResponseData data = new ResponseData(true);
        return data;
    }

    /**
     * 购物车确认勾选商品.
     * 
     * @param cartItems
     *            确认商品的集合.
     * @param request
     *            统一上下文.
     * @return 返回确认信息.
     */
    @RequestMapping(value = "/shopcart/confirm")
    @ResponseBody
    public ResponseData confirmShopCart(@RequestBody List<ShopCartItem> cartItems, HttpServletRequest request) {
        List<String> result = shopCartService.confirmShopCartItem(cartItems, createRequestContext(request), request);
        ResponseData data = new ResponseData(result);
        return data;
    }

    /**
     * 购物车页面初始化数据.
     * 
     * @param request
     *            统一上下文.
     * @return 返回初始化数据.
     */

    @RequestMapping(value = "order/shopping-cart")
    @ResponseBody
    public ModelAndView queryShopCartByStand(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        Long salesOrgId = Long.parseLong(request.getSession().getAttribute(SystemProfileConstants.SALES_ORG_ID).toString());
        SpmCurrency currency = new SpmCurrency();
        List<String> currencyCode = paramService.getSalesParamValues(iRequest, SystemProfileConstants.SPM_CURRENCY,
                salesOrgId);
        currency.setCurrencyCode(currencyCode.get(0));
        List<SpmCurrency> currencies = spmCurrencyService.querySpmCurrency(iRequest, currency, 1, 10);
        List<Product> cartItems = shopCartService.queryShopCartItem(iRequest, null);
        ModelAndView result = new ModelAndView();
        result.setViewName(getViewPath() + "/order/shopping-cart");
        result.addObject("products", cartItems);
        result.addObject("currencyCode", currencies.get(0).getSymbol());
        return result;
    }

    /**
     * 更改购买方式（备用）.
     * 
     * @param request
     *            统一上下文.
     * @param orderType
     *            类型.
     * @return 返回更改结果.
     */
    @RequestMapping(value = "/shopcart/changeOrderType")
    @ResponseBody
    public ResponseData saveOrderType(HttpServletRequest request, String orderType) {
        request.getSession().removeAttribute("orderType");
        request.getSession().setAttribute("orderType", orderType);
        ResponseData data = new ResponseData(true);
        return data;
    }

}
