/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * market服务接口.
 * 
 * @author Zhao
 *
 */
public interface IMarketParamService extends IParamValueService {

    /**
     * 获取组织属性值.
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名
     * @param type
     *            参数level：MARKET=10002 OPERATING_UNIT=11001
     * @param orgId
     *            saleOrgId, invOrgId，MarketId，OuId
     * @param orgType
     *            组织类型类型INV、SALES、MARKET、OU
     * @return 对应orgId的Level
     */
    List<String> getParamValues(IRequest request, String paramName, Long type, String orgId, String orgType);

    /**
     * 获取唯一组织属性值.
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名
     * @param type
     *            参数level：MARKET=10002 OPERATING_UNIT=11001
     * @param orgId
     *            saleOrgId, invOrgId，MarketId，OuId
     * @param orgType
     *            组织类型类型INV、SALES、MARKET、OU
     * @return 对应orgId的Level
     */
    String getParamValue(IRequest request, String paramName, Long type, String orgId, String orgType);

    /**
     * 根据参数名称、级别、参数值获取级别值.
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名
     * @param type
     *            参数level：MARKET=10002 OPERATING_UNIT=11001
     * @param paramValue
     *            参数值
     * @return levelValue级别值
     */
    String getLevelValueParamValue(IRequest request, String paramName, Long type, String paramValue);
}
