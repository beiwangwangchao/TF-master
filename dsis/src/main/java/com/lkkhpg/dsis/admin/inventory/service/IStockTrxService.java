/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.service;

import java.util.List;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.dto.StockTrx;
import com.lkkhpg.dsis.common.inventory.dto.StockTrxDetail;
import com.lkkhpg.dsis.common.inventory.dto.Storage;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 出入库Service接口.
 * 
 * @author mclin
 */
public interface IStockTrxService extends ProxySelf<IStockTrxService> {

    /**
     * 根据页面输入字段查询出入库记录.
     * 
     * @param request
     *            请求上下文
     * @param stockTrx
     *            出入库Dto
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return 出入库List
     */
    List<StockTrx> queryStockTrxs(IRequest request, StockTrx stockTrx, int page, int pagesize);

    /**
     * 根据页面条件查询出入库报表
     * @param request
     * @param storage
     * @param page
     * @param pageSize
     * @return
     */
    List<Storage>queryStorage(IRequest request,Storage storage,int page,int pageSize);


    /**
     * 删除页面选中的出入库记录.
     * 
     * @param request
     *            请求上下文
     * @param stockTrx
     *            出入库Dto
     */
    void deleteStockTrx(IRequest request, StockTrx stockTrx);

    /**
     * 批量删除页面选中的出入库记录.
     * 
     * @param request
     *            请求上下文
     * @param stockTrxs
     *            出入库List
     */
    void batchDelete(IRequest request, List<StockTrx> stockTrxs);

    /**
     * 创建出入库事务头记录.
     * 
     * @param request
     *            请求上下文
     * @param stockTrx
     *            出入库事务Dto
     * @return StockTrxs 出入库Dto
     * @throws InventoryException
     *             库存统一异常
     */
    List<StockTrx> createStockTrx(IRequest request, @StdWho StockTrx stockTrx) throws InventoryException;

    /**
     * 删除出入库事务明细记录.
     * 
     * @param stockTrxDetail
     *            出入库事务明细行Dto
     */
    void deleteStockTrxDetail(StockTrxDetail stockTrxDetail);

    /**
     * 批量删除页面选中的出入库明细记录.
     * 
     * @param request
     *            请求上下文
     * @param stockTrxDetails
     *            出入库事务细行Dto
     */
    void batchDeleteDetails(IRequest request, List<StockTrxDetail> stockTrxDetails);

    /**
     * 删除整个出入库事务.
     * 
     * @param request
     *            请求上下文
     * @param stockTrx
     *            出入库事务Dto
     */
    void deleteAll(IRequest request, StockTrx stockTrx);

    /**
     * 提交出入库事务.
     * 
     * @param request
     *            请求上下文
     * @param stockTrx
     *            出入库事务Dto
     * @param invTransactions
     *            库存事务Dto
     * @param flag
     *            标志符
     * @return List 库存List
     * @throws InventoryException
     *             库存统一异常
     */
    List<StockTrx> submitTransaction(IRequest request, @StdWho StockTrx stockTrx,
            @StdWho List<InvTransaction> invTransactions, boolean flag) throws InventoryException;

    /**
     * 根据单据编号获取对应出入库事务.
     * 
     * @param request
     *            请求上下文
     * @param trxId
     *            出入库ID
     * @param organizationId
     * 			      当前库存组织ID
     * @return stockTrx 出入库Dto
     * @throws InventoryException
     *             库存统一异常
     */
    StockTrx getStockTrx(IRequest request, Long trxId, Long organizationId) throws InventoryException;

    /**
     * 根据单据编号获取对应出入库事务明细行.
     * 
     * @param request
     *            请求上下文
     * @param trxId
     *            出入库事务ID
     * @return List 出入库明细行List
     */
    List<StockTrxDetail> queryDetails(IRequest request, Long trxId, Long organizationId);

    /**
     * 根据单据编号获取对应出入库事务明细行.
     * 
     * @param request
     *            请求上下文
     * @param stockTrx
     *            出入库Dto
     */
    void updateStatus(IRequest request, @StdWho StockTrx stockTrx);

    /**
     * 根据trxId删除所有明细行.
     * 
     * @param request
     *            请求上下文
     * @param stockTrx
     *            出入库Dto
     */
    void deleteAllDetails(IRequest request, StockTrx stockTrx);
    /**
     * 根据退货单号得到退货单中的商品及其价格和数量信息.
     *
     * @param request
     *            请求上下文
     * @param outRefundNo
     *            退货单号
     */
    List<StockTrxDetail> getOutRefundItem(IRequest request, String outRefundNo);
}
