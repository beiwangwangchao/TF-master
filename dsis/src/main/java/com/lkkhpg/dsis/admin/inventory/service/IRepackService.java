/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.service;

import java.util.List;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.exception.RepackTrxException;
import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.dto.RepackTrx;
import com.lkkhpg.dsis.common.inventory.dto.RepackTrxDetail;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.dto.InvItemHierarchy;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 重新分包Service接口.
 * 
 * @author hanrui.huang
 */
public interface IRepackService extends ProxySelf<IRepackService> {

    /**
     * 查询分包记录.
     * 
     * @param request
     *            请求上下文
     * @param repackTrx
     *            重新分包Dto
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return repackTrx 重新分包List
     */
    List<RepackTrx> queryRepack(IRequest request, RepackTrx repackTrx, int page, int pagesize);

    /**
     * 查询商品明细.
     * 
     * @param request
     *            请求上下文
     * @param invItemHierarchy
     *            商品分配DTO
     * @param page
     *            页数.
     * @param pagesize
     *            每页记录数.
     * @return itemHierarchy 商品明细List
     */
    List<InvItemHierarchy> queryItem(IRequest request, InvItemHierarchy invItemHierarchy, int page, int pagesize);

    /**
     * 查询商品明细.
     * 
     * @param request
     *            请求上下文
     * @param invItemHierarchy
     *            商品分配DTO
     * @return itemHierarchy 商品明细List
     */
    List<InvItemHierarchy> queryItem(IRequest request, InvItemHierarchy invItemHierarchy);

    /**
     * 获取商品明细-组合包中的批次信息.
     * 
     * @param request
     *            请求上下文
     * @param invOnhandQuantity
     *            库存量DTO
     * @return invOnhandQuantitys 库存量List
     */
    List<InvOnhandQuantity> queryComposeLot(IRequest request, InvOnhandQuantity invOnhandQuantity);

    /**
     * 保存分包记录.
     * 
     * @param request
     *            请求上下文
     * @param repackTrxs
     *            重新分包List
     * @return 重新分包List
     * @throws RepackTrxException
     *             分包异常
     */
    List<RepackTrx> batchUpdate(IRequest request, @StdWho List<RepackTrx> repackTrxs) throws RepackTrxException;

    /**
     * 删除分包管理.
     * 
     * @param request
     *            请求上下文
     * @param repackTrxs
     *            重新分包List
     * @return 执行结果
     */
    boolean batchDelete(IRequest request, List<RepackTrx> repackTrxs);

    /**
     * 查询同一库存组织下的商品包明细.
     * 
     * @param request
     *            统一上下文.
     * @param item
     *            查询条件.
     * @param page
     *            页数.
     * @param pagesize
     *            每页记录数.
     * @return 商品包明细List
     */
    List<InvItem> queryItemsByOrganizationId(IRequest request, InvItem item, int page, int pagesize);

    /**
     * 重新分包提交事务.
     * 
     * @param request
     *            请求上下文
     * @param repackTrxs
     *            重新分包DTO
     * @param invTransactions
     *            库存事务处理DTO
     * @param flag
     *            保存标记
     * @return 重新分包List
     * @throws InventoryException
     *             库存统一异常
     * @throws RepackTrxException
     *             分包异常
     */
    List<RepackTrx> submitTransaction(IRequest request, @StdWho List<RepackTrx> repackTrxs, boolean flag)
            throws InventoryException, RepackTrxException;

    /**
     * 批量操作重新分包明细数据.
     * 
     * @param repackTrx
     *            头行数据
     */
    void processRepackTrxDetails(@StdWho RepackTrx repackTrx);

    /**
     * 保存重新分包单.
     * 
     * @param request
     *            请求上下文
     * @param repackTrx
     *            重新分包事务Dto
     * @return 重新分包事务Dto
     */
    RepackTrx createRepack(IRequest request, @StdWho RepackTrx repackTrx);

    /**
     * 修改重新分包单.
     * 
     * @param repackTrx
     *            重新分包事务Dto
     * @return 重新分包事务Dto
     * @throws RepackTrxException
     *             重复提交Exception
     */
    RepackTrx updateCode(RepackTrx repackTrx) throws RepackTrxException;

    /**
     * 更新重新分包状态.
     * 
     * @param request
     *            请求上下文
     * @param repackTrxs
     *            重新分包DTO
     */
    void updateRepackStatus(IRequest request, @StdWho List<RepackTrx> repackTrxs);

    /**
     * 批量删除.
     * 
     * @param request
     *            IRequest
     * @param repackTrxDetails
     *            RepackTrxDetail列表
     * @return boolean 删除是否成功
     */
    boolean batchDeleteDetails(IRequest request, List<RepackTrxDetail> repackTrxDetails);

    /**
     * 更新重新分包状态.
     * 
     * @param request
     *            请求上下文
     * @param repackTrxDetail
     *            分包明细Dto
     * @param page
     *            页
     * @param pagesize
     *            每页记录数.
     * @return 分包明细List
     */
    List<RepackTrxDetail> selectDetail(IRequest request, RepackTrxDetail repackTrxDetail, int page, int pagesize);

    /**
     * 根据主健id查找RepackTrx 对象.
     * 
     * @param request
     *            IRequest
     * @param trxId
     *            trxId
     * @return RepackTrx RepackTrx对象
     */
    RepackTrx selectById(IRequest request, Long trxId);

    /**
     * 根据 RepackTrxDetail 查询RepackTrxDetail列表.
     * 
     * @param request
     *            IRequest
     * @param repackTrxDetail
     *            RepackTrxDetail参数
     * @return List RepackTrxDetail的对象列表
     */
    List<RepackTrxDetail> selectDetails(IRequest request, RepackTrxDetail repackTrxDetail);

    /**
     * 获取所选择批次的组合数量.
     * @param request 请求上下文
     * @param invTransaction 库存事务
     * @return 所选择批次的组合数量
     */
    int getLoTQuantity(IRequest request, InvTransaction invTransaction);
}
