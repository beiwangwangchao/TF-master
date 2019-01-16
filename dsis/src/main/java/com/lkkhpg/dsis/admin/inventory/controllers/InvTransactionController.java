/*
 *
 */

package com.lkkhpg.dsis.admin.inventory.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.inventory.service.IInvTransactionService;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.inventory.dto.InvTransactionQuery;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 库存事务Controller,用来处理库存事务增删查改操作.
 * 
 * @author qiubin.shen
 */
@Controller
public class InvTransactionController extends BaseController {

    @Autowired
    private IInvTransactionService invTransactionService;

    /**
     * 获取所有库存组织.
     * 
     * @param request
     *            请求参数
     * @return ResponseData结果集
     */
    @RequestMapping(value = "/im/trx/queryOrganizations")
    @ResponseBody
    public ResponseData queryOrganizations(HttpServletRequest request) {
        return new ResponseData(invTransactionService.queryOrganization(createRequestContext(request)));
    }

    /**
     * 根据查询条件获取所有库存事务.
     * 
     * @param page
     *            页
     * @param pagesize
     *            总页数
     * @param request
     *            请求
     * @param invTransactionQuery
     *            库存事务查询参数dto
     * @return ResponseData结果集
     */
    @RequestMapping(value = "/im/trx/query")
    @ResponseBody
    public ResponseData queryTransations(HttpServletRequest request,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, InvTransactionQuery invTransactionQuery) {
        HttpSession session = request.getSession();
        Long invOrgId = (Long) session.getAttribute(SystemProfileConstants.INV_ORG_ID);
        return new ResponseData(invTransactionService.queryInvTransactions(createRequestContext(request), page,
                pagesize, invTransactionQuery, invOrgId));
    }

    /**
     * 根据查询条件获取所有商品编号.
     * 
     * @param page
     *            页
     * @param pagesize
     *            总页数
     * @param request
     *            请求
     * @param codeCondition
     *            查询值
     * @param conditionType
     *            查询类型
     * @return ResponseData结果集
     */
    @RequestMapping(value = "/im/trx/queryItemNumbers")
    @ResponseBody
    public ResponseData queryItemNumbers(HttpServletRequest request,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, String codeCondition, String conditionType) {
        return new ResponseData(invTransactionService.queryItemNumbers(createRequestContext(request), page, pagesize,
                codeCondition, conditionType));
    }

    /**
     * 获取当前库存组织.
     * 
     * @param request
     *            请求参数
     * @return ResponseData结果集
     */
    @RequestMapping(value = "/im/trx/getCurrentOrganization")
    @ResponseBody
    public ResponseData getCurrentOrganization(HttpServletRequest request) {
        return new ResponseData(invTransactionService.getCurrentOrganization(createRequestContext(request)));
    }

    /**
     * 查询库存组织下的商品列表.
     * 
     * @param request
     *            上下文请求
     * @param invItem
     *            商品dto
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return ResponseData 响应数据
     */
    @RequestMapping(value = "/im/trx/queryItemsByOrgId")
    @ResponseBody
    public ResponseData queryItemsByOrgId(InvItem invItem, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(invTransactionService.queryItemsByOrgId(requestContext, invItem, page, pagesize));
    }

    /**
     * 查询当前库存组织下的所有商品类型.
     * 
     * @param request
     *            上下文请求
     * @param invOrgId
     *            库存组织ID
     * @return ResponseData 响应数据
     */
    @RequestMapping(value = "/im/trx/queryCategorysByInvOrgId")
    @ResponseBody
    public ResponseData queryCategorysByInvOrgId(HttpServletRequest request, Long invOrgId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(invTransactionService.queryCategorysByInvOrgId(requestContext, invOrgId));
    }
}
