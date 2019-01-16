/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 销售订单地址service.
 * 
 * @author wuyichu
 */
public interface ISalesSitesService extends ProxySelf<ISalesSitesService> {

    /**
     * 提交销售订单地址.
     * 
     * @param request
     *            统一上下文
     * @param salesSites
     *            销售订单地址DTO
     * @return 处理过后的销售订单地址
     * @throws CommOrderException
     *             地址类型不是账单或者配送时抛出.
     */
    SalesSites submit(IRequest request, @StdWho SalesSites salesSites) throws CommOrderException;

    /**
     * 通过主键删除订单地址.
     * 
     * @param request
     *            统一上下文
     * @param siteId
     *            主键
     * @return 删除的行数
     */
    int deleteSite(IRequest request, Long siteId);

    /**
     * 通过订单头查询订单的地址信息.
     * 
     * @param request
     *            统一上下文
     * @param headerId
     *            订单头id
     * @param isAutoship
     *            是否autoship Y/N
     * @return 订单的地址信息集合
     */
    List<SalesSites> getSitesByHeaderId(IRequest request, Long headerId, String isAutoship);
}
