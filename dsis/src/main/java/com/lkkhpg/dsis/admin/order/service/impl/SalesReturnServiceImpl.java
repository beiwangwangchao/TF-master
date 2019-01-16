/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.service.IInvTransactionService;
import com.lkkhpg.dsis.admin.order.service.ISalesReturnService;
import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.mapper.SpmCurrencyMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmInvOrganizationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.ProductConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.delivery.mapper.OrderDeliveryLineMapper;
import com.lkkhpg.dsis.common.enums.SequenceType;
import com.lkkhpg.dsis.common.exception.CommInventoryException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.inventory.dto.InvLot;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.mapper.InvLotMapper;
import com.lkkhpg.dsis.common.member.dto.MemAccountingTrx;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.dto.SalesAdjustment;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesReturn;
import com.lkkhpg.dsis.common.order.dto.SalesRtnLine;
import com.lkkhpg.dsis.common.order.dto.SalesRtnLot;
import com.lkkhpg.dsis.common.order.dto.SalesRtnRefund;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesReturnMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesRtnLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesRtnLotMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesRtnRefundMapper;
import com.lkkhpg.dsis.common.product.mapper.InvItemAssignMapper;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.common.service.IMemberBalanceTrxService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.common.service.IVoucherService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.ICodeService;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 退货单Service实现类.
 * 
 * @author houmin
 */
@Service
@Transactional
public class SalesReturnServiceImpl implements ISalesReturnService {

    private final Logger logger = LoggerFactory.getLogger(SalesReturnServiceImpl.class);

