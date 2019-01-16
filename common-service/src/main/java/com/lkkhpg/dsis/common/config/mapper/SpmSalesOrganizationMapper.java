/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 销售区域MAPPER.
 *
 * @author frank.li
 */
public interface SpmSalesOrganizationMapper {

    int deleteByPrimaryKey(Long bsalesOrgId);

    int insert(SpmSalesOrganization record);

    int insertSelective(SpmSalesOrganization record);

    SpmSalesOrganization selectByPrimaryKey(Long bsalesOrgId);

    SpmSalesOrganization queryBaseInfo(@Param("salesOrgId") Long salesOrgId);

    int updateByPrimaryKeySelective(SpmSalesOrganization record);

    int updateByPrimaryKeyMarket(SpmSalesOrganization record);

    int updateByPrimaryKey(SpmSalesOrganization record);

    List<SpmSalesOrganization> querySalesOrgByMarkId(SpmSalesOrganization org);

    List<SpmSalesOrganization> queryBySalesOrganization(SpmSalesOrganization org);

    List<SpmSalesOrganization>  queryBySalesOrgByTime(SpmSalesOrganization org);

    List<SpmSalesOrganization> queryByUserAndRole();

    List<SpmSalesOrganization> queryBySalesFu(SpmSalesOrganization record);

    List<SpmSalesOrganization> queryBySalesZi(SpmSalesOrganization record);

    List<SpmSalesOrganization> queryNullBy(SpmSalesOrganization record);

    List<SpmSalesOrganization> selectByMarketId(Long marketId);


    List<SpmSalesOrganization>selectOrganization(SpmSalesOrganization marketId);

    List<SpmSalesOrganization>queryOrganization(Long marketId);

    SpmSalesOrganization selectBySalesOrgId(@Param("salesOrgId") Long salesOrgId);
    
    SpmSalesOrganization getSalesOrgId(Long marketId);

    List<SpmSalesOrganization> getSalesOrgByCodeAndMarket(SpmSalesOrganization record);

    Long getSalesOrgIdByCode(String code);

    List<SpmSalesOrganization> queryAllSalesOrganization(SpmSalesOrganization salesOrganization);

    List<SpmSalesOrganization> querySalesOrganizationByRole(@Param("roleId") long roleId);

}