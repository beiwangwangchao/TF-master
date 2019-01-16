/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductCategoriesRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductCategoriesResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 产品查询接口.
 * 
 * @author fengwanjun
 *
 */
public interface IGetProductService extends ProxySelf<IGetProductService> {

    /**
     * 产品类别查询接口.
     * 
     * @param getProductCategoriesRequest
     *            lang 语言 market 市场 saleOrganization 销售组织 companyCode 公司代码
     * @return List<GetProductCategoriesResponse>
     * @throws DAppException
     */
    List<GetProductCategoriesResponse> getProductCategories(GetProductCategoriesRequest getProductCategoriesRequest)
            throws DAppException;

    /**
     * 产品列表查询接口.
     * 
     * @param request
     *            请求上下文
     * @param getProductListRequest
     *            产品列表参数
     * @param getProductList
     *            lang 语言 market 市场 saleOrganization 销售组织 companyCode 公司代码
     *            cateCode 产品类别代码 productCodes 产品代码
     * @return List<GetProductListResponse>
     * @throws DAppException
     */
    List<GetProductListResponse> getProductList(IRequest request, GetProductListRequest getProductListRequest)
            throws DAppException;

}