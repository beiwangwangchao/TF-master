/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickLine;

/**
 * 挑库单行接口.
 * 
 * @author Zhaoqi
 *
 */
public interface DeliveryPickLineMapper {

    /**
     * 挑库-挑库订单行表insert.
     * 
     * @param deliveryPickLine
     *            挑库行.
     * @return int影响行数.
     */
    int insertPickLine(DeliveryPickLine deliveryPickLine);

    /**
     * 挑库-挑库订单查询.
     * 
     * @param deliveryPickLine
     *            挑库行dto.
     * @return get挑库订单头集合.
     */
    List<DeliveryPickLine> selectOrder(DeliveryPickLine deliveryPickLine);

    /**
     * 挑库-订单行查询.
     * 
     * @param deliveryPickLine
     *            挑库行表dto.
     * @return get挑库订单行集合.
     */
    List<DeliveryPickLine> selectOrderLine(@Param(value = "orderNumber") String orderNumber,
            @Param(value = "default_inv_org_id") Long default_inv_org_id);

    /**
     * 挑库-库存量查询.
     * 
     * @param map
     *            根据组织id、商品id
     * @return 挑库行dto.
     */
    DeliveryPickLine queryInventory(@Param(value = "itemId") Long itemId, @Param(value = "invOrgId") Long invOrgId);

    /**
     * 挑库-库存组织查询.
     * 
     * @param orderNumber
     *            订单编号.
     * @return get组织id、name集合.
     */
    List<Map<String, Object>> queryInvOrgs(String orderNumber);

    /**
     * 订单类型不同获取供货组织选择不同.
     * 
     * @param deliveryPickLine
     *            挑库行表dto.
     * @return get挑库订单头集合.
     */
    List<DeliveryPickLine> selectInvOrgBySite(DeliveryPickLine deliveryPickLine);

    /**
     * 订单类型不同获取供货组织选择不同.
     * 
     * @param deliveryPickLine
     *            挑库行表dto.
     * @return get挑库订单头集合.
     */
    List<DeliveryPickLine> selectInvOrgByOrg(DeliveryPickLine deliveryPickLine);

    DeliveryPickLine getOrgByOrderNumber(DeliveryPickLine deliveryPickLine);
}