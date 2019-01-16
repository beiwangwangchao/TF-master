/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.lot.service.InvLotService;
import com.lkkhpg.dsis.admin.inventory.service.IInvTransactionService;
import com.lkkhpg.dsis.admin.inventory.service.ITransferTrxService;
import com.lkkhpg.dsis.admin.product.product.service.IInvItemPropertyService;
import com.lkkhpg.dsis.common.config.dto.OrgSelection;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.inventory.dto.InvLot;
import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrx;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrxDetail;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrxDetailQuery;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrxQuery;
import com.lkkhpg.dsis.common.inventory.mapper.InvOnhandQuantityMapper;
import com.lkkhpg.dsis.common.inventory.mapper.TransferTrxDetailMapper;
import com.lkkhpg.dsis.common.inventory.mapper.TransferTrxMapper;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.dto.InvUnitOfMeasureB;
import com.lkkhpg.dsis.common.product.mapper.InvItemMapper;
import com.lkkhpg.dsis.common.service.ICommInvUnitService;
import com.lkkhpg.dsis.common.service.IInvOnhandQuantityService;
import com.lkkhpg.dsis.common.service.ISpmInvOrganizationService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 移库事务Service.
 * 
 * @author chuangsheng.zhang.
 */

@Transactional
@Service
public class TransferTrxServiceImpl implements ITransferTrxService {

    @Autowired
    private IInvTransactionService invTransactionService;

    @Autowired
    private TransferTrxMapper transferTrxMapper;

    @Autowired
    private TransferTrxDetailMapper transferTrxDetailMapper;

    @Autowired
    private ISpmInvOrganizationService spmInvOrganizationService;

    @Autowired
    private IInvItemPropertyService invItemPropertyService;

    @Autowired
    private IInvOnhandQuantityService invOnhandQuantityService;

    @Autowired
    private InvItemMapper invItemMapper;

    @Autowired
    private IDocSequenceService docSequenceService;

    @Autowired
    private InvLotService invLotService;

    @Autowired
    private ICommInvUnitService commInvUnitService;

    @Autowired
    private InvOnhandQuantityMapper invOnhandQuantityMapper;
    
    @Autowired
    private MessageSource messageSource;

    /**
     * 移库明细插入更新或者插入过程.
     * 
     * @param request
     *            请求上下文
     * @param transferTrx
     *            转入转出单
     */
    private void processTransferTrxDetails(IRequest request, TransferTrx transferTrx) {
        Iterator<TransferTrxDetail> transferTrxDetailIter = transferTrx.getTransferTrxDetails().iterator(); // 获取快码值迭代器
        TransferTrxDetail transferTrxDetail;
        while (transferTrxDetailIter.hasNext()) {
            transferTrxDetail = transferTrxDetailIter.next();
            if (transferTrxDetail.getTrxDetailId() == null) {
                transferTrxDetail.setTrxId(transferTrx.getTrxId()); // 设置头ID跟行ID一致
                transferTrxDetailMapper.insert(transferTrxDetail);

            } else if (transferTrxDetail.getTrxDetailId() != null) {
                transferTrxDetailMapper.updateByPrimaryKeySelective(transferTrxDetail);
            }
        }
    }

    @Override
    public TransferTrx createTransferTrx(IRequest request, TransferTrx transferTrx) {
        transferTrxMapper.insert(transferTrx);
        if (transferTrx.getTransferTrxDetails() != null) {
            processTransferTrxDetails(request, transferTrx);
        }
        return transferTrx;
    }

