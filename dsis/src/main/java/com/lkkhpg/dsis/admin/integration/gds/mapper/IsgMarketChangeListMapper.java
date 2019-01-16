/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeList;

/**
 * gds市场变更接口表.
 * 
 * @author yuchuan.zeng@hand-china.com
 *
 */
public interface IsgMarketChangeListMapper {
    int deleteByPrimaryKey(Long interfaceId);

    int insert(IsgMarketChangeList record);

    int insertSelective(IsgMarketChangeList record);

    List<IsgMarketChangeList> selectByPrimaryKey(Long interfaceId);

    int updateByPrimaryKeySelective(IsgMarketChangeList record);

    int updateByPrimaryKey(IsgMarketChangeList record);

    List<IsgMarketChangeList> selectByParams(Map<String, Object> params);
    
    List<IsgMarketChangeList> selectByGdsIdAndMemberCode(Map<String, Object> params);
}