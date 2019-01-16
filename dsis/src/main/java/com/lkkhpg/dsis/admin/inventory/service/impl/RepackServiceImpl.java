/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.exception.RepackTrxException;
import com.lkkhpg.dsis.admin.inventory.service.IInvTransactionService;
import com.lkkhpg.dsis.admin.inventory.service.IRepackService;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.dto.RepackTrx;
import com.lkkhpg.dsis.common.inventory.dto.RepackTrxDetail;
import com.lkkhpg.dsis.common.inventory.mapper.InvTransactionMapper;
import com.lkkhpg.dsis.common.inventory.mapper.RepackTrxDetailMapper;
import com.lkkhpg.dsis.common.inventory.mapper.RepackTrxMapper;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.dto.InvItemHierarchy;
import com.lkkhpg.dsis.common.product.mapper.InvItemHierarchyMapper;
import com.lkkhpg.dsis.common.product.mapper.InvItemMapper;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 重新分包Service接口实现.
 *
 * @author hanrui.huang
 */
@Service
@Transactional
public class RepackServiceImpl implements IRepackService {

    private Logger logger = LoggerFactory.getLogger(RepackServiceImpl.class);

    @Autowired
    private RepackTrxMapper repackTrxMapper;

    @Autowired
    private InvItemHierarchyMapper itemHierarchyMapper;

    @Autowired
    private RepackTrxDetailMapper repackTrxDetailMapper;

    @Autowired
    private InvItemMapper invItemMapper;

    @Autowired
    private IInvTransactionService invTransactionService;

    @Autowired
    private IDocSequenceService docSequenceService;

    @Autowired
    private InvTransactionMapper invTransactionMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<RepackTrx> queryRepack(IRequest request, RepackTrx repackTrx, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        if (repackTrx == null || repackTrx.getOrganizationId() == null) {
            return null;
        }
        return repackTrxMapper.selectRepack(repackTrx);
    }

    @Override
    public List<InvItemHierarchy> queryItem(IRequest request, InvItemHierarchy invItemHierarchy, int page,
                                            int pagesize) {
        // 进行分页
        PageHelper.startPage(page, pagesize);
        return itemHierarchyMapper.queryItemsByOrganizationId(invItemHierarchy);
    }

    @Override
    public RepackTrx createRepack(IRequest request, @StdWho RepackTrx repackTrx) {
        repackTrx.setObjectVersionNumber(1L);
        repackTrxMapper.insert(repackTrx);
        // 判断如果行不为空，则迭代循环插入
        if (repackTrx.getRepackTrxDetails() != null) {
            self().processRepackTrxDetails(repackTrx);
        }
        return repackTrx;
    }

