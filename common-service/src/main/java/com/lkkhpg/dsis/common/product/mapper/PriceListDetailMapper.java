/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.product.dto.PriceListDetail;

/**
 * 商品行价格mapper.
 * 
 * @author wuyichu
 */
public interface PriceListDetailMapper {

    int deleteByPrimaryKey(Long listDetailId);

    int insert(PriceListDetail record);

    int insertSelective(PriceListDetail record);

    PriceListDetail selectByPrimaryKey(Long listDetailId);

    int updateByPrimaryKeySelective(PriceListDetail record);

    int updateByPrimaryKey(PriceListDetail record);

    List<PriceListDetail> selectByItemId(@Param("itemId") Long itemId, @Param("currency") String currency,
            @Param("uomCode") String uomCode, @Param("salesOrgId") Long salesOrgId);

    List<PriceListDetail> selectByItem(@Param("itemId") Long itemId);
    
    PriceListDetail selectByItemAndSalesOrg(HashMap<String, Object> map);
    
    PriceListDetail selectByItemAndSalesOrgAndCurrentDate(HashMap<String, Object> map);

    PriceListDetail selectPriceForOrder(@Param("itemId") Long itemId, @Param("currency") String currency,
                                        @Param("uomCode") String uomCode, @Param("salesOrgId") Long salesOrgId,@Param("priceType") String priceType);

}