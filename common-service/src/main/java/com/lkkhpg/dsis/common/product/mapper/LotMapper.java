/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.inventory.dto.StockTrxDetail;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.dto.Lot;

/**
 * 批次Mapper接口.
 * 
 * @author mclin
 */
public interface LotMapper {

    int insert(Lot record);

    int insertSelective(Lot record);

    List<Lot> queryLots(@Param("org") SpmInvOrganization org, @Param("itm") InvItem itm);

    Lot getLot(@Param("organizationId") Long organizationId, @Param("stockTrxDetail") StockTrxDetail stockTrxDetail);
    
    List<Lot> queryLotsByItemAndOrg(Lot lot);
    
    Lot getLotWhenStkin(@Param("orgId") Long orgId, @Param("lotNumber") String lotNumber, @Param("itemId") Long itemId);
    
    Lot getLotWhenStkot(@Param("orgId") Long orgId, @Param("lotNumber") String lotNumber, @Param("itemId") Long itemId);
    
    List<Lot> queryLotsIn(@Param("orgId") Long orgId);
    
    List<Lot> queryLotsOut(@Param("orgId") Long orgId);
    
}