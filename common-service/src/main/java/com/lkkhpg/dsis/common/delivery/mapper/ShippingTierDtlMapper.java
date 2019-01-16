/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.delivery.dto.ShippingTierDtl;

/**
 * @author huangjiajing
 */
public interface ShippingTierDtlMapper {
    int deleteByPrimaryKey(ShippingTierDtl shippingTierDtl);

    int insert(ShippingTierDtl shippingTierDtl);

    ShippingTierDtl selectByPrimaryKey(ShippingTierDtl shippingTierDtl);

    List<ShippingTierDtl> selectShippingTierDtls(ShippingTierDtl shippingTierDtl);

    int updateByPrimaryKey(ShippingTierDtl shippingTierDtl);

    int queryCount(ShippingTierDtl shippingTierDtl);

}