    /**
     * 根据转出单生成转入单.
     * 
     * @param request
     *            请求上下文
     * @param transferTrxOut
     *            转出单
     * @return 转入单
     * @throws InventoryException
     *             异常
     */
    public TransferTrx createTransferInTrx(IRequest request, TransferTrx transferTrxOut) throws InventoryException {
        // 查看该转出单下面是否有一个状态为 转入待定的转入单
        TransferTrxQuery trxQuery = new TransferTrxQuery();
        trxQuery.setSourceTrxId(transferTrxOut.getTrxId().toString());
        trxQuery.setStatus(InventoryConstants.IN_PENDING_FOR_TI);
        List<TransferTrx> transferInTrxs = queryTransferTrxAndDetail(request, trxQuery);

        TransferTrx transferTrxIn;
        if (transferInTrxs != null && transferInTrxs.size() > 0) {
            transferTrxIn = transferInTrxs.get(0);
        } else {
            transferTrxIn = new TransferTrx();
            transferTrxIn.setTrxId(null);
            transferTrxIn.setStatus(InventoryConstants.IN_PENDING_FOR_TI);
            transferTrxIn.setTransferType(InventoryConstants.TRANSFER_TYPE_IN);
            transferTrxIn.setTrxNumber(generatedTrxNum(request, InventoryConstants.TRANSFER_TYPE_IN));
            transferTrxIn.setSourceTrxId(transferTrxOut.getTrxId().toString());
            transferTrxIn.setTrxDate(new Date());
            transferTrxIn.setOrganizationId(transferTrxOut.getOrganizationId());
            transferTrxIn.setTransferOrgId(transferTrxOut.getTransferOrgId());
            transferTrxIn.setCreatedBy(transferTrxOut.getCreatedBy());
            transferTrxIn.setLastUpdatedBy(transferTrxOut.getLastUpdatedBy());
            transferTrxIn.setCreationDate(new Date());
            transferTrxIn.setLastUpdateDate(new Date());
        }

        ArrayList<TransferTrxDetail> trxDetailIns = new ArrayList<TransferTrxDetail>();
        if (transferTrxIn.getTransferTrxDetails() != null && transferTrxIn.getTransferTrxDetails().size() > 0) {
            trxDetailIns.addAll(transferTrxIn.getTransferTrxDetails());
        }
        for (TransferTrxDetail transferTrxDetailOut : transferTrxOut.getTransferTrxDetails()) {

            BigDecimal remainingNumber = transferTrxDetailOut.getRemainingIndAftCar();
            if (remainingNumber != null && remainingNumber.longValue() > 0) {
                boolean isExist = false;
                for (TransferTrxDetail trxDetail : trxDetailIns) {
                    if (trxDetail.getSourceLineId().equals(transferTrxDetailOut.getTrxDetailId())) {
                        trxDetail.setTrxQty(trxDetail.getTrxQty().add(remainingNumber));
                        isExist = true;
                        break;
                    }
                }
                if (isExist) {
                    continue;
                }

                TransferTrxDetail TransferTrxDetailIn = new TransferTrxDetail();
                TransferTrxDetailIn.setOrganizationId(transferTrxOut.getTransferOrgId());
                TransferTrxDetailIn.setItemId(transferTrxDetailOut.getItemId());
                TransferTrxDetailIn.setLotNumber(transferTrxDetailOut.getLotNumber());
                TransferTrxDetailIn.setExpiryDate(transferTrxDetailOut.getExpiryDate());
                TransferTrxDetailIn.setPackingType(transferTrxDetailOut.getPackingType());
                TransferTrxDetailIn.setNumberOfCarton(transferTrxDetailOut.getNumberOfCarton());
                TransferTrxDetailIn.setNumberOfIndCarton(transferTrxDetailOut.getNumberOfIndCarton());
                TransferTrxDetailIn.setSourceLineId(transferTrxDetailOut.getTrxDetailId());
                TransferTrxDetailIn.setTrxQty(remainingNumber);
                TransferTrxDetailIn.setSubinventoryId(transferTrxDetailOut.getSubinventoryId());
                TransferTrxDetailIn.setLocatorId(transferTrxDetailOut.getLocatorId());
                TransferTrxDetailIn.setStatus(InventoryConstants.ACTIVE);
                TransferTrxDetailIn.setCreatedBy(transferTrxDetailOut.getCreatedBy());
                TransferTrxDetailIn.setLastUpdatedBy(transferTrxDetailOut.getLastUpdatedBy());
                TransferTrxDetailIn.setCreationDate(new Date());
                TransferTrxDetailIn.setLastUpdateDate(new Date());

                trxDetailIns.add(TransferTrxDetailIn);
            }

        }
        transferTrxIn.setTransferTrxDetails(trxDetailIns);
        self().batchUpdate(request, transferTrxIn);

        return transferTrxIn;

    }

    @Override
    public TransferTrx updateTransferTrx(IRequest request, TransferTrx transferTrx) {
        transferTrxMapper.updateByPrimaryKeySelective(transferTrx);
        if (transferTrx.getTransferTrxDetails() != null) {
            processTransferTrxDetails(request, transferTrx);
        }
        return transferTrx;
    }

