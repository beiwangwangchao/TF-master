/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lkkhpg.dsis.common.product.dto.InvCategoryB;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.mws.constant.MwsOrderConstants;
import com.lkkhpg.dsis.mws.dto.Product;
import com.lkkhpg.dsis.mws.dto.ShopCartItem;
import com.lkkhpg.dsis.mws.service.IProductService;
import com.lkkhpg.dsis.mws.service.IShopCartService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 商品controller.
 * 
 * @author zihao.yang
 *
 */
@Controller
public class FastShoppingController extends BaseController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IShopCartService shopCartService;

    /**
     * 根据商品类型获取商品.
     * 
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/fastShopping/queryProductsInCategory")
    @ResponseBody
    public ResponseData queryProductsByInCategorys(HttpServletRequest request) throws UnsupportedEncodingException {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        String token = generateCaptchaKey();
        session.setAttribute(MwsOrderConstants.ORDER_CONFIRM_TOKEN, token);
        Map<String, Object> result = productService.getProductsByInCategorys(requestContext);
        List<Object> resultList = new ArrayList<Object>();
        resultList.add(token);
        resultList.add(result);
        return new ResponseData(resultList);
    }



    @RequestMapping(value = "/fastShopping/queryCategory")
    @ResponseBody
    public ResponseData queryProductsCategory(HttpServletRequest request) throws UnsupportedEncodingException {
        IRequest requestContext = createRequestContext(request);
        List<Object>resultLists=new ArrayList<Object>();
        resultLists.add(productService.getProductCateryItem(requestContext));
        return new ResponseData(resultLists);
    }


    /**
     * 生成token.
     * 
     * @return 返回 UUID 形式的 token
     */
    private String generateCaptchaKey() {
        return UUID.randomUUID().toString();
    }

    /**
     * 快速下单检验最小购买量.
     * 
     * @param request
     *            统一上下文.
     * @param products
     *            商品明细.
     * @return 返回检验结果.
     */
    @RequestMapping(value = "/fastShopping/checkMinQuantity")
    @ResponseBody
    public ResponseData checkMinQuantity(HttpServletRequest request, @RequestBody List<ShopCartItem> products) {
        List<String> result = shopCartService.checkMinQuantity(createRequestContext(request), products);
        ResponseData data = new ResponseData(result);
        return data;
    }
}
