/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.constant.VoucherConstants;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.order.dto.SalesAdjustment;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.promotion.dto.VoucherAssign;
import com.lkkhpg.dsis.common.promotion.dto.VoucherTransaction;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherAssignMapper;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherMapper;
import com.lkkhpg.dsis.common.service.ITaxService;
import com.lkkhpg.dsis.common.service.IVoucherApplyValiService;
import com.lkkhpg.dsis.common.service.IVoucherQuantityTrxService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 优惠券使用Service实现类.
 * 
 * @author houmin
 *
 */
@Service
@Transactional
public class VoucherApplyValiServiceImpl implements IVoucherApplyValiService {

    private Logger logger = LoggerFactory.getLogger(VoucherApplyValiServiceImpl.class);

    @Autowired
    private ITaxService taxService;
    @Autowired
    private VoucherMapper voucherMapper;
    @Autowired
    private VoucherAssignMapper voucherAssignMapper;
    @Autowired
    private IVoucherQuantityTrxService voucherQuantityTrxService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesOrder voucherApplyValidator(IRequest request, SalesOrder salesOrder, boolean isDoTrx)
            throws CommVoucherException {
        // TODO 1.销售订单行上优惠券校验

        // 2.销售订单头上优惠券校验
        salesOrder = salesHeadVoucherVali(request, salesOrder, isDoTrx);

        return salesOrder;
    }

