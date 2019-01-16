/*
 *
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * location服务接口.
 * 
 * @author Zhao
 *
 */
public interface ILocationParamService extends IParamValueService {

    /**
     * 获取组织属性值.
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名
     * @param type
     *            参数level：COUNTRY=10004 STATE=10005 CITY=10006
     * @param orgId
     *            saleOrgId, invOrgId，MarketId，OuId
     * @param orgType
     *            组织类型类型INV、SALES、MARKET、OU
     * @return 对应orgId的Level
     */
    List<String> getParamValues(IRequest request, String paramName, Long type, String orgId, String orgType);

}
