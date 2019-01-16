/*
 *
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickHead;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 挑库发放服务接口.
 * 
 * @author Zhaoqi
 *
 */
public interface ICommDeliveryPickService extends ProxySelf<ICommDeliveryPickService> {

    /**
     * 挑库-插入挑库单并创建发运单.
     * 
     * @param request
     *            统一上下文
     * @param deliveryPickHead
     *            挑库订单头信息.
     * @return 生成的发运单.
     * @throws CommDeliveryException
     *             发运统一异常.
     */
    List<Long> saveDeliveryPick(IRequest request, @StdWho DeliveryPickHead deliveryPickHead)
            throws CommDeliveryException;

}
