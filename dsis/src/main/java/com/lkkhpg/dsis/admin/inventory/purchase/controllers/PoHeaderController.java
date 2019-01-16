/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.purchase.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.inventory.purchase.service.IPoHeaderService;
import com.lkkhpg.dsis.common.inventory.purchase.dto.PoHeader;
import com.lkkhpg.dsis.common.inventory.purchase.dto.PoLine;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 采购订单Controller.
 * 
 * @author HuangJiaJing
 *
 */
@Controller
public class PoHeaderController extends BaseController {
    @Autowired
    private IPoHeaderService headerService;

    /**
     * 查询采购订单头.
     * 
     * @param request
     *            请求上下文
     * @param poHeader
     *            采购订单dto
     * @param page
     *            页数
     * @param pagesize
     *            每页显示的条数
     * @return ResponseData集合
     */
    @RequestMapping(value = "/inv/poheader/query")
    @ResponseBody
    public ResponseData queryByPoHeader(HttpServletRequest request, PoHeader poHeader,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(headerService.queryPoHeader(requestContext, poHeader, page, pagesize));
    }

    /**
     * 查询商品订单行.
     * 
     * @param request
     *            请求上下文
     * @param poLine
     *            商品订单dto
     * @param page
     *            页数
     * @param pagesize
     *            每页显示的条数
     * @return ResponseData集合
     */
    @RequestMapping(value = "/inv/poline/query")
    @ResponseBody
    public ResponseData queryByPoLine(HttpServletRequest request, PoLine poLine,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(headerService.queryPoLine(requestContext, poLine, page, pagesize));
    }
    
    /**
     * 查询商品订单行（不分页）.
     * 
     * @param request
     *            请求上下文
     * @param poLine
     *            商品订单dto
     * @param //page
     *            页数
     * @param //pagesize
     *            每页显示的条数
     * @return ResponseData集合
     */
    @RequestMapping(value = "/inv/poline/queryNoPage")
    @ResponseBody
    public ResponseData queryByPoLine(HttpServletRequest request, PoLine poLine) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(headerService.queryPoLineNoPage(requestContext, poLine));
    }
    
    /**
     * 保存采购订单.
     * 
     * @param request
     *            请求上下文
     * @param poHeaders
     *            采购订单集合
     * @return ResponseData集合
     */
    @RequestMapping(value = "/inv/poheader/save")
    @ResponseBody
    public ResponseData saveByPoHeader(HttpServletRequest request, @RequestBody List<PoHeader> poHeaders) {
        IRequest requestContext = createRequestContext(request);
        poHeaders = headerService.savePoHeader(requestContext, poHeaders);
        return new ResponseData(poHeaders);
    }
    @RequestMapping(value = "/inv/poheader/submit")
    @ResponseBody
    public ResponseData submitByPoHeader(HttpServletRequest request, @RequestBody List<PoHeader> poHeaders) {
        HttpSession session = request.getSession();
        IRequest requestContext = createRequestContext(request);
        requestContext.setAttribute("createdBy", Long.parseLong(String.valueOf(session.getAttribute("userId"))));
        requestContext.setAttribute("orgId",  Long.parseLong(String.valueOf(session.getAttribute("_invOrgId"))));
        poHeaders = headerService.submitPoHeader(requestContext, poHeaders);
        return new ResponseData(poHeaders);
    }
}
