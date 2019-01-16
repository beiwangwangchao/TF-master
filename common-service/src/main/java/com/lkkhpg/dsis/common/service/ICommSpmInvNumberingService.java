/*
 *
 */
package com.lkkhpg.dsis.common.service;

import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 发票编号发放接口.
 * 
 * @author li.peng@hand-china.com
 *
 */
public interface ICommSpmInvNumberingService extends ProxySelf<ICommSpmInvNumberingService> {

    /**
     * 创建发票编号.
     * 
     * @param request
     *            请求上下文
     * @param orderId
     *            销售订单编号
     * @return 发票编号
     * @throws CommSystemProfileException
     *             系统配置统一异常.
     */
    String createInvoiceNumber(IRequest request, Long orderId) throws CommSystemProfileException;
}
