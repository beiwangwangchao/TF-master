/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductCategoriesResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListData;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListPriceData;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListRequest;

/**
 * 产品类别Mapper.
 * 
 * @author fengwanjun
 */
public interface GetProductListMapper {
    
    List<GetProductCategoriesResponse> selectTopCategory(String lang);
    
    List<GetProductCategoriesResponse> selectChilendCategory(Map<String, Object> map);

    List<GetProductListData> selectProductList(GetProductListRequest getProductListRequest);

    List<GetProductListPriceData> selectPriceByItemId(Map<String, Object> priceMap);
    
    List<GetProductListData> selectAllItemQuantityBySalesOrgId(GetProductListRequest getProductListRequest);
    
    List<GetProductListData> selectProductsBySalesOrg(GetProductListRequest getProductListRequest);
}