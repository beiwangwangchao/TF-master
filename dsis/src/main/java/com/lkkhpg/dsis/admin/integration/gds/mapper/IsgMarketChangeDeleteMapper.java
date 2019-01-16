/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeDelete;

/**
 * 删除转出市场申请Mapper.
 * 
 * @author shenqb
 */
public interface IsgMarketChangeDeleteMapper {
    int deleteByPrimaryKey(BigDecimal interfaceId);

    int insert(IsgMarketChangeDelete record);

    int insertSelective(IsgMarketChangeDelete record);

    IsgMarketChangeDelete selectByPrimaryKey(BigDecimal interfaceId);

    int updateByPrimaryKeySelective(IsgMarketChangeDelete record);

    int updateByPrimaryKey(IsgMarketChangeDelete record);
    
    List<IsgMarketChangeDelete> selectByParams(Map<String, Object> params);
}