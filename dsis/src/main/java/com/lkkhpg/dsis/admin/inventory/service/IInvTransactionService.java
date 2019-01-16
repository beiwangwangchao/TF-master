/*
 *
 */

package com.lkkhpg.dsis.admin.inventory.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.dto.InvTransactionQuery;
import com.lkkhpg.dsis.common.product.dto.InvCategoryB;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * @author qiubin.shen
 */
public interface IInvTransactionService {

    /**
     * 根据查询条件获取所有库存组织.
     * 
     * @param request
     *            请求
     * @return 库存组织结果集
     */
    List<Map<String, Object>> queryOrganization(IRequest request);

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
     * @param invOrgId
     *            当前库存组织ID
     * @return 库存事务结果集
     */
    List<InvTransaction> queryInvTransactions(IRequest request, int page, int pagesize,
            InvTransactionQuery invTransactionQuery, Long invOrgId);

    /**
     * 根据查询条件获取商品编号.
     * 
     * @param request
     *            请求
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @param codeCondition
     *            查询值
     * @param conditionType
     *            查询类型
     * @return 商品结果集
     */
    List<InvItem> queryItemNumbers(IRequest request, int page, int pagesize, String codeCondition,
            String conditionType);

    /**
     * 创建库存事务处理.
     * 
     * @param request
     *            请求上下文
     * @param invTransactions
     *            库存事务处理List
     * @return invTransactions 库存事务处理List
     * @throws InventoryException
     *             库存统一异常
     */
    List<InvTransaction> processTransaction(IRequest request, @StdWho List<InvTransaction> invTransactions)
            throws InventoryException;

    /**
     * 获取当前库存组织.
     * 
     * @param request
     *            请求
     * @return spmInvOrganizations 库存组织结果集
     */
    List<SpmInvOrganization> getCurrentOrganization(IRequest request);

    /**
     * 根据当前库存组织获取商品列表.
     *
     * @param request
     *            请求
     * @param item
     *            商品查询参数
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @return invItems 商品结果集
     */
    List<InvItem> queryItemsByOrgId(IRequest request, InvItem item, int page, int pagesize);

    /**
     * 根据当前库存组织获取商品类别列表.
     *
     * @param request
     *            请求
     * @param invOrgId
     *            库存组织ID
     * @return invCategoryBs 商品结果集
     */
    List<InvCategoryB> queryCategorysByInvOrgId(IRequest request, Long invOrgId);

}
