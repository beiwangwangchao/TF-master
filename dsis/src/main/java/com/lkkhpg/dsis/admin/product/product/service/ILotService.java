/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.service;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.dto.StockTrxDetail;
import com.lkkhpg.dsis.common.product.dto.Lot;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 批次接口.
 * 
 * @author mclin
 */
public interface ILotService extends ProxySelf<ILotService> {
    /**
     * 根据库存组织ID批次信息（入库）.
     * 
     * @param requestContext
     *            请求上下文
     * @param orgId
     *            库存组织ID
     * @return Lots 批次Dto
     */
    List<Lot> queryLotsIn(IRequest requestContext, Long orgId);

    /**
     * 根据条件获得具体某个批次.
     * 
     * @param requestContext
     *            请求上下文
     * @param organizationId
     *            库存组织ID
     * @param stockTrxDetail
     *            出入库事务明细Dto
     * @return Lot 批次Dto
     */
    Lot getLot(IRequest requestContext, Long organizationId, StockTrxDetail stockTrxDetail);

    /**
     * 根据库存组织ID(出库).
     * 
     * @param requestContext
     *            请求上下文
     * @param orgId
     *            库存组织ID
     * @return Lots 批次Dto
     */
    List<Lot> queryLotsOut(IRequest requestContext, Long orgId);

    /**
     * 根据库存组织ID,商品ID，出库类型以及批次号自动带出批次到期日查询.
     * 
     * @param requestContext
     *            请求上下文
     * @param lot
     *            批次Dto
     * @return Lot 批次Dto
     */
    Lot queryLotWithExpiryDate(IRequest requestContext, Lot lot);
}
