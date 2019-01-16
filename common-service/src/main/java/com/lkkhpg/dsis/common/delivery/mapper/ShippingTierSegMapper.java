/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.delivery.dto.ShippingTierSeg;

/**
 * @author huangjiajing
 */
public interface ShippingTierSegMapper {
    int deleteByPrimaryKey(ShippingTierSeg record);

    int insert(ShippingTierSeg shippingTierDtlSeg);

    ShippingTierSeg selectByPrimaryKey(ShippingTierSeg record);

    List<ShippingTierSeg> selectShippingTierDtlSegs(ShippingTierSeg record);

    int updateByPrimaryKey(ShippingTierSeg record);
    
    int queryCount(ShippingTierSeg record);

}