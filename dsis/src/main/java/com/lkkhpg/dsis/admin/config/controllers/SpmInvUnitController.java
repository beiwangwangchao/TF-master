/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.config.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.dto.InvUnitConvert;
import com.lkkhpg.dsis.common.product.dto.InvUnitOfMeasureB;
import com.lkkhpg.dsis.common.service.ICommInvUnitService;
import com.lkkhpg.dsis.common.service.ICommItemService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 单位信息处理Controller.
 * 
 * @author houmin
 *
 */
@Controller
public class SpmInvUnitController extends BaseController {

    @Autowired
    private ICommInvUnitService commInvUnitService;
    @Autowired
    private ICommItemService commItemService;

    /**
     * 查询单位信息.
     * 
     * @param request
     *            统一上下文
     * @param invUnitOfMeasureB
     *            单位对象
     * @param page
     *            页码
     * @param pagesize
     *            每页显示的记录数
     * @return 对应单位信息
     * @throws CommSystemProfileException
     *             系统统一配置异常
     */
    @RequestMapping(value = "spm/unit/queryUnitNamesOfUomManage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryUnitInfo(HttpServletRequest request, InvUnitOfMeasureB invUnitOfMeasureB,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws CommSystemProfileException {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(
                commInvUnitService.queryUnitNamesOfUomManage(iRequest, invUnitOfMeasureB, page, pagesize));
    }

    /**
     * 批量更新单位信息.
     * 
     * @param request
     *            统一上下文
     * @param invUnitOfMeasureBs
     *            单位对象集合
     * @param result
     *            结果集绑定
     * @return 更新后的单位信息
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "spm/unit/batchUpdateUoms", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData batchUpdateUoms(HttpServletRequest request,
            @RequestBody List<InvUnitOfMeasureB> invUnitOfMeasureBs, BindingResult result)
            throws CommSystemProfileException {
        getValidator().validate(invUnitOfMeasureBs, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(commInvUnitService.batchUpdateUoms(iRequest, invUnitOfMeasureBs));
    }

    /**
     * 单位维护界面查询商品.
     * 
     * @param request
     *            统一上下文
     * @param invItem
     *            商品对象
     * @param page
     *            页码
     * @param pagesize
     *            每页显示的记录数
     * @return 满足条件的商品信息
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "spm/unit/queryItemsOfUnitConvert", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryItemsOfUnitConvert(HttpServletRequest request, InvItem invItem,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws CommSystemProfileException {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(commItemService.queryItemsOfUnitConvert(iRequest, invItem, page, pagesize));
    }

    /**
     * 查询单位转换信息.
     * 
     * @param request
     *            统一上下文
     * @param itemId
     *            商品Id
     * @param page
     *            页码
     * @param pagesize
     *            每页显示的记录数
     * @return 对应商品的单位转换信息
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "spm/unit/queryInvUnitConverts")
    @ResponseBody
    public ResponseData queryInvUnitConverts(HttpServletRequest request, @RequestParam Long itemId,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws CommSystemProfileException {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(commInvUnitService.queryInvUnitConverts(iRequest, itemId, page, pagesize));
    }

    /**
     * 批量更新商品单位转换信息.
     * 
     * @param request
     *            统一上下文
     * @param invUnitConverts
     *            商品单位转换信息集合
     * @param result
     *            结果集校验
     * @return 更新后商品单位转换信息
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "spm/unit/batchUpdateInvUnitConverts", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData batchUpdateInvUnitConverts(HttpServletRequest request,
            @RequestBody List<InvUnitConvert> invUnitConverts, BindingResult result) throws CommSystemProfileException {
        getValidator().validate(invUnitConverts, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(commInvUnitService.batchUpdateInvUnitConverts(iRequest, invUnitConverts));
    }

}
