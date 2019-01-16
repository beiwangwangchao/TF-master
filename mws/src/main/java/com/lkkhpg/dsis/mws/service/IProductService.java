/*
 *
 */
package com.lkkhpg.dsis.mws.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.product.dto.InvCategoryB;
import com.lkkhpg.dsis.common.product.dto.InvItemAttrTl;
import com.lkkhpg.dsis.mws.dto.Product;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import javafx.beans.binding.ObjectExpression;

/**
 * 商品接口.
 * 
 * @author xiawang.liu
 *
 */
public interface IProductService extends ProxySelf<IProductService> {

    /**
     * 查询商品类别.
     * 
     * @param requestContext
     *            统一上下文
     * @param invCategoryB
     *            商品类型
     * @return 商品类型
     */
    List<InvCategoryB> getProductCategorys(IRequest requestContext);




    Map<String,Object> getProductCateryItem(IRequest requestContext);
    /**
     * 获取商品类别&类别商品.
     * 
     * @param requestContext
     *            统一上下文
     * @return 类别商品详细信息
     *         [{"categoryId-categoryName":[{"productId":"","productName":""},{
     *         "productId":"","productName":""}]},
     *         {"categoryId-categoryName":[{"productId":"","productName":""},{
     *         "productId":"","productName":""}]}]
     */
    Map<String, Object> getProductsByInCategorys(IRequest requestContext);

    /**
     * 根据查询条件查询商品详细.
     * 
     * @param requestContext
     *            统一上下文
     * @param record
     *            商品
     * @param salesOrgId
     *            组织Id
     * @return 响应信息(id,name,...简介，规格，使用方式...)
     * 
     */
    List<Product> getDetailProductsByWhereClause(IRequest requestContext, Product record);

    /**
     * 根据查询条件查询商品简易.
     * 
     * @param requestContext
     *            统一上下文
     * @param record
     *            商品
     * @param pagesize
     * @param page
     * @return 响应信息（id,name,DESC,PV,price,image）
     */
    List<Product> getSimpleProductsByWhereClause(IRequest requestContext, Product record, int page,
            int pagesize);

    /**
     * @param requestContext
     *            统一上下文
     * @param itemIds
     *            排除的筛选的产品ID
     * @param saleOrgId
     *            销售组织ID
     * @param itemName
     *            商品名
     * @return 响应信息（id,name,image,price,PV,规格）
     */
    List<Product> getFastProductsByWhereClause(IRequest requestContext, List<Long> itemIds, Long saleOrgId,
            String itemName);

    /**
     * @param requestContext
     *            统一上下文
     * @param products
     *            商品
     * @return 响应信息
     */
    List<Product> getImageForProducts(IRequest requestContext, List<Product> products);
    
    List<InvItemAttrTl> getWhetherHide(IRequest request, Long itemId);

    Integer getXtfSum(Long marketId);
}
