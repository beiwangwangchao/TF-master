/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * organization服务接口.
 * 
 * @author Zhao
 *
 */
public interface IOrgParamService extends IParamValueService {

    /**
     * 获取组织属性值.
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名
     * @param type
     *            参数level：SALES-ORG=10003 INV-ORG=11002
     * @param orgId
     *            saleOrgId, invOrgId，MarketId，OuId
     * @param orgType
     *            组织类型类型INV、SALES、MARKET、OU
     * @return 对应orgId的Level
     */
    List<String> getParamValues(IRequest request, String paramName, Long type, String orgId, String orgType);
}
