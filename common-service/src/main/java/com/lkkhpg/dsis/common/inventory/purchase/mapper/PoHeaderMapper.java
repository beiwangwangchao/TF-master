/*
 *
 */
package com.lkkhpg.dsis.common.inventory.purchase.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.purchase.dto.PoHeader;


/**
 * 采购订单Mapper.
 * @author HuangJiaJing
 *
 */
public interface PoHeaderMapper {
    int deleteByPrimaryKey(Long poHeaderId);

    int insert(PoHeader record);

    int insertSelective(PoHeader record);

    PoHeader selectByPrimaryKey(Long poHeaderId);

    int updateByPrimaryKeySelective(PoHeader record);

    int updateByPrimaryKey(PoHeader record);
    
    List<PoHeader> queryByPoHeader(PoHeader record);
    
}