    /**
     * 订单行上优惠券校验.
     * 
     * @param request
     *            统一上下文
     * @param salesOrder
     *            销售订单详情
     * @param isDoTrx
     *            是否进行事务处理
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void salesLineVoucherVali(IRequest request, SalesOrder salesOrder, boolean isDoTrx)
            throws CommVoucherException {
        // TODO 销售订单行优惠券校验

    }

    /**
     * 订单头上优惠券校验.
     * 
     * @param request
     *            统一上下文
     * @param salesOrder
     *            销售订单详情
     * @param isDoTrx
     *            是否进行事务处理
     * @throws CommVoucherException
     *             优惠券统一异常
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private SalesOrder salesHeadVoucherVali(IRequest request, SalesOrder salesOrder, boolean isDoTrx)
            throws CommVoucherException {
        BigDecimal nonOverlappingQty = BigDecimal.ZERO; // 不可叠加优惠券数量
        BigDecimal discountAmt = BigDecimal.ZERO; // 优惠总额
        Map<Long, Long> voucherQtyMap = new HashMap<Long, Long>();
        // 1.汇总行上的订单金额、税额、税前金额
        // 税后金额
        BigDecimal afterAmt = BigDecimal.ZERO;
        // 订单税额
        BigDecimal taxAmt = BigDecimal.ZERO;
        // 税后金额
        BigDecimal beforeAmt = BigDecimal.ZERO;
        for (SalesLine salesLine : salesOrder.getLines()) {
            afterAmt = afterAmt.add(salesLine.getUnitSellingPrice().multiply(salesLine.getQuantity()));
        }
        // 获取税率
        SpmTax tax = taxService.getTax(request);
        if (afterAmt.compareTo(BigDecimal.ZERO) > 0) {
            taxAmt = taxService.getTaxAmount(request, afterAmt, tax);
        }
        if (tax != null && !tax.getPriceInclueTax()) {
            beforeAmt = afterAmt.subtract(taxAmt);
        } else {
            beforeAmt = afterAmt;
        }
        // 2.获取头上优惠券
        List<Voucher> vouchers = salesOrder.getVouchers();
        if (vouchers == null || vouchers.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Not found the salesOrder apply voucher, headerId : {}.",
                        new Object[] { salesOrder.getHeaderId() });
            }
            return salesOrder;
        }
        // 循环校验每张优惠券
        for (Voucher voucher : vouchers) {
            // 判断优惠券是否可叠加
            if (VoucherConstants.PDM_USAGE_MODE_NSTAC.equals(voucher.getUsageMode())
                    && nonOverlappingQty.compareTo(BigDecimal.ZERO) > 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Non-Overlapping voucher quantity is error! voucherName : {}", voucher.getName());
                }
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHER_NSTAC_QTY_ERROR, null);
            }
            // 判断优惠券税应用：税前则报错
            if (VoucherConstants.PDM_APPLY_ON_ECLD.equals(voucher.getAppTax())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Sales Order header can not apply afterTax voucher. voucherName : {}",
                            new Object[] { voucher.getName() });
                }
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHER_AFTERTAX_NOT_APPLY, null);
            }
            // 判断优惠券是否有效
            if (voucherMapper.selectEnableVoucher(voucher) == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Voucher not enabled or invalid, voucherName : {}",
                            new Object[] { voucher.getName() });
                }
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHAER_INVALID, null);
            }
            // 判断优惠券数量是否足够
            if (!voucherAssignQtyVali(voucher, salesOrder, voucherQtyMap)) {
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_MEMBER_NOT_ASSIGN_VOUCHER, null);
            }
            // 判断优惠券应用条件是否是"订单"
            if (!VoucherConstants.PDM_APPLY_CRITERIA_INVOI.equals(voucher.getApplyCriteria())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Voucher applyCriteria apply error, VoucherCriteria : {}", voucher.getApplyCriteria());
                }
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHER_APPLY_CRITERIA, null);
            }
            // TODO 优惠券类型=数量时校验逻辑

            // 优惠券类型=金额时校验
            if (VoucherConstants.PDM_VOUCHER_TYPE_AM.equals(voucher.getType())) {
                // 校验优惠券使用规则
                if (VoucherConstants.PDM_APPLY_ON_ICLD.equals(voucher.getUseCriteria())) {
                    voucherOrderAmtVali(voucher.getOperation(), voucher.getOrderAmount(), afterAmt);
                }
                if (VoucherConstants.PDM_APPLY_ON_ECLD.equals(voucher.getUseCriteria())) {
                    voucherOrderAmtVali(voucher.getOperation(), voucher.getOrderAmount(), beforeAmt);
                }
            }
            if (VoucherConstants.PDM_USAGE_MODE_NSTAC.equals(voucher.getUsageMode())) {
                nonOverlappingQty = nonOverlappingQty.add(BigDecimal.ONE);
            }
            // TODO 折扣类型=赠品时校验逻辑

            // 折扣类型=固定金额时校验
            if (VoucherConstants.PDM_DISCOUNT_TYPE_FIX.equals(voucher.getDiscountType())) {
                // 总优惠额
                discountAmt = discountAmt.add(voucher.getDiscountValue());
            }
            // 折扣类型=折扣时校验
            if (VoucherConstants.PDM_DISCOUNT_TYPE_PERCE.equals(voucher.getDiscountType())) {
                BigDecimal discountPercent = voucher.getDiscountValue().divide(SystemProfileConstants.ONE_HUNDRED,
                        SystemProfileConstants.BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN);
                // (1-折扣)*税后优惠总额
                discountAmt = discountAmt.add(afterAmt.multiply(discountPercent));
            }
        }
        // TODO 校验行上赠品数量

        // 校验使用优惠券后优惠金额
        if (!orderActrualAmtVali(salesOrder, discountAmt)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Sales order actrual pay amount is error, orderHeaderId : {}.",
                        new Object[] { salesOrder.getHeaderId() });
            }
            throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHER_DISCOUNT_AMT_ERROR, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Voucher apply validate is OK. orderHeaderId : {}", new Object[] { salesOrder.getHeaderId() });
        }
        // 优惠金额
        salesOrder.setDiscountAmt(discountAmt);
        // 事务处理
        if (isDoTrx) {
            voucherQtyTrxProcess(request, salesOrder, voucherQtyMap, VoucherConstants.TRX_SOURCE_TYPE_ORDER_HEAD);
        }
        return salesOrder;
    }

    /**
     * 校验优惠券所分配数量是否足够.
     * 
     * @param voucher
     *            优惠券对象
     * @param salesOrder
     *            销售订单详情
     * @param voucherQtyMap
     *            记录所应用的每种优惠券数量
     * @throws CommVoucherException
     *             优惠券统一异常
     * @return false-校验错误，true-校验正确
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private boolean voucherAssignQtyVali(Voucher voucher, SalesOrder salesOrder, Map<Long, Long> voucherQtyMap)
            throws CommVoucherException {
        VoucherAssign voucherAssign = new VoucherAssign();
        voucherAssign.setVoucherId(voucher.getVoucherId());
        voucherAssign.setAssignType(voucher.getAppScope());
        // 功能原因暂时注释
        // voucherAssign.setMarketId(salesOrder.getMarketId());
        if (VoucherConstants.VOUCHER_ASSIGN_TYPE_SALES.equals(voucher.getAppScope())) {
            voucherAssign.setSalesOrgId(voucher.getSalesOrgId());
        }
        if (VoucherConstants.VOUCHER_ASSIGN_TYPE_MEM.equals(voucher.getAppScope())) {
            voucherAssign.setMemberId(salesOrder.getMemberId());
        }
        List<VoucherAssign> voucherAssigns = voucherAssignMapper.selectVoucherAssign(voucherAssign);
        if (voucherAssigns == null || voucherAssigns.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("The member not assign the voucher,voucherName : {}.", new Object[] { voucher.getName() });
            }
            return false;
        }
        Long assignQty = voucherAssigns.get(0).getQuantity(); // 分配的优惠券数量
        if (assignQty == null) {
            assignQty = 0L;
        }
        // 判断voucherId是否存在于map中
        if (voucherQtyMap.containsKey(voucher.getVoucherId())) {
            voucherQtyMap.put(voucher.getVoucherId(), voucherQtyMap.get(voucher.getVoucherId()) + (1L));
        } else {
            voucherQtyMap.put(voucher.getVoucherId(), 1L);
        }
        if (assignQty.compareTo(voucherQtyMap.get(voucher.getVoucherId())) < 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Member owned voucher quantity not enough, memberId : {}, voucherId : {}",
                        new Object[] { salesOrder.getMemberId(), voucher.getVoucherId() });
            }
            return false;
        }
        return true;
    }

    /**
     * 优惠券类型是”金额“时，校验优惠券规则是否正确.
     * 
     * @param operation
     *            运算符
     * @param voucherLimitAmt
     *            优惠券额度
     * @param orderAmt
     *            订单金额
     * @throws CommVoucherException
     *             优惠券统一异常
     */
    private void voucherOrderAmtVali(String operation, BigDecimal voucherLimitAmt, BigDecimal orderAmt)
            throws CommVoucherException {
        switch (operation) {
        case VoucherConstants.PDM_CALCULATE_OPERATOR_MT:
            if (orderAmt.compareTo(voucherLimitAmt) <= 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("OrderAmount not match the operating conditions,OrderAmount : {},voucherLimitAmt : {}",
                            new Object[] { orderAmt, voucherLimitAmt });
                }
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHER_ORDER_AMOUNT_ERROR, null);
            }
            break;
        case VoucherConstants.PDM_CALCULATE_OPERATOR_MOE:
            if (orderAmt.compareTo(voucherLimitAmt) < 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("OrderAmount not match the operating conditions,OrderAmount : {},voucherLimitAmt : {}",
                            new Object[] { orderAmt, voucherLimitAmt });
                }
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHER_ORDER_AMOUNT_ERROR, null);
            }
            break;
        case VoucherConstants.PDM_CALCULATE_OPERATOR_LT:
            if (orderAmt.compareTo(voucherLimitAmt) >= 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("OrderAmount not match the operating conditions,OrderAmount : {},voucherLimitAmt : {}",
                            new Object[] { orderAmt, voucherLimitAmt });
                }
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHER_ORDER_AMOUNT_ERROR, null);
            }
            break;
        case VoucherConstants.PDM_CALCULATE_OPERATOR_LOE:
            if (orderAmt.compareTo(voucherLimitAmt) > 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("OrderAmount not match the operating conditions,OrderAmount : {},voucherLimitAmt : {}",
                            new Object[] { orderAmt, voucherLimitAmt });
                }
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHER_ORDER_AMOUNT_ERROR, null);
            }
            break;
        default:
            break;
        }
    }

    /**
     * 订单总优惠金额校验.
     * 
     * @param salesOrder
     *            销售订单详情
     * @param discountAmt
     *            计算后优惠金额
     * @throws CommVoucherException
     *             优惠券统一异常
     */
    private boolean orderActrualAmtVali(SalesOrder salesOrder, BigDecimal discountAmt) throws CommVoucherException {
        // 运费
        BigDecimal shippingFee = BigDecimal.ZERO;
        // 调整金额总和
        BigDecimal adjustmentAmt = BigDecimal.ZERO;
        // 税后金额
        BigDecimal afterAmt = salesOrder.getOrderAmt();
        // 实付款
        BigDecimal actrualPayAmt = salesOrder.getActrualPayAmt();
        if (OrderConstants.ORDER_DELIVERY_SHIPP.equals(salesOrder.getDeliveryType())) {
            shippingFee = salesOrder.getLogistics().getShippingFee();
        }
        // 是否有金额调整
        if (salesOrder.getAdjustMents() == null || salesOrder.getAdjustMents().isEmpty()) {
            adjustmentAmt = BigDecimal.ZERO;
        } else {
            for (SalesAdjustment salesAdjustment : salesOrder.getAdjustMents()) {
                adjustmentAmt = adjustmentAmt.add(salesAdjustment.getAdjustmentAmount());
            }
        }

        // TODO: 待修改为整个订单的金额.
        // 订单总优惠金额
        BigDecimal discountValue = afterAmt.add(shippingFee).subtract(actrualPayAmt).add(adjustmentAmt);
        if (discountValue.compareTo(discountAmt) != 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Discount amount validate error! Actrual discount amount : {}; "
                        + "After validate discount amount : {}", new Object[] { discountValue, discountAmt });
            }
            return false;
        }
        return true;
    }

    /**
     * 优惠券事务处理.
     * 
     * @param request
     *            统一上下文
     * @param salesOrder
     *            订单详情
     * @param voucherQtyMap
     *            优惠券id:使用数量集合
     * @param trxSourceType
     *            事务来源类型
     * @throws CommVoucherException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void voucherQtyTrxProcess(IRequest request, SalesOrder salesOrder, Map<Long, Long> voucherQtyMap,
            String trxSourceType) throws CommVoucherException {
        List<VoucherTransaction> voucherTrxs = new ArrayList<VoucherTransaction>();
        long voucherId;
        Iterator<Long> it = voucherQtyMap.keySet().iterator();
        while (it.hasNext()) {
            voucherId = it.next();
            VoucherTransaction voucherTrx = new VoucherTransaction();
            voucherTrx.setMarketId(salesOrder.getMarketId());
            voucherTrx.setSalesOrgId(salesOrder.getSalesOrgId());
            voucherTrx.setMemberId(salesOrder.getMemberId());
            voucherTrx.setTrxType(VoucherConstants.TRX_TYPE_APPLY);
            voucherTrx.setTrxSourceType(trxSourceType);
            voucherTrx.setTrxSourceKey(String.valueOf(salesOrder.getHeaderId()));
            voucherTrx.setTrxSourceReference(salesOrder.getOrderNumber());
            voucherTrx.setTrxDate(new Date());
            voucherTrx.setVoucherId(voucherId);
            // 使用优惠券则事务处理数量为负数
            voucherTrx.setTrxQty(-voucherQtyMap.get(voucherId));
            voucherTrxs.add(voucherTrx);
        }
        voucherQuantityTrxService.processVoucherQuantityTrx(request, voucherTrxs);
    }

}
