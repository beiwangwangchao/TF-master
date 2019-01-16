/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.math.BigDecimal;

import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 挑库规则匹配Service.
 * 
 * @author chenjingxiong
 */
public interface IDeliveryInvOrgMatchService extends ProxySelf<IDeliveryInvOrgMatchService> {

    /**
     * 匹配库存组织ID.
     * 
     * @param request
     *            统一上下文.
     * @param salesOrgId
     *            销售区域ID.
     * @param deliveryType
     *            配送类型.
     * @param deliveryLocationId
     *            配送地址.
     * @return 匹配到的库存组织ID.
     */
    // Long matchInvOrg(IRequest request, Long salesOrgId, String deliveryType,
    // Long deliveryLocationId);

    /**
     * 匹配库存组织ID.
     * 
     * @param request
     *            统一上下文.
     * @param salesOrgId
     *            销售区域ID.
     * @param deliveryType
     *            配送类型.
     * @param deliverySite
     *            配送地址.
     * @return 匹配到的库存组织ID.
     */

    Long matchInvOrg(IRequest request, Long salesOrgId, String deliveryType, SalesSites deliverySite);

    /**
     * 
     * 匹配快递类型订单的库存组织ID.
     * 
     * @param request
     *            统一上下文.
     * @param salesOrgId
     *            销售区域ID.
     * @param deliverySite
     *            配送地址.
     * @param itemId
     *            商品编码
     * @param quantity
     *            数量
     * @return 匹配到的库存组织ID.匹配到的库存组织ID.
     */
    Long matchInvOrg(IRequest request, Long salesOrgId, SalesSites deliverySite, Long itemId, BigDecimal quantity);

}
