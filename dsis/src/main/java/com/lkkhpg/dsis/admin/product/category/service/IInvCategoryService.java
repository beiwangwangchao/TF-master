/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.category.service;

import java.util.List;

import com.lkkhpg.dsis.admin.product.exception.ItemException;
import com.lkkhpg.dsis.common.product.dto.InvCategoryB;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 商品类别管理接口.
 * 
 * @author linxiaodong
 */
public interface IInvCategoryService extends ProxySelf<IInvCategoryService> {

    /**
     * 条件查询.
     * 
     * @param request
     *            统一上下文.
     * @param invCategoryB
     *            查询条件.
     * @return 类别列表.
     */
    List<InvCategoryB> queryCategory(IRequest request, InvCategoryB invCategoryB);

    /**
     * 查询顶层类别.
     * 
     * @param request
     *            统一上下文.
     * @return 顶层类别列表.
     */
    List<InvCategoryB> queryTopCategory(IRequest request);

    /**
     * 查询子类别.
     * 
     * @param request
     *            统一上下文.
     * @param invCategoryB
     *            查询条件.
     * @return 子类别列表.
     */
    List<InvCategoryB> queryChildrenCategory(IRequest request, InvCategoryB invCategoryB);

    /**
     * 保存商品类别.
     * 
     * @param request
     *            统一上下文.
     * @param invCategoryB
     *            商品类别.
     * @return 保存后的商品类别.
     * @throws ItemException
     *             商品统一异常.
     */
    InvCategoryB saveOrUpdate(IRequest request, @StdWho InvCategoryB invCategoryB) throws ItemException;

    /**
     * 批量删除商品类别.
     * 
     * @param request
     *            统一上下文.
     * @param invCategoryB
     *            商品类别.
     * @return true/flase
     * @throws ItemException
     *             商品统一异常
     */
    boolean batchDelete(IRequest request, InvCategoryB invCategoryB) throws ItemException;

    /**
     * 删除单个商品类别.
     * 
     * @param request
     *            统一上下文.
     * @param invCategoryB
     *            商品类别.
     * @return true/flase
     * @throws ItemException
     *             商品统一异常.
     */
    boolean deleteCategory(IRequest request, InvCategoryB invCategoryB) throws ItemException;

    /**
     * 获取最底层且有分配商品的类别.
     * 
     * @param request
     *            统一上下文.
     * @param itemType
     *            类型：商品、商品包.
     * @return 底层且分配商品的类别列表.
     */
    List<InvCategoryB> queryBottomCategory(IRequest request, String itemType);

    /**
     * 获取所有有效的商品的类别.
     * 
     * @param request
     *            请求上下文
     * @return List 商品类别List
     */
    List<InvCategoryB> queryCategorySelection(IRequest request);
    
    /**
     * 获取所有有效的商品一级类别.
     * 
     * @param request
     *            请求上下文
     * @return List 商品类别List
     */
    List<InvCategoryB> queryParentCates(IRequest request);

    /**
     * 查询该类型是否已存在
     *
     * @param request
     *             请求上下文
     * @param invCategoryB
     *             请求体，里面保存类型名
     * @return Long
     *             返回已存在的类型数量
     */
    Long beforeInsert(IRequest request, InvCategoryB invCategoryB);
}
