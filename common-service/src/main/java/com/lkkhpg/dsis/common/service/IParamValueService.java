/*
 *
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmParamValue;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 组织属性值Service.
 * 
 * @author chenjingxiong
 */
public interface IParamValueService extends ProxySelf<IParamValueService> {

    /**
     * 获取组织属性值.
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名
     * @param type
     *            参数level：MARKET=10002 SALES-ORG=10003 COUNTRY=10004 STATE=10005
     *            CITY=10006 OPERATING_UNIT=11001 INV-ORG=11002
     * @param orgId
     *            saleOrgId, invOrgId，MarketId，OuId
     * @param orgType
     *            组织类型类型INV、SALES、MARKET、OU
     * @return 对应orgId的Level
     */
    List<String> getParamValues(IRequest request, String paramName, Long type, String orgId, String orgType);

    /**
     * 获取对应Location Code.
     * 
     * @param request
     *            统一上下文
     * @param orgId
     *            saleOrgId, invOrgId，MarketId，OuId
     * @param orgType
     *            组织类型类型INV、SALES、MARKET、OU
     * @param type
     *            COUNTRY=10004 STATE=10005 CITY=10006
     * @return Location Code.
     */
    String getLocationCode(IRequest request, String orgType, String orgId, Long type);

    /**
     * 查询组织属性表,取得参数值.
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名
     * @param type
     *            六种类型：MARKET=10002 SALES-ORG=10003 COUNTRY=10004 STATE=10005
     *            CITY=10006 OPERATING_UNIT=11001 INV-ORG=11002
     * @param locationCode
     *            地点代码
     * @return 配置的参数值.
     */
    List<SpmParamValue> queryParamLinesTable(IRequest request, String paramName, Long type, String locationCode);

    /**
     * 获取父组织ID.
     * 
     * @param request
     *            统一上下文
     * @param orgType
     *            组织类型类型INV、SALES、MARKET、OU
     * @param orgId
     *            对应的组织ID
     * @return 组织ID
     */
    String getParentOrgID(IRequest request, String orgType, String orgId);

    /**
     * 获取Market 获取 OU 的ID.
     * 
     * @param request
     *            统一上下文
     * @param orgType
     *            组织类型类型INV、SALES、MARKET、OU
     * @param orgId
     *            对应的组织ID
     * @return 市场、业务实体ID
     */
    String getMarketOrOuId(IRequest request, String orgType, String orgId);
}
