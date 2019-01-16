/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.OrderPayTransaction;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * @author runbai.chen
 */
public interface IOrderPayTransactionService extends ProxySelf<IOrderPayTransactionService> {
    /**
     * 生成事务code.
     * 
     * @param request
     *            上下文
     * @param orderPayTransaction
     *            事务DTO
     * @return 事务
     * @throws CommOrderException 
     */
    OrderPayTransaction generateTransaction(IRequest request, @StdWho OrderPayTransaction orderPayTransaction)
            throws CommOrderException;

    /**
     * 启动银行支付事务.
     * 
     * @param request
     *            上下文
     * @param orderPayTransaction
     *            事务DTO
     * @return 事务
     */

    OrderPayTransaction lauchTransaction(IRequest request, @StdWho OrderPayTransaction orderPayTransaction);

    /**
     * 结束银行支付事务.
     * 
     * @param request
     *            上下文
     * @param orderPayTransaction
     *            事务DTO
     * @return 事务
     */

    OrderPayTransaction finishTransaction(IRequest request, @StdWho OrderPayTransaction orderPayTransaction);

}
