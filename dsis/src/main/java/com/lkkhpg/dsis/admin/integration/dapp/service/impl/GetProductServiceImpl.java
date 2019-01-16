/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductCategoriesRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductCategoriesResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListData;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListPriceData;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListResponse.Price;
import com.lkkhpg.dsis.admin.integration.dapp.mapper.GetProductListMapper;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetProductService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmTaxMapper;
import com.lkkhpg.dsis.common.product.mapper.InvItemHierarchyMapper;
import com.lkkhpg.dsis.common.product.mapper.InvItemMapper;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 产品查询接口Controller.
 *
 * @author fengwanjun
 */
@Transactional
@Service
public class GetProductServiceImpl implements IGetProductService {

    public static Integer hundred = 100;

    @Autowired
    private GetProductListMapper getProductListMapper;

    @Autowired
    private IParamService paramServiceImpl;

    @Autowired
    private SpmTaxMapper spmTaxMapper;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private InvItemHierarchyMapper invItemHierarchyMapper;

    @Autowired
    private InvItemMapper invItemMapper;
    
    @Autowired
    private SpmMarketMapper spmMarketMapper;

    /**
     * 产品类别查询接口.
     *
     * @param getProductCategoriesRequest
     *            lang 语言 market 市场 saleOrganization 销售组织 companyCode 公司代码
     * @return List<InvCategoryB>
     * @throws DAppException
     *             DApp接口异常
     */
    @Override
    @Transactional
    public List<GetProductCategoriesResponse> getProductCategories(
            GetProductCategoriesRequest getProductCategoriesRequest) throws DAppException {
        List<GetProductCategoriesResponse> getProductCategoriesList = getProductListMapper
                .selectTopCategory(getProductCategoriesRequest.getLang());
        if (getProductCategoriesList == null || getProductCategoriesList.size() == 0) {
            return null;
        }
        List<GetProductCategoriesResponse> getProductCategoriesResponseList = new ArrayList<GetProductCategoriesResponse>();
        for (GetProductCategoriesResponse getProductCategoriesResponse : getProductCategoriesList) {
            List<String> saleOrganizationList = new ArrayList<String>();
            if (getProductCategoriesRequest.getSaleOrganizations().contains(",")) {
                String[] saleOrganizations = getProductCategoriesRequest.getSaleOrganizations().split(",");
                for (String saleOrg : saleOrganizations) {
                    saleOrganizationList.add(saleOrg);
                }
            } else {
                saleOrganizationList.add(getProductCategoriesRequest.getSaleOrganizations());
            }
            // children
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("lang", getProductCategoriesRequest.getLang());
            map.put("saleOrganizationList", saleOrganizationList);
            map.put("categoryId", getProductCategoriesResponse.getParentCateCode());
            List<GetProductCategoriesResponse> getProductCategoriesChilendList = getProductListMapper
                    .selectChilendCategory(map);
            if (getProductCategoriesChilendList != null) {
                getProductCategoriesResponse.setCategories(getProductCategoriesChilendList);
                getProductCategoriesResponseList.add(getProductCategoriesResponse);
            }
        }
        return getProductCategoriesResponseList;
    }

