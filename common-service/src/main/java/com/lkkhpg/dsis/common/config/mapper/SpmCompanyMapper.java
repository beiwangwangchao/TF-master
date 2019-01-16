/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import com.lkkhpg.dsis.common.config.dto.SpmCompany;

import java.util.List;

/**
 * 公司MAPPER.
 * 
 * @author frank.li
 */
public interface SpmCompanyMapper {
    int insert(SpmCompany record);

    SpmCompany selectByPrimaryKey(Long companyId);

    int updateByPrimaryKey(SpmCompany record);

    List<SpmCompany> queryCompany(SpmCompany record);

    List<SpmCompany> queryCompany2(SpmCompany record);

    List<SpmCompany> queryCompanyByMarketId(SpmCompany record);

    SpmCompany queryParentName(SpmCompany record);
    
    int getUniqueCount(SpmCompany record);

    List<SpmCompany> queryNo(SpmCompany company);

    List<String>  queryPartner();

    SpmCompany selectPartner( Long marketId);
}