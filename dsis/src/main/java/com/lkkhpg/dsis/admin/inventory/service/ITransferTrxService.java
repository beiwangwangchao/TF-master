/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.service;

import java.util.List;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrx;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrxDetail;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrxDetailQuery;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrxQuery;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 移库事务service.
 * 
 * @author zhangchaungsheng
 */
public interface ITransferTrxService extends ProxySelf<ITransferTrxService> {
    /**
     * 查询转入转出单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrxQuery
     *            转入转出查询dto
     * @param page
     *            页
     * @param pagesize
     *            页的大小
     * @return 转入转出单list
     */
    List<TransferTrx> selectTransferTrxs(IRequest request, TransferTrxQuery transferTrxQuery, int page, int pagesize);

    /**
     * 查询转入转出单行.
     * 
     * @param request
     *            请求上下文
     * @param transferTrxDetail
     *            转入转出单行
     * @return 转入转出单行list
     */
    List<TransferTrxDetail> selectTransferTrxDetails(IRequest request, TransferTrxDetail transferTrxDetail);

    /**
     * 新建转入转出单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrx
     *            转入转出单
     * @return 转入转出单
     */
    TransferTrx createTransferTrx(IRequest request, @StdWho TransferTrx transferTrx);

    /**
     * 更新转入转出单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrx
     *            转入转出单
     * @return 转入转出单
     */
    TransferTrx updateTransferTrx(IRequest request, @StdWho TransferTrx transferTrx);

    /**
     * 根据转出单创建转入单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrxOut
     *            转出单
     * @return 转入单
     * @throws InventoryException
     *             异常
     */
    TransferTrx createTransferInTrx(IRequest request, TransferTrx transferTrxOut) throws InventoryException;

    /**
     * 更新转入转出单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrx
     *            转入转出单
     * @return 转入转出单
     * @throws InventoryException
     *             异常
     */
    TransferTrx batchUpdate(IRequest request, @StdWho TransferTrx transferTrx) throws InventoryException;

    /**
     * 提交传出单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrxs
     *            转出单
     * @return 转出单
     * @throws InventoryException
     *             库存统一异常
     */
    TransferTrx saveTransferOutTrx(IRequest request, @StdWho TransferTrx transferTrxs) throws InventoryException;

    /**
     * 提交转出单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrxs
     *            转出单
     * @return 转出单
     * @throws InventoryException
     *             库存统一异常 异常
     */

    TransferTrx submitTransferOutTrx(IRequest request, @StdWho TransferTrx transferTrxs) throws InventoryException;

    /**
     * 保存转入单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrx
     *            转入单
     * @return 转入单
     * @throws InventoryException
     *             库存统一异常
     */
    TransferTrx saveTransferInTrx(IRequest request, @StdWho TransferTrx transferTrx) throws InventoryException;

    /**
     * 提交转入单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrx
     *            转入单
     * @return 转入单
     * @throws InventoryException
     *             库存统一异常 异常
     */
    TransferTrx submitTransferInTrx(IRequest request, @StdWho TransferTrx transferTrx) throws InventoryException;

    /**
     * 释放转入单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrxDetails
     *            转入单行
     * @return 转入单
     * @throws InventoryException
     *             异常
     */
    TransferTrx releaseTransferInTrx(IRequest request, @StdWho List<TransferTrxDetail> transferTrxDetails)
            throws InventoryException;

    /**
     * 自动生成流水号.
     * 
     * @param request
     *            请求上下文
     * @param transferType
     *            移库单据类型
     * @return 单据流水号
     */
    String generatedTrxNum(IRequest request, String transferType);

    /**
     * 批量删除转入转单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrxs
     *            转入转出单集合
     * @return 删除结果
     */
    boolean batchDelete(IRequest request, @StdWho List<TransferTrx> transferTrxs);

    /**
     * 批量删除转入转单行.
     * 
     * @param request
     *            请求上下文
     * @param transferTrxDetails
     *            转入转出单行
     * @return 删除结果
     */
    boolean batchDeleteDetail(IRequest request, @StdWho List<TransferTrxDetail> transferTrxDetails);

    /**
     * 
     * @param request
     *            请求上下文
     * @param transferTrxQuery
     *            转入转出单查询dto
     * @param page
     *            页数
     * @param pagesize
     *            页的大小
     * @return TransferTrxQuery list
     */
    List<TransferTrxQuery> selectInTransferTrxs(IRequest request, TransferTrxQuery transferTrxQuery, int page,
            int pagesize);

    /**
     * 查询转入转出单行明细.
     * 
     * @param request
     *            请求上下文
     * @param transferTrxDetailQuery
     *            查询转入转出单行明细dto
     * @return 转入转出明细list
     */
    List<TransferTrxDetailQuery> selectInTransferTrxDetails(IRequest request,
            TransferTrxDetailQuery transferTrxDetailQuery);

    /**
     * 验证提交或者保存的转出单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrx
     *            转出单
     * @return 是否通过验证
     * @throws InventoryException
     *             库存统一异常 异常
     */
    boolean validateTransferOutTrx(IRequest request, TransferTrx transferTrx) throws InventoryException;

    /**
     * 验证提交或者保存的转入单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrx
     *            转入单
     * @return 是否通过验证
     * @throws InventoryException
     *             库存统一异常 异常
     */
    boolean validateTransferInTrx(IRequest request, TransferTrx transferTrx) throws InventoryException;

    /**
     * 查询转入单上可新增的转入单行.
     * 
     * @param request
     *            请求上下文
     * @param outTrxId
     *            转出单ID
     * @param inTrxId
     *            转入单ID
     * @param outTrxdDetailIds
     *            转出行ID
     * @param itemNumber
     *            商品编码
     * @return 转入行。
     */
    List<TransferTrxDetailQuery> addTransferInTrxDetail(IRequest request, Long outTrxId, Long inTrxId,
            List<String> outTrxdDetailIds, String itemNumber);

    /**
     * 删除转出单行.
     * 
     * @param request
     *            请求上下文.
     * @param transferTrxDetails
     *            转出单行
     */
    void removeTransferInDetailTrx(IRequest request, List<TransferTrxDetail> transferTrxDetails);

    /**
     * 获取转入行的剩余转入数量.
     * 
     * @param request
     *            上下文
     * @param trxId
     *            转入单号
     * @param sourceTrxId
     *            源转出单号
     * @param itemId
     *            商品ID
     * @param lotNumber
     *            批次号
     * @param packingType
     *            包装类型
     * @return TransferTrxDetail 转出单行
     */
    TransferTrxDetail getRemainingIndAftCar(IRequest request, Long trxId, Long sourceTrxId, Long itemId,
            String lotNumber, String packingType);

    /**
     * 提交转出单前校验.
     * 
     * @param request
     *            请求上下文
     * @param checks
     *            转出单+批次s
     * @return 校验结果(到期日出错的批次)
     */
    List<TransferTrxDetail> transferOutTrxCheck(IRequest request, TransferTrx checks);
    
    /**
     * 提交转入单前校验.
     * 
     * @param request
     *            请求上下文
     * @param checks
     *            转入单+批次s
     * @return 校验结果(到期日出错的批次)
     */
    List<TransferTrxDetail> transferInTrxCheck(IRequest request, TransferTrx checks);
}
