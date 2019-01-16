/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.product.mapper;

import com.lkkhpg.dsis.common.product.dto.PriceList;

/**
 * 商品头价格信息mapper.
 * 
 * @author wuyichu
 */
public interface PriceListMapper {
    int deleteByPrimaryKey(Long priceListId);

    int insert(PriceList record);

    int insertSelective(PriceList record);

    PriceList selectByPrimaryKey(Long priceListId);

    int updateByPrimaryKeySelective(PriceList record);

    int updateByPrimaryKey(PriceList record);

    Long getPriceListIdByOrgId(Long salesOrgId);
}