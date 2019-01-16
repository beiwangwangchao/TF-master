/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.member.dto.MemAccountingTrx;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesVoucher;
import com.lkkhpg.dsis.common.promotion.dto.VoucherTransaction;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员资产冲销操作service接口.
 * 
 * @author gulin
 */
public interface IMemWriteOffService extends ProxySelf<IMemWriteOffService> {

    /**
     * 根据支付订单头Id生成会员资产冲销dto集合.
     * 
     * @param request
     *            统一上下文.
     * @param salesOrder
     *            订单信息.
     * @param orderPayments 
     * @return 会员资产冲销dto集合.
     */
    List<MemAccountingTrx> createTrxByOrderPayment(IRequest request, SalesOrder salesOrder, List<OrderPayment> orderPayments);

    /**
     * 根据退款订单头Id生成会员资产冲销dto集合.
     * 
     * @param request
     *            统一上下文.
     * @param salesOrder
     *            订单信息.
     * @param marketId
     *            所属市场id.
     * @return 会员资产冲销dto集合.
     */
    List<MemAccountingTrx> createTrxByOrderInvalid(IRequest request, SalesOrder salesOrder, Long marketId);

    /**
     * 订单失效后优惠券冲销.
     * 
     * @param request
     *            统一上下文
     * @param salesOrder
     *            订单详情
     * @param salesVouchers
     *            优惠券使用信息集合
     * @return 优惠券事务处理对象集合
     */
    List<VoucherTransaction> createVoucherTrxByOrderInvalid(IRequest request, SalesOrder salesOrder,
            List<SalesVoucher> salesVouchers);
}