    @Override
    public void processRepackTrxDetails(@StdWho RepackTrx repackTrx) {
        repackTrxDetailMapper.deleteByTrxId(repackTrx.getTrxId());
        Long organizationId = repackTrx.getOrganizationId();
        // 获取商品的ID
        List<RepackTrxDetail> repackTrxDetails = repackTrx.getRepackTrxDetails();
        for (RepackTrxDetail repackTrxDetail : repackTrxDetails) {
            // 组织ID
            repackTrxDetail.setOrganizationId(organizationId);
            repackTrxDetail.setProgramId(repackTrx.getPackageItemId());
            repackTrxDetail.setSubinventoryId(1L); // 子库ID不能为空暂设为1
            repackTrxDetail.setLocatorId(1L); // 货位ID不能为空暂设为-1
            repackTrxDetail.setObjectVersionNumber(1L);
            repackTrxDetail.setTrxId(repackTrx.getTrxId()); // 设置头ID跟行ID一致
            repackTrxDetailMapper.insert(repackTrxDetail);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<RepackTrx> batchUpdate(IRequest request, @StdWho List<RepackTrx> repackTrxs) throws RepackTrxException {
        for (RepackTrx repackTrx : repackTrxs) {
            if (StringUtils.isBlank(repackTrx.getTrxNumber())) {
                DocSequence docSequence = new DocSequence(InventoryConstants.DOC_SEQ_TYPE_REPACK,
                        InventoryConstants.DOC_SEQ_PREFIX_REPACK, null, null, null, null);
                String seq = docSequenceService.getSequence(request, docSequence,
                        InventoryConstants.DOC_SEQ_PREFIX_REPACK, InventoryConstants.MAX_LENGTH_REPACK,
                        InventoryConstants.MAX_LENGTH_INIT_VALUE);
                repackTrx.setTrxNumber(seq);
                self().createRepack(request, repackTrx);
            } else if (StringUtils.isNotBlank(repackTrx.getTrxNumber())) {
                self().updateCode(repackTrx);
            }
        }
        return repackTrxs;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RepackTrx updateCode(RepackTrx repackTrx) throws RepackTrxException {
        RepackTrx trx = repackTrxMapper.selectByPrimaryKey(repackTrx.getTrxId());
        validateRepeatSubmit(trx);
        repackTrxMapper.updateByTrxNumber(repackTrx);
        // 判断如果行不为空，则迭代循环插入
        if (repackTrx.getRepackTrxDetails() != null) {
            List<RepackTrx> repackTrxs = repackTrxMapper.selectRepack(repackTrx);
            repackTrx.setTrxId(repackTrxs.get(0).getTrxId());
            self().processRepackTrxDetails(repackTrx);
        }
        return repackTrx;
    }

    /**
     * 验证重复提交.
     *
     * @param trx RepackTrx
     * @throws RepackTrxException 重复提交异常
     */
    private void validateRepeatSubmit(RepackTrx trx) throws RepackTrxException {
        RepackTrx dbTrx = repackTrxMapper.selectByTrxNumber(trx.getTrxNumber());
        // 重复提交
        if (dbTrx != null && StringUtils.isNotBlank(dbTrx.getStatus())
                && InventoryConstants.REPACK_STATUS_COMP.equals(dbTrx.getStatus())) {
            throw new RepackTrxException(RepackTrxException.REPEAT_SUBMIT, null);
        }
    }

    @Override
    public boolean batchDelete(IRequest request, List<RepackTrx> repackTrxs) {
        for (RepackTrx repackTrx : repackTrxs) {
            repackTrxMapper.deleteRepack(repackTrx.getTrxNumber());
            repackTrxDetailMapper.deleteByTrxId(repackTrx.getTrxId());
        }
        return true;
    }

    @Override
    public List<InvItem> queryItemsByOrganizationId(IRequest request, InvItem item, int page, int pagesize) {
        // 进行分页
        PageHelper.startPage(page, pagesize);
        return invItemMapper.queryItemsByOrganizationId(item);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<RepackTrx> submitTransaction(IRequest request, @StdWho List<RepackTrx> repackTrxs, boolean flag)
            throws InventoryException, RepackTrxException {
        RepackTrx repackTrx = repackTrxs.get(0);
        validate(repackTrx);
        if (flag) {
            List<RepackTrx> repackTrxList = self().batchUpdate(request, repackTrxs);
            repackTrx = repackTrxList.get(0);
        }
        // 库存组织ID
        Long orgId = repackTrx.getOrganizationId();
        // 事务处理日期
        Date trxDate = repackTrx.getTrxDate();
        // 明细行
        List<RepackTrxDetail> repackTrxDetails = repackTrx.getRepackTrxDetails();
        // 提交出入库事务
        List<InvTransaction> invTrxs = new ArrayList<>();
        InvTransaction trx = new InvTransaction();
        // 子库ID非空暂定为1
        trx.setSubinventoryId(1L);
        // 货位ID非空暂定为1
        trx.setLocatorId(1L);
        trx.setOrganizationId(orgId);
        // 商品ID
        trx.setItemId(repackTrx.getPackageItemId());
        trx.setTrxSourceReference(repackTrx.getTrxNumber());
        // 事务处理日期
        trx.setTrxDate(trxDate);
        // 批次
        trx.setLotNumber(null);
        // 注释
        trx.setRemark(repackTrx.getRemark());

        // 商品包ID(新增)
        trx.setPackageItemId(repackTrx.getPackageItemId());

        String operType = repackTrx.getOperType();
        BigDecimal repackQty = new BigDecimal(repackTrx.getRepackQty());
        // 事务处理数量
        if (InventoryConstants.OPER_TYPE_COMPS.equals(operType)) {
            trx.setTrxType(InventoryConstants.TRANSACTION_TYPE_COMBINE_COMPLETE);
            // 分包类型为组合的时候事务为整数
            trx.setTrxQty(repackQty);
        } else {
            trx.setTrxType(InventoryConstants.TRANSACTION_TYPE_DECOMPOSE_COMPLETE);
            trx.setTrxQty(BigDecimal.ZERO.subtract(repackQty));
        }
        // 事务处理来源类型
        trx.setTrxSourceType(InventoryConstants.INV_REPACK_TRX);
        // 事务处理来源键值
        trx.setTrxSourceKey(String.valueOf(repackTrx.getTrxId()));
        invTrxs.add(trx);
        if (repackTrxDetails != null) {
            // 构造出入库事务List
            for (int i = 0; i < repackTrxDetails.size(); i++) {
                RepackTrxDetail detail = repackTrxDetails.get(i);
                InvTransaction trx2 = new InvTransaction();
                if (StringUtils.isBlank(detail.getLotNumber())) {
                    detail.setLotNumber(null);
                }
                // 库存组织ID
                trx2.setOrganizationId(orgId);
                // 字库ID
                trx2.setSubinventoryId(detail.getSubinventoryId());
                // 货位ID
                trx2.setLocatorId(detail.getLocatorId());
                // 商品ID
                trx2.setItemId(detail.getCountItemId());
                // 批次
                trx2.setLotNumber(detail.getLotNumber());

                // 商品包ID(新增)
                trx2.setPackageItemId(repackTrx.getPackageItemId());

                // 事务处理日期
                trx2.setTrxDate(trxDate);
                trx2.setTrxSourceReference(repackTrx.getTrxNumber());
                // 事务处理数量
                BigDecimal allocateQty = new BigDecimal(detail.getAllocateQty());
                if (InventoryConstants.OPER_TYPE_COMPS.equals(operType)) {
                    // 事务处理类型
                    trx2.setTrxType(InventoryConstants.TRANSACTION_TYPE_COMBINE_CONSUME);
                    trx2.setTrxQty(BigDecimal.ZERO.subtract(allocateQty));
                } else {
                    trx2.setTrxType(InventoryConstants.TRANSACTION_TYPE_DECOMPOSE_CONSUME);
                    trx2.setTrxQty(allocateQty);
                }
                // 事务处理来源类型
                trx2.setTrxSourceType(InventoryConstants.INV_REPACK_TRX_DETAIL);
                // 事务处理来源键值
                trx2.setTrxSourceKey(String.valueOf(detail.getTrxDetailId()));
                // 到期日
                trx2.setExpiryDate(detail.getExpiryDate());
                // 版本号
                trx2.setObjectVersionNumber(1L);
                // 设置备注
                if (StringUtils.isNotBlank(detail.getRemark())) {
                    if (StringUtils.isNotBlank(trx.getRemark())) {
                        trx2.setRemark(
                                trx.getRemark() + InventoryConstants.REPACKTRX_REMARK_SEPARATOR + detail.getRemark());
                    } else {
                        trx2.setRemark(detail.getRemark());
                    }
                }

                invTrxs.add(trx2);
            }
        }
        invTransactionService.processTransaction(request, invTrxs);
        self().updateRepackStatus(request, repackTrxs);
        return repackTrxs;
    }

    @Override
    public void updateRepackStatus(IRequest request, @StdWho List<RepackTrx> repackTrxs) {
        for (RepackTrx repackTrx : repackTrxs) {
            repackTrx.setStatus(InventoryConstants.REPACK_STATUS_COMP);
            repackTrxMapper.updateByTrxNumber(repackTrx);
        }
    }

    @Override
    public List<RepackTrxDetail> selectDetail(IRequest request, RepackTrxDetail repackTrxDetail, int page,
                                              int pagesize) {
        // 进行分页
        PageHelper.startPage(page, pagesize);
        return repackTrxDetailMapper.selectRepackTrxDetail(repackTrxDetail);
    }

    @Override
    public boolean batchDeleteDetails(IRequest request, List<RepackTrxDetail> repackTrxDetails) {
        for (RepackTrxDetail detail : repackTrxDetails) {
            repackTrxDetailMapper.deleteByPrimaryKey(detail.getTrxDetailId());
        }
        return true;
    }

    @Override
    public RepackTrx selectById(IRequest request, Long trxId) {
        RepackTrx params = new RepackTrx();
        params.setTrxId(trxId);
        List<RepackTrx> trxs = repackTrxMapper.selectRepack(params);
        if (trxs.size() == 1) {
            return trxs.iterator().next();
        }
        return null;
    }

    @Override
    public List<RepackTrxDetail> selectDetails(IRequest request, RepackTrxDetail repackTrxDetail) {
        return repackTrxDetailMapper.selectRepackTrxDetails(repackTrxDetail);
    }

    @Override
    public List<InvItemHierarchy> queryItem(IRequest request, InvItemHierarchy invItemHierarchy) {
        return itemHierarchyMapper.queryItemsByOrganizationId(invItemHierarchy);
    }


    @Override
    public List<InvOnhandQuantity> queryComposeLot(IRequest request, InvOnhandQuantity invOnhandQuantity) {
        return repackTrxDetailMapper.queryComposeLot(invOnhandQuantity);
    }

    /**
     * 验证RepackTrx是否合法，是否含有批次行.
     *
     * @param trx RepackTrx
     * @throws RepackTrxException RepackTrxException
     */
    private void validate(RepackTrx trx) throws RepackTrxException {

        validateRepeatSubmit(trx);

        if (trx.getRepackTrxDetails() == null || trx.getRepackTrxDetails().isEmpty()) {
            throw new RepackTrxException(RepackTrxException.NOT_INCLUDE_DETAILS, null);
        }
        Map<String, Long> map = new HashMap<String, Long>();
        InvItemHierarchy params = new InvItemHierarchy();
        params.setOrganizationId(trx.getOrganizationId());
        params.setPackageItemId(trx.getPackageItemId());
        // 查询商品包对应明细
        List<InvItemHierarchy> invItemHierarchies = itemHierarchyMapper.queryItemsByOrganizationId(params);
        if (invItemHierarchies == null || invItemHierarchies.isEmpty()) {
            throw new RepackTrxException(RepackTrxException.NOT_INCLUDE_DETAILS, null);
        }
        Set<String> invItemIds = new HashSet<String>();
        for (InvItemHierarchy invItem : invItemHierarchies) {
            invItemIds.add(String.valueOf(invItem.getItemId()));
        }
        for (RepackTrxDetail detail : trx.getRepackTrxDetails()) {
            // 如果没有实际扣的商品，则报错
            if (detail.getCountItemId() == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("there is no countItemId in {}", detail.getTrxDetailId());
                }
                throw new RepackTrxException(RepackTrxException.INCLUDE_MORE_DETAILS, null);
            }
            // 商品明细中没有ItemId，而detail中有此itemId，说明此detail是多余的
            String key = String.valueOf(detail.getItemPackageComponents());
            if (!invItemIds.contains(key)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("the more details are {}", detail.getTrxDetailId());
                }
                throw new RepackTrxException(RepackTrxException.INCLUDE_MORE_DETAILS, null);
            }
            // sum是总数量，以后可能 要用
            Long sum = map.get(key);
            if (sum == null) {
                map.put(String.valueOf(detail.getItemPackageComponents()), detail.getAllocateQty());
                continue;
            }
            sum += detail.getAllocateQty();
            map.put(key, sum);
        }
        for (String invItemId : invItemIds) {
            // 商品明细中有此id，而detail中没有此itemId，说明此分包单少数据了
            if (!map.containsKey(invItemId)) {
                throw new RepackTrxException(RepackTrxException.NOT_INCLUDE_DETAILS, null);
            }
        }
    }

    @Override
    public int getLoTQuantity(IRequest request, InvTransaction invTransaction) {
        int comcsQuantity = 0;
        int dcmcsQuantity = 0;
        invTransaction.setTrxType(InventoryConstants.COMBINE_CONSUME);
        //组合-商品扣减数量
        if (invTransactionMapper.getQuantity(invTransaction) != null) {
            comcsQuantity = Math.abs(invTransactionMapper.getQuantity(invTransaction));
        }

        invTransaction.setTrxType(InventoryConstants.DECOMPOSE_CONSUME);
        //-- 分解-商品增加
        if (invTransactionMapper.getQuantity(invTransaction) != null) {
            dcmcsQuantity = Math.abs(invTransactionMapper.getQuantity(invTransaction));
        }
        return comcsQuantity - dcmcsQuantity;
    }

}