    /**
     * 产品列表查询接口.
     *
     * @param request
     *            请求上下文
     * @param getProductListRequest
     *            lang 语言 market 市场 saleOrganization 销售组织 companyCode 公司代码
     *            cateCode 产品类别代码 productCodes 产品代码
     * @return List<GetProductListResponse>
     * @throws DAppException
     *             DApp接口异常
     */
    @Override
    @Transactional
    public List<GetProductListResponse> getProductList(IRequest request, GetProductListRequest getProductListRequest)
            throws DAppException {
        List<String> saleOrganizationList = new ArrayList<String>();
        if (getProductListRequest.getSaleOrganizations().contains(",")) {
            String[] saleOrganizations = getProductListRequest.getSaleOrganizations().split(",");
            for (String saleOrg : saleOrganizations) {
                saleOrganizationList.add(saleOrg);
            }
        } else {
            saleOrganizationList.add(getProductListRequest.getSaleOrganizations());
        }
        getProductListRequest.setSaleOrganizationList(saleOrganizationList);
        if (getProductListRequest.getProductCodes() != null) {
            List<String> productCodeList = new ArrayList<String>();
            if (getProductListRequest.getProductCodes().contains(",")) {
                String[] productCodes = getProductListRequest.getProductCodes().split(",");
                for (String pc : productCodes) {
                    productCodeList.add(pc);
                }
            } else {
                productCodeList.add(getProductListRequest.getProductCodes());
            }
            getProductListRequest.setProductCodeList(productCodeList);
        }

        List<GetProductListData> getProductListDatas = getProductListMapper
                .selectProductsBySalesOrg(getProductListRequest);
        List<GetProductListResponse> getProductListResponses = new ArrayList<GetProductListResponse>();
        GetProductListResponse getProductListResponse = null;
        
        if (getProductListDatas != null && getProductListDatas.size() > 0) {

            for (GetProductListData getProductListData : getProductListDatas) {
                if (getProductListData.getRemoveFlag() == 0) {
                    getProductListResponse = new GetProductListResponse();
                    // products
                    getProductListResponse.setSaleOrganization(getProductListData.getSaleOrganization());
                    getProductListResponse.setCateCode(getProductListData.getCateCode());
                    getProductListResponse.setProductCode(getProductListData.getProductCode());
                    getProductListResponse.setProductName(getProductListData.getProductName());
                    if (getProductListData.getDapp() != null && "Y".equals(getProductListData.getDapp())) {
                        getProductListResponse.setDapp(getProductListData.getDapp());
                    } else {
                        getProductListResponse.setDapp("N");
                    }
                    getProductListResponse.setPv(getProductListData.getPv());
                    getProductListResponse.setInventory(getProductListData.getInventory());
                    getProductListResponse.setOrderedQty(getProductListData.getOrderedQty());

                    if (getProductListData.getBackOrder() != null && getProductListData.getBackOrder().equals("Y")) {
                        getProductListResponse.setBackOrder(true);
                    } else {
                        getProductListResponse.setBackOrder(false);
                    }
                    // prices
                    Map<String, Object> priceMap = new HashMap<String, Object>();
                    priceMap.put("itemId", getProductListData.getProductId());
                    priceMap.put("saleOrganization", getProductListData.getSaleOrganization());
                    List<GetProductListPriceData> prices = getProductListMapper.selectPriceByItemId(priceMap);

                    // 是否启用税
                    String enableTax = getProductListData.getEnableTax();

                    // 价格是否包含税
                    String includeTax = getProductListData.getPriceIncludeTax();

                    // 计算税率
                    BigDecimal taxPercent = getProductListData.getTaxPercent();
                        if (null != taxPercent) {
                            // 如果获取到税则返回
                            getProductListResponse.setTaxRate(taxPercent.doubleValue() / 100d);
                    }else{
                        getProductListResponse.setTaxRate(null);
                        taxPercent = BigDecimal.ZERO;
                    }
                        //加拿大市场特殊逻辑：如果是该市场下的特定商品，则税率为7%（后面调整）
                        Long orgId = spmSalesOrganizationMapper.getSalesOrgIdByCode(getProductListData.getSaleOrganization());
                        SpmMarket market = spmMarketMapper.selectBySalesOrgId(orgId);
                        if("15160502".equals(getProductListData.getProductCode())){
                            if("SBC".equals(getProductListData.getSaleOrganization())){
                                taxPercent = BigDecimal.valueOf(7);
                                getProductListResponse.setTaxRate(taxPercent.doubleValue() / 100d);
                            }
                            if("SONT".equals(getProductListData.getSaleOrganization())){
                                taxPercent = BigDecimal.valueOf(0);
                                getProductListResponse.setTaxRate(taxPercent.doubleValue() / 100d);
                            }
                        }
                      
                    if (prices.size() > 0) {
                        List<GetProductListResponse.Price> pricesR = new ArrayList<GetProductListResponse.Price>();
                        for (GetProductListPriceData price : prices) {
                            GetProductListResponse.Price priceR = new GetProductListResponse.Price();
                            priceR = processPrice(priceR, price.getPrice(), enableTax, includeTax, taxPercent.doubleValue(), price.getPrecision(), price.getDisableTaxFlag());
                            priceR.setType(price.getType());
                            priceR.setCurrency(price.getCurrency());
                            pricesR.add(priceR);
                        }
                        getProductListResponse.setPrices(pricesR);
                    }
                    getProductListResponses.add(getProductListResponse);
                }
            }
        }
        return getProductListResponses;
    }

    private Price processPrice(Price priceR, Double price, String enableTax, String includeTax, Double taxPercent, Long precision, String disableTaxFlag) {
        //先判断是否启用税标识
        if(!StringUtils.isEmpty(disableTaxFlag) && disableTaxFlag.equals(BaseConstants.YES)){
            priceR.setPrice(new BigDecimal(price).setScale(precision.intValue(), BigDecimal.ROUND_HALF_UP));
            priceR.setPriceBeforeTax(new BigDecimal(price).setScale(precision.intValue(), BigDecimal.ROUND_HALF_UP));
            return priceR;
        }
        
        // 不启用税
        if (StringUtils.isEmpty(enableTax) || BaseConstants.NO.equals(enableTax)) {
            priceR.setPrice(new BigDecimal(price).setScale(precision.intValue(), BigDecimal.ROUND_HALF_UP));
            priceR.setPriceBeforeTax(new BigDecimal(price).setScale(precision.intValue(), BigDecimal.ROUND_HALF_UP));
            // 启用税
        } else {
            // 如果启用税，并且价格不含税
            if (!StringUtils.isEmpty(includeTax) && BaseConstants.NO.equals(includeTax)) {
                priceR.setPriceBeforeTax(new BigDecimal(price).setScale(precision.intValue(), BigDecimal.ROUND_HALF_UP));
                Double priceAfterTax = price * (1 + taxPercent / hundred);
                priceR.setPrice(new BigDecimal(priceAfterTax).setScale(precision.intValue(), BigDecimal.ROUND_HALF_UP));

                // 如果启用税，并且价格包含税
            } else if (!StringUtils.isEmpty(includeTax) && BaseConstants.YES.equals(includeTax)) {
                priceR.setPrice(new BigDecimal(price).setScale(precision.intValue(), BigDecimal.ROUND_HALF_UP));
                Double priceBeforeTax = price / (1 + taxPercent / hundred);
                priceR.setPriceBeforeTax(
                        new BigDecimal(priceBeforeTax).setScale(precision.intValue(), BigDecimal.ROUND_HALF_UP));
           
            
            }else{
                priceR.setPrice(new BigDecimal(price).setScale(precision.intValue(), BigDecimal.ROUND_HALF_UP));
                
                priceR.setPriceBeforeTax(new BigDecimal(price).setScale(precision.intValue(), BigDecimal.ROUND_HALF_UP));
           
            
            }
        }

        return priceR;
    }
}