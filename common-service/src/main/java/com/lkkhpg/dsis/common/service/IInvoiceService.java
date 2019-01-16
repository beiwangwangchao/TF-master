/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.order.dto.Invoice;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * . 发票接口.
 * 
 * @author li.peng@hand-china.com
 *
 */
public interface IInvoiceService extends ProxySelf<IInvoiceService> {

    /**
     * 创建发票.
     * 
     * @param request
     *            统一上下文.
     * @param orderId
     *            销售订单ID.
     * @return Invoice 发票
     * @throws CommOrderException
     *             订单统一异常
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    Invoice createInvoice(IRequest request, Long orderId) throws CommOrderException, CommSystemProfileException;

    /**
     * 查询符合条件的发票信息.
     * 
     * @param request
     *            统一上下文.
     * @param invoice
     *            发票查询条件.
     * @return 返回查询结果.
     */
    List<Invoice> queryInvoice(IRequest request, Invoice invoice);

    /**
     * 根据订单Id查询发票，如果发票不能存在，则重新生成发票.
     * 
     * @param request
     *            统一上下文
     * @param orderId
     *            订单ID
     * @return 发票信息
     * 
     * @throws CommOrderException
     *             订单统一异常
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    Invoice printInvoice(IRequest request, Long orderId) throws CommOrderException, CommSystemProfileException;
}
