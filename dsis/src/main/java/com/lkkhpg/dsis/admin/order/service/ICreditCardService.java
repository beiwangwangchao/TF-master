/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service;

import java.io.IOException;

import com.lkkhpg.dsis.admin.order.exception.OrderException;
import com.lkkhpg.dsis.common.order.dto.CreditCard;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 信用卡接口服务.
 * 
 * @author wuyichu
 */
public interface ICreditCardService {

    /**
     * 提交信用卡信息.
     * 
     * @param request
     *            请求的基础信息
     * @param creditCard
     *            信用卡数据
     * @return 提交后的信用卡信息
     * @throws OrderException
     *             写入或更新失败时抛出
     * @throws IOException I/O异常
     */
    CreditCard submit(IRequest request, @StdWho CreditCard creditCard) throws OrderException, IOException;

}
