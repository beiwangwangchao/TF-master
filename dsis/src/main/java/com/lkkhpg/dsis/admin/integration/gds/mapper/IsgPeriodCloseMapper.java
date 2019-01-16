/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgPeriodClose;

/**
 * 关闭月份mapper.
 * 
 * @author Jessey
 *
 */
public interface IsgPeriodCloseMapper {

    int deleteByPrimaryKey(Short interfaceId);

    int insert(IsgPeriodClose record);

    int insertSelective(IsgPeriodClose record);

    IsgPeriodClose selectByPrimaryKey(Short interfaceId);

    int updateByPrimaryKeySelective(IsgPeriodClose record);

    int updateByPrimaryKey(IsgPeriodClose record);

    List<IsgPeriodClose> selectByParams(Map<String, Object> params);
}