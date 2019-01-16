/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTier;

/**
 * @author huangjiajing
 */
public interface ShippingTierMapper {
    int deleteByPrimaryKey(ShippingTier record);

    int insert(ShippingTier record);

    int insertSelective(ShippingTier record);

    ShippingTier selectByPrimaryKey(ShippingTier record);

    List<ShippingTier> selectShippingTiers(ShippingTier shippingTier);

    int updateByPrimaryKeySelective(ShippingTier record);

    int updateByPrimaryKey(ShippingTier record);

    int queryTierCount(ShippingTier record);

    List<ShippingTier> selectByLocation(@Param("location") SpmLocation location, @Param("salesOrgId") Long salesOrgId,
            @Param("apptype") String apptype);

}