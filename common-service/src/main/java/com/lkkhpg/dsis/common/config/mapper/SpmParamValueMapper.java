/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.config.dto.SpmParamValue;

/**
 * 组织参数行表接口.
 * 
 * @author Zhao
 *
 */
public interface SpmParamValueMapper {

    /**
     * 获取参数属性.
     * 
     * @param map
     *            参数包括String paramName, Long type, String orgId
     * @return 响应信息
     */
    List<SpmParamValue> getParamValue(Map<?, ?> map);

    /**
     * 销售线-取得locationCode.
     * 
     * @param orgId
     *            组织ID(SalesOrg,InvOrg,Market,OU)
     * @return 响应信息
     */
    List<Map<String, String>> getLocationCodeBySales(Long orgId);

    /**
     * 库存线-取得locationCode.
     * 
     * @param orgId
     *            组织ID(SalesOrg,InvOrg,Market,OU)
     * @return 响应信息
     */
    List<Map<String, String>> getLocationCodeByInv(Long orgId);

    /**
     * 销售线-取得MarketCode.
     * 
     * @param orgId
     *            组织ID(SalesOrg,InvOrg,Market,OU)
     * @return 响应信息
     */
    List<Map<String, String>> getLocationCodeByMarket(Long orgId);

    /**
     * 销售线-取得MarketCode.
     * 
     * @param orgId
     *            组织ID(SalesOrg,InvOrg,Market,OU)
     * @return 响应信息
     */
    List<Map<String, String>> getLocationCodeByOu(Long orgId);

    /**
     * 查询组织属性表,取得参数值.
     * 
     * @param map
     *            参数包括String paramName, Long type, String orgId
     * @return 响应信息
     */
    List<SpmParamValue> queryParamLines(Map<?, ?> map);

    /**
     * 查询组织属性表,根据参数名称、级别、参数值获取级别值.
     * 
     * @param map
     *            参数包括String paramName, Long type, String paramValue
     * @return 响应信息
     */
    List<SpmParamValue> queryParamLinesByParamValue(Map<?, ?> map);
    
    

    /**
     * 销售线-获取父级组织ID.
     * 
     * @param orgId
     *            组织ID(SalesOrg,InvOrg,Market,OU)
     * @return 响应信息
     */
    String getParentOrgIDBySales(Long orgId);

    /**
     * 库存线-获取父级组织ID.
     * 
     * @param orgId
     *            组织ID(SalesOrg,InvOrg,Market,OU)
     * @return 响应信息
     */
    String getParentOrgIDByInv(Long orgId);

    /**
     * 销售线-获取市场ID.
     * 
     * @param orgId
     *            组织ID(SalesOrg,InvOrg,Market,OU)
     * @return 响应信息
     */
    String getMarketId(Long orgId);

    /**
     * 库存线-获取OU-ID.
     * 
     * @param orgId
     *            组织ID(SalesOrg,InvOrg,Market,OU)
     * @return 响应信息
     */
    String getOuId(Long orgId);

}