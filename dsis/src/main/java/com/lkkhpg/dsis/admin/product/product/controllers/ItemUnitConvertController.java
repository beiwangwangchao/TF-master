/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.product.product.service.IInvUnitConvertService;
import com.lkkhpg.dsis.common.product.dto.InvUnitConvert;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 商品单位转换率Controller.
 * 
 * @author mclin
 */
@Controller
public class ItemUnitConvertController extends BaseController {


    @Autowired
    private IInvUnitConvertService invUnitConvertService;

    /**
     * 获取商品单位转换率信息.
     * 
     * @param request
     *            请求上下文
     * @param invUnitConvert
     *            商品转换率Dto
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/pm/iuc/get")
    @ResponseBody
    public ResponseData getItemUnitConvert(HttpServletRequest request, InvUnitConvert invUnitConvert) {
        IRequest requestContext = createRequestContext(request);
        if (invUnitConvert == null) {
            return new ResponseData();
        }
        List<InvUnitConvert> list = new ArrayList<>();
        InvUnitConvert iuc = invUnitConvertService.getInvUnitConvert(requestContext, invUnitConvert);
        if (iuc == null) {
            return new ResponseData();
        }
        list.add(iuc);
        return new ResponseData(list);
    }

    /**
     * 查询商品主单位.
     * 
     * @param request
     *            请求上下文
     * @param itemUnitConvert
     *            商品转化率
     * @param page
     *            起始页
     * @param pagesize
     *            页大小
     * @return ResponseData结果集
     */
    @RequestMapping(value = "/im/itemPackType/query")
    @ResponseBody
    public ResponseData queryItemPackTypes(HttpServletRequest request, InvUnitConvert itemUnitConvert,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);

        return new ResponseData(invUnitConvertService.queryItemPackTypes(requestContext, itemUnitConvert));
    }

}
