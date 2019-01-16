/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.mws.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.delivery.dto.ShippingTier;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTierSeg;

/**
 * 物流信息mapper.
 * 
 * @author guanghui.liu
 */
public interface MwsShippingTierMapper {

    List<ShippingTier> selectShippingTierByLocation(@Param("shippingTierSeg") ShippingTierSeg shippingTierSeg,
            @Param("salesOrgId") Long salesOrgId, @Param("currencyCode") String currencyCode,@Param("apptype") String apptype);

}