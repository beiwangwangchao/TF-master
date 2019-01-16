/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.product.dto.InvItemTl;

/**
 * 商品多语言mapper.
 * 
 * @author wangchao
 *
 */
public interface InvItemTlMapper {
    int deleteByPrimaryKey(InvItemTl key);

    int insert(InvItemTl record);

    int insertSelective(InvItemTl record);

    InvItemTl selectByPrimaryKey(InvItemTl record);

    int updateByPrimaryKeySelective(InvItemTl record);

    int updateByPrimaryKey(InvItemTl record);

    List<InvItemTl> getItemTlsByItemId(Long itemId);
}