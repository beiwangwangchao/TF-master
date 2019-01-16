/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.promotion.dto.VoucherTransaction;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 优惠券数量事务处理.
 * 
 * @author houmin
 *
 */
public interface IVoucherQuantityTrxService extends ProxySelf<IVoucherQuantityTrxService> {

    /**
     * 优惠券数量事务处理.
     * 
     * @param request
     *            统一上下文
     * @param voucherTrxs
     *            优惠券事务对象列表
     * @throws CommVoucherException
     *             优惠券统一异常
     */
    void processVoucherQuantityTrx(IRequest request, List<VoucherTransaction> voucherTrxs) throws CommVoucherException;

    /**
     * 优惠券事务属性校验.
     * 
     * @param request
     *            统一上下文
     * @param voucherTrx
     *            优惠券事务对象
     * @throws CommVoucherException
     *             优惠券统一异常
     */
    void valiVoucherTrx(IRequest request, VoucherTransaction voucherTrx) throws CommVoucherException;

    /**
     * 校验优惠券是否可用.
     * 
     * @param request
     *            统一上下文
     * @param voucher
     *            优惠券事务对象
     * @throws CommVoucherException
     *             优惠券统一异常
     */
    void updateVoucherQty(IRequest request, VoucherTransaction voucherTrx) throws CommVoucherException;

}
