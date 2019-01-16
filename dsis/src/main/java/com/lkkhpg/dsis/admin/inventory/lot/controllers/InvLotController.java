/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.lot.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.lot.service.InvLotService;
import com.lkkhpg.dsis.common.inventory.dto.InvLot;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 批次实现类.
 * 
 * @author runbai.chen
 *
 */
@Controller
public class InvLotController extends BaseController {

    @Autowired
    private InvLotService invLotService;

    /**
     * 查询批次.
     * 
     * @param request
     *            用户请求
     * @param invLot
     *            批次查询条件
     * @param page
     *            客户端提交页面总数，继承BaseControll，默认值为1
     * @param pagesize
     *            客户端提交页面数据数量，继承BaseControll，默认值为10
     * @return 批次列表
     * @throws InventoryException
     *             as
     * @throws IOException
     *             IO Exception
     * @throws IllegalAccessException
     *             Illegal Access Exception
     * @throws IllegalArgumentException
     *             Illegal Argument Exception
     */
    @RequestMapping(value = "/im/lot/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData selectInvLotsByParas(HttpServletRequest request, HttpServletResponse response, InvLot invLot,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize)
            throws InventoryException, IllegalArgumentException, IllegalAccessException, IOException {
        IRequest iRequest = createRequestContext(request);
        List<InvLot> list = invLotService.selectInvLotsByParas(iRequest, invLot, page, pagesize);

        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, list);
            return null;
        } else {
            return new ResponseData(list);
        }

    }

    /**
     * 提交客户端数据到数据库，调用invLotService.batchProcessInvLot.
     * 
     * @param request
     *            用户请求
     * @param invLots
     *            客户端返回批次LIST
     * @param result
     *            验证结果 @return 批次列表
     * @return 批次數據
     * @throws InventoryException
     *             库存统一异常
     * 
     */
    @RequestMapping(value = "/im/lot/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submit(HttpServletRequest request, @RequestBody List<InvLot> invLots, BindingResult result)
            throws InventoryException {
        getValidator().validate(invLots, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest iRequest = createRequestContext(request);
        if (!validateInvLot(iRequest, invLots)) {
            throw new InventoryException(InventoryException.SHIPPING_EXIST, null);
        }

        invLotService.batchProcessInvLot(iRequest, invLots);
        return new ResponseData(invLots);
    }

    /**
     * 判断数据的唯一性.
     * 
     * @param request
     *            请求上下文
     * @param invLots
     *            批次集合
     * @return false 有相同的数据 true没有重复的数据
     */
    private Boolean validateInvLot(IRequest request, List<InvLot> invLots) {

        HashSet<String> invLotSet = new HashSet<String>();
        for (InvLot invLot : invLots) {
            Object[] ids = { invLot.getOrganizationId(), invLot.getItemId(), invLot.getLotNumber() };
            String key = StringUtils.join(ids, ":");
            if (invLotSet.contains(key)) {
                return false;
            } else {
                invLotSet.add(key);
            }
        }
        return invLotService.validateInvLot(request, invLots);

    }

    /**
     * 查询商品下的具有库存量的批次号.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品id
     * @param orgId
     *            组织ID
     * @return ResponseData结果集
     */
    @RequestMapping(value = "/im/itemLot/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryItemLots(HttpServletRequest request, Long itemId, Long orgId) {
        if (itemId == null || orgId == null) {
            return new ResponseData();
        }
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(invLotService.queryItemLots(iRequest, itemId, orgId));
    }
}
