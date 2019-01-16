/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.vendor.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.vendor.service.IPoVendorService;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.inventory.vendor.dto.PoVendor;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 供应商管理Controller.
 * 
 * @author liang.rao
 *
 */
@Controller
public class PoVendorController extends BaseController {

    @Autowired
    private IPoVendorService poVendorService;

    /**
     * 保存供应商信息.
     * 
     * @param vendor
     *            供应商信息.
     * @param request
     *            请求上下文.
     * @return 响应信息.
     * @throws CommSystemProfileException
     *             系统配置统一异常.
     * @throws InventoryException
     *             库存统一异常.
     */
    @RequestMapping(value = "/po/vendor/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveVendor(@RequestBody PoVendor vendor, HttpServletRequest request, BindingResult result)
            throws CommSystemProfileException, InventoryException {
        IRequest requestContext = createRequestContext(request);
        List<PoVendor> verdors = new ArrayList<PoVendor>();
        if (verdors != null) {
            getValidator().validate(verdors, result);
        }
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        vendor = poVendorService.saveVendor(requestContext, vendor);
        if (vendor != null) {
            verdors.add(vendor);
            return new ResponseData(verdors);
        } else {
            return new ResponseData(false);
        }
    }

    /**
     * 查询供应商信息.
     * 
     * @param request
     *            请求上下文.
     * @param vendor
     *            供应商信息.
     * @param page
     *            页.
     * @param pagesize
     *            分页大小.
     * @return 响应信息.
     */
    @RequestMapping(value = "/po/vendor/query")
    @ResponseBody
    public ResponseData queryVendor(HttpServletRequest request, @ModelAttribute PoVendor vendor,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(poVendorService.queryVendor(requestContext, vendor, page, pagesize));
    }

    /**
     * 根据Id查询供应商信息.
     * 
     * @param request
     *            请求上下文.
     * @param vendorId
     *            供应商Id.
     * @param page
     *            页.
     * @param pagesize
     *            分页大小.
     * @return 响应信息.
     */
    @RequestMapping(value = "/po/vendor/queryById")
    @ResponseBody
    public ResponseData queryVendorById(HttpServletRequest request, Long vendorId,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        PoVendor vendor = new PoVendor();
        vendor.setVendorId(vendorId);
        return new ResponseData(poVendorService.queryVendor(requestContext, vendor, page, pagesize));
    }

    /**
     * 失效供应商信息.
     * 
     * @param vendor
     *            供应商信息集合.
     * @param request
     *            请求上下文.
     * @return 响应信息.
     * @throws InventoryException
     *             库存统一异常.
     */
    @RequestMapping(value = "/po/vendor/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteVendor(@RequestBody PoVendor vendor, HttpServletRequest request)
            throws InventoryException {
        IRequest requestContext = createRequestContext(request);
        poVendorService.deleteVendor(requestContext, vendor);
        return new ResponseData();
    }
}
