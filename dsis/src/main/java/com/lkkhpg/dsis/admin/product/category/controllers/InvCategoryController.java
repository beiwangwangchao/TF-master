/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.category.controllers;

import com.lkkhpg.dsis.admin.product.category.service.IInvCategoryService;
import com.lkkhpg.dsis.admin.product.exception.ItemException;
import com.lkkhpg.dsis.admin.product.product.service.IItemService;
import com.lkkhpg.dsis.common.product.dto.InvCategoryB;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品类别控制层.
 *
 * @author linxiaodong
 */
@Controller
public class InvCategoryController extends BaseController {

    @Autowired
    private IInvCategoryService invCategoryService;

    @Autowired
    private IItemService itemService;


    /**
     * 查询商品类别树.
     *
     * @param request HttpServletRequest.
     * @return 商品类别树.
     */
    @RequestMapping(value = "/pm/category/queryTop")
    @ResponseBody
    public List<InvCategoryB> queryTopCategory(HttpServletRequest request) {
        // 构建上下文参数
        IRequest requestContext = createRequestContext(request);
        // 查询顶层节点
        List<InvCategoryB> topCategory = invCategoryService.queryTopCategory(requestContext);
        return topCategory;
    }

    /**
     * 查询子节点.
     *
     * @param request      HttpServletRequest.
     * @param invCategoryB 查询条件.
     * @return 子类别列表.
     */
    @RequestMapping(value = "/pm/category/queryChildren")
    @ResponseBody
    public List<InvCategoryB> queryChildren(HttpServletRequest request, @ModelAttribute InvCategoryB invCategoryB) {
        // 构建上下文参数
        IRequest requestContext = createRequestContext(request);
        List<InvCategoryB> subCategories = invCategoryService.queryChildrenCategory(requestContext, invCategoryB);
        return subCategories;
    }

    /**
     * 查询类别详情.
     *
     * @param request      HttpServletRequest.
     * @param invCategoryB 查询条件.
     * @return 类别列表.
     */
    @RequestMapping(value = "/pm/category/query")
    @ResponseBody
    public List<InvCategoryB> queryCategory(HttpServletRequest request, @ModelAttribute InvCategoryB invCategoryB) {
        // 构建上下文参数
        IRequest requestContext = createRequestContext(request);
        List<InvCategoryB> list = invCategoryService.queryCategory(requestContext, invCategoryB);
        return list;
    }

    /*
    * 查询类别的类别价格
    * */
    @RequestMapping(value = "/pm/category/queryPrice")
    @ResponseBody
    public ResponseData setFirstPrice(HttpServletRequest request, String categoryId) {
        IRequest requestContext = createRequestContext(request);
        InvCategoryB invCategoryB=new InvCategoryB();
        invCategoryB.setCategoryId(Long.valueOf(categoryId));
        List<InvCategoryB> list = invCategoryService.queryCategory(requestContext, invCategoryB);
        return new ResponseData(list);
    }

    /**
     * 查询类别分配的商品.
     *
     * @param request      HttpServletRequest.
     * @param invCategoryB 查询条件.
     * @param page         页码.
     * @param pagesize     每页记录数.
     * @return 商品类别列表.
     */
    @RequestMapping(value = "/pm/category/queryItems")
    @ResponseBody
    public ResponseData queryItemByCategory(HttpServletRequest request, @ModelAttribute InvCategoryB invCategoryB,
                                            int page, int pagesize) {
        // 构建上下文参数
        IRequest requestContext = createRequestContext(request);
        List<InvItem> list = itemService.queryProductByCategory(requestContext, invCategoryB, page, pagesize);
        return new ResponseData(list);
    }

    /**
     * 保存更新类别和商品分配信息.
     *
     * @param request      HttpServletRequest.
     * @param invCategoryB 商品类别.
     * @param result       BindingResult.
     * @return ResponseData.
     * @throws ItemException 商品统一异常.
     */
    @RequestMapping(value = "/pm/category/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveCategory(HttpServletRequest request, @RequestBody InvCategoryB invCategoryB,
                                     BindingResult result) throws ItemException {
        // 构建上下文参数
        IRequest requestContext = createRequestContext(request);

        // 校验
        getValidator().validate(invCategoryB, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        // updaed by 11816 at 2017/12/19 14:51
        if (("".equals(invCategoryB.getCategoryId()) || null == invCategoryB.getCategoryId())
                && invCategoryService.beforeInsert(requestContext, invCategoryB) > 0) {
            ResponseData responseData = new ResponseData();
            responseData.setMessage("已存在的商品类型！");
            responseData.setSuccess(false);
            return responseData;
        } else {
            InvCategoryB categoryB = invCategoryService.saveOrUpdate(requestContext, invCategoryB);
            List<InvCategoryB> list = new ArrayList<>(1);
            list.add(categoryB);
            return new ResponseData(list);
        }
    }

    /**
     * 删除单个类别.
     *
     * @param request      HttpServletRequest.
     * @param invCategoryB 商品类别.
     * @return ResponseData.
     * @throws ItemException 商品统一异常.
     */
    @RequestMapping(value = "/pm/category/delete")
    @ResponseBody
    public ResponseData deleteCategory(HttpServletRequest request, @ModelAttribute InvCategoryB invCategoryB)
            throws ItemException {
        // 构建上下文参数
        IRequest requestContext = createRequestContext(request);
        invCategoryService.deleteCategory(requestContext, invCategoryB);

        return new ResponseData(true);
    }

    /**
     * 删除类别和其子孙类别.
     *
     * @param request      HttpServletRequest.
     * @param invCategoryB 商品类别.
     * @return ResponseData.
     * @throws ItemException 商品统一异常
     */
    @RequestMapping(value = "/pm/category/batchDelete")
    @ResponseBody
    public ResponseData batchDeleteCategory(HttpServletRequest request, @ModelAttribute InvCategoryB invCategoryB)
            throws ItemException {
        // 构建上下文参数
        IRequest requestContext = createRequestContext(request);
        invCategoryService.batchDelete(requestContext, invCategoryB);
        return new ResponseData(true);
    }

    /**
     * 获取所有可用的商品类别.
     *
     * @param request 请求上下文
     * @return ResponseData 结果集
     */
    @RequestMapping(value = "/pm/category/querySelection")
    @ResponseBody
    public ResponseData queryOrganizations(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(invCategoryService.queryCategorySelection(requestContext));
    }

    /**
     * 获取所有可用的商品一级类别.
     *
     * @param request 请求上下文
     * @return ResponseData 结果集
     */
    @RequestMapping(value = "/pm/category/queryParentCates")
    @ResponseBody
    public ResponseData queryParentCates(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(invCategoryService.queryParentCates(requestContext));
    }

}
