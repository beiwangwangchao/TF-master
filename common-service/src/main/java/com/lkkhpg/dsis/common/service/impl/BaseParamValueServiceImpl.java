/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.config.dto.SpmParamValue;
import com.lkkhpg.dsis.common.config.mapper.SpmParamValueMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.IParamValueService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 组织属性值Service的抽象实现类.
 * 
 * @author chenjingxiong
 */
@Service
public abstract class BaseParamValueServiceImpl implements IParamValueService {

    @Autowired
    private SpmParamValueMapper spmParamValueMapper;

    /**
     * 留给子类实现.
     */
    @Override
    public abstract List<String> getParamValues(IRequest request, String paramName, Long type, String orgId,
            String orgType);

    /**
     * 获取对应Location Code.
     * 
     * @param orgId saleOrgId, invOrgId，MarketId，OuId @param orgType 组织类型 @param
     * type COUNTRY=10004 STATE=10005 CITY=10006 @return 响应信息
     */
      public String getLocationCode(IRequest request, String orgType, String orgId, Long type) {
        String locationCode = null;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (SystemProfileConstants.ORG_TYPE_SALES.equals(orgType)) { // 销售线
            list = spmParamValueMapper.getLocationCodeBySales(Long.parseLong(orgId));
        } else if (SystemProfileConstants.ORG_TYPE_INV.equals(orgType)) { // 库存线
            list = spmParamValueMapper.getLocationCodeByInv(Long.parseLong(orgId));
        } else if (SystemProfileConstants.ORG_TYPE_MARKET.equals(orgType)) { // market
            list = spmParamValueMapper.getLocationCodeByMarket(Long.parseLong(orgId));
        } else if (SystemProfileConstants.ORG_TYPE_OU.equals(orgType)) { // ou
            list = spmParamValueMapper.getLocationCodeByOu(Long.parseLong(orgId));
        }

        if (list.isEmpty()) {
            return null;
        }

        if (SystemProfileConstants.COUNTRY.equals(type)) {
            locationCode = list.get(0).get("countryCode");
        } else if (SystemProfileConstants.STATE.equals(type)) {
            locationCode = list.get(0).get("statusCode");
        } else if (SystemProfileConstants.CITY.equals(type)) {
            locationCode = list.get(0).get("cityCode");
        }
        return locationCode;
    }

    /**
     * 获取父组织ID.
     * 
     * @param request 统一上下文 @param orgType SALES-ORG=10003、 INV-ORG=11002 @param
     * orgId 对应的组织ID @return 响应信息
     */
    public String getParentOrgID(IRequest request, String orgType, String orgId) {
        String parentId = null;
        if (SystemProfileConstants.ORG_TYPE_SALES.equals(orgType)) { // 销售线
            parentId = spmParamValueMapper.getParentOrgIDBySales(Long.parseLong(orgId));
        } else if (SystemProfileConstants.ORG_TYPE_INV.equals(orgType)) { // 库存线
            // 库存组织单层无父级别
            return null;
        } // Market和Ou没有父级组织,从orgType的取值范围里忽略
        return parentId;
    }

    /**
     * 获取Market 获取 OU 的ID.
     * 
     * @param request 统一上下文 @param orgType SALES-ORG=10003、 INV-ORG=11002 @param
     * orgId 对应的组织ID @return 响应信息
     */
    public String getMarketOrOuId(IRequest request, String orgType, String orgId) {
        String marketOrOuId = null;
        if (SystemProfileConstants.ORG_TYPE_SALES.equals(orgType)) { // 销售线
            marketOrOuId = spmParamValueMapper.getMarketId(Long.parseLong(orgId));
        } else if (SystemProfileConstants.ORG_TYPE_INV.equals(orgType)) { // 库存线
            // TODO 现在库存组织表没有父级组织ID
            marketOrOuId = spmParamValueMapper.getOuId(Long.parseLong(orgId));
        } // Market和Ou没有父级组织,从orgType的取值范围里忽略
        return marketOrOuId;
    }

    /**
     * 查询组织属性表,取得参数值.
     * 
     * @param request 统一上下文 @param paramName 参数值 @param type MARKET=10002
     * SALES-ORG=10003 COUNTRY=10004 STATE=10005 CITY=10006 OPERATING_UNIT=11001
     * INV-ORG=11002 @param locationCode 位置code @return 响应信息
     */
    public List<SpmParamValue> queryParamLinesTable(IRequest request, String paramName, Long type,
            String locationCode) {
        Map<Object, Object> map = new HashMap<>();
        map.put("paramName", paramName);
        map.put("type", type);
        map.put("locationCode", locationCode);
        List<SpmParamValue> result = spmParamValueMapper.queryParamLines(map);
        return result;
    }

    /**
     * 查询组织属性表,根据参数名称、级别、参数值获取级别值.
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名称
     * @param type
     *            MARKET=10002 SALES-ORG=10003 COUNTRY=10004 STATE=10005
     *            CITY=10006 OPERATING_UNIT=11001 INV-ORG=11002
     * @param paramValue
     *            参数值
     * @return 响应信息
     */
    public List<SpmParamValue> queryParamLinesByParamValue(IRequest request, String paramName, Long type,
            String paramValue) {
        Map<Object, Object> map = new HashMap<>();
        map.put("paramName", paramName);
        map.put("type", type);
        map.put("paramValue", paramValue);
        List<SpmParamValue> result = spmParamValueMapper.queryParamLinesByParamValue(map);
        return result;
    }
}