    @Autowired
    private ICommSalesOrderService commSalesOrderService;
    @Autowired
    private SalesReturnMapper salesReturnMapper;
    @Autowired
    private SalesRtnRefundMapper salesRtnRefundMapper;
    @Autowired
    private SalesRtnLineMapper salesRtnLineMapper;
    @Autowired
    private SalesRtnLotMapper salesRtnLotMapper;
    @Autowired
    private SalesLineMapper salesLineMapper;
    @Autowired
    private OrderDeliveryLineMapper orderDeliveryLineMapper;
    @Autowired
    private InvItemAssignMapper invItemAssignMapper;
    @Autowired
    private IDocSequenceService docSequenceService;
    @Autowired
    private InvLotMapper invLotMapper;
    @Autowired
    private SpmPeriodMapper spmPeriodMapper;
    @Autowired
    private IInvTransactionService invTransactionService;
    @Autowired
    private IMemberBalanceTrxService memberBalanceTrxService;
    @Autowired
    private IVoucherService voucherService;
    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;
    @Autowired
    private SpmInvOrganizationMapper spmInvOrganizationMapper;
    @Autowired
    private SpmLocationMapper spmLocationMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    @Autowired
    private ICodeService codeService;
    @Autowired
    private IParamService paramService;
    @Autowired
    private SpmCurrencyMapper spmCurrencyMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesReturn saveReturnOrderDetail(IRequest request, SalesReturn salesReturn) throws CommOrderException {
        if (salesReturn == null || salesReturn.getOrderHeaderId() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("salesReturn is null.");
            }
            return salesReturn;
        }
        // 获取订单详情
        SalesOrder salesOrder = commSalesOrderService.getOrder(request, salesReturn.getOrderHeaderId(), true, false);
        if (salesOrder == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("SalesOrder not found!OrderHeaderId : {}", salesReturn.getOrderHeaderId());
            }
            return salesReturn;
        }
        processRtnHead(request, salesReturn, salesOrder);
        processRtnLine(salesReturn);
        processRtnRefund(salesReturn);
        return salesReturn;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesReturn submitReturnOrderDetail(IRequest request, SalesReturn salesReturn)
            throws CommOrderException, CommInventoryException, CommMemberException {
        if (salesReturn == null || salesReturn.getOrderHeaderId() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("salesReturn is null.");
            }
            return salesReturn;
        }
        // 获取订单详情
        SalesOrder salesOrder = commSalesOrderService.getOrder(request, salesReturn.getOrderHeaderId(), true, false);
        if (salesOrder == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("SalesOrder not found!OrderHeaderId : {}", salesReturn.getOrderHeaderId());
            }
            return null;
        }
        // 校验Ecoupon
        voucherService.validateEcoupon(request, salesReturn.getOrderHeaderId());
        processRtnHead(request, salesReturn, salesOrder);
        createCreditNote(request, salesReturn);
        processRtnLine(salesReturn);
        processRtnRefund(salesReturn);
        processInvAccountingTrx(request, salesReturn);
        processMemAccountingTrx(request, salesReturn, salesOrder);
        return salesReturn;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteSalesReturn(IRequest request, List<SalesReturn> salesReturns) throws CommOrderException {
        if (salesReturns == null || salesReturns.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("SalesReturns is null.");
            }
            return;
        }
        for (SalesReturn salesReturn : salesReturns) {
            if (salesReturn == null || salesReturn.getReturnId() == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("salesReturn or returnId is null");
                }
                return;
            }
            // 校验状态
            SalesReturn objLock = salesReturnMapper.selectByPrimaryKey(salesReturn.getReturnId());
            if (!OrderConstants.RETURN_STATUS_NEW.equals(objLock.getReturnStatus())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The returnNumber[{}] status is changed.", salesReturn.getReturnNumber());
                }
                throw new CommOrderException(CommOrderException.MSG_WARNING_DTO_SALESRETURN_STATUS_CHANGED, null);
            }
            if (salesReturn.getSalesRtnLines() != null) {
                // 删除批次
                for (SalesRtnLine salesRtnLine : salesReturn.getSalesRtnLines()) {
                    salesRtnLotMapper.deleteByRtnLineId(salesRtnLine.getReturnLineId());
                    // 删除商品包中商品退货批次信息
                    if (ProductConstants.ITEM_TYPE_PACKAGE.equals(salesRtnLine.getItemType())
                            || OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_NOT_COUNT.equals(salesRtnLine.getItemType())
                            || OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT.equals(salesRtnLine.getItemType())) {
                        if (!ProductConstants.ITEM_COUNT_TYPE_PACKG.equals(salesRtnLine.getCountType())) {
                            for (SalesRtnLine itemPkgDetails : salesRtnLine.getItemPkgDetail()) {
                                salesRtnLotMapper.deleteByRtnLineId(itemPkgDetails.getReturnLineId());
                            }
                        }
                    }
                }
            }
            salesRtnLineMapper.deleteByReturnId(salesReturn.getReturnId());
            salesRtnRefundMapper.deleteByReturnId(salesReturn.getReturnId());
            salesReturnMapper.deleteByPrimaryKey(salesReturn.getReturnId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteSalesRtnLine(IRequest request, List<SalesRtnLine> salesRtnLines) throws CommOrderException {
        if (salesRtnLines == null || salesRtnLines.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("salesRtnLines is null.");
            }
            return;
        }
        for (SalesRtnLine salesRtnLine : salesRtnLines) {
            salesRtnLotMapper.deleteByRtnLineId(salesRtnLine.getReturnLineId());
            salesRtnLineMapper.deleteByPrimaryKey(salesRtnLine.getReturnLineId());
            // 商品包时删除包中商品信息
            if (salesRtnLine.getItemPkgDetail() != null) {
                for (SalesRtnLine itemPkgDetail : salesRtnLine.getItemPkgDetail()) {
                    salesRtnLotMapper.deleteByRtnLineId(itemPkgDetail.getReturnLineId());
                    salesRtnLineMapper.deleteByPrimaryKey(itemPkgDetail.getReturnLineId());
                }
            }
        }
    }

    /**
     * 退货单头处理.
     * 
     * @param request
     *            统一上下文
     * @param salesReturn
     *            退货单详情
     * @param salesOrder
     *            退货订单详情
     * @throws CommOrderException
     *             订单异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void processRtnHead(IRequest request, SalesReturn salesReturn, SalesOrder salesOrder)
            throws CommOrderException {
        salesReturnVali(request, salesReturn, salesOrder);
        String returnNumber; // 退货单编号
        if (salesReturn.getReturnId() == null) {
            returnNumber = docSequenceService.getSequence(request,
                    new DocSequence(SequenceType.RETURNORDER.toString(), null, null, null, null, null),
                    SequenceType.RETURNORDER.toString(), OrderConstants.RETURN_DIGIT,
                    OrderConstants.RETURN_DIGIT_BEGIN_NUM);
            salesReturn.setReturnNumber(returnNumber);
            salesReturn.setCreatedBy(request.getAccountId());
            salesReturnMapper.insertSelective(salesReturn);
        } else {
            // lock
            SalesReturn objLock = salesReturnMapper.selectByReturnIdForLock(salesReturn.getReturnId());
            if (!OrderConstants.RETURN_STATUS_NEW.equals(objLock.getReturnStatus())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The returnNumber[{}] status is changed.", salesReturn.getReturnNumber());
                }
                throw new CommOrderException(CommOrderException.MSG_WARNING_DTO_SALESRETURN_STATUS_CHANGED, null);
            }
            salesReturnMapper.updateByPrimaryKeySelective(salesReturn);
        }
    }

    /**
     * 库存事务处理.
     * 
     * @param request
     *            统一上下文
     * @param salesReturn
     *            退货单详情
     * @throws InventoryException
     *             库存统一异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void processInvAccountingTrx(IRequest request, SalesReturn salesReturn) throws InventoryException {
        for (SalesRtnLine salesRtnLine : salesReturn.getSalesRtnLines()) {
            if (!DTOStatus.DELETE.equals(salesRtnLine.get__status())) {
                // 非商品包处理
                if (!ProductConstants.ITEM_TYPE_PACKAGE.equals(salesRtnLine.getItemType())
                        && !OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_NOT_COUNT.equals(salesRtnLine.getItemType())
                        && !OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT.equals(salesRtnLine.getItemType())) {
                    // "实际商品编码”触发产生库存事务处理
                    processLotAndInvTrx(request, salesRtnLine, salesReturn, salesRtnLine.getCountItemId(), false);
                } else {
                    if (ProductConstants.ITEM_COUNT_TYPE_IDV.equals(salesRtnLine.getCountType())) {
                        for (SalesRtnLine itemPkgDetails : salesRtnLine.getItemPkgDetail()) {
                            processLotAndInvTrx(request, itemPkgDetails, salesReturn, salesRtnLine.getCountItemId(),
                                    true);
                        }
                    } else if (ProductConstants.ITEM_COUNT_TYPE_PACKG.equals(salesRtnLine.getCountType())) {
                        processLotAndInvTrx(request, salesRtnLine, salesReturn, salesRtnLine.getCountItemId(), false);
                    }
                }
            }
        }
    }

    /**
     * 批次和库存事务逻辑处理.
     * 
     * @param request
     *            统一上下文
     * @param salesRtnLine
     *            行信息
     * @param salesReturn
     *            退货单信息
     * @param trxItemId
     *            事务处理商品ID
     * @param isItemPkg
     *            计算库存方式是否是单个商品
     * @throws InventoryException
     *             库存 统一异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void processLotAndInvTrx(IRequest request, SalesRtnLine salesRtnLine, SalesReturn salesReturn,
            Long trxItemId, boolean isItemPkg) throws InventoryException {
        List<InvTransaction> invTransactions = new ArrayList<InvTransaction>();
        List<SalesRtnLot> lots = salesRtnLine.getSalesRtnLots();
        // 启用批次控制
        if (OrderConstants.RETURN_INV_FLAG_Y.equals(salesRtnLine.getReturnInvFlag())) {
            if (OrderConstants.YES.equals(salesRtnLine.getLotCtrlFlag())) {
                for (SalesRtnLot lot : lots) {
                    InvTransaction invTransaction = new InvTransaction(); // 库存事务处理
                    // 商品包时
                    if (isItemPkg) {
                        invTransaction.setPackageItemId(trxItemId);
                        invTransaction.setItemId(salesRtnLine.getCountItemId());
                    } else {
                        invTransaction.setItemId(trxItemId);
                    }
                    invTransaction.setOrganizationId(salesReturn.getInvOrgId());
                    invTransaction.setLotNumber(lot.getLotNumber());
                    invTransaction.setTrxQty(lot.getQuantity());
                    invTransaction.setExpiryDate(lot.getDeliveryEndDate());
                    invTransaction.setTrxType(MemberConstants.TRX_TYPE_RETURN);
                    // invTransaction.setTrxDate(salesReturn.getReturnDate());
                    invTransaction.setTrxDate(new Date());
                    invTransaction.setTrxSourceType(MemberConstants.TRX_TYPE_RETURN_LOT);
                    invTransaction.setTrxSourceKey(lot.getReturnLotId().toString());
                    invTransaction.setTrxSourceReference(salesReturn.getReturnNumber());
                    invTransactions.add(invTransaction);
                    InvLot invLot = new InvLot();
                    invLot.setItemId(salesRtnLine.getItemId());
                    invLot.setLotNumber(lot.getLotNumber());
                    invLot.setOrganizationId(salesReturn.getInvOrgId());
                    invLot.setExpiryDate(lot.getDeliveryEndDate());
                    if (invLotMapper.queryInvLotCount(invLot) < 1) {
                        // 插入批次表
                        invLotMapper.insertSelective(invLot);
                    }
                }
            } else {
                // 不启用批次控制
                InvTransaction invTransaction = new InvTransaction(); // 库存事务处理
                // 商品包时
                if (isItemPkg) {
                    invTransaction.setPackageItemId(trxItemId);
                    invTransaction.setItemId(salesRtnLine.getCountItemId());
                } else {
                    invTransaction.setItemId(trxItemId);
                }
                invTransaction.setOrganizationId(salesReturn.getInvOrgId());
                invTransaction.setTrxQty(salesRtnLine.getQuantity());
                invTransaction.setTrxType(MemberConstants.TRX_TYPE_RETURN);
                invTransaction.setTrxSourceType(MemberConstants.TRX_TYPE_RETURN_LINE);
                invTransaction.setTrxDate(salesReturn.getReturnDate());
                invTransaction.setTrxSourceKey(salesRtnLine.getReturnLineId().toString());
                invTransaction.setTrxSourceReference(salesReturn.getReturnNumber());
                invTransactions.add(invTransaction);
            }
        }
        // 库存事务处理
        if (invTransactions.size() > 0) {
            invTransactionService.processTransaction(request, invTransactions);
        }
    }

    /**
     * 账务事务处理.
     * 
     * @param request
     *            统一上下文
     * @param salesReturn
     *            退货单详情
     * @param salesOrder
     *            退货订单详情
     * @throws CommMemberException
     *             会员统一异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void processMemAccountingTrx(IRequest request, SalesReturn salesReturn, SalesOrder salesOrder)
            throws CommMemberException {
        // 非会员订单不做账务事务处理
        if (salesOrder.getMemberId() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Non-Member Order do not process member accounting transactional.OrderNumber : [{}]",
                        salesOrder.getOrderNumber());
            }
            if (OrderConstants.RETURN_TYPE_EXCH.equals(salesReturn.getReturnType())) {
                salesReturn.setReturnStatus(OrderConstants.RETURN_STATUS_EXGED);
            } else if (OrderConstants.RETURN_TYPE_REFD.equals(salesReturn.getReturnType())) {
                salesReturn.setReturnStatus(OrderConstants.RETURN_STATUS_REFED);
            }
            salesReturnMapper.updateByPrimaryKeySelective(salesReturn);
            return;
        }
        BigDecimal adjustAmount = BigDecimal.ZERO;
        BigDecimal rbValue = BigDecimal.ZERO;
        BigDecimal salesPoints = BigDecimal.ZERO;
        BigDecimal pvSum = BigDecimal.ZERO;
        String returnType = salesReturn.getReturnType();
        MemAccountingTrx memAccountingTrx = new MemAccountingTrx();
        // 销售订单奖金月份是否关闭
        if (salesOrder.getPeriodId() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("PeriodId not found!SalesReturn : {}", new Object[] { salesReturn });
            }
            throw new CommMemberException(CommMemberException.MSG_ERROR_OM_BONUS_MONTH_ERROR, null);
        }
        SpmPeriod spmPeriod = spmPeriodMapper.selectByPrimaryKey(salesReturn.getPeriodId());
        if (spmPeriod == null || StringUtils.isEmpty(spmPeriod.getClosingStatus())) {
            if (logger.isDebugEnabled()) {
                logger.debug("Period is not found or is not open!PeriodId : {}",
                        new Object[] { salesReturn.getPeriodId() });
            }
            throw new CommMemberException(CommMemberException.MSG_ERROR_OM_BONUS_MONTH_ERROR, null);
        }
        // 组装会员账务事务处理dto
        Member member = memberMapper.selectByPK(salesOrder.getMemberId());
        if (member == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Member not found! MemberId : {}", new Object[] { salesReturn.getMemberId() });
            }
            throw new CommMemberException(CommMemberException.MSG_ERROR_NO_MEMBER, null);
        }
        memAccountingTrx.setCompanyId(member.getCompanyId());
        memAccountingTrx.setMemberId(salesOrder.getMemberId());
        memAccountingTrx.setSalesOrgId(salesOrder.getSalesOrgId());
        memAccountingTrx.setTrxDate(new Date());
        memAccountingTrx.setTrxType(MemberConstants.TRX_TYPE_RETURN);
        memAccountingTrx.setTrxSourceType(MemberConstants.TRX_SOURCE_TYPE_RETURN);
        memAccountingTrx.setTrxSourceId(salesReturn.getReturnId());
        memAccountingTrx.setTrxSourceLineId(salesReturn.getReturnId());
        // pv计算
        for (SalesRtnLine salesRtnLine : salesReturn.getSalesRtnLines()) {
            if (!DTOStatus.DELETE.equals(salesRtnLine.get__status())) {
                for (SalesLine salesLine : salesOrder.getLines()) {
                    // 取订单行上的pv和销售价格
                    if (salesLine.getLineId().equals(salesRtnLine.getOrderLineId())
                            && salesLine.getItemId().equals(salesRtnLine.getItemId())) {
                        pvSum = pvSum.add(salesRtnLine.getQuantity().multiply(salesLine.getPv()));
                        break;
                    }
                }
            }
        }
        // 换货时，支付调整金额退至EB或RB
        if (OrderConstants.RETURN_TYPE_EXCH.equals(salesReturn.getReturnType())) {
            for (SalesAdjustment adjustMent : salesOrder.getAdjustMents()) {
                adjustAmount = adjustAmount.add(adjustMent.getAdjustmentAmount());
            }
        }
        // 退款时
        if (OrderConstants.RETURN_TYPE_REFD.equals(salesReturn.getReturnType())) {
            for (SalesRtnRefund salesRtnRefund : salesReturn.getSalesRtnRefundLines()) {
                switch (salesRtnRefund.getRefundMethod()) {
                case OrderConstants.ADJUSTMENT_TYPE_RB:
                    rbValue = rbValue.add(salesRtnRefund.getRefundAmount());
                    break;
                case OrderConstants.ADJUSTMENT_TYPE_SP:
                    salesPoints = salesPoints.add(salesRtnRefund.getRefundAmount());
                    break;
                default:
                    break;
                }
            }
        }
        // 未关闭
        if (OrderConstants.CLOSING_STATUS_N.equals(spmPeriod.getClosingStatus())) {
            switch (returnType) {
            case OrderConstants.RETURN_TYPE_REFD:
                salesReturn.setReturnStatus(OrderConstants.RETURN_STATUS_REFED);
                salesReturnMapper.updateByPrimaryKeySelective(salesReturn);
                // RB处理
                if (rbValue.compareTo(BigDecimal.ZERO) > 0) {
                    memAccountingTrx.setAccountingType(OrderConstants.ADJUSTMENT_TYPE_RB);
                    memAccountingTrx.setTrxValue(rbValue);
                    memberBalanceTrxService.processAccountingTrx(request, memAccountingTrx);
                }
                break;
            case OrderConstants.RETURN_TYPE_EXCH:
                salesReturn.setReturnStatus(OrderConstants.RETURN_STATUS_EXGED);
                salesReturnMapper.updateByPrimaryKeySelective(salesReturn);
                // 退还至RB
                memAccountingTrx.setAccountingType(OrderConstants.ADJUSTMENT_TYPE_RB);
                memAccountingTrx.setTrxValue(salesReturn.getActualPayAmount());
                memberBalanceTrxService.processAccountingTrx(request, memAccountingTrx);
                break;
            default:
                break;
            }
        } else if (OrderConstants.CLOSING_STATUS_Y.equals(spmPeriod.getClosingStatus())) {
            switch (returnType) {
            case OrderConstants.RETURN_TYPE_REFD:
                salesReturn.setReturnStatus(OrderConstants.RETURN_STATUS_RECLM);
                salesReturnMapper.updateByPrimaryKeySelective(salesReturn);
                // RB处理
                if (rbValue.compareTo(BigDecimal.ZERO) > 0) {
                    memAccountingTrx.setAccountingType(OrderConstants.ADJUSTMENT_TYPE_EB);
                    memAccountingTrx.setTrxValue(rbValue);
                    memberBalanceTrxService.processAccountingTrx(request, memAccountingTrx);
                }
                break;
            case OrderConstants.RETURN_TYPE_EXCH:
                salesReturn.setReturnStatus(OrderConstants.RETURN_STATUS_EXCLM);
                salesReturnMapper.updateByPrimaryKeySelective(salesReturn);
                // 退还至EB
                memAccountingTrx.setAccountingType(OrderConstants.ADJUSTMENT_TYPE_EB);
                memAccountingTrx.setTrxValue(salesReturn.getActualPayAmount());
                memberBalanceTrxService.processAccountingTrx(request, memAccountingTrx);
                break;
            default:
                break;
            }
        }
        // PV处理
        memAccountingTrx.setAccountingType(MemberConstants.ACCOUNTING_TYPE_PV);
        memAccountingTrx.setTrxValue(BigDecimal.ZERO.subtract(pvSum));
        memberBalanceTrxService.processAccountingTrx(request, memAccountingTrx);
        // SalesPoint处理
        if (salesPoints.compareTo(BigDecimal.ZERO) > 0) {
            memAccountingTrx.setAccountingType(OrderConstants.ADJUSTMENT_TYPE_SP);
            memAccountingTrx.setTrxValue(salesPoints);
            memberBalanceTrxService.processAccountingTrx(request, memAccountingTrx);
        }
    }

    /**
     * 退货行处理.
     * 
     * @param salesReturn
     *            退货单详情
     * @throws CommOrderException
     *             订单异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void processRtnLine(SalesReturn salesReturn) throws CommOrderException {
        List<SalesRtnLine> salesRtnLines = salesReturn.getSalesRtnLines();
        for (SalesRtnLine salesRtnLine : salesRtnLines) {
            switch (salesRtnLine.get__status()) {
            case DTOStatus.ADD:
                salesRtnLine.setSalesOrgId(salesReturn.getSalesOrgId());
                salesRtnLine.setReturnId(salesReturn.getReturnId());
                salesRtnLineMapper.insertSelective(salesRtnLine);
                processRtnLot(salesRtnLine, DTOStatus.ADD);
                // 商品包中商品退货记录
                if (ProductConstants.ITEM_TYPE_PACKAGE.equals(salesRtnLine.getItemType())
                        || OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_NOT_COUNT.equals(salesRtnLine.getItemType())
                        || OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT.equals(salesRtnLine.getItemType())) {
                    // 商品包的库存计算方式不是商品包时
                    if (!ProductConstants.ITEM_COUNT_TYPE_PACKG.equals(salesRtnLine.getCountType())) {
                        for (SalesRtnLine itemPkgDetail : salesRtnLine.getItemPkgDetail()) {
                            itemPkgDetail.setReturnId(salesRtnLine.getReturnId());
                            itemPkgDetail.setSalesOrgId(salesRtnLine.getSalesOrgId());
                            salesRtnLineMapper.insertSelective(itemPkgDetail);
                            processRtnLot(itemPkgDetail, DTOStatus.ADD);
                        }
                    }
                }
                break;
            case DTOStatus.DELETE:
                processRtnLot(salesRtnLine, DTOStatus.DELETE);
                salesRtnLineMapper.deleteByPrimaryKey(salesRtnLine.getReturnLineId());
                if (ProductConstants.ITEM_TYPE_PACKAGE.equals(salesRtnLine.getItemType())
                        || OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_NOT_COUNT.equals(salesRtnLine.getItemType())
                        || OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT.equals(salesRtnLine.getItemType())) {
                    if (!ProductConstants.ITEM_COUNT_TYPE_PACKG.equals(salesRtnLine.getCountType())) {
                        for (SalesRtnLine itemPkgDetail : salesRtnLine.getItemPkgDetail()) {
                            processRtnLot(itemPkgDetail, DTOStatus.DELETE);
                            salesRtnLineMapper.deleteByPrimaryKey(itemPkgDetail.getReturnLineId());
                        }
                    }
                }
                break;
            case DTOStatus.UPDATE:
                salesRtnLineMapper.updateByPrimaryKeySelective(salesRtnLine);
                processRtnLot(salesRtnLine, DTOStatus.UPDATE);
                if (ProductConstants.ITEM_TYPE_PACKAGE.equals(salesRtnLine.getItemType())
                        || OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_NOT_COUNT.equals(salesRtnLine.getItemType())
                        || OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT.equals(salesRtnLine.getItemType())) {
                    if (!ProductConstants.ITEM_COUNT_TYPE_PACKG.equals(salesRtnLine.getCountType())) {
                        for (SalesRtnLine itemPkgDetail : salesRtnLine.getItemPkgDetail()) {
                            salesRtnLineMapper.updateByPrimaryKeySelective(itemPkgDetail);
                            processRtnLot(itemPkgDetail, DTOStatus.UPDATE);
                        }
                    }
                }
            default:
                break;
            }
        }
    }

    /**
     * 退货数量校验.
     * 
     * @param salesRtnLine
     *            退货行信息
     * @param invOrgId
     *            库存组织ID
     * @param isItemPkg
     *            是否是商品包
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void returnQuantityVali(SalesRtnLine salesRtnLine, String returnStatus, Long invOrgId, boolean isItemPkg)
            throws CommOrderException {
        BigDecimal outstandingQty = BigDecimal.ZERO; // 已发运数量
        BigDecimal retunrnQty = BigDecimal.ZERO; // 已退货数量
        BigDecimal enabledQty = BigDecimal.ZERO; // 可退货数量
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> invAndLotMap = new HashMap<String, Object>();
        List<Long> invOrgIds = new ArrayList<Long>();
        // 可退货数量校验
        enabledQty = salesRtnLine.getEnabledRtnQuantity();
        outstandingQty = orderDeliveryLineMapper.selectQtySumByOrderLineId(salesRtnLine.getOrderLineId());
        retunrnQty = salesRtnLineMapper.selectSumQtyByOrderLineId(salesRtnLine.getOrderLineId(), returnStatus,
                salesRtnLine.getReturnId());
        if (retunrnQty == null) {
            retunrnQty = BigDecimal.ZERO;
        }
        // 重新计算可退货数量
        enabledQty = outstandingQty.subtract(retunrnQty);
        salesRtnLine.setEnabledRtnQuantity(enabledQty);
        invOrgIds.add(invOrgId);
        map.put("itemId", salesRtnLine.getItemId());
        map.put("invOrgIds", invOrgIds);
        // 判断退货仓库是否分配商品
        if (invItemAssignMapper.queryIncludeItemOrgs(map) < 1) {
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_ITEM_NO_EXISTS_ORG, null);
        } else {
            invAndLotMap = salesRtnLineMapper.selectByItemAndOrgId(salesRtnLine.getItemId(), invOrgId);
            if (invAndLotMap == null || invAndLotMap.isEmpty()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("stockFalg or invFlag not found {}", salesRtnLine);
                }
            }
            if (salesRtnLine.getQuantity().compareTo(enabledQty) > 0) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_QUANTITY, null);
            }
        }
    }

    /**
     * 退货行信息校验.
     * 
     * @param salesReturn
     *            退货单详情
     * @param salesOrder
     *            销售订单详情
     * @throws CommOrderException
     *             订单统一异常
     */
    private void returnLineVali(SalesReturn salesReturn, SalesOrder salesOrder) throws CommOrderException {
        List<SalesRtnLine> salesRtnLines = salesReturn.getSalesRtnLines();
        // 积分兑换订单退款方式只能选择退款
        if (OrderConstants.ORDER_TYPE_RDEM.equals(salesOrder.getOrderType())) {
            if (!OrderConstants.RETURN_TYPE_REFD.equals(salesReturn.getReturnType())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Redeem order can only select <refunds> return type.OrderHeaderId:{}, ReturnType:{}",
                            salesOrder.getHeaderId(), salesReturn.getReturnType());
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURNTYPE_NOT_ALLOW, null);
            }
        }
        for (SalesRtnLine salesRtnLine : salesRtnLines) {
            if (!DTOStatus.DELETE.equals(salesRtnLine.get__status()) && salesRtnLine.getItemId() != null) {
                // 商品代码校验
                if (salesRtnLine.getItemId() == null) {
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RTN_ITEM_NOT_EMPTY, null);
                }
                // 计入库存校验
                if (StringUtils.isEmpty(salesRtnLine.getReturnInvFlag())) {
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_RTN_INV_FLAG_NOT_EMPTY, null);
                }
                // 批次校验
                if (!OrderConstants.NO.equals(salesRtnLine.getCountStockFlag())
                        && OrderConstants.YES.equals(salesRtnLine.getLotCtrlFlag())) {
                    if (salesRtnLine.getSalesRtnLots() == null || salesRtnLine.getSalesRtnLots().size() == 0) {
                        throw new CommOrderException(CommOrderException.MSG_ERROR_DM_DELIVERY_LOT_NOT_EMPTY, null);
                    }
                }
                // 退货数量校验
                if (!ProductConstants.ITEM_TYPE_PACKAGE.equals(salesRtnLine.getItemType())
                        && !OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_NOT_COUNT.equals(salesRtnLine.getItemType())
                        && !OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT.equals(salesRtnLine.getItemType())) {
                    returnQuantityVali(salesRtnLine, salesReturn.getReturnStatus(), salesReturn.getInvOrgId(), false);
                } else {
                    // 库存计算方式不是计算商品包时
                    if (!ProductConstants.ITEM_COUNT_TYPE_PACKG.equals(salesRtnLine.getCountType())) {
                        for (SalesRtnLine itemPkgDetail : salesRtnLine.getItemPkgDetail()) {
                            returnQuantityVali(itemPkgDetail, salesReturn.getReturnStatus(), salesReturn.getInvOrgId(),
                                    true);
                        }
                    }
                }
            }
        }
    }

    /**
     * 退货单批次存入.
     * 
     * @param salesRtnLine
     *            退货单行详情
     * @param lineStatus
     *            退货单行状态
     * @throws CommOrderException
     *             订单统一异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void processRtnLot(SalesRtnLine salesRtnLine, String lineStatus) throws CommOrderException {
        String status = "nochange";
        if (salesRtnLine.getReturnLineId() != null && salesRtnLine.getSalesRtnLots() != null) {
            for (SalesRtnLot salesRtnLot : salesRtnLine.getSalesRtnLots()) {
                if (DTOStatus.DELETE.equals(lineStatus)) {
                    status = DTOStatus.DELETE;
                } else if (!StringUtils.isEmpty(salesRtnLot.get__status())) {
                    status = salesRtnLot.get__status();
                }
                switch (status) {
                case DTOStatus.ADD:
                    salesRtnLot.setSalesOrgId(salesRtnLine.getSalesOrgId());
                    salesRtnLot.setReturnLineId(salesRtnLine.getReturnLineId());
                    salesRtnLotMapper.insertSelective(salesRtnLot);
                    break;
                case DTOStatus.DELETE:
                    salesRtnLotMapper.deleteByPrimaryKey(salesRtnLot.getReturnLotId());
                    break;
                case DTOStatus.UPDATE:
                    salesRtnLotMapper.updateByPrimaryKeySelective(salesRtnLot);
                    break;
                default:
                    break;
                }
            }
        }
    }

    /**
     * 退货单-退款信息存入.
     * 
     * @param salesReturn
     *            退货单详情
     * @throws CommOrderException
     *             订单统一异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void processRtnRefund(SalesReturn salesReturn) throws CommOrderException {
        if (OrderConstants.RETURN_TYPE_EXCH.equals(salesReturn.getReturnType())) {
            return;
        }
        List<SalesRtnRefund> slaesRtnRefunds = salesReturn.getSalesRtnRefundLines();
        for (SalesRtnRefund salesRtnRefund : slaesRtnRefunds) {
            switch (salesRtnRefund.get__status()) {
            case DTOStatus.ADD:
                salesRtnRefund.setReturnId(salesReturn.getReturnId());
                salesRtnRefundMapper.insertSelective(salesRtnRefund);
                break;
            case DTOStatus.UPDATE:
                salesRtnRefundMapper.updateByPrimaryKeySelective(salesRtnRefund);
                break;
            case DTOStatus.DELETE:
                salesRtnRefundMapper.deleteByPrimaryKey(salesRtnRefund.getReturnRefundId());
                break;
            default:
                break;
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SalesReturn> selectSalesReturn(IRequest request, SalesReturn salesReturn, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        String returnStatus = salesReturn.getReturnStatus();
        List<String> statusList = new ArrayList<String>();
        if (returnStatus != null) {
            for (String s : returnStatus.split(";")) {
                statusList.add(s);
            }
        }
        salesReturn.setReturnStatusList(statusList);
        List<SalesReturn> list = salesReturnMapper.selectSalesReturnByParas(salesReturn);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SpmLocation getLocations(IRequest request, Long salesOrgId, Long invOrgId) {
        Long locationId = 0L;
        if (salesOrgId == null && invOrgId == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("salesOrgId and invOrgId is null {}{}", salesOrgId, invOrgId);
            }
            return null;
        }
        if (salesOrgId != null) {
            locationId = spmSalesOrganizationMapper.selectByPrimaryKey(salesOrgId).getLocationId();
            if (locationId == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("salesOrgId not found location {}", salesOrgId);
                }
                return null;
            }
        }
        if (invOrgId != null) {
            locationId = spmInvOrganizationMapper.selectByPrimaryKey(invOrgId).getLocationId();
            if (locationId == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("invOrgId not found location {}", invOrgId);
                }
                return null;
            }
        }
        return spmLocationMapper.selectLocationById(locationId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesReturn getReturnDetail(IRequest request, Long returnId) throws CommOrderException {
        // 查询头信息
        SalesReturn salesReturn = salesReturnMapper.selectDetailByReturnId(returnId);
        SpmLocation salesLocation = self().getLocations(request, salesReturn.getSalesOrgId(), null);
         //SpmLocation invLocation = self().getLocations(request, null, salesReturn.getInvOrgId());
       // salesReturn.setInvOrgLocation(salesLocation.getAddressReturn());
        //salesReturn.setSalesOrgLocation(invLocation.getAddressReturn());
        List<SalesRtnLot> rtnLots;
        List<SalesRtnLine> salesRtnLines = salesRtnLineMapper.selectDetailByReturnId(salesReturn.getSalesOrgId(),
                salesReturn.getInvOrgId(), salesReturn.getReturnId(), salesReturn.getReturnStatus(), null);
        // 查询行中商品包中商品明细
        for (SalesRtnLine salesRtnLine : salesRtnLines) {
            // 查询可退货数量：已发运-已退货
            if (ProductConstants.ITEM_TYPE_PACKAGE.equals(salesRtnLine.getItemType())
                    || OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_NOT_COUNT.equals(salesRtnLine.getItemType())
                    || OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT.equals(salesRtnLine.getItemType())) {
                if (!ProductConstants.ITEM_COUNT_TYPE_PACKG.equals(salesRtnLine.getCountType())) {
                    List<SalesRtnLine> itemPkgDetails = salesRtnLineMapper.selectDetailByReturnId(
                            salesReturn.getSalesOrgId(), salesReturn.getInvOrgId(), salesReturn.getReturnId(),
                            salesReturn.getReturnStatus(), salesRtnLine.getOrderLineId());
                    BigDecimal outstandQty = salesRtnLineMapper.getOutstandQtyByPkg(salesRtnLine.getOrderLineId(),
                            salesRtnLine.getItemId());
                    outstandQty = outstandQty == null ? BigDecimal.ZERO : outstandQty;
                    BigDecimal returnQty = salesRtnLineMapper.selectSumQtyByOrderLineId(salesRtnLine.getOrderLineId(),
                            salesReturn.getReturnStatus(), returnId);
                    returnQty = returnQty == null ? BigDecimal.ZERO : returnQty;
                    if (outstandQty.subtract(returnQty).compareTo(BigDecimal.ZERO) < 0) {
                        salesRtnLine.setEnabledRtnQuantity(BigDecimal.ZERO);
                    } else {
                        salesRtnLine.setEnabledRtnQuantity(outstandQty.subtract(returnQty));
                    }
                    // 商品包明细中商品批次信息
                    for (SalesRtnLine itemPkgDetail : itemPkgDetails) {
                        itemPkgDetail.setSalesRtnLots(
                                salesRtnLotMapper.queryByReturnLineId(itemPkgDetail.getReturnLineId()));
                        rtnLots = salesRtnLotMapper.selectLotsByItemIdAndLineId(returnId, salesReturn.getReturnStatus(),
                                itemPkgDetail.getItemId(), itemPkgDetail.getOrderLineId(), null, null);
                        for (SalesRtnLot salesRtnLot : itemPkgDetail.getSalesRtnLots()) {
                            for (SalesRtnLot rtnLot : rtnLots) {
                                if (salesRtnLot.getLotNumber().equals(rtnLot.getLotNumber())) {
                                    salesRtnLot.setEnabledLotQty(rtnLot.getOutstandingQty()
                                            .subtract(rtnLot.getBackQty()).subtract(rtnLot.getReturnQty()));
                                    break;
                                }
                            }
                        }
                    }
                    salesRtnLine.setItemPkgDetail(itemPkgDetails);
                    continue;
                }
            }
            // 查询批次信息
            salesRtnLine.setSalesRtnLots(salesRtnLotMapper.queryByReturnLineId(salesRtnLine.getReturnLineId()));
            rtnLots = salesRtnLotMapper.selectLotsByItemIdAndLineId(returnId, salesReturn.getReturnStatus(),
                    salesRtnLine.getItemId(), salesRtnLine.getOrderLineId(), null, null);
            for (SalesRtnLot salesRtnLot : salesRtnLine.getSalesRtnLots()) {
                for (SalesRtnLot rtnLot : rtnLots) {
                    if (salesRtnLot.getLotNumber().equals(rtnLot.getLotNumber())) {
                        salesRtnLot.setEnabledLotQty(rtnLot.getOutstandingQty().subtract(rtnLot.getBackQty())
                                .subtract(rtnLot.getReturnQty()));
                        break;
                    }
                }
            }
        }
        // 查询退款信息
        List<SalesRtnRefund> salesRtnRefundLines = salesRtnRefundMapper.queryByReturnId(returnId);
        salesReturn.setSalesRtnLines(salesRtnLines);
        salesReturn.setSalesRtnRefundLines(salesRtnRefundLines);
        return salesReturn;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SalesLine> selectDetailByReturnId(IRequest request, SalesLine salesLine) {
        return salesLineMapper.selectByReturnHeaderId(salesLine);
    }

    /**
     * 退货单校验.
     * 
     * @param salesReturn
     *            退货单详情
     * @param salesOrder
     *            订单详情
     * @throws CommOrderException
     *             订单统一异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void salesReturnVali(IRequest request, SalesReturn salesReturn, SalesOrder salesOrder)
            throws CommOrderException {
        returnLineVali(salesReturn, salesOrder);
        Map<String, BigDecimal> map = isReturnAdjusAndShip(salesReturn, salesOrder);
        int temp = 0;
        BigDecimal returnAmtSum = BigDecimal.ZERO; // 退款行退款金额总和
        BigDecimal returnAmtCount = BigDecimal.ZERO; // 汇总退款单行：销售价格×退货数量
        BigDecimal adjustAmt = salesReturn.getAdjustAmt();
        BigDecimal shipFeeAmt = salesReturn.getShippingFeeAmt();
        BigDecimal actualRtnAmt = salesReturn.getActualPayAmount();
        BigDecimal manualAdjustAmt = salesReturn.getManualAdjustAmt();
        SalesReturn amount = salesReturnMapper.getAmount(salesReturn);
        // 手工调整金额大于等于0
        if (BigDecimal.ZERO.compareTo(manualAdjustAmt) > 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("The manual adjustment amount is less than 0. manualAdjustAmt:[{}]", manualAdjustAmt);
            }
            throw new CommOrderException(CommOrderException.MSG_WARNING_RETURN_MANUAL_ADJUSTAMT_LARGER_ZERO, null);
        }
        // 汇总订单行上退款总额或积分总和
        BigDecimal unitSelling = BigDecimal.ZERO;
        for (SalesRtnLine salesRtnLine : salesReturn.getSalesRtnLines()) {
            if (!DTOStatus.DELETE.equals(salesRtnLine.get__status())) {
                for (SalesLine salesLine : salesOrder.getLines()) {
                    // 取订单行上价格或积分
                    if (salesLine.getLineId().equals(salesRtnLine.getOrderLineId())
                            && salesLine.getItemId().equals(salesRtnLine.getItemId())) {
                        unitSelling = OrderConstants.ORDER_TYPE_RDEM.equals(salesOrder.getOrderType())
                                ? salesLine.getUnitRedeemPoint() : salesLine.getUnitSellingPrice();
                        break;
                    }
                }
                returnAmtCount = returnAmtCount.add(
                        salesRtnLine.getQuantity().multiply(unitSelling).multiply(salesRtnLine.getConversionRate()));
            }
        }
        List<String> orgParams = paramService.getParamValues(request, SystemProfileConstants.SPM_CURRENCY,
                SystemProfileConstants.ORG_TYPE_SALES, salesReturn.getSalesOrgId());
        // 取本位币下精度
        SpmCurrency spmCurrency = new SpmCurrency();
        spmCurrency.setCurrencyCode(orgParams.get(0));
        List<SpmCurrency> spmCurrencys = spmCurrencyMapper.querySpmCurrency(spmCurrency);
        int precision = spmCurrencys.get(0).getPrecision().intValue();
        // 取退货单销售组织上本位币组织参数对应的精度
        returnAmtCount = returnAmtCount.setScale(precision, BigDecimal.ROUND_HALF_UP);
        if (OrderConstants.RETURN_TYPE_REFD.equals(salesReturn.getReturnType())) {
            boolean hasRefund = false;
            if (salesReturn.getSalesRtnRefundLines() != null) {
                for (SalesRtnRefund salesRtnRefund : salesReturn.getSalesRtnRefundLines()) {
                    if (!DTOStatus.DELETE.equals(salesRtnRefund.get__status())) {
                        hasRefund = true;
                        break;
                    }
                }
            }
            if (!hasRefund) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_REFUND_WAY_NOT_NULL, null);
            }
            List<SalesRtnRefund> slaesRtnRefunds = salesReturn.getSalesRtnRefundLines();
            for (SalesRtnRefund salesRtnRefund : slaesRtnRefunds) {
                if (!DTOStatus.DELETE.equals(salesRtnRefund.get__status())) {
                    if (OrderConstants.ADJUSTMENT_TYPE_SP.equals(salesRtnRefund.getRefundMethod())) {
                        temp += 1;
                    }
                    returnAmtSum = returnAmtSum.add(salesRtnRefund.getRefundAmount());
                }
            }
            if (returnAmtSum.compareTo(actualRtnAmt) != 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Return single refund amount[{}] is not equal the sum of real-refundable[{}]",
                            new Object[] { returnAmtSum, actualRtnAmt });
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_AMOUNT_NOT_EQUAL, null);
            }
        }
        // 积分兑换订单
        if (OrderConstants.ORDER_TYPE_RDEM.equals(salesOrder.getOrderType())) {
            if (temp != 1 && OrderConstants.RETURN_TYPE_REFD.equals(salesReturn.getReturnType())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Select the type of redeem order can only a refund SalesPoint.OrderHeaderId : {}, "
                            + "SalesPoint size : {}", salesOrder.getHeaderId(), temp);
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_METHOD_NOT_ONLY, null);
            }
            if (returnAmtCount.add(adjustAmt).add(shipFeeAmt).subtract(manualAdjustAmt).compareTo(actualRtnAmt) != 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Return single refund amount[{}] is not equal the sum of real-refundable[{}]",
                            new Object[] { returnAmtCount, actualRtnAmt });
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_AMOUNT_NOT_EQUAL, null);
            }
        } else {
            if (temp != 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Orders of non-redeem can not select SalesPoint refund.OrderHeaderId : {}, "
                            + "SalesPoint size : {}", salesOrder.getHeaderId(), temp);
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_METHOD_NOT_ALLOW, null);
            }
            // 原订单金额-历史退货金额-当前退款金额+人工调整>=优惠券额数
            BigDecimal valiAmt = amount.getOrderAmt().subtract(amount.getReturnAmt()).subtract(returnAmtCount)
                    .add(map.get("actrulAdjustAmt"));
            if (valiAmt.compareTo(amount.getVoucherOrderAmt()) >= 0) {
                if (actualRtnAmt
                        .compareTo(returnAmtCount.add(adjustAmt).add(shipFeeAmt).subtract(manualAdjustAmt)) != 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Actual return amount calculate error!");
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_ACTRULAMT_CHECK_ERROR, null);
                }
            } else {
                if (BigDecimal.ZERO.compareTo(amount.getReturnPromotionSum()) < 0
                        && BigDecimal.ZERO.compareTo(salesReturn.getReturnPromotion()) != 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Repeat return Promotion error! OrderNumber : {}",
                                new Object[] { salesReturn.getOrderNumber() });
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_PROMOTION, null);
                }
                if (actualRtnAmt.compareTo(returnAmtCount.add(adjustAmt).add(shipFeeAmt).subtract(manualAdjustAmt)
                        .subtract(salesReturn.getReturnPromotionSum())) != 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Actual Return Amount calculate is error!");
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_ACTRULAMT_CHECK_ERROR, null);
                }
            }
        }
    }

    /**
     * 返还人工调整和返还运费校验.
     * 
     * @param salesReturn
     *            退货单对象
     * @param salesOrder
     *            订单对象
     * @return 订单上人工调整和运费金额
     * @throws CommOrderException
     *             订单统一异常
     */
    private Map<String, BigDecimal> isReturnAdjusAndShip(SalesReturn salesReturn, SalesOrder salesOrder)
            throws CommOrderException {
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        BigDecimal actrulShipFeeAmt = BigDecimal.ZERO; // 订单实际运费
        BigDecimal actrulAdjustAmt = BigDecimal.ZERO; // 订单实际人工调整
        BigDecimal enabledQtySum = BigDecimal.ZERO; // 可退货数量总和
        BigDecimal currentRtnQtySum = BigDecimal.ZERO; // 当前退货数量总和
        // 运费
        int isRtnShipFlag = salesReturnMapper.isReturnShippingFeeFlag(salesReturn.getOrderHeaderId(),
                salesReturn.getReturnId());
        SalesLogistics logistics = salesOrder.getLogistics();
        if (logistics != null) {
            actrulShipFeeAmt = logistics.getShippingFee();
        }
        // 返还人工调整
        int isRtnAdjustFlag = salesReturnMapper.isReturnAdjustFlag(salesReturn.getOrderHeaderId(),
                salesReturn.getReturnId());
        List<SalesAdjustment> adjustMents = salesOrder.getAdjustMents() == null ? new ArrayList<SalesAdjustment>()
                : salesOrder.getAdjustMents();
        for (SalesAdjustment adjustMent : adjustMents) {
            actrulAdjustAmt = actrulAdjustAmt.add(adjustMent.getAdjustmentAmount());
        }
        // 判断：可退货数量和当前退货数量是否相等来判断是否是最后一单
        for (SalesRtnLine salesRtnLine : salesReturn.getSalesRtnLines()) {
            if (!DTOStatus.DELETE.equals(salesRtnLine.get__status())) {
                enabledQtySum = enabledQtySum.add(salesRtnLine.getEnabledRtnQuantity());
                currentRtnQtySum = currentRtnQtySum.add(salesRtnLine.getQuantity());
            }
        }
        // 是最后一单
        if (enabledQtySum.compareTo(currentRtnQtySum) < 1) {
            // 1.有人工调整且是否返还人工调整选择的为"否"
            if (BaseConstants.NO.equals(salesReturn.getReturnAdjustFlag())
                    && (BigDecimal.ZERO).compareTo(actrulAdjustAmt) != 0) {
                if (isRtnAdjustFlag < 1) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("You must manually adjust the amount of refund.SalesOrder:[{}]",
                                new Object[] { salesReturn.getOrderNumber() });
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_RM_MUST_RETURN_ADJUST_AMT, null);
                }
            }
            // 2.有运费且是否返还运费选择的为"否"
            if (BaseConstants.NO.equals(salesReturn.getShippingFeeFlag())
                    && (BigDecimal.ZERO).compareTo(actrulShipFeeAmt) != 0) {
                if (isRtnShipFlag < 1) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("You must manually shippingFee the amount of refund.SalesOrder:[{}]",
                                new Object[] { salesReturn.getOrderNumber() });
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_RM_MUST_RETURN_SHIPPING_FEE_AMT, null);
                }
            }
        }
        // 3.人工调整校验：是否重复返还和金额校验
        if (BaseConstants.YES.equals(salesReturn.getReturnAdjustFlag())) {
            if (isRtnAdjustFlag > 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("OrderNumber: {} had return adjustAmount!SalesOrder:[{}]",
                            new Object[] { salesReturn.getOrderNumber() });
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_ADJUST_FLAG_REPEAT, null);
            } else {
                if (salesReturn.getAdjustAmt().compareTo(actrulAdjustAmt) != 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Adjust Amount is not equals.ReturnOrder : {},SalesOrder : {}",
                                new Object[] { salesReturn.getAdjustAmt(), actrulAdjustAmt });
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_ADJUST_AMOUNT_ERROR, null);
                }
            }
        }
        // 4.运费校验：是否重复返还和金额校验
        if (BaseConstants.YES.equals(salesReturn.getShippingFeeFlag())) {
            if (isRtnShipFlag > 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("OrderNumber: {} had return shippingFee amount!SalesOrder:[{}]",
                            new Object[] { salesReturn.getOrderNumber() });
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_SHIPFEE_FLAG_REPEAT, null);
            } else {
                if (salesReturn.getShippingFeeAmt().compareTo(actrulShipFeeAmt) != 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("ShippingFee Amount is not equals.ReturnOrder : {},SalesOrder : {}",
                                new Object[] { salesReturn.getShippingFeeAmt(), actrulShipFeeAmt });
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RETURN_SHIPFEE_AMT_ERROR, null);
                }
            }
        }
        map.put("actrulAdjustAmt", actrulAdjustAmt);
        map.put("actrulShipFeeAmt", actrulShipFeeAmt);
        return map;
    }

    /**
     * 生成账款单编号并更新到退货单表中.
     * 
     * @param salesReturn
     *            退货单详情
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void createCreditNote(IRequest request, SalesReturn salesReturn) {
        StringBuffer creditNoteNumber = new StringBuffer(); // 账款单编号
        String nowYear = new SimpleDateFormat("yy").format(new Date());
        SpmMarket spmMarket = spmMarketMapper.selectBySalesOrgId(salesReturn.getSalesOrgId());
        if (spmMarket == null || StringUtils.isEmpty(spmMarket.getCode())) {
            if (logger.isDebugEnabled()) {
                logger.debug("Create credite note error!Market not found!");
            }
            return;
        }
        // 当前市场下订单是否生成账款单
        CodeValue codeValue = codeService.getCodeValue(request, OrderConstants.RM_CREDIT_NOTED_NO, spmMarket.getCode());
        if (codeValue == null || StringUtils.isEmpty(codeValue.getMeaning())
                || StringUtils.isEmpty(codeValue.getValue())) {
            if (logger.isDebugEnabled()) {
                logger.debug("codeValue or meaning not found,CodeName : {}",
                        new Object[] { OrderConstants.RM_CREDIT_NOTED_NO });
            }
            return;
        }
        // 订单对应会员是否存在GST ID
        // Member member = memberMapper.selectByPK(salesReturn.getMemberId());
        // if (member == null || StringUtils.isEmpty(member.getGstIdNumber())) {
        // if (logger.isDebugEnabled()) {
        // logger.debug("The member of salesOrder not found GST Id
        // Number.MemberId : {}",
        // salesReturn.getMemberId());
        // }
        // return;
        // }
        // creditNoteNumber.append(SequenceType.CREDITNOTE.toString());
        creditNoteNumber.append(codeValue.getMeaning().toString());
        String sequenceStr = docSequenceService.getSequence(request,
                new DocSequence(codeValue.getMeaning().toString(), nowYear, codeValue.getValue(), null, null, null),
                nowYear, OrderConstants.CREDIT_NOTE_DIGIT, OrderConstants.CREDIT_NOTE_BEGIN_NUM);
        creditNoteNumber.append(sequenceStr);
        salesReturn.setCreditNote(creditNoteNumber.toString());
        salesReturnMapper.updateByPrimaryKeySelective(salesReturn);
    }

}