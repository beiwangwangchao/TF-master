/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.product.product.service.IUomService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 单位(包装类型)Controller.
 * 
 * @author mclin
 */
@Controller
public class UomController extends BaseController {

    @Autowired
    private IUomService uomService;

    /**
     * 获取库存组织信息.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品ID
     * @param uomCode
     *            单位代码
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/pm/uom/query")
    public ResponseData queryOrgs(HttpServletRequest request, @RequestParam Long itemId, @RequestParam String uomCode) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(uomService.queryUomSelection(requestContext, itemId, uomCode));
    }

    /**
     * 获取初始单位信息.
     * 
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/pm/uom/queryAll")
    @ResponseBody
    public ResponseData queryOrgs(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(uomService.queryAllUom(requestContext));
    }
}
