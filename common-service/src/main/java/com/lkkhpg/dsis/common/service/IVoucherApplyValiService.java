/*
 *
 */
package com.lkkhpg.dsis.common.service;

import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 优惠券使用校验Service.
 * 
 * @author houmin
 *
 */
public interface IVoucherApplyValiService extends ProxySelf<IVoucherApplyValiService> {

    /**
     * 优惠券使用校验.
     * 
     * @param request
     *            统一上下文
     * @param salesOrder
     *            销售订单详情
     * @param isDoTrx
     *            是否进行事务处理
     * @param 销售订单详情
     */
    SalesOrder voucherApplyValidator(IRequest request, SalesOrder salesOrder, boolean isDoTrx)
            throws CommVoucherException;

}
