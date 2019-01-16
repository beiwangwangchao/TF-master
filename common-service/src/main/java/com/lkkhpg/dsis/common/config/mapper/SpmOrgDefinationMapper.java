/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmOrgDefination;

/**
 * 组织定义mapper.
 * 
 * @author wangc
 */
public interface SpmOrgDefinationMapper {
    int deleteByPrimaryKey(Short definationId);

    int insert(SpmOrgDefination record);

    int insertSelective(SpmOrgDefination record);

    SpmOrgDefination selectByPrimaryKey(Short definationId);

    int updateByPrimaryKeySelective(SpmOrgDefination record);

    int updateByPrimaryKey(SpmOrgDefination record);
    
    List<SpmOrgDefination> queryByOrganization(SpmOrgDefination record);

    List<SpmOrgDefination> queryByOrganization2(SpmOrgDefination record);

    List<Long> querySameCompanySalesOrgId(Long salesOrgId);
    
    Integer checkRepeatByCode(String orgCode);
    
}