/*
 *
 */
package com.lkkhpg.dsis.common.inventory.purchase.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.purchase.dto.PoLine;


/**
 * 采购订单行行表.
 * @author HuangJiaJing
 *
 */
public interface PoLineMapper {
    int deleteByPrimaryKey(Long poLineId);

    int insert(PoLine record);

    int insertSelective(PoLine record);

    PoLine selectByPrimaryKey(Long poLineId);

    int updateByPrimaryKeySelective(PoLine record);

    int updateByPrimaryKey(PoLine record);
    
    List<PoLine> queryByPoLine(PoLine record);
}