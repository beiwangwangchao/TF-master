/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.mws.dto.AutoShipCriteria;
import com.lkkhpg.dsis.mws.dto.NativeSalesOrder;
import com.lkkhpg.dsis.mws.dto.Product;
import com.lkkhpg.dsis.mws.service.IMemberAutoShipOrderService;
import com.lkkhpg.dsis.mws.service.IProductService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * Autoship订单controller.
 * 
 * @author guanghui.liu
 *
 */
@Controller
public class AutoShipController extends BaseController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IMemberAutoShipOrderService memberAutoShipOrderService;

    @Autowired
    private IProductService productService;

    /**
     * 访问autoship订单时,准备数据.
     * 
     * @param request
     *            请求上下文
     * @return 跳转到autoship订单页面,带有填充页面的数据
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "account/account-autoship")
    @ResponseBody
    public ModelAndView orderConfirm(HttpServletRequest request) throws JsonProcessingException {
        ModelAndView mav = new ModelAndView();
        IRequest requestContext = createRequestContext(request);
        // 获取自动订单详细信息，1.如果查询不到记录则页面提示：请管理员开通自动订货权限 2.查询到自动订货商品信息，返回到前台
        Map<String, Object> autoShipInfo = memberAutoShipOrderService.queryAutoShipProducts(requestContext);
        if (null == autoShipInfo) {
            // 查询不到该会员自动订单记录
            mav.setViewName(getViewPath() + "/account/account-autoship");
            mav.addObject("result", "empty");
            return mav;
        } else {
            mav.addObject("historyLogistics", autoShipInfo.get("historyLogistics"));
            // mav.addObject("historySites",
            // objectMapper.writeValueAsString(autoShipInfo.get("sites")));
            mav.addObject("taxRate", autoShipInfo.get("taxRate"));
            mav.addObject("creditCard", autoShipInfo.get("creditCard"));
            mav.addObject("autoSalesOrgId", autoShipInfo.get("autoSalesOrgId"));
            mav.addObject("autoSalesOrgName", autoShipInfo.get("autoSalesOrgName"));
            mav.addObject("currency", autoShipInfo.get("currency"));
            mav.addObject("currencyCode", autoShipInfo.get("currencyCode"));
            mav.addObject("products", autoShipInfo.get("products"));
            mav.addObject("totalPV", autoShipInfo.get("totalPV"));
            mav.addObject("totalPrice", autoShipInfo.get("totalPrice"));
            mav.addObject("totalPoint", autoShipInfo.get("totalPoint"));
            mav.addObject("pointRate", autoShipInfo.get("pointRate"));
            mav.addObject("pointLimit", autoShipInfo.get("pointLimit"));
            mav.addObject("deliveryType", autoShipInfo.get("deliveryType"));
            mav.addObject("productList", objectMapper.writeValueAsString(autoShipInfo.get("products")));
        }

        Map<String, Object> allSites = memberAutoShipOrderService.querySites(requestContext,
                (List<SalesSites>) autoShipInfo.get("sites"));
        mav.setViewName(getViewPath() + "/account/account-autoship");
        mav.addObject("shipSites", allSites.get("shipSites"));
        mav.addObject("billSites", allSites.get("billSites"));
        return mav;
    }

    /**
     * 查询可添加商品信息.
     * 
     * @param request
     *            统一上下文.
     * @param criteria
     *            查询条件dto.
     * @return 返回符合条件的商品信息.
     */
    @RequestMapping(value = "/autoship/product/query")
    @ResponseBody
    public ResponseData getOptionalProducts(HttpServletRequest request, @RequestBody AutoShipCriteria criteria) {
        IRequest requestContext = createRequestContext(request);
        List<Product> products = memberAutoShipOrderService.queryOptionalProducts(requestContext, criteria);
        ResponseData data = new ResponseData(products);
        return data;
    }

    /**
     * 自动订单提交.
     * 
     * @param request
     *            统一上下文.
     * @param nativeSalesOrder
     *            提交信息.
     * @return 返回提交结果.
     * @throws CommOrderException
     *             抛出更新异常.
     */
    @RequestMapping(value = "/autoship/productline/update")
    @ResponseBody
    public ResponseData updateAutoShipLine(HttpServletRequest request, @RequestBody NativeSalesOrder nativeSalesOrder)
            throws CommOrderException {
        IRequest requestContext = createRequestContext(request);
        List<String> result = memberAutoShipOrderService.updateAutoShipLine(requestContext,
                nativeSalesOrder.getProducts(), nativeSalesOrder.getAutoshipOrder());
        ResponseData data = new ResponseData(result);
        return data;
    }

    /**
     * 为选中的商品添加图片信息.
     * 
     * @param request
     *            统一上下文.
     * @param products
     *            需要添加的商品集合.
     * @return 返回添加结果结果.
     */
    @RequestMapping(value = "/autoship/productinfo/addimg")
    public ResponseData addProductImgInfo(HttpServletRequest request, @RequestBody List<Product> products) {
        IRequest requestContext = createRequestContext(request);
        List<Product> result = productService.getImageForProducts(requestContext, products);
        ResponseData data = new ResponseData(result);
        return data;
    }

    /**
     * 查询当前会员自动订货单的商品信息.
     * 
     * @param request
     *            统一上下文.
     * @return 返回结果.
     */
    @RequestMapping(value = "/autoship/product/querybyid")
    @ResponseBody
    public ResponseData getProduct(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberAutoShipOrderService.getProduct(requestContext));
    }

    /**
     * 查询当前自动订货单所有地址信息.
     * 
     * @param request
     *            统一上下文.
     * @return 返回结果.
     */
    @RequestMapping(value = "/autoship/sites/query")
    @ResponseBody
    public ResponseData getAllSites(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberAutoShipOrderService.getAllSites(requestContext));
    }
}
