/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.lkkhpg.dsis.common.inventory.dto.*;
import com.lkkhpg.dsis.common.inventory.mapper.*;
import com.lkkhpg.dsis.integration.payment.dto.PayRefundRequest;
import com.lkkhpg.dsis.integration.payment.mapper.PayRefundRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.service.IInvTransactionService;
import com.lkkhpg.dsis.admin.inventory.service.IStockTrxService;
import com.lkkhpg.dsis.admin.product.product.service.IInvItemPropertyService;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.product.dto.Lot;
import com.lkkhpg.dsis.common.product.mapper.InvItemMapper;
import com.lkkhpg.dsis.common.product.mapper.LotMapper;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 出入库Service接口实现.
 *
 * @author mclin
 */
@Transactional
@Service
public class StockTrxServiceImpl implements IStockTrxService {

    private Logger logger = LoggerFactory.getLogger(StockTrxServiceImpl.class);

    @Autowired
    private StockTrxMapper stockTrxMapper;

    @Autowired
    private StockTrxDetailMapper stockTrxDetailMapper;

    @Autowired
    private LotMapper invLotMapper;

    @Autowired
    private InvOnhandQuantityMapper onHandQuaMapper;

    @Autowired
    private IInvTransactionService invTransactionService;

    @Autowired
    private IDocSequenceService docSequenceService;

    @Autowired
    private IInvItemPropertyService invItemPropertyService;

    @Autowired
    private InvItemMapper invItemMapper;

    @Autowired
    private PayRefundRequestMapper payRefundRequestMapper;

    @Autowired
    private InvTransactionMapper invTransactionMapper;
    @Autowired
    private StorageMapper storageMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<StockTrx> queryStockTrxs(IRequest request, StockTrx stockTrx, int page, int pagesize) {
        if (stockTrx == null || stockTrx.getOrganizationId() == null) {
            return null;
        }
        PageHelper.startPage(page, pagesize);
        return stockTrxMapper.queryStockTrxs(stockTrx, stockTrx.getSelStatus());
    }

    @Override
    public List<Storage> queryStorage(IRequest request, Storage storage, int page, int pageSize) {
        if (storage == null || storage.getOrganizationId() == null) {
            return null;
        }
        PageHelper.startPage(page, pageSize);
        return storageMapper.queryStorage(storage);
    }

    @Override
    public void deleteStockTrx(IRequest request, StockTrx stockTrx) {
        if (stockTrx == null || stockTrx.getTrxId() == null) {
            return;
        }
        stockTrxMapper.deleteStockTrx(stockTrx);
    }

