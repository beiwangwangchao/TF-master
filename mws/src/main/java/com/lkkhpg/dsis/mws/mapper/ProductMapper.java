/*
 *
 */

package com.lkkhpg.dsis.mws.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.mws.dto.Product;

/**
 * @author xiawang.liu
 */
public interface ProductMapper {

    /**
     * @param record
     *            商品信息
     * @param priceType 商品价格类型
     * @return 响应信息
     */
    List<Product> getDetailProductsByWhereClause(@Param("product") Product record, @Param("priceType") String priceType);

    /**
     * @param record
     *            商品信息
     * @param priceType 商品价格类型
     * @return 响应信息
     */
    List<Product> getDetailProductsByWhereClause2(@Param("product") Product record, @Param("priceType") String priceType);

    /**
     * @param record
     *            商品信息
     * @param priceType 价格类型
     * @return 响应信息
     */
    List<Product> getSimpleProductsByWhereClause(@Param("product") Product record, @Param("priceType") String priceType);

    /**
     * @param record
     *            商品信息
     * @param priceType 价格类型
     * @return 响应信息
     */
    List<Product> getSimpleProductsByWhereClause2(@Param("product") Product record, @Param("priceType") String priceType);

    /**
     * @param record
     *            商品信息
     * @param priceType 价格类型
     * @return 响应信息
     */
    List<Product> getProductsInCategory(@Param("product") Product record, @Param("priceType") String priceType);
    
    /**
     * @param record 
     *            商品信息
     * @return 响应信息
     */
    List<Product> getProductsCategoryNamesByItemId(Product record);
    
    /**
     * @param itemIds
     *            排除的产品ID 
     * @param saleOrgId
     *            销售组织Id 
     * @param currencyCode
     *            币种Code 
     * @param itemName
     *            商品名称 
     * @param priceType 价格类型
     * @param sysDate
     *            当前系统日期 
     * @return 响应信息
     */
    List<Product> getFastProductsByWhereClause(@Param("itemIds")List<Long> itemIds, 
            @Param("saleOrgId")Long saleOrgId, @Param("Code")String currencyCode, @Param("itemName")String itemName, @Param("priceType") String priceType);

    Integer getXtfSum(@Param("marketId")Long marketId);
}