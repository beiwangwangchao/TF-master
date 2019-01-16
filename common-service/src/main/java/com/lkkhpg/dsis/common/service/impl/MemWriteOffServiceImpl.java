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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.VoucherConstants;
import com.lkkhpg.dsis.common.member.dto.MemAccountingTrx;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesVoucher;
import com.lkkhpg.dsis.common.order.mapper.OrderPaymentMapper;
import com.lkkhpg.dsis.common.promotion.dto.VoucherTransaction;
import com.lkkhpg.dsis.common.service.IMemWriteOffService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员资产冲销操作service接口实现类.
 * 
 * @author gulin
 */
@Service
@Transactional
public class MemWriteOffServiceImpl implements IMemWriteOffService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrderPaymentMapper orderPaymentMapper;

    private SalesOrder salesOrder;

    private Member member;

    public List<MemAccountingTrx> createTrxByOrderPayment(IRequest request, SalesOrder salesOrder,
            List<OrderPayment> orderPayments) {
        List<MemAccountingTrx> trxs = new ArrayList<MemAccountingTrx>();
        String orderType = salesOrder.getOrderType();
        this.salesOrder = salesOrder;
        if (OrderConstants.ORDER_TYPE_CONP.equals(orderType) || OrderConstants.ORDER_TYPE_DIRP.equals(orderType)
                || OrderConstants.ORDER_TYPE_STFP.equals(orderType)) {
            return trxs;
        }
        Long memberId = salesOrder.getMemberId();
        member = memberMapper.selectByPrimaryKey(memberId);
        // 获取该订单支付调整数据，若包含exchange_balance,remaining_balance,则执行事务处理
        // List<SalesAdjustment> salesAdjustments = salesOrder.getAdjustMents();
        // 获取订单商品详情，以计算pv值，以及计算积分兑换时订单所需总积分
        List<SalesLine> salesLines = salesOrder.getLines();
        BigDecimal pvNum = BigDecimal.ZERO;
        BigDecimal pointNum = BigDecimal.ZERO;
        for (SalesLine s : salesLines) {
            pvNum = pvNum.add(s.getPv().multiply(s.getQuantity()));
            if (s.getRedeemPoint() == null) {
                s.setRedeemPoint(BigDecimal.ZERO);
            }
            pointNum = pointNum.add(s.getRedeemPoint());
        }
        // 非积分兑换
        trxs = new ArrayList<MemAccountingTrx>();
        // for (SalesAdjustment temp : salesAdjustments) {
        // if (null != temp &&
        // (temp.getAdjustmentType().equals(OrderConstants.ADJUSTMENT_TYPE_EB)
        // ||
        // temp.getAdjustmentType().equals(OrderConstants.ADJUSTMENT_TYPE_RB)))
        // {
        // trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_PAY,
        // temp.getAdjustmentType(),
        // temp.getAdjustmentAmount()));
        // }
        // }
        // 自动订货单创建账务处理记录
        if (salesOrder.getSalesPoints() != null && salesOrder.getSalesPoints().compareTo(BigDecimal.ZERO) > 0) {
            trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_VOID, MemberConstants.ACCOUNTING_TYPE_SP,
                    salesOrder.getSalesPoints()));
        }

        for (OrderPayment orderPayment : orderPayments) {
            if (OrderConstants.PAY_METHOD_MODIFY_EBPAY.equals(orderPayment.getPaymentMethod())) {
                trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_PAY, MemberConstants.ACCOUNTING_TYPE_EB,
                        BigDecimal.ZERO.subtract(orderPayment.getPaymentAmount())));
            }
            if (OrderConstants.PAY_METHOD_MODIFY_RBPAY.equals(orderPayment.getPaymentMethod())) {
                trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_PAY, MemberConstants.ACCOUNTING_TYPE_RB,
                        BigDecimal.ZERO.subtract(orderPayment.getPaymentAmount())));
            }
        }
        if (!OrderConstants.ORDER_TYPE_RDEM.equals(orderType)) {
            if (pvNum.compareTo(BigDecimal.ZERO) > 0) {
                trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_PAY, MemberConstants.ACCOUNTING_TYPE_PV, pvNum));
            }
            return trxs;
        } else {
            trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_PAY, MemberConstants.ACCOUNTING_TYPE_SP,
                    pointNum.negate()));
            return trxs;
        }
    }

    public List<MemAccountingTrx> createTrxByOrderInvalid(IRequest request, SalesOrder salesOrder, Long marketId) {
        List<MemAccountingTrx> trxs = new ArrayList<MemAccountingTrx>();
        String orderType = salesOrder.getOrderType();
        this.salesOrder = salesOrder;
        if (OrderConstants.ORDER_TYPE_CONP.equals(orderType) || OrderConstants.ORDER_TYPE_DIRP.equals(orderType)
                || OrderConstants.ORDER_TYPE_STFP.equals(orderType)) {
            return trxs;
        }
        Long memberId = salesOrder.getMemberId();
        member = memberMapper.selectByPrimaryKey(memberId);
        // 定义支付总金额，若所属市场为马来地区，则退回到Remaining Balance，需要去除支付信息中EB,RB,ECUP代扣金额
        BigDecimal amounts = BigDecimal.ZERO;
        // // 获取该订单支付调整数据，若包含exchange_balance,remaining_balance,则执行事务处理
        // List<SalesAdjustment> salesAdjustments = salesOrder.getAdjustMents();
        // 获取订单商品详情，以计算pv值，以及计算积分兑换时订单所需总积分
        List<SalesLine> salesLines = salesOrder.getLines();
        BigDecimal pvNum = new BigDecimal(0);
        BigDecimal pointNum = new BigDecimal(0);
        for (SalesLine s : salesLines) {
            pvNum = pvNum.add(s.getPv().multiply(s.getQuantity()));
            if (s.getRedeemPoint() == null) {
                s.setRedeemPoint(BigDecimal.ZERO);
            }
            pointNum = pointNum.add(s.getRedeemPoint());
        }
        // 将支付调整中exchange_balance和remaining_balance生成事务dto放入集合中
        trxs = new ArrayList<MemAccountingTrx>();
        // for (SalesAdjustment temp : salesAdjustments) {
        // if (null != temp &&
        // (OrderConstants.ADJUSTMENT_TYPE_EB.equals(temp.getAdjustmentType())
        // ||
        // OrderConstants.ADJUSTMENT_TYPE_RB.equals(temp.getAdjustmentType())))
        // {
        // trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_VOID,
        // temp.getAdjustmentType(),
        // temp.getAdjustmentAmount().negate()));
        // }
        // }

        // 自动订货单创建账务处理记录
        if (salesOrder.getSalesPoints() != null && salesOrder.getSalesPoints().compareTo(BigDecimal.ZERO) > 0) {
            trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_PAY, MemberConstants.ACCOUNTING_TYPE_SP,
                    BigDecimal.ZERO.subtract(salesOrder.getSalesPoints())));
        }

        // 获取支付信息中的exchange_balance和remaining_balance生成事务dto放入集合中
        List<OrderPayment> orderPayments = orderPaymentMapper.selectByHeaderId(salesOrder.getHeaderId());
        for (OrderPayment orderPayment : orderPayments) {
            if (OrderConstants.PAY_METHOD_MODIFY_EBPAY.equals(orderPayment.getPaymentMethod())) {
                trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_PAY, MemberConstants.ACCOUNTING_TYPE_EB,
                        orderPayment.getPaymentAmount()));
            } else if (OrderConstants.PAY_METHOD_MODIFY_RBPAY.equals(orderPayment.getPaymentMethod())) {
                trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_PAY, MemberConstants.ACCOUNTING_TYPE_RB,
                        orderPayment.getPaymentAmount()));
            } else {
                if (!OrderConstants.PAYMENT_METHOD_ECUP.equals(orderPayment.getPaymentMethod())) {
                    amounts = amounts.add(orderPayment.getPaymentAmount());
                }
            }
        }

        // 马来西亚地区，非积分兑换，订单失效
        if (!OrderConstants.ORDER_TYPE_RDEM.equals(orderType) && 1 == marketId) {
            if (pvNum.compareTo(BigDecimal.ZERO) > 0) {
                trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_VOID, MemberConstants.ACCOUNTING_TYPE_PV,
                        pvNum.negate()));
            }
            // 实际支付金额退回到remaining balance
            trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_VOID, MemberConstants.ACCOUNTING_TYPE_RB, amounts));
        } else if (OrderConstants.ORDER_TYPE_RDEM.equals(orderType) && 1 == marketId) {
            trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_VOID, MemberConstants.ACCOUNTING_TYPE_SP, pointNum));
            // 实际支付金额=运费退回到remaining balance
            trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_VOID, MemberConstants.ACCOUNTING_TYPE_RB, amounts));
        } else if (!OrderConstants.ORDER_TYPE_RDEM.equals(orderType) && 1 != marketId) {
            if (pvNum.compareTo(BigDecimal.ZERO) > 0) {
                trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_VOID, MemberConstants.ACCOUNTING_TYPE_PV,
                        pvNum.negate()));
            }
        } else if (OrderConstants.ORDER_TYPE_RDEM.equals(orderType) && 1 != marketId) {
            trxs.add(getAccountingTrx(MemberConstants.TRX_TYPE_VOID, MemberConstants.ACCOUNTING_TYPE_SP, pointNum));
        }

        return trxs;
    }

    private MemAccountingTrx getAccountingTrx(String trxType, String accountingType, BigDecimal account) {
        MemAccountingTrx trx = new MemAccountingTrx();
        // 组合冲销事务dto
        trx.setMemberId(member.getMemberId());
        trx.setCompanyId(member.getCompanyId());
        trx.setSalesOrgId(salesOrder.getSalesOrgId());
        trx.setTrxDate(new Date());
        trx.setTrxType(trxType);
        trx.setTrxSourceType(MemberConstants.TRX_SOURCE_TYPE_ORDER);
        trx.setTrxSourceId(salesOrder.getHeaderId()); // 设置事务来源ID
        trx.setTrxSourceLineId(salesOrder.getHeaderId());
        trx.setAccountingType(accountingType);
        trx.setTrxValue(account);
        return trx;
    }

    @Override
    public List<VoucherTransaction> createVoucherTrxByOrderInvalid(IRequest request, SalesOrder salesOrder,
            List<SalesVoucher> salesVouchers) {
        // 优惠券事务处理
        Map<Long, Long> voucherQtyMap = new HashMap<Long, Long>();
        VoucherTransaction voucherTrx = new VoucherTransaction();
        List<VoucherTransaction> voucherTrxs = new ArrayList<VoucherTransaction>();
        voucherTrx.setSalesOrgId(salesOrder.getSalesOrgId());
        voucherTrx.setMemberId(salesOrder.getMemberId());
        voucherTrx.setTrxType(VoucherConstants.TRX_TYPE_BACK);
        voucherTrx.setTrxSourceType(VoucherConstants.TRX_SOURCE_TYPE_ORDER_HEAD);
        voucherTrx.setTrxSourceKey(String.valueOf(salesOrder.getHeaderId()));
        voucherTrx.setTrxSourceReference(salesOrder.getOrderNumber());
        voucherTrx.setTrxDate(new Date());
        for (SalesVoucher salesVoucher : salesVouchers) {
            if (voucherQtyMap.containsKey(salesVoucher.getVoucherId())) {
                voucherQtyMap.put(salesVoucher.getVoucherId(), voucherQtyMap.get(salesVoucher.getVoucherId()) + (1L));
            } else {
                voucherQtyMap.put(salesVoucher.getVoucherId(), 1L);
            }
        }
        Iterator<Long> it = voucherQtyMap.keySet().iterator();
        while (it.hasNext()) {
            Long voucherId = it.next();
            voucherTrx.setVoucherId(voucherId);
            voucherTrx.setTrxQty(voucherQtyMap.get(voucherId));
            voucherTrxs.add(voucherTrx);
        }
        return voucherTrxs;
    }

}
