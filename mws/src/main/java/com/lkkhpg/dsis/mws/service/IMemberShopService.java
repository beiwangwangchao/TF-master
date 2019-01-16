/*
 *
 */
package com.lkkhpg.dsis.mws.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 选择店铺service接口.
 * @author Zhaoqi
 *
 */
public interface IMemberShopService extends ProxySelf<IMemberShopService> {

    /**
     * 查询出所有市场.
     * 
     * @param request
     *            统一上下文
     * @param marketId 市场id
     * @return 返回市场name
     */
    List<SpmMarket> queryMarket(IRequest request, Long marketId);
    
    /**
     * 根据id获取市场下面的店铺.
     * 
     * @param request
     *            统一上下文
     * @param marketId
     *            市场id
     * @return 对应的市场信息
     */
    List<SpmSalesOrganization> selectByMarketName(IRequest request, Long marketId);
    
    SpmSalesOrganization showShopBySalesOrgId(IRequest request, Long salesOrgId);
    
    SpmMarket selectMarketByCode(IRequest request, String code);
    
    SpmSalesOrganization selectBySalesOrgId(IRequest request, Long salesOrgId);
}
