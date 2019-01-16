/*
 *
 */
package com.lkkhpg.dsis.common.product.mapper;

import com.lkkhpg.dsis.common.product.dto.InvUnitOfMeasureTl;

/**
 * 单位多语言Mapper.
 * 
 * @author wangchao
 *
 */
public interface InvUnitOfMeasureTlMapper {
    int deleteByPrimaryKey(InvUnitOfMeasureTl record);

    int insert(InvUnitOfMeasureTl record);

    int insertSelective(InvUnitOfMeasureTl record);

    InvUnitOfMeasureTl selectByPrimaryKey(InvUnitOfMeasureTl record);

    int updateByPrimaryKeySelective(InvUnitOfMeasureTl record);

    int updateByPrimaryKey(InvUnitOfMeasureTl record);
}