    @Override
    public void batchDelete(IRequest request, List<StockTrx> stockTrxs) {
        if (stockTrxs == null || stockTrxs.isEmpty()) {
            return;
        }
        for (StockTrx stockTrx : stockTrxs) {
            self().deleteAllDetails(request, stockTrx);
            self().deleteStockTrx(request, stockTrx);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<StockTrx> createStockTrx(IRequest requestContext, @StdWho StockTrx stockTrx) throws InventoryException {

        stockTrx.setCreatedBy(requestContext.getAttribute("createdBy"));
        stockTrx.setCreationDate(requestContext.getAttribute("creationDate"));
        /*退货因为lov的原因 只能放交易编号  现根据交易编号找到对应的退货单号
        * updated by 15750 at 2018/02/09*/
        String transactionId=stockTrx.getTransactionId();
        if(transactionId !=null) {
            PayRefundRequest payRefundRequest = new PayRefundRequest();
            payRefundRequest.setTransactionId(transactionId);
            List<PayRefundRequest> payRefundRequests = payRefundRequestMapper.queryPayRefundLov(payRefundRequest);
            String outRefundNo =payRefundRequests.get(0).getOutRefundNo();
            stockTrx.setOutRefundNo(outRefundNo);
        }
        StockTrx trxTemp = stockTrxMapper.selectByPrimaryKey(stockTrx.getTrxId());
        if (trxTemp != null && InventoryConstants.STOCKIO_COMPLETE_STATUS.equals(trxTemp.getStatus())) {
            throw new InventoryException(InventoryException.MSG_ERROR_TRANSACTION_REPEAT, null);
        }

        /* 处理时间大于当前系统时间问题 */
        Date trxDate = stockTrx.getTrxDate();
        if (trxDate.getTime() > new Date().getTime()) {
            if (logger.isDebugEnabled()) {
                logger.debug("TrxDate is bigger than SystemDate: {}", new Object[] { trxDate });
            }
            throw new InventoryException(InventoryException.MSG_ERROR_NOT_ALLOWED_TRX_DATE, null);
        }

        // 插入头数据
        if (stockTrx.getTrxId() != null) {
            stockTrxMapper.updateByPrimaryKeySelective(stockTrx);
        } else {
            // 流水号段数设置
            DocSequence docSequence;
            // 库存组织
            String orgId = 0 + stockTrx.getOrganizationId().toString();
            String trxNumberStr;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());
            if (InventoryConstants.STOCKIO_IN_CODE.equals(stockTrx.getTrxType())) {
                // 入库
                docSequence = new DocSequence(InventoryConstants.STOCKIO_IN_CODE, orgId, date, null, null, null);
                trxNumberStr = docSequenceService.getSequence(requestContext, docSequence, date,
                        InventoryConstants.STOCKIO_SEQ_LENGTH, InventoryConstants.STOCKIO_START_NUMBER);
                trxNumberStr = InventoryConstants.STOCKIO_IN_CODE + trxNumberStr;
            } else if (InventoryConstants.STOCKIO_STADJ_TYPE_CODE.equals(stockTrx.getTrxType())) {
                // 盘盈
                docSequence = new DocSequence(InventoryConstants.STOCKIO_STKAD_SEQ_CODE, orgId, date, null, null, null);
                trxNumberStr = docSequenceService.getSequence(requestContext, docSequence, date,
                        InventoryConstants.STOCKIO_SEQ_LENGTH, InventoryConstants.STOCKIO_START_NUMBER);
                trxNumberStr = InventoryConstants.STOCKIO_STKAD_SEQ_CODE + trxNumberStr;
            } else if (InventoryConstants.PSTOCKI_IN_CODE.equals(stockTrx.getTrxType())) {
                // 采购入库
                docSequence = new DocSequence(InventoryConstants.STOCKIO_IN_CODE, orgId, date, null, null, null);
                trxNumberStr = docSequenceService.getSequence(requestContext, docSequence, date,
                        InventoryConstants.STOCKIO_SEQ_LENGTH, InventoryConstants.STOCKIO_START_NUMBER);
                trxNumberStr = InventoryConstants.STOCKIO_IN_CODE + trxNumberStr;
            } else if (InventoryConstants.RTURN_IN_CODE.equals(stockTrx.getTrxType())) {
                // 退货入库
                docSequence = new DocSequence(InventoryConstants.STOCKIO_IN_CODE, orgId, date, null, null, null);
                trxNumberStr = docSequenceService.getSequence(requestContext, docSequence, date,
                        InventoryConstants.STOCKIO_SEQ_LENGTH, InventoryConstants.STOCKIO_START_NUMBER);
                trxNumberStr = InventoryConstants.STOCKIO_IN_CODE + trxNumberStr;
            } else if (InventoryConstants.PSTOCKI_OT_CODE.equals(stockTrx.getTrxType())) {
                // 采购出库
                docSequence = new DocSequence(InventoryConstants.STOCKIO_OUT_CODE, orgId, date, null, null, null);
                trxNumberStr = docSequenceService.getSequence(requestContext, docSequence, date,
                        InventoryConstants.STOCKIO_SEQ_LENGTH, InventoryConstants.STOCKIO_START_NUMBER);
                trxNumberStr = InventoryConstants.STOCKIO_OUT_CODE + trxNumberStr;
            }else {
                // 出库
                docSequence = new DocSequence(InventoryConstants.STOCKIO_OUT_CODE, orgId, date, null, null, null);
                trxNumberStr = docSequenceService.getSequence(requestContext, docSequence, date,
                        InventoryConstants.STOCKIO_SEQ_LENGTH, InventoryConstants.STOCKIO_START_NUMBER);
                trxNumberStr = InventoryConstants.STOCKIO_OUT_CODE + trxNumberStr;
            }
            stockTrx.setTrxNumber(trxNumberStr);
            Long trxId = stockTrxMapper.getTrxId();
            stockTrx.setTrxId(trxId);
            stockTrxMapper.insertStockTrx(stockTrx);
        }
        // 判断如果行不为空，则迭代循环插入
        if (stockTrx.getStockTrxDetails() != null) {
            stockTrx = processStockTrxDetails(requestContext, stockTrx);
        }
        List<StockTrx> stockTrxs = new ArrayList<>();
        stockTrxs.add(0, stockTrx);
        return stockTrxs;
    }

    private List<InvOnhandQuantity> makeTotalQuas(List<StockTrxDetail> stockTrxDetails) {
        // 汇总结果list长度
        int length = 0;
        // 明细行行数
        int detailSize = stockTrxDetails.size();
        // 用于存储汇总后的结果
        List<InvOnhandQuantity> list = new ArrayList<>();
        InvOnhandQuantity oqTemp = new InvOnhandQuantity();
        // 获得第一个明细行
        StockTrxDetail stockTrxDetail = stockTrxDetails.get(0);
        oqTemp.setItemId(stockTrxDetail.getItemId());
        oqTemp.setLotNumber(stockTrxDetail.getLotNumber());
        oqTemp.setQuantity(stockTrxDetail.getQuantity());
        // 现在汇总结果list里面没有东西，第一个明细行就是汇总后的第一条信息了
        list.add(0, oqTemp);
        // list 长度+1
        length++;

        /* 汇总明细行的出库数量 */
        // 遍历明细行，从1开始，0已经处理过了
        for (int i = 1; i < detailSize; i++) {
            // 获取第i个明细行
            stockTrxDetail = stockTrxDetails.get(i);
            int j;
            // 遍历汇总结果的list
            for (j = 0; j < length; j++) {
                // 获取汇总结果的第j个，从0开始与明细行比较
                oqTemp = list.get(j);
                if (stockTrxDetail.getLotNumber() != null) {
                    // 商品ID和批次相同的作为同一类，汇总其数量
                    if (stockTrxDetail.getItemId().equals(oqTemp.getItemId())
                            && stockTrxDetail.getLotNumber().equals(oqTemp.getLotNumber())) {
                        // 数量相加
                        oqTemp.setQuantity(oqTemp.getQuantity().add(stockTrxDetail.getQuantity()));
                        // 把新的汇总结果更新到汇总结果list里面
                        list.set(j, oqTemp);
                        // 该汇总行已经得到汇总，跳出循环，进行下一条明细行的汇总
                        break;
                    }
                } else {
                    // 批次号为空只根据商品ID区分不同汇总
                    if (stockTrxDetail.getItemId().equals(oqTemp.getItemId()) && oqTemp.getLotNumber() == null) {
                        oqTemp.setQuantity(oqTemp.getQuantity().add(stockTrxDetail.getQuantity()));
                        // 该汇总行已经得到汇总，跳出循环，进行下一条明细行的汇总
                        break;
                    }
                }
            }
            // 汇总结果list里面没有找到该明细行同类别的记录，添加一条
            if (j >= length) {
                InvOnhandQuantity temp = new InvOnhandQuantity();
                temp.setItemId(stockTrxDetail.getItemId());
                temp.setLotNumber(stockTrxDetail.getLotNumber());
                temp.setQuantity(stockTrxDetail.getQuantity());
                list.add(temp);
                length++;
            }
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    private StockTrx processStockTrxDetails(IRequest requestContext, StockTrx stockTrx) throws InventoryException {
        int index = 0;
        Long organizationId = stockTrx.getOrganizationId();
        String operReason = stockTrx.getOperReasonCode();

        List<StockTrxDetail> details = stockTrx.getStockTrxDetails();
        // 获取明细行迭代器
        Iterator<StockTrxDetail> stockTrxDetailValueIter = stockTrx.getStockTrxDetails().iterator();
        StockTrxDetail stockTrxDetail;
        while (stockTrxDetailValueIter.hasNext()) {
            stockTrxDetail = stockTrxDetailValueIter.next();

            if (InventoryConstants.STOCKIO_IN_CODE.equals(stockTrx.getTrxType())
                    || InventoryConstants.STOCKIO_STADJ_TYPE_CODE.equals(stockTrx.getTrxType())) {
                // 入库或盘盈
                // 启用批次控制
                if (invItemPropertyService.isLotControl(requestContext, stockTrx.getOrganizationId(),
                        stockTrxDetail.getItemId())) {
                    if (StringUtils.isEmpty(stockTrxDetail.getLotNumber())) {
                        // 启用批次时批次号必输
                        throw new InventoryException(InventoryException.MSG_ERROR_LOT_REQUIRED, null);
                    } else {
                        if (stockTrxDetail.getExpiryDate() == null) {
                            throw new InventoryException(InventoryException.MSG_ERROR_EXPIRYDATE_REQUIRED, null);
                        }
                        // 判断批次是否可以插入
                        boolean flag = validLotWhenStkin(organizationId, stockTrxDetail.getLotNumber(),
                                stockTrxDetail.getItemId(), stockTrxDetail.getExpiryDate());
                        if (flag) {
                            // 批次不存在就插入对应批次
                            Lot lotTemp = new Lot();
                            lotTemp.setOrganizationId(organizationId);
                            lotTemp.setItemId(stockTrxDetail.getItemId());
                            lotTemp.setLotNumber(stockTrxDetail.getLotNumber());
                            lotTemp.setExpiryDate(stockTrxDetail.getExpiryDate());
                            lotTemp.setEnabledFlag(InventoryConstants.STOCKIO_ENABLE_FLAG);
                            invLotMapper.insertSelective(lotTemp);
                        }
                    }
                } else {
                    // 不启用批次，批次号必须为空
                    if (StringUtils.isNotEmpty(stockTrxDetail.getLotNumber())) {
                        throw new InventoryException(InventoryException.MSG_ERROR_LOT_MUST_NULL, null);
                    }
                    // 不启用批次，批次到期日也必须为空
                    if (stockTrxDetail.getExpiryDate() != null) {
                        throw new InventoryException(InventoryException.MSG_ERROR_EXPIRYDATE_MUST_NULL, null);
                    }
                }
            } else {
                // 出库
                // 启用批次控制
                if (invItemPropertyService.isLotControl(requestContext, stockTrx.getOrganizationId(),
                        stockTrxDetail.getItemId())) {
                    if (StringUtils.isEmpty(stockTrxDetail.getLotNumber())) {
                        // 启用批次时批次号必输
                        throw new InventoryException(InventoryException.MSG_ERROR_LOT_REQUIRED, null);
                    } else {
                        if (stockTrxDetail.getExpiryDate() == null) {
                            throw new InventoryException(InventoryException.MSG_ERROR_EXPIRYDATE_REQUIRED, null);
                        }
                        // 批次满足基本条件，判断批次是否存在库存现有量，以及与库存量表里面的批次是否对得上
                        // 无异常抛出证明满足出库条件
                        validLotWhenStkot(organizationId, stockTrxDetail.getLotNumber(), stockTrxDetail.getItemId(),
                                stockTrxDetail.getExpiryDate());
                    }
                } else {
                    // 不启用批次，批次号必须为空
                    if (StringUtils.isNotEmpty(stockTrxDetail.getLotNumber())) {
                        throw new InventoryException(InventoryException.MSG_ERROR_LOT_MUST_NULL, null);
                    }
                    // 不启用批次，批次到期日也必须为空
                    if (stockTrxDetail.getExpiryDate() != null) {
                        throw new InventoryException(InventoryException.MSG_ERROR_EXPIRYDATE_MUST_NULL, null);
                    }
                }
            }
            /* TODO: 字库ID暂时未涉及到，默认1(后边迭代修改) */
            stockTrxDetail.setSubinventoryId(1L);
            /* TODO: 货位ID暂时未涉及到，默认1(后边迭代修改) */
            stockTrxDetail.setLocatorId(1L);

            /* 操作明细行 */
            if (InventoryConstants.STOCKIO_SINGAL_TYPE.equals(stockTrxDetail.getPackingType())) {
                // 单件数量
                stockTrxDetail.setNumberOfCarton(BigDecimal.ZERO);
                stockTrxDetail.setNumberOfIndCarton(stockTrxDetail.getAmount());
            } else {
                // 出入库数量不能为0,出入库数量不能为0
                if (BigDecimal.ZERO.compareTo(stockTrxDetail.getAmount()) >= 0
                        || BigDecimal.ZERO.compareTo(stockTrxDetail.getQuantity()) == 0) {
                    throw new InventoryException(InventoryException.MSG_ERROR_AMOUNT_CAN_NOT_BE_ZERO, null);
                }
                // 判断箱子单位数量unitOfCarton，数量ammount和出入库数量是否对得上
                if (stockTrxDetail.getUnitOfCarton() != null) {
                    if ((stockTrxDetail.getUnitOfCarton().multiply(stockTrxDetail.getAmount()))
                            .compareTo(stockTrxDetail.getQuantity()) != 0) {
                        throw new InventoryException(InventoryException.MSG_ERROR_QUANTITY_NOT_EQUAL, null);
                    }
                }
                // 箱子数量
                stockTrxDetail.setNumberOfIndCarton(BigDecimal.ZERO);
                stockTrxDetail.setNumberOfCarton(stockTrxDetail.getAmount());
            }
            // 设置出库原因
            stockTrxDetail.setOperReasonCode(operReason);
            if (stockTrxDetail.getTrxDetailId() != null) {
                stockTrxDetailMapper.updateByPrimaryKeySelective(stockTrxDetail);
                index++;
            } else {
                // 设置头ID跟行ID一致
                stockTrxDetail.setTrxId(stockTrx.getTrxId());
                stockTrxDetail.setOrganizationId(stockTrx.getOrganizationId());
                stockTrxDetail.setOperType(stockTrx.getTrxType());
                stockTrxDetail.setOperReasonCode(stockTrx.getOperReasonCode());
                stockTrxDetailMapper.insertStockTrxDetail(stockTrxDetail);
                details.set(index, stockTrxDetail);
                index++;
            }
        }
        stockTrx.setStockTrxDetails(details);
        return stockTrx;
    }

    @Override
    public void deleteStockTrxDetail(StockTrxDetail stockTrxDetail) {
        if (stockTrxDetail == null || stockTrxDetail.getTrxDetailId() == null) {
            return;
        }
        stockTrxDetailMapper.deleteByPrimaryKey(stockTrxDetail);
    }

    @Override
    public void batchDeleteDetails(IRequest requestContext, List<StockTrxDetail> stockTrxDetails) {
        if (stockTrxDetails == null || stockTrxDetails.isEmpty()) {
            return;
        }
        for (StockTrxDetail stockTrxDetail : stockTrxDetails) {
            self().deleteStockTrxDetail(stockTrxDetail);
        }
    }

    @Override
    public void deleteAll(IRequest requestContext, StockTrx stockTrx) {
        List<StockTrxDetail> stockTrxDetails = stockTrx.getStockTrxDetails();
        // 删除行
        self().batchDeleteDetails(requestContext, stockTrxDetails);
        // 删除头
        stockTrxMapper.deleteByPrimaryKey(stockTrx);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<StockTrx> submitTransaction(IRequest request, @StdWho StockTrx stockTrx,
                                            @StdWho List<InvTransaction> invTransactions, boolean flag) throws InventoryException {
        stockTrx.setCreatedBy(request.getAttribute("createdBy"));
        for (InvTransaction invTransaction : invTransactions) {
            invTransaction.setCreatedBy(request.getAttribute("createdBy"));
        }
        //判断退货单号是否已入库
        //updated by 15750 at 2018/02/28
        String outRefundNo=stockTrx.getOutRefundNo();
        if(outRefundNo != null){
            int i=invTransactionMapper.queryOutRefund(outRefundNo);
            if(i>0){
                throw new InventoryException(InventoryException.MSG_ERROR_OUT_REFUND_NO, null);
            }
        }
        List<StockTrx> stockTrxs = new ArrayList<StockTrx>();
        if (flag) {
            stockTrxs = self().createStockTrx(request, stockTrx);
            String trxNumber = stockTrxs.get(0).getTrxNumber();
            List<StockTrxDetail> details = stockTrxs.get(0).getStockTrxDetails();
            int size = invTransactions.size();
            for (int i = 0; i < size; i++) {
                InvTransaction inv = invTransactions.get(i);
                inv.setTrxSourceReference(trxNumber);
                inv.setTrxSourceKey(details.get(i).getTrxDetailId().toString());
                invTransactions.set(i, inv);
            }
            invTransactionService.processTransaction(request, invTransactions);
            StockTrx temp = stockTrxs.get(0);
            temp.setStatus(InventoryConstants.STOCKIO_COMPLETE_STATUS);
            stockTrxs.add(temp);
            self().updateStatus(request, stockTrxs.get(0));

            //将这个退货单号记录已入库  下次将不可选到
            //updated by 15750 at 2018/02/28
            if(outRefundNo != null ){
                PayRefundRequest payRefundRequest = new PayRefundRequest();
                payRefundRequest.setOutRefundNo(outRefundNo);
                payRefundRequestMapper.update1(payRefundRequest);
            }
            return stockTrxs;
        } else {
            StockTrx trxTemp = stockTrxMapper.selectByPrimaryKey(stockTrx.getTrxId());
            if (trxTemp != null && InventoryConstants.STOCKIO_COMPLETE_STATUS.equals(trxTemp.getStatus())) {
                throw new InventoryException(InventoryException.MSG_ERROR_TRANSACTION_REPEAT, null);
            }
            // 重新校验明细行的商品批次信息
            // 获取明细行迭代器
            Iterator<StockTrxDetail> stockTrxDetailValueIter = stockTrx.getStockTrxDetails().iterator();
            while (stockTrxDetailValueIter.hasNext()) {
                StockTrxDetail stockTrxDetail = stockTrxDetailValueIter.next();
                if (InventoryConstants.STOCKIO_IN_CODE.equals(stockTrx.getTrxType())
                        || InventoryConstants.STOCKIO_STADJ_TYPE_CODE.equals(stockTrx.getTrxType())) {
                    // 入库或盘盈
                    // 启用批次控制
                    if (invItemPropertyService.isLotControl(request, stockTrx.getOrganizationId(),
                            stockTrxDetail.getItemId())) {
                        if (StringUtils.isEmpty(stockTrxDetail.getLotNumber())) {
                            // 启用批次时批次号必输
                            throw new InventoryException(InventoryException.MSG_ERROR_LOT_REQUIRED, null);
                        } else {
                            if (stockTrxDetail.getExpiryDate() == null) {
                                throw new InventoryException(InventoryException.MSG_ERROR_EXPIRYDATE_REQUIRED, null);
                            }
                            // 判断批次是否可以插入
                            boolean lotFlag = validLotWhenStkin(stockTrx.getOrganizationId(),
                                    stockTrxDetail.getLotNumber(), stockTrxDetail.getItemId(),
                                    stockTrxDetail.getExpiryDate());
                            if (lotFlag) {
                                // 批次不存在就插入对应批次
                                Lot lotTemp = new Lot();
                                lotTemp.setOrganizationId(stockTrx.getOrganizationId());
                                lotTemp.setItemId(stockTrxDetail.getItemId());
                                lotTemp.setLotNumber(stockTrxDetail.getLotNumber());
                                lotTemp.setExpiryDate(stockTrxDetail.getExpiryDate());
                                lotTemp.setEnabledFlag(InventoryConstants.STOCKIO_ENABLE_FLAG);
                                invLotMapper.insertSelective(lotTemp);
                            }
                        }
                    } else {
                        // 不启用批次，批次号必须为空
                        if (StringUtils.isNotEmpty(stockTrxDetail.getLotNumber())) {
                            throw new InventoryException(InventoryException.MSG_ERROR_LOT_MUST_NULL, null);
                        }
                        // 不启用批次，批次到期日也必须为空
                        if (stockTrxDetail.getExpiryDate() != null) {
                            throw new InventoryException(InventoryException.MSG_ERROR_EXPIRYDATE_MUST_NULL, null);
                        }
                    }
                } else {
                    // 出库
                    // 启用批次控制
                    if (invItemPropertyService.isLotControl(request, stockTrx.getOrganizationId(),
                            stockTrxDetail.getItemId())) {
                        if (StringUtils.isEmpty(stockTrxDetail.getLotNumber())) {
                            // 启用批次时批次号必输
                            throw new InventoryException(InventoryException.MSG_ERROR_LOT_REQUIRED, null);
                        } else {
                            if (stockTrxDetail.getExpiryDate() == null) {
                                throw new InventoryException(InventoryException.MSG_ERROR_EXPIRYDATE_REQUIRED, null);
                            }
                            // 批次满足基本条件，判断批次是否存在库存现有量，以及与库存量表里面的批次是否对得上
                            // 无异常抛出证明满足出库条件
                            validLotWhenStkot(stockTrx.getOrganizationId(), stockTrxDetail.getLotNumber(),
                                    stockTrxDetail.getItemId(), stockTrxDetail.getExpiryDate());
                        }
                    } else {
                        // 不启用批次，批次号必须为空
                        if (StringUtils.isNotEmpty(stockTrxDetail.getLotNumber())) {
                            throw new InventoryException(InventoryException.MSG_ERROR_LOT_MUST_NULL, null);
                        }
                        // 不启用批次，批次到期日也必须为空
                        if (stockTrxDetail.getExpiryDate() != null) {
                            throw new InventoryException(InventoryException.MSG_ERROR_EXPIRYDATE_MUST_NULL, null);
                        }
                    }
                }
            }
            invTransactionService.processTransaction(request, invTransactions);
            stockTrx.setStatus(InventoryConstants.STOCKIO_COMPLETE_STATUS);
            self().updateStatus(request, stockTrx);

            stockTrxs.add(stockTrx);
            //将这个退货单号记录已入库  下次将不可选到
            //updated by 15750 at 2018/02/28
            if(outRefundNo != null){
                PayRefundRequest payRefundRequest = new PayRefundRequest();
                payRefundRequest.setOutRefundNo(outRefundNo);
                payRefundRequestMapper.update1(payRefundRequest);
            }
            return stockTrxs;
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public StockTrx getStockTrx(IRequest request, Long trxId, Long organizationId) throws InventoryException {
        StockTrx stockTrx = stockTrxMapper.selectByPrimaryKey(trxId);
        if (stockTrx == null) {
            return null;
        }
        List<StockTrxDetail> details = self().queryDetails(request, trxId, organizationId);
        if (!details.isEmpty()) {
            stockTrx.setStockTrxDetails(details);
            stockTrx.setOperReasonCode(details.get(0).getOperReasonCode());
        } else {
            stockTrx.setOperReasonCode(InventoryConstants.STOCKIO_OPER_REASON);
        }
        return stockTrx;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<StockTrxDetail> queryDetails(IRequest request, Long trxId, Long organizationId) {
        List<StockTrxDetail> details = stockTrxDetailMapper.queryDetails(trxId, organizationId);
        int size = details.size();
        for (int i = 0; i < size; i++) {
            StockTrxDetail temp = details.get(i);
            String uomCode = invItemMapper.getItemUomCode(temp.getItemId()).getUomCode();
            if (uomCode.equals(temp.getPackingType())) {
                // 单件
                temp.setAmount(temp.getQuantity());
                temp.setUnitOfCarton(null);
            } else {
                // 非单件
                if (temp.getUnitOfCarton() == null || temp.getUnitOfCarton().compareTo(BigDecimal.ZERO) == 0) {
                    temp.setAmount(BigDecimal.ZERO);
                } else {
                    temp.setAmount(temp.getQuantity().divide(temp.getUnitOfCarton()));
                }
            }
        }
        return details;
    }

    @Override
    public void updateStatus(IRequest request, @StdWho StockTrx stockTrx) {
        stockTrx.setCreatedBy(request.getAttribute("createdBy"));
        stockTrxMapper.updateStatus(stockTrx);
    }

    @Override
    public void deleteAllDetails(IRequest request, StockTrx stockTrx) {
        stockTrxDetailMapper.deleteByTrxId(stockTrx);
    }

    @Override
    public List<StockTrxDetail> getOutRefundItem(IRequest request, String outRefundNo){
        List<StockTrxDetail> details = stockTrxDetailMapper.getOutRefundItem(outRefundNo);
        int size = details.size();
        for (int i = 0; i < size; i++) {
            StockTrxDetail temp = details.get(i);
            String uomCode = invItemMapper.getItemUomCode(temp.getItemId()).getUomCode();
            if (uomCode.equals(temp.getPackingType())) {
                // 单件
                temp.setAmount(temp.getQuantity());
                temp.setUnitOfCarton(null);
            } else {
                // 非单件
                if (temp.getUnitOfCarton() == null || temp.getUnitOfCarton().compareTo(BigDecimal.ZERO) == 0) {
                    temp.setAmount(BigDecimal.ZERO);
                } else {
                    temp.setAmount(temp.getQuantity().divide(temp.getUnitOfCarton()));
                }
            }
        }
        return details;
    }

    /**
     * 入库批次检验.
     *
     * @param orgId
     *            库存组织ID
     * @param lotNumber
     *            批次号
     * @param itemId
     *            商品ID
     * @param expiryDate
     *            批次到期日
     * @return boolean true可以插入批次, false不用插入批次
     * @throws InventoryException
     *             库存统一异常
     */
    public boolean validLotWhenStkin(Long orgId, String lotNumber, Long itemId, Date expiryDate)
            throws InventoryException {
        // 查询批次是否存在
        Lot lot = invLotMapper.getLotWhenStkin(orgId, lotNumber, itemId);
        if (lot == null) {
            // 不存在批次判断是否能够添加
            return true;
        } else {
            // 已存在批次，判断批次是否有效
            if (true != "Y".equals(lot.getEnabledFlag())) {
                throw new InventoryException(InventoryException.MSG_ERROR_LOT_NUMBER_NOT_ENABLED,
                        new Object[] { lotNumber });
            }
            // 已存在批次，判断批次到期日是否相等
            if (expiryDate.compareTo(lot.getExpiryDate()) != 0) {
                throw new InventoryException(InventoryException.MSG_ERROR_HAVE_LOT_WRONG_EXPIRYDATE,
                        new Object[] { lotNumber });
            }
        }
        return false;
    }

    /**
     * 出库批次检验.
     *
     * @param orgId
     *            库存组织ID
     * @param lotNumber
     *            批次号
     * @param expiryDate
     *            批次到期日
     * @param itemId
     *            商品ID
     * @throws InventoryException
     *             库存统一异常
     */
    public void validLotWhenStkot(Long orgId, String lotNumber, Long itemId, Date expiryDate)
            throws InventoryException {
        // 查询批次是否存在
        Lot lot = invLotMapper.getLotWhenStkot(orgId, lotNumber, itemId);
        if (lot == null) {
            // 现有量表里面没有此批次
            throw new InventoryException(InventoryException.MSG_ERROR_STOCKIO_ONHAND_WRONG, null);
        } else {
            // 已存在批次，判断批次到期日是否相等
            if (expiryDate.compareTo(lot.getExpiryDate()) != 0) {
                throw new InventoryException(InventoryException.MSG_ERROR_HAVE_LOT_WRONG_EXPIRYDATE,
                        new Object[] { lotNumber });
            }
        }
    }

}