    @Override
    public TransferTrx batchUpdate(IRequest request, TransferTrx transferTrx) throws InventoryException {
        if (transferTrx.getTrxId() == null) {
            self().createTransferTrx(request, transferTrx);
        } else {
            self().updateTransferTrx(request, transferTrx);
        }
        // 更新剩余转出数量
        TransferTrxQuery transferTrxQuery = new TransferTrxQuery();
        transferTrxQuery.setTrxNumber(transferTrx.getTrxNumber());
        List<TransferTrx> transferTrxs = queryTransferTrxAndDetail(request, transferTrxQuery);
        if (transferTrxs != null && transferTrxs.size() > 0) {
            transferTrx = transferTrxs.get(0);
            if (InventoryConstants.TRANSFER_TYPE_OUT.equals(transferTrx.getTransferType())) {
                updateTrxRemainingNumber(request, transferTrx);
            } else {
                transferTrxQuery.setTrxNumber(null);

                transferTrxQuery.setTrxId(Long.valueOf(transferTrx.getSourceTrxId()));
                List<TransferTrx> trxs = queryTransferTrxAndDetail(request, transferTrxQuery);

                if (trxs != null && trxs.size() > 0) {

                    updateTrxRemainingNumber(request, trxs.get(0));
                }
            }
        }
        return transferTrx;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TransferTrx saveTransferOutTrx(IRequest request, TransferTrx transferTrx) throws InventoryException {

        // 验证数据是否合法
        self().validateTransferOutTrx(request, transferTrx);
        if (transferTrx.getTrxId() == null) {
            transferTrx.setTransferType(InventoryConstants.TRANSFER_TYPE_OUT);
            transferTrx.setStatus(InventoryConstants.OUT_NEW);
            String trxNumber = self().generatedTrxNum(request, InventoryConstants.TRANSFER_TYPE_OUT);
            transferTrx.setTrxNumber(trxNumber);
        }
        // 订单行的必输值 先设置成默认值
        if (transferTrx.getTransferTrxDetails() != null) {
            for (TransferTrxDetail transferTrxDetail : transferTrx.getTransferTrxDetails()) {

                transferTrxDetail.setStatus(InventoryConstants.ACTIVE);
                transferTrxDetail.setOrganizationId(transferTrx.getOrganizationId());
                // TODO: 货位 暂时先设默认值 -1
                transferTrxDetail.setLocatorId(1L);
                // TODO: 子库 暂时先设默认值 -1
                transferTrxDetail.setSubinventoryId(1L);
            }
        }

        self().batchUpdate(request, transferTrx);

        return transferTrx;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TransferTrx submitTransferOutTrx(IRequest request, TransferTrx transferTrx) throws InventoryException {

        // 验证数据是否合法
        self().validateTransferOutTrx(request, transferTrx);
        // 更新转出单
        transferTrx.setTransferType(InventoryConstants.TRANSFER_TYPE_OUT);
        transferTrx.setStatus(InventoryConstants.OUT_COMPLETED);
        if (transferTrx.getTrxId() == null) {
            String trxNumber = self().generatedTrxNum(request, InventoryConstants.TRANSFER_TYPE_OUT);
            transferTrx.setTrxNumber(trxNumber);
        }

        if (transferTrx.getTransferTrxDetails() != null) {
            // 订单行的必输值 先设置成默认值
            for (TransferTrxDetail transferTrxDetail : transferTrx.getTransferTrxDetails()) {
                transferTrxDetail.setOrganizationId(transferTrx.getOrganizationId());
                transferTrxDetail.setStatus(InventoryConstants.ACTIVE);
                // TODO: 货位 暂时先设默认值 -1
                transferTrxDetail.setLocatorId(1L);
                // TODO: 子库 暂时先设默认值 -1
                transferTrxDetail.setSubinventoryId(1L);
            }
        }
        transferTrx = self().batchUpdate(request, transferTrx);
        // 创建转入单
        self().createTransferInTrx(request, transferTrx);

        // 校验批次到期日是否一致
        List<TransferTrxDetail> wrongLots = self().transferOutTrxCheck(request, transferTrx);
        if (wrongLots != null && wrongLots.size() > 0) {// 有不一致的批次,报错
            String display = messageSource.getMessage(InventoryException.MSG_ERROR_TRANSFER_SUBMIT_ERROR_EXPIREDATE_DIFF, null, LocaleUtils.toLocale(request.getLocale()));
            StringBuffer msg = new StringBuffer(display);
            DateFormat format = new SimpleDateFormat(SystemProfileConstants.DATE_FORMAT);
            for (TransferTrxDetail ttd : wrongLots) {
                msg.append(InventoryException.SUBMIT_ERROR_EXPIREDATE_DIFF_1);
                msg.append(ttd.getItemNumber());
                msg.append(InventoryException.SUBMIT_ERROR_EXPIREDATE_DIFF_2);
                msg.append(ttd.getLotNumber());
                msg.append(InventoryException.SUBMIT_ERROR_EXPIREDATE_DIFF_2);
                msg.append(format.format(ttd.getExpiryDate()));
                msg.append(InventoryException.SUBMIT_ERROR_EXPIREDATE_DIFF_3);
            } // 以上处理得到详细报错信息
            throw new InventoryException(msg.toString(), new Object[] {});
        }

        // 提交库存事务
        List<InvTransaction> invTransactions = new ArrayList<InvTransaction>();
        if (transferTrx.getTransferTrxDetails() != null) {
            for (TransferTrxDetail trxDetail : transferTrx.getTransferTrxDetails()) {
                InvTransaction invTransaction = new InvTransaction();
                invTransaction.setOrganizationId(trxDetail.getOrganizationId());
                invTransaction.setSubinventoryId(trxDetail.getSubinventoryId());
                // 拼接头上和行上的备注
                StringBuilder sb = new StringBuilder();
                if (!StringUtils.isEmpty(transferTrx.getRemark())) {
                    sb.append(transferTrx.getRemark()).append(";");
                }
                if (!StringUtils.isEmpty(trxDetail.getRemark())) {
                    sb.append(trxDetail.getRemark());
                }
                invTransaction.setRemark(sb.toString());
                invTransaction.setLocatorId(trxDetail.getLocatorId());
                invTransaction.setItemId(trxDetail.getItemId());
                invTransaction.setLotNumber(trxDetail.getLotNumber());
                invTransaction.setTrxDate(transferTrx.getTrxDate());
                invTransaction.setTrxQty(trxDetail.getTrxQty().negate());
                invTransaction.setTrxType(InventoryConstants.TRANSFER_TYPE_OUT);
                invTransaction.setTrxSourceType(InventoryConstants.TRANSFER_TRX_DETAIL);
                invTransaction.setTrxSourceKey(String.valueOf(trxDetail.getTrxDetailId()));
                invTransaction.setExpiryDate(trxDetail.getExpiryDate());
                invTransaction.setTrxSourceReference(transferTrx.getTrxNumber());
                invTransactions.add(invTransaction);

            }
        }

        invTransactionService.processTransaction(request, invTransactions);

        return transferTrx;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TransferTrx saveTransferInTrx(IRequest request, TransferTrx transferTrx) throws InventoryException {
        // 验证数据是否合法
        self().validateTransferInTrx(request, transferTrx);
        return self().batchUpdate(request, transferTrx);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TransferTrx submitTransferInTrx(IRequest request, TransferTrx transferTrx) throws InventoryException {

        // 验证数据是否合法
        self().validateTransferInTrx(request, transferTrx);
        // 将转入单状态变成已完成
        transferTrx.setStatus(InventoryConstants.IN_COMPLETE);
        transferTrx = self().batchUpdate(request, transferTrx);
        TransferTrxQuery query = new TransferTrxQuery();
        query.setTrxId(Long.valueOf(transferTrx.getSourceTrxId()));
        List<TransferTrx> transferOutTrxs = queryTransferTrxAndDetail(request, query);
        // 将判断 转入的总数量 和传出的总数量是否相同
        TransferTrx transferOutTrx = transferOutTrxs.get(0);
        boolean isComplete = isALLComplete(request, transferOutTrx.getTrxId());
        // 如果转出单和转入单上的数量不一致，新生成一个转入单。
        if (!isComplete) {
            self().createTransferInTrx(request, transferOutTrx);
        }

        // 校验批次到期日是否一致
        List<TransferTrxDetail> wrongLots = self().transferInTrxCheck(request, transferTrx);
        if (wrongLots != null && wrongLots.size() > 0) {// 有不一致的批次,报错
            String display = messageSource.getMessage(InventoryException.MSG_ERROR_TRANSFER_SUBMIT_ERROR_EXPIREDATE_DIFF, null, LocaleUtils.toLocale(request.getLocale()));
            StringBuffer msg = new StringBuffer(display);
            DateFormat format = new SimpleDateFormat(SystemProfileConstants.DATE_FORMAT);
            for (TransferTrxDetail ttd : wrongLots) {
                msg.append(InventoryException.SUBMIT_ERROR_EXPIREDATE_DIFF_1);
                msg.append(ttd.getItemNumber());
                msg.append(InventoryException.SUBMIT_ERROR_EXPIREDATE_DIFF_2);
                msg.append(ttd.getLotNumber());
                msg.append(InventoryException.SUBMIT_ERROR_EXPIREDATE_DIFF_2);
                msg.append(format.format(ttd.getExpiryDate()));
                msg.append(InventoryException.SUBMIT_ERROR_EXPIREDATE_DIFF_3);
            } // 以上处理得到详细报错信息
            throw new InventoryException(msg.toString(), new Object[] {});
        }

        // 提交库存事务
        List<InvTransaction> invTransactions = new ArrayList<InvTransaction>();
        if (transferTrx.getTransferTrxDetails() != null) {
            for (TransferTrxDetail trxDetail : transferTrx.getTransferTrxDetails()) {
                if (InventoryConstants.ACTIVE.equals(trxDetail.getStatus())) {
                    InvTransaction invTransaction = new InvTransaction();
                    invTransaction.setOrganizationId(trxDetail.getOrganizationId());
                    invTransaction.setSubinventoryId(trxDetail.getSubinventoryId());
                    invTransaction.setLocatorId(trxDetail.getLocatorId());
                    invTransaction.setItemId(trxDetail.getItemId());
                    // 查询转入组织是是否维护该商品的批次.
                    if (trxDetail.getLotNumber() != null) {
                        InvLot invLot = new InvLot();
                        invLot.setItemId(trxDetail.getItemId());
                        invLot.setOrganizationId(trxDetail.getOrganizationId());
                        invLot.setLotNumber(trxDetail.getLotNumber());
                        boolean isExist = invLotService.isExistInvLot(request, invLot);

                        if (!isExist) {

                            invLot.setExpiryDate(trxDetail.getExpiryDate());
                            invLot.setEnabledFlag(InventoryConstants.YES);
                            invLotService.insertInvLot(request, invLot);
                        }
                    }
                    // 拼接头上和行上的备注
                    StringBuffer sb = new StringBuffer();
                    if (!StringUtils.isEmpty(transferTrx.getRemark())) {
                        sb.append(transferTrx.getRemark()).append(";");
                    }
                    if (!StringUtils.isEmpty(trxDetail.getRemark())) {
                        sb.append(trxDetail.getRemark());
                    }
                    invTransaction.setRemark(sb.toString());
                    invTransaction.setTransferOrgId(transferTrx.getOrganizationId());
                    invTransaction.setLotNumber(trxDetail.getLotNumber());
                    invTransaction.setTrxDate(transferTrx.getTrxDate());
                    invTransaction.setTrxQty(trxDetail.getTrxQty());
                    invTransaction.setTrxType(InventoryConstants.TRANSFER_TYPE_IN);
                    invTransaction.setTrxSourceType(InventoryConstants.TRANSFER_TRX_DETAIL);
                    invTransaction.setTrxSourceKey(String.valueOf(trxDetail.getTrxDetailId()));
                    invTransaction.setExpiryDate(trxDetail.getExpiryDate());
                    invTransaction.setTrxSourceReference(transferTrx.getTrxNumber());
                    invTransactions.add(invTransaction);
                }
            }
        }

        invTransactionService.processTransaction(request, invTransactions);

        return transferTrx;
    }

    public TransferTrx releaseTransferInTrx(IRequest request, List<TransferTrxDetail> transferTrxDetails)
            throws InventoryException {

        if (transferTrxDetails == null || transferTrxDetails.size() == 0) {
            return null;
        }

        for (TransferTrxDetail transferInTrxDetail : transferTrxDetails) {
            // 增加转出单的数量

            TransferTrxDetail transferOutTrxDetail = transferTrxDetailMapper
                    .selectByPrimaryKey(transferInTrxDetail.getSourceLineId());
            transferOutTrxDetail.setRemainingIndAftCar(
                    transferOutTrxDetail.getRemainingIndAftCar().add(transferInTrxDetail.getTrxQty()));
            transferTrxDetailMapper.updateByPrimaryKeySelective(transferOutTrxDetail);
            // 将要释放的行 trxQty 变成0
            transferInTrxDetail.setTrxQty(new BigDecimal(0));
            transferTrxDetailMapper.updateByPrimaryKeySelective(transferInTrxDetail);
        }
        TransferTrx transferInTrx = transferTrxMapper.selectByPrimaryKey(transferTrxDetails.get(0).getTrxId());
        TransferTrxQuery trxQuery = new TransferTrxQuery();
        trxQuery.setTrxId(Long.valueOf(transferInTrx.getSourceTrxId()));
        List<TransferTrx> transferOutTrxs = queryTransferTrxAndDetail(request, trxQuery);

        return self().createTransferInTrx(request, transferOutTrxs.get(0));
    }

    @Override
    public String generatedTrxNum(IRequest request, String transferType) {
        // TODO Auto-generated method stub

        DocSequence docSequence = new DocSequence();

        String sequence = null;

        switch (transferType) {
        case InventoryConstants.TRANSFER_TYPE_OUT:
            docSequence.setDocType(InventoryConstants.TRANSFER_TYPE_OUT);
            sequence = docSequenceService.getSequence(request, docSequence, InventoryConstants.TO,
                    InventoryConstants.SEQ_LENGTH, InventoryConstants.INIT_VALUE);
            break;
        case InventoryConstants.TRANSFER_TYPE_IN:
            docSequence.setDocType(InventoryConstants.TRANSFER_TYPE_IN);
            sequence = docSequenceService.getSequence(request, docSequence, InventoryConstants.TI,
                    InventoryConstants.SEQ_LENGTH, InventoryConstants.INIT_VALUE);
            break;
        default:
            break;
        }

        return sequence;
    }

    @Override
    public List<TransferTrx> selectTransferTrxs(IRequest request, TransferTrxQuery transferTrxQuery, int page,
            int pagesize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(page, pagesize);
        if (transferTrxQuery.getOverallStatusQ() != null) {
            List<String> overallStatuss = Arrays.asList(transferTrxQuery.getOverallStatusQ().split(";"));
            transferTrxQuery.setOverallStatuses(overallStatuss);
        }
        if (transferTrxQuery.getOutStatus() != null) {
            List<String> outStatuss = Arrays.asList(transferTrxQuery.getOutStatus().split(";"));
            transferTrxQuery.setOutStatuses(outStatuss);
        }
        List<TransferTrx> tx = transferTrxMapper.selectTransferTrxs(transferTrxQuery);
        for (TransferTrx trx : tx) {
            OrgSelection org = spmInvOrganizationService.getOrgSelection(request, trx.getOrganizationId());
            trx.setMarketName(org.getOperationUnitName());
        }

        return tx;

    }

    @Override
    public List<TransferTrxQuery> selectInTransferTrxs(IRequest request, TransferTrxQuery transferTrxQuery, int page,
            int pagesize) {

        if (transferTrxQuery.getInStatus() != null) {
            List<String> inStatuses = Arrays.asList(transferTrxQuery.getInStatus().split(";"));
            transferTrxQuery.setInStatuses(inStatuses);
        }
        if (transferTrxQuery.getOutStatus() != null) {
            List<String> outStatuses = Arrays.asList(transferTrxQuery.getOutStatus().split(";"));
            transferTrxQuery.setOutStatuses(outStatuses);
        }
        if (transferTrxQuery.getOverallStatusQ() != null) {
            List<String> overallStatuses = Arrays.asList(transferTrxQuery.getOverallStatusQ().split(";"));
            transferTrxQuery.setOverallStatuses(overallStatuses);
        }

        PageHelper.startPage(page, pagesize);
        List<TransferTrxQuery> tx = transferTrxMapper.selectInTransferTrxs(transferTrxQuery);
        for (TransferTrx trx : tx) {
            OrgSelection org = spmInvOrganizationService.getOrgSelection(request, trx.getOrganizationId());
            trx.setMarketName(org.getOperationUnitName());
        }
        return tx;
    }

    @Override
    public List<TransferTrxDetail> selectTransferTrxDetails(IRequest request, TransferTrxDetail transferTrxDetail) {
        List<TransferTrxDetail> transferTrxDetails = transferTrxDetailMapper
                .selectTransferTrxDetails(transferTrxDetail);
        for (TransferTrxDetail trxDetail : transferTrxDetails) {

            InvItem invItem = invItemMapper.selectByPrimaryKey(trxDetail.getItemId());
            trxDetail.setItemNumber(invItem.getItemNumber());
            trxDetail.setItemName(invItem.getItemName());
            trxDetail.setItemUomCode(invItem.getUomCode());
            // 商品主单位名称
            if (!StringUtils.isEmpty(invItem.getUomCode())) {
                InvUnitOfMeasureB unit = commInvUnitService.queryInvUnitConvertByCode(invItem.getUomCode());
                if (unit != null) {
                    trxDetail.setItemUomName(unit.getName());
                }
            }
            // 包装类型单位名称
            if (!StringUtils.isEmpty(trxDetail.getPackingType())) {
                InvUnitOfMeasureB unit = commInvUnitService.queryInvUnitConvertByCode(trxDetail.getPackingType());
                if (unit != null) {
                    trxDetail.setName(unit.getName());
                }
            }
            // 库存量
            Map<String, Object> onhandMap = new HashMap<String, Object>();
            onhandMap.put("itemId", trxDetail.getItemId());
            onhandMap.put("organizationId", trxDetail.getOrganizationId());
            onhandMap.put("lotNumber", trxDetail.getLotNumber());
            trxDetail.setQuantity(invOnhandQuantityMapper.getQuantityByItemAndLotNumber(onhandMap));
        }
        return transferTrxDetails;
    }

    @Override
    public boolean batchDelete(IRequest request, List<TransferTrx> transferTrxs) {

        for (TransferTrx transferTrx : transferTrxs) {
            // 先删除行
            TransferTrxDetail transferTrxDetail = new TransferTrxDetail();

            Long trxId = transferTrx.getTrxId();
            transferTrxDetail.setTrxId(trxId);
            transferTrxDetailMapper.deleteTransferTrxId(transferTrxDetail);
            // 在删除头
            transferTrxMapper.deleteByPrimaryKey(trxId);
        }

        return true;
    }

    @Override
    public boolean batchDeleteDetail(IRequest request, List<TransferTrxDetail> transferTrxDetails) {
        for (TransferTrxDetail transferTrxDetail : transferTrxDetails) {
            Long trxDetailId = transferTrxDetail.getTrxDetailId();
            transferTrxDetailMapper.deleteByPrimaryKey(trxDetailId);
        }
        return true;
    }

    @Override
    public List<TransferTrxDetailQuery> selectInTransferTrxDetails(IRequest request,
            TransferTrxDetailQuery transferTrxDetailQuery) {
        List<TransferTrxDetailQuery> transferTrxDetails = transferTrxDetailMapper
                .selectInTransferTrxDetails(transferTrxDetailQuery);
        for (TransferTrxDetailQuery trxDetail : transferTrxDetails) {

            InvItem invItem = invItemMapper.selectByPrimaryKey(trxDetail.getItemId());
            trxDetail.setItemNumber(invItem.getItemNumber());
            trxDetail.setItemName(invItem.getItemName());
            trxDetail.setItemUomCode(invItem.getUomCode());
            // 商品主单位名称
            if (!StringUtils.isEmpty(invItem.getUomCode())) {
                InvUnitOfMeasureB unit = commInvUnitService.queryInvUnitConvertByCode(invItem.getUomCode());
                if (unit != null) {
                    trxDetail.setItemUomName(unit.getName());
                }
            }
            // 包装类型单位名称
            if (!StringUtils.isEmpty(trxDetail.getPackingType())) {
                InvUnitOfMeasureB unit = commInvUnitService.queryInvUnitConvertByCode(trxDetail.getPackingType());
                if (unit != null) {
                    trxDetail.setName(unit.getName());
                }
            }
        }
        return transferTrxDetails;

    }

    /**
     * 判断转出和转入的数量是否相等.
     * 
     * @param request
     *            请求上下文
     * @param outTrxID
     *            转出单ID
     * @return 是否相等
     */
    public boolean isALLComplete(IRequest request, Long outTrxID) {

        TransferTrxDetail transferTrxDetail = new TransferTrxDetail();
        transferTrxDetail.setTrxId(outTrxID);
        List<TransferTrxDetail> transferTrxDetails = transferTrxDetailMapper
                .selectTransferTrxDetails(transferTrxDetail);
        for (TransferTrxDetail trxOutDeatail : transferTrxDetails) {
            if (trxOutDeatail.getRemainingIndAftCar().longValue() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 更新转出单上未转入的数量.
     * 
     * @param request
     *            请求上下文
     * 
     * @param transferOutTrx
     *            转出单.
     * @return 转出单
     * @throws InventoryException
     *             异常
     */
    public TransferTrx updateTrxRemainingNumber(IRequest request, TransferTrx transferOutTrx)
            throws InventoryException {

        for (TransferTrxDetail transferOutTrxDetail : transferOutTrx.getTransferTrxDetails()) {
            TransferTrxDetail trxInQuery = new TransferTrxDetail();
            trxInQuery.setSourceLineId(transferOutTrxDetail.getTrxDetailId());
            List<TransferTrxDetail> transferInTrxDetails = transferTrxDetailMapper.selectTransferTrxDetails(trxInQuery);

            BigDecimal reminingNum = transferOutTrxDetail.getTrxQty();
            for (TransferTrxDetail transferInTrxDetail : transferInTrxDetails) {
                reminingNum = reminingNum.subtract(transferInTrxDetail.getTrxQty());
            }
            /* Mclin 修改，添加转入数量大于剩余转入数量的判断 */
            if (BigDecimal.ZERO.compareTo(reminingNum) > 0) {
                throw new InventoryException(InventoryException.MSG_ERROR_TRF_ENTER_NUM_BIGGER_THAN_REMAINING_NUM,
                        new Object[] {});
            }

            transferOutTrxDetail.setRemainingIndAftCar(reminingNum);
            transferTrxDetailMapper.updateByPrimaryKey(transferOutTrxDetail);
        }
        return transferOutTrx;
    }

    /**
     * 查询转入转单及转入转出单行.
     * 
     * @param request
     *            请求上下文
     * @param trxQuery
     *            查询转入转出单dto.
     * @return 转入转出list
     */
    public List<TransferTrx> queryTransferTrxAndDetail(IRequest request, TransferTrxQuery trxQuery) {
        List<TransferTrx> transferTrxs = transferTrxMapper.selectTransferTrxForUpdate(trxQuery);
        for (TransferTrx trx : transferTrxs) {
            TransferTrxDetail transferTrxDetail = new TransferTrxDetail();
            transferTrxDetail.setTrxId(trx.getTrxId());
            List<TransferTrxDetail> transferTrxDetails = transferTrxDetailMapper
                    .selectTransferTrxDetails(transferTrxDetail);
            trx.setTransferTrxDetails(transferTrxDetails);
        }
        return transferTrxs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean validateTransferOutTrx(IRequest request, TransferTrx transferTrx) throws InventoryException {

        if (transferTrx.getTrxId() != null) {
            TransferTrxQuery trxQuery = new TransferTrxQuery();
            trxQuery.setTrxId(transferTrx.getTrxId());
            List<TransferTrx> tempTrxlist = queryTransferTrxAndDetail(request, trxQuery);
            TransferTrx tempTrx = tempTrxlist.get(0);
            // 重复提交
            if (InventoryConstants.COMP.equals(tempTrx.getStatus())) {
                throw new InventoryException(InventoryException.MSG_ERROR_SHALL_NOT_REPEAT_SUBMISSION, new Object[] {});
            }
            // 行数据为空
            if (tempTrx.getTransferTrxDetails().isEmpty()
                    && (transferTrx.getTransferTrxDetails() == null || transferTrx.getTransferTrxDetails().isEmpty())) {
                throw new InventoryException(InventoryException.MSG_ERROR_TRANSFER_LINE_IS_NULL, new Object[] {});
            }
        } else {
            // 行数据为空
            if (transferTrx.getTransferTrxDetails() == null || transferTrx.getTransferTrxDetails().isEmpty()) {
                throw new InventoryException(InventoryException.MSG_ERROR_TRANSFER_LINE_IS_NULL, new Object[] {});
            }
        }
        //
        // //转入但数量大于转出单总量
        // private static final String MSG_ERROR_IN_BG_OUT =
        // "MSG_ERROR_IN_BG_OUT";

        // 转出转入组织不能相同
        if (transferTrx.getOrganizationId().equals(transferTrx.getTransferOrgId())) {
            throw new InventoryException(InventoryException.MSG_ERROR_ORGANIZATION_NOT_ALLOWED_SAME, new Object[] {});
        }
        // 转出日期必须小于系统当前日期
        if (new Date().before(transferTrx.getTrxDate())) {
            throw new InventoryException(InventoryException.MSG_ERROR_TRXDATE_MUST_LESS_THAN_NOW_DATE, new Object[] {});
        }
        // 验证订单行
        if (transferTrx.getTransferTrxDetails() == null) {
            return true;
        }
        for (TransferTrxDetail transferTrxDetail : transferTrx.getTransferTrxDetails()) {

            if (transferTrxDetail.getItemId() == null) {
                throw new InventoryException(InventoryException.MSG_ERROR_REQUIRED, new Object[] {});
            }
            if (transferTrxDetail.getTrxQty() == null) {
                throw new InventoryException(InventoryException.MSG_ERROR_REQUIRED, new Object[] {});
            }
            boolean isUseLot = invItemPropertyService.isLotControl(request, transferTrxDetail.getOrganizationId(),
                    transferTrxDetail.getItemId());

            // 启用批次控制
            if (isUseLot) {
                if (transferTrxDetail.getLotNumber() == null || transferTrxDetail.getLotNumber().isEmpty()) {
                    throw new InventoryException(InventoryException.MSG_ERROR_ITEM_USE_LOT, new Object[] {});
                }
            }
            // 检查库存量 MSG_ERROR_QUTRY_GT_STOCK
            InvOnhandQuantity invOnhandQuantity = new InvOnhandQuantity();

            invOnhandQuantity.setItemId(transferTrxDetail.getItemId());
            invOnhandQuantity.setOrganizationId(transferTrx.getOrganizationId());
            if (isUseLot) {
                invOnhandQuantity.setLotNumber(transferTrxDetail.getLotNumber());
            }
            BigDecimal number = invOnhandQuantityService.queryOnhandQuantity(request, invOnhandQuantity);

            if (number.compareTo(transferTrxDetail.getTrxQty()) < 0) {
                throw new InventoryException(InventoryException.MSG_ERROR_QUTRY_GT_STOCK, new Object[] {});
            }
            if (BigDecimal.ZERO.compareTo(transferTrxDetail.getTrxQty()) >= 0) {
                throw new InventoryException(InventoryException.MSG_ERROR_TrxQty_CAN_NOT_BE_ZERO, new Object[] {});
            }
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean validateTransferInTrx(IRequest request, TransferTrx transferTrx) throws InventoryException {

        if (transferTrx.getTrxId() != null) {
            TransferTrxQuery trxQuery = new TransferTrxQuery();
            trxQuery.setTrxId(transferTrx.getTrxId());
            List<TransferTrx> tempTrxlist = queryTransferTrxAndDetail(request, trxQuery);
            TransferTrx tempTrx = tempTrxlist.get(0);
            // 重复提交
            if (InventoryConstants.COMP.equals(tempTrx.getStatus())) {
                throw new InventoryException(InventoryException.MSG_ERROR_SHALL_NOT_REPEAT_SUBMISSION, new Object[] {});
            }
            // 行数据为空
            if (tempTrx.getTransferTrxDetails().isEmpty()
                    && (transferTrx.getTransferTrxDetails() == null || transferTrx.getTransferTrxDetails().isEmpty())) {
                throw new InventoryException(InventoryException.MSG_ERROR_TRANSFER_LINE_IS_NULL, new Object[] {});
            }
        } else {
            // 行数据为空
            if (transferTrx.getTransferTrxDetails() == null || transferTrx.getTransferTrxDetails().isEmpty()) {
                throw new InventoryException(InventoryException.MSG_ERROR_TRANSFER_LINE_IS_NULL, new Object[] {});
            }
        }
        // 转出日期必须小于系统当前日期
        if (new Date().before(transferTrx.getTrxDate())) {
            throw new InventoryException(InventoryException.MSG_ERROR_TRXDATE_MUST_LESS_THAN_NOW_DATE, new Object[] {});
        }
        if (transferTrx.getTransferTrxDetails() == null) {
            return true;
        }
        for (TransferTrxDetail trxDetail : transferTrx.getTransferTrxDetails()) {
            TransferTrxDetail trxOutDetail = transferTrxDetailMapper.selectByPrimaryKey(trxDetail.getSourceLineId());
            if (trxOutDetail.getTrxQty().compareTo(trxDetail.getTrxQty()) < 0) {
                throw new InventoryException(InventoryException.MSG_ERROR_IN_BG_OUT, new Object[] {});
            }
            if (InventoryConstants.CANCELED.equals(trxDetail.getStatus())
                    && StringUtils.isEmpty(trxDetail.getRemark())) {
                throw new InventoryException(InventoryException.MSG_ERROR_REMAR_NOT_NULL_WHEN_STATUS_CANCLE,
                        new Object[] {});
            }
            // 数量必须大于0
            if (InventoryConstants.ACTIVE.equals(trxDetail.getStatus())
                    && BigDecimal.ZERO.compareTo(trxDetail.getTrxQty()) >= 0) {
                throw new InventoryException(InventoryException.MSG_ERROR_TrxQty_CAN_NOT_BE_ZERO, new Object[] {});
            }
        }
        return true;
    }

    @Override
    public List<TransferTrxDetailQuery> addTransferInTrxDetail(IRequest request, Long outTrxId, Long inTrxId,
            List<String> outTrxdDetailIds, String itemNumber) {
        // transferTrxMapper
        if (outTrxdDetailIds.size() == 0) {
            outTrxdDetailIds = null;
        }
        List<TransferTrxDetailQuery> TransferTrxDetails = transferTrxDetailMapper.queryAddTransferTrxDetails(outTrxId,
                inTrxId, outTrxdDetailIds, itemNumber);

        for (TransferTrxDetailQuery detail : TransferTrxDetails) {

            // 将转出剩余数量更新成0
            TransferTrxDetail outTrxDetail = new TransferTrxDetail();
            outTrxDetail.setTrxDetailId(detail.getSourceLineId());
            outTrxDetail.setRemainingIndAftCar(BigDecimal.ZERO);
            transferTrxDetailMapper.updateByPrimaryKeySelective(outTrxDetail);

            InvItem invItem = invItemMapper.selectByPrimaryKey(detail.getItemId());
            detail.setItemNumber(invItem.getItemNumber());
            detail.setItemName(invItem.getItemName());
            detail.setItemUomCode(invItem.getUomCode());
            // 商品主单位名称
            if (!StringUtils.isEmpty(invItem.getUomCode())) {
                InvUnitOfMeasureB unit = commInvUnitService.queryInvUnitConvertByCode(invItem.getUomCode());
                if (unit != null) {
                    detail.setItemUomName(unit.getName());
                }
            }
            // 包装类型单位名称
            if (!StringUtils.isEmpty(detail.getPackingType())) {
                InvUnitOfMeasureB unit = commInvUnitService.queryInvUnitConvertByCode(detail.getPackingType());
                if (unit != null) {
                    detail.setName(unit.getName());
                }
            }
            detail.setRemainingQty(0L);
        }
        return TransferTrxDetails;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void removeTransferInDetailTrx(IRequest request, List<TransferTrxDetail> transferTrxDetails) {

        // 更新转出单的剩余数量
        TransferTrxDetail temp;
        Iterator<TransferTrxDetail> iterator = transferTrxDetails.iterator();
        while (iterator.hasNext()) {
            temp = iterator.next();
            TransferTrxDetail transferOutTrxDetail = transferTrxDetailMapper.selectByPrimaryKey(temp.getSourceLineId());
            transferOutTrxDetail
                    .setRemainingIndAftCar(temp.getTrxQty().add(transferOutTrxDetail.getRemainingIndAftCar()));
            transferTrxDetailMapper.updateByPrimaryKey(transferOutTrxDetail);
        }
        // 删除转出单.

        self().batchDeleteDetail(request, transferTrxDetails);

    }

    @Override
    public TransferTrxDetail getRemainingIndAftCar(IRequest request, Long trxId, Long sourceTrxId, Long itemId,
            String lotNumber, String packingType) {
        List<TransferTrxDetail> list = transferTrxDetailMapper.getRemainingIndAftCar(trxId, sourceTrxId, itemId,
                lotNumber, packingType);
        if (list.isEmpty()) {
            return new TransferTrxDetail();
        }
        return list.get(0);
    }

    @Override
    public List<TransferTrxDetail> transferOutTrxCheck(IRequest request, TransferTrx checks) {
        Long trxId = checks.getTrxId();
        Long organizationId = checks.getOrganizationId();
        Long transferOrgId = checks.getTransferOrgId();
        List<TransferTrxDetail> wrongLots = new ArrayList<TransferTrxDetail>();
        // 循环校验每个批次
        for (TransferTrxDetail lot : checks.getTransferTrxDetails()) {
            TransferTrxDetail ttd = transferTrxDetailMapper.outTrxCheck(trxId, organizationId, transferOrgId,
                    lot.getItemId(), lot.getLotNumber());
            if (ttd != null) {
                wrongLots.add(ttd);
            }
        }
        return wrongLots;
    }

    @Override
    public List<TransferTrxDetail> transferInTrxCheck(IRequest request, TransferTrx checks) {
        Long trxId = checks.getTrxId();
        Long organizationId = checks.getOrganizationId();
        Long transferOrgId = checks.getTransferOrgId();
        List<TransferTrxDetail> wrongLots = new ArrayList<TransferTrxDetail>();
        // 循环校验每个批次
        for (TransferTrxDetail lot : checks.getTransferTrxDetails()) {
            TransferTrxDetail ttd = transferTrxDetailMapper.inTrxCheck(trxId, organizationId, transferOrgId,
                    lot.getItemId(), lot.getLotNumber());
            if (ttd != null) {
                wrongLots.add(ttd);
            }
        }
        return wrongLots;
    }

}
