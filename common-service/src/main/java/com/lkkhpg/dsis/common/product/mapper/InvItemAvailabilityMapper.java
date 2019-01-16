/*
 *
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.product.dto.InvItemAvailability;

/**
 * 商品可用性mapper.
 * 
 * @author wangchao
 *
 */
public interface InvItemAvailabilityMapper {
    int deleteByPrimaryKey(InvItemAvailability key);

    int insert(InvItemAvailability record);

    int insertSelective(InvItemAvailability record);

    InvItemAvailability selectByPrimaryKey(InvItemAvailability key);

    int updateByPrimaryKeySelective(InvItemAvailability record);

    int updateByPrimaryKey(InvItemAvailability record);

    List<InvItemAvailability> getItemAvailabilities(Long itemId);
}