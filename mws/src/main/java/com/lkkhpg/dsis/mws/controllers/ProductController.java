/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.product.dto.InvItemAttrTl;
import com.lkkhpg.dsis.mws.constant.ProductConstants;
import com.lkkhpg.dsis.mws.dto.Product;
import com.lkkhpg.dsis.mws.dto.ShopCartItem;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.mws.service.IProductService;
import com.lkkhpg.dsis.mws.service.IShopCartService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 商品controller.
 * 
 * @author xiawang.liu
 *
 */
@Controller
public class ProductController extends BaseController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IShopCartService shopCartService;

    /**
     * 查询商品类型.
     * 
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/product/queryInvCategorys")
    @ResponseBody
    public ResponseData queryInvCategorys(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(productService.getProductCategorys(requestContext));
    }

    /**
     * 根据商品类型获取商品.
     * 
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/product/queryProductsInCategory")
    @ResponseBody
    public Map<String, Object> queryProductsByInCategorys(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return productService.getProductsByInCategorys(requestContext);
    }

    /**
     * 根据查询条件查询商品详细信息.
     * 
     * @param request
     *            请求上下文
     * @param product
     *            商品
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/product/queryDetailProductsByWhereClause")
    @ResponseBody
    public ResponseData getDetailProductsByWhereClause(HttpServletRequest request, Product product) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(productService.getDetailProductsByWhereClause(requestContext, product));
    }

    /**
     * 根据查询条件查询商品简易信息.
     * 
     * @param request
     *            请求上下文
     * @param product
     *            商品
     * @param page
     * @param pagesize
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/product/querySimpleProductsByWhereClause")
    @ResponseBody
    public ResponseData getSimpleProductsByWhereClause(HttpServletRequest request, Product product,
            @RequestParam(defaultValue = ProductConstants.PAGE) int page,
            @RequestParam(defaultValue = ProductConstants.PAGESIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        List<Product> list = productService.getSimpleProductsByWhereClause(requestContext, product, page, pagesize);
        /*for (int i =0;i<list.size();i++) {
            List<InvItemAttrTl> invList = productService.getWhetherHide(requestContext, list.get(i).getItemId());
            list.add(invList.get(0));
        }*/
        return new ResponseData(list);
    }

    /**
     * 添加到购物车.
     * 
     * @param request
     *            请求上下文
     * @param cart
     *            购物车
     * @return responseData 状态
     * @throws MemberException
     */
    @RequestMapping(value = "/product/addToCart")
    @ResponseBody
    public ResponseData addToCart(HttpServletRequest request, ShopCartItem cart) throws MemberException {
        shopCartService.insertShopCartItem(cart, createRequestContext(request));
        List<Integer> number = new ArrayList<Integer>();
        IRequest iRequest = createRequestContext(request);
        Long memberId = Long.parseLong(iRequest.getAttribute(Member.FIELD_MEMBER_ID).toString());
        Long salesOrgId = Long.parseLong(iRequest.getAttribute(SystemProfileConstants.SALES_ORG_ID).toString());
        number.add(shopCartService.getShopCartCount(memberId, salesOrgId));
        ResponseData data = new ResponseData(number);
        return data;
    }
    
    /**
     * 获取是否隐藏.
     * 
     * @param request
     *            统一上下文
     * @param itemId
     *            商品id
     * @return list 集合
     */
    @RequestMapping(value = "product/getWhetherHide")
    public ResponseData getWhetherHide(HttpServletRequest request, Long itemId) {
        List<InvItemAttrTl> list = productService.getWhetherHide(createRequestContext(request), itemId);
        List<InvItemAttrTl> ls = new ArrayList<InvItemAttrTl>();
        for (InvItemAttrTl invItemAttrTl : list) {
            if ("SPJJ".equals(invItemAttrTl.getName())
                    || "SYSM".equals(invItemAttrTl.getName())
                    || "GGCS".equals(invItemAttrTl.getName())
                    || "DOSE".equals(invItemAttrTl.getName())) {
                ls.add(invItemAttrTl);
            }
        }
        return new ResponseData(ls);
    }
}
